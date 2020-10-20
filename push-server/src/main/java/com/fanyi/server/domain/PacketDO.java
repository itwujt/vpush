package com.fanyi.server.domain;

import com.fanyi.server.constant.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 数据包
 * @author Best Wu
 * @date 2020/4/10 21:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacketDO implements Serializable {
    private static final long serialVersionUID = -4692964126416835464L;
    /**
     * 消息类型 <br>
     * 必填 <br>
     * @see MessageType
     */
    private Integer messageType;
    /**
     * 消息对象
     */
    private MessageDO message;
    /**
     * 发送人 id <br>
     * 必填 <br>
     */
    private String sendUserId;
    /**
     * 发送人 id <br>
     * 必填 <br>
     */
    private String telephone;
    /**
     * 接收人列表
     */
    private List<String> receiveUserIds;
    /**
     * 是否为广播
     */
    private Boolean broadcast;


}
