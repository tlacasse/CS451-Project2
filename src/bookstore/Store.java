package bookstore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bookstore.people.Cashier;
import bookstore.people.Person;
import bookstore.people.Visitor;
import bookstore.server.Server;

public class Store {

	public static void open(Server server, Config config) throws IOException {
		Queue queue = new Queue(config);

		final Thread serverThread = new Thread(server);

		final ArrayList<Cashier> cashierList = new ArrayList<>();
		final ArrayList<Visitor> visitorList = new ArrayList<>();
		final ArrayList<Thread> cashierThreads = new ArrayList<>();
		final ArrayList<Thread> visitorThreads = new ArrayList<>();

		for (int i = 0; i < config.get(Param.CASHIERS); i++) {
			Cashier cashier = new Cashier(queue, config);
			cashierList.add(cashier);
			cashierThreads.add(new Thread(cashier));
		}
		for (int i = 0; i < config.get(Param.VISITORS); i++) {
			Visitor visitor = new Visitor(queue, config);
			visitorList.add(visitor);
			visitorThreads.add(new Thread(visitor));
		}
		server.setReferences(visitorList, cashierList, config, queue);
		server.sendSizes();
		Person.resetId();

		System.out.println("\n!!!!! BEGIN !!!!!\n");

		serverThread.start();
		startThreads(cashierThreads);
		startThreads(visitorThreads);
		stopThreads(visitorThreads);
		config.setDone();
		stopThreads(cashierThreads);
		try {
			serverThread.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	private static void startThreads(List<Thread> list) {
		for (Thread thread : list) {
			thread.start();
		}
	}

	private static void stopThreads(List<Thread> list) {
		for (Thread thread : list) {
			try {
				thread.join();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	private Store() {
	}

}
