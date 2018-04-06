package qdu.graduation.backend.dao;

import org.springframework.stereotype.Repository;
import qdu.graduation.backend.entity.Classes;

/**
 * Created by Jay on 2018/4/6.
 */
@Repository
public interface ClassesDao {

    Classes selectClassesByUserId(Integer userId);

}
