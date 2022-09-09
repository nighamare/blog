package com.lycoris.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 14:54
 */
@Data
@Accessors(chain = true)
public class AccountDetail implements Serializable {
    /**
     * 账号ID
     */
    private Long id;
    /**
     * 账号ID
     */
    private Long accountId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 账户类型：0-用户，1-管理员
     */
    private Integer accountType;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastUpdate;

    /**
     * 状态：0-正常 1-禁用
     */
    private Integer status;
    /**
     * 性别
     */
    private Integer sex;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String phoneNum;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 个性签名
     */
    private String personalSignature;

    /**
     * 头像blob
     */
    private String avatar;

    private static final long serialVersionUID = 1L;
}
