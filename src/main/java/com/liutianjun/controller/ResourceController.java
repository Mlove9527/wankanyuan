package com.liutianjun.controller;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.liutianjun.pojo.Resource;
import com.liutianjun.service.ResourceService;

/**
 * 资源管理
 * @Title: ResourceController.java  
 * @Package com.liutianjun.controller  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年5月25日  
 * @version V1.0
 */
@Controller
@RequestMapping("/admin")
public class ResourceController {

	protected Map<String, Object> resultMap = new HashMap<String, Object>();
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 资源菜单管理界面
	 * @Title: viewResourceManage 
	 * @param page
	 * @param rows
	 * @param name
	 * @param model
	 * @return 
	 * String
	 */
	@RequestMapping(value="/viewResourceManage",method=RequestMethod.GET)
	public String viewResourceManage(@RequestParam(value="page", defaultValue="1")Integer page, 
            @RequestParam(value="rows", defaultValue="10")Integer rows,
            @RequestParam(value="name", required=false)String name,
            Model model) {
		Map<String, Object> map = resourceService.findAll(page, rows, name);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("page", page);
		model.addAttribute("rows", rows);
		model.addAttribute("name", name);

		return "admin/menumanage.jsp";
	}
	
	/**
	 * 插入新资源
	 * @Title: insertResourceInfo 
	 * @param resource
	 * @param attributes
	 * @return 
	 * String
	 */
	@RequestMapping(value="/insertResource",method=RequestMethod.POST)
	public String insertResource(Resource resource, RedirectAttributes attributes) {
		if(1 ==resourceService.insert(resource)) {
			attributes.addFlashAttribute("msg", "新增成功!");
		}else {
			attributes.addFlashAttribute("msg", "新增失败!");
		}
		return "redirect:/admin/viewResourceManage";
	}
	
	/**
	 * 获取资源信息
	 * @Title: getResourceInfo 
	 * @param id
	 * @return
	 * @throws Exception 
	 * String
	 */
	@RequestMapping(value="/getResourceInfo",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getResourceInfo(Integer id) throws Exception {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
		
		Resource resource = resourceService.selectByPrimaryKey(id);
		ObjectMapper mapper = new ObjectMapper();
		String resourceJson = mapper.writeValueAsString(resource);
		return resourceJson;
	}
	
	/**
	 * 更新资源信息
	 * @Title: updateResource 
	 * @param resource
	 * @param attributes
	 * @return 
	 * String
	 */
	@RequestMapping(value="/updateResource",method=RequestMethod.POST)
	public String updateResource(Resource resource, RedirectAttributes attributes) {
		if(1 ==resourceService.updateByPrimaryKey(resource)) {
			attributes.addFlashAttribute("msg", "更新成功!");
		}else {
			attributes.addFlashAttribute("msg", "更新失败!");
		}
		return "redirect:/admin/viewResourceManage";
	}
	
	/**
	 * 批量删除
	 * @Title: deleteResourcesByIds 
	 * @param ids
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/deleteResourcesByIds",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteResourcesByIds(Integer[] ids) {
		resultMap.put("status", 400);
		resultMap.put("message", "删除失败!");
		
		if(0 < resourceService.deleteByIds(ids)) {
			resultMap.put("status", 200);
			resultMap.put("message", "删除成功!");
		}
		
		return resultMap;
	}
	
}
