package com.liutianjun.dao;

import com.liutianjun.pojo.SysConfig;
import com.liutianjun.pojo.SysConfigQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysConfigDao {
    int countByExample(SysConfigQuery example);

    int deleteByExample(SysConfigQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysConfig record);

    int insertSelective(SysConfig record);

    List<SysConfig> selectByExample(SysConfigQuery example);

    SysConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysConfig record, @Param("example") SysConfigQuery example);

    int updateByExample(@Param("record") SysConfig record, @Param("example") SysConfigQuery example);

    int updateByPrimaryKeySelective(SysConfig record);

    int updateByPrimaryKey(SysConfig record);
}