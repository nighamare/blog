package com.lycoris.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Comment;
import com.lycoris.domain.Praise;
import com.lycoris.dto.PraiseInfo;
import com.lycoris.mapper.CommentMapper;
import com.lycoris.mapper.PraiseMapper;
import com.lycoris.service.PraiseService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/18 9:19
 */
@Service
public class PraiseServiceImpl implements PraiseService {
    @Resource
    private PraiseMapper praiseMapper;
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertSelective(Praise praise) {
        praiseMapper.insertSelective(praise);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateByPrimaryKeySelective(Praise praise) {
        praiseMapper.updateByPrimaryKeySelective(praise);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(Long[] longs) {
        praiseMapper.deleteByIds(longs);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Long aLong) {
        praiseMapper.deleteByPrimaryKey(aLong);
    }

    @Override
    public PageInfo<Praise> selectByPageSelective(int pageNum, int pageSize, Praise praise) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(praiseMapper.selectAll(praise));
    }

    @Override
    public Praise selectById(Long aLong) {
        Object o = redisTemplate.opsForValue().get("praise" + aLong);
        if (o != null) {
            return (Praise) o;
        } else {
            Praise praise = praiseMapper.selectByPrimaryKey(aLong);
            redisTemplate.opsForValue().set("praise:" + aLong, praise);
            return praise;
        }
    }

    @Override
    public Praise selectOneByCondition(Praise praise) {
        return praiseMapper.selectOneByCondition(praise);
    }

    @Override
    public int selectCount(Praise praise) {
        Object o = redisTemplate.opsForValue().get("praise:" + praise.getPraiseForId());
        if (o == null) {
            int i = praiseMapper.selectCount(praise);
            redisTemplate.opsForValue().set("praiseCount:" + praise.getPraiseForId(), i);
            return i;

        } else {
            return (int) o;
        }

    }

    @Override
    public PageInfo<PraiseInfo> getListByPage(int pageNum, int pageSize, PraiseInfo praiseInfo) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(praiseMapper.selectAllByPage(praiseInfo));
    }

    @Override
    public Boolean isLiked(Praise praise) {
        Praise praise1 = praiseMapper.selectOneByCondition(praise);
        return praise1 != null;
    }

    @Override
    public List<Praise> getListByConditions(Long articleId, Long accountId) {
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        List<Comment> comments = commentMapper.selectAll(comment);
        List<Long> ids = new ArrayList<>();
        for (Comment comment1 : comments) {
            ids.add(comment1.getId());
        }
        return praiseMapper.selectPraiseList(accountId, ids);
    }
}
