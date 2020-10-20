package com.fanyi.core.async;

/**
 * 线程
 * @author Best Wu
 * @date 2020/4/9 20:04
 */
public abstract class BaseAsyncTask implements Runnable {

    @Override
    public void run() {
        try {
            execute();
            onSuccess();
        } catch (Exception e) {
            onFailure(e);
        }
    }

    /**
     * 执行
     * @throws Exception 执行过程中发生的异常
     */
    protected abstract void execute() throws Exception;

    /**
     * 当 成功时的 处理
     */
    protected abstract void onSuccess();

    /**
     * 当 失败时的处理
     * @param e 异常 对象
     */
    protected abstract void onFailure(Exception e);
}
