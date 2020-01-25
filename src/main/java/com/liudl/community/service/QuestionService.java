package com.liudl.community.service;

import com.liudl.community.dto.PaginationDTO;
import com.liudl.community.dto.QuestionDTO;
import com.liudl.community.mapper.QuestionMapper;
import com.liudl.community.mapper.UserMapper;
import com.liudl.community.model.Question;
import com.liudl.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TwistedFate on 2020/1/21 15:06
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;


    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalcount = questionMapper.count();
        paginationDTO.setPagination(totalcount,page,size);

        //offset代表偏移量
        Integer offset = size * (paginationDTO.getPage() - 1);
        List<Question> questions = questionMapper.list(offset, size);

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            //根据question的Creator查出对应的user
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question信息传递给questionDTO
            BeanUtils.copyProperties(question,questionDTO);
            //将user信息传递给questionDTO
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOs(questionDTOList);

        return paginationDTO;
    }

}
