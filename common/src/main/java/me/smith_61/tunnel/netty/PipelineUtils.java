package me.smith_61.tunnel.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class PipelineUtils {

	public static final ChannelInitializer<Channel> BASE_INITIALIZER = new ChannelInitializer<Channel>() {

		@Override
		protected void initChannel(Channel ch) throws Exception {
			ChannelPipeline pipeline = ch.pipeline();
			
			//Encoders
			pipeline.addLast("PacketEncoder", new PacketEncoder());
			
			//Decoders
			pipeline.addLast("PacketDecoder", new PacketEncoder());
		}
		
	};
}
