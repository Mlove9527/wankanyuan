package com.liutianjun.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liutianjun.pojo.Message;
import com.liutianjun.pojo.User;
import com.liutianjun.service.MessageService;
import com.liutianjun.service.UserService;

/**
 * 系统通知管理
 * @Title: MessageController.java  
 * @Package com.liutianjun.controller  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年6月1日  
 * @version V1.0
 */
@Controller
@RequestMapping("/message")
public class MessageController {

	protected Map<String, Object> resultMap = new HashMap<String, Object>();
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 系统通知界面
	 * @Title: viewMessage 
	 * @param model
	 * @return 
	 * String
	 */
	@RequestMapping(value="/viewMessage",method=RequestMethod.GET)
	public String viewMessage(Model model) {
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    //获取用户
	    User user = userService.selectByUsername(username);
		List<Message> list = messageService.findMessageByType(user.getId(), -1);
		
		model.addAttribute("list", list);
		
		return "jsp/message/system_message.jsp";
	}
	
	/**
	 * 根据主键删除通知
	 * @Title: deleteMessageById 
	 * @param id
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/deleteMessage",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteMessageById(Integer id) {
		resultMap.put("status", 400);
		resultMap.put("message", "删除失败!");
		if(1 == messageService.deleteByPrimaryKey(id)) {
			resultMap.put("message", "删除成功!");
			resultMap.put("status", 200);
		}
		return resultMap;
	}
	
	/**
	 * 处理好友请求
	 * @Title: dealFriendRequest 
	 * @param id
	 * @param cmd 0：拒绝 1：通过
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/dealFriendRequest",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> dealFriendRequest(Integer id, Integer cmd) {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
		int i = messageService.dealFriendRequest(id, cmd);
		if(3 == i) {
			resultMap.put("message", "添加好友成功!");
			resultMap.put("status", 200);
		}
		if(1 == i) {
			resultMap.put("message", "拒绝成功!");
			resultMap.put("status", 200);
		}
		return resultMap;
	}
	
	/**
	 * 处理增加新组织请求
	 * @Title: dealAddNewOrgRequest 
	 * @param id
	 * @param cmd
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/dealAddNewOrgRequest",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> dealAddNewOrgRequest(Integer id, Integer cmd) {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
		int i = messageService.dealAddNewOrgRequest(id, cmd);
		if(2 == i) {
			resultMap.put("message", "操作成功!");
			resultMap.put("status", 200);
		}
		return resultMap;
	}
	
	/**
	 * 根据类型清空消息
	 * @Title: clearAllMessageByType 
	 * @param userId
	 * @param type
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/clearAllMessage",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> clearAllMessageByType(Integer userId, Integer type) {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    //获取用户
	    User user = userService.selectByUsername(username);
		if(0 != messageService.clearAllMessageByType(user.getId(), type)) {
			resultMap.put("message", "操作成功!");
			resultMap.put("status", 200);
		}
		return resultMap;
	}
	
	/**
	 * 发送好友请求
	 * @Title: sendFriendRequest 
	 * @param userId
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/sendFriendRequest",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> sendFriendRequest(Integer userId) {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    //获取用户
	    User user = userService.selectByUsername(username);
		if(0 != messageService.sendFriendRequest(userId, user.getId())) {
			resultMap.put("message", "已发送请求,请等待对方同意!");
			resultMap.put("status", 200);
		}
		return resultMap;
	}
	
	/**
	 * 批量发送好友申请
	 * @Title: sendAllFriendRequest 
	 * @param ids
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/sendAllFriendRequest",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> sendAllFriendRequest(Integer[] ids) {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
		//获取用户名
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		//获取用户
		User user = userService.selectByUsername(username);
		int i = 0;
		if(null != ids && ids.length > 0) {
			for (Integer userId : ids) {
				i += messageService.sendFriendRequest(userId, user.getId());
			}
			
			if(i == ids.length) {
				resultMap.put("message", "已发送请求,请等待对方同意!");
				resultMap.put("status", 200);
			}
		}
		return resultMap;
	}
	
}
