package com.lycoris.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * 标签博客关系表
 * @TableName article_tag
 */
@Data
public class ArticleTag implements Serializable {
    /**
     * 标签博客关系ID
     */
    private Long id;

    /**
     * 标签ID
     */
    private Long tagId;

    /**
     * 博客ID
     */
    private Long articleId;

    private static final long serialVersionUID = 1L;
}