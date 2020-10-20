package com.fanyi.server.netty;

/**
 * @author Best Wu
 * @date 2020/4/8 22:05
 */
public interface NettyServer {
    /**
     * 开启连接
     */
    void open() throws Exception;

    /**
     * 关闭连接
     */
    void close();
}
