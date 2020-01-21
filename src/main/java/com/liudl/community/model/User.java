package com.liudl.community.model;

import lombok.Data;

/**
 * Created by TwistedFate on 2020/1/19 9:44
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
