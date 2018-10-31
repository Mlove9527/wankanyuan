package com.liutianjun.service;

import java.util.List;
import java.util.Map;

import com.liutianjun.pojo.Application;
import com.liutianjun.pojo.UserAppRelation;

/**
 * 用户应用关系
 * @Title: UserAppRelationService.java  
 * @Package com.liutianjun.service  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年5月8日  
 * @version V1.0
 */
public interface UserAppRelationService {

	//根据用户id和用户名插入关系表
	int insert(Integer userId,List<Application> list);
	
	//根据用户id删除关系表
	int deleteByPrimaryKey(Integer id);
	
	//添加用户的应用
	int addToMineByIds(Integer userId,Integer[] ids);
	
	//删除用户的应用
	int removeFromMineByIds(Integer userId,Integer[] ids);
	
	//查找用户的应用集合
	//Map<String,Object> findMine(Integer page, Integer rows, String appName, String appType, Integer userId,String orderByClause, String field, String[] option);
	public Map<String,Object> findMine(Integer page, Integer rows, String appName, String appType, Integer userId, String orderByClause, 
			List<String> appNameOption, List<String> creatorOption, List<String> isAsyncOption, List<String> keywordsOption, List<String> appIntroOption, String createTimeOption,List<String> appTypeOption,List<String> option);
	
	
	//查找我的应用
	List<UserAppRelation> findMine(Integer userId);

	//查找字段列表
	Map<String, Object> findFileList(String field, String content, Integer userId, String appName, String appType, 
			List<String> appNameOption, List<String> creatorOption, List<String> isAsyncOption, List<String> keywordsOption, List<String> appIntroOption, 
			List<String> createTimeOption,List<String> appTypeOption,
			int pageIndex,int pageSize);

	//查询我的应用类别列表
	List<String> findMyTypeList(Integer userId);
	 /*
     *新加一条  根据项目id来获取项目的创建者
     */
    UserAppRelation selectcreatorbyappid(UserAppRelation userAppRelation);
    /*
     * 移除
     */
    int deletefromuserapp(UserAppRelation userAppRelation);
}
