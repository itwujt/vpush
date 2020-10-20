package com.fanyi.core.repository.impl;

import com.fanyi.core.domain.TokenContextDO;
import com.fanyi.core.repository.TokenRepository;
import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

/**
 * @author Best Wu
 * @date 2020/4/8 20:11
 */
public class SimpleTokenRepository implements TokenRepository {

    @Override
    public String generateKey(String userId, String telephone) {
        StringBuilder str = new StringBuilder();
        str.append(userId).append("_").append(telephone);
        return Base64.encodeBase64URLSafeString(str.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String createToken(String secretKey, TokenContextDO tokenContextDO) {
        return Jwts.builder()
                .setId(tokenContextDO.getJti())
                .setSubject(tokenContextDO.getSub())
                .setIssuedAt(tokenContextDO.getIat())
                .setExpiration(tokenContextDO.getExp())
                .setClaims(tokenContextDO.getClaims())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Override
    public Claims parse(String token, String secretKey) throws ExpiredJwtException, SignatureException {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
