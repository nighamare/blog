package com.lycoris.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.lycoris.domain.Comment;
import com.lycoris.domain.Tag;
import com.lycoris.model.HttpResult;
import com.lycoris.service.TagService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/14 23:55
 */
@RequestMapping("/tag")
@RestController
public class TagController {
    @Resource
    private TagService tagService;

    @GetMapping("/list")
    public HttpResult list() {
        return HttpResult.create(0,"查询成功！").set("data",tagService.list());
    }
    @GetMapping("/list10")
    public HttpResult list10() {
        return HttpResult.create(0,"查询成功！").set("data",tagService.list10());
    }
    @GetMapping("/articleTags")
    public HttpResult getArticleTags() {
        return HttpResult.create(0,"查询成功！").set("data",tagService.list());
    }

    @GetMapping("/listbp")
    public HttpResult listByPage(@RequestParam(defaultValue = "1")int pageNum, @RequestParam(defaultValue = "10")int pageSize, Tag tag){
        return HttpResult.create(0,"查询成功！").set("data", tagService.getListByPage(pageNum, pageSize, tag));
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/insert")
    public HttpResult insert(@RequestBody Tag tag){
        if (tag == null) {
            return HttpResult.create(200310,"添加失败！请重试！");
        }
        tagService.insertSelective(tag);
        return HttpResult.create(0,"添加成功！");
    }
    @SaCheckLogin
    @PutMapping("/update")
    public HttpResult update(@RequestBody Tag tag){
        if (tag == null) {
            return HttpResult.create(200310,"修改失败！请重试！");
        }
        tagService.updateByPrimaryKeySelective(tag);
        return HttpResult.create(0,"修改成功！");
    }
    @SaCheckLogin
    @DeleteMapping("/delete")
    public HttpResult deleteTag(Long[] ids) {
        if (ids.length == 0) {
            return HttpResult.create(202300, "删除失败！");
        } else {
            tagService.deleteByIds(ids);
            return HttpResult.create(0, "删除成功！");
        }
    }



}
