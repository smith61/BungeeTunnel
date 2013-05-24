package me.smith_61.tunnel.netty;

import com.google.common.base.Preconditions;

import me.smith_61.tunnel.packet.Packet;
import me.smith_61.tunnel.packet.PacketHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

public class PacketDelegate extends ChannelInboundMessageHandlerAdapter<Packet> {

	private PacketHandler handler = null;
	
	public void setHandler(PacketHandler handler) {
		Preconditions.checkNotNull(handler, "Handler can not be null");
		
		this.handler = handler;
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, Packet msg) throws Exception {
		if(this.handler != null) {
			msg.handle(this.handler);
		}
	}

}
