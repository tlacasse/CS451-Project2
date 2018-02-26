package bookstore.people;

import bookstore.Queue;

public class Cashier extends Person {

	private boolean isDone;

	public Cashier(Queue<Visitor> queue) {
		super(queue);
		isDone = false;
	}

	@Override
	public void run() {
		while (!isDone) {
			// fix
		}
	}

	public void setDone() {
		isDone = true;
	}

}
