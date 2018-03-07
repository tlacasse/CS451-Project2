package bookstore;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import bookstore.people.Visitor;

public class Queue {

	private final Semaphore lock, canEnter, canExit;
	private final ArrayList<Visitor> list;
	private final Config config;

	public Queue(Config config) {
		this.config = config;
		list = new ArrayList<>();
		lock = new Semaphore(1);
		canEnter = new Semaphore(config.get(Param.QUEUEBOUND));
		canExit = new Semaphore(0);
	}

	public void enqueue(Visitor item) throws InterruptedException {
		canEnter.acquire();
		lock.acquire();
		list.add(item);
		lock.release();
		canExit.release();
	}

	public Visitor dequeue() throws InterruptedException {
		if (canExit.tryAcquire(config.get(Param.TIME) * 3, TimeUnit.MILLISECONDS)) {
			lock.acquire();
			Visitor item = list.remove(0);
			lock.release();
			canEnter.release();
			return item;
		}
		return null;
	}

	public short[] idList() {
		final short[] result = new short[config.get(Param.QUEUEBOUND)];
		int i = 0;
		for (Visitor v : list) {
			result[i++] = v.snapshot()[0];
		}
		return result;
	}

}
