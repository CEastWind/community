package com.liudl.community.controller;

import com.liudl.community.dto.CommentCreateDTO;
import com.liudl.community.dto.CommentDTO;
import com.liudl.community.dto.ResultDTO;
import com.liudl.community.enums.CommentTypeEnum;
import com.liudl.community.exception.CustomizeErrorCode;
import com.liudl.community.mapper.CommentMapper;
import com.liudl.community.model.Comment;
import com.liudl.community.model.User;
import com.liudl.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by TwistedFate on 2020/1/30 12:26
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody//自动将对象转化为json格式给前端
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    //spring会自动将前端传来的json对象映射为CommentDTO对象，json提交
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOs = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOs);
    }
}
