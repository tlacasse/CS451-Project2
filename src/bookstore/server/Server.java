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

// IPC for visualization. This is the server, sending data to the Game Maker client, using sockets.
// Only one way because the java program doesn't really need any client feedback.
public class Server implements Runnable, AutoCloseable {

	private final ServerSocket server;

	private Socket client;
	private OutputStream stream;
	private Buffer buffer;

	private List<Visitor> visitors;
	private List<Cashier> cashiers;
	private Config config;
	private Queue queue;

	public Server(int port) throws IOException {
		// create sockets and connect
		server = new ServerSocket(port);
		System.out.println(server);
		getClient();
	}

	private void getClient() throws IOException {
		client = server.accept();
		System.out.println(client);
		stream = client.getOutputStream(); // only need outputstream to send
											// data. don't need to recieve data.
	}

	@Override
	public void run() {
		try {
			while (!config.isDone()) {
				// write simulation status at every time interval. not exact,
				// due to OS scheduling, but can't really tell.
				Thread.sleep(config.get(Param.TIME));
				writeBuffer();
			}
			// make sure visualization is complete
			// at fast speeds seemed like config.isDone(), but the last data was
			// not sent yet
			Thread.sleep(config.get(Param.TIME) * 5);
			writeBuffer();
		} catch (InterruptedException | IOException ie) {
			System.out.println("Thread Failed: " + this);
			ie.printStackTrace();
		}
	}

	// Buffer = visitor -> currentQueueSize -> queueIds -> cashiers
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

		buffer.send(stream);
	}

	public void reset() throws IOException {
		buffer.write(Code.RESTART.value);
		buffer.send(stream);

		client.close();
		getClient();
	}

	public void sendSizes() throws IOException {
		buffer.write(Code.SIZE.value);
		buffer.write(config.get(Param.MAXITEMS), visitors.size(), cashiers.size(), config.get(Param.QUEUEBOUND));
		buffer.send(stream);
	}

	// and create the buffer
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
		buffer = new Buffer(size);
		System.out.println("Server Buffer Size: " + size + " bytes.");
	}

	// called implicitly from try with resources
	@Override
	public void close() throws Exception {
		client.close();
		server.close();
	}

}
