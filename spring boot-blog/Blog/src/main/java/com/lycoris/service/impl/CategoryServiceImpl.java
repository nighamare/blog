package com.lycoris.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Category;
import com.lycoris.mapper.CategoryMapper;
import com.lycoris.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 22:27
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void insertSelective(Category category) {
        categoryMapper.insertSelective(category);
    }

    @Override
    public void updateByPrimaryKeySelective(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void deleteByIds(Long[] longs) {
        categoryMapper.deleteByIds(longs);
    }

    @Override
    public void deleteById(Long aLong) {
        categoryMapper.deleteByPrimaryKey(aLong);
    }

    @Override
    public PageInfo<Category> selectByPageSelective(int pageNum, int pageSize, Category category) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(categoryMapper.selectAll(category));
    }

    @Override
    public Category selectById(Long aLong) {
        return categoryMapper.selectByPrimaryKey(aLong);
    }

    @Override
    public Category selectOneByCondition(Category category) {
        return categoryMapper.selectOneByCondition(category);
    }

    @Override
    public List<Category> list() {
        String s = (String) redisTemplate.opsForValue().get("category");
        List<Category> category = JSON.parseArray(s, Category.class);
        if (category == null) {
            List<Category> catg = categoryMapper.selectAll(null);
            String s1 = JSON.toJSONString(catg);
            this.redisTemplate.opsForValue().set("category", s1);
            return catg;
        } else {
            return category;
        }
    }

    @Override
    public PageInfo<Category> getListByPage(int pageNum, int pageSize, Category category) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(categoryMapper.selectAllByPage(category));
    }
}
