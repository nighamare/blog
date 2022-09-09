package com.lycoris.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 14:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentInfo {
    /**
     * 评论id
     */
    private Long id;

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

    /**
     * 回复量
     */
    private Integer replyCount;

    /**
     * 回复列表
     */
    private List<CommentReply> commentReplyList;

}
