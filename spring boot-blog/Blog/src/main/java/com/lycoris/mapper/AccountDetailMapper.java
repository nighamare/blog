package com.lycoris.mapper;

import com.lycoris.dto.AccountDetail;

import java.util.List;

/**
* @author DELL
* @description 针对表【account_info(用户信息表)】的数据库操作Mapper
* @createDate 2022-08-10 14:38:32
* @Entity com.lycoris.domain.AccountDetail
*/
public interface AccountDetailMapper {

    AccountDetail selectByPrimaryKey(Long id);
    List<AccountDetail> selectAll(AccountDetail accountDetail);
    AccountDetail selectOneByCondition(AccountDetail accountDetail);
    int selectCount(AccountDetail accountDetail);

    List<AccountDetail> selectAllBySearch(String keyword);

}
