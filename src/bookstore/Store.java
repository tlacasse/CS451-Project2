package bookstore;

import java.util.ArrayList;
import java.util.List;

import bookstore.people.Cashier;
import bookstore.people.Visitor;

public class Store {

	private final Queue<Visitor> queue;
	private final Config config;

	public Store() {
		config = Config.create();
		queue = new Queue<>();
	}

	public void start() {
		System.out.println("\n!!!!! BEGIN !!!!!\n");

		final ArrayList<Thread> cashierThreads = new ArrayList<>();
		final ArrayList<Thread> visitorThreads = new ArrayList<>();

		for (int i = 0; i < config.get(Param.CASHIERS); i++) {
			cashierThreads.add(new Thread(new Cashier(queue, config)));
		}
		for (int i = 0; i < config.get(Param.VISITORS); i++) {
			visitorThreads.add(new Thread(new Visitor(queue, config)));
		}
		startThreads(cashierThreads);
		startThreads(visitorThreads);
		stopThreads(visitorThreads);
		config.setDone();
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
