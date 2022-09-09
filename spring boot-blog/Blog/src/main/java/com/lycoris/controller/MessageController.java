package com.lycoris.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.lycoris.domain.Message;
import com.lycoris.domain.Message;
import com.lycoris.dto.MessageInfo;
import com.lycoris.model.HttpResult;
import com.lycoris.service.MessageService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/22 15:12
 */
@RequestMapping("/messages")
@RestController
public class MessageController {

    @Resource
    private MessageService messageService;

    @GetMapping("/list")
    public HttpResult list() {
        return HttpResult.create(0, "查询成功！").set("data", messageService.selectAllMessageInfo(null));
    }
    @SaCheckLogin
    @PostMapping("/add")
    public HttpResult add(@RequestBody Message message) {
        messageService.insertSelective(message);
        return HttpResult.create(0, "添加成功！");
    }
    @SaCheckLogin
    @GetMapping("/listbp")
    public HttpResult listByPage(@RequestParam(defaultValue = "1")int pageNum, @RequestParam(defaultValue = "10")int pageSize, Message message){
        return HttpResult.create(0,"查询成功！").set("data", messageService.getListByPage(pageNum, pageSize, message));
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/insert")
    public HttpResult insert(@RequestBody Message message){
        if (message == null) {
            return HttpResult.create(200310,"添加失败！请重试！");
        }
        messageService.insertSelective(message);
        return HttpResult.create(0,"添加成功！");
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/update")
    public HttpResult update(@RequestBody Message message){
        if (message == null) {
            return HttpResult.create(200310,"修改失败！请重试！");
        }
        messageService.updateByPrimaryKeySelective(message);
        return HttpResult.create(0,"修改成功！");
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/delete")
    public HttpResult deleteMessage(Long[] ids) {
        if (ids.length == 0) {
            return HttpResult.create(202300, "删除失败！");
        } else {
            messageService.deleteByIds(ids);
            return HttpResult.create(0, "删除成功！");
        }
    }


}
