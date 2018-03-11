package bookstore.people;

import bookstore.Config;
import bookstore.Program;
import bookstore.Queue;

// covers the similarities between Cashier and Visitors, 
// mostly that they implement Runnable and have a reference to the contested resource (the Queue)
public abstract class Person implements Runnable {

	private static short idInc = 0;

	public static void resetId() {
		idInc = 0;
	}

	protected final Config config;
	protected final Queue queue;
	protected final short id;

	public Person(Queue queue, Config config) {
		this.queue = queue;
		this.config = config;
		id = idInc++;
	}

	// this is the main data that will be sent to the visualization. Also, using
	// shorts because they are smaller -> sending less data
	public abstract short[] snapshot();

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + Program.rightPad(id + "", 7) + " ";
	}

}
