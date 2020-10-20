package com.fanyi.server.async;

import com.fanyi.core.async.BaseAsyncTask;
import com.fanyi.core.util.SpringContextUtil;
import com.fanyi.server.netty.WebsocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.util.Assert;

/**
 * 异步线程开启 长连接服务
 * @author Best Wu
 * @date 2020/4/10 22:23 <br>
 */
@Slf4j
public class WebsocketServerAsyncTask extends BaseAsyncTask {

    private AsyncTaskExecutor asyncTaskExecutor = SpringContextUtil.getBean(AsyncTaskExecutor.class);


    private WebsocketServer websocketServer;

    public WebsocketServerAsyncTask(WebsocketServer websocketServer) {
        Assert.notNull(websocketServer, "websocketServer must not be null");
        this.websocketServer = websocketServer;
    }

    @Override
    protected void execute() throws Exception {
        log.info("【执行 开启 ws 长连接服务的 线程：{}】", Thread.currentThread().getName());
        this.websocketServer.open();
    }

    @Override
    protected void onSuccess() {

    }

    @Override
    protected void onFailure(Exception e) {
        log.error("【执行 WebsocketServerAsyncTask 出现异常】", e);
        this.websocketServer.close();
    }

    /**
     * 线程执行
     */
    public void exe() {
        this.asyncTaskExecutor.execute(this);
    }
}
