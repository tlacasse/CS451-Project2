package bookstore.people;

import bookstore.Config;
import bookstore.Program;
import bookstore.Queue;

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

	public abstract short[] snapshot();

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + Program.rightPad(id + "", 7) + " ";
	}

}
