package com.liutianjun.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liutianjun.dao.MessageDao;
import com.liutianjun.dao.UserDao;
import com.liutianjun.pojo.Message;
import com.liutianjun.pojo.MessageQuery;
import com.liutianjun.pojo.MessageQuery.Criteria;
import com.liutianjun.pojo.Organization;
import com.liutianjun.pojo.User;
import com.liutianjun.service.FriendsService;
import com.liutianjun.service.MessageService;
import com.liutianjun.service.OrganizationService;

/**
 * 消息服务
 * @Title: MessageServiceimpl.java  
 * @Package com.liutianjun.service.impl  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年5月31日  
 * @version V1.0
 */
@Service
public class MessageServiceimpl implements MessageService {

	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private FriendsService friendsService;
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * 发送系统消息
	 * <p>Title: sendSysMessage</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param content
	 * @return
	 */
	@Override
	public int sendSysMessage(Integer userId, String content) {
		Message message = new Message();
		message.setCreateTime(new Date());
		message.setUserId(userId);
		message.setContent(content);
		message.setType(0);
		return messageDao.insert(message);
	}
	
	/**
	 * 发送组织新增申请
	 * <p>Title: sendAddNewOrgRequest</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param record
	 * @return
	 */
	@Override
	public int sendAddNewOrgRequest(Integer userId, Organization record) {
		Message message = new Message();
		message.setCreateTime(new Date());
		message.setUserId(userId);
		message.setObjId(record.getId());
		message.setStatus(0);
		message.setContent("新组织申请创建：组织结构名称:"+record.getOrganizationName()+" 真实姓名:"+record.getRealName()+
				" 联系电话:"+record.getPhone()+" 单位简介:"+record.getUnitInfo());
		message.setType(0);
		return messageDao.insert(message);
	}

	/**
	 * 发送好友申请
	 * <p>Title: sendFriendRequest</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param objId
	 * @return
	 */
	@Override
	public int sendFriendRequest(Integer userId, Integer objId) {
		User obj = userDao.selectByPrimaryKey(objId);
		Message message = new Message();
		message.setCreateTime(new Date());
		message.setUserId(userId);
		message.setObjId(objId);
		message.setStatus(0);
		message.setType(1);
		message.setContent(obj.getUsername()+"申请加您为好友，请及时处理。");
		return messageDao.insert(message);
	}

	/**
	 * 发送话题回复
	 * <p>Title: sendTopicReply</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param objId
	 * @return
	 */
	@Override
	public int sendTopicReply(Integer userId,String objName, Integer objId) {
		Message message = new Message();
		message.setCreateTime(new Date());
		message.setUserId(userId);
		message.setObjId(objId);
		message.setType(2);
		message.setObjName(objName);
		message.setContent(objName +"回复了您的话题，请查看。");
		return messageDao.insert(message);
	}

	/**
	 * 根据类型清理消息
	 * <p>Title: clearAllMessageByType</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param type
	 * @return
	 */
	@Override
	public int clearAllMessageByType(Integer userId, Integer type) {
		MessageQuery example = new MessageQuery();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		//-1表示所有类型
		if(type != -1) {
			criteria.andTypeEqualTo(type);
		}
		return messageDao.deleteByExample(example);
	}

	/**
	 * 根据消息id单独删除
	 * <p>Title: deleteByPrimaryKey</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return messageDao.deleteByPrimaryKey(id);
	}

	/**
	 * 处理好友请求
	 * <p>Title: dealFriendRequest</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @param cmd
	 * @return
	 */
	@Override
	public int dealFriendRequest(Integer id, Integer cmd) {
		int i = 0;
		Message message = messageDao.selectByPrimaryKey(id);
		message.setStatus(1);
		if(0 == cmd) {
			message.setResult("已拒绝");
		}
		if(1 == cmd) {
			message.setResult("已通过");
			
			//通过后，双向添加好友
			i += friendsService.toBeFriend(message.getUserId(), message.getObjId());
		}
		message.setUpdateTime(new Date());
		i += messageDao.updateByPrimaryKey(message);
		return i;
	}

	/**
	 * 根据类型查找通知列表
	 * <p>Title: findMessageByType</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param type
	 * @return
	 */
	@Override
	public List<Message> findMessageByType(Integer userId, Integer type) {
		MessageQuery example = new MessageQuery();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		//-1表示所有类型
		if(type != -1) {
			criteria.andTypeEqualTo(type);
		}
		example.setOrderByClause("create_time desc");
		
		return messageDao.selectByExample(example);
	}
	
	/**
	 * 统计未处理的消息
	 * <p>Title: findUnDealMessage</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @return
	 */
	@Override
	public int findUnDealMessage(Integer userId) {
		MessageQuery example = new MessageQuery();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andStatusEqualTo(0);
		return messageDao.countByExample(example);
	}

	/**
	 * 处理新增组织请求
	 * <p>Title: dealAddNewOrgRequest</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @param cmd
	 * @return
	 */
	@Override
	public int dealAddNewOrgRequest(Integer id, Integer cmd) {
		int i = 0;
		Message message = messageDao.selectByPrimaryKey(id);
		
		if(0 == cmd) {
			message.setResult("已拒绝");
			//拒绝后删除组织记录
			i += organizationService.deleteGroupById(message.getObjId());
			
		}
		if(1 == cmd) {
			message.setResult("已通过");
			//通过后，修改组织状态
			Organization organization = organizationService.selectByPrimaryKey(message.getObjId());
			organization.setStatus(1);
			i += organizationService.undateGroup(organization);
		}
		message.setStatus(1);
		message.setUpdateTime(new Date());
		i += messageDao.updateByPrimaryKey(message);
		return i;
	}

}
