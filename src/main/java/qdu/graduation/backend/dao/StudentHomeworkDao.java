package qdu.graduation.backend.dao;

import org.springframework.stereotype.Component;
import qdu.graduation.backend.entity.StudentHomework;

import java.util.List;

@Component
public interface StudentHomeworkDao {
    int insert(StudentHomework record);

    int insertSelective(StudentHomework record);

    List<StudentHomework> queryAllHomeworkByStudentId(Integer studentId);
}