package com.fanyi.server.netty;

import com.fanyi.core.util.SpringContextUtil;
import com.fanyi.server.netty.handler.*;
import com.fanyi.server.properties.WebsocketProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Best Wu
 * @date 2020/4/8 22:11
 */
@Slf4j
public class WebsocketServer implements NettyServer {
    /**
     * 获取 websocketProperties
     */
    private WebsocketProperties websocketProperties = SpringContextUtil.getBean(WebsocketProperties.class);

    /**
     * server
     */
    private ServerBootstrap serverBootstrap;
    /**
     * 用来处理连接
     */
    private EventLoopGroup boss;
    /**
     * 处理已连接的 io
     */
    private EventLoopGroup worker;
    /**
     * 回调
     */
    private ChannelFuture future;

    @Override
    public void open() throws Exception{
        this.serverBootstrap = new ServerBootstrap();
        // 日后考虑 增加线程数的问题来处理 大量的连接 还要考虑服务器 cpu 问题
        // 处理 连接后的 io 的线程数 也适当控制
        this.boss = new NioEventLoopGroup();
        this.worker = new NioEventLoopGroup();
        this.serverBootstrap
                .group(this.boss, this.worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        initHandler(nioSocketChannel.pipeline());
                    }
                });
        initOptions(this.serverBootstrap);
        this.future = this.serverBootstrap.bind(websocketProperties.getWebsocketPort()).sync();
        this.future.channel().closeFuture().sync();
    }


    @Override
    public void close() {
        log.info("【连接关闭了】");
        this.boss.shutdownGracefully();
        this.worker.shutdownGracefully();
    }

    private void initHandler(ChannelPipeline pipeline) {
        // 服务端 http 编解码
        pipeline.addLast(new HttpServerCodec());
        // 对大数据的写 支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 扩大缓冲区
        HttpObjectAggregator httpObjectAggregator = new HttpObjectAggregator(65536);
        httpObjectAggregator.setMaxCumulationBufferComponents(8 * 1024);
        pipeline.addLast(httpObjectAggregator);
        pipeline.addLast(new HttpRequestHandler());
        /************************** 以上处理 HTTP **************************************************/

        pipeline.addLast(new IdleStateHandler(20, 40, 60));

        pipeline.addLast(new HeartBeatHandler());

        /************************** 以上处理 心跳 **************************************************/


        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(websocketProperties.getWebsocketPath(), null, true));
        pipeline.addLast(new HandshakeCompleteCheckTokenHandler());


        /************************** 以上处理 ws 协议 **************************************************/

        pipeline.addLast(new WebsocketFrameDecodeHandler());
        pipeline.addLast(new WebsocketServerHandler());
        /************************** 以上 自定义 ws handler**************************************************/
    }

    private void initOptions(ServerBootstrap serverBootstrap) {
        // 队列数 用于临时存放已完成三次握手的请求的队列的最大长度
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        // 使用对象池 重用缓冲区
        serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        // 使用对象池 重用缓冲区
        serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
    }

}
