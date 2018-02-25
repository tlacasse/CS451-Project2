package bookstore.people;

public abstract class Person implements Runnable {

	private static int idInc = 0;

	protected final Cashier[] cashiers;
	protected final int id;

	public Person(Cashier[] cashiers) {
		this.cashiers = cashiers;
		id = idInc++;
	}

}
