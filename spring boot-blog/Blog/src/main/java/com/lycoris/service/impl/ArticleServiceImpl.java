package com.lycoris.service.impl;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lycoris.domain.AccountInfo;
import com.lycoris.domain.Article;
import com.lycoris.dto.ArticleTagDetail;
import com.lycoris.dto.ArticlesInfo;
import com.lycoris.mapper.AccountInfoMapper;
import com.lycoris.mapper.ArticleMapper;
import com.lycoris.dto.SimilarArticle;
import com.lycoris.mapper.ArticleTagMapper;
import com.lycoris.service.ArticleService;
import com.lycoris.util.SimilarityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 22:00
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private AccountInfoMapper accountInfoMapper;
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertSelective(Article article) {
        articleMapper.insertSelective(article);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateByPrimaryKeySelective(Article article) {
        articleMapper.updateByPrimaryKeySelective(article);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(Long[] longs) {
        articleMapper.deleteByIds(longs);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Long aLong) {
        articleMapper.deleteByPrimaryKey(aLong);
    }

    @Override
    public PageInfo<Article> selectByPageSelective(int pageNum, int pageSize, Article article) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(articleMapper.selectAll(article));
    }

    @Override
    public Article selectById(Long aLong) {
        return articleMapper.selectByPrimaryKey(aLong);
    }

    @Override
    public Article selectOneByCondition(Article article) {
        return articleMapper.selectOneByCondition(article);
    }

    @SaCheckLogin
    @Override
    public List<SimilarArticle> getSimilarArticle(Long id) {
//        获取相似文章
        Article article = articleMapper.selectByPrimaryKey(id);
        String title = article.getTitle();
//        标题相似度匹配
        Article article1 = new Article();
        article1.setCategoryId(article.getCategoryId());
        List<Article> articles = articleMapper.selectAll(article1);
        List<SimilarArticle> list = new ArrayList<>();
        for (Article article2 : articles) {
            SimilarArticle similarArticle = new SimilarArticle();

            float cos = SimilarityUtil.cos(title, article2.getTitle());
            if (cos > 0.5 && !article2.getId().equals(id)) {
                similarArticle.setArticleId(article2.getId());
                similarArticle.setTitle(article2.getTitle());
                similarArticle.setSummary(article2.getSummary());
                AccountInfo accountInfo = accountInfoMapper.selectByPrimaryKey(article2.getAccountId());
                similarArticle.setAccountId(accountInfo.getAccountId());
                similarArticle.setNickname(accountInfo.getNickname());
                similarArticle.setAvatar(accountInfo.getAvatar());
                list.add(similarArticle);
            }
        }
        return list;
    }

    @Override
    public PageInfo<ArticlesInfo> selectArticlesInfoByPageSelective(int pageNum, int pageSize, ArticlesInfo articlesInfo) {
        if (articlesInfo == null) {
            articlesInfo = new ArticlesInfo();
        }
        articlesInfo.setStatus(0);
        PageHelper.startPage(pageNum, pageSize);
        List<ArticlesInfo> articlesInfos = articleMapper.selectAllArticlesInfo(articlesInfo);
        PageInfo<ArticlesInfo> pageInfo = new PageInfo<>(articlesInfos);
        List<ArticlesInfo> list = new ArrayList<>();
        for (ArticlesInfo articlesInfo1 : articlesInfos) {
            List<ArticleTagDetail> articleTagList = articleTagMapper.getArticleTagList(articlesInfo1.getId());
            articlesInfo1.setTags(articleTagList);
            list.add(articlesInfo1);
        }
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public PageInfo<ArticlesInfo> selectAllArticlesInfoByLike(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ArticlesInfo> articlesInfos = articleMapper.selectAllArticlesInfoByLike(keyword);
        PageInfo<ArticlesInfo> pageInfo = new PageInfo<>(articlesInfos);
        List<ArticlesInfo> list = new ArrayList<>();
        for (ArticlesInfo articlesInfo1 : articlesInfos) {
            List<ArticleTagDetail> articleTagList = articleTagMapper.getArticleTagList(articlesInfo1.getId());
            articlesInfo1.setTags(articleTagList);
            list.add(articlesInfo1);
        }
        pageInfo.setList(list);
        return pageInfo;
    }

    @SaCheckLogin
    @Override
    public ArticlesInfo selectOneArticleInfoByArticleId(Long articleId) {
        return articleMapper.selectOneArticleInfoByArticleId(articleId);
    }

    @Override
    public List<Article> selectHotArticles() {
        String s = (String) redisTemplate.opsForValue().get("hotArticle");
        List<Article> hotArticle = JSON.parseArray(s, Article.class);
        if (hotArticle == null) {
            List<Article> articles = articleMapper.selectHotArticles();
            String s1 = JSON.toJSONString(articles);
            this.redisTemplate.opsForValue().set("hotArticle", s1);
            return articles;
        } else {
            return hotArticle;
        }
    }

    @Override
    public List<Article> recommendedByStationMaster() {

        String s = (String) redisTemplate.opsForValue().get("recommendedArticle");
        List<Article> recommendedArticle = JSON.parseArray(s, Article.class);
        if (recommendedArticle == null) {
            List<Article> articles = articleMapper.recommendedByStationMaster();
            String s1 = JSON.toJSONString(articles);
            this.redisTemplate.opsForValue().set("recommendedArticle", s1);
            return articles;
        } else {
            return recommendedArticle;
        }
    }

    @Override
    public List<Article> asYouLike() {

        String s = (String) redisTemplate.opsForValue().get("likeArticle");
        List<Article> likeArticle = JSON.parseArray(s, Article.class);
        if (likeArticle == null) {
            List<Article> articles = articleMapper.asYouLike();
            String s1 = JSON.toJSONString(articles);
            this.redisTemplate.opsForValue().set("likeArticle", s1);
            return articles;
        } else {
            return likeArticle;
        }
    }

    @Override
    public PageInfo<Article> selectArticlesByPage(int pageNum, int pageSize, Article article) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(articleMapper.selectAllByVague(article));
    }

    @Override
    public PageInfo<Article> selectAuditArticlesByPage(int pageNum, int pageSize, Article article) {
//        选择待审核的文章 博客状态：0-已发布；1-草稿；2-已删除；3-待审核；4-审核不通过
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(articleMapper.selectAll(article));
    }

    @Override
    public int selectArticleCountByStatus(Integer status) {
        return articleMapper.selectArticleCountByStatus(status);
    }

    @Override
    public int selectArticleCount() {
        return articleMapper.selectArticleCount();
    }

    @Override
    public PageInfo<ArticlesInfo> selectArticlesInfoByPageAccountId(int pageNum, int pageSize, Long accountId) {
        PageHelper.startPage(pageNum, pageSize);
        List<ArticlesInfo> articlesInfos = articleMapper.selectArticlesInfoByPageAccountId(accountId);
        PageInfo<ArticlesInfo> pageInfo = new PageInfo<>(articlesInfos);
        List<ArticlesInfo> list = new ArrayList<>();
        for (ArticlesInfo articlesInfo1 : articlesInfos) {
            List<ArticleTagDetail> articleTagList = articleTagMapper.getArticleTagList(articlesInfo1.getId());
            articlesInfo1.setTags(articleTagList);
            list.add(articlesInfo1);
        }
        pageInfo.setList(list);
        return pageInfo;
    }
}
