package com.dzjin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzjin.model.ProjectAuthority;
import com.dzjin.model.ProjectRole;
import com.dzjin.model.ProjectRoleAuthority;
import com.dzjin.service.ProjectRoleService;
import com.liutianjun.pojo.User;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectRoleController 
 * 类描述： 项目默认角色的控制接口，一共包含默认创建者、项目成员以及浏览者三个默认的角色的管理，同时还可以添加其他默认的角色，这个接口是管理后台使用的。
 * 创建人：dzjin 
 * 创建时间：2018年6月19日 上午9:41:56 
 * 修改人：dzjin 
 * 修改时间：2018年6月19日 上午9:41:56 
 * 修改备注： 添加备注信息
 * @version 
 *
 */
@Controller
@RequestMapping("/projectRole")
public class ProjectRoleController {
	
	@Autowired
	ProjectRoleService projectRoleService;
	
	/**
	 * 查询项目默认角色，管理端使用
	 * @param httpSession
	 * @param page	页数
	 * @param stirp	页面大小
	 * @return	管理端角色管理界面
	 */
	@RequestMapping("/selectProjectRole")
	public String selectProjectRole(HttpSession httpSession  , Integer page , Integer stirp){
		if(page == null){
			page=1;
		}
		if(stirp == null){
			stirp=12;
		}
		Map<String, Object> map = projectRoleService.selectProjectRole(page , stirp);
		httpSession.setAttribute("projectRoles", map.get("list"));
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", stirp);
		List<ProjectAuthority> projectAuthorities = projectRoleService.selectProjectAuthority();
		httpSession.setAttribute("projectAuthorities", projectAuthorities);
		return "/admin/projectrolemanage.jsp";
	}
	
	/**
	 * 管理端新增默认角色并赋予指定的权限
	 * @param session
	 * @param role_name	默认角色名称
	 * @param auth_ids	权限列表，以逗号分隔开
	 * @return	新建项目内默认角色姐结果
	 */
	@RequestMapping("/addProjectRole")
	@ResponseBody
	public Map<String, Object> addProjectRole(HttpServletRequest request , HttpSession session , 
			String role_name , String auth_ids){
		
		Map<String, Object> map = new HashMap<String , Object>();
		//不能新建创建者、项目成员以及访问者三个系统配置的角色
		switch (role_name) {
			case "创建者":
				map.put("result", false);
				map.put("message", "创建者角色为系统配置角色，不能新增！");
				return map;
			case "项目成员":
				map.put("result", false);
				map.put("message", "项目成员角色为系统配置角色，不能新增！");		
				return map;
			case "访问者":
				map.put("result", false);
				map.put("message", "访问者角色为系统配置角色，不能新增！");
				return map;
			default:
				break;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ProjectRole projectRole = new ProjectRole();
		projectRole.setRole_name(role_name);
		projectRole.setCreate_datetime(simpleDateFormat.format(new Date()));
		projectRole.setUpdate_datetime(simpleDateFormat.format(new Date()));
		//新建项目默认角色记录
		if(projectRoleService.insertProjectRole(projectRole) == 1){
			int num = 0;
			User user = (User)request.getAttribute("user");
			if(!auth_ids.equals("")){
				String[] ids = auth_ids.split(",");
				//赋予该角色指定的权限
				for (int i = 0 ; i<ids.length ; i++){
					ProjectRoleAuthority projectRoleAuthority = new ProjectRoleAuthority();
					projectRoleAuthority.setUser_id(user.getId());
					projectRoleAuthority.setRole_id(projectRole.getId());
					projectRoleAuthority.setAuthority_id(Integer.valueOf(ids[i]));
					projectRoleAuthority.setBind_datetime(simpleDateFormat.format(new Date()));
					if(projectRoleService.insertProjectRoleAuthority(projectRoleAuthority) == 1){
						num++;
					}
				}
				map.put("result", true);
				map.put("message", num+"条权限赋予成功，"+(ids.length-num)+"条权限赋予失败");
			}else{
				//角色没有权限
				map.put("result", true);
				map.put("message", "角色新建成功");
			}
		}else{
			map.put("result", false);
			map.put("message", "新建角色失败");
		}
		return map;
	}
	
	/**
	 * 批量删除项目默认角色，注意创建者、项目成员以及浏览者角色是不能被删除的
	 * @param session
	 * @param ids	默认角色ID组
	 * @return	删除结果
	 */
	@RequestMapping("/deleteProjectRoles")
	@ResponseBody
	public Map<String, Object> deleteProjectRoles(HttpSession session , String ids){
		String[] p_role_ids = ids.split(",");
		int num = 0;
		for(int i=0;i<p_role_ids.length;i++){
			//系统默认创建者、项目成员以及访问者角色不能删除
			if(!p_role_ids[i].equals("1") && !p_role_ids[i].equals("2") && !p_role_ids[i].equals("3")){
				//删除允许删除的角色
				if(projectRoleService.deleteProjectRole(Integer.valueOf(p_role_ids[i])) == 1){
					num++;
					//删除权限所包含的权限列表
					projectRoleService.deleteProjectRoleAuthorityByRoleId(Integer.valueOf(p_role_ids[i]));
				}
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("result", true);
		map.put("message", num+"位角色成功删除，"+(p_role_ids.length-num)+"位角色删除失败！");
		return map;
	}
	
	/**
	 * 通过ID获取项目默认角色的名称以及对应的权限列表
	 * @param session
	 * @param id	项目默认角色ID
	 * @return
	 */
	@RequestMapping("/getAuthorityByRoleId")
	@ResponseBody
	public Map<String, Object> getAuthorityByRoleId(HttpSession session , Integer id){
		Map<String, Object> map = new HashMap<>();
		ProjectRole projectRole = projectRoleService.getProjectRoleById(id);
		List<ProjectAuthority> projectAuthorities = projectRoleService.selectProjectAuthorityByRoleId(id);
		map.put("id", projectRole.getId());
		map.put("role_name", projectRole.getRole_name());
		map.put("projectAuthorities", projectAuthorities);
		return map;
	}
	
	/**
	 * 更新项目默认角色
	 * @param request
	 * @param session
	 * @param role_id	项目默认角色ID
	 * @param role_name	角色名
	 * @param auth_ids	权限ID组
	 * @return	默认角色更新信息
	 */
	@RequestMapping("/updateProjectRoleAuthority")
	@ResponseBody
	public Map<String, Object> updateProjectRoleAuthority(HttpServletRequest request , HttpSession session , 
			Integer role_id , String role_name , String auth_ids){
		Map<String, Object> map = new HashMap<>();
		//创建者的权限不能更改
		if(role_id == 1){
			map.put("result", false);
			map.put("message", "创建者的权限不能更改");
			return map;
		}
		//更新默认角色的权限
		ProjectRole projectRole = new ProjectRole();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		projectRole.setUpdate_datetime(simpleDateFormat.format(new Date()));
		projectRole.setId(role_id);
		projectRole.setRole_name(role_name);
		if(projectRoleService.updateProjectRole(projectRole) == 1){
			//删除该角色当前含有的所有权限
			projectRoleService.deleteProjectRoleAuthorityByRoleId(role_id);
			//重新赋予权限
			int num = 0;
			User user = (User)request.getAttribute("user");
			if(!auth_ids.equals("")){
				String[] ids = auth_ids.split(",");
				for (int i = 0 ; i<ids.length ; i++){
					ProjectRoleAuthority projectRoleAuthority = new ProjectRoleAuthority();
					projectRoleAuthority.setUser_id(user.getId());
					projectRoleAuthority.setRole_id(projectRole.getId());
					projectRoleAuthority.setAuthority_id(Integer.valueOf(ids[i]));
					projectRoleAuthority.setBind_datetime(simpleDateFormat.format(new Date()));
					if(projectRoleService.insertProjectRoleAuthority(projectRoleAuthority) == 1){
						num++;
					}
				}
				map.put("result", true);
				map.put("message", num+"条权限赋予成功，"+(ids.length-num)+"条权限赋予失败！");
			}else{
				map.put("result", true);
				map.put("message", "角色更新成功");
			}
		}else{
			map.put("result", false);
			map.put("message", "角色更新失败");
		}
		return map;
	}

}
