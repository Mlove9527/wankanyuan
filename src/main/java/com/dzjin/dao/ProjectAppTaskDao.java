package com.dzjin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dzjin.model.ProjectAppTask;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectAppTaskDao 
 * 类描述： 项目应用运行结果dao
 * 创建人：dzjin 
 * 创建时间：2018年6月17日 上午11:09:21 
 * 修改人：dzjin 
 * 修改时间：2018年6月17日 上午11:09:21 
 * 修改备注： 
 * @version 
 *
 */
public interface ProjectAppTaskDao {
	
	@Insert("insert into project_app_task"
			+ "(param,project_id,user_id,app_id,username,taskDescription,taskName,create_datetime) "
			+ "values(#{param},#{project_id},#{user_id},#{app_id},#{username},#{taskDescription},#{taskName},#{create_datetime})")
	@Options(useGeneratedKeys = true , keyColumn="id" , keyProperty="id")
	public int insertProjectAppTask(ProjectAppTask projectAppTask);
	
	@Update("update project_app_task "
			+ "set param=#{param} , project_id=#{project_id} , user_id=#{user_id} , "
			+ "app_id=#{app_id} , username=#{username} , taskDescription=#{taskDescription} , "
			+ "taskName=#{taskName} , create_datetime=#{create_datetime} "
			+ "where id=#{id}")
	public int updateProjectAppTask(ProjectAppTask projectAppTask);
	
	@Select("select project_app_task.*,application.app_name , application.is_async "
			+ "from project_app_task , application "
			+ "where project_id=#{project_id} and "
			+ "application.id = project_app_task.app_id and "
			+ "taskName like '%${searchWord}%' "
			+ "order by project_app_task.id desc")
	public List<ProjectAppTask> selectProjectAppTask(
			@Param("project_id")Integer project_id , 
			@Param("searchWord")String searchWord);
	
	@Select("select project_app_task.*,application.result_address "
			+ "from project_app_task,application where project_id=#{project_id} "
			+ "and isRelease=1 and application.id=project_app_task.app_id order by id desc")
	public List<ProjectAppTask> selectReleasedProjectAppTask(@Param("project_id")Integer project_id);
	
	@Select("select * from project_app_task where id=#{id}")
	public ProjectAppTask getProjectAppTask(@Param("id")Integer id);
	
	@Update("update project_app_task set isRelease=#{isRelease} where id=#{id}")
	public int updateIsRelease(@Param("id")Integer id , @Param("isRelease")Integer isRelease);
	
	@Delete("delete from project_app_task where id=#{id}")
	public int deleteProjectAppTask(@Param("id")Integer id);
}
