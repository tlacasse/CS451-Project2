package bookstore.people;

import bookstore.Config;
import bookstore.Param;
import bookstore.Queue;

public class Cashier extends Person {

	public static enum Status {
		WAITING, CHECKINGOUT, COMPLETE;
	}

	private Status status;
	private Visitor visitor;

	public Cashier(Queue<Visitor> queue, Config config) {
		super(queue, config);
		status = Status.WAITING;
		visitor = null;
	}

	@Override
	public void run() {
		try {
			while (!config.isDone()) {
				visitor = queue.dequeue();
				if (visitor != null) {
					status = Status.CHECKINGOUT;
					System.out.println(this);
					Thread.sleep(config.get(Param.CHECKOUT) * visitor.items());
					visitor.checkOut();
					visitor = null;
					status = Status.COMPLETE;
					System.out.println(this);
				} else {
					if (status != Status.WAITING) {
						status = Status.WAITING;
						System.out.println(this);
					}
				}
			}
		} catch (InterruptedException ie) {
			System.out.println("Thread Failed: " + this);
			ie.printStackTrace();
		}
		System.out.println("Cashier " + id + " is done.");
	}

	@Override
	public int[] snapshot() {
		return new int[] { id, visitor == null ? -1 : visitor.id, status.ordinal() };
	}

	@Override
	public String toString() {
		return super.toString() + "[" + status + "] (" + visitor + ")";
	}

}
