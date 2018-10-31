package com.dzjin.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzjin.dao.ProjectFileDao;
import com.dzjin.dao.ProjectFloderDao;
import com.dzjin.model.ProjectFile;
import com.dzjin.model.ProjectFloder;

@Service
public class ProjectFileService {
	
	@Autowired
	ProjectFileDao projectFileDao;
	@Autowired
	ProjectFloderDao projectFloderDao;
	@Autowired
	ProjectFloderService projectFloderService;
	
	/**
	 * 插入一条项目文件记录
	 * @param projectFile
	 * @return
	 */
	public int insertPorjectFile(ProjectFile projectFile){
		return projectFileDao.insertPorjectFile(projectFile);
	}
	
	/**
	 * 更新项目文件的项目ID字段
	 * @param floder_id
	 * @param files
	 * @return
	 */
	public Boolean updateFloderId(Integer floder_id , String files){
		boolean result = true;
		String ids[] = files.split(",");
		for(int i = 0 ; i<ids.length ; i++){
			if(1 != projectFileDao.updateFloderId(floder_id, Integer.valueOf(ids[i]))){
				result = false;
			}
		}
		return result;
	}
	/**
	 * 查询某个文件夹下面的文件
	 * @param floder_id
	 * @return
	 */
	public List<ProjectFile> selectProjectFileByFloderId(Integer floder_id){
		List<ProjectFile> projectFiles = new ArrayList<ProjectFile>();
		getProjectFiles(floder_id, projectFiles);
		return projectFiles;
	}
	
	public void getProjectFiles(Integer floder_id , List<ProjectFile> projectFiles){
		
		List<ProjectFloder> projectFloders = projectFloderDao.selectProjectFloderByParentId(floder_id);
		if(projectFloders.size()>0){
			projectFiles.addAll(projectFileDao.selectProjectFileByFloderId(floder_id));
			//然后进行遍历
			Iterator<ProjectFloder> iterator = projectFloders.iterator();
			while(iterator.hasNext()){
				ProjectFloder projectFloder = (ProjectFloder) iterator.next();
				getProjectFiles(projectFloder.getId(), projectFiles);
			}
		}else{
			//查看下面包含的文件
			projectFiles.addAll(projectFileDao.selectProjectFileByFloderId(floder_id));
		}
	}
	
	/**
	 * 批量删除文件
	 * @param files
	 * @return
	 */
	public Boolean deleteFiles(String files){
		boolean result = true;
		String ids[] = files.split(",");
		for(int i = 0 ; i<ids.length ; i++){
			if(1 != projectFileDao.deleteFile(Integer.valueOf(ids[i]))){
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * 计算项目内成员上传文件数量
	 * @param p_id
	 * @param creator_id
	 * @return
	 */
	public int countProjectFileNumByPidAndUid(Integer p_id , Integer creator_id){
		
		List<ProjectFloder> projectFloders = projectFloderService.selectProjectRootFloderByProjectId(p_id);
		List<ProjectFile> projectFiles = new ArrayList<ProjectFile>();
		
		Iterator<ProjectFloder> iterator = projectFloders.iterator();
		while(iterator.hasNext()){
			ProjectFloder projectFloder = (ProjectFloder)iterator.next();
			projectFiles.addAll(selectProjectFileByFloderId(projectFloder.getId()));	
		}
		int num = 0;
		Iterator<ProjectFile> iterator2 = projectFiles.iterator();
		while(iterator2.hasNext()){
			ProjectFile projectFile = (ProjectFile)iterator2.next();
			if(projectFile.getCreator_id() == creator_id){
				num++;
			}
		}
		return num;
	}

}
