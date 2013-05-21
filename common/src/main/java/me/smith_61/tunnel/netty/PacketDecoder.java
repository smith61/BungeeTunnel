package me.smith_61.tunnel.netty;

import me.smith_61.tunnel.exceptions.InvalidPacketException;
import me.smith_61.tunnel.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class PacketDecoder extends ReplayingDecoder<Void> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, MessageBuf<Object> out) throws Exception {
		while(true) {
			int startIndex = in.readerIndex();
			
			Packet.ensurePacketLength(in);
			
			int endIndex = in.readerIndex();
			
			byte[] packet = new byte[endIndex - startIndex];
			in.readBytes(packet);
			
			in.readerIndex(endIndex);
			this.checkpoint();
			
			try {
				out.add(Packet.readPacket(packet));
			}
			catch(InvalidPacketException ipe) {
				System.out.println(ipe.getMessage());
				System.out.println("Skipping packet.");
			}
		}
	}

}
