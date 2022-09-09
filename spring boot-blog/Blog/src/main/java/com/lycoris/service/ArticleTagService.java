package com.lycoris.service;

import com.github.pagehelper.PageInfo;
import com.lycoris.domain.ArticleTag;
import com.lycoris.dto.ArticleTagDetail;

import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 22:21
 */
public interface ArticleTagService extends BasicService<ArticleTag,Long>{
    List<ArticleTagDetail> getArticleTagList(Long articleId);
    PageInfo<ArticleTagDetail> getArticleTagByPage(int pageNum, int pageSize,ArticleTagDetail articleTagDetail);

    PageInfo<ArticleTag> selectArticleTagByPage(int pageNum, int pageSize,ArticleTag articleTag);


    void handleTagNames(String[] tagName,Long articleId);
    void updateArticleTagNames(String[] tagName,Long articleId);
}
