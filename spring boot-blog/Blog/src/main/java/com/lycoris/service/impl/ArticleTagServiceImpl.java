package com.lycoris.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lycoris.domain.ArticleTag;
import com.lycoris.domain.Tag;
import com.lycoris.dto.ArticleTagDetail;
import com.lycoris.mapper.ArticleTagMapper;
import com.lycoris.mapper.TagMapper;
import com.lycoris.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 22:22
 */
@Service
public class ArticleTagServiceImpl implements ArticleTagService {
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Resource
    private TagMapper tagMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void insertSelective(ArticleTag articleTag) {
        articleTagMapper.insertSelective(articleTag);
    }

    @Override
    public void updateByPrimaryKeySelective(ArticleTag articleTag) {
        articleTagMapper.updateByPrimaryKeySelective(articleTag);
    }

    @Override
    public void deleteByIds(Long[] longs) {
        articleTagMapper.deleteByIds(longs);
    }

    @Override
    public void deleteById(Long aLong) {
        articleTagMapper.deleteByPrimaryKey(aLong);
    }

    @Override
    public PageInfo<ArticleTag> selectByPageSelective(int pageNum, int pageSize, ArticleTag articleTag) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(articleTagMapper.selectAll(articleTag));
    }

    @Override
    public ArticleTag selectById(Long aLong) {
        return articleTagMapper.selectByPrimaryKey(aLong);
    }

    @Override
    public ArticleTag selectOneByCondition(ArticleTag articleTag) {
        return articleTagMapper.selectOneByCondition(articleTag);
    }

    @Override
    public List<ArticleTagDetail> getArticleTagList(Long articleId) {
        return articleTagMapper.getArticleTagList(articleId);
    }

    @Override
    public PageInfo<ArticleTagDetail> getArticleTagByPage(int pageNum, int pageSize, ArticleTagDetail articleTagDetail) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(articleTagMapper.selectAllDetail(articleTagDetail));
    }

    @Override
    public PageInfo<ArticleTag> selectArticleTagByPage(int pageNum, int pageSize, ArticleTag articleTag) {
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleTag> articleTags = articleTagMapper.selectAll(articleTag);
        return new PageInfo<>(articleTags);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void handleTagNames(String[] tagName, Long articleId) {
        String stringTags = (String) redisTemplate.opsForValue().get("tags");
        List<Tag> tags = JSON.parseArray(stringTags, Tag.class);
        if (tags == null) {
            List<Tag> tags1 = tagMapper.selectAll(null);
            String s1 = JSON.toJSONString(tags1);
            this.redisTemplate.opsForValue().set("tags", s1);
        } else {
            //已存在的Tag
            List<Tag> list = new ArrayList<>();
            List<String> listTagName = new ArrayList<>();
            List<String> tagNames = new ArrayList<>(Arrays.asList(tagName));
            //                判断tag表中是否有该标签
            for (Tag tag : tags) {
                String tagName1 = tag.getTagName();
                if (tagNames.contains(tagName1)) {
                    list.add(tag);
                    listTagName.add(tagName1);
                }
            }
            tagNames.removeIf(listTagName::contains);
//            try {
//                tagNames.removeAll(listTagName);
//            }catch (Exception e){
//                e.printStackTrace();
//                throw new MyRuntimeException(200210,"出现异常！");
//            }

//            List<Tag> collect = tags.stream().filter(item -> tagNames.contains(item.getTagName())).collect(Collectors.toList());
//            List<String> collect = tagNames.stream().filter(listTagName::contains).collect(Collectors.toList());
            //已存在则直接添加进articletag表
            for (Tag tag : list) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tag.getId());
                articleTag.setArticleId(articleId);
                articleTagMapper.insertSelective(articleTag);
            }
            //不存在则先加入tag后插入at
            for (String s : tagNames) {
                Tag tag1 = new Tag();
                tag1.setTagName(s.trim());
                tag1.setTagDesc(s);
                tagMapper.insertSelective(tag1);
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tag1.getId());
                articleTag.setArticleId(articleId);
                articleTagMapper.insertSelective(articleTag);
            }
        }


    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateArticleTagNames(String[] tagName, Long articleId) {
        String stringTags = (String) redisTemplate.opsForValue().get("tags");
        List<Tag> tags = JSON.parseArray(stringTags, Tag.class);
        if (tags == null) {
            List<Tag> tags1 = tagMapper.selectAll(null);
            String s1 = JSON.toJSONString(tags1);
            this.redisTemplate.opsForValue().set("tags", s1);
        } else {
            //已存在的Tag
            List<Tag> list = new ArrayList<>();
            List<String> listTagName = new ArrayList<>();
            List<String> tagNames = new ArrayList<>();
            //去重 转集合
            for (String value : tagName) {
                //如果集合里面没有相同的元素才往里存
                if (!tagNames.contains(value)) {
                    tagNames.add(value);
                }
            }
            /*查询这篇文章的所有标签啊*/
            ArticleTagDetail articleTagDetail = new ArticleTagDetail();
            articleTagDetail.setArticleId(articleId);
            List<ArticleTagDetail> articleTagDetails = articleTagMapper.selectAllDetail(articleTagDetail);
            List<ArticleTagDetail> collect = articleTagDetails.stream().filter(item -> !tagNames.contains(item.getTagName())).collect(Collectors.toList());
            for (ArticleTagDetail tagDetail : collect) {
                articleTagMapper.deleteByPrimaryKey(tagDetail.getId());
            }
            //判断tag表中是否有该标签
            for (Tag tag : tags) {
                String tagName1 = tag.getTagName();
                if (tagNames.contains(tagName1)) {
                    list.add(tag);
                    listTagName.add(tagName1);
                }
            }
            tagNames.removeIf(listTagName::contains);
            //已存在则直接添加进articletag表
            for (Tag tag : list) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tag.getId());
                articleTag.setArticleId(articleId);
                ArticleTag articleTag1 = articleTagMapper.selectOneByCondition(articleTag);
                if (articleTag1 == null) {
                    articleTagMapper.insertSelective(articleTag);
                }

            }
            //不存在则先加入tag后插入at
            for (String s : tagNames) {
                Tag tag1 = new Tag();
                tag1.setTagName(s);
                tag1.setTagDesc(s);
                tagMapper.insertSelective(tag1);
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tag1.getId());
                articleTag.setArticleId(articleId);
                ArticleTag articleTag1 = articleTagMapper.selectOneByCondition(articleTag);
                if (articleTag1 == null) {
                    articleTagMapper.insertSelective(articleTag);
                }
            }
        }
    }
}



