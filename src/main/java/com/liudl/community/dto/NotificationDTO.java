package com.liudl.community.dto;

import com.liudl.community.model.User;
import lombok.Data;

/**
 * Created by TwistedFate on 2020/2/3 18:03
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Integer type;
    private String typeName;
    private Long notifier;
    private String notifierName;
    private Long outerId;
    private String outerTitle;
}