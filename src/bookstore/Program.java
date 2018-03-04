package bookstore;

import java.io.IOException;
import java.util.Scanner;

import bookstore.server.Server;

public class Program {

	public static void main(String[] args) throws IOException, Exception {
		try (Server server = new Server(); Scanner scan = new Scanner(System.in)) {
			String line = "y";
			Config config = null;
			while (isYes(line)) {
				if (config != null) {
					server.reset();

					System.out.println("??? Use Same Parameters? (y/n)");
					config = isYes(scan.nextLine()) ? config.clearState() : null;
				}
				if (config == null) {
					config = Config.create(scan);
				}
				(new Store(config)).open(server);
				System.out.println("??? Go Again? (y/n)");
				line = scan.nextLine();
			}
		}
	}

	private static boolean isYes(String line) {
		if (line == null) {
			return false;
		}
		if (line.equals("")) {
			return false;
		}
		line = line.toLowerCase();
		if (line.charAt(0) == 'y') {
			return true;
		}
		return false;
	}

	public static String rightPad(String str, int len) {
		str = "                   " + str;
		return str.substring(str.length() - len, str.length());
	}

}
