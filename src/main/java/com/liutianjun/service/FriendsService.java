package com.liutianjun.service;

import java.util.List;

import com.liutianjun.pojo.Friends;

public interface FriendsService {

	//添加好友
	int insert(Integer userId,Integer friendId);
	
	//删除好友
	int deleteByUserIdAndFriendsId(Integer userId,Integer[] friendsIds);
	
	//查询我的好友列表
	List<Friends> findAllMyFriends(Integer userId,String friendName, String sysRoles);
	
	//更新我的好友信息
	int updateFriendsInfo(Integer userId);
	
	//互相加好友
	int toBeFriend(Integer userId,Integer objId);
	
}
