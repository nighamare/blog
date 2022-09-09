package com.lycoris.mapper;

import com.lycoris.domain.Article;
import com.lycoris.dto.ArticlesInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author DELL
* @description 针对表【article(博客文章信息表)】的数据库操作Mapper
* @createDate 2022-08-10 14:38:32
* @Entity com.lycoris.domain.Article
*/
public interface ArticleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    List<Article> selectAll(Article article);
    Article selectOneByCondition(Article article);
    void deleteByIds(@Param("ids") Long[] ids);
    int selectCount(Article article);
    List<ArticlesInfo> selectAllArticlesInfo(ArticlesInfo articlesInfo);
    List<ArticlesInfo> selectAllArticlesInfoByLike(String keyword);
    ArticlesInfo selectOneArticleInfoByArticleId(Long articleId);


    List<Article> selectHotArticles();
    List<Article> recommendedByStationMaster();
    List<Article> asYouLike();
    List<Article> selectAllByVague(Article article);

    int selectArticleCountByStatus(Integer status);
    int selectArticleCount();


    List<ArticlesInfo> selectArticlesInfoByPageAccountId(Long accountId);
}
