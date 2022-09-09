package com.lycoris.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * 标签信息表
 * @TableName tag
 */
@Data
public class Tag implements Serializable {
    /**
     * 标签ID
     */
    private Long id;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签描述
     */
    private String tagDesc;

    private static final long serialVersionUID = 1L;
}