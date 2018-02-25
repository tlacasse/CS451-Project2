package bookstore;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public void aaa() throws IOException {
		try (ServerSocket server = new ServerSocket(6790)) {
			System.out.println(server);
			try (Socket client = server.accept()) {
				System.out.println(client);
				try (PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
						Scanner scan = new Scanner(System.in)) {
					String line;
					while (!(line = scan.nextLine()).equals("")) {
						writer.println(line);
					}
				}
			}
		}
	}

}
