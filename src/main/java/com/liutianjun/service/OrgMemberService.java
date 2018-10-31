package com.liutianjun.service;

import java.util.List;

import com.liutianjun.pojo.OrgMember;

public interface OrgMemberService {

	//批量添加用户到组
	int addOrgMembers(Integer groupId, Integer[] userIds);
	
	//根据组id获取组内成员
	List<OrgMember> findOrgMembersByGroupId(Integer groupId);
	
	//根据组织结内id获取组能成员
	List<OrgMember> findOrgMembersByOrgId(Integer orgId);
	
	//将组内成员从组中移除
	int removeOrgMembers(Integer[] ids);
	
	//查询我的组id集合
	List<Integer> findMyGroupIds();
	
	//查找组内成员
	List<OrgMember> findGroupMembersByName(String username, Integer groupId, String orgRole);
	
	
}
