package com.xtkong.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ProjectNodeDao {
	@Insert("insert ignore into project_node_relation(p_id,node_id,cs_id,ft_id,p_data_id) "
			+ "values(#{p_id},#{node_id},#{cs_id},#{ft_id},#{p_data_id})")
	public int insert(@Param("p_id") Integer p_id, @Param("node_id") String nodeId, @Param("cs_id") Integer cs_id,
			@Param("ft_id") Integer ft_id,@Param("p_data_id") String p_data_id);

	@Select("select node_id from project_node_relation where p_id=#{p_id} and cs_id=#{cs_id} and ft_id=#{ft_id} "
			+ " ORDER BY node_id desc limit 1")
	public String select(@Param("p_id") Integer p_id, @Param("cs_id") Integer cs_id, @Param("ft_id") Integer ft_id);

	@Update("update project_node_relation set p_node_id=#{p_node_id} where"
			+ " p_id=#{p_id} and node_id=#{node_id} and cs_id=#{cs_id} and ft_id=#{ft_id}")
	public int updataPNodeId(@Param("p_id") Integer p_id, @Param("node_id") String nodeId, @Param("cs_id") Integer cs_id,
			@Param("ft_id") Integer ft_id, @Param("p_node_id") String pNodeId);

	@Select("select p_node_id from project_node_relation where p_id=#{p_id}  and node_id=#{node_id} and cs_id=#{cs_id} and ft_id=#{ft_id} "
			+ " ORDER BY p_node_id desc limit 1")
	public String selectPNoId(@Param("p_id") Integer p_id, @Param("node_id") String nodeId,
			@Param("cs_id") Integer cs_id, @Param("ft_id") Integer ft_id);

	@Delete("delete from project_node_relation where"
			+ " p_id=#{p_id} and node_id=#{node_id} and cs_id=#{cs_id} and ft_id=#{ft_id}")
	public int remove(@Param("p_id") Integer p_id, @Param("node_id") String nodeId, @Param("cs_id") Integer cs_id,
			@Param("ft_id") Integer ft_id);
	@Delete("delete from project_node_relation where"
			+ " p_id=#{p_id} and cs_id=#{cs_id} and p_data_id=#{p_data_id}")
	public int removeByPDataId(@Param("p_id") Integer p_id, @Param("cs_id") Integer cs_id,
			 @Param("p_data_id") String p_data_id);

}
