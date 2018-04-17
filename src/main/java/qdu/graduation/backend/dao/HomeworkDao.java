package qdu.graduation.backend.dao;

import qdu.graduation.backend.entity.Homework;

public interface HomeworkDao {
    int deleteByPrimaryKey(Integer homeworkId);

    int insert(Homework record);

    int insertSelective(Homework record);

    Homework selectByPrimaryKey(Integer homeworkId);

    int updateByPrimaryKeySelective(Homework record);

    int updateByPrimaryKey(Homework record);
}