package com.lycoris.mapper;

import com.lycoris.domain.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author DELL
* @description 针对表【account(账号信息表)】的数据库操作Mapper
* @createDate 2022-08-10 14:38:32
* @Entity com.lycoris.domain.Account
*/
public interface AccountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    List<Account> selectAll(Account account);
    Account selectOneByCondition(Account account);
    void deleteByIds(@Param("ids") Long[] ids);
    int selectCount(Account account);

}
