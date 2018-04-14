package qdu.graduation.backend.dao;

import org.springframework.stereotype.Repository;
import qdu.graduation.backend.entity.Board;

import java.util.List;

@Repository
public interface BoardDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Board record);

    int insertSelective(Board record);

    Board selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Board record);

    int updateByPrimaryKey(Board record);

    List<Board> selectByClassId(Integer id);

}