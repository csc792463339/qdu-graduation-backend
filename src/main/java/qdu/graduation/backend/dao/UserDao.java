package qdu.graduation.backend.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.server.reactive.AbstractListenerServerHttpResponse;
import org.springframework.stereotype.Repository;
import qdu.graduation.backend.entity.User;

import java.util.List;

@Repository
public interface UserDao {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    String existPhone(String phone);

    User selectByPhone(@Param("phone") String phone);

    List<User> selectAllStudent();

    List<User> selectAllTeacher();

    List<Integer> getStudentIdByTeacherId(Integer teacherId);

}