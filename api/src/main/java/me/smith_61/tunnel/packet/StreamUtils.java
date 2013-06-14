package me.smith_61.tunnel.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This class provides many utilities for
 * 	reading and writing common data types
 * 	to a stream
 */
public class StreamUtils {
	
	/**
	 * Reads a byte array from the given stream.
	 *
	 * @param input The stream to read from
	 * 
	 * @return The read in byte array
	 */
	public byte[] readByteArray(DataInput input) throws IOException {
		byte[] data = new byte[StreamUtils.readLength(input)];
		input.readFully(data);
		
		return data;
	}
	
	/**
	 * Writes a byte array to the given stream
	 * 
	 * @param output The stream to write to
	 * @param data The data to write
	 */
	public void writeByteArray(DataOutput output, byte[] data) throws IOException {
		output.writeInt(data.length);
		output.write(data);
	}
	
	/**
	 * Reads an int array from the given stream
	 * 
	 * @param input
	 * 
	 * @return The read in int array
	 */
	public int[] readIntArray(DataInput input) throws IOException {
		int[] data = new int[StreamUtils.readLength(input)];
		for(int i=0; i<data.length; i++) {
			data[i] = input.readInt();
		}
		
		return data;
	}
	
	/**
	 * Writes an int array to the given stream
	 * 
	 * @param output The stream to write to
	 * @param data The data to write
	 */
	public void writeIntArray(DataOutput output, int[] data) throws IOException {
		output.writeInt(data.length);
		for(int i=0; i<data.length; i++) {
			output.writeInt(data[i]);
		}
	}
	
	
	private static int readLength(DataInput input) throws IOException {
		int length = input.readInt();
		if(length < 0) {
			throw new IOException("Negative array length");
		}
		
		return length;
	}
}
