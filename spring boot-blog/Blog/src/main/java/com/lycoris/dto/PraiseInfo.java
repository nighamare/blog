package com.lycoris.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/29 23:07
 */
@Data
@Accessors(chain = true)
public class PraiseInfo {
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

    private String avatar;

    private String nickname;


}
