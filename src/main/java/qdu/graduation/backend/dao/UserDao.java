package qdu.graduation.backend.dao;

import org.springframework.stereotype.Component;
import qdu.graduation.backend.entity.User;

@Component
public interface UserDao {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}