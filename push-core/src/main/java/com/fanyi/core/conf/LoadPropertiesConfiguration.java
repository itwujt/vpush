package com.fanyi.core.conf;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.Set;

/**
 * 手动加载 自定义 properties 文件中定义的属性
 * @author Best Wu
 * @date 2020/4/9 19:43
 */
@Slf4j
public class LoadPropertiesConfiguration {

    public static Properties getProperties(String path) {
        Properties properties = new Properties();
        try {
            // 获取指定路径的文件流
            InputStream inputStream = LoadPropertiesConfiguration.class.getClassLoader().getResourceAsStream(path);
            if (inputStream != null) {
                Reader reader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
                // 加载properties
                properties.load(reader);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("【不支持的编码异常】", e);
        } catch (IOException e) {
            log.error("【输入输出异常】", e);
        }
        return properties;
    }

    public static <T> T getT(Properties properties, T t, String prefix) {
        try {
            // 只有在properties不为null的时候进行处理，这里基本上都会处理
            if (null != properties) {
                // 获取值循环处理
                Set<Object> objects = properties.keySet();
                for (Object object : objects) {
                    String key = object.toString();
                    if (key.startsWith(prefix)) {
                        // 获取指定class的全部的field，之后进行循环处理
                        Field[] fields = t.getClass().getDeclaredFields();
                        for (Field field : fields) {
                            // 循环对比，找到真爱
                            if (key.endsWith(field.getName())) {
                                field.setAccessible(true);
                                // 进行赋值，这里就要求属性文件里定义的类型 必须有String类型参数的构造器，
                                // 即不能是int boolean了 得是Integer 和Boolean 需为包装类型
                                field.set(t, field.getType().getConstructor(String.class).newInstance(properties.getProperty(key)));
                            }
                        }
                    }
                }
                return t;
            }
        } catch (Exception e) {
            log.error("【properties 赋值失败】", e);
        }
        return t;
    }
}
