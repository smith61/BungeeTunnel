package me.smith_61.tunnel.netty;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This encoder replaced the packet encoder once
 * 	a channel has been connected and a tunnel
 * 	has been initialized.
 * 
 * It prepends a header onto a packet that specifies the destination server
 * 	of the packet.
 * 
 * The header format is:
 * 		destinationLength: int
 * 		destination: String(UTF-8)
 */
public class TunneledPacketEncoder extends MessageToMessageEncoder<TunneledPacket> {

	@Override
	protected void encode(ChannelHandlerContext ctx, TunneledPacket msg, MessageBuf<Object> out) throws Exception {
		byte[] destination = msg.getDestination().getBytes(Charset.forName("UTF-8"));
		byte[] packet = msg.getRawPacket();
		
		//Initial size of 1 int + two byte arrays
		//No need to encode packet length as it will be the remaining portion
		// of the frame that is encoded in the next handler
		ByteBuf buf = ctx.alloc().buffer(4 + destination.length + packet.length);
		buf.writeInt(destination.length).writeBytes(destination);
		buf.writeBytes(packet);
		
		out.add(buf);
	}

}
