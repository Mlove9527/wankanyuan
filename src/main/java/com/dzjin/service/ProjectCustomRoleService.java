package com.dzjin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzjin.dao.ProjectCustomRoleDao;
import com.dzjin.model.ProjectCustomRole;

/**
 * 
 * 项目名称：wankangyuan 类名称：ProjectCustomRoleService 类描述： 项目内实际角色service 创建人：dzjin
 * 创建时间：2018年6月29日 下午3:10:03 修改人：dzjin 修改时间：2018年6月29日 下午3:10:03 修改备注：
 * 
 * @version
 *
 */
@Service
public class ProjectCustomRoleService {

	@Autowired
	ProjectCustomRoleDao projectCustomRoleDao;

	/**
	 * 新增项目内自定义角色
	 * 
	 * @param projectCustomRole
	 * @return
	 */
	public int insertProjectCustomRole(ProjectCustomRole projectCustomRole) {
		return projectCustomRoleDao.insertProjectCustomRole(projectCustomRole);
	}

	public int deleteprojectCustomRole(Integer id) {
		return projectCustomRoleDao.deleteprojectCustomRole(id);
	}

	public List<ProjectCustomRole> selectProjectCustomRoleByPId(Integer p_id) {
		return projectCustomRoleDao.selectProjectCustomRoleByPId(p_id);
	}
	
	public List<ProjectCustomRole> selectProjectCustomRolesByUID(Integer user_id, List<String> authority_numbers) {
		List<ProjectCustomRole> projectCustomRolesTrue=new ArrayList<>();
		List<ProjectCustomRole> projectCustomRoles = projectCustomRoleDao.selectProjectCustomRolesByUID(user_id);
		for (ProjectCustomRole projectCustomRole : projectCustomRoles) {
			if(projectCustomRole.getRolename().equals("创建者")) {
				break;
			}
			List<String> authoritiesList=Arrays.asList(projectCustomRole.getAuthorities().split(","));
			for (String authority_number : authority_numbers) {
				if(authoritiesList.contains(authority_number)) {
					projectCustomRolesTrue.add(projectCustomRole);
					break;
				}
			}
		}
		return projectCustomRolesTrue;
	}

	public List<ProjectCustomRole> selectProjectCustomRolesByUID1(Integer user_id, List<String> authority_numbers) {
		List<ProjectCustomRole> projectCustomRoles = projectCustomRoleDao.selectProjectCustomRolesByUID(user_id);
		for (ProjectCustomRole projectCustomRole : projectCustomRoles) {
			if(projectCustomRole.getRolename().equals("创建者")) {
				break;
			}
			String[] authorities = projectCustomRole.getAuthorities().split(",");
			boolean b = true;
			for (String authority_number : authority_numbers) {
				for (String authority : authorities) {
					if (authority.equals(authority_number)) {
						b = false;
						break;
					}
				}
				if (!b) {
					break;
				}
			}
			if (b) {
				projectCustomRoles.remove(projectCustomRole);
			}
		}
		return projectCustomRoles;
	}

	public ProjectCustomRole selectProjectCustomRolesByUIDPID(Integer user_id, Integer p_id,
			List<String> authority_numbers) {
		ProjectCustomRole projectCustomRole = projectCustomRoleDao.selectProjectCustomRolesByPIDUID(user_id, p_id);
		String[] authorities = projectCustomRole.getAuthorities().split(",");
		for (String authority_number : authority_numbers) {
			for (String authority : authorities) {
				if (authority.equals(authority_number)) {
					return projectCustomRole;
				}
			}
		}
		return null;
	}

	public ProjectCustomRole getProjectCustomRole(Integer id) {
		return projectCustomRoleDao.getProjectCustomRole(id);
	}

	public ProjectCustomRole getProjectCustomRoleByRolename(String rolename, Integer p_id) {
		return projectCustomRoleDao.getProjectCustomRoleByRolename(rolename, p_id);
	}

	public int updateProjectCustomRole(ProjectCustomRole projectCustomRole) {
		return projectCustomRoleDao.updateProjectCustomRole(projectCustomRole);
	}

}
