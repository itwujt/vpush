package com.fanyi.server.netty.handler;

import com.fanyi.server.constant.MessageType;
import com.fanyi.server.domain.PacketDO;
import com.fanyi.server.repository.ChannelRepository;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

import static com.fanyi.server.constant.Cons.*;

/**
 * 处理 websocket 推送消息的逻辑
 * @author Best Wu
 * @date 2020/4/10 22:01
 */
@Slf4j
public class WebsocketServerHandler extends SimpleChannelInboundHandler<PacketDO> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("【通道id：{}，成功连接】", ctx.channel().id());
        String clientType = ctx.channel().attr(CLIENT_TYPE).get();
        String userId = ctx.channel().attr(USER_ID).get();
        String telephone = ctx.channel().attr(TELEPHONE).get();
        ChannelRepository.insert(clientType + "_" + userId + "_" + telephone, ctx.channel());
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketDO packetDO) throws Exception {
        // TODO 处理消息类型 以及 入库等操作
        Integer messageType = packetDO.getMessageType();
        MessageType match = MessageType.match(messageType);
        switch (match) {
            case CONNECT:
                log.info("【连接类型消息，通道 id：{}】", ctx.channel().id());
                ctx.channel().writeAndFlush(new TextWebSocketFrame("收到"));
                break;
            case HEARTBEAT:
                log.info("【心跳类型消息，通道 id：{}】", ctx.channel().id());
                break;
            case MESSAGE:
                log.info("【对话类型消息，通道 id：{}】", ctx.channel().id());
                break;
            case BROADCAST:
                log.info("【广播类型消息，通道 id：{}】", ctx.channel().id());
                break;
            default:
                log.info("【不支持的类型消息，通道 id：{}，通道将关闭】", ctx.channel().id());
                break;
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("【通道id：{}，断开连接】", ctx.channel().id());
        String clientType = ctx.channel().attr(CLIENT_TYPE).get();
        String userId = ctx.channel().attr(USER_ID).get();
        String telephone = ctx.channel().attr(TELEPHONE).get();
        ChannelRepository.remove(clientType + "_" + userId + "_" + telephone);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("【推送消息产生异常，通道id：{}】", ctx.channel().id(), cause);
        ctx.channel().close();
        log.info("【通道id：{} 关闭】", ctx.channel().id());
    }


    private void dealWithMessage(ChannelHandlerContext ctx, PacketDO packetDO) {
        // TODO 这块好好想想
        String sendUserId = packetDO.getSendUserId();
        String telephone = packetDO.getTelephone();
        List<String> receiveUserIds = packetDO.getReceiveUserIds();
        List<String> offlineUsers = new LinkedList<>();

    }
}
