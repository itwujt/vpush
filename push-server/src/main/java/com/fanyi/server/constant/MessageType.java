package com.fanyi.server.constant;

import com.fanyi.core.exception.FrameworkException;

/**
 * 消息类型
 * @author Best Wu
 * @date 2020/4/10 21:34
 */
public enum MessageType {
    /**
     * 连接消息类型
     */
    CONNECT(1 << 1, "连接消息"),
    /**
     * 心跳消息类型
     */
    HEARTBEAT(1 << 2, "心跳消息"),
    /**
     * 对话消息 类型
     */
    MESSAGE(1 << 3, "对话消息"),
    /**
     * 广播消息类型
     */
    BROADCAST(1 << 4, "广播消息");

    /**
     * 类型
     */
    private Integer type;
    /**
     * 描述
     */
    private String desc;

    MessageType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static MessageType match(Integer type) throws FrameworkException{
        switch (type) {
            case 2:
                return CONNECT;
            case 4:
                return HEARTBEAT;
            case 8:
                return MESSAGE;
            case 16:
                return BROADCAST;
            default:
                break;
        }
        throw FrameworkException.of(401, "【不支持的消息类型】");
    }
}
