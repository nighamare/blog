package com.lycoris.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 14:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentReply {

    /**
     * 评论id
     */
    private Long id;
    /**
     * 文章ID
     * */
    private Long articleId;

    /**
     * 父评论id
     */
    private Long rootCommentId;

    /**
     * 用户id
     */
    private Long accountId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 被回复用户id
     */
    private Long replyCommentAccountId;

    /**
     * 被回复用户昵称
     */
    private String replyNickname;

    /**
     * 回复目标评论id
     */
    private Long replyCommentId;

    /**
     * 评论等级[ 1 一级评论 默认 ，2 二级评论]
     */
    private Integer commentLevel;
    /**
     * 评论状态
     * */
    private Integer status;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer praiseNum;

    /**
     * 评论时间
     */
    private Date createTime;

}
