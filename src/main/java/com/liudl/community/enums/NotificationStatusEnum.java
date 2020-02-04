package com.liudl.community.enums;

/**
 * Created by TwistedFate on 2020/2/3 17:00
 */
public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1);

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
