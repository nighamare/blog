package com.lycoris.service;

import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Article;
import com.lycoris.domain.Category;
import com.lycoris.domain.Tag;

import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 22:30
 */
public interface TagService extends BasicService<Tag, Long> {
    List<Tag> list();


    List<Tag> list10();

    PageInfo<Tag>  getListByPage(int pageNum, int pageSize, Tag tag);
}
