package com.fanyi.server.service;

/**
 * 用于 用户 获取 token
 * @author Best Wu
 * @date 2020/4/12 20:08
 */
public interface ILoginService {
    /**
     * 根据 用户 id 和 电话 返回登录信息 token
     * @param userId 用户 id
     * @param telephone 电话
     * @return java.lang.String
     */
    String doLogin(String userId, String telephone);
}
