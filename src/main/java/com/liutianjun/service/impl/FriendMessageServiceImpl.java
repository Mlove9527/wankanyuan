package com.liutianjun.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liutianjun.dao.FriendMessageDao;
import com.liutianjun.pojo.FriendMessage;
import com.liutianjun.pojo.FriendMessageQuery;
import com.liutianjun.pojo.FriendMessageQuery.Criteria;
import com.liutianjun.pojo.User;
import com.liutianjun.service.FriendMessageService;
import com.liutianjun.service.UserService;

/**
 * 好友消息Impl
 * @Title: FriendMessageServiceImpl.java  
 * @Package com.liutianjun.service.impl  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年6月7日  
 * @version V1.0
 */
@Service
public class FriendMessageServiceImpl implements FriendMessageService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private FriendMessageDao friendMessageDao;
	
	/**
	 * 发送好友消息
	 * <p>Title: sendFriendMessage</p>  
	 * <p>Description: </p>  
	 * @param username
	 * @param objname
	 * @param content
	 * @return
	 */
	@Override
	public int sendFriendMessage(String username, String objname, String content) {
		User sender = userService.selectByUsername(username);
		User receiver = userService.selectByUsername(objname);
		FriendMessage friendMessage = new FriendMessage();
		friendMessage.setSenderId(sender.getId());
		friendMessage.setSenderName(username);
		friendMessage.setSenderHeadimg(sender.getHeadimg());
		friendMessage.setReceiverId(receiver.getId());
		friendMessage.setReceiverName(objname);
		friendMessage.setReceiverHeadimg(receiver.getHeadimg());
		friendMessage.setContent(content);
		friendMessage.setSendTime(new Date());
		return friendMessageDao.insert(friendMessage);
	}
	
	/**
	 * 群发好友消息
	 * <p>Title: sendMassFriendMessage</p>  
	 * <p>Description: </p>  
	 * @param username
	 * @param ids
	 * @param content
	 * @return
	 */
	@Override
	public int sendMassFriendMessage(String username, Integer[] ids, String content) {
		int i = 0;
		for (Integer id : ids) {
			i += sendFriendMessage(username, userService.selectByPrimaryKey(id).getUsername(), content);
		}
		return i;
	}


	/**
	 * 获取最新的好友消息
	 * <p>Title: getRecentMessage</p>  
	 * <p>Description: </p>  
	 * @param receiverId
	 * @param senderId
	 * @return
	 */
	@Override
	public List<FriendMessage> getFriendRecentMessageList(Integer receiverId, Integer senderId) {
		FriendMessageQuery example = new FriendMessageQuery();
		Criteria criteria = example.createCriteria();
		criteria.andReceiverIdEqualTo(receiverId);
		criteria.andSenderIdEqualTo(senderId);
		example.setOrderByClause("send_time ASC");
		List<FriendMessage> list = friendMessageDao.selectByExample(example);
		return list;
	}

	/**
	 * 获取所有好友的最新消息
	 * <p>Title: findRecentMessageList</p>  
	 * <p>Description: </p>  
	 * @param receiverId
	 * @return
	 */
	@Override
	public List<FriendMessage> findAllRecentMessageList(Integer receiverId) {
		FriendMessageQuery example = new FriendMessageQuery();
		Criteria criteria = example.createCriteria();
		criteria.andReceiverIdEqualTo(receiverId);
		example.setOrderByClause("send_time DESC");
		return friendMessageDao.selectByExample(example);
	}

	/**
	 * 清空我的所有好友消息
	 * <p>Title: cleanAllMyMessages</p>  
	 * <p>Description: </p>  
	 * @param receiverId
	 * @return
	 */
	@Override
	public int cleanAllMyMessages(Integer receiverId) {
		FriendMessageQuery example = new FriendMessageQuery();
		Criteria criteria = example.createCriteria();
		criteria.andReceiverIdEqualTo(receiverId);
		return friendMessageDao.deleteByExample(example);
	}

	/**
	 * 通过主键删除
	 * <p>Title: deleteFriendMessage</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return
	 */
	@Override
	public int deleteFriendMessage(Integer id) {
		if(null != id) {
			return friendMessageDao.deleteByPrimaryKey(id);
		}
		return 0;
	}

}
