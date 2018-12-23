package com.zzb.handlers;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("TextWebSocketFrameHandler:handlerAdded");
        //System.out.println(ctx.channel().toString());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("TextWebSocketFrameHandler:channelRead0");
        String message = msg.content().toString(io.netty.util.CharsetUtil.UTF_8);
        System.out.println(message);
        ctx.writeAndFlush(new TextWebSocketFrame("[server] receive message [" + message + "] successfully"));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("TextWebSocketFrameHandler:handlerRemoved");
        //System.out.println(ctx.channel().toString());
    }
}