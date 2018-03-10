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
				visitor = queue.dequeue();
				if (visitor != null) {
					setStatusAndPrint(Status.CHECKINGOUT);
					// .snapshot[2] is desiredItems
					for (int i = 0; i < visitor.snapshot()[2]; i++) {
						Thread.sleep(config.get(Param.CHECKOUT));
						visitor.checkOut();
					}
					visitor = null;
					setStatusAndPrint(Status.COMPLETE);
				} else {
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

	@Override
	public short[] snapshot() {
		return new short[] { id, visitor == null ? -1 : visitor.id, (short) status.ordinal() };
	}

	@Override
	public String toString() {
		return super.toString() + "[" + status + "] (" + visitor + ")";
	}

}
