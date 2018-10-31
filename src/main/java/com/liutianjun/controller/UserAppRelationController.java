package com.liutianjun.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liutianjun.pojo.User;
import com.liutianjun.pojo.UserAppRelation;
import com.liutianjun.service.UserAppRelationService;
import com.liutianjun.service.UserService;

/**
 * 用户应用关系
 * @Title: UserAppRelationController.java  
 * @Package com.liutianjun.controller  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年5月9日  
 * @version V1.0
 */
@Controller
@RequestMapping("/userAppRelation")
public class UserAppRelationController {
	
	protected Map<String, Object> resultMap = new HashMap<String, Object>();

	@Autowired
	private UserAppRelationService userAppRelationService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 添加应用到我的
	 * @Title: addToMine 
	 * @param httpSession
	 * @param ids
	 * @return 
	 * String
	 */
	@RequestMapping(value="/addToMine{index}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addToMine(Integer[] ids,
			@PathVariable String index) {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    User user = userService.selectByUsername(username);
		if(0 < userAppRelationService.addToMineByIds(user.getId(), ids)) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功!");
		}
		return resultMap;
	}
	
	/**
	 * 从我的中删除应用
	 * @Title: removeFromMine 
	 * @param httpSession
	 * @param ids
	 * @return 
	 * String
	 */
	@RequestMapping(value="/removeFromMine{index}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> removeFromMine(Integer[] ids,
			@PathVariable String index) {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
//        User user = userService.selectByUsername(username);
        int selectUseridByusername = userService.selectUseridByusername(username);
		/*
		 * 在这里添加  通过项目id来判断创建人是不是上面的user  
		 *   是的话 则将该id在数组中删除
		 *   然后再进行下面的删除操作
		 */
        UserAppRelation u=new UserAppRelation();
        u.setUserId(selectUseridByusername);
      /*  System.out.println(username);
        System.out.println(selectUseridByusername);//null
*/        /*
         * 这里再写个获取用户id的方法 
         */
        for (int i = 0; i < ids.length; i++) {
        	u.setAppId(ids[i]);
			UserAppRelation selectcreatorbyappid = userAppRelationService.selectcreatorbyappid(u);
			/*System.out.println(selectcreatorbyappid.getCreator());
			System.out.println(selectcreatorbyappid.getCreator().equals(username));*/
			if(selectcreatorbyappid.getCreator().equals(username)) {
				int deletefromuserapp = userAppRelationService.deletefromuserapp(u);
				if(0 < deletefromuserapp) {
					resultMap.put("status", 200);
					resultMap.put("message", "操作成功!");
				}
			}
		}
        
		return resultMap;
	}
	
	/**
	 * 获取我的应用列表
	 * @Title: getMine 
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
	@RequestMapping(value="/getMine",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getMine(@RequestParam(value="page", defaultValue="1")Integer page, 
            @RequestParam(value="rows", defaultValue="12")Integer rows, 
            @RequestParam(value="appName",required=false)String appName,
            @RequestParam(value="appType",required=false)String appType,
            @RequestParam(value="orderName",defaultValue="ID")String orderName,
            @RequestParam(value="orderDir",defaultValue="DESC")String orderDir,
           
            @RequestParam(value="appNameOption",required=false)List<String> appNameOption,
            @RequestParam(value="creatorOption",required=false)List<String> creatorOption,
            @RequestParam(value="isAsyncOption",required=false)List<String> isAsyncOption,
            @RequestParam(value="keywordsOption",required=false)List<String> keywordsOption,
            @RequestParam(value="appIntroOption",required=false)List<String> appIntroOption,
            @RequestParam(value="createTimeOption",required=false)String createTimeOption,
            @RequestParam(value="appTypeOption",required=false)List<String> appTypeOption,HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    //获取用户
	    User user = userService.selectByUsername(username);
		
	    //Map<String, Object> map = userAppRelationService.findMine(page,rows,appName,appType,user.getId(),orderName +" "+ orderDir,field,option);
	    Map<String, Object> map = userAppRelationService.findMine(page,rows,appName,appType,user.getId(),
	    		orderName +" "+ orderDir,appNameOption,creatorOption,isAsyncOption,keywordsOption,appIntroOption,createTimeOption,appTypeOption, appTypeOption);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"));
		return mapper.writeValueAsString(map);
	}
	
	/**
	 * 获取应用类别列表
	 * @Title: getAppTypeList 
	 * @return
	 * @throws Exception 
	 * String
	 */
	@RequestMapping(value="/getMyAppTypeList",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getMyAppTypeList() throws Exception {
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    //获取用户
	    User user = userService.selectByUsername(username);
		//获取应用筛选列表
		List<String> typeList = userAppRelationService.findMyTypeList(user.getId());
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(typeList);
	}
	
	/**
	 * 获取应用类别列表
	 * @Title: getAppTypeList 
	 * @return
	 * @throws Exception 
	 * String
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
            @RequestParam(value="createTimeOption",required=false)List<String> createTimeOption,
            @RequestParam(value="appTypeOption",required=false)List<String> appTypeOption,
            @RequestParam(value="pageIndex",defaultValue="1")int pageIndex,@RequestParam(value="pageSize",defaultValue="12")int pageSize) throws Exception {
	
		
		String username = (String)SecurityUtils.getSubject().getPrincipal();
        User user = userService.selectByUsername(username);
        List<UserAppRelation> typeList =new ArrayList<>();
		//获取应用筛选列表
        Map<String, Object> map = userAppRelationService.findFileList(field,content,user.getId(),appName,appType,
				appNameOption,creatorOption,isAsyncOption,keywordsOption,appIntroOption,createTimeOption,appTypeOption,pageIndex,pageSize);
        typeList=(List<UserAppRelation>) map.get("data");
		if(typeList.contains(null)) {
			typeList.add(new UserAppRelation());
		}
		typeList.remove(null);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.setDateFormat(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"));
		return mapper.writeValueAsString(map);
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
