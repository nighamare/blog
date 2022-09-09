package com.lycoris.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 14:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyCount {

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 回复数量
     */
    private Integer replyCount;

}
