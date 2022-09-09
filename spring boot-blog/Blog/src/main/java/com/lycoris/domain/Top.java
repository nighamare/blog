package com.lycoris.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 置顶信息表
 * @TableName top
 */
@Data
public class Top implements Serializable {
    /**
     * 置顶ID
     */
    private Long id;

    /**
     * 博客ID
     */
    private Long articleId;

    /**
     * 置顶时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}