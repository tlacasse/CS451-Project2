package bookstore.people;

import bookstore.Config;
import bookstore.Queue;

public class Cashier extends Person {

	public Cashier(Queue<Visitor> queue, Config config) {
		super(queue, config);
	}

	@Override
	public void run() {
		while (!config.isDone()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Cashier " + id + " is done.");
	}

}
