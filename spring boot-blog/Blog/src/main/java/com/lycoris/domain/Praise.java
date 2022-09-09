package com.lycoris.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 点赞信息表
 * @author DELL
 * @TableName praise
 */
@Data
public class Praise implements Serializable {
    /**
     * 点赞ID
     */
    private Long id;

    /**
     * 点赞的ID
     */
    private Long praiseForId;

    /**
     * 点赞者ID
     */
    private Long accountId;

    /**
     * 点赞时间
     */
    private Date createTime;

    /**
     * 点赞类型：0-文章 1-评论
     */
    private Integer praiseType;

    private static final long serialVersionUID = 1L;
}