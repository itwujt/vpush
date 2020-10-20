package com.fanyi.server.netty.handler;

import com.fanyi.core.exception.FrameworkException;
import com.fanyi.core.util.SpringContextUtil;
import com.fanyi.server.constant.ClientType;
import com.fanyi.server.properties.WebsocketProperties;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.net.URLDecoder;

import static com.fanyi.server.constant.Cons.*;

/**
 * @author Best Wu
 * @date 2020/4/9 20:40
 */
@Slf4j
@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private WebsocketProperties websocketProperties = SpringContextUtil.getBean(WebsocketProperties.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        // 获取请求 url
        String uri = request.uri();
        Assert.notNull(uri, "uri must not be null");
        Assert.hasLength(uri, "uri must not be empty");
        if (uri.contains(websocketProperties.getWebsocketPath())) {
            // url 拆分 获取 参数 以问号拆分
            String[] temp = uri.split("\\u003F");
            if (temp.length == WS_URL_SPILT_NUM) {
                // 传参 如 ?clientType=2&userId=123&telephone=185...&token=asdsalkdjla
                String paramCollection = URLDecoder.decode(temp[1], "UTF-8");
                String[] keyValueParams = paramCollection.split("&");
                if (keyValueParams.length == WS_URL_PARAM_NUM) {
                    String clientType = keyValueParams[0].split("=")[1];
                    ClientType type = ClientType.match(Integer.parseInt(clientType));
                    switch (type) {
                        case WEB:
                            clientType = "WEB";
                            break;
                        case APP:
                            clientType = "APP";
                            break;
                        default:
                            break;
                    }
                    String userId = keyValueParams[1].split("=")[1];
                    String telephone = keyValueParams[2].split("=")[1];
                    String token = keyValueParams[3].split("=")[1];
                    ctx.channel().attr(CLIENT_TYPE).set(clientType);
                    ctx.channel().attr(USER_ID).set(userId);
                    ctx.channel().attr(TELEPHONE).set(telephone);
                    ctx.channel().attr(TOKEN).set(token);
                    request.setUri(websocketProperties.getWebsocketPath());
                    // 传递给后一个 handler 升级握手
                    ctx.fireChannelRead(request.retain());
                    return;
                }
                throw FrameworkException.of(401, "【请携带正确的参数尝试连接！！！】");
            }
            throw FrameworkException.of(401, "【请携带参数尝试连接！！！】");
        }
        throw FrameworkException.of(401, "【不支持的连接！！！】");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("【处理 Http 请求异常】", cause);
        ctx.channel().close();
        log.info("【通道，id：{}关闭，Http 请求发生异常】", ctx.channel().id());
    }
}
