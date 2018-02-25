package bookstore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) throws IOException {
		try (ServerSocket server = new ServerSocket(6799)) {
			System.out.println(server);
			try (Socket client = server.accept()) {
				System.out.println(client);
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
						PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
						Scanner scan = new Scanner(System.in)) {
					String line;
					while (!(line = scan.nextLine()).equals("")) {
						writer.println(line);
					}
				}
				client.close();
				server.close();
			}
		}
	}

}
