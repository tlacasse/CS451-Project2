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

	private final Semaphore wait; // not really a synchronization semaphore,
									// just using the convenience of the
									// waiting. explained below
	private final short desiredItems;
	private short items;
	private Status status;

	public Visitor(Queue queue, Config config) {
		super(queue, config);
		status = Status.SHOPPING;

		items = 0;
		desiredItems = config.get(Param.ITEMS); // random

		wait = new Semaphore(0); // explained below
	}

	@Override
	public void run() {
		try {
			// some delay and beginning randomization (<i>visitors enter at
			// different times</i>)
			Thread.sleep((2000 + RAND.nextInt(2000)));

			// get all its items, waiting before each item -> "shopping"
			while (items < desiredItems) {
				Thread.sleep(config.get(Param.SHOPPING));
				items++;
				displayIf(Config.DISPLAY_ALL);
			}

			queue.enqueue(this); // put itself into queue, which has the lock,
									// and will wait until no other threads are
									// using the queue.
			status = Status.QUEUE;
			displayIf(Config.DISPLAY_MID);

			// wait for cashier to check out this visitor. Semaphore starts at
			// 0, guaranteeing the visitor to wait, and a permit will be
			// released only when all the items have been checked out, allowing
			// the visitor to complete execution, and join. Once all are joined
			// Store will signal that Cashiers should end too.
			wait.acquire();
			status = Status.DONE;
		} catch (InterruptedException ie) {
			System.out.println("Thread Failed: " + this);
			ie.printStackTrace();
		}
		System.out.println("Visitor " + id + " is done.");
	}

	private void displayIf(int minDisplayConfig) {
		if (config.get(Param.DISPLAY) >= minDisplayConfig) {
			System.out.println(this);
		}
	}

	// cashier will call this, eventually releasing the permit to allow this
	// visitor to finish executing. Also decreases items for visuals.
	public void checkOut() {
		if (status == Status.QUEUE) {
			items--;
			if (items == 0) {
				wait.release();
			}
		} else {
			throw new IllegalStateException("Must be in queue.");
		}
	}

	public static final int SNAPSHOT_SIZE = 4;
	public static final int TOTALITEMS_INDEX = 2;

	// everything about the visitor the visualization needs.
	@Override
	public short[] snapshot() {
		return new short[] { id, items, desiredItems, (short) status.ordinal() };
	}

	@Override
	public String toString() {
		return super.toString() + "[" + status + "] (" + items + "/" + desiredItems + ")";
	}

}
