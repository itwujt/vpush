package com.fanyi.server.service.impl;

import com.fanyi.core.cache.CeService;
import com.fanyi.core.domain.TokenContextDO;
import com.fanyi.core.factory.TokenFactoryBuilder;
import com.fanyi.core.repository.TokenRepository;
import com.fanyi.core.repository.impl.SimpleIdRepository;
import com.fanyi.server.constant.Cons;
import com.fanyi.server.service.ILoginService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author Best Wu
 * @date 2020/4/12 20:10
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private CeService cacheService;

    @Override
    public String doLogin(String userId, String telephone) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(telephone, "telephone must not be null");
        Assert.hasLength(userId, "userId must not be empty");
        Assert.hasLength(telephone, "telephone must not be empty");

        TokenRepository tokenRepository = TokenFactoryBuilder.getTokenFactory().getTokenRepository();
        String secretKey = tokenRepository.generateKey(userId, telephone);
        String user = userId + "_" + telephone;
        cacheService.setExpire(Cons.PUSH_USER_SECRET_KEY_PREFIX + user, secretKey, 40L);
        SimpleIdRepository simpleIdRepository = new SimpleIdRepository(0L, 0L);
        String id = simpleIdRepository.generateId();
        long current = System.currentTimeMillis();
        Date now = new Date(current);
        Date exp = new Date(current + 30 * 1000L);
        Claims claims = new DefaultClaims();
        claims.put("user", user);
        TokenContextDO tokenContextDO = TokenContextDO.builder().jti(id).iat(now).exp(exp).sub(user).claims(claims).build();
        return tokenRepository.createToken(secretKey, tokenContextDO);
    }
}
