package com.liutianjun.dao;

import com.liutianjun.pojo.FriendMessage;
import com.liutianjun.pojo.FriendMessageQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FriendMessageDao {
    int countByExample(FriendMessageQuery example);

    int deleteByExample(FriendMessageQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(FriendMessage record);

    int insertSelective(FriendMessage record);

    List<FriendMessage> selectByExample(FriendMessageQuery example);

    FriendMessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FriendMessage record, @Param("example") FriendMessageQuery example);

    int updateByExample(@Param("record") FriendMessage record, @Param("example") FriendMessageQuery example);

    int updateByPrimaryKeySelective(FriendMessage record);

    int updateByPrimaryKey(FriendMessage record);
}