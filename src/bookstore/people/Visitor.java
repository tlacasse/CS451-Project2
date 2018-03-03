package bookstore.people;

import java.util.Random;
import java.util.concurrent.Semaphore;

import bookstore.Config;
import bookstore.Param;
import bookstore.Queue;

public class Visitor extends Person {

	public static enum Status {
		SHOPPING, CHECKOUT;
	}

	private static final Random RAND = new Random();

	private final Semaphore wait;
	private final int desiredItems;
	private int items;
	private Status status;

	public Visitor(Queue<Visitor> queue, Config config) {
		super(queue, config);
		status = Status.SHOPPING;

		items = 0;
		desiredItems = config.get(Param.ITEMS);

		wait = new Semaphore(0);
	}

	public Status getStatus() {
		return status;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(RAND.nextInt(200) * config.get(Param.TIME));
			while (items < desiredItems) {
				Thread.sleep(config.get(Param.SHOPPING));
				items++;
				if (config.get(Param.DISPLAY) >= Config.DISPLAY_ALL) {
					System.out.println(this);
				}
			}
			status = Status.CHECKOUT;
			if (config.get(Param.DISPLAY) >= Config.DISPLAY_MID) {
				System.out.println(this);
			}
			queue.enqueue(this);
			wait.acquire();
		} catch (InterruptedException ie) {
			System.out.println("Thread Failed: " + this);
			ie.printStackTrace();
		}
	}

	public void checkOut() {
		if (wait.availablePermits() > 0) {
			throw new IllegalStateException();
		}
		wait.release();
	}

	@Override
	public String toString() {
		return super.toString() + "[" + status + "] (" + items + "/" + desiredItems + ")";
	}

}
