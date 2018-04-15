package qdu.graduation.backend.dao;

import org.springframework.stereotype.Repository;
import qdu.graduation.backend.entity.StudentClass;

import java.util.List;

@Repository
public interface StudentClassDao {
    int insert(StudentClass record);

    int insertSelective(StudentClass record);

    List<StudentClass> getAllStudentByClassID(Integer classId);

}