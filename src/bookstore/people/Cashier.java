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

	public Cashier(Queue queue, Config config) {
		super(queue, config);
		status = Status.WAITING;
	}

	@Override
	public void run() {
		try {
			while (!config.isDone()) {
				// try to remove a visitor from the queue, can wait, but only
				// for 3 time intervals to allow this to get back to the
				// "!config.isDone()" condition
				visitor = queue.dequeue();
				if (visitor != null) {
					setStatusAndPrint(Status.CHECKINGOUT);
					for (int i = 0; i < visitor.snapshot()[Visitor.TOTALITEMS_INDEX]; i++) {
						// check out each individual item
						Thread.sleep(config.get(Param.CHECKOUT));
						visitor.checkOut();
					}
					visitor = null;
					setStatusAndPrint(Status.COMPLETE);
				} else {
					// COMPLETE is different than WAITING, after a cashier
					// completes checking out, they will either wait or checkout
					// someone else in the queue
					if (status != Status.WAITING) {
						setStatusAndPrint(Status.WAITING);
					}
				}
			}
		} catch (InterruptedException ie) {
			System.out.println("Thread Failed: " + this);
			ie.printStackTrace();
		}
		System.out.println("Cashier " + id + " is done.");
	}

	private void setStatusAndPrint(Status status) {
		this.status = status;
		System.out.println(this);
	}

	public static final int SNAPSHOT_SIZE = 3;

	// everything about the cashier the visualization needs.
	@Override
	public short[] snapshot() {
		return new short[] { id, visitor == null ? -1 : visitor.id, (short) status.ordinal() };
	}

	@Override
	public String toString() {
		return super.toString() + "[" + status + "] (" + visitor + ")";
	}

}
