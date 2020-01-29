package com.liudl.community.exception;

/**
 * Created by TwistedFate on 2020/1/28 18:15
 */
//该类中可以定义不同系统&&业务类型的errorCode
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("the question you want is not exist,please check");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
