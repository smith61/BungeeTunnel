package me.smith_61.tunnel.netty;

import me.smith_61.tunnel.exceptions.InvalidPacketException;
import me.smith_61.tunnel.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;

public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, MessageBuf<Object> out) throws Exception {
		byte[] buffer = new byte[in.readableBytes()];
		
		in.readBytes(buffer);
		
		try {
			out.add(Packet.readPacket(buffer));
		}
		catch(InvalidPacketException ipe) {
			ipe.printStackTrace();
		}
	}

}
