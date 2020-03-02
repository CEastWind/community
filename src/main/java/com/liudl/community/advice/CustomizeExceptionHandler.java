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

    /**
     * 处理springmvc可以拦截的异常，不包括4xx,5xx等
     * Exception为所有异常父类
     */
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(Throwable e,
                                           Model model,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {

        String contentType = request.getContentType();

        /**
         * 该异常处理分为返回json格式和返回text/html两种情况
         * 所谓e instanceof CustomizeException说明了该匹配条件下只是处理我们在代码中自行抛出自定义的异常,
         * 而非该匹配条件下的异常统统给页面的信息是  SYS_ERROR。当然该情况下估计只有找日志来确定具体异常
         */
        if ("application/json".equals(contentType)) {
            //返回json,我们要做的是当请求时json时，我们返回的也是json对象
            ResultDTO resultDTO;
            if (e instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            //手动给response写json
            try {
                response.setContentType("application/json");
                //response.setContentType("application/json;charset=utf-8");以后其它项目是否需要这样写??????
                response.setStatus(200);//都异常了还给200？？？这里的意思应该是成功返回异常信息到浏览器了
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
