package com.dzjin.dao;



import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dzjin.model.ProjectFile;

public interface ProjectFileDao {
	
	@Insert("insert into project_file(file_name,file_location,file_type,file_size,create_datetime,creator_id,p_id) "
			+ "values(#{file_name},#{file_location},#{file_type},#{file_size},#{create_datetime},#{creator_id},#{p_id})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public int insertPorjectFile(ProjectFile projectFile);
	
	@Update("update project_file set floder_id=#{floder_id} where id=#{id}")
	public int updateFloderId(@Param("floder_id")Integer floder_id , @Param("id")Integer id);
	
	@Select("select project_file.* , user.username "
			+ "from project_file,user "
			+ "where floder_id=#{floder_id} and user.id=project_file.creator_id")
	public List<ProjectFile> selectProjectFileByFloderId(@Param("floder_id")Integer floder_id);
	
	@Delete("delete from project_file where id=#{id}")
	public int deleteFile(@Param("id")Integer id);
	
	@Select("select count(*) from project_file where p_id=#{p_id} and creator_id=#{creator_id}")
	public int countProjectFileNumByPidAndUid(@Param("p_id")Integer p_id , @Param("creator_id")Integer creator_id);
	
 
}
