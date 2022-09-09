package com.lycoris.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lycoris.domain.FriendshipChain;
import com.lycoris.mapper.FriendshipChainMapper;
import com.lycoris.service.FriendshipChainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/22 17:40
 */
@Service
public class FriendshipChainServiceImpl implements FriendshipChainService {
    @Resource
    private FriendshipChainMapper friendshipChainMapper;
    @Override
    public void insertSelective(FriendshipChain friendshipChain) {
        friendshipChainMapper.insertSelective(friendshipChain);
    }
    @Override
    public void updateByPrimaryKeySelective(FriendshipChain friendshipChain) {
        friendshipChainMapper.updateByPrimaryKeySelective(friendshipChain);
    }
    @Override
    public void deleteByIds(Long[] integers) {
        for (Long integer : integers) {
            friendshipChainMapper.deleteByPrimaryKey(integer);
        }
    }
    @Override
    public void deleteById(Long integer) {
        friendshipChainMapper.deleteByPrimaryKey(integer);
    }

    @Override
    public PageInfo<FriendshipChain> selectByPageSelective(int pageNum, int pageSize, FriendshipChain friendshipChain) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(friendshipChainMapper.selectAll(friendshipChain));
    }

    @Override
    public FriendshipChain selectById(Long integer) {
        return friendshipChainMapper.selectByPrimaryKey(integer);
    }

    @Override
    public FriendshipChain selectOneByCondition(FriendshipChain friendshipChain) {
        return null;
    }

    @Override
    public PageInfo<FriendshipChain> getListByPage(int pageNum, int pageSize, FriendshipChain friendshipChain) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(friendshipChainMapper.selectAllByPage(friendshipChain));
    }

    @Override
    public int selectFriendshipChainCountByStatus(Integer status) {
        return friendshipChainMapper.selectFriendshipChainCountByStatus(status);
    }


}
