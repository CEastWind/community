package com.liudl.community.controller;

import com.liudl.community.dto.CommentDTO;
import com.liudl.community.dto.QuestionDTO;
import com.liudl.community.enums.CommentTypeEnum;
import com.liudl.community.model.Question;
import com.liudl.community.service.CommentService;
import com.liudl.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by TwistedFate on 2020/1/27 10:47
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    //浏览器地址路径值提交
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> commentDTOs = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        List<Question> relatedQuestions = questionService.selectRelated(questionDTO);

        //累加阅读数
        questionService.incView(id);
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("commentDTOs", commentDTOs);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
