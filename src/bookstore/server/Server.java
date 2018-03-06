package bookstore.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import bookstore.Config;
import bookstore.Param;
import bookstore.people.Cashier;
import bookstore.people.Visitor;

public class Server implements Runnable, AutoCloseable {

	private final ServerSocket server;

	private Socket client;
	private OutputStream stream;
	private Buffer bufferPeople, bufferSize;

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
			while (!config.isDone()) {
				Thread.sleep(config.get(Param.TIME));
				bufferPeople.write(Code.PEOPLE.value);
				bufferPeople.write(visitors.size());
				bufferPeople.write(cashiers.size());
				for (Visitor visitor : visitors) {
					bufferPeople.write(visitor);
				}
				for (Cashier cashier : cashiers) {
					bufferPeople.write(cashier);
				}
				bufferPeople.send();
			}
		} catch (InterruptedException | IOException ie) {
			System.out.println("Thread Failed: " + this);
			ie.printStackTrace();
		}
	}

	public void reset() throws IOException {
		bufferSize.write(Code.RESTART.value);
		bufferSize.send();
		client = server.accept();
		System.out.println(client);
		stream = client.getOutputStream();
	}

	public void sendSizes() throws IOException {
		bufferSize.write(Code.ITEMS.value);
		bufferSize.write(config.maxItemCount());
		bufferSize.write(visitors.size());
		bufferSize.send();
	}

	public void setReferences(List<Visitor> visitors, List<Cashier> cashiers, Config config) {
		this.visitors = visitors;
		this.cashiers = cashiers;
		this.config = config;

		// size = typeByte + maxItems + visitors.size
		bufferSize = new Buffer(1 + 4 + 4, stream);

		// size = typeByte + visitors.size + cashiers.size + allPeople
		int numNumbers = (visitors.size() * Visitor.SNAPSHOT_SIZE) + (cashiers.size() * Cashier.SNAPSHOT_SIZE);
		bufferPeople = new Buffer(1 + 4 + 4 + (2 * numNumbers), stream);
	}

	@Override
	public void close() throws Exception {
		client.close();
		server.close();
	}

}
