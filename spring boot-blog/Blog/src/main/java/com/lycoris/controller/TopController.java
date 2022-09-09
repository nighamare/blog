package com.lycoris.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.lycoris.domain.Top;
import com.lycoris.model.HttpResult;
import com.lycoris.service.TopService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/29 23:20
 */
@RequestMapping("/top")
@RestController
public class TopController {
    @Resource
    private TopService topService;
    
    @GetMapping("/listbp")
    public HttpResult listByPage(@RequestParam(defaultValue = "1")int pageNum, @RequestParam(defaultValue = "10")int pageSize, Top top){
        return HttpResult.create(0,"查询成功！").set("data", topService.getListByPage(pageNum, pageSize, top));
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/insert")
    public HttpResult insert(@RequestBody Top top){
        if (top == null) {
            return HttpResult.create(200310,"添加失败！请重试！");
        }
        topService.insertSelective(top);
        return HttpResult.create(0,"添加成功！");
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/update")
    public HttpResult update(@RequestBody Top top){
        if (top == null) {
            return HttpResult.create(200310,"修改失败！请重试！");
        }
        topService.updateByPrimaryKeySelective(top);
        return HttpResult.create(0,"修改成功！");
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/delete")
    public HttpResult deleteTop(Long[] ids) {
        if (ids == null) {
            return HttpResult.create(202300, "删除失败！");
        } else {
            topService.deleteByIds(ids);
            return HttpResult.create(0, "删除成功！");
        }
    }
}
