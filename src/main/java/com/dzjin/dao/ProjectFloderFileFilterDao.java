package com.dzjin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dzjin.model.Project;
import com.dzjin.model.ProjectFile;

public interface ProjectFloderFileFilterDao {
	
	/**
	 * 查找文件名称
	 * @param floder_id
	 * @return
	 */
	@Select("select distinct project_file.file_name from project_file,user "
			+ "where floder_id=#{floder_id} and user.id=project_file.creator_id ${sql}")
	public List<String> selectFileNameByFloderId(@Param("floder_id")Integer floder_id,@Param("sql")String sql);
	/**
	 * 查找文件类型
	 * @param floder_id
	 * @return
	 */
	@Select("select distinct  project_file.file_type from project_file,user "
			+ "where floder_id=#{floder_id} and user.id=project_file.creator_id ${sql}")
	public List<String> selectFileTypeByFloderId(@Param("floder_id")Integer floder_id,@Param("sql")String sql);
	/**
	 * 查找文件大小
	 * @param floder_id
	 * @return
	 */
	@Select("select distinct  project_file.file_size from project_file,user "
			+ "where floder_id=#{floder_id} and user.id=project_file.creator_id ${sql}")
	public List<String> selectFileSizeByFloderId(@Param("floder_id")Integer floder_id,@Param("sql")String sql);
	/**
	 * 查找传创建时间
	 * @param floder_id
	 * @return
	 */
	@Select("select distinct  project_file.create_datetime from project_file,user "
			+ "where floder_id=#{floder_id} and user.id=project_file.creator_id ${sql}")
	public List<String> selectCreateDatetimeByFloderId(@Param("floder_id")Integer floder_id,@Param("sql")String sql);
	/**
	 * 查找传创建时间
	 * @param floder_id
	 * @return
	 */
	@Select("select distinct user.username from project_file,user "
			+ "where floder_id=#{floder_id} and user.id=project_file.creator_id ${sql}")
	public List<String> selectCreatorNameByFloderId(@Param("floder_id")Integer floder_id,@Param("sql")String sql);

	/**
	 * 过滤所有文件
	 * @param floder_id
	 * @return
	 */
	@Select("${sql}")
	public List<ProjectFile> selectProjectFileByFilter(@Param("sql")String sql);
	
	
	@Select("select distinct ${column} from project_file,user "
			+ "where floder_id=#{floder_id} and user.id=project_file.creator_id ${sql}")
	public List<String> selectColumnsByFloderId(@Param("floder_id")Integer floder_id,@Param("sql")String sql,@Param("column")String column);
}
