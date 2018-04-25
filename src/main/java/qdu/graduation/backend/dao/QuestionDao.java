package qdu.graduation.backend.dao;

import qdu.graduation.backend.entity.Question;

import java.util.List;

public interface QuestionDao {
    int deleteByPrimaryKey(Integer questionId);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);

    List<Question> selectAllQuestionByTeacherId(Integer teacherId);

    Integer getSumScore(String ids);
}