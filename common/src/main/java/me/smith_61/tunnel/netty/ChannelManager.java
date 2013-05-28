package me.smith_61.tunnel.netty;

import io.netty.channel.Channel;
import me.smith_61.tunnel.Server;

public interface ChannelManager {
	
	public Channel getChannel(Server server);
}
