package com.dzjin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzjin.model.Project;
import com.dzjin.model.ProjectAppTask;
import com.dzjin.service.ProjectAppTaskService;
import com.liutianjun.pojo.Application;
import com.liutianjun.pojo.User;
import com.liutianjun.service.ApplicationService;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectAppEndController 
 * 类描述： 项目-应用结果controller
 * 创建人：dzjin 
 * 创建时间：2018年6月13日 下午7:50:34 
 * 修改人：dzjin 
 * 修改时间：2018年6月13日 下午7:50:34 
 * 修改备注： 
 * @version 
 *
 */
@Controller
@RequestMapping("/projectAppEnd")
public class ProjectAppTaskController {
	
	@Autowired
	ProjectAppTaskService projectAppTaskService;
	@Autowired
	ApplicationService applicationService;
	
	/**
	 * 查询项目内应用结果
	 * @param httpSession
	 * @param type 返回页面类型，1或者null代表返回到列表页面，2代表返回到返回到卡片界面
	 * @param page 页数
	 * @param strip 页面
	 * @param searchWord 搜索关键字
	 * @return 返回项目内应用结果界面
	 */
	@RequestMapping("/selectProjectAppEnd")
	public String selectProjectAppEnd(HttpSession httpSession , HttpServletRequest request , 
			Integer type , Integer page , Integer strip , String searchWord){
		
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
		httpSession.setAttribute("queryCondition", null);//取消设置筛选条件
		User user = (User)request.getAttribute("user");
		Project project = (Project) httpSession.getAttribute("project");
		Map<String , Object> map = projectAppTaskService.selectProjectAppTask(page, strip, project.getId(),searchWord , user);
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

	/**
	 * 新增项目内应用结果接口，供甲方调用
	 * @param param
	 * @param project_id
	 * @param user_id
	 * @param app_id
	 * @param username
	 * @param taskDescription
	 * @param taskName
	 * @param session
	 * @param request
	 * @return
	 * 测试样例：
	 * http://localhost:8098/wankangyuan/projectAppTask/insertOrUpdateProjectAppEnd?param=参数&project.id=69&userid=1&app.id=169&username=admin&taskDescription=应用描述信息&taskName=应用结果名称
	 */
	@RequestMapping("/insertOrUpdateProjectAppEnd")
	@ResponseBody
	public String insertOrUpdateProjectAppEnd(
			@RequestParam(name = "param")String param ,
			@RequestParam(name = "project.id")String project_id ,
			@RequestParam(name = "userid")String user_id ,
			@RequestParam(name = "app.id")String app_id ,
			@RequestParam(name = "username")String username ,
			@RequestParam(name = "taskDescription")String taskDescription ,
			@RequestParam(name = "taskName")String taskName ,
			HttpSession session ,
			HttpServletRequest request){
		
		ProjectAppTask projectAppTask = new ProjectAppTask();
		projectAppTask.setParam(param);
		projectAppTask.setProject_id(project_id);
		projectAppTask.setUser_id(user_id);
		projectAppTask.setApp_id(app_id);
		projectAppTask.setUsername(username);
		projectAppTask.setTaskDescription(taskDescription);
		projectAppTask.setTaskName(taskName);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		projectAppTask.setCreate_datetime(simpleDateFormat.format(new Date()));
		
		//从请求中获取应用运行结果ID，如果是首次运行，该ID应该是null，如果是重新运行，则该ID不为null
		String id = (String)request.getAttribute("taskId");
		if(id == null){
			//首次运行，向数据库中添加一条应用结果记录
			if(projectAppTaskService.insertProjectAppTask(projectAppTask) == 1){
				return String.valueOf(projectAppTask.getId());
			}else{
				return "failure";
			}
		}else{
			//重新运行，更新传过来的参数
			projectAppTask.setId(id);
			if(projectAppTaskService.updateProjectAppTask(projectAppTask) == 1){
				return String.valueOf(projectAppTask.getId());
			}else{
				return "failure";
			}
		}
	}
	
	/**
	 * 重新运行应用或者查看应用参数
	 * @param httpSession
	 * @param task_id 项目内应用结果ID
	 * @param operType query查看参数；update重新运行应用
	 * @return
	 */
	@RequestMapping("/projectAppReRun")
	@ResponseBody
	public Map<String, Object> projectAppReRun(HttpSession httpSession , Integer task_id , String operType){
		Map<String, Object> map = new HashMap<>();
		ProjectAppTask projectAppTask = projectAppTaskService.getProjectAppTask(task_id);
		Application application = applicationService.selectByPrimaryKey(Integer.valueOf(projectAppTask.getApp_id()));
		if(application == null){
			map.put("result", false);
			map.put("message", "相关应用查询失败");
			return map;
		}
		String paraAddress = application.getParaAddress();
		//对参数参数进行替换并新增结果id以及类型两个参数
		if(paraAddress != null && !paraAddress.equals("")){
			
			paraAddress = paraAddress.replace("{project.id}", String.valueOf(projectAppTask.getProject_id()));
			paraAddress = paraAddress.replace("{userid}", String.valueOf(projectAppTask.getUser_id()));
			paraAddress = paraAddress.replace("{username}", String.valueOf(projectAppTask.getUsername()));
			paraAddress = paraAddress.replace("{app.id}", String.valueOf(projectAppTask.getApp_id()));
			paraAddress += "&taskId="+projectAppTask.getId();
			paraAddress += "&opertype="+operType;

			map.put("result", true);
			map.put("message", paraAddress);
			
		}else{
			map.put("result", false);
			map.put("message", "相关应用参数地址解析失败");
		}
		return map;
	}
	
	/**
	 * 生成应用结果地址
	 * @param httpSession
	 * @param task_id 应用ID
	 * @return
	 */
	@RequestMapping("/projectAppTaskResultEnd")
	@ResponseBody
	public Map<String, Object> projectAppTaskResultEnd(HttpSession httpSession , Integer task_id){
		
		Map<String, Object> map = new HashMap<>();
		ProjectAppTask projectAppTask = projectAppTaskService.getProjectAppTask(task_id);
		Application application = applicationService.selectByPrimaryKey(Integer.valueOf(projectAppTask.getApp_id()));
		if(application == null){
			map.put("result", false);
			map.put("message", "相关应用查询失败");
			return map;
		}
		String resultAddress = application.getResultAddress();
		//需要对地址参数进行替换
		if(resultAddress != null && !resultAddress.equals("")){
			
			resultAddress = resultAddress.replace("{taskId}", String.valueOf(projectAppTask.getProject_id()));
			resultAddress = resultAddress.replace("{userid}", String.valueOf(projectAppTask.getUser_id()));
			resultAddress = resultAddress.replace("{username}", String.valueOf(projectAppTask.getUsername()));
			
			map.put("result", true);
			map.put("message", resultAddress);
		}else{
			map.put("result", false);
			map.put("message", "应用结果地址解析失败");
		}
		return map;
	}
	
	/**
	 * 生成应用结果文件地址
	 * @param httpSession
	 * @param task_id 应用结果ID
	 * @return
	 */
	@RequestMapping("/projectAppTaskResultFile")
	@ResponseBody
	public Map<String, Object> projectAppTaskResultFile(HttpSession httpSession , Integer task_id){
		
		Map<String, Object> map = new HashMap<>();
		ProjectAppTask projectAppTask = projectAppTaskService.getProjectAppTask(task_id);
		Application application = applicationService.selectByPrimaryKey(Integer.valueOf(projectAppTask.getApp_id()));
		if(application == null){
			map.put("result", false);
			map.put("message", "相关应用查询失败");
			return map;
		}
		String fileResultAddress = application.getFileResultAddress();
		//需要对地址进行解析
		if(fileResultAddress != null && !fileResultAddress.equals("")){
			
			fileResultAddress = fileResultAddress.replace("{taskId}", String.valueOf(projectAppTask.getProject_id()));
			fileResultAddress = fileResultAddress.replace("{userid}", String.valueOf(projectAppTask.getUser_id()));
			fileResultAddress = fileResultAddress.replace("{username}", String.valueOf(projectAppTask.getUsername()));
			
			map.put("result", true);
			map.put("message", fileResultAddress);
			
		}else{
			map.put("result", false);
			map.put("message", "应用结果地址解析失败");
		}
		return map;
	}
	
	/**
	 * 发布应用结果
	 * @param session
	 * @param taskIds 应用结果ID数组，以英文逗号分隔
	 * @return
	 */
	@RequestMapping("/projectAppTaskRelease")
	@ResponseBody
	public Map<String, Object> projectAppTaskRelease(HttpSession session  , String taskIds){
		String[] task_ids = taskIds.split(",");
		int num = 0;
		for(int i  = 0 ; i<task_ids.length ; i++){
			if(projectAppTaskService.updateIsRelease(Integer.valueOf(task_ids[i]), 1) == 1){
				num++;
			}
		}
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("result", true);
		map.put("message", num+"条应用结果发布成功，"+(task_ids.length - num) +"条应用结果发布失败");
		return map;
	}
	
	/**
	 * 取消发布应用结果
	 * @param session
	 * @param taskIds 应用结果ID数组，以英文逗号分隔
	 * @return
	 */
	@RequestMapping("/projectAppTaskUnRelease")
	@ResponseBody
	public Map<String, Object> projectAppTaskUnRelease(HttpSession session  , String taskIds){
		String[] task_ids = taskIds.split(",");
		int num = 0;
		for(int i  = 0 ; i<task_ids.length ; i++){
			if(projectAppTaskService.updateIsRelease(Integer.valueOf(task_ids[i]), 0) == 1){
				num++;
			}
		}
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("result", true);
		map.put("message", num+"条应用结果取消发布成功，"+(task_ids.length - num) +"条应用结果取消发布失败");
		return map;
	}
	
	/**
	 * 移除应用结果
	 * @param session
	 * @param taskIds 应用结果ID数组，以英文逗号分隔
	 * @return
	 */
	@RequestMapping("/projectAppTaskDelete")
	@ResponseBody
	public Map<String, Object> projectAppTaskDelete(HttpSession session  , String taskIds){
		String[] task_ids = taskIds.split(",");
		int num = 0;
		for(int i  = 0 ; i<task_ids.length ; i++){
			if(projectAppTaskService.deleteProjectAppTask(Integer.valueOf(task_ids[i])) == 1){
				num++;
			}
		}
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("result", true);
		map.put("message", num+"条应用结果取移除成功，"+(task_ids.length - num) +"条应用结果移除失败");
		return map;
	}
	
}
