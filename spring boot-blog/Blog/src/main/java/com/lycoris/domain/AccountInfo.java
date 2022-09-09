package com.lycoris.domain;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户信息表
 * @TableName account_info
 */
@Data
@Accessors(chain = true)
public class AccountInfo implements Serializable {
    /**
     * 账户ID
     */
    private Long accountId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer sex;

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