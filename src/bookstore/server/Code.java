package bookstore.server;

public enum Code {

	RESTART(0), PEOPLE(1), ITEMS(2);

	public final byte value;

	private Code(int value) {
		this.value = (byte) value;
	}

}
