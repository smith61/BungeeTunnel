package me.smith_61.tunnel.netty;

import java.nio.charset.Charset;

import me.smith_61.tunnel.Server;
import me.smith_61.tunnel.ServerManager;
import me.smith_61.tunnel.exceptions.InvalidPacketException;
import me.smith_61.tunnel.packet.Packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;

import io.netty.channel.ChannelHandlerContext;

import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This decoder replaced the PacketDecoder once a channel
 * 	has been connected and a tunnel has been initialized
 */
public class TunneledPacketDecoder extends MessageToMessageDecoder<ByteBuf> {

	private final ServerManager sManager;
	private final ChannelManager cManager;
	
	public TunneledPacketDecoder(ServerManager sManager, ChannelManager cManager) {
		this.sManager = sManager;
		this.cManager = cManager;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, MessageBuf<Object> out) throws Exception {
		int length = msg.readInt();
		String destination = msg.toString(4, length, Charset.forName("UTF-8"));
		
		Server server = this.sManager.getServer(destination);
		if(server != null) {
			if(server == this.sManager.getThisServer()) {
				//This server is the destination of this packet. Decode packet and pass packet
				// to next handler.
				byte[] buffer = new byte[msg.readableBytes()];
				msg.readBytes(buffer);
				
				try {
					out.add(Packet.readPacket(buffer));
				}
				catch(InvalidPacketException ipe) {
					ipe.printStackTrace();
					System.err.println("Discarding packet.");
				}
			}
			else {
				//If this is not the destination lookup the channel
				// that the server is tunneled over and write the
				// packet to it.
				//This saves us time as only the destination needs
				// to be decoded.
				this.cManager.getChannel(server).write(msg.readerIndex(0));
				
			}
		}
		else {
			System.err.println(destination + " not found. Discarding packet.");
		}
	}

}
