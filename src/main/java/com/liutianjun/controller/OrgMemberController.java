package com.liutianjun.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liutianjun.dao.OrgMemberDao;
import com.liutianjun.pojo.OrgMember;
import com.liutianjun.pojo.Organization;
import com.liutianjun.service.OrgMemberService;
import com.liutianjun.service.OrganizationService;

/**
 * 组内成员
 * @Title: OrgMemberController.java  
 * @Package com.liutianjun.controller  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年6月29日  
 * @version V1.0
 */

@Controller
@RequestMapping("/orgMember")
public class OrgMemberController {

	protected Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Autowired
	private OrgMemberService orgMemberService;
	@Autowired
	private OrgMemberDao orgMemberDao;
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * 批量添加用户到组
	 * @Title: addOrgMembers 
	 * @param groupId
	 * @param userids
	 * @return 
	 * String
	 */
	@RequestMapping(value="/addOrgMembers",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addOrgMembers(Integer groupId, Integer[] userIds) {
		resultMap.put("status", 400);
		resultMap.put("message", "添加失败!");
		Organization organization = organizationService.selectByPrimaryKey(groupId);
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		if(!username.equals(organization.getCreator())){
			resultMap.put("message", "您不是组织结构创建者，添加用户!");
			return resultMap;
		}
		
		if(null != userIds && userIds.length == orgMemberService.addOrgMembers(groupId, userIds)) {
	    	resultMap.put("status", 200);
			resultMap.put("message", "添加成功!");
	    }
		return resultMap;
	}
	
	/**
	 * 根据组id获取组内成员
	 * @Title: findOrgMembers 
	 * @param groupId
	 * @return
	 * @throws Exception 
	 * String
	 */
	@RequestMapping(value="/findOrgMembers",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findOrgMembers(Integer groupId) throws Exception {
		List<OrgMember> list = orgMemberService.findOrgMembersByGroupId(groupId);
		ObjectMapper objectMapper = new ObjectMapper();
		String orgMemberList = objectMapper.writeValueAsString(list);
		return orgMemberList;
	}
	
	/**
	 * 查询组内成员
	 * @Title: findGroupMembersByname 
	 * @param username
	 * @return 
	 * String
	 */
	@RequestMapping(value="/findGroupMembersByname",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findGroupMembersByname(String username, Integer groupId,String orgRole) {
		try {
			List<OrgMember> list = orgMemberService.findGroupMembersByName(username, groupId, orgRole);
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonOrgUserList = objectMapper.writeValueAsString(list);
			
			return jsonOrgUserList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 移除组内成员
	 * @Title: removeOrgMembers 
	 * @param orgIds
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/removeOrgMembers",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> removeOrgMembers(Integer[] orgIds) {
		resultMap.put("status", 400);
		resultMap.put("message", "移除失败!");
		OrgMember orgMember = orgMemberDao.selectByPrimaryKey(orgIds[0]);
		Organization organization = organizationService.selectByPrimaryKey(orgMember.getOrgId());
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		if(!username.equals(organization.getCreator())){
			resultMap.put("message", "您不是组织结构创建者，不能移除成员!");
			return resultMap;
		}
		if(orgIds.length == orgMemberService.removeOrgMembers(orgIds)) {
	    	resultMap.put("status", 200);
			resultMap.put("message", "移除成功!");
	    }
		return resultMap;
	}
	
	
	
}
