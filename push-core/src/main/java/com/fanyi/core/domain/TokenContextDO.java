package com.fanyi.core.domain;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Best Wu
 * @date 2020/4/8 19:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenContextDO {

    private String jti;

    private String sub;

    private Date iat;

    private Date exp;

    private Claims claims;
}
