package qdu.graduation.backend.dao;

import org.springframework.stereotype.Repository;
import qdu.graduation.backend.entity.HomeworkClass;

@Repository
public interface HomeworkClassDao {
    int insert(HomeworkClass record);

    int insertSelective(HomeworkClass record);
}