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
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzjin.model.Project;
import com.dzjin.model.ProjectTopic;
import com.dzjin.model.ProjectTopicFollow;
import com.dzjin.service.ProjectTopicService;
import com.liutianjun.pojo.User;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectTopicController 
 * 类描述： 项目内话题controller
 * 创建人：dzjin 
 * 创建时间：2018年6月14日 下午1:48:58 
 * 修改人：dzjin 
 * 修改时间：2018年6月14日 下午1:48:58 
 * 修改备注： 
 * @version 
 *
 */
@Controller
@RequestMapping("/projectTopic")
public class ProjectTopicController {

	@Autowired
	ProjectTopicService projectTopicService;
	
	/**
	 * 创建话题
	 * @param projectTopic
	 * @return
	 */
	@RequestMapping("/insertProjectTopic")
	@ResponseBody
	public Map<String, Object> insertProjectTopic(HttpServletRequest httpServletRequest , HttpSession httpSession , 
			ProjectTopic projectTopic){
		User user = (User)httpServletRequest.getAttribute("user");
		//设置创建人的ID
		projectTopic.setUser_id(user.getId());
		Map<String, Object> map =new HashMap<String , Object>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//设置创建时间
		projectTopic.setCreate_datetime(simpleDateFormat.format(new Date()));
		//设置最新发言时间
		projectTopic.setLast_datetime(simpleDateFormat.format(new Date()));
		if(projectTopicService.insertProjectTopic(projectTopic) == 1){
			map.put("result", true);
		}else{
			map.put("result", false);
			map.put("message", "新增话题失败");
		}
		return map;
	}
	
	/**
	 * 获取项目内的话题列表
	 * @param httpServletRequest
	 * @param httpSession
	 * @param page
	 * @param strip
	 * @return
	 */
	@RequestMapping("/selectProjectTopic")
	public String selectProjectTopic(HttpServletRequest httpServletRequest , HttpSession httpSession , 
			Integer page , Integer strip , String timeRadio , String followRadio , String lookRadio){
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip = 12;
		}
		if(timeRadio == null){
			timeRadio = new String("desc");
		}
		if(followRadio == null){
			followRadio = new String("desc");
		}
		if(lookRadio == null){
			lookRadio = new String("desc");
		}
		httpSession.setAttribute("timeRadio", timeRadio);
		httpSession.setAttribute("followRadio", followRadio);
		httpSession.setAttribute("lookRadio", lookRadio);
		//获取当前项目的信息
		Project project = (Project)httpSession.getAttribute("project");
		Map<String, Object> result = projectTopicService.selectProjectTopic(
				page , strip , project.getId() , timeRadio , followRadio , lookRadio);
		httpSession.setAttribute("projectTopics", result.get("list"));
		httpSession.setAttribute("total", result.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("roes", strip);
		return "/jsp/project/project_discuss.jsp";
	}
	
	/**
	 * 删除项目内的话题
	 * @param session
	 * @param project_topic_id 话题ID
	 * @return
	 */
	@RequestMapping("/deleteProjectTopic")
	@ResponseBody
	public Map<String, Object> deleteProjectTopic(HttpSession session , Integer project_topic_id){
		Map<String, Object> map = new HashMap<>();		
		if(projectTopicService.deleteProjectTopic(project_topic_id) == 1){
			map.put("result", true);
		}else{
			map.put("result", false);
			map.put("message", "删除话题失败");
		}
		return map;
	}
	
	/**
	 * 获取主题的详细内容以及主题的跟帖情况,同时获取最新的5条话题用于界面右端的显示
	 * @param httpSession
	 * @param project_topic_id
	 * @param page
	 * @param strip
	 * @return
	 */
	@RequestMapping("/selectProjectTopicFollow")
	public String selectProjectTopicFollow(HttpSession httpSession , 
			Integer project_topic_id , Integer page , Integer strip){
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip = 12;
		}
		httpSession.setAttribute("projectTopic", projectTopicService.getProjectTopicById(project_topic_id));
		projectTopicService.upLookNum(project_topic_id);
		Map<String, Object> map = projectTopicService.selectProjectTopicFollow(project_topic_id, page, strip);
		httpSession.setAttribute("projectTopicFollows", map.get("list"));
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		
		//设置TOP5的话题
		Project project = (Project) httpSession.getAttribute("project");
		Map<String, Object> result = projectTopicService.selectProjectTopic(1, 5, project.getId() , "desc", "desc", "desc");
		httpSession.setAttribute("topProjectTopics", result.get("list"));
		
		
		return "/jsp/project/project_disdetail.jsp";
	}
	
	/**
	 * 新增话题回复
	 * @param projectTopicFollow
	 * @return
	 */
	@RequestMapping("/insertProjectTopicFollow")
	@ResponseBody
	public Map<String, Object> insertProjectTopicFollow(HttpServletRequest httpServletRequest , HttpSession httpSession , 
			ProjectTopicFollow projectTopicFollow){
		User user = (User)httpServletRequest.getAttribute("user");
		projectTopicFollow.setUser_id(user.getId());
		Map<String, Object> map =new HashMap<String , Object>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		projectTopicFollow.setCreate_datetime(simpleDateFormat.format(new Date()));
		if(projectTopicService.insertProjectTopicFollow(projectTopicFollow)== 1){
			//更新话题的跟帖数量
			projectTopicService.upFollowNum(projectTopicFollow.getProject_topic_id());
			//更新话题的最近更贴时间
			ProjectTopic projectTopic = new ProjectTopic();
			projectTopic.setId(projectTopicFollow.getProject_topic_id());
			projectTopic.setLast_datetime(simpleDateFormat.format(new Date()));
			projectTopicService.updateLastDatetime(projectTopic);
			map.put("result", true);
		}else{
			map.put("result", false);
			map.put("message", "话题回复失败");
		}
		return map;
	}
	
	/**
	 * 删除话题回复
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteProjectTopicFollow")
	@ResponseBody
	public Map<String, Object> deleteProjectTopicFollow(HttpSession httpSession , Integer id){
		Map<String, Object> map = new HashMap<>();
		if(projectTopicService.deleteProjectTopicFollow(id) == 1){
			ProjectTopic projectTopic = (ProjectTopic)httpSession.getAttribute("projectTopic");
			projectTopicService.downFollowNum(projectTopic.getId());
			map.put("result", true);
		}else{
			map.put("result", false);
			map.put("message", "删除话题回复失败");
		}
		return map;
	}
	
}
