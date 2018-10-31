package com.dzjin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzjin.dao.ProjectAppDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liutianjun.pojo.Application;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectAppService 
 * 类描述： 
 * 创建人：dzjin 
 * 创建时间：2018年6月5日 上午8:35:07 
 * 修改人：dzjin 
 * 修改时间：2018年6月5日 上午8:35:07 
 * 修改备注： 
 * @version 
 *
 */
@Service
public class ProjectAppService {
	
	@Autowired
	ProjectAppDao projectAppDao;
	
	/**
	 * 根据项目ID查询项目内应用
	 * @param project_id
	 * @param page
	 * @param strip
	 * @return
	 */
	public Map<String, Object> selectProjectApp(Integer project_id , Integer page , Integer strip , String keyWord){
		Map<String, Object> result = new HashMap<>();
		PageHelper.startPage(page, strip);
		List<Application> applications = projectAppDao.selectProjectApp(project_id , keyWord);
		PageInfo<Application> pageInfo = new PageInfo<Application>(applications);
		result.put("list", applications);
		result.put("total", pageInfo.getTotal());
		return result;
	}
	
	/**
	 * 解除项目-应用绑定关系
	 * @param project_id
	 * @param app_id
	 * @return
	 */
	public int deleteProjectAppRelation(Integer project_id ,  Integer app_id){
		return projectAppDao.deleteProjectAppRelation(project_id, app_id);
	}
}
