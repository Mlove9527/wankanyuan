package com.liutianjun.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzjin.model.ProjectCustomRole;
import com.dzjin.model.ProjectUser;
import com.dzjin.service.ProjectCustomRoleService;
import com.dzjin.service.ProjectUserService;
import com.liutianjun.pojo.User;
import com.liutianjun.service.ProjectAppRelationService;

/**
 * 项目应用关系
 * @Title: ProjectAppRelationController.java  
 * @Package com.liutianjun.controller  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年5月17日  
 * @version V1.0
 */
@Controller
@RequestMapping("/ProjectAppRelation")
public class ProjectAppRelationController {

	protected Map<String, Object> resultMap = new HashMap<String, Object>();
	@Autowired
	private ProjectAppRelationService projectAppRelationService;
	
	@Autowired
	private ProjectCustomRoleService projectCustomRoleService;
	@Autowired
	private ProjectUserService projectUserService;
	
	/**
	 * 添加应用到项目
	 * @Title: addToProject 
	 * @param projectId
	 * @param ids
	 * @return 
	 * String
	 */
	@RequestMapping(value="/addToProject",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addToProject(Integer projectId,Integer[] ids,HttpServletRequest request) {
		resultMap.put("status", 400);
		resultMap.put("message", "对不起，您没有权限!");
		User user = (User)request.getAttribute("user");
		//获取项目成员
		ProjectUser projectUser = projectUserService.getProjectUser(projectId, user.getId());
		
		List<ProjectCustomRole> list = projectCustomRoleService.selectProjectCustomRoleByPId(projectId);
		if(list != null && list.size()>0) {
			for (ProjectCustomRole projectCustomRole : list) {
				if(projectUser != null && projectUser.getRole_id() == projectCustomRole.getId()) {
					if(projectCustomRole.getAuthorities() != null){
						String[]auths = projectCustomRole.getAuthorities().split(",");
						if(Arrays.asList(auths).contains("43")) {
							if(0 < projectAppRelationService.insert(projectId, ids)) {
								resultMap.put("status", 200);
								resultMap.put("message", "添加成功!");
							}
							
						}
						
					}
				}
			}
			
		}
		
		return resultMap;
	}
	
	
}
