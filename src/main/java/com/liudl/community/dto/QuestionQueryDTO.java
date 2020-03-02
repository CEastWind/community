package com.liudl.community.dto;

import lombok.Data;

/**
 * Created by TwistedFate on 2020/3/1 13:51
 */
@Data
public class QuestionQueryDTO {
    private Integer page;
    private Integer size;
    private String search;
}
