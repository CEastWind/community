package com.liudl.community.controller;

import com.liudl.community.mapper.UserMapper;
import com.liudl.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if (user != null) {
                    //登录成功，写cookie和session
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        //如果没有token这个cookie，则返回没登录的页面
        return "index";
    }
}
