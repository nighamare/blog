package com.lycoris.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 博客文章信息表
 * @TableName article
 */
@Data
public class Article implements Serializable {
    /**
     * 博客ID
     */
    private Long id;

    /**
     * 博主ID
     */
    private Long accountId;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 博客类型ID
     */
    private Long categoryId;

    /**
     * 博客状态：0-已发布；1-草稿；2-回收站；3-待审核；4-审核不通过 5-已删除
     */
    private Integer status;

    /**
     * 评论开启：0开启，1关闭
     */
    private Integer commentOpen;

    /**
     * 赞赏开启：0开启，1关闭
     */
    private Integer appreciation;

    /**
     * 是否推荐：0推荐，1不推荐
     */
    private Integer recommend;

    /**
     * 是否转载：0 原创，1转载
     */
    private Integer reprint;

    /**
     * 转载地址
     */
    private String reprintUrl;

    /**
     * 博客封面图片路径
     */
    private String blogImg;

    /**
     * 点击数
     */
    private Long views;

    /**
     * 评论数
     */
    private Long commentNum;

    /**
     * 点赞数
     */
    private Long likes;

    /**
     * 发表时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastUpdate;

    private static final long serialVersionUID = 1L;
}