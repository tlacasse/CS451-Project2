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
		// contested resource, also passed in to constructor of runnables
		Queue queue = new Queue(config);

		// put server in another thread because this thread is waiting on
		// threads to die.
		final Thread serverThread = new Thread(server);

		final ArrayList<Cashier> cashierList = new ArrayList<>();
		final ArrayList<Visitor> visitorList = new ArrayList<>();
		// need to hold threads to be able to start threads and join dead
		// threads
		final ArrayList<Thread> cashierThreads = new ArrayList<>();
		final ArrayList<Thread> visitorThreads = new ArrayList<>();

		for (int i = 0; i < config.get(Param.CASHIERS); i++) {
			Cashier cashier = new Cashier(queue, config);
			cashierList.add(cashier);
			// implements Runnable so can put in thread to run as a thread
			cashierThreads.add(new Thread(cashier));
		}
		for (int i = 0; i < config.get(Param.VISITORS); i++) {
			Visitor visitor = new Visitor(queue, config);
			visitorList.add(visitor);
			// implements Runnable so can put in thread to run as a thread
			visitorThreads.add(new Thread(visitor));
		}
		server.setReferences(visitorList, cashierList, config, queue);
		server.sendSizes(); // set up dimensions of visualization
		Person.resetId();

		System.out.println("\n!!!!! BEGIN !!!!!\n");

		serverThread.start(); // starts all the threads. they then run
								// unpredictably, but that is ok because there
								// is synchronization
		startThreads(cashierThreads); // starts all the threads
		startThreads(visitorThreads); // starts all the threads
		stopThreads(visitorThreads);
		config.setDone(); // a way to stop the cashier loop, so they are not
							// waiting forever.
		stopThreads(cashierThreads);
		try {
			serverThread.join(); // waits for threads to finish. also, because
									// the program continues beyond these join
									// lines, and join was called on all
									// threads, the threads are all shut down.
									// there are no unaccounted for threads
									// still running.
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
