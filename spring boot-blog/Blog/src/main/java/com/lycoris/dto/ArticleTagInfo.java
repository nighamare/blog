package com.lycoris.dto;

import lombok.Data;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/31 14:32
 */
@Data
public class ArticleTagInfo {
    private String[] tagName;
    private Long articleId;
}
