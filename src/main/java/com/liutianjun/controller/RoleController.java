package com.liutianjun.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.liutianjun.pojo.Role;
import com.liutianjun.service.ResourceService;
import com.liutianjun.service.RoleService;

/**
 * 角色管理
 * @Title: RoleController.java  
 * @Package com.liutianjun.controller  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年6月20日  
 * @version V1.0
 */
@Controller
public class RoleController {

	protected Map<String, Object> resultMap = new HashMap<String, Object>();
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 显示权限管理页面
	 * @Title: viewRoleManage
	 * @param page
	 * @param rows
	 * @param role
	 * @param model
	 * @return 
	 * String
	 */
	@RequestMapping(value="/admin/viewRoleManage",method=RequestMethod.GET)
	public String viewRoleManage(@RequestParam(value="page", defaultValue="1")Integer page, 
            @RequestParam(value="rows", defaultValue="10")Integer rows,
            @RequestParam(value="rolename", required=false)String role,
            Model model) {
		Map<String, Object> map = roleService.findAll(page, rows, role);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("page", page);
		model.addAttribute("rows", rows);
		model.addAttribute("rolename", role);
		
		Map<String, Object> resourceMap = resourceService.findAll();
		model.addAttribute("resourceList", resourceMap.get("list"));
		
		return "admin/rolemanage.jsp";
	}
	
	/**
	 * 插入新角色
	 * @Title: insertRole 
	 * @param ids
	 * @param role
	 * @param attributes
	 * @return 
	 * String
	 */
	@RequestMapping(value="/admin/insertRole",method=RequestMethod.POST)
	public String insertRole(Integer[] ids, Role role, RedirectAttributes attributes) {
		String idsStr = Arrays.toString(ids);
		String idSerStr = idsStr.replaceAll(" ", "");
		String substring = StringUtils.substring(idSerStr, 1, idSerStr.length()-1);
		role.setResourceIds(substring);
		if(1 == roleService.insert(role)) {
			attributes.addFlashAttribute("msg", "添加成功！");
		}else {
			attributes.addFlashAttribute("msg", "添加失败！");
		}
		
		return "redirect:/admin/viewRoleManage";
	}
	
	/**
	 * 获取角色信息
	 * @Title: insertRole 
	 * @param ids
	 * @return 
	 * String
	 * @throws Exception 
	 */
	@RequestMapping(value="/admin/getRoleInfo",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getRoleInfo(String id) throws Exception {
		Role role = roleService.selectByPrimaryKey(Integer.valueOf(id));
		ObjectMapper mapper = new ObjectMapper();
		
		String roleJson = mapper.writeValueAsString(role);
		
		return roleJson;
	}
	
	/**
	 * 获取角色列表
	 * @Title: getRoleList 
	 * @return
	 * @throws Exception 
	 * String
	 */
	@RequestMapping(value="/role/getRoleList",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getRoleList() throws Exception {
		Map<String, Object> map = roleService.findAll();
		ObjectMapper mapper = new ObjectMapper();
		
		String roleJson = mapper.writeValueAsString(map.get("list"));
		
		return roleJson;
	}
	
	/**
	 * 更新角色
	 * @Title: updateRole 
	 * @param editIds
	 * @param role
	 * @param attributes
	 * @return 
	 * String
	 */
	@RequestMapping(value="/admin/updateRole",method=RequestMethod.POST)
	public String updateRole(Integer[] editIds, Role role, RedirectAttributes attributes) {
		String idsStr = Arrays.toString(editIds);
		String idSerStr = idsStr.replaceAll(" ", "");
		String substring = StringUtils.substring(idSerStr, 1, idSerStr.length()-1);
		role.setResourceIds(substring);
		if(1 == roleService.updateByPrimaryKey(role)) {
			attributes.addFlashAttribute("msg", "更新成功！");
		}else {
			attributes.addFlashAttribute("msg", "更新失败！");
		}
		
		return "redirect:/admin/viewRoleManage";
	}
	
	/**
	 * 批量删除角色
	 * @Title: deleteByIds 
	 * @param ids
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/admin/deleteRolesByIds",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteRolesByIds(Integer[] ids) {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
		if(Arrays.asList(ids).contains(1)) {
			resultMap.put("message", "管理员角色不能删除!");
			return resultMap;
		}
		
		if(0 < roleService.deleteByIds(ids)) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功!");
		}
		
		return resultMap;
	}
	
}
