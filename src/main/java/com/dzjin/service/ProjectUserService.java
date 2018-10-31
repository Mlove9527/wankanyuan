package com.dzjin.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzjin.dao.ProjectUserDao;
import com.dzjin.model.ProjectUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liutianjun.pojo.User;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectUserService 
 * 类描述： 项目成员service
 * 创建人：dzjin 
 * 创建时间：2018年6月14日 下午1:06:28 
 * 修改人：dzjin 
 * 修改时间：2018年6月14日 下午1:06:28 
 * 修改备注： 
 * @version 
 *
 */
@Service
public class ProjectUserService {
	
	@Autowired
	ProjectUserDao projectUserDao;
	@Autowired
	ProjectFloderService projectFloderService;
	@Autowired
	ProjectFileService projectFileService;
	
	/**
	 * 增加项目成员
	 * @param projectUser
	 * @return
	 */
	public int insertProjectUser(ProjectUser projectUser){
		return projectUserDao.insertProjectUser(projectUser);
	}
	/**
	 * 获取项目内用户列表
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectProjectUsers(Integer id , Integer page , Integer strip , String searchWord){
		PageHelper.startPage(page, strip);
		List<ProjectUser> projectUsers = projectUserDao.selectProjectUsers(id , searchWord);
		Iterator<ProjectUser> iterator = projectUsers.iterator();
		while(iterator.hasNext()){
			ProjectUser projectUser = (ProjectUser)iterator.next();
			projectUser.setFile_num(projectFileService.countProjectFileNumByPidAndUid(id, projectUser.getUser_id()));
			projectUser.setTopic_num(projectUserDao.countProjectUserTopicNum(id, projectUser.getUser_id()));
			projectUser.setTopic_follow_num(projectUserDao.countProjectUserTopicFollowNum(id, projectUser.getUser_id()));	
		}
		PageInfo<ProjectUser> pageInfo = new PageInfo<ProjectUser>(projectUsers);
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("list", projectUsers);
		map.put("total", pageInfo.getTotal());
		return map;
	}
	/**
	 * 获取项目内用户信息
	 * @param project_id
	 * @param user_id
	 * @return
	 */
	public ProjectUser getProjectUser(Integer project_id , Integer user_id){
		return projectUserDao.getProjectUser(project_id, user_id);
	}
	
	/**
	 * 删除项目成员
	 * @param project_id
	 * @param user_id
	 * @return
	 */
	public int deleteProjectUser(Integer project_id , Integer user_id){
		return projectUserDao.deleteProjectUserByUserIdAndProjectId(project_id, user_id);
	}
	 
	/**
	 * 根据关系id删除项目成员
	 * @param id
	 * @return
	 */
	public int deleteProjectUser(Integer id){
		return projectUserDao.deleteProjectUserByRelationId(id);
	}
	
	/**
	 * 获取指定组织非当前成员的用户列表
	 * @param org_id
	 * @param project_id
	 * @return
	 */
	public List<User> selectOrganizationNotProjectUser(Integer org_id , Integer project_id){
		return projectUserDao.selectOrganizationNotProjectUser(org_id, project_id);
	}
	
	/**
	 * 更新项目成员的角色
	 * @param id
	 * @param role_id
	 * @return
	 */
	public int updateProjectUserRole(Integer id , Integer role_id){
		return projectUserDao.updateProjectUserRole(id, role_id);
	}
	
	/**
	 * 获取项目内被授予指定默认角色的成员的数量
	 * @param role_id
	 * @return
	 */
	public int countProjectCustomRoleUserNum(Integer role_id){
		return projectUserDao.countProjectCustomRoleUserNum(role_id);
	}

}
