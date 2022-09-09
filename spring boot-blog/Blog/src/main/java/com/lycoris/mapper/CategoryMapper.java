package com.lycoris.mapper;

import com.lycoris.domain.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author DELL
* @description 针对表【category(博客类型信息表)】的数据库操作Mapper
* @createDate 2022-08-10 14:38:32
* @Entity com.lycoris.domain.BlogType
*/
public interface CategoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectAll(Category category);
    Category selectOneByCondition(Category category);
    void deleteByIds(@Param("ids") Long[] ids);
    int selectCount(Category category);
    List<Category> selectAllByPage(Category category);

}
