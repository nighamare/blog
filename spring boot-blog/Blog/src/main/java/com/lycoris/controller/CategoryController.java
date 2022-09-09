package com.lycoris.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.lycoris.domain.Category;
import com.lycoris.model.HttpResult;
import com.lycoris.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/14 23:39
 */
@RequestMapping("/category")
@RestController
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public HttpResult list(){
        return HttpResult.create(0,"查询成功！").set("data", categoryService.list());
    }
    @GetMapping("/listbp")
    public HttpResult listByPage(@RequestParam(defaultValue = "1")int pageNum, @RequestParam(defaultValue = "10")int pageSize, Category category){
        return HttpResult.create(0,"查询成功！").set("data", categoryService.getListByPage(pageNum, pageSize, category));
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/insert")
    public HttpResult insert(@RequestBody Category category){
        if (category == null) {
            return HttpResult.create(200310,"添加失败！请重试！");
        }
        categoryService.insertSelective(category);
        return HttpResult.create(0,"添加成功！");
    }
    @SaCheckLogin
    @PutMapping("/update")
    public HttpResult update(@RequestBody Category category){
        if (category == null) {
            return HttpResult.create(200310,"修改失败！请重试！");
        }
        categoryService.updateByPrimaryKeySelective(category);
        return HttpResult.create(0,"修改成功！");
    }
    @SaCheckLogin
    @DeleteMapping("/delete")
    public HttpResult deleteTag(Long[] ids) {
        if (ids.length == 0) {
            return HttpResult.create(202300, "删除失败！");
        } else {
            categoryService.deleteByIds(ids);
            return HttpResult.create(0, "删除成功！");
        }
    }

}
