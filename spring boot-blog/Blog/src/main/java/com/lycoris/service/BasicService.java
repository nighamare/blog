package com.lycoris.service;

import com.github.pagehelper.PageInfo;

/**
 * @author tsh
 * @version 1.0
 * @date 2022/3/24 10:17
 */
public interface BasicService<E, ID> {
    /**
     * 新增实体
     *
     * @param e 新增实体
     */
    void insertSelective(E e);

    /**
     * 修改实体
     *
     * @param e 新增实体
     */
    void updateByPrimaryKeySelective(E e);

    /**
     * 批量删除
     *
     * @param ids 新增实体
     */
    void deleteByIds(ID[] ids);

    /**
     * 根据主键删除实体
     *
     * @param id 主键
     */
    void deleteById(ID id);

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param e
     * @return com.github.pagehelper.PageInfo<E>
     * @author tsh
     * @creed: Talk is cheap,show me the code
     * @date 2022/3/24 10:27
     */
    PageInfo<E> selectByPageSelective(int pageNum, int pageSize, E e);

    /**
     * #Description 通过id查询实体
     *
     * @param id
     * @return E
     * @author tsh
     * @creed: Talk is cheap,show me the code
     * @date 2022/3/24 10:29
     */
    E selectById(ID id);

    /**
     * #Description 通过传入的条件查询单个对象
     *
     * @param e
     * @return E
     * @author tsh
     * @creed: Talk is cheap,show me the code
     * @date 2022/3/24 10:30
     */
    E selectOneByCondition(E e);


}

