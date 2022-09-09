package com.lycoris.mapper;

import com.lycoris.domain.AccountInfo;

import java.util.List;

/**
* @author DELL
* @description 针对表【account_info(用户信息表)】的数据库操作Mapper
* @createDate 2022-08-25 13:43:29
* @Entity com.lycoris.domain.AccountInfo
*/
public interface AccountInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AccountInfo record);

    int insertSelective(AccountInfo record);

    AccountInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountInfo record);

    int updateByPrimaryKey(AccountInfo record);

    List<AccountInfo> selectAccountInfoAll(AccountInfo accountInfo);

}
