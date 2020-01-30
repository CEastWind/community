package com.liudl.community.advice;

import com.alibaba.fastjson.JSON;
import com.liudl.community.dto.ResultDTO;
import com.liudl.community.exception.CustomizeErrorCode;
import com.liudl.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by TwistedFate on 2020/1/28 17:04
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    //处理springmvc可以拦截的异常，不包括4xx,5xx等
    // Exception为所有异常父类,
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(Throwable e,
                                           Model model,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            //返回json,我们要做的是当请求时json时，我们返回的也是json对象
            ResultDTO resultDTO;
            if (e instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            //手动给respongse写json
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
            } catch (IOException ioe) {
            }
            return null;
        }else {
            //跳转到错误页面error.html
            if (e instanceof CustomizeException) {
                //CustomizeException.getMessage已被重写
                model.addAttribute("message", e.getMessage());
            }else {
                System.out.println(e.getMessage());
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            //自己定义的error.html页面
            return new ModelAndView("error");
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
