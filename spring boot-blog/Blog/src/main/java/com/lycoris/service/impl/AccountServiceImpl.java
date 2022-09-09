package com.lycoris.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Account;
import com.lycoris.domain.AccountInfo;
import com.lycoris.dto.AccountDetail;
import com.lycoris.mapper.AccountDetailMapper;
import com.lycoris.mapper.AccountInfoMapper;
import com.lycoris.mapper.AccountMapper;
import com.lycoris.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 14:57
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private AccountInfoMapper accountInfoMapper;
    @Resource
    private AccountDetailMapper accountDetailMapper;


    @Override
    public void insertSelective(AccountDetail accountDetail) {
        Account account = new Account();
        account.setId(accountDetail.getId()).setUsername(accountDetail.getUsername()).setPassword(accountDetail.getPassword()).
                setAccountType(accountDetail.getAccountType()).setStatus(accountDetail.getStatus()).setCreateTime(accountDetail.getCreateTime()).setLastUpdate(accountDetail.getLastUpdate());
        accountMapper.insertSelective(account);
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountId(account.getId()).setNickname(accountDetail.getNickname())
                .setAvatar(accountDetail.getAvatar()).setSex(accountDetail.getSex())
                .setPhoneNum(accountDetail.getPhoneNum()).setEmail(accountDetail.getEmail())
                .setAddress(accountDetail.getAddress()).setPersonalSignature(accountDetail.getPersonalSignature());
        accountInfoMapper.insertSelective(accountInfo);

    }

    @Override
    public void updateByPrimaryKeySelective(AccountDetail accountDetail) {
        Account account = new Account();
        account.setId(accountDetail.getId()).setUsername(accountDetail.getUsername()).setPassword(accountDetail.getPassword()).
                setAccountType(accountDetail.getAccountType()).setStatus(accountDetail.getStatus()).setCreateTime(accountDetail.getCreateTime()).setLastUpdate(accountDetail.getLastUpdate());
        accountMapper.updateByPrimaryKeySelective(account);
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountId(accountDetail.getId()).setNickname(accountDetail.getNickname())
                .setAvatar(accountDetail.getAvatar()).setSex(accountDetail.getSex())
                .setPhoneNum(accountDetail.getPhoneNum()).setEmail(accountDetail.getEmail())
                .setAddress(accountDetail.getAddress()).setPersonalSignature(accountDetail.getPersonalSignature());

        accountInfoMapper.updateByPrimaryKeySelective(accountInfo);

    }

    @Override
    public void deleteByIds(Long[] longs) {
        for (Long id : longs) {
            accountMapper.deleteByPrimaryKey(id);
            accountInfoMapper.deleteByPrimaryKey(id);
        }

    }

    @Override
    public void deleteById(Long aLong) {
        accountMapper.deleteByPrimaryKey(aLong);
        accountInfoMapper.deleteByPrimaryKey(aLong);

    }

    @Override
    public PageInfo<AccountDetail> selectByPageSelective(int pageNum, int pageSize, AccountDetail accountDetail) {
        Account account = new Account();
        AccountInfo accountInfo = new AccountInfo();
        if (accountDetail != null) {
            account.setId(accountDetail.getId()).setUsername(accountDetail.getUsername()).setPassword(accountDetail.getPassword()).
                    setAccountType(accountDetail.getAccountType()).setStatus(accountDetail.getStatus()).setCreateTime(accountDetail.getCreateTime()).setLastUpdate(accountDetail.getLastUpdate());
            accountInfo.setAccountId(accountDetail.getId()).setNickname(accountDetail.getNickname())
                    .setAvatar(accountDetail.getAvatar()).setSex(accountDetail.getSex())
                    .setPhoneNum(accountDetail.getPhoneNum()).setEmail(accountDetail.getEmail())
                    .setAddress(accountDetail.getAddress()).setPersonalSignature(accountDetail.getPersonalSignature());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<AccountDetail> accountDetails = accountDetailMapper.selectAll(accountDetail);
        return new PageInfo<>(accountDetails);

    }

    @Override
    public AccountDetail selectById(Long aLong) {

        return accountDetailMapper.selectByPrimaryKey(aLong);
    }

    @Override
    public AccountDetail selectOneByCondition(AccountDetail accountDetail) {

        return accountDetailMapper.selectOneByCondition(accountDetail);
    }

    @Override
    public AccountInfo selectAccountInfoById(Long id) {
        return accountInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public Account adminLogin(Account account) {
        account.setAccountType(1);
        return accountMapper.selectOneByCondition(account);
    }

    @Override
    public Account userLogin(Account account) {
        account.setAccountType(0);
        return accountMapper.selectOneByCondition(account);
    }

    @Override
    public Account selectByUsername(Account account) {

        return accountMapper.selectOneByCondition(account);
    }

    @Override
    public void register(Account account) {
        account.setAccountType(0);
        accountMapper.insertSelective(account);
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountId(account.getId());
        accountInfoMapper.insertSelective(accountInfo);
    }

    @Override
    public PageInfo<AccountDetail> selectAccountInfoByLike(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageSize, pageNum);
        return new PageInfo<>(accountDetailMapper.selectAllBySearch(keyword));
    }

    @Override
    public void updatePassword(Account account) {
        accountMapper.updateByPrimaryKeySelective(account);
    }

    @Override
    public Account selectAccountById(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateAccountInfoSelective(AccountInfo record) {
        return accountInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void updateAccountSelective(Account account) {
        accountMapper.updateByPrimaryKeySelective(account);
    }

    @Override
    public PageInfo<AccountInfo> selectAccountInfoByPage(int pageNum, int pageSize, AccountInfo accountInfo) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(accountInfoMapper.selectAccountInfoAll(accountInfo));
    }
}
