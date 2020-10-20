package com.fanyi.server.repository;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Best Wu
 * @date 2020/4/10 21:26
 */
public class ChannelRepository {

    private static Map<String, Channel> channelGroup = new ConcurrentHashMap<>(256);

    public static Channel get(String key) {
        return channelGroup.get(key);
    }

    public static void insert(String key, Channel channel) {
        channelGroup.put(key, channel);
    }

    public static void remove(String key) {
        channelGroup.keySet().removeIf(k -> k.equals(key));
    }
}
