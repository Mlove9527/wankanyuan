package com.liutianjun.service;

import java.util.List;

import com.liutianjun.pojo.FriendMessage;

public interface FriendMessageService {

	//发送新的消息
	int sendFriendMessage(String username, String objname, String content);
	
	//群发消息
	int sendMassFriendMessage(String username, Integer[] ids, String content);
	
	//获取好友的留言
	List<FriendMessage> getFriendRecentMessageList(Integer receiverId,Integer senderId);
	
	//获取最新消息列表
	List<FriendMessage> findAllRecentMessageList(Integer receiverId);
	
	//清空所有消息
	int cleanAllMyMessages(Integer receiverId);
	
	//通过主键删除消息
	int deleteFriendMessage(Integer id);

	
}
