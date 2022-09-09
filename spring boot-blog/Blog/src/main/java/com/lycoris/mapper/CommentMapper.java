package com.lycoris.mapper;

import com.lycoris.domain.Comment;
import com.lycoris.dto.CommentInfo;
import com.lycoris.dto.CommentReply;
import com.lycoris.dto.ReplyCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author DELL
* @description 针对表【comment(评论信息表)】的数据库操作Mapper
* @createDate 2022-08-17 15:28:40
* @Entity com.lycoris.domain.Comment
*/
public interface CommentMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectAll(Comment comment);
    Comment selectOneByCondition(Comment comment);
    void deleteByIds(@Param("ids") Long[] ids);
    int selectCount(Comment comment);

    /**
     * 查看评论
     *
     * @param articleId 文章id
     * @return 评论集合
     */
    List<CommentInfo> getCommentList(@Param("articleId") Long articleId);
    /**
     * 查看评论id集合下的回复
     *
     * @param commentIdList 评论id集合
     * @return 回复集合
     */
    List<CommentReply> getReplyList(@Param("commentIdList") List<Long> commentIdList);

    /**
     * 查看当条评论下的回复
     *
     * @param commentId 评论id
     * @return 回复集合
     */
    List<CommentReply> listRepliesByCommentId(@Param("commentId") Long commentId);

    /**
     * 根据评论id查询回复总量
     *
     * @param commentIdList 评论id集合
     * @return 回复数量
     */
    List<ReplyCount> listReplyCountByCommentId(@Param("commentIdList") List<Long> commentIdList);

    List<CommentReply> getCommentsByPage(CommentReply commentReply);

    List<Comment> selectByRootCommentId(Long id);
    List<Comment> selectByReplyCommentId(Long id);



}
