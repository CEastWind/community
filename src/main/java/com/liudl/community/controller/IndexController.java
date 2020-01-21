package com.liudl.community.controller;

import com.liudl.community.dto.QuestionDTO;
import com.liudl.community.mapper.QuestionMapper;
import com.liudl.community.mapper.UserMapper;
import com.liudl.community.model.User;
import com.liudl.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        //登录成功，写cookie和session
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }

        List<QuestionDTO> questionDTOList = questionService.list();
        model.addAttribute("questionDTOs", questionDTOList);
        //如果没有token这个cookie，则返回没登录的页面
        return "index";
    }
}
