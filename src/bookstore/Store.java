package bookstore;

import java.util.ArrayList;
import java.util.LinkedList;

import bookstore.people.Cashier;
import bookstore.people.Visitor;

public class Store {

	public final int cashierCount;
	public final int visitorCount;

	private final Cashier[] cashiers;
	private final LinkedList<Visitor> queue;

	public Store(int cashierCount, int visitorCount) {
		this.cashierCount = cashierCount;
		this.visitorCount = visitorCount;
		
		queue = new LinkedList<>();
		cashiers = new Cashier[cashierCount];
		for (int i = 0; i < cashierCount; i++) {
			cashiers[i] = new Cashier(cashiers);
		}
	}
	
	public void start(){
		final ArrayList<Thread> threads = new ArrayList<>();

		for (int i = 0; i < visitorCount; i++) {
			threads.add(new Thread(new Visitor(cashiers)));
		}
		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}
	
	

}
