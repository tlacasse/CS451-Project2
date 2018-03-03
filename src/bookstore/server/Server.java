package bookstore.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable, AutoCloseable {

	private final ServerSocket server;
	private final Socket client;

	public Server() throws IOException {
		server = new ServerSocket(6790);
		System.out.println(server);
		client = server.accept();
		System.out.println(client);
	}

	@Override
	public void run() {

	}

	@Override
	public void close() throws Exception {
		client.close();
		server.close();
	}

}
