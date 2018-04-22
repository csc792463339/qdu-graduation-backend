package qdu.graduation.backend.dao;

import qdu.graduation.backend.entity.Question;

public interface QuestionDao {
    int deleteByPrimaryKey(Integer questionId);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);
}