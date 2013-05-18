package me.smith_61.tunnel.exceptions;

public class InvalidPacketException extends Exception {

	private static final long serialVersionUID = 1288538668273776685L;

	public InvalidPacketException(int id) {
		super("Invalid packet id: " + id);
	}
	
	public InvalidPacketException(Class<?> cl) {
		super("Class: " + cl.getName() + " is missing no arg constructor.");
	}
	
	public InvalidPacketException(String s, Throwable t) {
		super(s, t);
	}
}
