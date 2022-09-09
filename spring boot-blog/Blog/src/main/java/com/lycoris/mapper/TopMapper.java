package com.lycoris.mapper;

import com.lycoris.domain.Top;
import com.lycoris.dto.PraiseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author DELL
* @description 针对表【top(置顶信息表)】的数据库操作Mapper
* @createDate 2022-08-30 00:16:42
* @Entity com.lycoris.domain.Top
*/
public interface TopMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Top record);

    int insertSelective(Top record);

    Top selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Top record);

    int updateByPrimaryKey(Top record);

    List<Top> selectAllByPage(Top top);

    Top selectOne(Top top);

    void deleteByIds(@Param("ids") Long[] ids);
}
