package com.lycoris.config;

import com.alibaba.fastjson.JSON;
import com.lycoris.domain.Article;
import com.lycoris.domain.Category;
import com.lycoris.domain.Tag;
import com.lycoris.mapper.ArticleMapper;
import com.lycoris.mapper.CategoryMapper;
import com.lycoris.mapper.CronMapper;
import com.lycoris.mapper.TagMapper;
import com.lycoris.service.ArticleService;
import com.lycoris.service.TagService;
import com.lycoris.util.CronUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/26 14:17
 */

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DynamicScheduleTask implements SchedulingConfigurer {
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private TagMapper tagMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Autowired      //注入mapper
    @SuppressWarnings("all")
    private CronMapper cronMapper;

    /**
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(() -> {
                    updateCategoryList();
                    updateLikeArticleList();
                    updateRecommendedArticleList();
                    updateHotArticleList();
                    updateTagList();
                },
                //1.添加任务内容(Runnable)
//                () -> System.out.println("执行动态定时任务: " + LocalDateTime.now().toLocalTime()),


                //2.设置执行周期(Trigger)
                triggerContext -> {

                    //2.1 从数据库获取执行周期
                    String cron = cronMapper.getCron();
                    //2.2 合法性校验.
                    if (StringUtils.hasLength(cron)) {
                        boolean b = CronUtil.checkValid(cron);
                        if (!b) {
                            cronMapper.updateCron();
                            configureTasks(taskRegistrar);
                        }
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }

    public void updateCategoryList() {
        String s = (String) redisTemplate.opsForValue().get("category");
        if (s != null) {
            redisTemplate.opsForValue().getOperations().delete("category");
        }
        List<Category> catg = categoryMapper.selectAll(null);
        String s1 = JSON.toJSONString(catg);
        this.redisTemplate.opsForValue().set("category", s1);
    }

    public void updateLikeArticleList() {
        String s = (String) redisTemplate.opsForValue().get("likeArticle");
        if (s != null) {
            redisTemplate.opsForValue().getOperations().delete("likeArticle");
        }
        List<Article> articles = articleMapper.asYouLike();
        String s1 = JSON.toJSONString(articles);
        this.redisTemplate.opsForValue().set("likeArticle", s1);
    }

    public void updateRecommendedArticleList() {
        String s = (String) redisTemplate.opsForValue().get("recommendedArticle");
        if (s != null) {
            redisTemplate.opsForValue().getOperations().delete("recommendedArticle");
        }
        List<Article> articles = articleMapper.recommendedByStationMaster();
        String s1 = JSON.toJSONString(articles);
        this.redisTemplate.opsForValue().set("recommendedArticle", s1);
    }

    public void updateHotArticleList() {
        String s = (String) redisTemplate.opsForValue().get("likeArticle");
        if (s != null) {
            redisTemplate.opsForValue().getOperations().delete("likeArticle");
        }
        List<Article> articles = articleMapper.selectHotArticles();
        String s1 = JSON.toJSONString(articles);
        this.redisTemplate.opsForValue().set("hotArticle", s1);
    }

    public void updateTagList() {
        String s = (String) redisTemplate.opsForValue().get("tags");
        if (s != null) {
            redisTemplate.opsForValue().getOperations().delete("tags");
        }
        List<Tag> tags1 = tagMapper.selectAll(null);
        String s1 = JSON.toJSONString(tags1);
        this.redisTemplate.opsForValue().set("tags", s1);
    }


}