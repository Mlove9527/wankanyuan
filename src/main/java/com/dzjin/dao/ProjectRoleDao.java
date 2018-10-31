package com.dzjin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dzjin.model.ProjectAuthority;
import com.dzjin.model.ProjectRole;
import com.dzjin.model.ProjectRoleAuthority;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectRoleDao 
 * 类描述： 项目内默认角色dao
 * 创建人：dzjin 
 * 创建时间：2018年6月19日 上午11:29:27 
 * 修改人：dzjin 
 * 修改时间：2018年6月19日 上午11:29:27 
 * 修改备注： 
 * @version 
 *
 */
public interface ProjectRoleDao {
	
	/**
	 * 获取角色列表
	 * @return
	 */
	@Select("select * from project_role")
	public List<ProjectRole> selectProjectRole();
	
	@Select("select * from project_role where id=#{id}")
	public ProjectRole getProjectRoleById(@Param("id")Integer id);
	
	/**
	 * 获取指定角色的权限列表
	 * @param role_id
	 * @return
	 */
	@Select("select project_authority.* "
			+ "from project_authority , project_role_authority "
			+ "where project_role_authority.role_id=#{role_id} and "
			+ "project_role_authority.authority_id = project_authority.id")
	public List<ProjectAuthority> selectProjectAuthorityByRoleId(@Param("role_id")Integer role_id);
	
	/**
	 * 增加角色
	 * @param projectRole
	 * @return
	 */
	@Insert("insert into project_role(role_name,create_datetime,update_datetime) "
			+ "values(#{role_name},#{create_datetime},#{update_datetime})")
	@Options(useGeneratedKeys = true , keyColumn = "id" , keyProperty = "id")
	public int insertProjectRole(ProjectRole projectRole);
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@Delete("delete from project_role where id=#{id}")
	public int deleteProjectRole(@Param("id")Integer id);
	
	@Delete("update project_role set role_name=#{role_name} , update_datetime=#{update_datetime} where id=#{id}")
	public int updateProjectRole(ProjectRole projectRole);
	
	/**
	 * 添加权限到角色
	 * @param projectRoleAuthority
	 * @return
	 */
	@Insert("insert into project_role_authority(role_id,authority_id,bind_datetime,user_id) "
			+ "values(#{role_id},#{authority_id},#{bind_datetime},#{user_id})")
	public int insertProjectRoleAuthority(ProjectRoleAuthority projectRoleAuthority);
	
	/**
	 * 移除权限从角色
	 * @param projectRoleAuthority
	 * @return
	 */
	@Delete("delete from project_role_authority where role_id=#{role_id} and authority_id=#{authority_id}")
	public int deleteProjectRoleAuthority(ProjectRoleAuthority projectRoleAuthority);
	
	/**
	 * 根据角色的ID删除角色所拥有的权限记录
	 * @param id
	 * @return
	 */
	@Delete("delete from project_role_authority where role_id =#{id}")
	public int deleteProjectRoleAuthorityByRoleId(@Param("id")Integer id);
	
	/**
	 * 获取所有权限列表
	 * @return
	 */
	@Select("select * from project_authority")
	public List<ProjectAuthority> selectProjectAuthority();
	
	@Select("select * from project_authority where id=#{id}")
	public ProjectAuthority getProjectAuthority(@Param("id")Integer id);
 
}
