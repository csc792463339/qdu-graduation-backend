package qdu.graduation.backend.dao;

import org.springframework.stereotype.Repository;
import qdu.graduation.backend.entity.StudentHomework;

@Repository
public interface StudentHomeworkDao {
    int insert(StudentHomework record);

    int insertSelective(StudentHomework record);
}