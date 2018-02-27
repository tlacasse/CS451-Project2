package bookstore;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Config {

	private static enum Key {
		VISITORS("Visitors", 25), CASHIERS("Cashiers", 5), TIME("Time Unit, in ms", 20), MAXITEMS("Max Items",
				6), MINSHOPPING("Min Shopping Time, in time units", 50), MAXSHOPPING("Max Shopping Time, in time units",
						75), MINCHECKOUT("Min Checkout Time, in time units",
								10), MAXCHECKOUT("Max Checkout Time, in time units", 20);

		public final String desc;
		public final int base;

		private Key(String desc, int base) {
			this.desc = desc;
			this.base = base;
		}
	}

	private final HashMap<Key, Integer> values;
	private final Random random;
	private boolean done;

	private Config() {
		values = new HashMap<>();
		random = new Random();
		done = false;
	}

	public static Config create() {
		final Config config = new Config();
		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("=== Change Parameters, leave blank for default ===");
			for (Key key : Key.values()) {
				System.out.println(key.desc + " (" + key.base + "):");
				final String line;
				final int value = (line = scan.nextLine()).equals("") ? key.base : Integer.parseInt(line);
				if (value < 1) {
					throw new IllegalArgumentException(key.desc + " must be greater than 0.");
				}
				config.values.put(key, value);
			}
		}
		return config;
	}

	public int get(Param param) {
		if (param == Param.VISITORS) {
			return values.get(Key.VISITORS);
		}
		if (param == Param.CASHIERS) {
			return values.get(Key.CASHIERS);
		}
		if (param == Param.TIME) {
			return values.get(Key.TIME);
		}
		if (param == Param.ITEMS) {
			return randomRange(1, values.get(Key.MAXITEMS));
		}
		if (param == Param.SHOPPING) {
			return values.get(Key.TIME) * randomRange(values.get(Key.MINSHOPPING), values.get(Key.MAXSHOPPING));
		}
		if (param == Param.CHECKOUT) {
			return values.get(Key.TIME) * randomRange(values.get(Key.MINCHECKOUT), values.get(Key.MAXCHECKOUT));
		}
		return -1;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone() {
		done = true;
	}

	private int randomRange(int min, int max) {
		return min + random.nextInt(max - min + 1);
	}

}
