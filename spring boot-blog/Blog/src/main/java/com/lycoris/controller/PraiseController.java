package com.lycoris.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.lycoris.domain.Praise;
import com.lycoris.dto.PraiseInfo;
import com.lycoris.model.HttpResult;
import com.lycoris.service.PraiseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/29 23:20
 */
@RequestMapping("/praise")
@RestController
public class PraiseController {
    @Resource
    private PraiseService praiseService;

    @GetMapping("/listbp")
    public HttpResult listByPage(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, PraiseInfo praiseInfo) {
        return HttpResult.create(0, "查询成功！").set("data", praiseService.getListByPage(pageNum, pageSize, praiseInfo));
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/insert")
    public HttpResult insert(@RequestBody Praise praise) {
        if (praise == null) {
            return HttpResult.create(200310, "添加失败！请重试！");
        }
        praiseService.insertSelective(praise);
        return HttpResult.create(0, "添加成功！");
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/update")
    public HttpResult update(@RequestBody Praise praise) {
        if (praise == null) {
            return HttpResult.create(200310, "修改失败！请重试！");
        }
        praiseService.updateByPrimaryKeySelective(praise);
        return HttpResult.create(0, "修改成功！");
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/delete")
    public HttpResult deletePraiseInfo(Long[] ids) {
        if (ids == null) {
            return HttpResult.create(202300, "删除失败！");
        } else {
            praiseService.deleteByIds(ids);
            return HttpResult.create(0, "删除成功！");
        }
    }

    @GetMapping("/getPraiseList")
    public HttpResult getPraiseList(Long articleId, Long accountId) {
        if (articleId == null || accountId == null) {
            return HttpResult.create(202300, "请求不能为空！");
        } else {
            List<Praise> listByConditions = praiseService.getListByConditions(articleId, accountId);
            List<Long> ids = new ArrayList<>();
            for (Praise listByCondition : listByConditions) {
                ids.add(listByCondition.getId());
            }
            return HttpResult.create(0, "请求不能为空！").set("data", listByConditions).set("list", ids);
        }

    }

}
