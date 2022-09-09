package com.lycoris.service;

import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Category;

import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 22:27
 */
public interface CategoryService extends BasicService<Category,Long>{

    List<Category> list();
    PageInfo<Category> getListByPage(int pageNum, int pageSize, Category category);


}
