package com.liudl.community.exception;

/**
 * Created by TwistedFate on 2020/1/28 18:15
 */
//该类中可以定义不同系统&&业务类型的errorCode
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"the question you want is not exist,please check"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录"),
    SYS_ERROR(200,"船体出现不明漏洞！前方危险，建议返航！"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或评论不存在"),
    COMMENT_NOT_FOUND(2006,"评论不存在或已删除"),
    ;

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
