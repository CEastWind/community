package com.liudl.community.exception;

/**
 * Created by TwistedFate on 2020/1/28 17:49
 */
/**
 * 继承RuntimeException的自定义异常类，方便在可预见的异常处throw，
 * 继承RuntimeException是为了不在throw该异常时需要try catch
 */
public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
