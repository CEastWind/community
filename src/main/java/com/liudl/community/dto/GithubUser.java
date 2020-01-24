package com.liudl.community.dto;

import lombok.Data;

/**
 * Created by TwistedFate on 2020/1/17 17:04
 */
@Data
public class GithubUser {
    private String login;
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
