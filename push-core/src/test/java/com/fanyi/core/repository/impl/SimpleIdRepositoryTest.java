package com.fanyi.core.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 测试雪花算法 id
 * @author Best Wu
 * @date 2020/4/8 21:14
 */
@Slf4j
public class SimpleIdRepositoryTest {

    @Test
    public void test0() {
        SimpleIdRepository simpleIdRepository = new SimpleIdRepository(0L, 0L);
        String id = simpleIdRepository.generateId();
        log.info("【生成id：{}】", id);
    }
}