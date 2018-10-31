package com.xtkong.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface ProjectNodeDataDao {
	@Insert("insert ignore into project_nodedata_relation(p_id,nodedata_id,cs_id,ft_id,p_data_id) "
			+ "values(#{p_id},#{nodedata_id},#{cs_id},#{ft_id},#{p_data_id})")
	public int insert(@Param("p_id") Integer p_id, @Param("nodedata_id") String nodedata_id,
			@Param("cs_id") Integer cs_id, @Param("ft_id") Integer ft_id, @Param("p_data_id") String p_data_id);

	// @Select("select data_id from project_nodedata_relation where p_id=#{p_id}
	// and cs_id=#{cs_id} and ft_id=#{ft_id}")
	// public List<String> select(@Param("p_id") Integer p_id, @Param("cs_id")
	// Integer cs_id,@Param("ft_id") Integer ft_id);
	//
	@Delete("delete from project_nodedata_relation where"
			+ " p_id=#{p_id} and cs_id=#{cs_id} and p_data_id=#{p_data_id}")
	public int remove(@Param("p_id") Integer p_id, @Param("cs_id") Integer cs_id, @Param("p_data_id") String p_data_id);

}
