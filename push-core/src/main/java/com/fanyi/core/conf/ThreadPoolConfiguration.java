package com.fanyi.core.conf;

import com.fanyi.core.constant.Cons;
import com.fanyi.core.properties.ThreadPoolProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static com.fanyi.core.constant.Cons.THREAD_POOL_PROPERTIES_PATH;
import static com.fanyi.core.constant.Cons.THREAD_POOL_PROPERTIES_PREFIX;

/**
 * <br>
 *
 * @author Best Wu
 * @date 2020/4/9 19:50 <br>
 */
@Configuration
public class ThreadPoolConfiguration {

    @Bean
    public ThreadPoolProperties threadPoolProperties() {
        ThreadPoolProperties threadPoolProperties = new ThreadPoolProperties();
        Properties properties = LoadPropertiesConfiguration.getProperties(THREAD_POOL_PROPERTIES_PATH);
        return LoadPropertiesConfiguration.getT(properties, threadPoolProperties, THREAD_POOL_PROPERTIES_PREFIX);
    }
}
