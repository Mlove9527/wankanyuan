package com.dzjin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzjin.dao.ProjectRoleDao;
import com.dzjin.model.ProjectAuthority;
import com.dzjin.model.ProjectRole;
import com.dzjin.model.ProjectRoleAuthority;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectRoleService 
 * 类描述： 项目内默认权限service
 * 创建人：dzjin 
 * 创建时间：2018年6月19日 下午12:04:18 
 * 修改人：dzjin 
 * 修改时间：2018年6月19日 下午12:04:18 
 * 修改备注： 
 * @version 
 *
 */
@Service
public class ProjectRoleService {
	
	@Autowired
	ProjectRoleDao projectRoleDao;
	
	/**
	 * 分页获取默认权限列表
	 * @param page
	 * @param strip
	 * @return
	 */
	public Map<String, Object> selectProjectRole(Integer page , Integer strip){
		PageHelper.startPage(page, strip);
		List<ProjectRole> projectRoles = projectRoleDao.selectProjectRole();
		PageInfo<ProjectRole> pageInfo = new PageInfo<ProjectRole>(projectRoles);
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("list", projectRoles);
		map.put("total", pageInfo.getTotal());
		return map;
	}
	/**
	 * 获取所有项目默认角色
	 * @return
	 */
	public List<ProjectRole> selectProjectRole(){
		return projectRoleDao.selectProjectRole();
	}
	
	/**
	 * 通过ID获取指定默认角色
	 * @param id
	 * @return
	 */
	public ProjectRole getProjectRoleById(Integer id){
		return projectRoleDao.getProjectRoleById(id);
	}
	/**
	 * 获取指定角色的权限列表
	 * @param role_id
	 * @return
	 */
	public List<ProjectAuthority> selectProjectAuthorityByRoleId(Integer role_id){
		return projectRoleDao.selectProjectAuthorityByRoleId(role_id);
	}
	
	/**
	 * 增加默认角色
	 * @param projectRole
	 * @return
	 */
	public int insertProjectRole(ProjectRole projectRole){
		return projectRoleDao.insertProjectRole(projectRole);
	}
	/**
	 * 删除默认角色
	 * @param id
	 * @return
	 */
	public int deleteProjectRole(Integer id){
		return projectRoleDao.deleteProjectRole(id);
	}
	/**
	 * 更新角默认角色
	 * @param projectRole
	 * @return
	 */
	public int updateProjectRole(ProjectRole projectRole){
		return projectRoleDao.updateProjectRole(projectRole);
	}
	
	/**
	 * 默认角色与项目内权限绑定
	 * @param projectRoleAuthority
	 * @return
	 */
	public int insertProjectRoleAuthority(ProjectRoleAuthority projectRoleAuthority){
		return projectRoleDao.insertProjectRoleAuthority(projectRoleAuthority);
	}
	/**
	 * 根据角色的ID删除角色所包含的权限列表
	 * @param id
	 * @return
	 */
	public int deleteProjectRoleAuthorityByRoleId(Integer id){
		return projectRoleDao.deleteProjectRoleAuthorityByRoleId(id);
	}
	
	/**
	 * 获取所有权限列表
	 * @return
	 */
	public List<ProjectAuthority> selectProjectAuthority(){
		return projectRoleDao.selectProjectAuthority();
	}
	/**
	 * 通过ID获取指定的权限
	 * @param id
	 * @return
	 */
	public ProjectAuthority getProjectAuthority(Integer id){
		return projectRoleDao.getProjectAuthority(id);
	}

}
