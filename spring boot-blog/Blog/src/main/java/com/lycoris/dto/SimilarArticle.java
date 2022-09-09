package com.lycoris.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/17 14:54
 */
@Data
public class SimilarArticle implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 文章ID
     * */
    private Long articleId;
    /**
     * 博主ID
     * */
    private Long accountId;
    /**
     * 文章标题
     * */
    private String title;
    /**
     * 文章摘要
     * */
    private String summary;
    /**
     * 昵称
     * */
    private String nickname;
    /**
     * 头像
     * */
    private String avatar;




}
