package com.dzjin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzjin.model.MyProjectModel;
import com.dzjin.model.QueryCondition;
import com.dzjin.service.ProjectFilterService;
import com.liutianjun.pojo.User;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectFilterController 
 * 类描述： 
 * 创建人：dzjin 
 * 创建时间：2018年7月13日 下午12:34:31 
 * 修改人：dzjin 
 * 修改时间：2018年7月13日 下午12:34:31 
 * 修改备注： 
 * @version 
 *
 */
@Controller
@RequestMapping("/projectFilter")
public class ProjectFilterController {
	
	@Autowired
	ProjectFilterService projectFilterService;
	
	/**
	 * 查询我创建的项目
	 * @param httpSession
	 * @param creator
	 * @param page
	 * @param strip
	 * @param searchWord 查询条件
	 * @return
	 */
	@RequestMapping("/selectCreatedProjectByFilterCondition")
	public String selectCreatedProject(HttpSession httpSession , HttpServletRequest request ,
			Integer page , Integer strip , String searchWord , Integer type , 
			QueryCondition projectQueryCondition){
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip = 12;
		}
		if(searchWord == null){
			searchWord = new String("");
			httpSession.setAttribute("projectSearchWord", null);
		}else{
			//更新关键字
			httpSession.setAttribute("projectSearchWord", searchWord);
		}
		if(projectQueryCondition == null){
			projectQueryCondition = new QueryCondition();
		}
		projectQueryCondition.setStrings(projectQueryCondition.getValues());//将筛选值的字符串形式切割成list形式，便于操作
		httpSession.setAttribute("projectQueryCondition", projectQueryCondition);//设置筛选条件

		//不需要筛选，直接查询
		Map<String, Object> map = new HashMap<String , Object>();
		User user = (User)request.getAttribute("user");
		map = projectFilterService.selectCreatedProjectByFilterCondition(user.getId(), page, strip, searchWord, projectQueryCondition);
		httpSession.setAttribute("projects", map.get("list"));
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
	
		if(type == null || type == 1){
			return "/jsp/project/project_create.jsp";
		}else{
			return "/jsp/project/project_create2.jsp";
		}
	}
	
	/**
	 * 查询我创建的项目
	 * @param httpSession
	 * @param creator
	 * @param page
	 * @param strip
	 * @param searchWord 查询条件
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/selectCreatedProjectByFilterCondition1")
	public String selectCreatedProject1(HttpSession httpSession , HttpServletRequest request ,
			Integer page , Integer strip , String searchWord , Integer type , 
			QueryCondition projectQueryCondition, String pName, String pNumber, String pCreator,
			String createDatetime, String keyWords, String isOpen, String pNameGl, String pNumberGl, String pCreatorGl,
			String createDatetimeGl, String keyWordsGl, String isOpenGl) throws UnsupportedEncodingException{
		isOpen=URLDecoder.decode(isOpen);
		pName=URLDecoder.decode(pName);
		pNumber=URLDecoder.decode(pNumber);
		pCreator=URLDecoder.decode(pCreator);
		keyWords=URLDecoder.decode(keyWords);
		isOpen=URLDecoder.decode(isOpen);
		if(page == null){
			page=1;
		}
		if(strip  == null){
			strip=12;
		}
		if(searchWord  == null){
			searchWord=""; 
			httpSession.setAttribute("projectSearchWord", null);
		}else{
			//更新关键字
			httpSession.setAttribute("projectSearchWord",searchWord);
		}
		if(projectQueryCondition  == null){
			projectQueryCondition=new QueryCondition();
		}
		projectQueryCondition .setStrings(projectQueryCondition .getValues());//将筛选值的字符串形式切割成list形式，便于操作
		httpSession.setAttribute("projectQueryCondition",projectQueryCondition );//设置筛选条件

		//不需要筛选，直接查询
		Map<String, Object> map = new HashMap<String , Object>();
		User user = (User)request.getAttribute("user");
		map = projectFilterService.selectCreatedProjectByFilterCondition1(user.getId(),page,strip ,searchWord , projectQueryCondition, pName, pNumber, pCreator,
				createDatetime, keyWords, isOpen, pNameGl, pNumberGl, pCreatorGl, createDatetimeGl, keyWordsGl, isOpenGl);
		httpSession.setAttribute("projects", map.get("list"));
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		
		httpSession.setAttribute("pName", pName);
		httpSession.setAttribute("pNumber", pNumber);
		httpSession.setAttribute("pCreator", pCreator);
		httpSession.setAttribute("createDatetime", createDatetime);
		httpSession.setAttribute("keyWords", keyWords);
		httpSession.setAttribute("isOpen", isOpen);
		
		httpSession.setAttribute("pNameGl", pNameGl);
		httpSession.setAttribute("pNumberGl", pNumberGl);
		httpSession.setAttribute("pCreatorGl", pCreatorGl);
		httpSession.setAttribute("createDatetimeGl", createDatetimeGl);
		httpSession.setAttribute("keyWordsGl", keyWordsGl);
		httpSession.setAttribute("isOpenGl", isOpenGl);
	
		if(type == null || type == 1){
			return "/jsp/project/project_create.jsp";
		}else{
			return "/jsp/project/project_create2.jsp";
		}
	}
	
	/**
	 * 根据筛选条件查询我的项目
	 * @param httpSession
	 * @param request
	 * @param page
	 * @param strip
	 * @param searchWord
	 * @param type
	 * @param projectQueryCondition
	 * @return
	 */
	@RequestMapping("/selectMineProjectByFilterCondition")
	public String selectMyProject(HttpSession httpSession , HttpServletRequest request , 
			Integer page , Integer strip, String searchWord , Integer type , 
			QueryCondition projectQueryCondition){
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip = 12;
		}
		if(searchWord == null){
			searchWord = new String("");
			httpSession.setAttribute("projectSearchWord", null);
		}else{
			//更新关键字
			httpSession.setAttribute("projectSearchWord", searchWord);
		}
		if(projectQueryCondition == null){
			projectQueryCondition = new QueryCondition();
		}
		projectQueryCondition.setStrings(projectQueryCondition.getValues());//将筛选值的字符串形式切割成list形式，便于操作
		httpSession.setAttribute("projectQueryCondition", projectQueryCondition);//设置筛选条件
		
		User user = (User)request.getAttribute("user");
		Map<String, Object> map = new HashMap<String , Object>();
		map = projectFilterService.selectMineProjectByFilterCondition(user.getId(), page, strip, searchWord, projectQueryCondition);
		httpSession.setAttribute("projects", map.get("list"));
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		if(type == null || type == 1){
			return "/jsp/project/project_mine.jsp";
		}else{
			return "/jsp/project/project_mine2.jsp";
		}
		
	}
	
	/**
	 * 根据筛选条件查询我的项目
	 * @param httpSession
	 * @param request
	 * @param page
	 * @param strip
	 * @param searchWord
	 * @param type
	 * @param projectQueryCondition
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/selectMineProjectByFilterCondition1",produces="text/html;charset=UTF-8")
	public String selectMyProject1(HttpSession httpSession , HttpServletRequest request , HttpServletResponse response,
			Integer page , Integer strip, String searchWord , Integer type , 
			QueryCondition projectQueryCondition, String pName, String pNumber, String pCreator,
			String createDatetime, String keyWords, String isOpen, String pNameGl, String pNumberGl, String pCreatorGl,
			String createDatetimeGl, String keyWordsGl, String isOpenGl) throws UnsupportedEncodingException{
		isOpen=URLDecoder.decode(isOpen);
		pName=URLDecoder.decode(pName);
		pNumber=URLDecoder.decode(pNumber);
		pCreator=URLDecoder.decode(pCreator);
		keyWords=URLDecoder.decode(keyWords);
		isOpen=URLDecoder.decode(isOpen);
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip = 12;
		}
		if(searchWord == null){
			searchWord = new String("");
			httpSession.setAttribute("projectSearchWord", null);
		}else{
			//更新关键字
			httpSession.setAttribute("projectSearchWord", searchWord);
		}
		if(projectQueryCondition == null){
			projectQueryCondition = new QueryCondition();
		}
		projectQueryCondition.setStrings(projectQueryCondition.getValues());//将筛选值的字符串形式切割成list形式，便于操作
		httpSession.setAttribute("projectQueryCondition", projectQueryCondition);//设置筛选条件
		
		User user = (User)request.getAttribute("user");
		Map<String, Object> map = new HashMap<String , Object>();
		map = projectFilterService.selectMineProjectByFilterCondition1(user.getId(), page, strip, searchWord, projectQueryCondition, pName, pNumber, pCreator,
				createDatetime, keyWords, isOpen, pNameGl, pNumberGl, pCreatorGl, createDatetimeGl, keyWordsGl, isOpenGl);
		httpSession.setAttribute("projects", map.get("list"));
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		/*
		 * 在这进行改动
		 */
		httpSession.setAttribute("pName", pName);
		//request.setAttribute("pName", pName);
		httpSession.setAttribute("pNumber", pNumber);
		httpSession.setAttribute("pCreator", pCreator);
		httpSession.setAttribute("createDatetime", createDatetime);
		httpSession.setAttribute("keyWords", keyWords);
		httpSession.setAttribute("isOpen", isOpen);
		
//		httpSession.setAttribute("pNameGl", null);
		httpSession.setAttribute("pNameGl", pNameGl);
		httpSession.setAttribute("pNumberGl", pNumberGl);
		httpSession.setAttribute("pCreatorGl", pCreatorGl);
		httpSession.setAttribute("createDatetimeGl", createDatetimeGl);
		httpSession.setAttribute("keyWordsGl", keyWordsGl);
		httpSession.setAttribute("isOpenGl", isOpenGl);
		
		if(type == null || type == 1){
			return "/jsp/project/project_mine.jsp";
		}else{
			return "/jsp/project/project_mine2.jsp";
		}
		
	}
	
	/**
	 * 根据筛选条件查询公开的项目
	 * @param httpSession
	 * @param page
	 * @param strip
	 * @param searchWord
	 * @param type
	 * @param projectQueryCondition
	 * @return
	 */
	@RequestMapping("/selectPublicProjectByFilterCondition")
	public String selectPublicProject(HttpSession httpSession ,  Integer page , Integer strip, 
			String searchWord , Integer type , QueryCondition projectQueryCondition){		
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip = 12;
		}
		if(searchWord == null){
			searchWord = new String("");
			httpSession.setAttribute("projectSearchWord", null);
		}else{
			//更新关键字
			httpSession.setAttribute("projectSearchWord", searchWord);
		}
		if(projectQueryCondition == null){
			projectQueryCondition = new QueryCondition();
		}
		projectQueryCondition.setStrings(projectQueryCondition.getValues());//将筛选值的字符串形式切割成list形式，便于操作
		httpSession.setAttribute("projectQueryCondition", projectQueryCondition);//设置筛选条件
		
		Map<String, Object> map = new HashMap<String , Object>();
		map = projectFilterService.selectPublicProjectByFilterCondition(page, strip, searchWord, projectQueryCondition);
		httpSession.setAttribute("projects", map.get("list"));
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		
		if(type == null || type == 1){
			return "/jsp/project/project_public.jsp";
		}else{
			return "/jsp/project/project_public2.jsp";
		}
		
	}
	
	/**
	 * 根据筛选条件查询公开的项目
	 * @param httpSession
	 * @param page
	 * @param strip
	 * @param searchWord
	 * @param type
	 * @param projectQueryCondition
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/selectPublicProjectByFilterCondition1")
	public String selectPublicProject1(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession ,  Integer page , Integer strip, 
			String searchWord , Integer type , QueryCondition projectQueryCondition, String pName, String pNumber, String pCreator,
			String createDatetime, String keyWords, String isOpen, String pNameGl, String pNumberGl, String pCreatorGl,
			String createDatetimeGl, String keyWordsGl, String isOpenGl) throws UnsupportedEncodingException{	
		isOpen=URLDecoder.decode(isOpen);
		pName=URLDecoder.decode(pName);
		pNumber=URLDecoder.decode(pNumber);
		pCreator=URLDecoder.decode(pCreator);
		keyWords=URLDecoder.decode(keyWords);
		isOpen=URLDecoder.decode(isOpen);
		response.setContentType("text/html;charset=UTF-8"); //如果是json数据,需要设置为("text/javascript;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip = 12;
		}
		if(searchWord == null){
			searchWord = new String("");
			httpSession.setAttribute("projectSearchWord", null);
		}else{
			//更新关键字
			httpSession.setAttribute("projectSearchWord", searchWord);
		}
		if(projectQueryCondition == null){
			projectQueryCondition = new QueryCondition();
		}
		projectQueryCondition.setStrings(projectQueryCondition.getValues());//将筛选值的字符串形式切割成list形式，便于操作
		httpSession.setAttribute("projectQueryCondition", projectQueryCondition);//设置筛选条件
		
		Map<String, Object> map = new HashMap<String , Object>();
		map = projectFilterService.selectPublicProjectByFilterCondition1(page, strip, searchWord, projectQueryCondition, pName, pNumber, pCreator,
				createDatetime, keyWords, isOpen, pNameGl, pNumberGl, pCreatorGl, createDatetimeGl, keyWordsGl, isOpenGl);
		httpSession.setAttribute("projects", map.get("list"));
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		
		httpSession.setAttribute("pName", pName);
		httpSession.setAttribute("pNumber", pNumber);
		httpSession.setAttribute("pCreator", pCreator);
		httpSession.setAttribute("createDatetime", createDatetime);
		httpSession.setAttribute("keyWords", keyWords);
		httpSession.setAttribute("isOpen", isOpen);
		
		httpSession.setAttribute("pNameGl", pNameGl);
		httpSession.setAttribute("pNumberGl", pNumberGl);
		httpSession.setAttribute("pCreatorGl", pCreatorGl);
		httpSession.setAttribute("createDatetimeGl", createDatetimeGl);
		httpSession.setAttribute("keyWordsGl", keyWordsGl);
		httpSession.setAttribute("isOpenGl", isOpenGl);
		
		if(type == null || type == 1){
			return "/jsp/project/project_public.jsp";
		}else{
			return "/jsp/project/project_public2.jsp";
		}
		
	}
	
	/**
	 * 根据过滤条件筛选某个字段的值我创建的项目
	 * @param session
	 * @param request
	 * @param projectQueryCondition
	 * @return
	 */
	@RequestMapping("/getDistinctColumnValueByColumnNameAndUidCreated")
	@ResponseBody
	public Map<String, Object> getDistinctColumnValueByColumnNameAndUidCreated(
			HttpSession session , HttpServletRequest request , 
			QueryCondition projectQueryCondition){
		User user = (User)request.getAttribute("user");
		List<String> strings = projectFilterService.selectDistinctColumnValueCreated(
				projectQueryCondition.getColumnName(), user.getId() , projectQueryCondition.getFilter());
		Map<String, Object> map = new HashMap<>();
		map.put("result", true);
		map.put("message", strings);
		return map;
	}
	
	/**
	 * 根据过滤条件筛选某个字段的值我创建的项目
	 * @param session
	 * @param request
	 * @param projectQueryCondition
	 * @return
	 */
	@RequestMapping("/getDistinctColumnValueByColumnNameAndUidCreated1")
	@ResponseBody
	public Map<String, Object> getDistinctColumnValueByColumnNameAndUidCreated1(
			HttpSession session , HttpServletRequest request , 
			QueryCondition projectQueryCondition, String pNameGl, String pNumberGl, String pCreatorGl,
			String createDatetimeGl, String keyWordsGl,String pName, String pNumber, String pCreator,
			String createDatetime, String keyWords, String isOpen,@RequestParam(value="pageIndex",defaultValue="1")int  pageIndex,@RequestParam(value="pageSize",defaultValue="10")int pageSize){
		User user = (User)request.getAttribute("user");
		List<String> strings = projectFilterService.selectDistinctColumnValueCreated1(
				projectQueryCondition.getColumnName(), user.getId() , projectQueryCondition.getFilter(), 
				pNameGl, pNumberGl, pCreatorGl, createDatetimeGl, keyWordsGl,pName,pNumber,pCreator,createDatetime,keyWords,isOpen,pageIndex,pageSize);
		Map<String, Object> map = new HashMap<>();
		map.put("result", true);
		map.put("message", strings);
		return map;
	}
	
	/**
	 * 根据过滤条件筛选某个字段的值我的项目
	 * @param session
	 * @param request
	 * @param projectQueryCondition
	 * @return
	 */
	@RequestMapping("/getDistinctColumnValueByColumnNameAndUidMine")
	@ResponseBody
	public Map<String, Object> getDistinctColumnValueByColumnNameAndUidMine(
			HttpSession session , HttpServletRequest request , 
			QueryCondition projectQueryCondition){
		User user = (User)request.getAttribute("user");
		List<String> strings = projectFilterService.selectDistinctColumnValueMine(
				projectQueryCondition.getColumnName(), user.getId() , projectQueryCondition.getFilter());
		Map<String, Object> map = new HashMap<>();
		map.put("result", true);
		map.put("message", strings);
		return map;
	}
	
	
	/**
	 * 根据过滤条件筛选某个字段的值我的项目
	 * @param session
	 * @param request
	 * @param projectQueryCondition
	 * @return
	 */
	@RequestMapping("/getDistinctColumnValueByColumnNameAndUidMine1")
	@ResponseBody
	public Map<String, Object> getDistinctColumnValueByColumnNameAndUidMine1(
			HttpSession session , HttpServletRequest request , 
			QueryCondition projectQueryCondition, String pNameGl, String pNumberGl, String pCreatorGl,
			String createDatetimeGl, String keyWordsGl,String pName, String pNumber, String pCreator,
			String createDatetime, String keyWords, String isOpen,@RequestParam(value="pageIndex",defaultValue="1")int  pageIndex,@RequestParam(value="pageSize",defaultValue="10")int pageSize){
		User user = (User)request.getAttribute("user");
		List<String> strings = projectFilterService.selectDistinctColumnValueMine1(
				projectQueryCondition.getColumnName(), user.getId() , projectQueryCondition.getFilter(), 
				pNameGl, pNumberGl, pCreatorGl, createDatetimeGl, keyWordsGl,pName,pNumber,pCreator,createDatetime,keyWords,isOpen,pageIndex,pageSize);
		Map<String, Object> map = new HashMap<>();
//		session.setAttribute("pName", null);
//		session.setAttribute("pNameGl", null);
		map.put("result", true);
		map.put("message", strings);
		return map;
	}
	
	/**
	 * 根据过滤条件筛选某个字段的值公开的项目
	 * @param session
	 * @param request
	 * @param projectQueryCondition
	 * @return
	 */
	@RequestMapping("/getDistinctColumnValueByColumnNameAndUidPublic")
	@ResponseBody
	public Map<String, Object> getDistinctColumnValueByColumnNameAndUidPublic(
			HttpSession session , HttpServletRequest request , 
			QueryCondition projectQueryCondition){
		List<String> strings = projectFilterService.selectDistinctColumnValuePublic(
				projectQueryCondition.getColumnName(), projectQueryCondition.getFilter());
		Map<String, Object> map = new HashMap<>();
		map.put("result", true);
		map.put("message", strings);
		return map;
	}
	
	/**
	 * 根据过滤条件筛选某个字段的值公开的项目
	 * @param session
	 * @param request
	 * @param projectQueryCondition
	 * @return
	 */
	@RequestMapping("/getDistinctColumnValueByColumnNameAndUidPublic1")
	@ResponseBody
	public Map<String, Object> getDistinctColumnValueByColumnNameAndUidPublic1(
			HttpSession session , HttpServletRequest request , 
			QueryCondition projectQueryCondition, String pNameGl, String pNumberGl, String pCreatorGl,
			String createDatetimeGl, String keyWordsGl,String pName, String pNumber, String pCreator,
			String createDatetime, String keyWords, String isOpen,@RequestParam(value="pageIndex",defaultValue="1")int  pageIndex,@RequestParam(value="pageSize",defaultValue="10")int pageSize){
		List<String> strings = projectFilterService.selectDistinctColumnValuePublic1(
				projectQueryCondition.getColumnName(), projectQueryCondition.getFilter(), 
				pNameGl, pNumberGl, pCreatorGl, createDatetimeGl, keyWordsGl,pName,pNumber,pCreator,createDatetime,keyWords,isOpen,pageIndex,pageSize);
		Map<String, Object> map = new HashMap<>();
		map.put("result", true);
		map.put("message", strings);
		return map;
	}
	
	
}
