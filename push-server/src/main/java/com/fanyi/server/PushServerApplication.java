package com.fanyi.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Best Wu
 * @date 2020/4/8 22:04
 */
@SpringBootApplication(scanBasePackages = "com.fanyi.**")
public class PushServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PushServerApplication.class, args);
    }
}
