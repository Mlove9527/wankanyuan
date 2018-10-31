package com.liutianjun.service;

import java.util.List;
import java.util.Map;

import com.liutianjun.pojo.Application;

/**
 * 应用服务
 * @Title: ApplicationService.java  
 * @Package com.liutianjun.service  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年5月7日  
 * @version V1.0
 */
public interface ApplicationService {

	//添加应用
	int insert(Application record);
	
	//根据主键删除应用
	int deleteByIds(Integer[] ids);
	
	//更新应用
	int updateByPrimaryKey(Application record);
	
	//根据主键查找应用
	Application selectByPrimaryKey(Integer id);
	
	//列出所有应用
	Map<String,Object> findAll();

	//列出所有公共应用带分页
	/*Map<String,Object> findAllPublic(Integer page, Integer rows, String appName, String appType);*/
	
	//查找自己创建的应用
	/*Map<String, Object> findCreate(Integer page, Integer rows, String appName, String appType, String creator,
			String orderByClause, String[] appNameOption, String[] creatorOption, String[] isAsyncOption, String[] keywordsOption,
			String[] appIntroOption, String[] createTimeOption, String[] isDisplayOption,String[] appTypeOption);*/
	public Map<String, Object> findCreate(Integer page, Integer rows, String appName, String appType, String creator,
			String orderByClause, List<String> appNameOption, List<String> creatorOption, List<String> isAsyncOption, List<String> keywordsOption,
			List<String> appIntroOption, List<String> createTimeOption, List<String> isDisplayOption,List<String> appTypeOption);
	
	//查找公共的应用
	/*Map<String, Object> findPublic(Integer page, Integer rows, String appName, String appType, String orderByClause,
			String field, String[] option);*/
	Map<String, Object> findPublic(Integer page, Integer rows, String appName, String appType, String orderByClause,
			List<String> appNameOption, List<String> creatorOption, List<String> isAsyncOption, List<String> keywordsOption,
			List<String> appIntroOption, List<String> createTimeOption,List<String> appTypeOption);
	//设置应用状态
	int setStatus(Integer cmd, Integer[] ids);
	
	//查询项目下的应用
	Map<String, Object> findMineProjectApp(Integer page, Integer rows, Integer ProjectId);
	
	//查询数组里的应用
	List<Application> findByIds(Integer[] ids);

	//查看公开应用
	Application findPublicByid(Integer id);
	
	//查找类别列表
	List<String> findTypeList();

	//查找应用字段列表
	Map<String, Object> findFieldList(String field, String content, String username, String appName, String appType, 
	List<String> appNameOption, List<String> creatorOption,List<String> isAsyncOption, List<String> keywordsOption, 
	List<String> appIntroOption, String createTimeOption, List<String> isDisplayOption,List<String> appTypeOption,int pageIndex,int pageSize);
	
	//查询公共的字段列表
	Map<String, Object> findPublicFieldList(String field, String content, String appName, String appType, 
			List<String> appNameOption, List<String> creatorOption, List<String> isAsyncOption, List<String> keywordsOption, List<String> appIntroOption, String createTimeOption,List<String> appTypeOption,int pageIndex,int pageSize);
	

	//查询我创建的应用类别列表
	List<String> findAppTypeList(String username);

	//查询公共的的应用类别列表
	List<String> findPublicAppTypeList();


}
