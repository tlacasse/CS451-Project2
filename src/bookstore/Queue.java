package bookstore;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import bookstore.people.Visitor;

//The contested resource, which then needs sync.
public class Queue {

	// SEMAPHORES:

	// lock - mutex, only one thread should be able to write the list at a
	// time, or else the data could be messed up.

	// canEnter - if it has permits, the queue is not full, if not the visitor
	// will wait until it is not full to enter.

	// canExit - if it has permits, the queue has visitors, if not the cashier
	// will wait until a visitor is in the queue to take it and check it out.
	private final Semaphore lock, canEnter, canExit;

	// ArrayList not array, for simple built in add at end, remove at beginning
	// Queue.
	private final ArrayList<Visitor> list;
	private final Config config;

	public Queue(Config config) {
		this.config = config;
		list = new ArrayList<>(config.get(Param.QUEUEBOUND));

		// one permit because only one can access at a time.
		lock = new Semaphore(1);
		// queue length number of permits because a queue length number of
		// visitors can fit in the queue.
		canEnter = new Semaphore(config.get(Param.QUEUEBOUND));
		// 0 permits, -> at the very beginning, cashiers just wait until
		// visitors are done, can't take from an empty queue.
		canExit = new Semaphore(0);
	}

	public void enqueue(Visitor item) throws InterruptedException {
		canEnter.acquire(); // make sure there are spaces left in the queue, if
							// not, wait.
		lock.acquire(); // get the one mutex permit, only one thread can write
						// at a time.
		list.add(item); // critical section
		lock.release(); // give up the one permit, ( this structure forces
						// always 1 or 0)
		canExit.release(); // release a permit, which then may allow a cashier
							// to remove from the queue.
	}

	public Visitor dequeue() throws InterruptedException {
		// make sure there is a visitor in the queue. This is a tryAcquire so
		// then the Cashier can get back to its loop condition, preventing
		// infinite waiting if all visitors are already done.
		if (canExit.tryAcquire(config.get(Param.TIME) * 3, TimeUnit.MILLISECONDS)) {
			lock.acquire();
			Visitor item = list.remove(0); // critical section
			lock.release();
			canEnter.release(); // release a permit, which then may allow a
								// visitor to enter the queue.
			return item;
		}
		return null;
	}

	// used for visualization
	public short[] idList() throws InterruptedException {
		final short[] result = new short[config.get(Param.QUEUEBOUND)];
		int i = 0;
		lock.acquire(); // did get ConcurrentModException, so need to lock
						// reading. Reasonable because looping through the
						// arraylist is quite a few steps, and could easily
						// allow time to switch threads.
		for (Visitor v : list) {
			result[i++] = v.snapshot()[0];
		}
		lock.release();
		return result;
	}

}
