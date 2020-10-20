package com.fanyi.core.properties;

import lombok.Data;

/**
 * @author Best Wu
 * @date 2020/4/9 19:38
 */
@Data
public class ThreadPoolProperties {

    private Integer corePoolSize;

    private Integer maxPoolSize;
}
