package com.lycoris.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 评论信息表
 * @TableName comment
 */
@Data
public class Comment implements Serializable {
    /**
     * 评论ID
     */
    private Long id;

    /**
     * 博客ID
     */
    private Long articleId;

    /**
     * 评论者ID
     */
    private Long accountId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 根评论id
     */
    private Long rootCommentId;

    /**
     * 所回复的目标评论的accountId
     */
    private Long replyCommentAccountId;

    /**
     * 回复目标评论id
     */
    private Long replyCommentId;

    /**
     * 删除（0代表未删除，1代表已删除）
     */
    private Integer status;

    /**
     * 评论等级[ 1 一级评论 默认 ，2 二级评论]
     */
    private Integer commentLevel;

    /**
     * 点赞数
     */
    private Integer praiseNum;

    /**
     * 置顶状态[ 1 置顶，0 不置顶 默认 ]
     */
    private Integer topStatus;

    /**
     * 评论时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}