package bookstore;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Queue<E> {

	private final Semaphore lock, available;
	private final ArrayList<E> list;
	private final Config config;

	public Queue(Config config) {
		this.config = config;
		list = new ArrayList<>();
		lock = new Semaphore(1);
		available = new Semaphore(0);
	}

	public void enqueue(E item) throws InterruptedException {
		lock.acquire();
		list.add(item);
		lock.release();
		available.release();
	}

	public E dequeue() throws InterruptedException {
		if (available.tryAcquire(config.get(Param.TIME) * 3, TimeUnit.MILLISECONDS)) {
			lock.acquire();
			E item = list.remove(0);
			lock.release();
			return item;
		}
		return null;
	}

}
