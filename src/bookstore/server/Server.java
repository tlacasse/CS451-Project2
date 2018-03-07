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
	private Buffer buffer;

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
				writeBuffer();
			}
			// make sure visualization is complete
			// at fast speeds seemed like config.isDone(), but the last data was
			// not sent yet
			Thread.sleep(config.get(Param.TIME));
			writeBuffer();
		} catch (InterruptedException | IOException ie) {
			System.out.println("Thread Failed: " + this);
			ie.printStackTrace();
		}
	}

	private void writeBuffer() throws InterruptedException, IOException {
		buffer.write(Code.PEOPLE.value);
		for (Visitor visitor : visitors) {
			buffer.write(visitor);
		}
		final short[] ids = queue.idList();
		buffer.write(ids.length);
		buffer.write(ids);
		for (Cashier cashier : cashiers) {
			buffer.write(cashier);
		}
		buffer.send();
	}

	public void reset() throws IOException {
		buffer.write(Code.RESTART.value);
		buffer.send();
		client.close();
		client = server.accept();
		System.out.println(client);
		stream = client.getOutputStream();
	}

	public void sendSizes() throws IOException {
		buffer.write(Code.SIZE.value);
		buffer.write(config.get(Param.MAXITEMS), visitors.size(), cashiers.size(), config.get(Param.QUEUEBOUND));
		buffer.send();
	}

	public void setReferences(List<Visitor> visitors, List<Cashier> cashiers, Config config, Queue queue) {
		this.visitors = visitors;
		this.cashiers = cashiers;
		this.config = config;
		this.queue = queue;

		// size = typeByte + allPeople
		// + currentQueueSize + queue.size (filled with id's)
		// OR
		// size = typeByte + maxItems + visitors.size + cashiers.size +
		// queue.size
		int size = (visitors.size() * Visitor.SNAPSHOT_SIZE) + (cashiers.size() * Cashier.SNAPSHOT_SIZE);
		size = 1 + 4 + (2 * (size + config.get(Param.QUEUEBOUND)));
		size = Math.max(size, 1 + (4 * 4));
		buffer = new Buffer(size, stream);
		System.out.println("Server Buffer Size: " + size + " bytes.");
	}

	@Override
	public void close() throws Exception {
		client.close();
		server.close();
	}

}
