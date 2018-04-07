package qdu.graduation.backend.dao;

import org.springframework.stereotype.Repository;
import qdu.graduation.backend.entity.File;

@Repository
public interface FileDao {
    int deleteByPrimaryKey(Integer fileId);

    int insert(File record);

    int insertSelective(File record);

    File selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKey(File record);
}