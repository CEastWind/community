package com.liudl.community.exception;

/**
 * Created by TwistedFate on 2020/1/28 18:15
 */
//该类中可以定义不同系统&&业务类型的errorCode
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"the question you want is not exist,please check"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录,请登录后重试！"),
    SYS_ERROR(200,"系统好像冒烟了耶，你一会儿再试吧！"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或评论不存在"),
    COMMENT_NOT_FOUND(2006,"评论不存在或已删除"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空"),
    NOT_YOUR_NOTIFICATION(2008, "亲，这不是你的通知哟"),
    NOTIFICATION_NOT_FOUND(2009, "诶，由于不可抗力，找不到这个通知耶"),
    FILE_UPLOAD_FAIL(2010,"图片上传失败")
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
