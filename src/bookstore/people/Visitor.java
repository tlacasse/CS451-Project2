package bookstore.people;

import bookstore.Config;
import bookstore.Program;

public class Visitor extends Person {

	public static enum Status {
		SHOPPING, CHECKOUT;
	}

	private final int desiredItems;
	private int items;
	private Status status;

	public Visitor(Cashier[] cashiers) {
		super(cashiers);
		status = Status.SHOPPING;

		items = 0;
		desiredItems = Config.ITEMS.random();
	}

	public Status getStatus() {
		return status;
	}

	@Override
	public void run() {
		try {
			while (items < desiredItems) {
				Thread.sleep(Config.SHOPPING.random() * Config.TIME_UNIT);
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
