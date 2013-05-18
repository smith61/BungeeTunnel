package me.smith_61.tunnel.exceptions;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This exception is thrown when a TunnelDataListener attempts
 * 	to be registered on a taken channel.
 * 
 */
public class ChannelRegisteredException extends RuntimeException {

	private static final long serialVersionUID = 182712771762822999L;
	
	private String channel;
	
	public ChannelRegisteredException(String channel) {
		super("Channel is already registered: " + channel);
		
		this.channel = channel;
	}
	
	public String getChannel() {
		return this.channel;
	}
}
