package bookstore.people;

import bookstore.Config;
import bookstore.Program;
import bookstore.Queue;

public abstract class Person implements Runnable {

	private static int idInc = 0;

	public static void resetId() {
		idInc = 0;
	}

	protected final Config config;
	protected final Queue<Visitor> queue;
	protected final int id;

	public Person(Queue<Visitor> queue, Config config) {
		this.queue = queue;
		this.config = config;
		id = idInc++;
	}

	public abstract int[] snapshot();

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + Program.rightPad(id + "", 7) + " ";
	}

}
