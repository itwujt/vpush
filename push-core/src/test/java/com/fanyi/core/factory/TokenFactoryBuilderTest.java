package com.fanyi.core.factory;

import com.fanyi.core.domain.TokenContextDO;
import com.fanyi.core.repository.TokenRepository;
import com.fanyi.core.repository.impl.SimpleIdRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

/**=
 * 测试 获取 token repository 并 生成 token 以及解析token <br>
 * @author Best Wu
 * @date 2020/4/8 21:37 <br>
 */
@Slf4j
public class TokenFactoryBuilderTest {

    @Test
    public void test0() {
        AbstractTokenFactory tokenFactory = TokenFactoryBuilder.getTokenFactory();
        TokenRepository tokenRepository = tokenFactory.getTokenRepository();
        String key = tokenRepository.generateKey("BestWu", "18512451650");
        SimpleIdRepository simpleIdRepository = new SimpleIdRepository(0L, 0L);
        String id = simpleIdRepository.generateId();
        long current = System.currentTimeMillis();
        Date now = new Date(current);
        Date exp = new Date(current + 30 * 60 * 1000L);
        Claims claims = new DefaultClaims();
        claims.put("random", UUID.randomUUID());
        TokenContextDO contextDO = TokenContextDO.builder().jti(id).sub("BestWu").iat(now).exp(exp).claims(claims).build();
        String token = tokenRepository.createToken(key, contextDO);
        log.info("【生成的token:{}】", token);
    }

    @Test
    public void test1() {
        AbstractTokenFactory tokenFactory = TokenFactoryBuilder.getTokenFactory();
        TokenRepository tokenRepository = tokenFactory.getTokenRepository();
        String key = tokenRepository.generateKey("BestWu", "18512451650");
        Claims parse = tokenRepository.parse("eyJhbGciOiJIUzI1NiJ9.eyJyYW5kb20iOiJhMzM1NWVjNi1kMTIxLTRjMjYtYWIzMi0xZmEwYzRjMjA5NGMifQ.fcl7Dqv4J4RSPSJ2603uWTH6gpNdsjDva1yFe0q-aec", key);
        log.info("【解析后token:{}】", parse);
    }
}