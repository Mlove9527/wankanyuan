package com.liutianjun.service;

import com.liutianjun.pojo.SysConfig;

/**
 * 系统配置Service
 * @Title: SysConfigService.java  
 * @Package com.liutianjun.service  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年5月25日  
 * @version V1.0
 */
public interface SysConfigService {

	//更新系统配置
	int updateByPrimaryKey(SysConfig record);
	
	//根据主键查询
	SysConfig selectByPrimaryKey(Integer id);
}
