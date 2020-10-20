package com.fanyi.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息对象
 * @author Best Wu
 * @date 2020/4/10 21:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDO implements Serializable {
    /**
     * 消息 id
     */
    private Integer msgId;
    /**
     * 内容
     */
    private String content;
    /**
     * 消息创建时间
     */
    private Date time;
}
