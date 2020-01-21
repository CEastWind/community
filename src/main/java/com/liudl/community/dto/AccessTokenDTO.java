package com.liudl.community.dto;

import lombok.Data;

/**
 * Created by TwistedFate on 2020/1/17 15:18
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_url;
    private String state;
}
