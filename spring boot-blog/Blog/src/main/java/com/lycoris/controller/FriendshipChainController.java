package com.lycoris.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.lycoris.domain.FriendshipChain;
import com.lycoris.model.HttpResult;
import com.lycoris.service.FriendshipChainService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/22 17:44
 */
@RequestMapping("friend")
@RestController
public class FriendshipChainController {
    @Resource
    private FriendshipChainService friendshipChainService;
    @GetMapping("/list")
    public HttpResult list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "12") int pageSize) {
        return HttpResult.create(0, "查询成功！").set("data", friendshipChainService.selectByPageSelective(pageNum, pageSize, null));
    }
    @SaCheckLogin
    @PostMapping("/apply")
    public HttpResult applyFriendship(@RequestBody FriendshipChain friendshipChain){
        if(friendshipChain==null){
            return HttpResult.create(200230,"请输入正确的信息！");
        }else{
            friendshipChainService.insertSelective(friendshipChain);
            return HttpResult.create(0,"申请成功，请等待审核！");
        }

    }

    @GetMapping("/listbp")
    public HttpResult listByPage(@RequestParam(defaultValue = "1")int pageNum, @RequestParam(defaultValue = "10")int pageSize, FriendshipChain friendshipChain){
        return HttpResult.create(0,"查询成功！").set("data", friendshipChainService.getListByPage(pageNum, pageSize, friendshipChain));
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/insert")
    public HttpResult insert(@RequestBody FriendshipChain friendshipChain){
        if (friendshipChain == null) {
            return HttpResult.create(200310,"添加失败！请重试！");
        }
        friendshipChainService.insertSelective(friendshipChain);
        return HttpResult.create(0,"添加成功！");
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/update")
    public HttpResult update(@RequestBody FriendshipChain friendshipChain){
        if (friendshipChain == null) {
            return HttpResult.create(200310,"修改失败！请重试！");
        }
        friendshipChainService.updateByPrimaryKeySelective(friendshipChain);
        return HttpResult.create(0,"修改成功！");
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/delete")
    public HttpResult deleteFriendshipChain(Long[] ids) {
        if (ids.length == 0) {
            return HttpResult.create(202300, "删除失败！");
        } else {
            friendshipChainService.deleteByIds(ids);
            return HttpResult.create(0, "删除成功！");
        }
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/passaudit/{id}")
    public HttpResult passAudit(@PathVariable Integer id) {
        if (id == null) {
            return HttpResult.create(200203, "审核失败！请选择有效文章！");
        } else {
            FriendshipChain friendshipChain = new FriendshipChain();
            friendshipChain.setId(id);
            friendshipChain.setStatus(0);
            friendshipChainService.updateByPrimaryKeySelective(friendshipChain);
            return HttpResult.create(0, "审核成功！");
        }
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/allpass")
    public HttpResult passAllAudit(Integer[] ids) {
        if (ids == null) {
            return HttpResult.create(200203, "审核失败！请选择有效文章！");
        } else {
            for (Integer id : ids) {
                FriendshipChain friendshipChain = new FriendshipChain();
                friendshipChain.setId(id);
                friendshipChain.setStatus(0);
                friendshipChainService.updateByPrimaryKeySelective(friendshipChain);
            }
            return HttpResult.create(0, "审核成功！");
        }
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/delete/{id}")
    public HttpResult deleteFriendshipChainById(@PathVariable Long id) {
        if (id == null) {
            return HttpResult.create(200030, "删除失败！请重试");
        } else {
            friendshipChainService.deleteById(id);
            return HttpResult.create(0, "删除成功！");
        }
    }

}
