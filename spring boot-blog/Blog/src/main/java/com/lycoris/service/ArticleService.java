package com.lycoris.service;

import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Article;
import com.lycoris.dto.ArticlesInfo;
import com.lycoris.dto.SimilarArticle;

import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 21:59
 */
public interface ArticleService extends BasicService<Article,Long>{

    List<SimilarArticle> getSimilarArticle(Long id);
    PageInfo<ArticlesInfo> selectArticlesInfoByPageSelective(int pageNum, int pageSize, ArticlesInfo articlesInfo);
    PageInfo<ArticlesInfo> selectAllArticlesInfoByLike(String keyword,int pageNum, int pageSize);
    ArticlesInfo selectOneArticleInfoByArticleId(Long articleId);

    List<Article> selectHotArticles();
    List<Article> recommendedByStationMaster();
    List<Article> asYouLike();

    PageInfo<Article> selectArticlesByPage(int pageNum, int pageSize, Article article);
    PageInfo<Article> selectAuditArticlesByPage(int pageNum, int pageSize, Article article);

    int selectArticleCountByStatus(Integer status);
    int selectArticleCount();

    PageInfo<ArticlesInfo> selectArticlesInfoByPageAccountId(int pageNum, int pageSize, Long accountId);
}
