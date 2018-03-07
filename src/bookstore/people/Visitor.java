package bookstore.people;

import java.util.Random;
import java.util.concurrent.Semaphore;

import bookstore.Config;
import bookstore.Param;
import bookstore.Queue;

public class Visitor extends Person {

	public static enum Status {
		SHOPPING, QUEUE, DONE;
	}

	private static final Random RAND = new Random();

	private final Semaphore wait;
	private final short desiredItems;
	private short items;
	private Status status;

	public Visitor(Queue queue, Config config) {
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
			Thread.sleep((100 + RAND.nextInt(150)) * config.get(Param.TIME));
			while (items < desiredItems) {
				Thread.sleep(config.get(Param.SHOPPING));
				items++;
				if (config.get(Param.DISPLAY) >= Config.DISPLAY_ALL) {
					System.out.println(this);
				}
			}
			status = Status.QUEUE;
			if (config.get(Param.DISPLAY) >= Config.DISPLAY_MID) {
				System.out.println(this);
			}
			queue.enqueue(this);
			wait.acquire();
			status = Status.DONE;
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

	public short items() {
		return items;
	}

	public static final int SNAPSHOT_SIZE = 4;

	@Override
	public short[] snapshot() {
		return new short[] { id, items, desiredItems, (short) status.ordinal() };
	}

	@Override
	public String toString() {
		return super.toString() + "[" + status + "] (" + items + "/" + desiredItems + ")";
	}

}
