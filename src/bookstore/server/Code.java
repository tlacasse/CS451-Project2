package bookstore.server;

public enum Code {

	VISITOR(1), CASHIER(2);

	public final byte value;

	private Code(int value) {
		this.value = (byte) value;
	}

}
