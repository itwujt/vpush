package com.fanyi.core.conf;

import com.fanyi.core.properties.ThreadPoolProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * <br>
 *
 * @author Best Wu
 * @date 2020/4/9 19:49 <br>
 */
@Configuration
public class AsyncExecutorConfiguration {

    @Bean
    public AsyncTaskExecutor asyncTaskExecutor(ThreadPoolProperties threadPoolProperties) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        threadPoolTaskExecutor.setQueueCapacity(threadPoolProperties.getMaxPoolSize() * 10);
        threadPoolTaskExecutor.setKeepAliveSeconds(300);
        threadPoolTaskExecutor.setThreadNamePrefix("BaseExecutor-");
        return threadPoolTaskExecutor;
    }
}
