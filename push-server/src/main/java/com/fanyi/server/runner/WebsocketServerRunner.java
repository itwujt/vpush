package com.fanyi.server.runner;

import com.fanyi.server.async.WebsocketServerAsyncTask;
import com.fanyi.server.netty.WebsocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Best Wu
 * @date 2020/4/12 15:51
 */
@Slf4j
@Component
public class WebsocketServerRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("【执行 Runner 的线程 name：{}】", Thread.currentThread().getName());
        new WebsocketServerAsyncTask(new WebsocketServer()).exe();
    }
}
