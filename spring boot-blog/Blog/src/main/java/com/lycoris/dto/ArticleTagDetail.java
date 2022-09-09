package com.lycoris.dto;

import com.lycoris.domain.ArticleTag;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/19 12:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleTagDetail extends ArticleTag {
    private Long articleId;
    private Long tagId;
    private String tagName;
    private String tagDesc;

}
