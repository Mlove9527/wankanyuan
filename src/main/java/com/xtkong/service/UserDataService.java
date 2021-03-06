package com.xtkong.service;

import java.util.List;

import com.liutianjun.pojo.UserDataRelation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtkong.controller.user.SourceDataController;
import com.xtkong.dao.UserDataDao;
import com.xtkong.model.UserData;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDataService {
	private static final Logger logger  =  Logger.getLogger(UserDataService.class );
	
	@Autowired
	UserDataDao userDataDao;

	public int insert(Integer uid, String dataid, Integer cs_id) {
		return userDataDao.insert(uid, dataid, cs_id);
	}

	@Transactional(rollbackFor={RuntimeException.class, Exception.class})
	public void insertBatch(List<UserDataRelation> entities) {
		for(UserDataRelation entity : entities)
		{
			try
			{
				userDataDao.insert(entity.getUid(), entity.getDataId(), entity.getCs_id());
			}
			catch(Exception e)
			{
				logger.warn("Insert fail: "+entity.getDataId()+" "+e.getMessage()+"");
			}
		}
	}

	public List<UserData> select(Integer uid) {
		return userDataDao.select(uid);
	}

	public List<String> selects(Integer uid, Integer cs_id) {
		return userDataDao.selects(uid, cs_id);
	}

	public int delete(Integer uid, String dataid, Integer cs_id) {
		return userDataDao.delete(uid, dataid, cs_id);
	}

	public int deleteid(String dataid, Integer cs_id) {
		return userDataDao.deleteid(dataid, cs_id);
	}
}
