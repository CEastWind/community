package com.liudl.community.controller;

import com.liudl.community.dto.QuestionDTO;
import com.liudl.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by TwistedFate on 2020/1/27 10:47
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    //浏览器地址路径值提交
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("questionDTO", questionDTO);
        return "question";
    }
}
