package com.lycoris.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Top;
import com.lycoris.mapper.TopMapper;
import com.lycoris.service.TopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/30 0:22
 */
@Service
public class TopServiceImpl implements TopService {
    @Resource
    private TopMapper topMapper;

    @Override
    public void insertSelective(Top top) {
        topMapper.insertSelective(top);
    }

    @Override
    public void updateByPrimaryKeySelective(Top top) {
        topMapper.updateByPrimaryKeySelective(top);
    }

    @Override
    public void deleteByIds(Long[] longs) {
      topMapper.deleteByIds(longs);
    }

    @Override
    public void deleteById(Long aLong) {
        topMapper.deleteByPrimaryKey(aLong);
    }

    @Override
    public PageInfo<Top> selectByPageSelective(int pageNum, int pageSize, Top top) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(topMapper.selectAllByPage(top));
    }

    @Override
    public Top selectById(Long aLong) {
        return topMapper.selectByPrimaryKey(aLong);
    }

    @Override
    public Top selectOneByCondition(Top top) {
        return topMapper.selectOne(top);
    }

    @Override
    public PageInfo<Top> getListByPage(int pageNum, int pageSize, Top top) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(topMapper.selectAllByPage(top));
    }
}
