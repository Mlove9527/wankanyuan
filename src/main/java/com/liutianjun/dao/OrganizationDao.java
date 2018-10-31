package com.liutianjun.dao;

import com.liutianjun.pojo.Organization;
import com.liutianjun.pojo.OrganizationQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrganizationDao {
    int countByExample(OrganizationQuery example);

    int deleteByExample(OrganizationQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(Organization record);

    int insertSelective(Organization record);

    List<Organization> selectByExample(OrganizationQuery example);

    Organization selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Organization record, @Param("example") OrganizationQuery example);

    int updateByExample(@Param("record") Organization record, @Param("example") OrganizationQuery example);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);
}