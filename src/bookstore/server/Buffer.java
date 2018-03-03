package bookstore.server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import bookstore.people.Person;

public class Buffer {

	private final ByteBuffer buffer;
	private final OutputStream stream;

	public Buffer(int size, OutputStream stream) {
		this.stream = stream;
		buffer = ByteBuffer.allocate(size);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
	}

	public void send() throws IOException {
		stream.write(buffer.array());
		buffer.clear(); // does not erase data
	}

	public void write(Person person) {
		for (int i : person.snapshot()) {
			buffer.putInt(i);
		}
	}

	public void write(byte b) {
		buffer.put(b);
	}

	public void write(int i) {
		buffer.putInt(i);
	}

}
