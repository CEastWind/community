package com.liudl.community.dto;

import lombok.Data;

/**
 * Created by TwistedFate on 2020/1/30 12:42
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
