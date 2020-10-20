package com.fanyi.server.constant;

import io.netty.util.AttributeKey;

/**
 * @author Best Wu
 * @date 2020/4/10 20:22
 */
public final class Cons {


    public static final int WS_URL_SPILT_NUM = 2;

    public static final int WS_URL_PARAM_NUM = 4;


    public static final AttributeKey<String> CLIENT_TYPE = AttributeKey.newInstance("clientType");
    public static final AttributeKey<String> USER_ID = AttributeKey.newInstance("userId");
    public static final AttributeKey<String> TELEPHONE = AttributeKey.newInstance("telephone");
    public static final AttributeKey<String> TOKEN = AttributeKey.newInstance("token");


    public static final String PUSH_USER_SECRET_KEY_PREFIX = "PUSH_USER_SECRET_";

    public static final String PUSH_USER_OFFLINE_KEY_PREFIX = "PUSH_USER_OFFLINE_";

    private Cons(){}
}
