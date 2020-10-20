package com.fanyi.server.netty.handler;

import com.fanyi.core.exception.FrameworkException;
import com.fanyi.server.repository.ChannelRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import static com.fanyi.server.constant.Cons.*;

/**
 * <br>
 *
 * @author Best Wu
 * @date 2020/4/10 21:05 <br>
 */
@Slf4j
@ChannelHandler.Sharable
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            String clientType = ctx.channel().attr(CLIENT_TYPE).get();
            String userId = ctx.channel().attr(USER_ID).get();
            String telephone = ctx.channel().attr(TELEPHONE).get();
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            if (IdleState.READER_IDLE.equals(stateEvent.state())) {
                log.info("【通道 id：{} 进入读空闲...】", ctx.channel().id());
            } else if (IdleState.WRITER_IDLE.equals(stateEvent.state())) {
                log.info("【通道 id：{} 进入写空闲...】", ctx.channel().id());
            } else if (IdleState.ALL_IDLE.equals(stateEvent.state())) {
                log.info("【通道 id：{} 进入读写空闲...正在关闭通道】", ctx.channel().id());
                // map 中 删除 通道
                ChannelRepository.remove(clientType + "_" + userId + "_" + telephone);
                throw FrameworkException.of(500, "【通道进入读写空闲，正在断开连接...】");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("【通道 id：{} 发生异常，正在关闭连接】", ctx.channel().id());
        ctx.channel().close();
        log.info("【通道 id：{}已关闭】", ctx.channel().id());
    }
}
