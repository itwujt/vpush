package com.fanyi.server.netty.handler;

import com.fanyi.core.util.JsonUtil;
import com.fanyi.server.domain.PacketDO;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * 解析数据包
 * @author Best Wu
 * @date 2020/4/10 21:52
 */
@Slf4j
@ChannelHandler.Sharable
public class WebsocketFrameDecodeHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String text = textWebSocketFrame.text();
        // 解析异常会 关闭通道
        PacketDO packetDO = JsonUtil.parseObject(text, PacketDO.class);
        // 将数据包传递给下一个 handler
        ctx.fireChannelRead(packetDO);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("【数据包解析异常】", cause);
        ctx.channel().close();
        log.info("【通道id：{} 关闭】", ctx.channel().id());
    }
}
