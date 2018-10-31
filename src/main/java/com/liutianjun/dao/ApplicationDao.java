package com.liutianjun.dao;

import com.liutianjun.pojo.Application;
import com.liutianjun.pojo.ApplicationQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApplicationDao {
    int countByExample(ApplicationQuery example);

    int deleteByExample(ApplicationQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Application record);

    int insertSelective(Application record);

    List<Application> selectByExampleWithBLOBs(ApplicationQuery example);

    List<Application> selectByExample(ApplicationQuery example);

    Application selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Application record, @Param("example") ApplicationQuery example);

    int updateByExampleWithBLOBs(@Param("record") Application record, @Param("example") ApplicationQuery example);

    int updateByExample(@Param("record") Application record, @Param("example") ApplicationQuery example);

    int updateByPrimaryKeySelective(Application record);

    int updateByPrimaryKeyWithBLOBs(Application record);

    int updateByPrimaryKey(Application record);
}