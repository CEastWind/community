package com.liudl.community.service;

import com.liudl.community.dto.PaginationDTO;
import com.liudl.community.dto.QuestionDTO;
import com.liudl.community.dto.QuestionQueryDTO;
import com.liudl.community.exception.CustomizeErrorCode;
import com.liudl.community.exception.CustomizeException;
import com.liudl.community.mapper.QuestionExtMapper;
import com.liudl.community.mapper.QuestionMapper;
import com.liudl.community.mapper.UserMapper;
import com.liudl.community.model.Question;
import com.liudl.community.model.QuestionExample;
import com.liudl.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by TwistedFate on 2020/1/21 15:06
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDTO list(String search, Integer page, Integer size) {

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalcount = questionExtMapper.countBySearch(questionQueryDTO);

        //没有相关数据
        if (totalcount == 0) {
            return null;
        }
        //算出导航页码条的信息
        paginationDTO.setPagination(totalcount,page,size);

        //offset代表偏移量
        Integer offset = size * (paginationDTO.getPage() - 1);

        questionQueryDTO.setPage(offset);
        questionQueryDTO.setSize(size);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        //QuestionExample questionExample = new QuestionExample();
        //questionExample.setOrderByClause("gmt_create desc");
        //List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            //根据question的Creator查出对应的user
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question信息传递给questionDTO
            BeanUtils.copyProperties(question,questionDTO);
            //将user信息传递给questionDTO
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalcount =(int) questionMapper.countByExample(questionExample);
        //没有相关数据
        if (totalcount == 0) {}
        //算出导航页码条的信息
        paginationDTO.setPagination(totalcount,page,size);

        //offset代表偏移量
        Integer offset = size * (paginationDTO.getPage() - 1);
        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample1, new RowBounds(offset, size));


        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            //根据question的Creator查出对应的user
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question信息传递给questionDTO
            BeanUtils.copyProperties(question,questionDTO);
            //将user信息传递给questionDTO
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        //当出现预想到的异常时，用自定义的异常throw出来，错误信息为自定义枚举类
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        }else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample questionExample2 = new QuestionExample();
            questionExample2.createCriteria().andIdEqualTo(question.getId());
            int isZeroOrOne = questionMapper.updateByExampleSelective(updateQuestion, questionExample2);
            //当出现预想到的异常时，用自定义的异常throw出来，错误信息为自定义枚举类
            if (isZeroOrOne != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        /**该写法高并发下回出错
        Question question = questionMapper.selectByPrimaryKey(id);
        Question updateQuestion = new Question();
        updateQuestion.setViewCount(question.getViewCount() + 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdEqualTo(id);
        questionMapper.updateByExampleSelective(updateQuestion, questionExample);**/
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<Question> selectRelated(QuestionDTO questionDTO) {
        if (StringUtils.isBlank(questionDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDTO.getTag(), ",|，");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexpTag);
        List<Question> relatedQuestions = questionExtMapper.selectRelated(question);
        return relatedQuestions;
    }
}
