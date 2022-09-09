package com.lycoris.mapper;

import com.lycoris.domain.Tag;
import com.lycoris.domain.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author DELL
* @description 针对表【tag(标签信息表)】的数据库操作Mapper
* @createDate 2022-08-10 14:38:32
* @Entity com.lycoris.domain.Tag
*/
public interface TagMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    List<Tag> selectAll(Tag tag);
    List<Tag> selectAll10(Tag tag);
    Tag selectOneByCondition(Tag tag);
    void deleteByIds(@Param("ids") Long[] ids);
    int selectCount(Tag tag);

    List<Tag> selectAllByPage(Tag tag);
}
