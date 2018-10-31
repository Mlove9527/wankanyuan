package com.dzjin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzjin.service.AdminProjectService;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：AdminProjectController 
 * 类描述： 后台项目管理，以及门户页项目展示的相关接口
 * 创建人：dzjin 
 * 创建时间：2018年6月26日 下午5:11:49 
 * 修改人：dzjin 
 * 修改时间：2018年6月26日 下午5:11:49 
 * 修改备注： 
 * @version 
 *
 */
@Controller
@RequestMapping("/adminProject")
public class AdminProjectController {
	
	@Autowired
	AdminProjectService  adminProjectService;
	
	/**
	 * 管理后台查询项目
	 * @param session
	 * @param page 页码
	 * @param strip 页面大小
	 * @param searchWord 查询关键字
	 * @param type 查询类型，0表示查询未公开的项目，1表示查询公开的项目，2或者null表示查询所有的项目
	 * @return
	 */
	@RequestMapping("/selectAdminProject")
	public String selectAdminProject(HttpSession session , Integer page , Integer strip , String searchWord , Integer type){
		
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip = 12;
		}
		if(searchWord == null){
			searchWord = new String("");
			session.setAttribute("adminProjectSearchWord", searchWord);
		}else{
			session.setAttribute("adminProjectSearchWord", searchWord);
		}
		session.setAttribute("adminProjectType", type);
		
		Map<String, Object> map = null;
		if(type == null || type == 2){
			map = adminProjectService.selectProject(page, strip, searchWord);
		}else{
			map = adminProjectService.selectIsShowProject(page, strip, searchWord , type);
		}
		session.setAttribute("adminProjects", map.get("list"));
		session.setAttribute("total", map.get("total"));
		session.setAttribute("page", page);
		session.setAttribute("rows", strip);
		
		return "/admin/promanage.jsp";
	}
	
	/**
	 * 发布项目到门户
	 * @param session
	 * @param ids
	 * @return
	 */
	@RequestMapping("/updateProjectIsShow1")
	@ResponseBody
	public Map<String, Object> updateProjectIsShow1(HttpSession session , String ids){
		Map<String, Object> map = new HashMap<>();
		String[]idStrings = ids.split(",");
		int num = 0;
		for(int i=0;i<idStrings.length;i++){
			if(adminProjectService.updateProjectIsShow1(Integer.valueOf(idStrings[i])) == 1){
				num++;
			}
		}
		map.put("result", true);
		map.put("message", num+"个项目发布到门户成功，"+(idStrings.length-num)+"个项目发布到门户失败！");
		return map;
	}
	
	/**
	 * 取消发布项目到门户
	 * @param session
	 * @param ids
	 * @return
	 */
	@RequestMapping("/updateProjectIsShow0")
	@ResponseBody
	public Map<String, Object> updateProjectIsShow0(HttpSession session , String ids){
		Map<String, Object> map = new HashMap<>();
		String[]idStrings = ids.split(",");
		int num = 0;
		for(int i=0;i<idStrings.length;i++){
			if(adminProjectService.updateProjectIsShow0(Integer.valueOf(idStrings[i])) == 1){
				num++;
			}
		}
		map.put("result", true);
		map.put("message", num+"个项目取消发布到门户成功，"+(idStrings.length-num)+"个项目取消发布到门户失败！");
		return map;
	}
	
	/**
	 * 选择发布到门户的项目
	 * @param httpSession
	 * @param page
	 * @param strip
	 * @return
	 */
	@RequestMapping("/selectShowProject")
	public String selectShowProject(HttpSession httpSession , Integer page , Integer strip){
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip =6;
		}
		Map<String, Object> map = adminProjectService.selectIsShowProject(page, strip, new String("") , 1);
		httpSession.setAttribute("showProjects", map.get("list"));
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		return "/";
	}

}
