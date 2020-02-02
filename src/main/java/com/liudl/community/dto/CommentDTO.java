package com.liudl.community.dto;

import com.liudl.community.model.User;
import lombok.Data;

/**
 * Created by TwistedFate on 2020/2/1 10:12
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;
}
