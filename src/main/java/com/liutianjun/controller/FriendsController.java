package com.liutianjun.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liutianjun.pojo.Friends;
import com.liutianjun.pojo.User;
import com.liutianjun.service.FriendsService;
import com.liutianjun.service.UserService;

/**
 * 好友管理
 * @Title: FriendsController.java  
 * @Package com.liutianjun.controller  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年6月20日  
 * @version V1.0
 */
@Controller
@RequestMapping("/friends")
public class FriendsController {

	protected Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Autowired
	private FriendsService friendsService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 进入好友管理
	 * @Title: viewFriendsManage 
	 * @param model
	 * @return 
	 * String
	 */
	@RequestMapping(value="/viewFriendsManage",method=RequestMethod.GET)
	public String viewFriendsManage() {
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    //获取用户
	    User user = userService.selectByUsername(username);
		//更新好友信息
	    friendsService.updateFriendsInfo(user.getId());
		return "jsp/friends/friend_manage.jsp";
	}
	
	/**
	 * 获取我的好友列表
	 * @Title: getMyFriends 
	 * @return 
	 * String
	 */
	@RequestMapping(value="/getMyFriends",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getMyFriends(String friendName,String sysRoles) {
		try {
			//获取用户名
		    String username = (String)SecurityUtils.getSubject().getPrincipal();
		    //获取用户
		    User user = userService.selectByUsername(username);
			List<Friends> list = friendsService.findAllMyFriends(user.getId(),friendName,sysRoles);
			
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonmyfriendsList = objectMapper.writeValueAsString(list);
			
			return jsonmyfriendsList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 批量移除好友
	 * @Title: removeFriends 
	 * @param ids
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/removeFriends",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> removeFriends(Integer[] ids) {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    //获取用户
	    User user = userService.selectByUsername(username);
	    if(null != ids && ids.length == friendsService.deleteByUserIdAndFriendsId(user.getId(), ids)) {
	    	resultMap.put("status", 200);
			resultMap.put("message", "移除成功!");
	    }
		return resultMap;
	}
	
}
