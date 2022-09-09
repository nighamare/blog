package com.lycoris.service;

import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Top;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/30 0:17
 */
public interface TopService extends BasicService<Top,Long>{
    PageInfo<Top> getListByPage(int pageNum, int pageSize, Top top);
}
