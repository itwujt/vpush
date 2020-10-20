package com.fanyi.core.factory;

import com.fanyi.core.repository.TokenRepository;

/**
 * @author Best Wu
 * @date 2020/4/8 19:17
 */
public abstract class AbstractTokenFactory {
    /**
     * 获取 token repository
     * @return com.fanyi.core.repository.TokenRepository
     */
    public abstract TokenRepository getTokenRepository();
}
