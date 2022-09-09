package com.lycoris.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 友情链表
 * @TableName friendship_chain
 */
@Data
public class FriendshipChain implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 链接名
     */
    private String linkName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 
     */
    private Integer status;

    /**
     * 链接头像
     */
    private String linkAvatar;

    /**
     * 链接地址
     */
    private String linkAddress;

    /**
     * 链接介绍
     */
    private String linkIntro;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}