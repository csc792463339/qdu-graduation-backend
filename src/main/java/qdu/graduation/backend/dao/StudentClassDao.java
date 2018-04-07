package qdu.graduation.backend.dao;

import org.springframework.stereotype.Repository;
import qdu.graduation.backend.entity.StudentClass;

@Repository
public interface StudentClassDao {
    int insert(StudentClass record);

    int insertSelective(StudentClass record);
}