package bookstore.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import bookstore.Config;
import bookstore.Param;
import bookstore.Queue;
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
	private Queue queue;

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
				for (Visitor visitor : visitors) {
					bufferPeople.write(visitor);
				}
				for (Cashier cashier : cashiers) {
					bufferPeople.write(cashier);
				}
				final short[] ids = queue.idList();
				bufferPeople.write(ids.length);
				bufferPeople.write(ids);
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
		bufferSize.write(Code.SIZE.value);
		bufferSize.write(config.get(Param.MAXITEMS), visitors.size(), cashiers.size(), config.get(Param.QUEUEBOUND));
		bufferSize.send();
	}

	public void setReferences(List<Visitor> visitors, List<Cashier> cashiers, Config config, Queue queue) {
		this.visitors = visitors;
		this.cashiers = cashiers;
		this.config = config;
		this.queue = queue;

		// size = typeByte + maxItems + visitors.size + cashiers.size +
		// queue.size
		bufferSize = new Buffer(1 + (4 * 4), stream);

		// size = typeByte + allPeople
		// + currentQueueSize + queue.size (filled with id's)
		int numSnapshot = (visitors.size() * Visitor.SNAPSHOT_SIZE) + (cashiers.size() * Cashier.SNAPSHOT_SIZE);
		bufferPeople = new Buffer(1 + 4 + (2 * (numSnapshot + config.get(Param.QUEUEBOUND))), stream);
		System.out.println("Buffer Size: " + (1 + 4 + (2 * (numSnapshot + config.get(Param.QUEUEBOUND)))));
	}

	@Override
	public void close() throws Exception {
		client.close();
		server.close();
	}

}
