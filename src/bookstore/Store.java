package bookstore;

import java.util.ArrayList;
import java.util.List;

import bookstore.people.Cashier;
import bookstore.people.Visitor;

public class Store {

	public final int cashierCount;
	public final int visitorCount;

	private final ArrayList<Cashier> cashiers;
	private final Queue<Visitor> queue;

	public Store(int cashierCount, int visitorCount) {
		this.cashierCount = cashierCount;
		this.visitorCount = visitorCount;

		queue = new Queue<>();
		cashiers = new ArrayList<>(cashierCount);
		for (int i = 0; i < cashierCount; i++) {
			cashiers.add(new Cashier(queue));
		}
	}

	public void start() {
		final ArrayList<Cashier> cashiers = new ArrayList<>();
		final ArrayList<Thread> cashierThreads = new ArrayList<>();
		final ArrayList<Thread> visitorThreads = new ArrayList<>();

		for (int i = 0; i < cashierCount; i++) {
			Cashier cashier = new Cashier(queue);
			cashiers.add(cashier);
			cashierThreads.add(new Thread(cashier));
		}
		for (int i = 0; i < visitorCount; i++) {
			visitorThreads.add(new Thread(new Visitor(queue)));
		}
		startThreads(cashierThreads);
		startThreads(visitorThreads);
		stopThreads(visitorThreads);
		// fix
		for (Cashier cashier : cashiers) {
			cashier.setDone();
		}
		stopThreads(cashierThreads);
	}

	private void startThreads(List<Thread> list) {
		for (Thread thread : list) {
			thread.start();
		}
	}

	private void stopThreads(List<Thread> list) {
		for (Thread thread : list) {
			try {
				thread.join();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

}
