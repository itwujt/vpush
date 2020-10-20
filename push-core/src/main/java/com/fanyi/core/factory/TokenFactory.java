package com.fanyi.core.factory;

import com.fanyi.core.repository.TokenRepository;
import com.fanyi.core.repository.impl.SimpleTokenRepository;

/**
 * @author Best Wu
 * @date 2020/4/8 20:09
 */
public class TokenFactory extends AbstractTokenFactory {

    /**
     * 获取 TokenRepository
     * @return com.fanyi.core.repository.TokenRepository
     */
    @Override
    public TokenRepository getTokenRepository() {
        return new SimpleTokenRepository();
    }

}
