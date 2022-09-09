package com.lycoris.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Tag;
import com.lycoris.mapper.TagMapper;
import com.lycoris.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 22:31
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertSelective(Tag tag) {
        tagMapper.insertSelective(tag);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateByPrimaryKeySelective(Tag tag) {
        tagMapper.updateByPrimaryKeySelective(tag);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(Long[] longs) {
        tagMapper.deleteByIds(longs);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Long aLong) {
        tagMapper.deleteByPrimaryKey(aLong);
    }

    @Override
    public PageInfo<Tag> selectByPageSelective(int pageNum, int pageSize, Tag tag) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(tagMapper.selectAll(tag));
    }

    @Override
    public Tag selectById(Long aLong) {
        return tagMapper.selectByPrimaryKey(aLong);
    }

    @Override
    public Tag selectOneByCondition(Tag tag) {
        return tagMapper.selectOneByCondition(tag);
    }

    @Override
    public List<Tag> list() {
        String s = (String) redisTemplate.opsForValue().get("tags");
        List<Tag> tags = JSON.parseArray(s, Tag.class);
        if (tags == null) {
            List<Tag> tags1 = tagMapper.selectAll(null);
            String s1 = JSON.toJSONString(tags1);
            this.redisTemplate.opsForValue().set("tags", s1);
            return tags1;
        } else {
            return tags;
        }

    }

    @Override
    public List<Tag> list10() {
        String s = (String) redisTemplate.opsForValue().get("tags10");
        List<Tag> tags = JSON.parseArray(s, Tag.class);
        if (tags == null) {
            List<Tag> tags1 = tagMapper.selectAll10(null);
            String s1 = JSON.toJSONString(tags1);
            this.redisTemplate.opsForValue().set("tags10", s1);
            return tags1;
        } else {
            return tags;
        }
    }

    @Override
    public PageInfo<Tag> getListByPage(int pageNum, int pageSize, Tag tag) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(tagMapper.selectAllByPage(tag));
    }

}
