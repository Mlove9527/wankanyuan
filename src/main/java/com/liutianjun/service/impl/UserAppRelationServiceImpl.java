package com.liutianjun.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzjin.service.ProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.impl.UResource.Array;
import com.liutianjun.dao.UserAppRelationDao;
import com.liutianjun.pojo.Application;
import com.liutianjun.pojo.UserAppRelation;
import com.liutianjun.pojo.UserAppRelationQuery;
import com.liutianjun.pojo.UserAppRelationQuery.Criteria;
import com.liutianjun.service.ApplicationService;
import com.liutianjun.service.UserAppRelationService;

/**
 * 用户应用关系
 * @Title: UserAppRelationServiceImpl.java  
 * @Package com.liutianjun.service.impl  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年5月8日  
 * @version V1.0
 */
@Service
public class UserAppRelationServiceImpl implements UserAppRelationService {

	@Autowired
	private UserAppRelationDao userAppRelationDao;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private MyFieldQueryService fieldQueryService;

	
	/**
	 * 插入新关系表
	 * <p>Title: insert</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @param username
	 * @return
	 */
	@Override
	public int insert(Integer userId, List<Application> list) {
		UserAppRelation userAppRelation = new UserAppRelation();
		userAppRelation.setUserId(userId);
		int i = 0;
		for (Application application : list) {
			userAppRelation.setAppId(application.getId());
			userAppRelation.setAppName(application.getAppName());
			userAppRelation.setAppType(application.getAppType());
			userAppRelation.setCreateTime(application.getCreateTime());
			userAppRelation.setCreator(application.getCreator());
			userAppRelation.setIsAsync(application.getIsAsync());
			userAppRelation.setKeywords(application.getKeywords());
			userAppRelation.setAppOverview(application.getAppIntro());
			i += userAppRelationDao.insert(userAppRelation);
		}
		return i;
	}
	
	/**
	 * 更新应用到我的
	 * @Title: copyApplicationtoMine 
	 * @param userId
	 * @param userAppRelation 
	 * void
	 */
	private void copyApplicationtoMine(Integer userId) {
		List<UserAppRelation> list = findMine(userId);
		if(list != null && list.size() > 0) {
			for (UserAppRelation userAppRelation : list) {
				Application application = applicationService.selectByPrimaryKey(userAppRelation.getAppId());
				if(application != null) {
					userAppRelation.setAppName(application.getAppName());
					userAppRelation.setAppType(application.getAppType());
					userAppRelation.setIsAsync(application.getIsAsync());
					userAppRelation.setKeywords(application.getKeywords());
					userAppRelation.setAppOverview(application.getAppIntro());
					userAppRelationDao.updateByPrimaryKey(userAppRelation);
				}else {
					userAppRelationDao.deleteByPrimaryKey(userAppRelation.getAppId());
				}
			}
		}
		
	}
	
	/**
	 * 查找我的应用
	 * <p>Title: findMine</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @return
	 */
	@Override
	public List<UserAppRelation> findMine(Integer userId) {
		UserAppRelationQuery example = new UserAppRelationQuery();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		return userAppRelationDao.selectByExample(example);
	}

	/**
	 * 根据主键删除
	 * <p>Title: deleteByPrimaryKey</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return userAppRelationDao.deleteByPrimaryKey(id);
	}

	/**
	 * 添加用户应用关系
	 * <p>Title: updateByPrimaryKey</p>  
	 * <p>Description: </p>  
	 * @param record
	 * @return
	 */
	@Override
	public int addToMineByIds(Integer userId, Integer[] ids) {
		if(ids != null && ids.length > 0) {
			//删除重复的应用
			removeFromMineByIds(userId, ids);
			//查询应用列表
			List<Application> list = applicationService.findByIds(ids);
			return insert(userId,list);
		}
		return 0;
	}

	/**
	 * 删除用户应用的关系
	 * <p>Title: deleteMineById</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param ids
	 * @return
	 */
	@Override
	public int removeFromMineByIds(Integer userId, Integer[] ids) {
		
//		List<Application> list = applicationDao.selectByExample(example);
		
		if(ids != null && ids.length > 0) {
			UserAppRelationQuery example = new UserAppRelationQuery();
			Criteria criteria = example.createCriteria();
			criteria.andUserIdEqualTo(userId);
			//在添加id的时候，跟
			criteria.andAppIdIn(Arrays.asList(ids));
			return userAppRelationDao.deleteByExample(example);
		}
		return 0;
		
		
	}

	/**
	 * 查询我的应用
	 * <p>Title: findMine</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return
	 */
	@Override
	public Map<String,Object> findMine(Integer page, Integer rows, String appName, String appType, Integer userId, String orderByClause, 
			List<String> appNameOption, List<String> creatorOption, List<String> isAsyncOption, List<String> keywordsOption, List<String> appIntroOption, String createTimeOption,List<String> appTypeOption,List<String> option) {
//		copyApplicationtoMine(userId);
		Map<String,Object> map = new HashMap<>();
		//根据用户名查询关系表
	    UserAppRelationQuery example = new UserAppRelationQuery();
	    Criteria criteria = example.createCriteria();
	    criteria.andUserIdEqualTo(userId);
	    if(StringUtils.isNotBlank(appName)) {
	    	//criteria.andAppNameLike("%"+appName+"%");
	    	criteria.andOrSearch(appName);
	    }
	    if(StringUtils.isNotBlank(appType)) {
	    	criteria.andAppTypeEqualTo(appType);
	    }
	    fieldQueryService.myAppListQuery(criteria, appNameOption, creatorOption, isAsyncOption, keywordsOption, appIntroOption, createTimeOption, appTypeOption, option);
		/*if(null != appNameOption && appNameOption.size() > 0 ) {
			if(appNameOption.contains("null")) {
				appNameOption.add("");
			}
			criteria.andAppNameNullIn(appNameOption);
		}
		if(null != creatorOption && creatorOption.size() > 0) {
			if(creatorOption.contains("null")) {
				creatorOption.add("");
			}
			criteria.andCreatorNullIn(creatorOption);

		}
		if(null != isAsyncOption && isAsyncOption.size() > 0 ) {
			List<String> optionList = new ArrayList<>();
			if(isAsyncOption.size() == 1) {
				if(isAsyncOption.contains("同步")) {
					optionList.add("0");
				}
				if(isAsyncOption.contains("异步")) {
					optionList.add("1");
				}
				if(isAsyncOption.contains("null")) {
					optionList.add("");
					optionList.add("null");

				}
				criteria.andIsAsyncNullIn(optionList);
			}
		}
		if(null != keywordsOption && keywordsOption.size() > 0 ) {
			if(keywordsOption.contains("null")) {
				keywordsOption.add("");
			}
			criteria.andKeywordsNullIn(keywordsOption);
		}
		if(null != appIntroOption && appIntroOption.size() > 0 ) {
			if(appIntroOption.contains("null")) {
				appIntroOption.add("");
			}
			criteria.andAppOverviewNullIn(appIntroOption);
		}
		if(null != appTypeOption && appTypeOption.size() > 0 ) {
			if(appTypeOption.contains("null")) {
				appTypeOption.add("");
			}
			criteria.andAppTypeNullIn(appTypeOption);

		}
		if(null != createTimeOption && createTimeOption.size() > 0 ) {
			if(createTimeOption.contains("null")) {
				createTimeOption.add("");
			}
			criteria.andCreateTimeNullIn(createTimeOption);
		}*/
	    
	    int total = userAppRelationDao.countByExample(example);
	    example.setOrderByClause(orderByClause);
	    example.setPageNo(page);
	    example.setPageSize(rows);
	    List<UserAppRelation> list = userAppRelationDao.selectByExample(example);
		map.put("page", page);
		map.put("rows", rows);
	    map.put("total", total);
	    map.put("list", list);
	    
	    return map;
	}
	
	/**
	 * 查找我的应用字段列表
	 * <p>Title: findFileList</p>  
	 * <p>Description: </p>  
	 * @param filed
	 * @return
	 */
	@Override
	public Map<String, Object> findFileList(String field, String content, Integer userId, String appName,
			String appType, List<String> appNameOption, List<String> creatorOption, List<String> isAsyncOption,
			List<String> keywordsOption, List<String> appIntroOption, List<String> createTimeOption,
			List<String> appTypeOption,int pageIndex,int pageSize) {
		UserAppRelationQuery example = new UserAppRelationQuery();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		if (StringUtils.isNotBlank(content)) {
			if (field.equals("app_name")) {
				criteria.andAppNameLike("%" + content + "%");
			}
			if (field.equals("creator")) {
				criteria.andCreatorLike("%" + content + "%");
			}
			if (field.equals("is_async")) {
				if ("异步".equals(content)) {
					criteria.andIsAsyncEqualTo(1);
				}
				if ("同步".equals(content)) {
					criteria.andIsAsyncEqualTo(0);
				}
			}
			if (field.equals("keywords")) {
				criteria.andKeywordsLike("%" + content + "%");
			}
			if (field.equals("app_overview")) {
				criteria.andAppOverviewLike("%" + content + "%");
			}
		}
		if (StringUtils.isNotBlank(appName)) {
			criteria.andAppNameLike("%" + appName + "%");
		}
		if (StringUtils.isNotBlank(appType)) {
			criteria.andAppTypeEqualTo(appType);
		}

		fieldQueryService.myFieldsQuery(field, criteria, appNameOption, creatorOption, isAsyncOption, keywordsOption,
				appIntroOption, createTimeOption, appTypeOption);

		/*
		 * if(null != appNameOption && appNameOption.size() > 0 ) {
		 * if(appNameOption.contains("null")) { appNameOption.add(""); }
		 * criteria.andAppNameNullIn(appNameOption); } if(null != creatorOption &&
		 * creatorOption.size() > 0) { if(creatorOption.contains("null")) {
		 * creatorOption.add(""); } criteria.andCreatorNullIn(creatorOption);
		 * 
		 * } if(null != isAsyncOption && isAsyncOption.size() > 0 ) { List<String>
		 * optionList = new ArrayList<>(); if(isAsyncOption.size() == 1) {
		 * if(isAsyncOption.contains("同步")) { optionList.add("0"); }
		 * if(isAsyncOption.contains("异步")) { optionList.add("1"); }
		 * if(isAsyncOption.contains("null")) { optionList.add("");
		 * optionList.add("null");
		 * 
		 * } criteria.andIsAsyncNullIn(optionList); } } if(null != keywordsOption &&
		 * keywordsOption.size() > 0 ) { if(keywordsOption.contains("null")) {
		 * keywordsOption.add(""); } criteria.andKeywordsNullIn(keywordsOption); }
		 * if(null != appIntroOption && appIntroOption.size() > 0 ) {
		 * if(appIntroOption.contains("null")) { appIntroOption.add(""); }
		 * criteria.andAppOverviewNullIn(appIntroOption); } if(null != appTypeOption &&
		 * appTypeOption.size() > 0 ) { if(appTypeOption.contains("null")) {
		 * appTypeOption.add(""); } criteria.andAppTypeNullIn(appTypeOption);
		 * 
		 * } if(null != createTimeOption && createTimeOption.size() > 0 ) {
		 * if(createTimeOption.contains("null")) { createTimeOption.add(""); }
		 * criteria.andCreateTimeNullIn(createTimeOption); }
		 */

//		example.or(criteria);
		example.setFields(field);
		example.setDistinct(true);
		example.setPageNo(pageIndex);
		example.setPageSize(pageSize);
		List<UserAppRelation> userAppFieldsList=userAppRelationDao.selectByExample(example);
		/*PageHelper.startPage(pageRequest.getPageIndex(), pageRequest.getPageSize());
		
		PageInfo<UserAppRelation> pagehelper = new PageInfo<UserAppRelation>(userAppFieldsList);*/
		example=new UserAppRelationQuery();
		example.setFields(field);
		example.setDistinct(true);
		int total=userAppRelationDao.selectByExample(example).size();
		Map<String, Object> map=new HashMap<>();
		map.put("data", userAppFieldsList);
		map.put("total", total);
		map.put("pageIndex", pageIndex);
		map.put("pageSize", pageSize);
		return  map;

	}

	/**
	 * 查询我的应用类别列表
	 */
	@Override
	public List<String> findMyTypeList(Integer userId) {
		UserAppRelationQuery example = new UserAppRelationQuery();
		example.setFields("app_type");
		example.setDistinct(true);
		List<UserAppRelation> list = userAppRelationDao.selectByExample(example);
		List<String> typeList = new ArrayList<>();
		if(null !=list && list.size() > 0) {
			for (UserAppRelation userAppRelation : list) {
				if(null != userAppRelation && null != userAppRelation.getAppType()) {
					typeList.add(userAppRelation.getAppType());
				}
			}
		}
		return typeList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.liutianjun.service.UserAppRelationService#selectcreatorbyappid(java.lang.Integer)
	 */
	@Override
	public UserAppRelation selectcreatorbyappid(UserAppRelation userAppRelation) {
		UserAppRelation selectcreatorbyappid = userAppRelationDao.selectcreatorbyappid(userAppRelation);
		return selectcreatorbyappid;
	}

	/*
	 * (non-Javadoc)
	 * @see com.liutianjun.service.UserAppRelationService#deletefromuserapp(java.lang.Integer)
	 */
	@Override
	public int deletefromuserapp(UserAppRelation userAppRelation) {
		if(userAppRelation.getUserId()!=null && userAppRelation.getAppId()!=null) {
			
			userAppRelationDao.deletefromuserapp(userAppRelation);
			return 1;
		}
		return 0;
		
	}
	
	
	private List<String> options(List<String> opt){
		if(null!=opt&&opt.size()>0) {
			List<String> options=new ArrayList<>();
			options.addAll(opt);
			if(opt.contains("null")) {
				options.add("");
				options.add(null);
				options.add("null");
			}
			/*for(String str:opt) {
				if("null".equals(str)) {
					
					break;
				}
			}*/
			return options;
		}
		return null;
	}
	

	
}
