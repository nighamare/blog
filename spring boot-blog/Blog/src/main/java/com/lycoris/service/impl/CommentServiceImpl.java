package com.lycoris.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Comment;
import com.lycoris.dto.CommentInfo;
import com.lycoris.dto.CommentReply;
import com.lycoris.dto.ReplyCount;
import com.lycoris.mapper.CommentMapper;
import com.lycoris.service.CommentService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/17 15:26
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void insertSelective(Comment comment) {
        if (comment.getRootCommentId() == null) {
            comment.setRootCommentId((long) -1);
        }
//        一级评论文章下的评论
        if (comment.getRootCommentId()==-1) {
            comment.setCommentLevel(1);
        }else if (comment.getRootCommentId().equals(comment.getReplyCommentId())){
//            二级评论，回复文章的评论
            comment.setCommentLevel(2);
        }else {
//            三级评论，回复二级/三级评论的评论
            comment.setCommentLevel(3);
        }
        commentMapper.insertSelective(comment);
    }

    @Override
    public void updateByPrimaryKeySelective(Comment comment) {
        commentMapper.updateByPrimaryKeySelective(comment);
    }

    @Override
    public void deleteByIds(Long[] longs) {
        commentMapper.deleteByIds(longs);
    }

    @Override
    public void deleteById(Long aLong) {
        commentMapper.deleteByPrimaryKey(aLong);
    }

    @Override
    public PageInfo<Comment> selectByPageSelective(int pageNum, int pageSize, Comment comment) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(commentMapper.selectAll(comment));
    }

    @Override
    public Comment selectById(Long aLong) {
        return commentMapper.selectByPrimaryKey(aLong);
    }

    @Override
    public Comment selectOneByCondition(Comment comment) {
        return commentMapper.selectOneByCondition(comment);
    }

    @Override
    public PageInfo<CommentInfo> getCommentPageList(Long articleId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommentInfo> commentList = commentMapper.getCommentList(articleId);
        // 提取评论id集合
        List<Long> commentIdList = new ArrayList<>();
        commentList.forEach(comment -> {
            commentIdList.add(comment.getId());
        });
        // 提取评论的回复集合
        List<CommentReply> replyList = commentMapper.getReplyList(commentIdList);
        // 根据评论id分组回复数据
        Map<Long, List<CommentReply>> replyMap = replyList.stream().collect(Collectors.groupingBy(CommentReply::getRootCommentId));
        List<ReplyCount> replyCounts = commentMapper.listReplyCountByCommentId(commentIdList);
        Map<Long, Integer> replyCountMap = replyCounts.stream().collect(Collectors.toMap(ReplyCount::getCommentId, ReplyCount::getReplyCount));
        // 将回复数据添加到评论中
        commentList.forEach(comment -> {
            comment.setCommentReplyList(replyMap.get(comment.getId()));
            comment.setReplyCount(replyCountMap.get(comment.getId()));
        });

        return new PageInfo<>(commentList);
    }

    @Override
    public int selectCount(Comment comment) {
        return commentMapper.selectCount(comment);
    }

    @Override
    public PageInfo<CommentReply> getListRepliesByCommentId(Long commentId,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(commentMapper.listRepliesByCommentId(commentId));
    }

    @Override
    public PageInfo<CommentReply> getAllCommentByPage(int pageNum, int pageSize, CommentReply commentReply) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(commentMapper.getCommentsByPage(commentReply));
    }

    @Override
    public List<Comment> selectByRootCommentId(Long id) {
        return commentMapper.selectByRootCommentId(id);
    }

    @Override
    public List<Comment> selectByReplyCommentId(Long id) {
        return commentMapper.selectByReplyCommentId(id);
    }


}
