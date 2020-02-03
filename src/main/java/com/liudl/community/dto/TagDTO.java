package com.liudl.community.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by TwistedFate on 2020/2/2 22:11
 */
@Data
public class TagDTO {
    private String CategoryName;
    private List<String> tags;
}
