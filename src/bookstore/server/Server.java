package bookstore.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import bookstore.Config;
import bookstore.people.Cashier;
import bookstore.people.Person;
import bookstore.people.Visitor;

public class Server implements Runnable, AutoCloseable {

	private final ServerSocket server;
	private final Socket client;
	private final OutputStream stream;

	private Buffer buffer;

	private List<Visitor> visitors;
	private List<Cashier> cashiers;
	private Config config;

	public Server() throws IOException {
		server = new ServerSocket(6790);
		System.out.println(server);
		client = server.accept();
		System.out.println(client);
		stream = client.getOutputStream();

		visitors = null;
		cashiers = null;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
			while (!config.isDone()) {
				Thread.sleep(1000);
				buffer.write(Code.PEOPLE.value);
				buffer.write(visitors.size());
				buffer.write(cashiers.size());
				for (Visitor visitor : visitors) {
					buffer.write(visitor);
				}
				for (Cashier cashier : cashiers) {
					buffer.write(cashier);
				}
				buffer.send();
			}
		} catch (InterruptedException | IOException ie) {
			System.out.println("Thread Failed: " + this);
			ie.printStackTrace();
		}
	}

	public void setReferences(List<Visitor> visitors, List<Cashier> cashiers, Config config) {
		this.visitors = visitors;
		this.cashiers = cashiers;
		this.config = config;
		buffer = new Buffer(1 + (4 * ((visitors.size() * 4) + (cashiers.size() * 3) + 2)), stream);
	}

	@Override
	public void close() throws Exception {
		client.close();
		server.close();
	}

}
