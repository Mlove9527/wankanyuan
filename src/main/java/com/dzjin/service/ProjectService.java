package com.dzjin.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzjin.dao.ProjectDao;
import com.dzjin.model.Project;
import com.dzjin.model.ProjectUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ProjectService {
	
	@Autowired
	ProjectDao projectDao;
	@Autowired
	ProjectUserService projectUserService;
	@Autowired
	ProjectCustomRoleService projectCustomRoleService;
	
	/**
	 * 插入项目
	 * @param project
	 * @return
	 */
	public int insertProject(Project project){
		return projectDao.insertProject(project);
	}
	
	/**
	 * 获取项目基本信息
	 * @param id
	 * @return
	 */
	public Project getProjectDetail(Integer id){
		return projectDao.getProjectDetail(id);
	}
	
	/**
	 * 更新项目简介
	 * @param project
	 * @return
	 */
	public int updateProjectIntroduction(Project project){
		return projectDao.updateProjectIntroduction(project);
	}
	
	/**
	 * 更新项目的名称，同步异步以及关键字
	 * @param project
	 * @return
	 */
	public int updateProject(Project project){
		return projectDao.updateProject(project);
	}
	
	/**
	 * 选取公共项目
	 * @return	公共项目列表
	 */
	public Map<String, Object> selectPublicProject(Integer page , Integer strip  , String searchWord){
		PageHelper.startPage(page, strip);
		List<Project> projects = projectDao.selectPublicProject(searchWord);
		PageInfo<Project> pageInfo = new PageInfo<Project>(projects);
		Map<String, Object> result = new HashMap<String , Object>();
		result.put("total", pageInfo.getTotal());
		result.put("list", projects);
		return result;
	}
	
	/**
	 * 选取公共项目
	 * @return	公共项目列表
	 */
	public List<Project> selectPublicProject1(String searchWord, String sql){
		List<Project> projects = projectDao.selectPublicProject1(searchWord, sql);
		return projects;
	}
	
	/**
	 * 选取我创建的项目
	 * @return	我创建的项目列表
	 */
	public Map<String, Object> selectCreatedProject(Integer creator ,  Integer page , Integer strip , String searchWord){
		PageHelper.startPage(page, strip);
		List<Project> projects = projectDao.selectCreatedProject(creator , searchWord);
		PageInfo<Project> pageInfo = new PageInfo<Project>(projects);
		Map<String, Object> result = new HashMap<String , Object>();
		result.put("total", pageInfo.getTotal());
		result.put("list", projects);
		return result;
	}
	
	/**
	 * 选取我创建的项目
	 * @return	我创建的项目列表
	 */
	public List<Project> selectCreatedProject1(Integer creator , String searchWord, String sql){
		List<Project> projects = projectDao.selectCreatedProject1(creator , searchWord, sql);
		return projects;
	}
	
	/**
	 * 选取我加入的项目
	 * @return	我加入的项目列表
	 */
	public Map<String, Object> selectMyProject(Integer user_id , Integer page , Integer strip , String searchWord){
		PageHelper.startPage(page, strip);
		List<Project> projects = projectDao.selectMyProject1(user_id , searchWord);
		PageInfo<Project> pageInfo = new PageInfo<Project>(projects);
		Map<String, Object> result = new HashMap<String , Object>();
		result.put("total", pageInfo.getTotal());
		result.put("list", projects);
		return result;
	}
	
	/**
	 * 选取我加入的项目
	 * @return	我加入的项目列表
	 */
	public List<Project> selectMyProject2(Integer user_id ,  String searchWord, String sql){
		List<Project> projects = projectDao.selectMyProject2(user_id , searchWord, sql);
		return projects;
	}
	
	/**
	 * 选择我的项目
	 * @param user_id
	 * @return
	 */
	public List<Project> selectMyProject(Integer user_id){
		return projectDao.selectMyProject(user_id);

	}
	public List<Project> selectMyProject1(Integer user_id){
		return projectDao.selectMyProject(user_id);

	}
	
	/**
	 * 将公共的项目添加到我的项目中
	 * @param idStrings
	 * @param user_id
	 * @return
	 */
	public boolean addPublicProjectToMine(String idStrings , Integer user_id){
		String[] ids = idStrings.split(",");
		boolean result = true;
		for(int i = 0 ; i< ids.length ; i++){
			//构造项目用户关系记录
			ProjectUser projectUser = new ProjectUser();
			projectUser.setProject_id(Integer.valueOf(ids[i]));
			projectUser.setUser_id(user_id);
			
			//判断项目用户是否已经绑定关系
			if(null == projectDao.getProjectUserByPidAndUid(projectUser)){
				projectUser.setRole_id(0);//也就是没有角色，不是项目成员
				projectUser.setBind_date_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				if(projectUserService.insertProjectUser(projectUser) != 1){
					result = false;
				}
			}
		}
		return result;
	}
	
	/**
	 * 更新项目公开状态
	 * @param idStrings
	 * @param is_open
	 * @return
	 */
	public boolean updateProjectOpenState(String idStrings , Integer is_open){
		String[] ids = idStrings.split(",");
		boolean result = true;
		for(int i = 0 ; i< ids.length ; i++){
			if(1 != projectDao.updateProjectOpenState(Integer.valueOf(ids[i]), is_open)){
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * 删除项目
	 * @param idStrings
	 * @return
	 */
	public boolean deleteProjects(String idStrings){
		String[] ids = idStrings.split(",");
		boolean result = true;
		for(int i = 0 ; i< ids.length ; i++){
			if(1 != projectDao.deleteProject(Integer.valueOf(ids[i]))){
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * 退出项目
	 * @param idStrings
	 * @param user_id
	 * @return
	 */
	public boolean exits(String idStrings , Integer user_id){
		String[] ids = idStrings.split(",");
		boolean result = true;
		for(int i = 0 ; i< ids.length ; i++){
			Project project = projectDao.getProjectDetail(Integer.valueOf(ids[i]));
			//创建者不能退出项目
			if(Integer.valueOf(project.getCreator()) != user_id){
				ProjectUser projectUser = new ProjectUser();
				projectUser.setProject_id(Integer.valueOf(ids[i]));
				projectUser.setUser_id(user_id);
				
				if(1 != projectDao.deleteProjectUser(projectUser)){
					result = false;
				}
			}
		}
		return result;
	}
	
	public Integer getProjectId(String projectName) {
		return projectDao.getProjectId(projectName);
	}
	
	public int countProjectFile(Integer id){
		return projectDao.countProjectFile(id);
	}
	
	public int countProjectApp(Integer id){
		return projectDao.countProjectApp(id);
	}
	
	public int countProjectAppTask(Integer id){
		return projectDao.countProjectAppTask(id);
	}
	
	public int countProjectUser(Integer id){
		return projectDao.countProjectUser(id);
	}

}
