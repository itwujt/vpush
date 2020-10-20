package com.fanyi.core.factory;

/**
 * @author Best Wu
 * @date 2020/4/8 20:12
 */
public class TokenFactoryBuilder {
    /**
     * 获取 TokenFactory
     * @return com.fanyi.core.factory.AbstractTokenFactory
     */
    public static AbstractTokenFactory getTokenFactory() {
        return new TokenFactory();
    }
}
