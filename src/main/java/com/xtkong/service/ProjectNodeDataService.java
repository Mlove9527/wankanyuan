package com.xtkong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtkong.dao.ProjectNodeDataDao;

@Service
public class ProjectNodeDataService {

	@Autowired
	ProjectNodeDataDao projectNodeDataDao;

	public int insert(Integer p_id, String nodedataId, Integer cs_id, Integer ft_id, String dataId) {
		return projectNodeDataDao.insert(p_id, nodedataId, cs_id, ft_id, dataId);
	}

	public int remove(Integer p_id, Integer cs_id, String p_data_id) {
		return projectNodeDataDao.remove(p_id, cs_id, p_data_id);

	}
//
//	public List<String> select(Integer p_id, Integer cs_id, Integer ft_id) {
//		return projectNodeDao.select(p_id, cs_id, ft_id);
//	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
