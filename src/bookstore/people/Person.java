package bookstore.people;

import bookstore.Queue;

public abstract class Person implements Runnable {

	private static int idInc = 0;

	protected final Queue<Visitor> queue;
	protected final int id;

	public Person(Queue<Visitor> queue) {
		this.queue = queue;
		id = idInc++;
	}

}
