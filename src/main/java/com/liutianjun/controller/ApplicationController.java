package com.liutianjun.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dzjin.model.ProjectCustomRole;
import com.dzjin.service.ProjectCustomRoleService;
import com.dzjin.service.ProjectService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liutianjun.pojo.Application;
import com.liutianjun.pojo.User;
import com.liutianjun.service.ApplicationService;
import com.liutianjun.service.UserAppRelationService;
import com.liutianjun.service.UserService;

/**
 * 应用控制层
 * @Title: ApplicationController.java  
 * @Package com.liutianjun.controller  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年5月7日  
 * @version V1.0
 */
@Controller
@RequestMapping("/application")
public class ApplicationController {
	
	protected Map<String, Object> resultMap = new HashMap<String, Object>();
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserAppRelationService userAppRelationService;
	
	@Autowired
	ProjectCustomRoleService projectCustomRoleService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 进入我的应用页面
	 * @Title: viewMine 
	 * @param index
	 * @param model
	 * @return 
	 * String
	 */
	@RequiresPermissions("application:view")
	@RequestMapping(value="/viewMine{index}",method=RequestMethod.GET)
	public String viewMine(@PathVariable String index,Model model) {
	    //获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    //获取用户
	    User user = userService.selectByUsername(username);
	    List<String> authority_numbers = new ArrayList<>();
//		authority_numbers.add("30");
		authority_numbers.add("43");
		List<ProjectCustomRole> projects = projectCustomRoleService.selectProjectCustomRolesByUID(user.getId(),
				authority_numbers);
		model.addAttribute("projectList", projects);
		return "jsp/application/app_mine"+index+".jsp";
	}
	
	/**
	 * 获取应用类别列表
	 * @Title: getAppTypeList 
	 * @return
	 * @throws Exception 
	 * String
	 */
	@RequestMapping(value="/getAppTypeList",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getAppTypeList() throws Exception {
		//获取应用筛选列表
		List<String> typeList = applicationService.findTypeList();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(typeList);
	}
	
	/**
	 * 我创建的应用
	 * @Title: viewCreate 
	 * @param index
	 * @param model
	 * @return 
	 * String
	 */
	@RequiresPermissions("application:view")
	@RequestMapping(value="/viewCreate{index}",method=RequestMethod.GET)
	public String viewCreate(@PathVariable String index,Model model) {
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    User user = userService.selectByUsername(username);
		model.addAttribute("projectList", projectService.selectMyProject(user.getId()));
		return "jsp/application/app_create"+index+".jsp";
	}
	
	/**
	 * 获取我创建的应用列表
	 * @Title: getCreate 
	 * @param page
	 * @param rows
	 * @param appName
	 * @param appType
	 * @param orderName
	 * @param orderDir
	 * @param field
	 * @param option
	 * @return
	 * @throws Exception 
	 * String
	 */
	@RequestMapping(value="/getCreate",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getCreate(@RequestParam(value="page", defaultValue="1")Integer page, 
            @RequestParam(value="rows", defaultValue="12")Integer rows, 
            @RequestParam(value="appName",required=false)String appName,
            @RequestParam(value="appType",required=false)String appType,
            @RequestParam(value="orderName",defaultValue="ID")String orderName,
            @RequestParam(value="orderDir",defaultValue="DESC")String orderDir,
            /*@RequestParam(value="field",required=false)String field,
            @RequestParam(value="option",required=false)String[] option*/
            @RequestParam(value="appNameOption",required=false)List<String> appNameOption,
            @RequestParam(value="creatorOption",required=false)List<String> creatorOption,
            @RequestParam(value="isAsyncOption",required=false)List<String> isAsyncOption,
            @RequestParam(value="keywordsOption",required=false)List<String> keywordsOption,
            @RequestParam(value="appIntroOption",required=false)List<String> appIntroOption,
            @RequestParam(value="createTimeOption",required=false)List<String> createTimeOption,
            @RequestParam(value="isDisplayOption",required=false)List<String> isDisplayOption,
            @RequestParam(value="appTypeOption",required=false)List<String> appTypeOption) throws Exception {
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
		Map<String, Object> map = applicationService.findCreate(page,rows,appName,appType,username,orderName +" "+ orderDir,
				appNameOption,creatorOption,isAsyncOption,keywordsOption,appIntroOption,createTimeOption,isDisplayOption,appTypeOption);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		return mapper.writeValueAsString(map);
	}
	
	/**
	 * 获取我创建的应用的字段列表
	 * <p>Title: getAppFiledList</p>  
	 * <p>Description: </p>  
	 * @param field
	 * @param content
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getMyAppFieldList",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getMyAppFiledList(String field,String content,
			@RequestParam(value="appName",required=false)String appName,
            @RequestParam(value="appType",required=false)String appType,
			@RequestParam(value="appNameOption",required=false)List<String> appNameOption,
            @RequestParam(value="creatorOption",required=false)List<String> creatorOption,
            @RequestParam(value="isAsyncOption",required=false)List<String> isAsyncOption,
            @RequestParam(value="keywordsOption",required=false)List<String> keywordsOption,
            @RequestParam(value="appIntroOption",required=false)List<String> appIntroOption,
            @RequestParam(value="createTimeOption",required=false)String createTimeOption,
            @RequestParam(value="isDisplayOption",required=false)List<String> isDisplayOption,
            @RequestParam(value="appTypeOption",required=false)List<String> appTypeOption,@RequestParam(value="pageIndex",defaultValue="1")int  pageIndex,@RequestParam(value="pageSize",defaultValue="10")int pageSize )
	throws Exception {
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		//获取应用筛选列表
		 Map<String, Object> map =  applicationService.findFieldList(field,content,username,appName,appType,
				appNameOption,creatorOption,isAsyncOption,keywordsOption,appIntroOption,createTimeOption,isDisplayOption,appTypeOption,pageIndex,pageSize);
//		typeList.remove(null);
		ObjectMapper mapper = new ObjectMapper();
		//mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		return mapper.writeValueAsString(map);
	}
	
	/**
	 * 获取我创建的应用类别列表
	 * <p>Title: getMyAppTypeList</p>  
	 * <p>Description: </p>  
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getMyAppTypeList",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getMyAppTypeList() throws Exception {
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
		//获取应用筛选列表
		List<String> typeList = applicationService.findAppTypeList(username);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(typeList);
	}
	
	/**
	 * 公共的应用
	 * @Title: viewPublic 
	 * @param index
	 * @param model
	 * @return 
	 * String
	 */
	@RequiresPermissions("application:view")
	@RequestMapping(value="/viewPublic{index}",method=RequestMethod.GET)
	public String viewPublic(@PathVariable String index,Model model) {
		return "jsp/application/app_public"+index+".jsp";
	}
	
	/**
	 * 查询公共的应用列表
	 * <p>Title: getPublic</p>  
	 * <p>Description: </p>  
	 * @param page
	 * @param rows
	 * @param appName
	 * @param appType
	 * @param orderName
	 * @param orderDir
	 * @param field
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPublic",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getPublic(@RequestParam(value="page", defaultValue="1")Integer page, 
            @RequestParam(value="rows", defaultValue="12")Integer rows, 
            @RequestParam(value="appName",required=false)String appName,
            @RequestParam(value="appType",required=false)String appType,
            @RequestParam(value="orderName",defaultValue="ID")String orderName,
            @RequestParam(value="orderDir",defaultValue="DESC")String orderDir,
            /*@RequestParam(value="field",required=false)String field,
            @RequestParam(value="option",required=false)String[] option*/
            @RequestParam(value="appNameOption",required=false)List<String> appNameOption,
            @RequestParam(value="creatorOption",required=false)List<String> creatorOption,
            @RequestParam(value="isAsyncOption",required=false)List<String> isAsyncOption,
            @RequestParam(value="keywordsOption",required=false)List<String> keywordsOption,
            @RequestParam(value="appIntroOption",required=false)List<String> appIntroOption,
            @RequestParam(value="createTimeOption",required=false)List<String> createTimeOption,
            @RequestParam(value="appTypeOption",required=false)List<String> appTypeOption) throws Exception {
		/*if(appNameOption!=null) {
			appNameOption=encode(appNameOption);
		}
		if(creatorOption!=null) {
			creatorOption=encode(creatorOption);
		}
		if(isAsyncOption!=null) {
			isAsyncOption=encode(isAsyncOption);
		}
		if(keywordsOption!=null) {
			keywordsOption=encode(keywordsOption);
		}
		if(appIntroOption!=null) {
			appIntroOption=encode(appIntroOption);
		}
		if(appTypeOption!=null) {
			appTypeOption=encode(appTypeOption);
		}*/
		Map<String, Object> map = applicationService.findPublic(page,rows,appName,appType,orderName +" "+ orderDir,
				appNameOption,creatorOption,isAsyncOption,keywordsOption,appIntroOption,createTimeOption,appTypeOption);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		return mapper.writeValueAsString(map);
	}
	
	/**
	 * 查询公共的字段列表
	 * <p>Title: getPublicAppFiledList</p>  
	 * <p>Description: </p>  
	 * @param field
	 * @param content
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPublicAppFieldList",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getPublicAppFiledList(String field,String content,
			@RequestParam(value="appName",required=false)String appName,
            @RequestParam(value="appType",required=false)String appType,
			@RequestParam(value="appNameOption",required=false)List<String> appNameOption,
            @RequestParam(value="creatorOption",required=false)List<String> creatorOption,
            @RequestParam(value="isAsyncOption",required=false)List<String> isAsyncOption,
            @RequestParam(value="keywordsOption",required=false)List<String> keywordsOption,
            @RequestParam(value="appIntroOption",required=false)List<String> appIntroOption,
            @RequestParam(value="createTimeOption",required=false)String createTimeOption,
            @RequestParam(value="appTypeOption",required=false)List<String> appTypeOption,@RequestParam(value="pageIndex",defaultValue="1")int  pageIndex,@RequestParam(value="pageSize",defaultValue="10")int pageSize) throws Exception {
		
		//获取应用筛选列表
		Map<String, Object> map = applicationService.findPublicFieldList(field,content,appName,appType,
				appNameOption,creatorOption,isAsyncOption,keywordsOption,appIntroOption,createTimeOption,appTypeOption,pageIndex,pageSize);
//		typeList.remove(null);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		return mapper.writeValueAsString(map);
	}
	
	/**
	 * 查询公共的类型列表
	 * <p>Title: getPublicAppTypeList</p>  
	 * <p>Description: </p>  
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPublicAppTypeList",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getPublicAppTypeList() throws Exception {
		//获取应用筛选列表
		List<String> typeList = applicationService.findPublicAppTypeList();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(typeList);
	}
	
	/**
	 * 设置应用属性
	 * @Title: setStatus 
	 * @param cmd
	 * @param id
	 * @return 
	 * String
	 */
	@RequiresPermissions("application:update")
	@RequestMapping(value="/setStatus{index}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> setStatus(Integer cmd,Integer[] ids,
			@PathVariable String index) {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
		if(0 < applicationService.setStatus(cmd, ids)) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功!");
		}
		
		return resultMap;
	}
	
	/**
	 * 创建新应用
	 * @Title: create 
	 * @param record
	 * @return 
	 * String
	 */
	@RequiresPermissions("application:create")
	@RequestMapping(value="/create{index}",method=RequestMethod.POST)
	public String create(Application record,
			@PathVariable String index,
			RedirectAttributes attributes) {
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		User user = userService.selectByUsername(username);
		record.setCreator(username);
		int i = applicationService.insert(record);
		userAppRelationService.addToMineByIds(user.getId(), new Integer[] {record.getId()});
		String msg = "创建失败";
		if(i>0) {
			msg = "创建成功";
		}
		attributes.addFlashAttribute("msg", msg);
		return "redirect:/application/viewCreate"+index;
	}
	
	/**
	 * 应用说明界面
	 * @Title: showExplain 
	 * @param id
	 * @param model
	 * @return 
	 * String
	 */
	@RequiresPermissions("application:view")
	@RequestMapping(value="/explain",method=RequestMethod.GET)
	public String showExplain(Integer id, Model model) {
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		Application application = applicationService.findPublicByid(id);
		if(null == application) {
			Application application2 = applicationService.selectByPrimaryKey(id);
			if(null != application2 && application2.getCreator().equals(username)) {
				application = application2;
			}else {
				model.addAttribute("msg", "应用未公开，或已被删除");
			}
		}
		model.addAttribute("application", application);
		return "jsp/application/app_explain.jsp";
	}
	
	/**
	 * 应用编辑界面
	 * @Title: viewUpdateForm 
	 * @param id
	 * @param model
	 * @param index
	 * @return 
	 * String
	 */
	@RequiresPermissions("application:update")
	@RequestMapping(value="/updateForm{index}",method=RequestMethod.GET)
	public String viewUpdateForm(Integer id, Model model, @PathVariable String index) {
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		Application application = applicationService.selectByPrimaryKey(id);
		if(null != application && application.getCreator().equals(username)) {
			model.addAttribute("application", application);
		}else {
			model.addAttribute("msg", "对不起，您没有权限");
		}
		
		model.addAttribute("index", index);
		return "jsp/application/app_explain2.jsp";
	}
	
	/**
	 * 编辑应用信息
	 * @Title: update 
	 * @param record
	 * @param attributes
	 * @param index
	 * @return 
	 * String
	 */
	@RequiresPermissions("application:update")
	@RequestMapping(value="/update{index}",method=RequestMethod.POST)
	public String update(Application record, 
			RedirectAttributes attributes,
			@PathVariable String index) {
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		String msg = "操作失败";
		Application application = applicationService.selectByPrimaryKey(record.getId());
		if(null != application && application.getCreator().equals(username)) {
			if(0 < applicationService.updateByPrimaryKey(record)) {
				msg = "更新成功";
			}
		}
		attributes.addFlashAttribute("msg", msg);
		return "redirect:/application/viewCreate"+index;
	}
	
	/**
	 * 删除应用
	 * @Title: delete 
	 * @param ids
	 * @param index
	 * @param attributes
	 * @return 
	 * String
	 */
	@RequiresPermissions("application:delete")
	@RequestMapping(value="/delete{index}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete(Integer[] ids, 
			@PathVariable String index) {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
			
		if(0 < applicationService.deleteByIds(ids)) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功!");
		}
		return resultMap;
	}
	private List<String> encode(List<String> values){
		if(values!=null&&values.size()>0) {
			values=values.stream().map(a->{
				String val="";
				try {
					val = new String(a.getBytes("ISO-8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return val;
			}).collect(Collectors.toList());
		}
		return values;
	}
}
