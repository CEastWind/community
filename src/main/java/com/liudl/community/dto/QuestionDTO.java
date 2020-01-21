package com.liudl.community.dto;

import com.liudl.community.model.User;
import lombok.Data;

/**
 * Created by TwistedFate on 2020/1/21 15:02
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
