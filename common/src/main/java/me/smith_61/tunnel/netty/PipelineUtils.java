package me.smith_61.tunnel.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class PipelineUtils {

	public static final ChannelInitializer<Channel> BASE_INITIALIZER = new ChannelInitializer<Channel>() {

		@Override
		protected void initChannel(Channel ch) throws Exception {
			ChannelPipeline pipeline = ch.pipeline();
			
			
			//Length of length field in bytes
			int lengthFieldSize = 4;
			
			//Encoders
			pipeline.addLast("LengthEncoder", new LengthFieldPrepender(lengthFieldSize));
			pipeline.addLast("PacketEncoder", new PacketEncoder());
			
			//Decoders
			pipeline.addLast("LengthDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, lengthFieldSize, 0, lengthFieldSize));
			pipeline.addLast("LengthDecoder", new PacketDecoder());
			
			//Delegates
			pipeline.addLast("PacketDelegate", new PacketDelegate());
		}
		
	};
}
