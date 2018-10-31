package com.liutianjun.dao;

import com.liutianjun.pojo.Resource;
import com.liutianjun.pojo.ResourceQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ResourceDao {
    int countByExample(ResourceQuery example);

    int deleteByExample(ResourceQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Resource record);

    int insertSelective(Resource record);

    List<Resource> selectByExample(ResourceQuery example);

    Resource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Resource record, @Param("example") ResourceQuery example);

    int updateByExample(@Param("record") Resource record, @Param("example") ResourceQuery example);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
}