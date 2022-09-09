package com.lycoris.service;

import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Account;
import com.lycoris.domain.AccountInfo;
import com.lycoris.dto.AccountDetail;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 14:50
 */
public interface AccountService extends BasicService<AccountDetail,Long> {

    AccountInfo selectAccountInfoById(Long id);

    Account adminLogin(Account account);    //管理员登录

    Account userLogin(Account account);     //用户登录

    Account selectByUsername(Account account);  //根据用户名查询用户信息


    void register(Account account);   //注册用户

    PageInfo<AccountDetail> selectAccountInfoByLike(String keyword,int pageNum,int pageSize);

    void updatePassword(Account account);
    Account selectAccountById(Long id);
    int updateAccountInfoSelective(AccountInfo record);

    void updateAccountSelective(Account account);

    PageInfo<AccountInfo> selectAccountInfoByPage(int pageNum,int pageSize,AccountInfo accountInfo);

}

