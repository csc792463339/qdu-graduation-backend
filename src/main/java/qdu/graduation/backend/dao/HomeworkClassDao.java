package qdu.graduation.backend.dao;

import org.springframework.stereotype.Repository;
import qdu.graduation.backend.entity.Homework;
import qdu.graduation.backend.entity.HomeworkClass;

import java.util.List;

@Repository
public interface HomeworkClassDao {
    int insert(HomeworkClass record);

    int insertSelective(HomeworkClass record);

    HomeworkClass selectHomeworkClassByClassId(String classId);
}