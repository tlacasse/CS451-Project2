package bookstore.people;

public class Cashier extends Person {

	private boolean isDone;

	public Cashier(Cashier[] cashiers) {
		super(cashiers);
		isDone = false;
	}

	@Override
	public void run() {

	}

	public void setDone() {
		isDone = true;
	}

}
