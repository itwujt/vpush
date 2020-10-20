package com.fanyi.core.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * <br>
 *
 * @author Best Wu
 * @date 2020/4/10 20:06 <br>
 */
@Component
public class RedisServiceImpl implements CeService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ValueOperations<String, Object> opsForValue() {
        return redisTemplate.opsForValue();
    }

    @Override
    public String get(String key) {
        Object value = opsForValue().get(key);
        if (null != value) {
            return (String) value;
        }
        return null;
    }

    @Override
    public void set(String key, Object value) {
        opsForValue().set(key, value);
    }

    /**
     * 有效期单位时间为分钟
     * @param key key
     * @param value value
     * @param expire 有效期
     */
    @Override
    public void setExpire(String key, Object value, long expire) {
        opsForValue().set(key, value, Duration.ofMinutes(expire));
    }
}
