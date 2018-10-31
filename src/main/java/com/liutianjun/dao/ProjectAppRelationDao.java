package com.liutianjun.dao;

import com.liutianjun.pojo.ProjectAppRelation;
import com.liutianjun.pojo.ProjectAppRelationQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectAppRelationDao {
    int countByExample(ProjectAppRelationQuery example);

    int deleteByExample(ProjectAppRelationQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectAppRelation record);

    int insertSelective(ProjectAppRelation record);

    List<ProjectAppRelation> selectByExample(ProjectAppRelationQuery example);

    ProjectAppRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectAppRelation record, @Param("example") ProjectAppRelationQuery example);

    int updateByExample(@Param("record") ProjectAppRelation record, @Param("example") ProjectAppRelationQuery example);

    int updateByPrimaryKeySelective(ProjectAppRelation record);

    int updateByPrimaryKey(ProjectAppRelation record);
}