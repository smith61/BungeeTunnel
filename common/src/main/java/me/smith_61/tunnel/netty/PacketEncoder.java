package me.smith_61.tunnel.netty;

import me.smith_61.tunnel.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

public class PacketEncoder extends MessageToMessageEncoder<Packet> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet msg, MessageBuf<Object> out) throws Exception {
		byte[] packet = Packet.writePacket(msg);
		
		ByteBuf buf = ctx.alloc().buffer(packet.length);
		
		buf.writeBytes(packet);
		
		out.add(buf);
	}

}
