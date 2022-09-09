package com.lycoris.mapper;

import com.lycoris.domain.Praise;
import com.lycoris.dto.PraiseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author DELL
* @description 针对表【praise(点赞信息表)】的数据库操作Mapper
* @createDate 2022-08-18 09:00:56
* @Entity com.lycoris.domain.Praise
*/
public interface PraiseMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Praise record);

    int insertSelective(Praise record);

    Praise selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Praise record);

    int updateByPrimaryKey(Praise record);

    List<Praise> selectAll(Praise praise);
    Praise selectOneByCondition(Praise praise);
    void deleteByIds(@Param("ids") Long[] ids);
    int selectCount(Praise praise);
    List<PraiseInfo> selectAllByPage(PraiseInfo praiseInfo);

    List<Praise> selectPraiseList(@Param("accountId") Long accountId,@Param("ids") List<Long> ids);
}
