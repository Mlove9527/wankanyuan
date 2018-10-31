package com.dzjin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzjin.model.Project;
import com.dzjin.model.ProjectCustomRole;
import com.dzjin.model.ProjectUser;
import com.dzjin.service.ProjectUserService;
import com.dzjin.service.ProjectCustomRoleService;
import com.dzjin.service.ProjectRoleService;
import com.liutianjun.pojo.Organization;
import com.liutianjun.pojo.User;
import com.liutianjun.service.OrganizationService;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectMemberController 
 * 类描述： 项目成员controller，包括项目内自定义权限的操作
 * 创建人：dzjin 
 * 创建时间：2018年6月14日 下午1:07:00 
 * 修改人：dzjin 
 * 修改时间：2018年6月14日 下午1:07:00 
 * 修改备注： 
 * @version 
 *
 */
@Controller
@RequestMapping("/projectMember")
public class ProjectMemberController {
	
	@Autowired
	OrganizationService organizationService;
	@Autowired
	ProjectUserService projectUserService;
	@Autowired
	ProjectRoleService projectRoleService;
	@Autowired
	ProjectCustomRoleService projectCustomRoleService;
	
	/**
	 * 获取项目成员列表
	 * @param httpSession
	 * @param page	页数
	 * @param strip	页面
	 * @param searchWord	查询关键字
	 * @param type	列表还是卡片形式
	 * @return
	 */
	@RequestMapping("/selectProjectMember")
	public String selectProjectMember(HttpSession httpSession , 
			Integer page , Integer strip , String searchWord , String type){
		
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip = 12;
		}
		if(searchWord == null){
			searchWord = new String("");
			httpSession.setAttribute("searchWord", searchWord);
		}else{
			httpSession.setAttribute("searchWord", searchWord);
		}
		httpSession.setAttribute("queryCondition", null);//设置筛选条件
		
		//组织结构列表，可能需要修改
		List<Organization> orgList = organizationService.findOrgList(-1);
		httpSession.setAttribute("orgList", orgList);
		
		//当前项目信息
		Project project = (Project)httpSession.getAttribute("project");
		Map<String, Object> map = projectUserService.selectProjectUsers(project.getId(), page, strip , searchWord);
		httpSession.setAttribute("projectMembers", map.get("list"));
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		
		//查询项目自定义角色列表，不包含对应的权限
		httpSession.setAttribute("projectCustomRoles", 
				projectCustomRoleService.selectProjectCustomRoleByPId(project.getId()));

		if(type == null || !type.equals("2")){
			return "/jsp/project/project_member.jsp";
		}else{
			return "/jsp/project/project_member2.jsp";
		}

	}
	
	/**
	 * 根据组ID获取组内成员
	 * @Title: getUserByOrg 
	 * @param organizationId
	 * @return 
	 * String
	 */
	@RequestMapping(value="/getUserByOrg",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getUserByOrg(HttpSession httpSession , Integer organizationId) {
		
		try {
			Project project = (Project)httpSession.getAttribute("project");
			List<User> orgUserList = projectUserService.selectOrganizationNotProjectUser(organizationId, project.getId());

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonOrgUserList = objectMapper.writeValueAsString(orgUserList);
			
			return jsonOrgUserList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	/**
//	 * 
//	 * @param session 
//	 * @param projectMember
//	 * @return
//	 */
//	@RequestMapping("/insertProjectMember")
//	@ResponseBody
//	public Map<String, Object> insertProjectMember(HttpSession session , ProjectUser projectUser){
//		Map<String, Object> map = new HashMap<>();
//		projectUser.setBind_date_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//		ProjectCustomRole projectCustomRole = 
//				projectCustomRoleService.getProjectCustomRoleByRolename("项目成员", projectUser.getProject_id());
//		if(projectCustomRole != null){
//			projectUser.setRole_id(projectCustomRole.getId());//设置项目自定义角色
//			if(projectUserService.insertProjectUser(projectUser) == 1){
//				map.put("result", true);
//				map.put("message", "增加项目成员成功");
//			}else{
//				map.put("result", false);
//				map.put("message", "增加项目成员失败");
//			}
//		}else{
//			map.put("result", false);
//			map.put("message", "项目内缺少自定义项目成员角色，请联系平台管理员");
//		}
//		return map;
//	}
	
	/**
	 * 批量添加项目成员
	 * @param request
	 * @param session
	 * @param ids	待增加成员ID组
	 * @return
	 */
	@RequestMapping("/insertProjectMembers")
	@ResponseBody
	public Map<String, Object> insertProjectMembers(HttpServletRequest request , HttpSession session , String ids){
		//当前用户信息
		User user = (User)request.getAttribute("user");
		//当前项目信息
		Project project = (Project)session.getAttribute("project");
		String[]idString = ids.split(",");
		int num = 0;
		ProjectUser projectMember = null;
		for(int i = 0 ;i<idString.length;i++){
			projectMember = new ProjectUser();
			projectMember.setProject_id(project.getId());
			projectMember.setUser_id(Integer.valueOf(idString[i]));
			projectMember.setLinkman_id(user.getId());	
			projectMember.setBind_date_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			ProjectCustomRole projectCustomRole = 
					projectCustomRoleService.getProjectCustomRoleByRolename("项目成员", project.getId());
			
			if(projectUserService.getProjectUser(projectMember.getProject_id(), projectMember.getUser_id()) == null
					&& projectCustomRole != null){
				projectMember.setRole_id(projectCustomRole.getId());//设置项目自定义成员角色ID
				if(projectUserService.insertProjectUser(projectMember) == 1){
					num++;
				}
			}

		}
		Map<String, Object> map = new HashMap<>();
		map.put("result", true);
		map.put("message", num+"位成员添加成功，"+(idString.length-num)+"位成员添加失败！");
		return map;
	}
	
	/**
	 * 批量删除项目成员
	 * @param ids 项目成员记录ID组
	 * @return
	 */
	@RequestMapping("/deleteProjectMembers")
	@ResponseBody
	public Map<String, Object> deleteProjectMembers(
			HttpSession httpSession , HttpServletRequest request , String ids){
		String[]idString = ids.split(",");
		int num = 0;
		User user = (User)request.getAttribute("user");
		
		Project project = (Project)httpSession.getAttribute("project");
		for(int i = 0 ;i<idString.length;i++){
			ProjectUser projectUser = projectUserService.getProjectUser(project.getId(), user.getId());
			if(projectUser != null && projectUser.getId()!=Integer.valueOf(idString[i])){
				if(projectUserService.deleteProjectUser(Integer.valueOf(idString[i])) == 1){
					num++;
				}
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("result", true);
		map.put("message", num+"位成员移除成功，"+(idString.length-num)+"位成员移除失败");
		return map;
	}
	
	/**
	 * 更新项目成员角色
	 * @param session
	 * @param id	项目成员关系记录
	 * @param role_id	项目内自定义角色ID
	 * @return	更新结果
	 */
	@RequestMapping("/updateProjectUserRole")
	@ResponseBody
	public Map<String, Object> updateProjectUserRole(HttpSession session , 
			Integer id , Integer role_id){
		Map<String, Object> map = new HashMap<>();
		if(projectUserService.updateProjectUserRole(id, role_id) == 1){
			map.put("result", true);
			map.put("message", "角色更新成功");
		}else{
			map.put("result", false);
			map.put("message", "角色更新失败");
		}
		return map;
	}
	
	
	
	
	
	/**
	 * 获取项目内自定义角色的权限
	 * @param session
	 * @param id	自定义角色ID
	 * @return
	 */
	@RequestMapping("/getProjectCustomRoleAuthorities")
	@ResponseBody
	public Map<String, Object> getProjectCustomRoleAuthorities(HttpSession session , Integer id){
		Map<String, Object> map = new HashMap<>();
		ProjectCustomRole projectCustomRole = projectCustomRoleService.getProjectCustomRole(id);
		if(projectCustomRole.getAuthorities() != null){
			String[]auths = projectCustomRole.getAuthorities().split(",");
			for(int i=0 ; i<auths.length ; i++){
				map.put(auths[i], auths[i]);
			}
		}
		return map;
	}
	
	/**
	 * 移除项目自定义角色
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteProjectCustomRole")
	@ResponseBody
	public Map<String, Object> deleteProjectCustomRole(Integer id){
		Map<String, Object> map = new HashMap<String , Object>();
		
		//判断项目内是否含有成员被授予定义角色
		if(projectUserService.countProjectCustomRoleUserNum(id)>0){
			map.put("result", false);
			map.put("message", "当前项目中含有成员被授予待移除的角色，角色不能移除");
			return map;
		}
		
		if(projectCustomRoleService.deleteprojectCustomRole(id) == 1){
			map.put("result", true);
			map.put("message", "项目自定义角色移除成功");
		}else{
			map.put("result", false);
			map.put("message", "项目自定义角色移除失败");
		}		
		return map;
	}
	
	/**
	 * 更新项目自定义角色权限
	 * @param request
	 * @param projectCustomRole
	 * @return
	 */
	@RequestMapping("/updateProjectCustomRole")
	@ResponseBody
	public Map<String, Object> updateProjectCustomRole(HttpServletRequest request , ProjectCustomRole projectCustomRole){
		Map<String, Object> map = new HashMap<>();
		
		User user = (User)request.getAttribute("user");
		projectCustomRole.setUpdater_id(user.getId());
		projectCustomRole.setUpdate_datetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		if(projectCustomRoleService.updateProjectCustomRole(projectCustomRole) == 1){
			map.put("result", true);
			map.put("message", "权限赋予成功");
		}else{
			map.put("result", false);
			map.put("message", "权限赋予失败");
		}		
		return map;
	}
	
	/**
	 * 新增项目内自定义角色
	 * @param request
	 * @param httpSession
	 * @param projectCustomRole
	 * @return
	 */
	@RequestMapping("/insertProjectCustomRole")
	@ResponseBody
	public Map<String, Object> insertProjectCustomRole(
			HttpServletRequest request , HttpSession httpSession , ProjectCustomRole projectCustomRole){
		Map<String, Object> map = new HashMap<>();
		
		if(projectCustomRole.getRolename().equals("创建者") || projectCustomRole.getRolename().equals("项目成员")
				|| projectCustomRole.getRolename().equals("访问者")){
			map.put("result", false);
			map.put("message", "默认自定义角色不能新增");
			return map;
		}
		
		User user = (User)request.getAttribute("user");
		Project project = (Project)httpSession.getAttribute("project");
		projectCustomRole.setP_id(project.getId());
		projectCustomRole.setCreator_id(user.getId());
		projectCustomRole.setCreate_datetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		if(projectCustomRoleService.insertProjectCustomRole(projectCustomRole) == 1){
			map.put("result", true);
			map.put("message", "新增自定义角色成功");
		}else{
			map.put("result", false);
			map.put("message", "新增自定义角色失败");
		}		
		return map;
	}
	
	/**
	 * 获取项目内自定义角色列表
	 * @param request
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/selectProjectCustomRoles")
	@ResponseBody
	public Map<String, Object> selectProjectCustomRoles(
			HttpServletRequest request , HttpSession httpSession){
		Map<String, Object> map = new HashMap<>();
		Project project = (Project)httpSession.getAttribute("project");
		List<ProjectCustomRole> projectCustomRoles = 
				projectCustomRoleService.selectProjectCustomRoleByPId(project.getId());
		Iterator<ProjectCustomRole> iterator = projectCustomRoles.iterator();
		while(iterator.hasNext()){
			ProjectCustomRole projectCustomRole = (ProjectCustomRole)iterator.next();
			map.put(String.valueOf(projectCustomRole.getId()), projectCustomRole.getRolename());
		}
		return map;
	}
	
}
