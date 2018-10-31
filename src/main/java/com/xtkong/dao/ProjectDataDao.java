package com.xtkong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ProjectDataDao {
	@Insert("insert ignore into project_data_relation(p_id,source_data_id,cs_id) "
			+ "values(#{p_id},#{source_data_id},#{cs_id})")
	public int insert(@Param("p_id") Integer p_id, @Param("source_data_id") String sourceDataId,
			@Param("cs_id") Integer cs_id);

	@Select("select source_data_id from project_data_relation where p_id=#{p_id} and cs_id=#{cs_id}")
	public List<String> select(@Param("p_id") Integer p_id, @Param("cs_id") Integer cs_id);

	@Update("update project_data_relation set p_data_id=#{p_data_id} where"
			+ " p_id=#{p_id} and source_data_id=#{source_data_id} and cs_id=#{cs_id}")
	public int updataPDataId(@Param("p_id") Integer p_id, @Param("source_data_id") String sourceDataId,
			@Param("cs_id") Integer cs_id, @Param("p_data_id") String pSourceDataId);

	@Select("select p_data_id from project_data_relation where"
			+ " p_id=#{p_id} and source_data_id=#{source_data_id} and cs_id=#{cs_id}"
			+ " ORDER BY p_data_id desc limit 1")
	public String selectPDataId(@Param("p_id") Integer p_id, @Param("source_data_id") String sourceDataId,
			@Param("cs_id") Integer cs_id);

	@Delete("delete from project_data_relation where"
			+ " p_id=#{p_id} and p_data_id=#{p_data_id} and cs_id=#{cs_id}")
	public int remove(@Param("p_id") Integer p_id, @Param("p_data_id") String pSourceDataId,
			@Param("cs_id") Integer cs_id);

}
