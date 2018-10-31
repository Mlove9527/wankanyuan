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
import com.dzjin.service.ProjectAppTaskFilterService;
import com.liutianjun.pojo.User;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectAppTaskFilterController 
 * 类描述： 项目内应用程序运行结果筛选
 * 创建人：dzjin 
 * 创建时间：2018年7月14日 下午10:19:37 
 * 修改人：dzjin 
 * 修改时间：2018年7月14日 下午10:19:37 
 * 修改备注： 
 * @version 
 *
 */
@Controller
@RequestMapping("/projectAppTaskFilter")
public class ProjectAppTaskFilterController {
	
	@Autowired
	ProjectAppTaskFilterService projectAppTaskFilterService;
	
	/**
	 * 根据字段名以及过滤条件筛选值
	 * @param session
	 * @param request
	 * @param projectQueryCondition
	 * @return
	 */
	@RequestMapping("/selectAppTaskDistinctColumnValue")
	@ResponseBody
	public Map<String, Object> selectAppTaskDistinctColumnValue(
			HttpSession session , HttpServletRequest request , 
			QueryCondition projectQueryCondition){
		Project project = (Project) session.getAttribute("project");
		List<String> strings = projectAppTaskFilterService.selectAppTaskDistinctColumnValue(
				projectQueryCondition.getColumnName(), project.getId() , projectQueryCondition.getFilter());
		Map<String, Object> map = new HashMap<>();
		map.put("result", true);
		map.put("message", strings);
		return map;
	}
	
	/**
	 * 根据筛选条件查询应运行结果
	 * @param httpSession
	 * @param request
	 * @param type
	 * @param page
	 * @param strip
	 * @param searchWord
	 * @param queryCondition
	 * @return
	 */
	@RequestMapping("/selectAppTaskByFilterCondition")
	public String selectAppTaskByFilterCondition(HttpSession httpSession , HttpServletRequest request , 
			Integer type , Integer page , Integer strip , String searchWord , 
			QueryCondition queryCondition){
		
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip = 12;
		}
		if(searchWord == null){
			searchWord = new String("");
			httpSession.setAttribute("projectAppTaskSearchWord", searchWord);
		}else{
			httpSession.setAttribute("projectAppTaskSearchWord", searchWord);
		}
		if(queryCondition == null){
			queryCondition = new QueryCondition();
		}
		queryCondition.setStrings(queryCondition.getValues());//将筛选值的字符串形式切割成list形式，便于操作
		httpSession.setAttribute("queryCondition", queryCondition);//设置筛选条件
		
		Project project = (Project) httpSession.getAttribute("project");
		User user = (User)request.getAttribute("user");
		Map<String , Object> map = 
				projectAppTaskFilterService.selectAppTaskByFilterCondition(
						page, strip, searchWord, queryCondition, project.getId(),user);
				
		httpSession.setAttribute("projectAppTasks", map.get("list"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		httpSession.setAttribute("total", map.get("total"));
		
		if(type == null || type == 1){
			return "/jsp/project/project_append.jsp";
		}else{
			return "/jsp/project/project_append2.jsp";
		}
		
	}

}
