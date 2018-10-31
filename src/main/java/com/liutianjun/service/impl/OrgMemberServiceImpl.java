package com.liutianjun.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liutianjun.dao.OrgMemberDao;
import com.liutianjun.dao.OrganizationDao;
import com.liutianjun.dao.UserDao;
import com.liutianjun.pojo.OrgMember;
import com.liutianjun.pojo.OrgMemberQuery;
import com.liutianjun.pojo.OrgMemberQuery.Criteria;
import com.liutianjun.pojo.Organization;
import com.liutianjun.pojo.User;
import com.liutianjun.service.OrgMemberService;

/**
 * 组内成员
 * @Title: OrgMemberServiceImpl.java  
 * @Package com.liutianjun.service.impl  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年6月28日  
 * @version V1.0
 */
@Service
public class OrgMemberServiceImpl implements OrgMemberService {

	@Autowired
	private OrgMemberDao orgMemberDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private OrganizationDao organizationDao;
	
	/**
	 * 批量添加用户到组
	 * <p>Title: addOrgMembers</p>  
	 * <p>Description: </p>  
	 * @param groupId
	 * @param userids
	 * @return
	 */
	@Override
	public int addOrgMembers(Integer groupId, Integer[] userIds) {
		int i = 0;
		User user;
		OrgMember orgMember;
		Organization organization = organizationDao.selectByPrimaryKey(groupId);
		for (Integer userid : userIds) {
			if(0 == findOrgMember(groupId, userid)) {
				user = userDao.selectByPrimaryKey(userid);
				if(null != user) {
					orgMember = new OrgMember();
					orgMember.setCreateTime(new Date());
					orgMember.setEmail(user.getEmail());
					orgMember.setGroupId(groupId);
					orgMember.setHeadimg(user.getHeadimg());
					orgMember.setUserId(userid);
					orgMember.setUsername(user.getUsername());
					orgMember.setOrgId(organization.getRootId());
					if(user.getUsername().equals(organization.getCreator())) {
						orgMember.setOrgRole("管理员");
					}else {
						orgMember.setOrgRole("成员");
					}
					i += orgMemberDao.insertSelective(orgMember);
				}
			}
		}
		return i;
	}
	
	//更新组织成员信息
	public int updateMembersInfo(OrgMember orgMember) {
		int i =0;
		if(null != orgMember && orgMember.getUserId() != null) {
			User user = userDao.selectByPrimaryKey(orgMember.getUserId());
			orgMember.setHeadimg(user.getHeadimg());
			orgMember.setEmail(user.getEmail());
			orgMember.setUpdateTime(new Date());
			i +=orgMemberDao.updateByPrimaryKey(orgMember);
		}
		
		return i;
	}
	
	/**
	 * 查找组员
	 * @Title: findOrgMember 
	 * @param groupId
	 * @param userId
	 * @return 
	 * int
	 */
	private int findOrgMember(Integer groupId,Integer userId) {
		OrgMemberQuery example = new OrgMemberQuery();
		Criteria criteria = example.createCriteria();
		criteria.andGroupIdEqualTo(groupId);
		criteria.andUserIdEqualTo(userId);
		return orgMemberDao.countByExample(example);
	}

	/**
	 * 根据组id获取组员
	 * <p>Title: findOrgMembersByGroupId</p>  
	 * <p>Description: </p>  
	 * @param groupId
	 * @return
	 */
	@Override
	public List<OrgMember> findOrgMembersByGroupId(Integer groupId) {
		OrgMemberQuery example = new OrgMemberQuery();
		Criteria criteria = example.createCriteria();
		criteria.andGroupIdEqualTo(groupId);
		List<OrgMember> list = orgMemberDao.selectByExample(example);
		for (OrgMember orgMember : list) {
			updateMembersInfo(orgMember);
		}
		return list;
	}
	
	/**
	 * 根据组织结构id获取组内成员
	 * <p>Title: findOrgMembersByOrgId</p>  
	 * <p>Description: </p>  
	 * @param orgId
	 * @return
	 */
	@Override
	public List<OrgMember> findOrgMembersByOrgId(Integer orgId) {
		OrgMemberQuery example = new OrgMemberQuery();
		Criteria criteria = example.createCriteria();
		criteria.andOrgIdEqualTo(orgId);
		List<OrgMember> list = orgMemberDao.selectByExample(example);
		for (OrgMember orgMember : list) {
			updateMembersInfo(orgMember);
		}
		return list;
	}
	
	/**
	 * 查询组内成员
	 * <p>Title: findOrgMembersByName</p>  
	 * <p>Description: </p>  
	 * @param username
	 * @return
	 */
	@Override
	public List<OrgMember> findGroupMembersByName(String username,Integer groupId,String orgRole) {
		OrgMemberQuery example = new OrgMemberQuery();
		example.setDistinct(true);
		example.setFields("user_id,username,headimg,email,org_Role");
		Criteria criteria = example.createCriteria();
		criteria.andUsernameLike("%"+username+"%");
		criteria.andGroupIdEqualTo(groupId);
		if(null != orgRole && orgRole != "") {
			criteria.andOrgRoleEqualTo(orgRole);
		}
		//List<Integer> MyGroupIds = findMyGroupIds();
		//criteria.andOrgIdIn(MyGroupIds);
		
		return orgMemberDao.selectByExample(example);
	}


	/**
	 * 移除组内成员
	 * <p>Title: removeOrgMembers</p>  
	 * <p>Description: </p>  
	 * @param ids
	 * @return
	 */
	@Override
	public int removeOrgMembers(Integer[] ids) {
		OrgMemberQuery example = new OrgMemberQuery();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(Arrays.asList(ids));
		return orgMemberDao.deleteByExample(example);
	}

	/**
	 * 查询组IDS
	 * <p>Title: findMyGroupIds</p>  
	 * <p>Description: </p>  
	 * @return
	 */
	@Override
	public List<Integer> findMyGroupIds() {
		List<Integer> list = new ArrayList<>();
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		OrgMemberQuery example = new OrgMemberQuery();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		//你所在的组的组织结构ID
		List<OrgMember> list1 = orgMemberDao.selectByExample(example);
		for (OrgMember orgMember : list1) {
			list.add(orgMember.getOrgId());
		}
		
		return list;
	}

	
}
