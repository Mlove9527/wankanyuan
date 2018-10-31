package com.liutianjun.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.liutianjun.pojo.FriendMessage;
import com.liutianjun.pojo.Friends;
import com.liutianjun.pojo.User;
import com.liutianjun.service.FriendMessageService;
import com.liutianjun.service.FriendsService;
import com.liutianjun.service.UserService;

/**
 * 好友消息Controller
 * @Title: FriendMessageController.java  
 * @Package com.liutianjun.controller  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年6月7日  
 * @version V1.0
 */
@Controller
@RequestMapping("/friendMessage")
public class FriendMessageController {
	
	protected Map<String, Object> resultMap = new HashMap<String, Object>();

	@Autowired
	private FriendMessageService friendMessageService;
	
	@Autowired
	private FriendsService friendsService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 好友消息列表
	 * @Title: viewFriendMessage 
	 * @param model
	 * @return 
	 * String
	 */
	@RequestMapping(value="/viewFriendMessage", method=RequestMethod.GET)
	public String viewFriendMessage(Model model) {
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    //获取用户
	    User user = userService.selectByUsername(username);
		List<FriendMessage> list = friendMessageService.findAllRecentMessageList(user.getId());
		model.addAttribute("list", list);
		return "jsp/friends/message_list.jsp";
	}
	
	/**
	 * 获取好友的最新消息
	 * @Title: getRecentMessage 
	 * @param senderId
	 * @return 
	 * String
	 */
	@RequestMapping(value="/getRecentMessage",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getRecentMessage (Integer senderId) {
		try {
			//获取用户名
			String username = (String)SecurityUtils.getSubject().getPrincipal();
			//获取用户
			User user = userService.selectByUsername(username);
			//获取好友的最新消息
			List<FriendMessage> friendRecentMessageList = friendMessageService.getFriendRecentMessageList(user.getId(), senderId);
			if(null != friendRecentMessageList && friendRecentMessageList.size() > 0) {
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonStr = objectMapper.writeValueAsString(friendRecentMessageList);
				for (FriendMessage friendMessage : friendRecentMessageList) {
					friendMessageService.deleteFriendMessage(friendMessage.getId());
				}
				return jsonStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 进入好友聊天页面
	 * @Title: viewSendMessage 
	 * @param objId
	 * @param model
	 * @return 
	 * String
	 */
	@RequestMapping(value="/viewSendMessage", method=RequestMethod.GET)
	public String viewSendMessage(Integer objId, Model model, RedirectAttributes attributes) {
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    //获取用户
	    User user = userService.selectByUsername(username);
	    Boolean isFriend = false;
		List<Friends> friendsList = friendsService.findAllMyFriends(user.getId(), null,null);
		for (Friends friends : friendsList) {
			if(objId == friends.getFriendId()) {
				isFriend = true;
			}
		}
		if(isFriend) {
			/*List<FriendMessage> list = friendMessageService.findAllRecentMessageList(user.getId());
			model.addAttribute("list", list);*/
			User obj = userService.selectByPrimaryKey(objId);
			model.addAttribute("obj", obj);
			return "jsp/friends/message_send.jsp";
		}
		attributes.addFlashAttribute("msg", "你只能和你的好友聊天");
		return "redirect:/friendMessage/viewFriendMessage";
	}
	
	/**
	 * 获取所有好友消息
	 * @Title: getAllMyMessage 
	 * @param id
	 * @return 
	 * String
	 */
	@RequestMapping(value="/getAllMyMessage",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getAllMyMessage (Integer id) {
		List<FriendMessage> list;
		ObjectMapper objectMapper;
		try {
			list = friendMessageService.findAllRecentMessageList(id);
			objectMapper = new ObjectMapper();
			String jsonStr = objectMapper.writeValueAsString(list);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 群发消息
	 * @Title: sendMassFriendMessage 
	 * @param ids
	 * @param content
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/sendMassFriendMessage/{content}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> sendMassFriendMessage(Integer[] ids, @PathVariable String content) {
		resultMap.put("status", 400);
		resultMap.put("message", "发送失败!");
		if(null != ids && ids.length > 0) {
			//获取用户名
		    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    	if(ids.length == friendMessageService.sendMassFriendMessage(username,ids, content)) {
	    		resultMap.put("status", 200);
				resultMap.put("message", "发送成功，共发送"+ids.length+"条!");
	    	}
	    }
		
		return resultMap;
		
	}
	
	/**
	 * 发送好友信息留言
	 * @Title: sendFriendMessage 
	 * @param username
	 * @param Objname
	 * @param content
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/sendFriendMessage/{content}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> sendFriendMessage(String username, String objname, @PathVariable String content) {
		resultMap.put("status", 400);
		resultMap.put("message", "发送失败!");
		if(null != content && content.trim() != "") {
			if(1 == friendMessageService.sendFriendMessage(username,objname, content)) {
				resultMap.put("status", 200);
				resultMap.put("message", "发送成功!");
			}
		}
		return resultMap;
		
	}
	
	/**
	 * 清空所有好友消息
	 * @Title: clearAllMessage 
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/clearAllMessage",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> clearAllMessage() {
		resultMap.put("status", 400);
		resultMap.put("message", "清空失败!");
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    User user = userService.selectByUsername(username);
	    int i = friendMessageService.cleanAllMyMessages(user.getId());
		if(0 < i) {
			resultMap.put("status", 200);
			resultMap.put("message", "清空成功，共计清空"+i+"条消息!");
		}
		return resultMap;
	}
	
}
