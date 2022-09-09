package com.lycoris.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Article;
import com.lycoris.domain.ArticleTag;
import com.lycoris.domain.Praise;
import com.lycoris.dto.ArticlesInfo;
import com.lycoris.dto.PraiseQuery;
import com.lycoris.model.HttpResult;
import com.lycoris.service.AccountService;
import com.lycoris.service.ArticleService;
import com.lycoris.service.ArticleTagService;
import com.lycoris.service.PraiseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/14 22:58
 */
@RequestMapping("/article")
@RestController
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private AccountService accountService;
    @Resource
    private ArticleTagService articleTagService;
    @Value("${file-upload.upload-path}")
    private String uploadPath;

    @Resource
    private PraiseService praiseService;

    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/write")
    public HttpResult add(@RequestBody Article article) {
        articleService.insertSelective(article);
        return HttpResult.create(0, "文章发表成功！").set("data", article);
    }

    @GetMapping("/{id}")
    public HttpResult getArticleById(@PathVariable Long id) {
        return HttpResult.create(0, "查询成功！").set("data", articleService.selectById(id));
    }

    @GetMapping("/similar/{id}")
    public HttpResult getSimilarArticle(@PathVariable Long id) {
        return HttpResult.create(0, "查询成功！").set("data", articleService.getSimilarArticle(id));
    }

    @GetMapping("/list")
    public HttpResult getArticles(ArticlesInfo articlesInfo, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return HttpResult.create(0, "查询成功！").set("data", articleService.selectArticlesInfoByPageSelective(pageNum, pageSize, articlesInfo));
    }

    @GetMapping("/page")
    public HttpResult getArticlesByPage(Article article, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return HttpResult.create(0, "查询成功！").set("data", articleService.selectArticlesByPage(pageNum, pageSize, article));
    }

    @GetMapping("/hot")
    public HttpResult getHotArticles() {
        return HttpResult.create(0, "查询成功！").set("data", articleService.selectHotArticles());
    }

    @GetMapping("/asLike")
    public HttpResult getLikeArticles() {
        return HttpResult.create(0, "查询成功！").set("data", articleService.selectHotArticles());
    }

    @GetMapping("/recommend")
    public HttpResult getRecommendArticles() {
        return HttpResult.create(0, "查询成功！").set("data", articleService.selectHotArticles());
    }

    @GetMapping("/category/{id}")
    public HttpResult getArticlesByCategory(@PathVariable Long id, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        ArticlesInfo articlesInfo = new ArticlesInfo();
        articlesInfo.setCategoryId(id);
        return HttpResult.create(0, "查询成功！").set("data", articleService.selectArticlesInfoByPageSelective(pageNum, pageSize, articlesInfo));
    }

    @GetMapping("/search")
    public HttpResult getSearchedArticles(String keywords, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        if (keywords == null) {
            return HttpResult.create(200021, "查询失败！");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("articles", articleService.selectAllArticlesInfoByLike(keywords, pageNum, pageSize));
        map.put("users", accountService.selectAccountInfoByLike(keywords, pageNum, pageSize));

        return HttpResult.create(0, "查询成功！").set("data", map);
    }

    @GetMapping("/user/{accountId}")
    public HttpResult getUserArticles(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize, @PathVariable Long accountId) {
        if (accountId == null) {
            return HttpResult.create(123498, "查询错误！");
        }
        return HttpResult.create(0, "查询成功").set("data", articleService.selectArticlesInfoByPageAccountId(pageNum, pageSize, accountId));
    }
    @GetMapping("/tag")
    public HttpResult getSearchedArticlesByTag(Long tagId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        if (tagId == null) {
            return HttpResult.create(200021, "查询失败！");
        }
        ArticleTag articleTag = new ArticleTag();
        articleTag.setTagId(tagId);
        List<ArticleTag> articleTags = articleTagService.selectArticleTagByPage(pageNum, pageSize, articleTag).getList();
        List<ArticlesInfo> list = new ArrayList<>();
        for (ArticleTag tag : articleTags) {
            ArticlesInfo articlesInfo = articleService.selectOneArticleInfoByArticleId(tag.getArticleId());
            if (articlesInfo != null) {
                list.add(articlesInfo);
            }
        }
        return HttpResult.create(0, "查询成功！").set("data", new PageInfo<>(list));
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/delete/{id}")
    public HttpResult deleteArticleById(@PathVariable Long id) {
        if (id == null) {
            return HttpResult.create(200030, "删除失败！请重试");
        } else {
            articleService.deleteById(id);
            return HttpResult.create(0, "删除成功！");
        }
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/deleteByIds")
    public HttpResult deleteByIds(Long[] ids) {
        if (ids == null) {
            return HttpResult.create(200030, "删除失败！请重试");
        } else {
            articleService.deleteByIds(ids);
            return HttpResult.create(0, "删除成功！");
        }
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/update")
    public HttpResult updateById(@RequestBody Article article) {
        articleService.updateByPrimaryKeySelective(article);
        return HttpResult.create(0, "修改成功！");
    }


    @GetMapping("/audit")
    public HttpResult getAuditArticlesByPage(Article article, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return HttpResult.create(0, "查询成功！").set("data", articleService.selectAuditArticlesByPage(pageNum, pageSize, article));
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/passaudit/{id}")
    public HttpResult passAudit(@PathVariable Long id) {
        if (id == null) {
            return HttpResult.create(200203, "修改失败！请选择有效文章！");
        } else {
            Article article = new Article();
            article.setId(id);
            article.setStatus(0);
            articleService.updateByPrimaryKeySelective(article);
            return HttpResult.create(0, "修改成功！");
        }
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/allpass")
    public HttpResult passAllAudit(Long[] ids) {
        if (ids == null) {
            return HttpResult.create(200203, "修改失败！请选择有效文章！");
        } else {
            for (Long id : ids) {
                Article article = new Article();
                article.setId(id);
                article.setStatus(0);
                articleService.updateByPrimaryKeySelective(article);
            }
            return HttpResult.create(0, "修改成功！");
        }
    }
    @SaCheckLogin
    @PostMapping("/uploadImg")
    public HttpResult updateAvatar(@RequestParam MultipartFile file) {
        /*start*/
        if (!file.isEmpty()) {
            if (uploadPath == null) {
                return HttpResult.create(500, "服务器内部错误！请与管理人员联系！");
            } else {
                // 如果目录不存在则创建
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    boolean mkdir = uploadDir.mkdir();
                    if (!mkdir) {
                        return HttpResult.create(500, "服务器内部错误！请与管理人员联系！");
                    }
                }
            }
            String originalFilename = file.getOriginalFilename();
            //获取原文件名
            if (originalFilename == null) {
                return HttpResult.create(200010, "原文件名为空，上传失败，请重试");
            } else {
                //获取文件后缀名
                String suffixName = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase(Locale.ROOT);


                //HuTool 工具 重新UUID+名字
                String filename = IdUtil.simpleUUID() + suffixName;

                //把上传的文件保存至本地
                try {
                    file.transferTo(Paths.get(uploadPath + filename));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*
                 这里应该把filename保存到数据库,供前端访问时使用
                */
                return HttpResult.create(0, "注册成功").set("url", "file/" + filename);

            }
        } else {
            return HttpResult.create(200010, "上传失败，请勿上传空文件！");
        }
        /*end*/
    }
    @SaCheckLogin
    @PostMapping("/deleteImg")
    public HttpResult delImg(@RequestBody String path) {
        int lastIndexOf = path.lastIndexOf("/");
        String imgPath = path.substring(lastIndexOf + 1, path.lastIndexOf("\""));
        imgPath = uploadPath + imgPath;
        File file = new File(imgPath);
        if (file.exists()) {
            if (file.delete()) {
                return HttpResult.create(0, "删除成功");
            } else {
                return HttpResult.create(200010, "删除失败，文件不存在！");
            }
        } else {
            return HttpResult.create(200011, "删除失败");
        }
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/likes")
    public HttpResult handleLikes(@RequestBody PraiseQuery praiseQuery) {
        if (praiseQuery == null) {
            return HttpResult.create(20001, "Invalid account id");
        } else {
            Long id = praiseQuery.getId();
            Long accountId = praiseQuery.getAccountId();
            boolean liked = praiseQuery.getIsLiked();
            Article article = articleService.selectById(id);
            Article article1 = new Article();
            article1.setId(id);
            Long likes = article.getLikes();
            Praise praise = new Praise();
            praise.setAccountId(accountId);
            praise.setPraiseType(0);
            praise.setPraiseForId(id);
            Praise praise1 = praiseService.selectOneByCondition(praise);
            if (!liked) {
                if (praise1 != null) {
                    praiseService.deleteById(praise1.getId());
                }
                article1.setLikes(likes - 1);
            } else {
                if (praise1 == null) {
                    praiseService.insertSelective(praise);
                }
                article1.setLikes(likes + 1);
            }
            articleService.updateByPrimaryKeySelective(article1);
            return HttpResult.create(0, "success");
        }
    }
    @SaCheckLogin
    @GetMapping("/praise")
    public HttpResult getPraiseInfo(Long articleId, Long accountId) {
        if (articleId == null || accountId == null) {
            return HttpResult.create(202300, "请求不能为空！");
        } else {
            Praise praise = new Praise();
            praise.setPraiseForId(articleId);
            praise.setAccountId(accountId);
            praise.setPraiseType(1);
            Boolean liked = praiseService.isLiked(praise);
            if (liked) {
                return HttpResult.create(0, "success！").set("data", true);
            }else {
                return HttpResult.create(0, "success！").set("data", false);
            }

        }

    }


}
