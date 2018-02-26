package bookstore;

import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		final int visitorCount, cashierCount;
		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("Number of Visitors:");
			visitorCount = Integer.parseInt(scan.nextLine());
			if (visitorCount < Config.VISITORS.min || visitorCount > Config.VISITORS.max) {
				throw new IllegalArgumentException(
						visitorCount + " out of range [" + Config.VISITORS.min + "," + Config.VISITORS.max + "]");
			}
			System.out.println("Number of Cashiers:");
			cashierCount = Integer.parseInt(scan.nextLine());
			if (cashierCount < Config.CASHIERS.min || cashierCount > Config.CASHIERS.max) {
				throw new IllegalArgumentException(
						cashierCount + " out of range [" + Config.CASHIERS.min + "," + Config.CASHIERS.max + "]");
			}
		}

		(new Store(cashierCount, visitorCount)).start();

	}

	public static String rightPad(String str, int len) {
		str = "                   " + str;
		return str.substring(str.length() - len, str.length());
	}

}
