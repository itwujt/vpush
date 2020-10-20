package com.fanyi.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Best Wu
 * @date 2020/4/9 20:16
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "netty.server")
public class WebsocketProperties {
    /**
     * ws 长连接 端口号 默认 19999
     */
    private Integer websocketPort = 19999;
    /**
     * ws 长连接 路径 默认 /ws
     */
    private String websocketPath = "/ws";
}
