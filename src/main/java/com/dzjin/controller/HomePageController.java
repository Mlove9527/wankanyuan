package com.dzjin.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzjin.model.Project;
import com.dzjin.model.ProjectAppTask;
import com.dzjin.service.AdminProjectService;
import com.dzjin.service.ProjectAppTaskService;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：HomePageController 
 * 类描述： 
 * 创建人：dzjin 
 * 创建时间：2018年7月12日 下午5:35:31 
 * 修改人：dzjin 
 * 修改时间：2018年7月12日 下午5:35:31 
 * 修改备注： 
 * @version 
 *
 */
@Controller
@RequestMapping("/homepage")
public class HomePageController {
	
	@Autowired
	AdminProjectService  adminProjectService;
	@Autowired
	ProjectAppTaskService projectAppTaskService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectShowProject")
	public String selectShowProject(HttpSession httpSession , Integer page , Integer strip , String searchWord){
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip =6;
		}
		if(searchWord == null){
			searchWord = new String("");
		}
		httpSession.setAttribute("homepageSearchWord", searchWord);

		Map<String, Object> map = adminProjectService.selectIsShowProject(page, strip, searchWord , 1);
		
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		
		List<Project> projects = (List<Project>)map.get("list");
		if(projects.size() > 0){
			Project project = projects.get(0);
			
			List<ProjectAppTask> pAppTasks = projectAppTaskService.selectReleasedProjectAppTask(project.getId());
			//获取当前项目已经发布的应用结果的地址，需要对应用结果地址进行替换
			Iterator<ProjectAppTask> iterator = pAppTasks.iterator();
			while(iterator.hasNext()){
				ProjectAppTask projectAppTask = (ProjectAppTask)iterator.next();
				String resultAddress = projectAppTask.getResult_address();
				//需要对地址参数进行替换
				if(resultAddress != null && !resultAddress.equals("")){
					resultAddress = resultAddress.replace("{taskId}", String.valueOf(projectAppTask.getProject_id()));
					resultAddress = resultAddress.replace("{userid}", String.valueOf(projectAppTask.getUser_id()));
					resultAddress = resultAddress.replace("{username}", String.valueOf(projectAppTask.getUsername()));
					projectAppTask.setResult_address(resultAddress);
				}
			}
			project.setProjectAppTasks(pAppTasks);
		}
		httpSession.setAttribute("showProjects", projects);

		return "/index.jsp";
	}
	
	@RequestMapping("/selectOpenAppTasksByProjectId")
	@ResponseBody
	public Map<String, Object> selectOpenAppTasksByProjectId(HttpSession session , Integer p_id){
		Map<String, Object> map = new HashMap<>();
		List<ProjectAppTask> pAppTasks = projectAppTaskService.selectReleasedProjectAppTask(p_id);
		//获取当前项目已经发布的应用结果的地址，需要对应用结果地址进行替换
		Iterator<ProjectAppTask> iterator = pAppTasks.iterator();
		while(iterator.hasNext()){
			ProjectAppTask projectAppTask = (ProjectAppTask)iterator.next();
			String resultAddress = projectAppTask.getResult_address();
			//需要对地址参数进行替换
			if(resultAddress != null && !resultAddress.equals("")){
				resultAddress = resultAddress.replace("{taskId}", String.valueOf(projectAppTask.getProject_id()));
				resultAddress = resultAddress.replace("{userid}", String.valueOf(projectAppTask.getUser_id()));
				resultAddress = resultAddress.replace("{username}", String.valueOf(projectAppTask.getUsername()));
				projectAppTask.setResult_address(resultAddress);
			}
		}
		//将结果放入到map中，然后返回到前端界面
		if(pAppTasks.size() > 0){
			map.put("message", pAppTasks);
			map.put("result", true);
		}else{
			map.put("message", "未查询到本项目内公开的应用运行结果");
			map.put("result", false);
		}
		return map;
	}


}
