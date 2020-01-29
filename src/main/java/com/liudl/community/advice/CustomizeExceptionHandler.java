package com.liudl.community.advice;

import com.liudl.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by TwistedFate on 2020/1/28 17:04
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    //处理springmvc可以拦截的异常，不包括4xx,5xx等
    // Exception为所有异常父类,
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(Throwable e, Model model) {
        if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
        }else {
            e.printStackTrace();
            model.addAttribute("message", "船体出现不明漏洞！前方危险，建议返航！");
        }
        //自己定义的error.html页面
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
