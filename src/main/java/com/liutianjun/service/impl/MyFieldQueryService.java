package com.liutianjun.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;
import com.liutianjun.pojo.ApplicationQuery.Criteria;

@Service
public class MyFieldQueryService {

	/**
	 * 查找字段       （公共、我创建的）
	 * @param field
	 * @param criteria
	 * @param appNameOption
	 * @param creatorOption
	 * @param isAsyncOption
	 * @param keywordsOption
	 * @param appIntroOption
	 * @param createTimeOption
	 * @param isDisplayOption
	 * @param appTypeOption
	 */
	public void fieldsQuery(String field, Criteria criteria, List<String> appNameOption, List<String> creatorOption,
			List<String> isAsyncOption, List<String> keywordsOption, List<String> appIntroOption,
			String createTimeOption, List<String> isDisplayOption, List<String> appTypeOption) {
		if (null != appNameOption && appNameOption.size() > 0 && !field.equals("app_name")) {
			if (appNameOption.contains("null")) {
				appNameOption.add("");
			}
			criteria.andAppNameNullIn(appNameOption);
		}
		if (null != creatorOption && creatorOption.size() > 0 && !field.equals("creator")) {
			if (creatorOption.contains("null")) {
				creatorOption.add("");
			}
			criteria.andCreatorNullIn(creatorOption);
		}
		if (null != isAsyncOption && isAsyncOption.size() > 0 && !field.equals("is_async")) {
			List<String> optionList = new ArrayList<>();
			optionList.addAll(isAsyncOption);
			if(isAsyncOption.size() == 1&&isAsyncOption.contains("null")) {
				criteria.andIsAsyncIsNull();
			}else {
				if(isAsyncOption.contains("null")) {
					optionList.add("null");
				}
				criteria.andIsAsyncNullIn(optionList);
			}
		}
		if (null != isDisplayOption && isDisplayOption.size() > 0 && !field.equals("is_display")) {
			if (isDisplayOption.size() == 1) {
				if ("私有".equals(isDisplayOption.get(0))) {
					criteria.andIsDisplayEqualTo(0);
				}
				if ("公开".equals(isDisplayOption.get(0))) {
					criteria.andIsDisplayEqualTo(1);
				}
			}else {
				
			}
		}

		if (null != keywordsOption && keywordsOption.size() > 0 && !field.equals("keywords")) {
			if (keywordsOption.contains("null")) {
				keywordsOption.add("");
			}
			criteria.andKeywordsNullIn(keywordsOption);
		}
		if (null != appIntroOption && appIntroOption.size() > 0 && !field.equals("app_intro")) {
			if (appIntroOption.contains("null")) {
				appIntroOption.add("");
			}
			criteria.andAppIntroNullIn(appIntroOption);
		}
		if (null != appTypeOption && appTypeOption.size() > 0 && !field.equals("app_type")) {
			if (appTypeOption.contains("null")) {
				appTypeOption.add("");
			}
			criteria.andAppTypeNullIn(appTypeOption);
		}
		if (null != createTimeOption && !createTimeOption.equals("") && !field.equals("create_time")) {
			/*if (createTimeOption.contains("null")) {
				createTimeOption.add("");
			}
			criteria.andCreateTimeNullIn(createTimeOption);*/
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				String[] shu=createTimeOption.split(",");
				criteria.andCreateTimeGreaterThanOrEqualTo(sdf.parse(shu[0]));
				criteria.andCreateTimeLessThanOrEqualTo(sdf.parse(shu[1]));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 *  列表  （公共、我创建的）
	 * @param criteria
	 * @param appNameOption
	 * @param creatorOption
	 * @param isAsyncOption
	 * @param keywordsOption
	 * @param appIntroOption
	 * @param createTimeOption
	 * @param isDisplayOption
	 * @param appTypeOption
	 */
	public void appListQuery( Criteria criteria, List<String> appNameOption, List<String> creatorOption,
			List<String> isAsyncOption, List<String> keywordsOption, List<String> appIntroOption,
			List<String> createTimeOption, List<String> isDisplayOption, List<String> appTypeOption) {
		if (null != appNameOption && appNameOption.size() > 0 ) {
			if (appNameOption.contains("null")) {
				appNameOption.add("");
			}
			criteria.andAppNameNullIn(appNameOption);
		}
		if (null != creatorOption && creatorOption.size() > 0 ) {
			if (creatorOption.contains("null")) {
				creatorOption.add("");
			}
			criteria.andCreatorNullIn(creatorOption);
		}
		if (null != isAsyncOption && isAsyncOption.size() > 0 ) {
			List<String> optionList = new ArrayList<>();
			
			optionList.addAll(isAsyncOption);
			if(isAsyncOption.size() == 1&&isAsyncOption.contains("null")) {
				criteria.andIsAsyncIsNull();
			}else {
				if(isAsyncOption.contains("null")) {
					optionList.add("null");
				}
				criteria.andIsAsyncNullIn(optionList);
			}
		}
		if (null != isDisplayOption && isDisplayOption.size() > 0) {
			if (isDisplayOption.size() == 1) {
				if ("私有".equals(isDisplayOption.get(0))) {
					criteria.andIsDisplayEqualTo(0);
				}
				if ("公开".equals(isDisplayOption.get(0))) {
					criteria.andIsDisplayEqualTo(1);
				}
			}else {
				List<String> optionList = new ArrayList<>();
				if (isDisplayOption.contains("私有")) {
					optionList.add("0");
				}
				if (isDisplayOption.contains("公开")) {
					optionList.add("1");
				}
				criteria.andIsDisplayNullIn(optionList);
			}
		}

		if (null != keywordsOption && keywordsOption.size() > 0) {
			if (keywordsOption.contains("null")) {
				keywordsOption.add("");
			}
			criteria.andKeywordsNullIn(keywordsOption);
		}
		if (null != appIntroOption && appIntroOption.size() > 0) {
			if (appIntroOption.contains("null")) {
				appIntroOption.add("");
			}
			criteria.andAppIntroNullIn(appIntroOption);
		}
		if (null != appTypeOption && appTypeOption.size() > 0 ) {
			if (appTypeOption.contains("null")) {
				appTypeOption.add("");
			}
			criteria.andAppTypeNullIn(appTypeOption);
		}
		if (null != createTimeOption && createTimeOption.size() ==2 ) {
			/*if (createTimeOption.contains("null")) {
				createTimeOption.add("");
			}
			criteria.andCreateTimeNullIn(createTimeOption);*/
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				criteria.andCreateTimeGreaterThanOrEqualTo(sdf.parse(createTimeOption.get(0)));
				criteria.andCreateTimeLessThanOrEqualTo(sdf.parse(createTimeOption.get(1)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 查找字段       （我的）
	 * @param field
	 * @param criteria
	 * @param appNameOption
	 * @param creatorOption
	 * @param isAsyncOption
	 * @param keywordsOption
	 * @param appIntroOption
	 * @param createTimeOption
	 * @param appTypeOption
	 */
	public void myFieldsQuery(String field, com.liutianjun.pojo.UserAppRelationQuery.Criteria criteria, List<String> appNameOption, List<String> creatorOption, List<String> isAsyncOption, List<String> keywordsOption, List<String> appIntroOption, List<String> createTimeOption,List<String> appTypeOption) {
		if(null != appNameOption && appNameOption.size() > 0 /*&& !field.equals("app_name")*/) {
			if(appNameOption.contains("null")) {
				appNameOption.add("");
    		}
			criteria.andAppNameNullIn(appNameOption);
    	}
    	if(null != creatorOption && creatorOption.size() > 0/*&& !field.equals("creator")*/) {
    		if(creatorOption.contains("null")) {
				creatorOption.add("");
    		}
    		criteria.andCreatorNullIn(creatorOption);

    	}
    	if(null != isAsyncOption && isAsyncOption.size() > 0 /*&& !field.equals("is_async")*/) {
    		List<String> optionList = new ArrayList<>();
    		
    			/*if(isAsyncOption.contains("同步")) {
    				optionList.add("0");
    			}
    			if(isAsyncOption.contains("异步")) {
    				optionList.add("1");
    			}*/
    			optionList.addAll(isAsyncOption);
    			if(isAsyncOption.size() == 1&&isAsyncOption.contains("null")) {
    				criteria.andIsAsyncIsNull();
    			}else {
    				if(isAsyncOption.contains("null")) {
						optionList.add("null");
					}
    				criteria.andIsAsyncNullIn(optionList);
    			}
    	}
    	if(null != keywordsOption && keywordsOption.size() > 0 /*&& !field.equals("keywords")*/) {
    		if(keywordsOption.contains("null")) {
				keywordsOption.add("");
    		}
			criteria.andKeywordsNullIn(keywordsOption);
    	}
    	if(null != appIntroOption && appIntroOption.size() > 0 /*&& !field.equals("app_overview")*/) {
    		if(appIntroOption.contains("null")) {
				appIntroOption.add("");
    		}
			criteria.andAppOverviewNullIn(appIntroOption);
    	}
    	if(null != appTypeOption && appTypeOption.size() > 0 /*&& !field.equals("app_type")*/) {
			if(appTypeOption.contains("null")) {
				appTypeOption.add("");
			}
    		criteria.andAppTypeNullIn(appTypeOption);

    	}
    	if(null != createTimeOption && createTimeOption.size() > 0 /*&& !field.equals("create_time")*/) {
			if(createTimeOption.contains("null")) {
				createTimeOption.add("");
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				criteria.andCreateTimeGreaterThanOrEqualTo(sdf.parse(createTimeOption.get(0)));
				criteria.andCreateTimeLessThanOrEqualTo(sdf.parse(createTimeOption.get(1)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}
	/**
	 *  列表  （公共、我创建的）
	 * @param criteria
	 * @param appNameOption
	 * @param creatorOption
	 * @param isAsyncOption
	 * @param keywordsOption
	 * @param appIntroOption
	 * @param createTimeOption
	 * @param isDisplayOption
	 * @param appTypeOption
	 */
	public void myAppListQuery( com.liutianjun.pojo.UserAppRelationQuery.Criteria criteria, List<String> appNameOption, List<String> creatorOption, List<String> isAsyncOption, List<String> keywordsOption, List<String> appIntroOption, String createTimeOption,List<String> appTypeOption,List<String> option) {
		if(null != appNameOption && appNameOption.size() > 0 ) {
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
				if(isAsyncOption.contains("同步")) {
					optionList.add("0");
				}
				if(isAsyncOption.contains("异步")) {
					optionList.add("1");
				}
			optionList.addAll(isAsyncOption);
				if(isAsyncOption.size() == 1&&isAsyncOption.contains("null")) {
//					optionList.add("");
//					optionList.add("null");
					criteria.andIsAsyncIsNull();

				}else {
					if(isAsyncOption.contains("null")) {
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
		if(null != createTimeOption && !createTimeOption.equals("") ) {
			/*if(createTimeOption.contains("null")) {
				createTimeOption.add("");
			}
			criteria.andCreateTimeNullIn(createTimeOption);
			*/
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				String[] shu=createTimeOption.split(",");
				criteria.andCreateTimeGreaterThanOrEqualTo(sdf.parse(shu[0]));
				criteria.andCreateTimeLessThanOrEqualTo(sdf.parse(shu[1]));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
