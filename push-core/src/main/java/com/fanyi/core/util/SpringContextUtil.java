package com.fanyi.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 用于手动获取 spring bean <br>
 * @author Best Wu
 * @date 2020/4/7 22:37
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 根据 bean 名称 获取 spring bean <br>
     * @date 2020/4/7 22:40
     * @param beanName bean 名称
     * @return T
     * @author Best Wu
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 根据 bean 类型 获取 spring bean <br>
     * @date 2020/4/7 22:40
     * @param clazz 类型
     * @return T
     * @author Best Wu
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据bean 名称和 类型 获取 spring bean <br>
     * @date 2020/4/7 22:40
     * @param beanName bean 名称
     * @param clazz 类型
     * @param <T> 泛型
     * @return T
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }
}
