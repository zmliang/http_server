package com.zml.app.user.netty;


import com.zml.app.user.socket.TextWebSocketFrameHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Autowired
    private ServerHandler serverHandler;

    @Autowired
    private TextWebSocketFrameHandler textWebSocketFrameHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());

        pipeline.addLast("aggregator", new HttpObjectAggregator(10 * 1024));
        // compression.连接失败连接失败
        pipeline.addLast("deflater", new HttpContentCompressor());
        pipeline.addLast("handler", serverHandler);
//        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
//        pipeline.addLast(textWebSocketFrameHandler);
    }


}
