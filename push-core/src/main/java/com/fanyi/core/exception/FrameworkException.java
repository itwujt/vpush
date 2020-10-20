package com.fanyi.core.exception;

/**
 * @author Best Wu
 * @date 2020/4/10 20:10
 */
public class FrameworkException extends RuntimeException{
    /**
     * 异常码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String message;

    private FrameworkException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public static FrameworkException of(Integer code, String message) {
        return new FrameworkException(code, message);
    }
}
