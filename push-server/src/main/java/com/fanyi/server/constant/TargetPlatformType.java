package com.fanyi.server.constant;

import com.fanyi.core.exception.FrameworkException;

/**
 * 表示 要推送目标的端类型
 * @author Best Wu
 * @date 2020/4/13 21:39
 */
public enum TargetPlatformType {
    /**
     * web 端
     */
    WEB(1 << 1, "WEB 端"),
    /**
     * app 端
     */
    APP(1 << 2, "APP 端"),
    /**
     * web 和 app 端
     */
    WEB_AND_APP(1 << 3, "WEB_AND_APP 端");
    /**
     * 类型
     */
    private Integer type;
    /**
     * 描述
     */
    private String desc;

    TargetPlatformType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static TargetPlatformType match(Integer type) throws FrameworkException {
        switch (type) {
            case 2:
                return WEB;
            case 4:
                return APP;
            case 8:
                return WEB_AND_APP;
            default:
                break;
        }
        throw FrameworkException.of(401, "【不支持的平台类型】");
    }
}
