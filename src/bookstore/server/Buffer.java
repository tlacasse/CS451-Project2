package bookstore.server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import bookstore.people.Cashier;
import bookstore.people.Visitor;

public class Buffer {

	private final ByteBuffer buffer;
	private final OutputStream stream;

	public Buffer(int size, OutputStream stream) {
		this.stream = stream;
		buffer = ByteBuffer.allocate(size);
	}

	public void send() throws IOException {
		stream.write(buffer.array());
		buffer.clear(); // does not erase data
	}

	public void write(Visitor visitor) {
		buffer.put(Code.VISITOR.value);
		for (int i : visitor.snapshot()) {
			buffer.putInt(i);
		}
	}

	public void write(Cashier cashier) {
		buffer.put(Code.CASHIER.value);
		for (int i : cashier.snapshot()) {
			buffer.putInt(i);
		}
	}

}
