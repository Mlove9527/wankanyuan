package com.dzjin.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzjin.dao.ProjectFloderDao;
import com.dzjin.model.ProjectFloder;

@Service
public class ProjectFloderService {
	
	@Autowired
	ProjectFloderDao projectFloderDao;
	
	/**
	 * 查询项目下的跟文件夹
	 * @param p_id
	 * @return
	 */
	public List<ProjectFloder> selectProjectRootFloderByProjectId(Integer p_id){
		List<ProjectFloder> projectFloders = projectFloderDao.selectProjectFloderByProjectId(p_id);
		return projectFloders;
	}
	
	/**
	 * 查询某个文件夹下的子文件
	 * @param parent_id
	 * @return
	 */
	public List<ProjectFloder> selectChildFloderByParentFloderId(Integer parent_id){
		List<ProjectFloder> projectFloders = projectFloderDao.selectProjectFloderByParentId(parent_id);
		return projectFloders;
	}

	/**
	 * 查询一个项目下的文件夹
	 * @param p_id
	 * @return
	 */
	public List<ProjectFloder> selectProjectFloderByProjectId(Integer p_id){
		List<ProjectFloder> projectFloders = projectFloderDao.selectProjectFloderByProjectId(p_id);
		Iterator<ProjectFloder> pIterator = projectFloders.iterator();
		while(pIterator.hasNext()){
			ProjectFloder projectFloder = (ProjectFloder) pIterator.next();
			projectFloder.setProjectFloders(getProjectFloders(projectFloder));
		}
		return projectFloders;
	}
	
	/**
	 * 递归查询项目内文件夹
	 * @param projectFloderParent
	 * @return
	 */
	public List<ProjectFloder> getProjectFloders(ProjectFloder projectFloderParent){
		
		List<ProjectFloder> projectFloders = projectFloderDao.selectProjectFloderByParentId(projectFloderParent.getId());
		if(projectFloders.size() > 0){
			projectFloderParent.setProjectFloders(projectFloders);
			Iterator<ProjectFloder> pIterator = projectFloders.iterator();
			while(pIterator.hasNext()){
				ProjectFloder projectFloder = (ProjectFloder)pIterator.next();
				projectFloder.setProjectFloders(getProjectFloders(projectFloder));
			}
			return projectFloders;
		}else{
			return null;
		}
	}
	
	/**
	 * 新增项目文件夹
	 * @param projectFloder
	 * @return
	 */
	public int insertProjectFloder(ProjectFloder projectFloder){
		return projectFloderDao.insertProjectFloder(projectFloder);
	}
	
	/**
	 * 删除项目文件夹
	 * @param id
	 * @return
	 */
	public int deleteProjectFloder(String id){
		return projectFloderDao.deleteProjectFloder(id);
	}
	
	/**
	 * 更新项目文件夹名称
	 * @param projectFloder
	 * @return
	 */
	public int updateProjectFloder(ProjectFloder projectFloder){
		return projectFloderDao.updateProjectFloder(projectFloder);
	}

}
