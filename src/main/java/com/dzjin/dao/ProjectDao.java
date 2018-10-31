package com.dzjin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dzjin.model.Project;
import com.dzjin.model.ProjectUser;

public interface ProjectDao {
	
	@Insert("insert into project(p_name , p_number , creator , create_datetime , is_asy , key_words) "
			+ "values(#{p_name},#{p_number},#{creator},#{create_datetime},#{is_asy},#{key_words})")
	@Options(useGeneratedKeys = true , keyColumn="id" , keyProperty="id")
	public int insertProject(Project project);
	
	@Select("select * from project where id=#{id}")
	public Project getProjectDetail(@Param("id")Integer id);
	
	@Update("update project set introduction=#{introduction} where id=#{id}")
	public int updateProjectIntroduction(Project project);  
	
	@Update("update project "
			+ "set p_name=#{p_name} , p_number=#{p_number} , is_asy=#{is_asy} , key_words=#{key_words} "
			+ "where id=#{id}")
	public int updateProject(Project project);
	
	/**
	 * 选取公共项目
	 * @return	公共项目列表
	 */
	@Select("SELECT project.*,`user`.username AS creatorName\n" + 
			"FROM project INNER JOIN `user` ON project.creator=`user`.id AND project.is_open=1\n" + 
			"WHERE project.p_name like '%${searchWord}%'\n" + 
			"or project.p_number like '%${searchWord}%' \n" + 
			"or `user`.username like '%${searchWord}%'\n" + 
			"or project.key_words like '%${searchWord}%'\n" + 
			"or project.create_datetime like '%${searchWord}%'\n" + 
			"or project.is_open like '%${searchWord}%' \n" + 
			"order by project.id desc")
	public List<Project> selectPublicProject(@Param("searchWord")String searchWord);
	
	
	/**
	 * 选取公共项目
	 * @return	公共项目列表
	 */
	@Select("select project.* , user.username as creatorName "
			+ "from project,user "
			+ "where project.creator=user.id and is_open=1 and p_name like '%${searchWord}%' "
			+ "${sql}"
			+ "order by id desc")
	public List<Project> selectPublicProject1(@Param("searchWord")String searchWord, @Param("sql")String sql);
	
	/**
	 * 选取我创建的项目
	 * @return	我创建的项目列表
	 */
	@Select("select project.* , `user`.username as creatorName \r\n" + 
			"FROM project INNER JOIN `user` ON project.creator=`user`.id AND project.creator=#{creator}\r\n" + 
			"WHERE project.p_name LIKE '%${searchWord}%'\r\n" + 
			"OR project.p_number LIKE '%${searchWord}%'\r\n" + 
			"OR project.create_datetime LIKE '%${searchWord}%'\r\n" + 
			"OR project.key_words LIKE '%${searchWord}%'\r\n" + 
			" order by id desc"
			) 
	public List<Project> selectCreatedProject(@Param("creator")Integer creator , @Param("searchWord")String searchWord);
	/**
	 * 选取我创建的项目
	 * @return	我创建的项目列表
	 */
	@Select("select project.* , user.username as creatorName "
			+ "from project,user "
			+ "where project.creator=user.id and creator=#{creator} and p_name like '%${searchWord}%'"
			+ "${sql}"
			+ " order by id desc") 
	public List<Project> selectCreatedProject1(@Param("creator")Integer creator , @Param("searchWord")String searchWord, @Param("sql")String sql);
	
	/**
	 * 选取我加入的项目
	 * @return	我加入的项目列表
	 */
	@Select("select DISTINCT project.*,user.username as creatorName FROM project INNER JOIN project_user ON project.id=project_user.project_id and project_user.user_id=#{user_id}\r\n" + 
			"LEFT JOIN `user` ON project.creator=user.id\r\n" + 
			" WHERE project.p_name like '%${searchWord}%'\r\n" + 
			"or project.p_number like '%${searchWord}%' \r\n" + 
			"or `user`.username like '%${searchWord}%'\r\n" + 
			"or project.key_words like '%${searchWord}%'\r\n" + 
			"or project.create_datetime like '%${searchWord}%'\r\n" + 
			"or project.is_open like '%${searchWord}%'\r\n" + 
			"order by project.id desc")
	public List<Project> selectMyProject1(@Param("user_id")Integer user_id , @Param("searchWord")String searchWord);
	
	/**
	 * 选取我加入的项目
	 * @return	我加入的项目列表
	 */
	@Select("select project.*,user.username as creatorName from project , project_user,user "
			+ "where project.id=project_user.project_id "
			+ "and project.creator=user.id "
			+ "and project_user.user_id=#{user_id} "
			+ "and project.p_name like '%${searchWord}%' "
			+ "${sql}"
			+ "order by project.id desc")
	public List<Project> selectMyProject2(@Param("user_id")Integer user_id , @Param("searchWord")String searchWord, @Param("sql")String sql);
	
	
	/**
	 * 选取我加入的项目，用于下拉列表中，因此此处不用进行搜索，直接查询，同时也不需要进行分页，直接全部拉取出来。
	 * @return	我加入的项目列表
	 */
	@Select("select project.*,user.username as creatorName from project , project_user,user "
			+ "where project.creator=user.id "
			+ "and project.id=project_user.project_id "   
			+ "and project_user.user_id=#{user_id} "
			+ "order by project.id desc")
	public List<Project> selectMyProject(@Param("user_id")Integer user_id);
	

	@Select("SELECT project_user.id AS id,project_user.project_id AS project_id,"
			+ "project_user.user_id AS user_id,project_user.role_id AS role_id "
			+ "from project,project_user,user where project.creator=user.id "
			+ "and project.id=project_user.project_id "
			+ "and project_user.user_id=#{user_id} ORDER BY project_id DESC")
	public List<ProjectUser> getProjectUser(@Param("user_id") Integer user_id);

	
	
	
	@Update("insert into project_user(project_id,user_id,bind_date_time,role_id,linkman_id) "
			+ "values(#{project_id},#{user_id},#{bind_date_time},#{role_id},#{linkman_id})")
	public int addPublicProjectToMine(ProjectUser projectUser);
	
	@Select("select * from project_user where project_id=#{project_id} and user_id=#{user_id}")
	public ProjectUser getProjectUserByPidAndUid(ProjectUser projectUser);
 	
	@Update("update project set is_open=#{is_open} where id=#{id}")
	public int updateProjectOpenState(@Param("id")Integer id , @Param("is_open")Integer is_open);
	
	@Delete("delete from project where id=#{id}")
	public int deleteProject(@Param("id")Integer id);
	
	@Delete("delete from project_user where project_id=#{project_id} and user_id=#{user_id}")
	public int deleteProjectUser(ProjectUser projectUser);
	
	@Select("select id from project where p_name=#{p_name} ORDER BY id ASC LIMIT 1")
	public Integer getProjectId(@Param("p_name") String p_name);
	
	
	@Select("select count(project_file.id) from project_floder,project_file where "
			+ "project_floder.p_id=#{id} and "
			+ "project_floder.id=project_file.floder_id")
	public int countProjectFile(@Param("id")Integer id);
	
	@Select("select count(*) from application where id in (select app_id from project_app_relation where project_id=#{id})")
	public int countProjectApp(@Param("id")Integer id);
	
	@Select("select count(*) from project_app_task where project_id=#{id}")
	public int countProjectAppTask(@Param("id")Integer id);
	
	@Select("select count(*) from project_user where project_id=#{id} and role_id!=0")
	public int countProjectUser(@Param("id")Integer id);
	
}
