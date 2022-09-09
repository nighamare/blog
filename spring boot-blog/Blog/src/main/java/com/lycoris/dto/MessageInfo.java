package com.lycoris.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/22 15:13
 */
@Data
public class MessageInfo {
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

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;
}
