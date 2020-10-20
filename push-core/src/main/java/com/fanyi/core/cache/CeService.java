package com.fanyi.core.cache;

/**
 * @author Best Wu
 * @date 2020/4/10 19:56
 */
public interface CeService {
    /**
     * 根据 key 获取 字符串 针对 字符串操作的
     * @param key key
     * @return java.lang.String
     */
    String get(String key);

    /**
     * 设置 key value ，基本也是字符串操作
     * @param key key
     * @param value value
     */
    void set(String key, Object value);

    /**
     * 设置有效期的 key value 基本也是字符串操作
     * @param key key
     * @param value value
     * @param expire 有效期
     */
    void setExpire(String key, Object value, long expire);
}
