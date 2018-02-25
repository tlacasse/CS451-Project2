package bookstore;

import java.util.ArrayList;
import java.util.Scanner;

import bookstore.people.Cashier;
import bookstore.people.Visitor;

public class Program {

	public static void main(String[] args) {
		int visitorCount, cashierCount;
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

		Cashier[] cashiers = new Cashier[cashierCount];
		for (int i = 0; i < cashierCount; i++) {
			cashiers[i] = new Cashier(cashiers);
		}

		ArrayList<Thread> threads = new ArrayList<>();

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

	public static String rightPad(String str, int len) {
		str = "                   " + str;
		return str.substring(str.length() - len, str.length());
	}

}
