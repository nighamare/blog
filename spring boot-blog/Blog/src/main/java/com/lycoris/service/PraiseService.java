package com.lycoris.service;

import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Praise;
import com.lycoris.dto.PraiseInfo;

import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/18 9:18
 */
public interface PraiseService extends BasicService<Praise, Long> {
    /**
     * 查询点赞数量
     * @param praise Praise
     * @return int
     */
    int selectCount(Praise praise);

    PageInfo<PraiseInfo>  getListByPage(int pageNum, int pageSize, PraiseInfo praiseInfo);

    Boolean isLiked(Praise praise);


    List<Praise> getListByConditions(Long articleId, Long accountId);

}
