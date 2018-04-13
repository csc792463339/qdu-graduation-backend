package qdu.graduation.backend.dao;

import org.springframework.stereotype.Repository;
import qdu.graduation.backend.entity.Classes;

import java.util.List;

@Repository
public interface ClassesDao {
    int deleteByPrimaryKey(Integer classId);

    int insert(Classes record);

    int insertSelective(Classes record);

    Classes selectByPrimaryKey(Integer classId);

    int updateByPrimaryKeySelective(Classes record);

    int updateByPrimaryKey(Classes record);

    List<Classes> selectAllClassesById(Integer teacherId);

    void insertClassesByTeacherId(Classes classes);
}