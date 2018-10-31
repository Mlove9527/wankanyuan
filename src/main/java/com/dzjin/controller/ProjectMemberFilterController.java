package com.dzjin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzjin.model.Project;
import com.dzjin.model.QueryCondition;
import com.dzjin.service.ProjectCustomRoleService;
import com.dzjin.service.ProjectUserFilterService;
import com.liutianjun.pojo.Organization;
import com.liutianjun.service.OrganizationService;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectMemberFilterController 
 * 类描述： 项目内成员筛选controller
 * 创建人：dzjin 
 * 创建时间：2018年7月15日 下午3:09:53 
 * 修改人：dzjin 
 * 修改时间：2018年7月15日 下午3:09:53 
 * 修改备注： 
 * @version 
 *
 */
@Controller
@RequestMapping("/projectMemberFilter")
public class ProjectMemberFilterController {
	
	@Autowired
	ProjectUserFilterService projectUserFilterService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	ProjectCustomRoleService projectCustomRoleService;
	
	/**
	 * 根据字段名以及过滤条件筛选值
	 * @param session
	 * @param request
	 * @param projectQueryCondition
	 * @return
	 */
	@RequestMapping("/selectProjectMemberDistinctColumnValue")
	@ResponseBody
	public Map<String, Object> selectProjectMemberDistinctColumnValue(
			HttpSession session , HttpServletRequest request , 
			QueryCondition projectQueryCondition){
		Project project = (Project) session.getAttribute("project");
		List<String> strings = 
				projectUserFilterService.selectProjectMemberDistinctColumnValue(
						projectQueryCondition.getColumnName(), project.getId(), projectQueryCondition.getFilter());
		Map<String, Object> map = new HashMap<>();
		map.put("result", true);
		map.put("message", strings);
		return map;
	}

	/**
	 * 根据筛选条件查询项目内成员
	 * @param httpSession
	 * @param page
	 * @param strip
	 * @param searchWord
	 * @param type
	 * @param queryCondition
	 * @return
	 */
	@RequestMapping("/selectProjectMemberByFilterCondition")
	public String selectProjectMemberByFilterCondition(HttpSession httpSession , 
			Integer page , Integer strip , String searchWord , String type , 
			QueryCondition queryCondition){
		
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
		if(queryCondition == null){
			queryCondition = new QueryCondition();
		}
		queryCondition.setStrings(queryCondition.getValues());//将筛选值的字符串形式切割成list形式，便于操作
		httpSession.setAttribute("queryCondition", queryCondition);//设置筛选条件

		Project project = (Project)httpSession.getAttribute("project");
		
		Map<String, Object> map = 
				projectUserFilterService.selectProjectMemberByFilterCondition(
						page, strip, searchWord, queryCondition, project.getId());
		httpSession.setAttribute("projectMembers", map.get("list"));
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		
		//组织结构列表，可能需要修改
		List<Organization> orgList = organizationService.findOrgList(-1);
		httpSession.setAttribute("orgList", orgList);
		
		//查询项目自定义角色列表，不包含对应的权限
		httpSession.setAttribute("projectCustomRoles", 
				projectCustomRoleService.selectProjectCustomRoleByPId(project.getId()));

		if(type == null || !type.equals("2")){
			return "/jsp/project/project_member.jsp";
		}else{
			return "/jsp/project/project_member2.jsp";
		}

	}


}
