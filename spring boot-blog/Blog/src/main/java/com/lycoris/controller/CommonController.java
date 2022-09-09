package com.lycoris.controller;

import com.alibaba.fastjson.JSON;
import com.lycoris.domain.Tag;
import com.lycoris.model.HttpResult;
import com.lycoris.service.ArticleService;
import com.lycoris.service.FriendshipChainService;
import com.lycoris.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/30 19:53
 */
@RequestMapping("/common")
@RestController
public class CommonController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ArticleService articleService;
    @Resource
    private FriendshipChainService friendshipChainService;
    @Resource
    private MessageService messageService;

    @GetMapping("/count")
    public HttpResult getCount(){
//        待审核文章数量
        int i = articleService.selectArticleCountByStatus(3);
        int i1 = articleService.selectArticleCount();
//        待审核友链
        int i2 = friendshipChainService.selectFriendshipChainCountByStatus(2);
//        待查看消息
        int i3 = messageService.selectCountByStatus(0);
        Map<String, Integer> map = new HashMap<>(16);
        map.put("auditArticle",i);
        map.put("auditFriendLink",i2);
        map.put("allArticle",i1);
        map.put("auditMessage",i3);
        return HttpResult.create(0,"查询成功！").set("data",map);
    }
    @GetMapping("/test")
    public HttpResult getTest(){
        String stringTags = (String) redisTemplate.opsForValue().get("tags");
        List<Tag> tags = JSON.parseArray(stringTags, Tag.class);
        System.out.println("stringTags = " + stringTags);
        return HttpResult.create(0,"查询成功！").set("data",tags);
    }




}
