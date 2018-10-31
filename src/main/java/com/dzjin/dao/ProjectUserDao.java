package com.dzjin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dzjin.model.ProjectUser;
import com.liutianjun.pojo.User;

/**
 * 
 * 项目名称：wankangyuan 类名称：ProjectMemberDao 类描述： 项目内成员dao 创建人：dzjin 创建时间：2018年6月23日
 * 上午12:11:14 修改人：dzjin 修改时间：2018年6月23日 上午12:11:14 修改备注：
 * 
 * @version
 *
 */
public interface ProjectUserDao {

	@Insert("insert into project_user(project_id,user_id,linkman_id,bind_date_time,role_id) "
			+ "values(#{project_id},#{user_id},#{linkman_id},#{bind_date_time},#{role_id})")
	public int insertProjectUser(ProjectUser projectMember);

	/**
	 * 获取项目内用户列表
	 * 
	 * @param id
	 * @return
	 */
	@Select("select project_user.* ,project_custom_role.rolename as role_name , u1.username , u2.username as linkman_username "
			+ "from project_user,user u1 , user u2 , project_custom_role "
			+ "where project_user.user_id = u1.id and project_user.linkman_id = u2.id and project_user.role_id = project_custom_role.id and project_id=#{id} and u1.username like '%${searchWord}%' order by id desc")
	public List<ProjectUser> selectProjectUsers(@Param("id") Integer id, @Param("searchWord") String searchWord);

	/**
	 * 获取项目内用户信息
	 * 
	 * @param project_id
	 * @param user_id
	 * @return
	 */
	@Select("select * from project_user where project_id=#{project_id} and user_id=#{user_id}")
	public ProjectUser getProjectUser(@Param("project_id") Integer project_id, @Param("user_id") Integer user_id);

	/**
	 * 删除项目成员
	 * 
	 * @param project_id
	 * @param user_id
	 * @return
	 */
	@Delete("delete from project_user where projct_id=#{project_id} and user_id=#{user_id}")
	public int deleteProjectUserByUserIdAndProjectId(@Param("project_id") Integer project_id,
			@Param("user_id") Integer user_id);

	/**
	 * 根据关系ID删除项目成员
	 * 
	 * @param id
	 * @return
	 */
	@Delete("delete from project_user where id=#{id}")
	public int deleteProjectUserByRelationId(@Param("id") Integer id);

	/**
	 * 获取指定组织非当前项目成员列表
	 * 
	 * @param org_id
	 * @param project_id
	 * @return
	 */
	@Select("select user.* from user where user.organization_id=#{org_id} and user.id not in (select user_id from project_user where project_id=#{project_id})")
	public List<User> selectOrganizationNotProjectUser(@Param("org_id") Integer org_id,
			@Param("project_id") Integer project_id);

	@Update("update project_user set role_id=#{role_id} where id=#{id}")
	public int updateProjectUserRole(@Param("id") Integer id, @Param("role_id") Integer role_id);

	/**
	 * 获取项目内成员文件上传数量
	 * 
	 * @param project_id
	 * @param user_id
	 * @return
	 */
	@Select("select count(project_file.id) from project_file , project_floder "
			+ "where project_floder.p_id=#{project_id} " + "and project_file.floder_id=project_floder.id "
			+ "and project_file.creator_id=#{user_id}")
	public int countProjectUserFileNum(@Param("project_id") Integer project_id, @Param("user_id") Integer user_id);

	/**
	 * 获取项目内成员发帖数量
	 * 
	 * @param project_id
	 * @param user_id
	 * @return
	 */
	@Select("select count(*) from project_topic where project_topic.project_id=#{project_id} and project_topic.user_id=#{user_id}")
	public int countProjectUserTopicNum(@Param("project_id") Integer project_id, @Param("user_id") Integer user_id);

	/**
	 * 获取项目内成员跟帖数量
	 * 
	 * @param project_id
	 * @param user_id
	 * @return
	 */
	@Select("select count(project_topic_follow.id) from project_topic,project_topic_follow "
			+ "where project_topic.project_id=#{project_id} " + "and project_topic_follow.user_id=#{user_id} "
			+ "and project_topic_follow.project_topic_id=project_topic.id")
	public int countProjectUserTopicFollowNum(@Param("project_id") Integer project_id,
			@Param("user_id") Integer user_id);

	/**
	 * 获取项目内被授予指定默认角色的成员的数量
	 * 
	 * @param role_id
	 * @return
	 */
	@Select("select count(*) from project_user where role_id=#{role_id}")
	public int countProjectCustomRoleUserNum(@Param("role_id") Integer role_id);

}
