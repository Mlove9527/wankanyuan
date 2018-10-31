package com.xtkong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtkong.dao.ProjectNodeDao;

@Service
public class ProjectNodeService {

	@Autowired
	ProjectNodeDao projectNodeDao;

	public int insert(Integer p_id, String nodeId, Integer cs_id, Integer ft_id, String p_data_id) {
		return projectNodeDao.insert(p_id, nodeId, cs_id, ft_id, p_data_id);
	}

	public int remove(Integer p_id, String nodeId, Integer cs_id, Integer ft_id) {
		return projectNodeDao.remove(p_id, nodeId, cs_id, ft_id);
	}

	public int removeByPDataId(Integer p_id, Integer cs_id,  String p_data_id) {
		return projectNodeDao.removeByPDataId(p_id, cs_id, p_data_id);
	}

	public int updadaPNodeId(Integer p_id, String nodeId, Integer cs_id, Integer ft_id, String pNodeId) {
		return projectNodeDao.updataPNodeId(p_id, nodeId, cs_id, ft_id, pNodeId);
	}

	public String selectPNoId(Integer p_id, String nodeId, Integer cs_id, Integer ft_id) {
		return projectNodeDao.selectPNoId(p_id, nodeId, cs_id, ft_id);
	}

	public String select(Integer p_id, Integer cs_id, Integer ft_id) {
		return projectNodeDao.select(p_id, cs_id, ft_id);
	}

}
