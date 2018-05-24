package com.zml.app.user.util;

import com.zml.app.user.netty.NettyHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpVersion;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

/**
 * @author pjk
 * @date 2018-04-23 15:59
 * @since
 */
public class ResponseUtil {

    private static final String JSON_TYPE = "application/json";

    public static void response(ChannelHandlerContext ctx, String content) {
        ByteBuf buffer = Unpooled.wrappedBuffer(content.getBytes());
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                ErrorCode.errorToHttpResponseStatus(ErrorCode.EC_OK),
                buffer);
        HttpHeaders.setContentLength(response, buffer.readableBytes());
        response.headers().set(CONTENT_TYPE, JSON_TYPE);
        NettyHelper.sendResponse(ctx, response);
    }

    //    private void response(ChannelHandlerContext ctx, String content) {
//        ByteBuf buffer = Unpooled.wrappedBuffer(content.getBytes());
//        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
//                ErrorCode.errorToHttpResponseStatus(ErrorCode.EC_OK),
//                buffer);
//        HttpHeaders.setContentLength(response, buffer.readableBytes());
//        response.headers().set(CONTENT_TYPE, JSON_TYPE);
//        NettyHelper.sendResponse(ctx, response);
//    }
}
