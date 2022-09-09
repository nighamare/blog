package com.lycoris.dto;

import com.lycoris.domain.ArticleTag;
import com.lycoris.domain.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/19 12:04
 */
@Data
public class ArticlesInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long accountId;
    private Long id;
    private String title;
    private String nickname;
    private String summary;
    /**
     * 博客类型ID
     */
    private Long categoryId;
    /**
     * 博客类型名称
     */
    private String categoryName;
    /**
     * 博客状态：0-已发布；1-草稿；2-回收站；3-待审核；4-审核不通过 5-已删除
     */
    private Integer status;

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

    List<ArticleTagDetail> tags;




}
