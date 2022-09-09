package com.lycoris.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 消息信息表
 * @TableName message
 */
@Data
public class Message implements Serializable {
    /**
     * 消息ID
     */
    private Long id;

    /**
     * 消息接收者ID
     */
    private Long accountId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息状态（0代表未读，1代表已读）
     */
    private Integer status;

    /**
     * 消息时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}