package com.lycoris.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * 博客类型信息表
 * @TableName category
 */
@Data
public class Category implements Serializable {
    /**
     * 博客类型ID
     */
    private Long id;

    /**
     * 博客类型名称
     */
    private String categoryName;

    /**
     * 博客类型描述
     */
    private String categoryDesc;

    private static final long serialVersionUID = 1L;
}