package com.xtkong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtkong.dao.ProjectDataDao;

@Service
public class ProjectDataService {

	@Autowired
	ProjectDataDao projectDataDao;

	public int insert(Integer p_id, String sourceDataId, Integer cs_id) {
		return projectDataDao.insert(p_id, sourceDataId, cs_id);
	}

	public int remove(Integer p_id, String sourceDataId, Integer cs_id) {
		return projectDataDao.remove(p_id, sourceDataId, cs_id);

	}

	public int updataPDataId(Integer p_id, String sourceDataId, Integer cs_id, String pSourceDataId) {
		return projectDataDao.updataPDataId(p_id, sourceDataId, cs_id, pSourceDataId);
	}

	public String selectPDataId(Integer p_id, String sourceDataId, Integer cs_id) {
		return projectDataDao.selectPDataId(p_id, sourceDataId, cs_id);
	}
	
	public List<String> select(Integer p_id, Integer cs_id) {
		return projectDataDao.select(p_id, cs_id);
	}

}
