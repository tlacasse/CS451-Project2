package bookstore.people;

import bookstore.Config;
import bookstore.Param;
import bookstore.Program;
import bookstore.Queue;

public class Visitor extends Person {

	public static enum Status {
		SHOPPING, CHECKOUT;
	}

	private final int desiredItems;
	private int items;
	private Status status;

	public Visitor(Queue<Visitor> queue, Config config) {
		super(queue, config);
		status = Status.SHOPPING;

		items = 0;
		desiredItems = config.get(Param.ITEMS);
	}

	public Status getStatus() {
		return status;
	}

	@Override
	public void run() {
		try {
			while (items < desiredItems) {
				Thread.sleep(config.get(Param.SHOPPING));
				items++;
				System.out.println(this);
			}
			status = Status.CHECKOUT;
			System.out.println(this);
		} catch (InterruptedException ie) {
			System.out.println("Thread Failed: " + this);
			ie.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return Program.rightPad(id + "", 10) + " [" + status + "] (" + items + "/" + desiredItems + ")";
	}

}
