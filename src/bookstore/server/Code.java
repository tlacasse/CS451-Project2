package bookstore.server;

//single byte to tell client what each packet is supposed to be
public enum Code {

	RESTART(0), PEOPLE(1), SIZE(2);

	public final byte value;

	private Code(int value) {
		this.value = (byte) value;
	}

}
