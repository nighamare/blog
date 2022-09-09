package com.lycoris.mapper;

import com.lycoris.domain.ArticleTag;
import com.lycoris.domain.ArticleTag;
import com.lycoris.dto.ArticleTagDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author DELL
* @description 针对表【article_tag(标签博客关系表)】的数据库操作Mapper
* @createDate 2022-08-10 14:38:32
* @Entity com.lycoris.domain.ArticleTag
*/
public interface ArticleTagMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArticleTag record);

    int insertSelective(ArticleTag record);

    ArticleTag selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleTag record);

    int updateByPrimaryKey(ArticleTag record);

    List<ArticleTag> selectAll(ArticleTag articleTag);
    ArticleTag selectOneByCondition(ArticleTag articleTag);
    void deleteByIds(@Param("ids") Long[] ids);
    int selectCount(ArticleTag articleTag);
    List<ArticleTagDetail> getArticleTagList(Long articleId);
    List<ArticleTagDetail> selectAllDetail(ArticleTagDetail articleTagDetail);
    
}
