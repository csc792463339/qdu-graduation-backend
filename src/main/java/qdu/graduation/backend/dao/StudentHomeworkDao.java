package qdu.graduation.backend.dao;

import qdu.graduation.backend.entity.StudentHomework;

public interface StudentHomeworkDao {
    int insert(StudentHomework record);

    int insertSelective(StudentHomework record);
}