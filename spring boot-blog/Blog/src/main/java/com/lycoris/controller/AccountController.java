package com.lycoris.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.lycoris.domain.Account;
import com.lycoris.domain.AccountInfo;
import com.lycoris.domain.Article;
import com.lycoris.dto.AccountDetail;
import com.lycoris.model.HttpResult;
import com.lycoris.service.AccountService;
import com.lycoris.service.ArticleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;


/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 14:32
 */
@RequestMapping("/account")
@RestController
public class AccountController {
    @Resource
    private AccountService accountService;
    @Resource
    private ArticleService articleService;

    @Value("${file-upload.upload-path}")
    private String uploadPath;

    @GetMapping("/findAll")
    public HttpResult findAll(AccountDetail accountDetail,@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "10") int pageSize) {
        return HttpResult.create(0, "查询成功！").set("data", accountService.selectByPageSelective(pageNum, pageSize, accountDetail));
    }

    @GetMapping("/findOne")
    public HttpResult findOne(@Param("id") Long id) {
        return HttpResult.create(0, "查询成功！").set("data", accountService.selectOneByCondition(new AccountDetail().setId(id)));
    }
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/insert")
    public HttpResult insert(@RequestBody AccountDetail accountDetail) {
        accountService.insertSelective(accountDetail);
        return HttpResult.create(0, "插入成功！");
    }
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/update")
    public HttpResult updateAccountInfo(@RequestBody AccountDetail accountDetail) {
        if (accountDetail == null) {
            return HttpResult.create(200301, "请勿输入空字段！");
        }
        accountService.updateByPrimaryKeySelective(accountDetail);
        return HttpResult.create(0, "更新成功！");
    }
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/updateAccount")
    public HttpResult updateAccount(@RequestBody Account account) {
        if (account == null) {
            return HttpResult.create(200301, "请勿输入空字段！");
        }
        accountService.updateAccountSelective(account);
        return HttpResult.create(0, "更新成功！");
    }

    @GetMapping("/author/{id}")
    public HttpResult getAuthorInfo(@PathVariable Long id) {
        Article article = articleService.selectById(id);
        AccountInfo accountInfo = accountService.selectAccountInfoById(article.getAccountId());
        return HttpResult.create(0, "查询成功！").set("data", accountInfo);
    }

    /* login */
    @PostMapping("/login")
    public HttpResult login(@RequestBody Account account) {
        Account login = accountService.userLogin(account);
        if (login != null) {
            StpUtil.login(login.getId());
            return HttpResult.create(0, "登录成功！").set("accountInfo", login).set("token", StpUtil.getTokenValue());
        } else {
            return HttpResult.create(20001, "用户名或密码错误，登录失败！");
        }
    }

    @PostMapping("/user/login")
    public HttpResult userLogin(@RequestBody Account account) {
        Account login = accountService.adminLogin(account);
        if (login != null) {
            StpUtil.login(login.getId());
            AccountDetail accountDetail = accountService.selectById(login.getId());
            accountDetail.setPassword(null);
            return HttpResult.create(0, "登录成功！").set("accountInfo", accountDetail).set("token", StpUtil.getTokenValue());
        } else {
            return HttpResult.create(20001, "用户名或密码错误，登录失败！");
        }
    }

    @PostMapping("/logout")
    public HttpResult logout() {
//        StpUtil.getLoginId();    // 获取当前会话登录的账号id
        boolean login = StpUtil.isLogin();// 获取当前会话是否已经登录, 返回true或false
        if (login) {
            StpUtil.logout();// 当前会话注销登录
            return HttpResult.create(0, "退出登录成功！");
        } else {
            return HttpResult.create(200009, "账户未登录");
        }
    }

    @PostMapping("/changePwd")
    public HttpResult changePwd(Long id, String oldPassword, String password) {
        if (id == null || Objects.equals(oldPassword, "") || Objects.equals(password, "")) {
            return HttpResult.create(2000113, "修改失败！请输入有效的密码！");
        } else {
            Account account = accountService.selectAccountById(id);
            if (account.getPassword().equals(oldPassword)) {
                account.setPassword(password);
                accountService.updatePassword(account);
                return HttpResult.create(0, "修改成功！");

            } else {
                return HttpResult.create(2000114, "修改失败！请输入正确有效的密码！");
            }
        }


    }

    @PutMapping("/register")
    public HttpResult register(Account account) {
        Account login = accountService.selectByUsername(account);
        if (login != null) {
            return HttpResult.create(20002, "账号已存在！");
        } else {
            Long id;
            try {
                accountService.register(account);
                id = account.getId();
            } catch (Exception e) {
                e.printStackTrace();
                return HttpResult.create(20003, "注册失败，稍后再试！");
            }
            return HttpResult.create(0, "注册成功").set("id", id);
        }

    }
    @SaCheckLogin
    @PostMapping("/updateAvatar/{accountId}")
    public HttpResult updateAvatar(@PathVariable Long accountId, @RequestParam MultipartFile file) {
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
                AccountInfo accountInfo = new AccountInfo();
                accountInfo.setAccountId(accountId);
                accountInfo.setAvatar("file/" + filename);
                int i = accountService.updateAccountInfoSelective(accountInfo);
                if (i > 0) {
                    return HttpResult.create(0, "注册成功").set("id", accountId);
                } else {
                    return HttpResult.create(2002003, "修改失败，请重新尝试！");
                }
            }
        } else {
            return HttpResult.create(200010, "上传失败，请勿上传空文件！");
        }
        /*end*/
    }
    @GetMapping("/infolist")
    public HttpResult infoList(AccountInfo accountInfo,@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "10") int pageSize){

        return HttpResult.create(0,"查询成功！").set("data",accountService.selectAccountInfoByPage(pageNum, pageSize, accountInfo));
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/updateainfo")
    public HttpResult updateAccountInfoById(@RequestBody AccountInfo accountInfo) {
        if (accountInfo == null) {
            return HttpResult.create(200301, "请勿输入空字段！");
        }
        accountService.updateAccountInfoSelective(accountInfo);
        return HttpResult.create(0, "更新成功！");
    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/delete/{id}")
    public HttpResult deleteAccountDetailById(@PathVariable("id") Long id) {
        if (id == null) {
            return HttpResult.create(200301, "请勿输入空字段！");
        }
        accountService.deleteById(id);
        return HttpResult.create(0, "删除成功!");

    }
    @SaCheckLogin
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/deleteByIds")
    public HttpResult deleteAccountByIds(Long[] ids) {
        if (ids == null) {
            return HttpResult.create(200301, "请勿输入空字段！");
        }
        accountService.deleteByIds(ids);
        return HttpResult.create(0, "删除成功!");

    }


}

