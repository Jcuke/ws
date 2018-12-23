package com.zzb.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri = "/ws";
    private WebSocketServerHandshaker handshaker;

    //public HttpRequestHandler(String wsUri) {
    //    this.wsUri = wsUri;
    //}


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HttpRequestHandler:handlerAdded");
        //System.out.println(ctx.channel().toString());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HttpRequestHandler:handlerRemoved");
        //System.out.println(ctx.channel().toString());
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println("HttpRequestHandler:channelRead0");
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            if (wsUri.equalsIgnoreCase(request.getUri())) {
                //ctx.fireChannelRead(request.retain());

                //if(HttpHeaders.is100ContinueExpected(request)){
                //    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
                //    ctx.writeAndFlush(response);
                //}

                //sendHttpResponse(ctx, request,
                //        new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
                //return;

                //if(request.getMethod().equals("GET")){
                //
                //} else {
                    ctx.fireChannelRead(request.retain());///////////////////////////
                //}
                //WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                //        "ws://"+request.headers().get(HttpHeaders.Names.HOST)+wsUri, null, false);
                //handshaker = wsFactory.newHandshaker(request);
                //if (handshaker == null) {
                //    WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
                //} else {
                //    handshaker.handshake(ctx.channel(), request);
                //}


            }
        } else if (msg instanceof TextWebSocketFrame) {
            //TextWebSocketFrame frame = (TextWebSocketFrame) msg;
            //ctx.writeAndFlush(new TextWebSocketFrame(frame.text()));
        }
    }


    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
// 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
// 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpHeaders.isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
}