package com.liutianjun.dao;

import com.liutianjun.pojo.Friends;
import com.liutianjun.pojo.FriendsQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FriendsDao {
    int countByExample(FriendsQuery example);

    int deleteByExample(FriendsQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Friends record);

    int insertSelective(Friends record);

    List<Friends> selectByExample(FriendsQuery example);

    Friends selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Friends record, @Param("example") FriendsQuery example);

    int updateByExample(@Param("record") Friends record, @Param("example") FriendsQuery example);

    int updateByPrimaryKeySelective(Friends record);

    int updateByPrimaryKey(Friends record);
}