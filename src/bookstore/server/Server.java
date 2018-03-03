package bookstore.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import bookstore.people.Cashier;
import bookstore.people.Visitor;

public class Server implements Runnable, AutoCloseable {

	private final ServerSocket server;
	private final Socket client;
	private final Buffer buffer;

	private List<Visitor> visitors;
	private List<Cashier> cashiers;

	public Server() throws IOException {
		server = new ServerSocket(6790);
		System.out.println(server);
		client = server.accept();
		System.out.println(client);
		buffer = new Buffer(1024, client.getOutputStream());

		visitors = null;
		cashiers = null;
	}

	@Override
	public void run() {
	}

	public void setLists(List<Visitor> visitors, List<Cashier> cashiers) {
		this.visitors = visitors;
		this.cashiers = cashiers;
	}

	@Override
	public void close() throws Exception {
		client.close();
		server.close();
	}

}
