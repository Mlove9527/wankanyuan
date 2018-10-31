package com.dzjin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzjin.dao.AdminProjectDao;
import com.dzjin.model.Project;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：AdminProjectService 
 * 类描述： 后台项目Service
 * 创建人：dzjin 
 * 创建时间：2018年6月26日 下午5:12:55 
 * 修改人：dzjin 
 * 修改时间：2018年6月26日 下午5:12:55 
 * 修改备注： 
 * @version 
 *
 */
@Service
public class AdminProjectService {
	
	@Autowired
	AdminProjectDao adminProjectDao;
	
	/**
	 * 获取平台内项目
	 * @param page
	 * @param strip
	 * @param searchWord
	 * @return
	 */
	public Map<String, Object> selectProject(Integer page , Integer strip , String searchWord){
		PageHelper.startPage(page, strip);
		List<Project> projects = adminProjectDao.selectProject(searchWord);
		PageInfo<Project> pageInfo = new PageInfo<Project>(projects);
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("list", projects);
		map.put("total", pageInfo.getTotal());
		return map;
	}
	
	/**
	 * 获取平台内公开或者未公开的项目
	 * @param page
	 * @param strip
	 * @param searchWord
	 * @param is_show
	 * @return
	 */
	public Map<String, Object> selectIsShowProject(Integer page , Integer strip , String searchWord , Integer is_show){
		PageHelper.startPage(page, strip);
		List<Project> projects = adminProjectDao.selectIsShowProject(searchWord , is_show);
		PageInfo<Project> pageInfo = new PageInfo<Project>(projects);
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("list", projects);
		map.put("total", pageInfo.getTotal());
		return map;
	}
	
	/**
	 * 将项目发布到门户
	 * @param id
	 * @return
	 */
	public int updateProjectIsShow1(Integer id){
		return adminProjectDao.updateProjectIsShow1(id);
	}
	
	/**
	 * 取消项目发布到门户
	 * @param id
	 * @return
	 */
	public int updateProjectIsShow0(Integer id){
		return adminProjectDao.updateProjectIsShow0(id);
	}

}
