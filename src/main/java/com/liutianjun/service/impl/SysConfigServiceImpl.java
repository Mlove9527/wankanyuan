package com.liutianjun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liutianjun.dao.SysConfigDao;
import com.liutianjun.pojo.SysConfig;
import com.liutianjun.service.SysConfigService;

/**
 * 系统配置ServiceImpl
 * @Title: SysConfigServiceImpl.java  
 * @Package com.liutianjun.service.impl  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年5月25日  
 * @version V1.0
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {

	@Autowired
	private SysConfigDao sysConfigDao;
	
	/**
	 * 根据主键更新
	 * <p>Title: updateByPrimaryKey</p>  
	 * <p>Description: </p>  
	 * @param record
	 * @return
	 */
	@Override
	public int updateByPrimaryKey(SysConfig record) {
		if(null == record) {
			return 0;
		}
		return sysConfigDao.updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据主键查询
	 * <p>Title: selectByPrimaryKey</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return
	 */
	@Override
	public SysConfig selectByPrimaryKey(Integer id) {
		return sysConfigDao.selectByPrimaryKey(id);
	}

}
