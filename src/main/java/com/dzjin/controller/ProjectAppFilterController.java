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
import com.dzjin.model.ProjectAppFilterRequestModel;
import com.dzjin.model.QueryCondition;
import com.dzjin.service.ProjectAppFilterService;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectAppFilterController 
 * 类描述： 项目内应用筛选controller
 * 创建人：dzjin 
 * 创建时间：2018年7月15日 下午5:06:51 
 * 修改人：dzjin 
 * 修改时间：2018年7月15日 下午5:06:51 
 * 修改备注： 
 * @version 
 *
 */
@Controller
@RequestMapping("/projectAppFilter")
public class ProjectAppFilterController {
	
	@Autowired
	ProjectAppFilterService projectAppFilterService;
	
	/**
	 * 根据字段名以及过滤条件筛选值
	 * @param session
	 * @param request
	 * @param projectQueryCondition
	 * @return
	 */
	@RequestMapping("/selectAppDistinctColumnValue")
	@ResponseBody
	public Map<String, Object> selectAppTaskDistinctColumnValue(
			HttpSession session , HttpServletRequest request , 
			QueryCondition projectQueryCondition,ProjectAppFilterRequestModel projectAppFilterRequest){
		Project project = (Project) session.getAttribute("project");
		List<String> strings = projectAppFilterService.selectAppDistinctColumnValue(
				projectQueryCondition.getColumnName(), project.getId() , projectQueryCondition.getFilter(),projectAppFilterRequest);
		Map<String, Object> map = new HashMap<>();
		map.put("result", true);
		map.put("message", strings);
		return map;
	}
	
	
	/**
	 * 
	 * @param httpSession
	 * @param p_id
	 * @param page
	 * @param strip
	 * @param searchWord
	 * @param type
	 * @param queryCondition
	 * @return
	 */
	@RequestMapping("/selectAppByFilterCondition")
	public String selectAppByFilterCondition(HttpSession httpSession , Integer p_id , Integer page , Integer strip ,
			String searchWord , Integer type , QueryCondition queryCondition,ProjectAppFilterRequestModel projectAppFilterRequest){
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip = 12;
		}
		if(searchWord == null){
			searchWord = new String("");
			httpSession.setAttribute("projectAppSearchWord", searchWord);
		}else{
			httpSession.setAttribute("projectAppSearchWord", searchWord);
		}
		if(queryCondition == null){
			queryCondition = new QueryCondition();
		}
		queryCondition.setStrings(queryCondition.getValues());//将筛选值的字符串形式切割成list形式，便于操作
		httpSession.setAttribute("queryCondition", queryCondition);//设置筛选条件
		
		Project project = (Project)httpSession.getAttribute("project");
		
		Map<String, Object> map = projectAppFilterService.selectAppByFilterCondition(page, strip, searchWord, queryCondition, project.getId());
		httpSession.setAttribute("projectApplications", map.get("list"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		httpSession.setAttribute("total", map.get("total"));
		
		httpSession.setAttribute("projectAppFilterRequest", projectAppFilterRequest);
		/*httpSession.setAttribute("page", page);
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("page", page);*/
		if(type == null || type == 1){
			return "/jsp/project/project_app.jsp";
		}else{
			return "/jsp/project/project_app2.jsp";
		}	
	}
	
	
	

}
