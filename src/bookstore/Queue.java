package bookstore;

import java.util.ArrayList;

public class Queue<E> {

	private ArrayList<E> list;

	public Queue() {
		list = new ArrayList<>();
	}

	public void enqueue(E item) {
		list.add(item);
	}

	public E dequeue() {
		return list.remove(0);
	}

}
