package bookstore;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Queue<E> {

	private final Semaphore lock, canEnter, canExit;
	private final ArrayList<E> list;
	private final Config config;

	public Queue(Config config) {
		this.config = config;
		list = new ArrayList<>();
		lock = new Semaphore(1);
		canEnter = new Semaphore(config.get(Param.QUEUEBOUND));
		canExit = new Semaphore(0);
	}

	public void enqueue(E item) throws InterruptedException {
		canEnter.acquire();
		lock.acquire();
		list.add(item);
		lock.release();
		canExit.release();
	}

	public E dequeue() throws InterruptedException {
		if (canExit.tryAcquire(config.get(Param.TIME) * 3, TimeUnit.MILLISECONDS)) {
			lock.acquire();
			E item = list.remove(0);
			lock.release();
			canEnter.release();
			return item;
		}
		return null;
	}

}
