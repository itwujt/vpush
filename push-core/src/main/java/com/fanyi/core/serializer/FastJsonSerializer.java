package com.fanyi.core.serializer;

import com.fanyi.core.util.JsonUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * <br>
 *
 * @author Best Wu
 * @date 2020/4/10 20:01 <br>
 */
public class FastJsonSerializer<T> implements RedisSerializer<T> {

    private Class<T> clazz;

    public FastJsonSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (null != t) {
            return JsonUtil.toJsonString(t).getBytes(StandardCharsets.UTF_8);
        }
        return new byte[0];
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (null != bytes && bytes.length > 0) {
            return JsonUtil.parseObject(new String(bytes, StandardCharsets.UTF_8), clazz);
        }
        return null;
    }
}
