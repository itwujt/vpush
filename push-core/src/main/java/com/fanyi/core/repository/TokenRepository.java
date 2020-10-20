package com.fanyi.core.repository;

import com.fanyi.core.domain.TokenContextDO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * @author Best Wu
 * @date 2020/4/8 19:55
 */
public interface TokenRepository {
    /**
     * 根据 用户 id 电话 生成 秘钥
     * @param userId 用户 id
     * @param telephone 电话号
     * @return java.lang.String
     */
    String generateKey(String userId, String telephone);

    /**
     * 根据生成的秘钥 和 token 中的必要信息 生成token
     * @param secretKey 秘钥
     * @param tokenContextDO token 上下文对象 包含token的信息
     * @see TokenContextDO
     * @return java.lang.String
     */
    String createToken(String secretKey, TokenContextDO tokenContextDO);

    /**
     * 解析
     * @param token token
     * @param secretKey 秘钥
     * @return io.jsonwebtoken.Claims
     */
    Claims parse(String token, String secretKey) throws ExpiredJwtException, SignatureException;


}
