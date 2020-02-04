package com.liudl.community.controller;

import com.liudl.community.cache.TagCache;
import com.liudl.community.dto.QuestionDTO;
import com.liudl.community.mapper.QuestionMapper;
import com.liudl.community.model.Question;
import com.liudl.community.model.User;
import com.liudl.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by TwistedFate on 2020/1/20 14:47
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag", questionDTO.getTag());
        model.addAttribute("id", questionDTO.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    //Get请求就渲染页面
    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    //post请求就处理文章发布业务
    //form提交
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "tag", required = false) String tag,
                            @RequestParam(value = "id", required = false) Long id,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

        if (StringUtils.isBlank(title)) {
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(description)){
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(tag)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "非法的标签输入:" + invalid);
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        //id为创建时插入页面的question.id,如果没有则为null
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
