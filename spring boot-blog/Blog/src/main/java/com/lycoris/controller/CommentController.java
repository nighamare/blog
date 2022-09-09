package com.lycoris.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.lycoris.domain.Article;
import com.lycoris.domain.Comment;
import com.lycoris.domain.Praise;
import com.lycoris.dto.CommentReply;
import com.lycoris.dto.PraiseQuery;
import com.lycoris.model.HttpResult;
import com.lycoris.service.CommentService;
import com.lycoris.service.PraiseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/17 15:23
 */
@RequestMapping("/comment")
@RestController
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private PraiseService praiseService;


    @GetMapping("/list/{articleId}")
    public HttpResult getCommentList(@PathVariable Long articleId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        int count = commentService.selectCount(comment);
        return HttpResult.create(0, "查询成功！").set("data", commentService.getCommentPageList(articleId, pageNum, pageSize)).set("count", count);
    }

    @GetMapping("/listbp")
    public HttpResult getCommentByPage(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, CommentReply commentReply) {
        return HttpResult.create(0, "查询成功！").set("data", commentService.getAllCommentByPage(pageNum, pageSize, commentReply));
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/add")
    public HttpResult addComment(@RequestBody Comment comment) {
        commentService.insertSelective(comment);
        return HttpResult.create(0, "添加成功！");
    }

    @GetMapping("/replies/{commentId}")
    public HttpResult getRepliesCommentList(@PathVariable Long commentId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize) {

        return HttpResult.create(0, "查询成功！").set("data", commentService.getListRepliesByCommentId(commentId, pageNum, pageSize));
    }
    @SaCheckLogin
    @PutMapping("/update")
    public HttpResult updateComment(@RequestBody Comment comment) {
        if (comment == null) {
            return HttpResult.create(202300, "修改失败！");
        } else {
            commentService.updateByPrimaryKeySelective(comment);
            return HttpResult.create(0, "修改成功！");
        }
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/delete")
    public HttpResult deleteComment(Long[] ids) {
        if (ids.length == 0) {
            return HttpResult.create(202300, "删除失败！");
        } else {
            commentService.deleteByIds(ids);
            for (Long id : ids) {
                List<Comment> comments1 = commentService.selectByRootCommentId(id);
                List<Comment> comments = commentService.selectByReplyCommentId(id);
                if (comments != null) {
                    for (Comment comment : comments) {
                        commentService.deleteById(comment.getId());
                    }
                }
                if (comments1 !=null){
                    for (Comment comment : comments1) {
                        commentService.deleteById(comment.getId());
                    }
                }
            }
            return HttpResult.create(0, "删除成功！");
        }
    }
    @SaCheckLogin
    @PostMapping("/likes")
    public HttpResult handleLikes(@RequestBody PraiseQuery praiseQuery) {
        if (praiseQuery == null) {
            return HttpResult.create(20001, "Invalid account id");
        } else {
            Long id = praiseQuery.getId();
            Long accountId = praiseQuery.getAccountId();
            boolean liked = praiseQuery.getIsLiked();
            Comment comment = commentService.selectById(id);
            Comment comment1 = new Comment();
            comment1.setId(id);
            Integer praiseNum = comment.getPraiseNum();

            Praise praise = new Praise();
            praise.setAccountId(accountId);
            praise.setPraiseType(1);
            praise.setPraiseForId(id);
            Praise praise1 = praiseService.selectOneByCondition(praise);
            if (!liked) {
                if (praise1 != null) {
                    praiseService.deleteById(praise1.getId());
                }
                comment1.setPraiseNum(praiseNum+1);
            } else {
                if (praise1 == null) {
                    praiseService.insertSelective(praise);
                }
                comment1.setPraiseNum(praiseNum-1);
            }
            commentService.updateByPrimaryKeySelective(comment1);
            return HttpResult.create(0, "success");
        }
    }


}
