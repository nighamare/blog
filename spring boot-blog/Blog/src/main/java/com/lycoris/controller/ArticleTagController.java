package com.lycoris.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.lycoris.domain.ArticleTag;
import com.lycoris.domain.Tag;
import com.lycoris.dto.ArticleTagDetail;
import com.lycoris.dto.ArticleTagInfo;
import com.lycoris.model.HttpResult;
import com.lycoris.service.ArticleTagService;
import com.lycoris.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/14 23:51
 */
@RequestMapping("/articleTag")
@RestController
public class ArticleTagController {
    @Resource
    private ArticleTagService articleTagService;

    @GetMapping("/list")
    public HttpResult getArticleTagList(ArticleTag articleTag) {
        List<ArticleTagDetail> articleTagList = articleTagService.getArticleTagList(articleTag.getArticleId());
        return HttpResult.create(0,"查询成功！").set("data",articleTagList);
    }
    @GetMapping("/artaglist")
    public HttpResult getArticleTagPage(@RequestParam(defaultValue = "1")int pageNum,@RequestParam(defaultValue = "10")int pageSize, ArticleTagDetail articleTagDetail) {
        return HttpResult.create(0,"查询成功！").set("data",articleTagService.getArticleTagByPage(pageNum,pageSize,articleTagDetail));
    }
    @SaCheckLogin
    @PostMapping("/handle")
    public HttpResult addArticleTag(String[] tagName,@RequestBody Long articleId){
        if (tagName == null) {
            return HttpResult.create(200303,"请重新选择标签！");
        }else{
            articleTagService.handleTagNames(tagName,articleId);
            return HttpResult.create(0,"保存成功！");
        }
    }
    @SaCheckLogin
    @PostMapping("/updateTag")
    public HttpResult updateArticleTagNames(String[] tagName,@RequestBody Long articleId){
        if (tagName == null) {
            return HttpResult.create(200303,"请重新选择标签！");
        }else{
            articleTagService.updateArticleTagNames(tagName,articleId);
            return HttpResult.create(0,"保存成功！");
        }
    }
    @GetMapping("/getTags/{articleId}")
    public HttpResult getArticleTagList(@PathVariable Long articleId) {
        List<ArticleTagDetail> articleTagList = articleTagService.getArticleTagList(articleId);
        return HttpResult.create(0,"查询成功！").set("data",articleTagList);
    }


}
