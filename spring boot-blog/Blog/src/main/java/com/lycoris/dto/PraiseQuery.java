package com.lycoris.dto;

import lombok.Data;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/9/1 15:36
 */
@Data
public class PraiseQuery {
    private Long id;
    private Long accountId;
    private Boolean isLiked;
}
