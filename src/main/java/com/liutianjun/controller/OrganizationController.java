package com.liutianjun.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liutianjun.pojo.OrgMember;
import com.liutianjun.pojo.Organization;
import com.liutianjun.service.OrgMemberService;
import com.liutianjun.service.OrganizationService;

/**
 * 组织结构管理
 * @Title: OrganizationController.java  
 * @Package com.liutianjun.controller  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年6月1日  
 * @version V1.0
 */
@Controller
@RequestMapping("/organization")
public class OrganizationController {

	protected Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private OrgMemberService orgMemberService;
	
	/**
	 * 添加新组织结构
	 * @Title: addNewOrg 
	 * @param record
	 * @return 
	 * Map<String,Object>
	 */
	@RequiresPermissions("organization:create")
	@RequestMapping(value="/addNewOrg",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addNewOrg(Organization record) {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    record.setCreator(username);
		if(1 == organizationService.addNewOrg(record)) {
			resultMap.put("status", 200);
			resultMap.put("message", "已提交申请，请等待审核!");
		}
		
		return resultMap;
	}
	
	/**
	 * 添加新组
	 * @Title: addNewGroup 
	 * @param parentId
	 * @param organizationName
	 * @return 
	 * Map<String,Object>
	 */
	@RequiresPermissions("organization:create")
	@RequestMapping(value="/addNewGroup",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addNewGroup(Integer parentId, String organizationName) {
		resultMap.put("status", 400);
		resultMap.put("message", "添加失败!");
		List<OrgMember> list = orgMemberService.findOrgMembersByGroupId(parentId);
		if(null != list && list.size() > 0) {
			resultMap.put("message", "该目录有成员，不能添加组!");
			return resultMap;
		}
		Organization organization = organizationService.selectByPrimaryKey(parentId);
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		if(!username.equals(organization.getCreator())){
			resultMap.put("message", "您不是组织结构创建者，不能添加组!");
			return resultMap;
		}
		if(null != parentId && 0 != organizationService.addNewGroup(parentId, organizationName)) {
			resultMap.put("status", 200);
			resultMap.put("message", "添加成功!");
		}
		
		return resultMap;
	}
	
	/**
	 * 根据id获取组名
	 * @Title: getGroupName 
	 * @param id
	 * @return 
	 * String
	 */
	@RequiresPermissions("organization:view")
	@RequestMapping(value="/getGroupName",method=RequestMethod.GET)
	@ResponseBody
	public String getGroupName(Integer id) {
		Organization organization = organizationService.selectByPrimaryKey(id);
		return organization.getOrganizationName();
	}
	
	
	/**
	 * 更新组名
	 * @Title: updateGroup 
	 * @param record
	 * @return 
	 * Map<String,Object>
	 */
	@RequiresPermissions("organization:update")
	@RequestMapping(value="/updateGroup",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateGroup(Organization record) {
		resultMap.put("status", 400);
		resultMap.put("message", "更新失败!");
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		Organization organization = organizationService.selectByPrimaryKey(record.getId());
		if(!username.equals(organization.getCreator())){
			resultMap.put("message", "您不是组织结构创建者，不能修改组!");
			return resultMap;
		}
		if(0 != organizationService.undateGroup(record)) {
			resultMap.put("status", 200);
			resultMap.put("message", "更新成功!");
		}
		
		return resultMap;
	}
	/**
	 * 删除分组
	 * @Title: deleteGroup 
	 * @param id
	 * @return 
	 * Map<String,Object>
	 */
	@RequiresPermissions("organization:delete")
	@RequestMapping(value="/deleteGroup",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteGroup(Integer id) {
		resultMap.put("status", 400);
		resultMap.put("message", "删除失败!");
		Organization organization = organizationService.selectByPrimaryKey(id);
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		if(!username.equals(organization.getCreator())){
			resultMap.put("message", "您不是组织结构创建者，不能删除组!");
			return resultMap;
		}
		if(0 != organizationService.deleteGroupById(id)) {
			resultMap.put("status", 200);
			resultMap.put("message", "删除成功!");
		}
		
		return resultMap;
	}
	
	/**
	 * 查询组织结构
	 * @Title: fingOrgList 
	 * @return
	 * @throws Exception 
	 * String
	 */
	@RequestMapping(value="/findOrgList",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String fingOrgList() throws Exception {
		List<Integer> list = orgMemberService.findMyGroupIds();
		List<Integer> list2 = organizationService.fingOrgIds();
		list.addAll(list2);
		List<Organization> orgList = organizationService.findOrgList(0,list);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonOrgList = objectMapper.writeValueAsString(orgList);
		return jsonOrgList;
	}
	
	/**
	 * 组织机构
	 * @Title: viewOrgList 
	 * @param page
	 * @param rows
	 * @param organizationName
	 * @return 
	 * String
	 */
	@RequestMapping(value = "/viewOrgList",method=RequestMethod.GET)
	public String viewOrgList(@RequestParam(value="page", defaultValue="1")Integer page, 
            @RequestParam(value="rows", defaultValue="10")Integer rows,
            @RequestParam(value="organizationName",required=false) String organizationName,Model model) {
		Map<String, Object> map = organizationService.findOrgList(page, rows, organizationName);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("page", page);
		model.addAttribute("rows", rows);
		model.addAttribute("organizationName", organizationName);
		return "/admin/orgmanage.jsp";
	}
	
	/**
	 * 处理添加组织机构请求
	 * @Title: dealOrgRequest 
	 * @param id
	 * @param cmd
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value = "/dealOrgRequest",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> dealOrgRequest(Integer[] ids,Integer cmd){
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
		if(0 < organizationService.dealAddOrgRequest(ids, cmd)) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功!");
		}
		
		return resultMap;
	}
	
}
