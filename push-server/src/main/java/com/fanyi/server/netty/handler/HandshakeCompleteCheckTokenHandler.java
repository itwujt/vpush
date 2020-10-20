package com.fanyi.server.netty.handler;

import com.fanyi.core.cache.CeService;
import com.fanyi.core.cache.RedisServiceImpl;
import com.fanyi.core.exception.FrameworkException;
import com.fanyi.core.factory.TokenFactoryBuilder;
import com.fanyi.core.repository.TokenRepository;
import com.fanyi.core.util.SpringContextUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;

import static com.fanyi.server.constant.Cons.*;

/**
 * @author Best Wu
 * @date 2020/4/10 20:34 <br>
 */
@Slf4j
@ChannelHandler.Sharable
public class HandshakeCompleteCheckTokenHandler extends ChannelInboundHandlerAdapter {

    private CeService cacheService = SpringContextUtil.getBean(RedisServiceImpl.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 握手成功后 对 token 进行校验
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            // 移除了 效果更佳
            ctx.channel().pipeline().remove(HttpRequestHandler.class);
            String userId = ctx.channel().attr(USER_ID).get();
            String telephone = ctx.channel().attr(TELEPHONE).get();
            String token = ctx.channel().attr(TOKEN).get();
            // 根据 userId 和 电话 在redis中 获取秘钥
            String secretKey = cacheService.get(PUSH_USER_SECRET_KEY_PREFIX + userId + "_" + telephone);
            if (null != secretKey) {
                TokenRepository tokenRepository = TokenFactoryBuilder.getTokenFactory().getTokenRepository();
                // 解析中出现异常 直接 关闭连接
                tokenRepository.parse(token, secretKey);
                return;
            }
            throw FrameworkException.of(401, "【用户身份以过期，请重新获取令牌】");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("【token 校验失败，验证未通过】", cause);
        ctx.channel().close();
        log.info("【通道正在关闭，id：{}】", ctx.channel().id());
    }
}
