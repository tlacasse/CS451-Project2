package bookstore.server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import bookstore.people.Person;

//stores the bytes that should be send to the client
public class Buffer {

	private final ByteBuffer buffer;

	public Buffer(int size) {
		buffer = ByteBuffer.allocate(size);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		// Game Maker reads as little endian, ByteBuffer defaults as big endian
	}

	public void send(OutputStream stream) throws IOException {
		stream.write(buffer.array());
		buffer.clear();
		// does not erase data, only resets writing position
	}

	public void write(Person person) {
		write(person.snapshot());
	}

	public void write(byte b) {
		buffer.put(b);
	}

	public void write(int... is) {
		for (int i = 0; i < is.length; i++) {
			buffer.putInt(is[i]);
		}
	}

	public void write(short... ss) {
		for (int i = 0; i < ss.length; i++) {
			buffer.putShort(ss[i]);
		}
	}

}
