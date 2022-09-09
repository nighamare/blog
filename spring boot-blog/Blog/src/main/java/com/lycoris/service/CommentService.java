package com.lycoris.service;

import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Comment;
import com.lycoris.dto.CommentInfo;
import com.lycoris.dto.CommentReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/17 15:25
 */
public interface CommentService extends BasicService<Comment,Long>{
    /**
     * 查询评论列表
     * @param articleId 文章id
     * @return List
     */
    PageInfo<CommentInfo> getCommentPageList(Long articleId, int pageNum, int pageSize);

    /**
     * 查询评论数量
     * @param comment Comment
     * @return int
     */
    int selectCount(Comment comment);

    PageInfo<CommentReply> getListRepliesByCommentId(Long commentId, int pageNum, int pageSize);


    PageInfo<CommentReply> getAllCommentByPage(int pageNum, int pageSize, CommentReply commentReply);

    List<Comment> selectByRootCommentId(Long id);
    List<Comment> selectByReplyCommentId(Long id);

}