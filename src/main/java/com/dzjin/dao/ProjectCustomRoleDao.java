package com.dzjin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dzjin.model.ProjectCustomRole;

/**
 * 
 * 项目名称：wankangyuan 类名称：ProjectCustomRoleDao 类描述： 项目内实际角色dao 创建人：dzjin
 * 创建时间：2018年6月29日 下午3:08:46 修改人：dzjin 修改时间：2018年6月29日 下午3:08:46 修改备注：
 * 
 * @version
 *
 */
public interface ProjectCustomRoleDao {

	@Insert("insert into project_custom_role(rolename,p_id,authorities,creator_id,create_datetime) "
			+ "values(#{rolename},#{p_id},#{authorities},#{creator_id},#{create_datetime})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	public int insertProjectCustomRole(ProjectCustomRole projectCustomRole);

	@Delete("delete from project_custom_role where id=#{id}")
	public int deleteprojectCustomRole(@Param("id") Integer id);

	@Select("select project_custom_role.id,project_custom_role.rolename,project_custom_role.p_id,project_custom_role.authorities"
			+ " from project_custom_role where p_id=#{p_id}")
	public List<ProjectCustomRole> selectProjectCustomRoleByPId(@Param("p_id") Integer p_id);

	@Select("SELECT project.id AS p_id, project.p_name, project_custom_role.authorities, project_custom_role.rolename FROM project , project_user , project_custom_role WHERE project.id = project_user.project_id AND project_user.user_id =#{user_id} AND project_custom_role.id = project_user.role_id ORDER BY p_id DESC ")
	public List<ProjectCustomRole> selectProjectCustomRolesByUID(@Param("user_id") Integer user_id);

	@Select("SELECT project.id AS p_id, project.p_name, project_custom_role.authorities, project_custom_role.rolename FROM project , project_user , project_custom_role WHERE project.id =project_user.project_id AND project_user.user_id =#{user_id}  AND p_id =#{p_id} AND project_custom_role.id = project_user.role_id ORDER BY p_id DESC ")
	public ProjectCustomRole selectProjectCustomRolesByPIDUID(@Param("user_id") Integer user_id,@Param("p_id") Integer p_id);

	@Select("select * from project_custom_role where id=#{id}")
	public ProjectCustomRole getProjectCustomRole(@Param("id") Integer id);

	@Select("select * from project_custom_role where rolename=#{rolename} and p_id=#{p_id}")
	public ProjectCustomRole getProjectCustomRoleByRolename(@Param("rolename") String rolename,
			@Param("p_id") Integer p_id);

	@Update("update project_custom_role " + "set authorities=#{authorities} , "
			+ "updater_id=#{updater_id} , update_datetime=#{update_datetime} " + "where id=#{id} and rolename!='创建者'")
	public int updateProjectCustomRole(ProjectCustomRole projectCustomRole);

}
