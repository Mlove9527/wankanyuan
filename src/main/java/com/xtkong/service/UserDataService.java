package com.xtkong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtkong.dao.UserDataDao;
import com.xtkong.model.UserData;

@Service
public class UserDataService {
	@Autowired
	UserDataDao userDataDao;

	public int insert(Integer uid, String dataid, Integer cs_id) {
		return userDataDao.insert(uid, dataid, cs_id);
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
