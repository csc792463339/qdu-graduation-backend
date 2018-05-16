package qdu.graduation.backend.dao;

import org.springframework.stereotype.Component;
import qdu.graduation.backend.entity.StudentHomework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public interface StudentHomeworkDao {
    int insert(StudentHomework record);

    int insertSelective(StudentHomework record);

    List<StudentHomework> queryAllHomeworkByStudentId(Integer studentId);

    List<StudentHomework> queryAllHomeworkByStudentIds(HashMap<String, String> paramMap);

    List<HashMap<String, String>> getAvgScoreByStuId(HashMap<String, String> paramMap);
}