package com.liudl.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by TwistedFate on 2020/1/26 21:11
 */
@Configuration
//顺带把css文件也拦截了，汗。。。
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new ThemeChangeInterceptor()).addPathPatterns("/**");
        //顺带把css文件也拦截了，汗。。。
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");

    }


}
