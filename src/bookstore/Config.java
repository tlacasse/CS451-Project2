package bookstore;

import java.util.Random;

public enum Config {

	ITEMS(1, 6), VISITORS(0, 50), CASHIERS(1, 10), SHOPPING(50, 75), CHECKOUT(10, 20);

	public static final long TIME_UNIT = 20;
	public final int min, max;

	private final Random random;

	private Config(int min, int max) {
		this.min = min;
		this.max = max;
		random = new Random();
	}

	public int random() {
		return min + random.nextInt(max - min + 1);
	}

}
