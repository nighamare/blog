package com.lycoris.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 账号信息表
 * @TableName account
 */
@Accessors(chain = true)
@Data
public class Account implements Serializable {
    /**
     * 账号ID
     */
    private Long id;

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

    private static final long serialVersionUID = 1L;
}