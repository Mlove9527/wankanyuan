package com.liutianjun.dao;

import com.liutianjun.pojo.OrgMember;
import com.liutianjun.pojo.OrgMemberQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrgMemberDao {
    int countByExample(OrgMemberQuery example);

    int deleteByExample(OrgMemberQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrgMember record);

    int insertSelective(OrgMember record);

    List<OrgMember> selectByExample(OrgMemberQuery example);

    OrgMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrgMember record, @Param("example") OrgMemberQuery example);

    int updateByExample(@Param("record") OrgMember record, @Param("example") OrgMemberQuery example);

    int updateByPrimaryKeySelective(OrgMember record);

    int updateByPrimaryKey(OrgMember record);
}