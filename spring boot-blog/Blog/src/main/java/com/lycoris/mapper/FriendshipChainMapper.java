package com.lycoris.mapper;

import com.lycoris.domain.FriendshipChain;

import java.util.List;

/**
* @author DELL
* @description 针对表【friendship_chain(友情链表)】的数据库操作Mapper
* @createDate 2022-08-26 16:07:13
* @Entity com.lycoris.domain.FriendshipChain
*/
public interface FriendshipChainMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FriendshipChain record);

    int insertSelective(FriendshipChain record);

    FriendshipChain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FriendshipChain record);

    int updateByPrimaryKey(FriendshipChain record);

    List<FriendshipChain> selectAll(FriendshipChain friendshipChain);

    List<FriendshipChain> selectAllByPage(FriendshipChain friendshipChain);

    int selectFriendshipChainCountByStatus(Integer status);

}
