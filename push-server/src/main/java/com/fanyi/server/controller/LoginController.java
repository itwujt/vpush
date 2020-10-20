package com.fanyi.server.controller;

import com.fanyi.server.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Best Wu
 * @date 2020/4/12 20:23
 */
@RestController
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @PostMapping("/login")
    public String login(String userId, String telephone) {
        return loginService.doLogin(userId, telephone);
    }
}
