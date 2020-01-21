package com.liudl.community.mapper;

import com.liudl.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by TwistedFate on 2020/1/20 20:19
 */
@Mapper
public interface QuestionMapper {
    @Select("select * from question")
    List<Question> list();

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);
}
