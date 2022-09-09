package com.lycoris.service;

import com.github.pagehelper.PageInfo;
import com.lycoris.domain.FriendshipChain;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/22 17:38
 */
public interface FriendshipChainService extends BasicService<FriendshipChain,Long> {
    PageInfo<FriendshipChain> getListByPage(int pageNum, int pageSize, FriendshipChain friendshipChain);

    int selectFriendshipChainCountByStatus(Integer status);
}
