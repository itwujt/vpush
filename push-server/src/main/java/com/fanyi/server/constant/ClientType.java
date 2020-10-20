package com.fanyi.server.constant;

import com.fanyi.core.exception.FrameworkException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 连接端 的类型
 * @author Best Wu
 * @date 2020/4/13 21:59 <br>
 */
public enum ClientType {
    /**
     * web
     */
    WEB(1 << 1, "web 端连接类型"),
    /**
     * app
     */
    APP(1 << 2, "app 端连接类型");

    private Integer type;

    private String desc;

    ClientType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ClientType match(Integer type) throws FrameworkException {
        switch (type) {
            case 2:
                return WEB;
            case 4:
                return APP;
            default:
                throw FrameworkException.of(401, "【不支持的端类型】");
        }
    }

}
