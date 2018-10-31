package com.dzjin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzjin.model.Project;
import com.dzjin.model.ProjectAppTask;
import com.dzjin.model.ProjectAuthority;
import com.dzjin.model.ProjectCustomRole;
import com.dzjin.model.ProjectFile;
import com.dzjin.model.ProjectFloder;
import com.dzjin.model.ProjectRole;
import com.dzjin.model.ProjectUser;
import com.dzjin.service.ProjectAppTaskService;
import com.dzjin.service.ProjectCustomRoleService;
import com.dzjin.service.ProjectFileService;
import com.dzjin.service.ProjectFloderService;
import com.dzjin.service.ProjectRoleService;
import com.dzjin.service.ProjectService;
import com.dzjin.service.ProjectUserService;
import com.liutianjun.pojo.User;
/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectController 
 * 类描述： 项目controller
 * 创建人：dzjin 
 * 创建时间：2018年7月9日 上午9:56:37 
 * 修改人：dzjin 
 * 修改时间：2018年7月9日 上午9:56:37 
 * 修改备注： 
 * @version 
 *
 */
@Controller
@RequestMapping(value = "/project")
public class ProjectController {
	
	@Autowired
	ProjectService projectService;
	@Autowired
	ProjectRoleService projectRoleService;
	@Autowired
	ProjectUserService projectUserService;
	@Autowired
	ProjectCustomRoleService projectCustomRoleService;
	@Autowired
	ProjectFileService projectFileService;
	@Autowired
	ProjectFloderService projectFloderService;
	@Autowired
	ProjectAppTaskService projectAppTaskService;
	
	/**
	 * 新建项目，需要做三件事情：
	 * 1、插入一条项目信息记录到数据库中；
	 * 2、查询出后台管理中配置的项目默认角色（比如创建者、项目成员或者是访问者，），并查询出其包含的默认权限，据此构造一条项目内的自定义角色，并存储在数据库中；
	 * 3、构造一条项目成员记录，并赋予创建者的角色，如果不能成功的查询出创建者角色的ID，说明项目自定义角色的创建是有问题的，此处直接直接将原来新增的项目记录删除；
	 * @param project
	 * @return
	 */
	@RequestMapping("/insertProject")
	public String insertProject(Project project , HttpServletRequest request){
		//设置创建时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		project.setCreate_datetime(simpleDateFormat.format(new Date()));
		User user = (User)request.getAttribute("user");
		project.setCreator(String.valueOf(user.getId()));
		//新增一条记录到数据库中
		if(projectService.insertProject(project) == 1){
			//创建项目内的默认创建者、项目成员以及访问者权限
			List<ProjectRole> projectRoles = projectRoleService.selectProjectRole();
			Iterator<ProjectRole> iterator = projectRoles.iterator();
			while(iterator.hasNext()){
				ProjectRole projectRole = (ProjectRole)iterator.next();
				List<ProjectAuthority> projectAuthorities = 
						projectRoleService.selectProjectAuthorityByRoleId(projectRole.getId());
				Iterator<ProjectAuthority> iterator2 = projectAuthorities.iterator();
				StringBuffer stringBuffer = new StringBuffer();
				while(iterator2.hasNext()){
					ProjectAuthority projectAuthority = (ProjectAuthority)iterator2.next();
					stringBuffer.append(projectAuthority.getAuthority_number()+",");
				}
				ProjectCustomRole projectCustomRole = new ProjectCustomRole();
				projectCustomRole.setRolename(projectRole.getRole_name());
				projectCustomRole.setP_id(project.getId());
				projectCustomRole.setAuthorities(stringBuffer.toString());
				projectCustomRole.setCreator_id(user.getId());
				projectCustomRole.setCreate_datetime(simpleDateFormat.format(new Date()));
				projectCustomRoleService.insertProjectCustomRole(projectCustomRole);
			}
			
			//设置当前用户为新建项目的成员
			ProjectUser projectUser = new ProjectUser();
			projectUser.setProject_id(project.getId());
			projectUser.setUser_id(user.getId());
			projectUser.setLinkman_id(user.getId());
			ProjectCustomRole projectCustomRole = 
					projectCustomRoleService.getProjectCustomRoleByRolename("创建者", project.getId());
			if(projectCustomRole != null){
				//正常角色
				projectUser.setRole_id(projectCustomRole.getId());
				projectUser.setBind_date_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				projectUserService.insertProjectUser(projectUser);
			}else{
				//不能正确查询出项目自定义创建者角色，直接将之前新增的项目记录删除掉；
				projectService.deleteProjects(String.valueOf(project.getId()));
			}
		}
		return "/project/selectCreatedProject";
	}
	
	/**
	 * 获取项目基本信息，需要做一下几件事情：
	 * 1、获取项目的基本信息；
	 * 2、获取项目内的文件总数量；
	 * 3、获取项目内的应用数量；
	 * 4、获取项目内的应用结果数量；
	 * 5、获取项目内的成员数量；
	 * 6、查询出当前用户在该项目内的角色以及相应的权限；
	 * 7、查询出需要展示在门户页的应用结果；
	 * @param httpSession
	 * @param id
	 * @return
	 */
	@RequestMapping("/getProjectDetail")
	public String getProjectDetail(HttpServletRequest request , HttpSession httpSession , Integer id,String url){
		
		Project project = null;
		if(null == id){
			project = (Project) httpSession.getAttribute("project");
			project = projectService.getProjectDetail(project.getId());
		}else{
			project = projectService.getProjectDetail(id);
		}
		//获取项目内的根文件夹，并查询出每个根文件夹下的文件数量并统计总数量
		List<ProjectFloder> projectFloders = projectFloderService.selectProjectRootFloderByProjectId(project.getId());
		Iterator<ProjectFloder> iterator2 = projectFloders.iterator();
		int num = 0;
		while(iterator2.hasNext()){
			ProjectFloder projectFloder = (ProjectFloder)iterator2.next();
			List<ProjectFile> projectFiles = 
					projectFileService.selectProjectFileByFloderId(projectFloder.getId());
			num+=projectFiles.size();
		}
		project.setFileNum(num);
		//获取项目内应用、应用结果、成员数量
		project.setAppNum(projectService.countProjectApp(project.getId()));
		project.setAppResultNum(projectService.countProjectAppTask(project.getId()));
		project.setMemberNum(projectService.countProjectUser(project.getId()));

		httpSession.setAttribute("project", project);
		
		/*
		 * 此处需要根据项目ID和当前成员的ID查询当前用户在当前项目内的角色，然后放到Session中，供页面上进行显示。
		 */
		Map<String, Object> authoritys = new HashMap<String , Object>();
		User user = (User)request.getAttribute("user");
		ProjectUser projectUser = projectUserService.getProjectUser(project.getId(), user.getId());
		List<ProjectAuthority> projectAuthorities = null;
		if(projectUser == null || projectUser.getRole_id() == 0){
			//用户记录为空或者是角色ID为空，说明是是访问者或者只是添加到我的项目，需要查询当前项目自定义的访问者权限
			ProjectCustomRole projectCustomRole = 
					projectCustomRoleService.getProjectCustomRoleByRolename("访问者" , project.getId());
			if(projectCustomRole.getAuthorities() != null){
				String[] auths = projectCustomRole.getAuthorities().split(",");
				for(int i =0;i<auths.length;i++){
					authoritys.put(auths[i], true);
				}
			}
		}else{
			//如果是创建者，系统中创建者的权限是不能进行修改的，默认含有所有的权限，所以直接去查项目内的角色即可。
			if(project.getCreator().equals(String.valueOf(user.getId()))){
				projectAuthorities = projectRoleService.selectProjectAuthorityByRoleId(1);
				Iterator<ProjectAuthority> iterator = projectAuthorities.iterator();
				while(iterator.hasNext()){
					ProjectAuthority projectAuthority = (ProjectAuthority)iterator.next();
					authoritys.put(projectAuthority.getAuthority_number(), true);
				}
			}else{
				//如果是除创建者之外的角色，直接从项目自定义角色中取出权限列表即可。
				ProjectCustomRole projectCustomRole = projectCustomRoleService.getProjectCustomRole(projectUser.getRole_id());
				if(projectCustomRole.getAuthorities() != null){
					String[] auths = projectCustomRole.getAuthorities().split(",");
					for(int i =0;i<auths.length;i++){
						authoritys.put(auths[i], true);
					}
				}
			}
		}
		httpSession.setAttribute("authoritys", authoritys);
		
		List<ProjectAppTask> pAppTasks = projectAppTaskService.selectReleasedProjectAppTask(project.getId());
		//获取当前项目已经发布的应用结果的地址，需要对应用结果地址进行替换
		Iterator<ProjectAppTask> iterator = pAppTasks.iterator();
		while(iterator.hasNext()){
			ProjectAppTask projectAppTask = (ProjectAppTask)iterator.next();
			String resultAddress = projectAppTask.getResult_address();
			//需要对地址参数进行替换
			if(resultAddress != null && !resultAddress.equals("")){
				resultAddress = resultAddress.replace("{taskId}", String.valueOf(projectAppTask.getProject_id()));
				resultAddress = resultAddress.replace("{userid}", String.valueOf(projectAppTask.getUser_id()));
				resultAddress = resultAddress.replace("{username}", String.valueOf(projectAppTask.getUsername()));
				projectAppTask.setResult_address(resultAddress);
			}
		}
		httpSession.setAttribute("pAppTasks", pAppTasks);
		httpSession.setAttribute("url", url);
		return "/jsp/project/project_detail.jsp";
	}
	
	/**
	 * 更新项目简介
	 * @param httpSession
	 * @param introduction
	 * @return
	 */
	@RequestMapping("/updatePorjectIntroduction")
	@ResponseBody
	public Map<String, Object> updatePorjectIntroduction(HttpSession httpSession , String introduction){
		Map<String, Object> map = new HashMap<String , Object>();
		Project project = (Project) httpSession.getAttribute("project");
		project.setIntroduction(introduction);
		if(1 == projectService.updateProjectIntroduction(project)){
			map.put("result", true);
		}else{
			map.put("result", false);
			map.put("message", "更新项目简介失败");
		}
		return map;
	}
	
	/**
	 * 编辑项目基本信息
	 * @param project
	 * @return
	 */
	@RequestMapping("/editProject")
	@ResponseBody
	public Map<String, Object> editProject(Project project){
		Map<String, Object> map = new HashMap<String , Object>();
		if(1 == projectService.updateProject(project)){
			map.put("result", true);
		}else{
			map.put("result", false);
			map.put("message", "更新记录失败");
		}
		return map;
	}
	
	/**
	 * 查询公开的项目
	 * @param httpSession
	 * @param page
	 * @param strip
	 * @param searchWord 搜索条件
	 * @return
	 */
	@RequestMapping("/selectPublicProject")
	public String selectPublicProject(HttpSession httpSession ,  Integer page , Integer strip, String searchWord , Integer type, String allValue){		
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip = 12;
		}
		if(searchWord == null){
			searchWord = new String("");
			httpSession.setAttribute("projectSearchWord", null);
		}else{
			//更新关键字
			httpSession.setAttribute("projectSearchWord", searchWord);
		}
		httpSession.setAttribute("projectQueryCondition", null);
		
		Map<String, Object> map = new HashMap<String , Object>();
		map = projectService.selectPublicProject(page, strip , searchWord);
		httpSession.setAttribute("projects", map.get("list"));
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		httpSession.setAttribute("allValue", allValue);
		
		httpSession.setAttribute("pName", "");
		httpSession.setAttribute("pNumber", "");
		httpSession.setAttribute("pCreator", "");
		httpSession.setAttribute("createDatetime", "");
		httpSession.setAttribute("keyWords", "");
		httpSession.setAttribute("isOpen", "");
		
		httpSession.setAttribute("pNameGl", "");
		httpSession.setAttribute("pNumberGl", "");
		httpSession.setAttribute("creatorGl", "");
		httpSession.setAttribute("createDatetimeGl", "");
		httpSession.setAttribute("keyWordsGl", "");
		httpSession.setAttribute("isOpenGl", "");
		
		if(type == null || type == 1){
			return "/jsp/project/project_public.jsp";
		}else{
			return "/jsp/project/project_public2.jsp";
		}
		
	}
	
	/**
	 * 查询我创建的项目
	 * @param httpSession
	 * @param creator
	 * @param page
	 * @param strip
	 * @param searchWord 搜索条件
	 * @return
	 */
	@RequestMapping("/selectCreatedProject")
	public String selectCreatedProject(HttpSession httpSession , HttpServletRequest request ,
			Integer page , Integer strip , String searchWord , Integer type, String allValue){
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip = 12;
		}
		if(searchWord == null){
			searchWord = new String("");
			httpSession.setAttribute("projectSearchWord", null);
		}else{
			//更新关键字
			httpSession.setAttribute("projectSearchWord", searchWord);
		}
		httpSession.setAttribute("projectQueryCondition", null);
		
		Map<String, Object> map = new HashMap<String , Object>();
		User user = (User)request.getAttribute("user");
		map = projectService.selectCreatedProject(user.getId() , page , strip ,searchWord);
		httpSession.setAttribute("projects", map.get("list"));
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		httpSession.setAttribute("allValue", allValue);
		
		httpSession.setAttribute("pName", "");
		httpSession.setAttribute("pNumber", "");
		httpSession.setAttribute("pCreator", "");
		httpSession.setAttribute("createDatetime", "");
		httpSession.setAttribute("keyWords", "");
		httpSession.setAttribute("isOpen", "");
		
		httpSession.setAttribute("pNameGl", "");
		httpSession.setAttribute("pNumberGl", "");
		httpSession.setAttribute("creatorGl", "");
		httpSession.setAttribute("createDatetimeGl", "");
		httpSession.setAttribute("keyWordsGl", "");
		httpSession.setAttribute("isOpenGl", "");
		if(type == null || type == 1){
			return "/jsp/project/project_create.jsp";
		}else{
			return "/jsp/project/project_create2.jsp";
		}
		
	}
	
	/**
	 * 查询我的项目
	 * @param httpSession
	 * @param user_id
	 * @param page
	 * @param strip
	 * @param searchWord 搜索条件
	 * @return
	 */
	@RequestMapping("/selectMyProject")
	public String selectMyProject(HttpSession httpSession , HttpServletRequest request , 
			Integer page , Integer strip, String searchWord , Integer type, String allValue){
		if(page == null){
			page = 1;
		}
		if(strip == null){
			strip = 12;
		}
		if(searchWord == null){
			searchWord = new String("");
			httpSession.setAttribute("projectSearchWord", null);
		}else{
			//更新关键字
			httpSession.setAttribute("projectSearchWord", searchWord);
		}
		httpSession.setAttribute("projectQueryCondition", null);
		
		User user = (User)request.getAttribute("user");
		Map<String, Object> map = new HashMap<String , Object>();
		map = projectService.selectMyProject(user.getId(), page, strip , searchWord);
		httpSession.setAttribute("projects", map.get("list"));
		httpSession.setAttribute("total", map.get("total"));
		httpSession.setAttribute("page", page);
		httpSession.setAttribute("rows", strip);
		httpSession.setAttribute("allValue", allValue);
		
		httpSession.setAttribute("pName", "");
		httpSession.setAttribute("pNumber", "");
		httpSession.setAttribute("creator", "");
		httpSession.setAttribute("createDatetime", "");
		httpSession.setAttribute("keyWords", "");
		httpSession.setAttribute("isOpen", "");
		
		httpSession.setAttribute("pNameGl", "");
		httpSession.setAttribute("pNumberGl", "");
		httpSession.setAttribute("creatorGl", "");
		httpSession.setAttribute("createDatetimeGl", "");
		httpSession.setAttribute("keyWordsGl", "");
		httpSession.setAttribute("isOpenGl", "");
		if(type == null || type == 1){
			return "/jsp/project/project_mine.jsp";
		}else{
			return "/jsp/project/project_mine2.jsp";
		}
		
	}
	
	/**
	 * 将公共项目添加到我的项目
	 * @param session
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequestMapping("/addPublicProjectToMine")
	@ResponseBody
	public Map<String, Object> addPublicProjectToMine(HttpSession session , HttpServletRequest request , 
			String ids){
		User user = (User)request.getAttribute("user");
		Map<String, Object> map = new HashMap<>();
		if(projectService.addPublicProjectToMine(ids, user.getId())){
			map.put("result", true);
		}else{
			map.put("result", false);
			map.put("message", "部分项目添加至我的项目失败！");
		}
		return map;
	}
	
	/**
	 * 将公共项目添加到我的项目
	 * @param session
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequestMapping("/addPublicProjectToMine1")
	@ResponseBody
	public Map<String, Object> addPublicProjectToMine1(HttpSession session , HttpServletRequest request , String ids,String searchWord,String allValue, String noChangId,
			String pName, String pNumber, String pCreator, 
			String createDatetime, String keyWords, String isOpen, String pNameGl, String pNumberGl, String pCreatorGl, 
			String createDatetimeGl, String keyWordsGl, String isOpenGl){
		User user = (User)request.getAttribute("user");
		Map<String, Object> map = new HashMap<>();
		String projecIds="";
		if(allValue != null && !allValue.equals("") && allValue.equals("true")){//全部
			String sql="";
			if(noChangId != null && !noChangId.equals("")){
				sql += " and project.id not in("+noChangId+")";
			}
			
			if(pNameGl != null && !pNameGl.equals("")){
				sql += " and project.p_name like '%"+pNameGl+"%' ";
			}
			if(pNumberGl != null && !pNumberGl.equals("")){
				sql += " and project.p_number like '%"+pNumberGl+"%' ";
			}
			if(pCreatorGl != null && !pCreatorGl.equals("")){
				sql += " and user.username like '%"+pCreatorGl+"%' ";
			}
			if(createDatetimeGl != null && !createDatetimeGl.equals("")){
				sql += " and project.create_datetime like '%"+createDatetimeGl+"%' ";
			}
			if(keyWordsGl != null && !keyWordsGl.equals("")){
				sql += " and project.key_words like '%"+keyWordsGl+"%' ";
			}
			
			if(pName != null && !pName.equals("")){
				if(pName.indexOf(",")>-1){
					sql += "and (";
					String[] pname = pName.split(",");
					for(int i=0;i<pname.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "p_name"+"='"+String.valueOf(pname[i])+"'";
							if(i != pname.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and p_name in('"+pName+"')";
				}
				
			}
			
			if(pNumber != null && !pNumber.equals("")){
				if(pNumber.indexOf(",")>-1){
					sql += "and (";
					String[] pnumber = pNumber.split(",");
					for(int i=0;i<pnumber.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "p_number"+"='"+String.valueOf(pnumber[i])+"'";
							if(i != pnumber.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and p_number in('"+pNumber+"')";
				}
				
			}
			
			if(pCreator != null && !pCreator.equals("")){
				if(pCreator.indexOf(",")>-1){
					sql += "and (";
					String[] create = pCreator.split(",");
					for(int i=0;i<create.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "username"+"='"+String.valueOf(create[i])+"'";
							if(i != create.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and username in('"+pCreator+"')";
				}
				
			}
			
			if(createDatetime != null && !createDatetime.equals("")){
				if(createDatetime.indexOf(",")>-1){
					sql += "and (";
					String[] createdatetime = createDatetime.split(",");
					for(int i=0;i<createdatetime.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "create_datetime"+"='"+String.valueOf(createdatetime[i])+"'";
							if(i != createdatetime.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and create_datetime in('"+createDatetime+"')";
				}
				
			}
			
			if(keyWords != null && !keyWords.equals("")){
				if(keyWords.indexOf(",")>-1){
					sql += "and (";
					String[] keywords = keyWords.split(",");
					for(int i=0;i<keywords.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "key_words"+"='"+String.valueOf(keywords[i])+"'";
							if(i != keywords.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and key_words in('"+keyWords+"')";
				}
				
			}
			
			if(isOpen != null && !isOpen.equals("")){
				if(isOpen.indexOf(",")>-1){
					sql += "and (";
					String[] isopen = isOpen.split(",");
					for(int i=0;i<isopen.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						if(String.valueOf(isopen[i]).equals("已公开")){
							sql += "is_open=1";
							if(i != isopen.length-1){
								sql += " or ";	
							}
						}
						if(String.valueOf(isopen[i]).equals("未公开")){
							sql += "is_open=1";
							if(i != isopen.length-1){
								sql += " or ";	
							}
						}
					}
					sql += ")";
				}else{
					if(isOpen.equals("已公开")){
						isOpen = "1";
					}else if(isOpen.equals("未公开")){
						isOpen = "0";
					}
					sql += " and is_open ="+isOpen;
				}
				
			}
			List<Project> dataList = projectService.selectPublicProject1(searchWord, sql);
			for(Project project:dataList){
				projecIds += project.getId()+",";
				projecIds.substring(0, projecIds.length()-1);
			}
		}else{
			projecIds=ids;
		}
		if(projectService.addPublicProjectToMine(projecIds, user.getId())){
			map.put("result", true);
		}else{
			map.put("result", false);
			map.put("message", "部分项目添加至我的项目失败！");
		}
		return map;
	}
	
	/**
	 * 改变项目的公开状态
	 * @param ids
	 * @param type	0代表取消公开，1代表公开
	 * @return	
	 */
	@RequestMapping("/updateProjectOpenState")
	@ResponseBody
	public Map<String, Object> updateProjectOpenState(String ids , Integer is_open){
		Map<String, Object> map = new HashMap<>();
		if(projectService.updateProjectOpenState(ids, is_open)){
			map.put("result", true);
		}else{
			map.put("result", false);
			map.put("message", "部分项目更新公开状态失败");
		}
		return map;
	}
	
	/**
	 * 改变项目的公开状态
	 * @param ids
	 * @param type	0代表取消公开，1代表公开
	 * @return	
	 */
	@RequestMapping("/updateProjectOpenState1")
	@ResponseBody
	public Map<String, Object> updateProjectOpenState1(HttpServletRequest request, String ids , Integer is_open, String searchWord, String allValue, String noChangId,
			String pName, String pNumber, String pCreator, 
			String createDatetime, String keyWords, String isOpen, String pNameGl, String pNumberGl, String pCreatorGl, 
			String createDatetimeGl, String keyWordsGl, String isOpenGl){
		Map<String, Object> map = new HashMap<>();
		
		String projecIds="";
		if(allValue != null && !allValue.equals("") && allValue.equals("true")){//全部
			User user = (User)request.getAttribute("user");
			String sql="";
			if(noChangId != null && !noChangId.equals("")){
				sql += " and project.id not in("+noChangId+")";
			}
			
			if(pNameGl != null && !pNameGl.equals("")){
				sql += " and project.p_name like '%"+pNameGl+"%' ";
			}
			if(pNumberGl != null && !pNumberGl.equals("")){
				sql += " and project.p_number like '%"+pNumberGl+"%' ";
			}
			if(pCreatorGl != null && !pCreatorGl.equals("")){
				sql += " and user.username like '%"+pCreatorGl+"%' ";
			}
			if(createDatetimeGl != null && !createDatetimeGl.equals("")){
				sql += " and project.create_datetime like '%"+createDatetimeGl+"%' ";
			}
			if(keyWordsGl != null && !keyWordsGl.equals("")){
				sql += " and project.key_words like '%"+keyWordsGl+"%' ";
			}
			
			if(pName != null && !pName.equals("")){
				if(pName.indexOf(",")>-1){
					sql += "and (";
					String[] pname = pName.split(",");
					for(int i=0;i<pname.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "p_name"+"='"+String.valueOf(pname[i])+"'";
							if(i != pname.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and p_name in('"+pName+"')";
				}
				
			}
			
			if(pNumber != null && !pNumber.equals("")){
				if(pNumber.indexOf(",")>-1){
					sql += "and (";
					String[] pnumber = pNumber.split(",");
					for(int i=0;i<pnumber.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "p_number"+"='"+String.valueOf(pnumber[i])+"'";
							if(i != pnumber.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and p_number in('"+pNumber+"')";
				}
				
			}
			
			if(pCreator != null && !pCreator.equals("")){
				if(pCreator.indexOf(",")>-1){
					sql += "and (";
					String[] create = pCreator.split(",");
					for(int i=0;i<create.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "username"+"='"+String.valueOf(create[i])+"'";
							if(i != create.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and username in('"+pCreator+"')";
				}
				
			}
			
			if(createDatetime != null && !createDatetime.equals("")){
				if(createDatetime.indexOf(",")>-1){
					sql += "and (";
					String[] createdatetime = createDatetime.split(",");
					for(int i=0;i<createdatetime.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "create_datetime"+"='"+String.valueOf(createdatetime[i])+"'";
							if(i != createdatetime.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and create_datetime in('"+createDatetime+"')";
				}
				
			}
			
			if(keyWords != null && !keyWords.equals("")){
				if(keyWords.indexOf(",")>-1){
					sql += "and (";
					String[] keywords = keyWords.split(",");
					for(int i=0;i<keywords.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "key_words"+"='"+String.valueOf(keywords[i])+"'";
							if(i != keywords.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and key_words in('"+keyWords+"')";
				}
				
			}
			
			if(isOpen != null && !isOpen.equals("")){
				if(isOpen.indexOf(",")>-1){
					sql += "and (";
					String[] isopen = isOpen.split(",");
					for(int i=0;i<isopen.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						if(String.valueOf(isopen[i]).equals("已公开")){
							sql += "is_open=1";
							if(i != isopen.length-1){
								sql += " or ";	
							}
						}
						if(String.valueOf(isopen[i]).equals("未公开")){
							sql += "is_open=1";
							if(i != isopen.length-1){
								sql += " or ";	
							}
						}
					}
					sql += ")";
				}else{
					if(isOpen.equals("已公开")){
						isOpen = "1";
					}else if(isOpen.equals("未公开")){
						isOpen = "0";
					}
					sql += " and is_open ="+isOpen;
				}
				
			}
			List<Project> dataList = projectService.selectCreatedProject1(user.getId() ,searchWord, sql);
			for(Project project:dataList){
				projecIds += project.getId()+",";
				projecIds.substring(0, projecIds.length()-1);
			}
		}else{
			projecIds=ids;
		}

		if(projectService.updateProjectOpenState(projecIds, is_open)){
			map.put("result", true);
		}else{
			map.put("result", false);
			map.put("message", "部分项目更新公开状态失败");
		}
		return map;
	}
	
	/**
	 * 批量删除项目
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteProjects")
	@ResponseBody
	public Map<String, Object> deleteProjects(String ids){
		Map<String, Object> map = new HashMap<>();
		if(projectService.deleteProjects(ids)){
			map.put("result", true);
		}else{
			map.put("result", false);
			map.put("message", "部分项目删除失败");
		}
		return map;
	}
	
	/**
	 * 批量删除项目
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteProjects1")
	@ResponseBody
	public Map<String, Object> deleteProjects1(HttpServletRequest request, String ids , String searchWord, String allValue, String noChangId
			, String pName, String pNumber, String pCreator, 
			String createDatetime, String keyWords, String isOpen, String pNameGl, String pNumberGl, String pCreatorGl, 
			String createDatetimeGl, String keyWordsGl, String isOpenGl){
		Map<String, Object> map = new HashMap<>();
		String projecIds="";
		if(allValue != null && !allValue.equals("") && allValue.equals("true")){//全部
			User user = (User)request.getAttribute("user");
			String sql="";
			if(noChangId != null && !noChangId.equals("")){
				sql += " and project.id not in("+noChangId+")";
			}
			
			if(pNameGl != null && !pNameGl.equals("")){
				sql += " and project.p_name like '%"+pNameGl+"%' ";
			}
			if(pNumberGl != null && !pNumberGl.equals("")){
				sql += " and project.p_number like '%"+pNumberGl+"%' ";
			}
			if(pCreatorGl != null && !pCreatorGl.equals("")){
				sql += " and user.username like '%"+pCreatorGl+"%' ";
			}
			if(createDatetimeGl != null && !createDatetimeGl.equals("")){
				sql += " and project.create_datetime like '%"+createDatetimeGl+"%' ";
			}
			if(keyWordsGl != null && !keyWordsGl.equals("")){
				sql += " and project.key_words like '%"+keyWordsGl+"%' ";
			}
			
			if(pName != null && !pName.equals("")){
				if(pName.indexOf(",")>-1){
					sql += "and (";
					String[] pname = pName.split(",");
					for(int i=0;i<pname.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "p_name"+"='"+String.valueOf(pname[i])+"'";
							if(i != pname.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and p_name in('"+pName+"')";
				}
				
			}
			
			if(pNumber != null && !pNumber.equals("")){
				if(pNumber.indexOf(",")>-1){
					sql += "and (";
					String[] pnumber = pNumber.split(",");
					for(int i=0;i<pnumber.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "p_number"+"='"+String.valueOf(pnumber[i])+"'";
							if(i != pnumber.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and p_number in('"+pNumber+"')";
				}
				
			}
			
			if(pCreator != null && !pCreator.equals("")){
				if(pCreator.indexOf(",")>-1){
					sql += "and (";
					String[] create = pCreator.split(",");
					for(int i=0;i<create.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "username"+"='"+String.valueOf(create[i])+"'";
							if(i != create.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and username in('"+pCreator+"')";
				}
				
			}
			
			if(createDatetime != null && !createDatetime.equals("")){
				if(createDatetime.indexOf(",")>-1){
					sql += "and (";
					String[] createdatetime = createDatetime.split(",");
					for(int i=0;i<createdatetime.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "create_datetime"+"='"+String.valueOf(createdatetime[i])+"'";
							if(i != createdatetime.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and create_datetime in('"+createDatetime+"')";
				}
				
			}
			
			if(keyWords != null && !keyWords.equals("")){
				if(keyWords.indexOf(",")>-1){
					sql += "and (";
					String[] keywords = keyWords.split(",");
					for(int i=0;i<keywords.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "key_words"+"='"+String.valueOf(keywords[i])+"'";
							if(i != keywords.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and key_words in('"+keyWords+"')";
				}
				
			}
			
			if(isOpen != null && !isOpen.equals("")){
				if(isOpen.indexOf(",")>-1){
					sql += "and (";
					String[] isopen = isOpen.split(",");
					for(int i=0;i<isopen.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						if(String.valueOf(isopen[i]).equals("已公开")){
							sql += "is_open=1";
							if(i != isopen.length-1){
								sql += " or ";	
							}
						}
						if(String.valueOf(isopen[i]).equals("未公开")){
							sql += "is_open=1";
							if(i != isopen.length-1){
								sql += " or ";	
							}
						}
					}
					sql += ")";
				}else{
					if(isOpen.equals("已公开")){
						isOpen = "1";
					}else if(isOpen.equals("未公开")){
						isOpen = "0";
					}
					sql += " and is_open ="+isOpen;
				}
				
			}
			List<Project> dataList = projectService.selectCreatedProject1(user.getId() ,searchWord, sql);
			for(Project project:dataList){
				projecIds += project.getId()+",";
				projecIds.substring(0, projecIds.length()-1);
			}
		}else{
			projecIds=ids;
		}

		if(projectService.deleteProjects(projecIds)){
			map.put("result", true);
		}else{
			map.put("result", false);
			map.put("message", "部分项目删除失败");
		}
		return map;
	}
	
	/**
	 * 退出项目
	 * @param ids
	 * @return
	 */
	@RequestMapping("/exit")
	@ResponseBody
	public Map<String, Object> exit(String ids , HttpServletRequest request){
		Map<String, Object> map = new HashMap<>();
		User user = (User)request.getAttribute("user");
		if(projectService.exits(ids, user.getId())){
			map.put("result", true);
		}else{
			map.put("result", false);
			map.put("message", "部分项目退出失败");
		}
		return map;
	}	
	
	/**
	 * 退出项目
	 * @param ids
	 * @return
	 */
	@RequestMapping("/exit2")
	@ResponseBody
	public Map<String, Object> exit2(String ids , HttpServletRequest request, String searchWord, 
			String allValue, String noChangId, String pName, String pNumber, String pCreator, 
			String createDatetime, String keyWords, String isOpen, String pNameGl, String pNumberGl, String pCreatorGl, 
			String createDatetimeGl, String keyWordsGl, String isOpenGl){
		Map<String, Object> map = new HashMap<>();
		String projecIds="";
		User user = (User)request.getAttribute("user");
		
		if(allValue != null && !allValue.equals("") && allValue.equals("true")){//全部
			String sql="";
			if(noChangId != null && !noChangId.equals("")){
				sql += " and project.id not in("+noChangId+")";
			}
			
			if(pNameGl != null && !pNameGl.equals("")){
				sql += " and project.p_name like '%"+pNameGl+"%' ";
			}
			if(pNumberGl != null && !pNumberGl.equals("")){
				sql += " and project.p_number like '%"+pNumberGl+"%' ";
			}
			if(pCreatorGl != null && !pCreatorGl.equals("")){
				sql += " and user.username like '%"+pCreatorGl+"%' ";
			}
			if(createDatetimeGl != null && !createDatetimeGl.equals("")){
				sql += " and project.create_datetime like '%"+createDatetimeGl+"%' ";
			}
			if(keyWordsGl != null && !keyWordsGl.equals("")){
				sql += " and project.key_words like '%"+keyWordsGl+"%' ";
			}
			
			if(pName != null && !pName.equals("")){
				if(pName.indexOf(",")>-1){
					sql += "and (";
					String[] pname = pName.split(",");
					for(int i=0;i<pname.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "p_name"+"='"+String.valueOf(pname[i])+"'";
							if(i != pname.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and p_name in('"+pName+"')";
				}
				
			}
			
			if(pNumber != null && !pNumber.equals("")){
				if(pNumber.indexOf(",")>-1){
					sql += "and (";
					String[] pnumber = pNumber.split(",");
					for(int i=0;i<pnumber.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "p_number"+"='"+String.valueOf(pnumber[i])+"'";
							if(i != pnumber.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and p_number in('"+pNumber+"')";
				}
				
			}
			
			if(pCreator != null && !pCreator.equals("")){
				if(pCreator.indexOf(",")>-1){
					sql += "and (";
					String[] create = pCreator.split(",");
					for(int i=0;i<create.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "username"+"='"+String.valueOf(create[i])+"'";
							if(i != create.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and username in('"+pCreator+"')";
				}
				
			}
			
			if(createDatetime != null && !createDatetime.equals("")){
				if(createDatetime.indexOf(",")>-1){
					sql += "and (";
					String[] createdatetime = createDatetime.split(",");
					for(int i=0;i<createdatetime.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "create_datetime"+"='"+String.valueOf(createdatetime[i])+"'";
							if(i != createdatetime.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and create_datetime in('"+createDatetime+"')";
				}
				
			}
			
			if(keyWords != null && !keyWords.equals("")){
				if(keyWords.indexOf(",")>-1){
					sql += "and (";
					String[] keywords = keyWords.split(",");
					for(int i=0;i<keywords.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
							//其他字段不需要进行转换
							sql += "key_words"+"='"+String.valueOf(keywords[i])+"'";
							if(i != keywords.length-1){
								sql += " or ";	
							}
						
					}
					sql += ")";
				}else{
					sql += " and key_words in('"+keyWords+"')";
				}
				
			}
			
			if(isOpen != null && !isOpen.equals("")){
				if(isOpen.indexOf(",")>-1){
					sql += "and (";
					String[] isopen = isOpen.split(",");
					for(int i=0;i<isopen.length;i++){
						//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						if(String.valueOf(isopen[i]).equals("已公开")){
							sql += "is_open=1";
							if(i != isopen.length-1){
								sql += " or ";	
							}
						}
						if(String.valueOf(isopen[i]).equals("未公开")){
							sql += "is_open=1";
							if(i != isopen.length-1){
								sql += " or ";	
							}
						}
					}
					sql += ")";
				}else{
					if(isOpen.equals("已公开")){
						isOpen = "1";
					}else if(isOpen.equals("未公开")){
						isOpen = "0";
					}
					sql += " and is_open ="+isOpen;
				}
				
			}			
			
			List<Project> dataList = projectService.selectMyProject2(user.getId(), searchWord, sql);
			for(Project project:dataList){
				projecIds += project.getId()+",";
				projecIds.substring(0, projecIds.length()-1);
			}
		}else{
			projecIds=ids;
		}
		if(projectService.exits(projecIds, user.getId())){
			map.put("result", true);
		}else{
			map.put("result", false);
			map.put("message", "部分项目退出失败");
		}
		return map;
	}	
	
	
}
