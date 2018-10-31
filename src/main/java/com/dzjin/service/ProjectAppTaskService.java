package com.dzjin.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dzjin.dao.ProjectAppTaskDao;
import com.dzjin.model.AppTaskProgressResult;
import com.dzjin.model.ProjectAppTask;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.liutianjun.pojo.Application;
import com.liutianjun.pojo.User;
import com.liutianjun.service.ApplicationService;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectAppTaskService 
 * 类描述： 
 * 创建人：dzjin 
 * 创建时间：2018年6月17日 上午11:13:41 
 * 修改人：dzjin 
 * 修改时间：2018年6月17日 上午11:13:41 
 * 修改备注： 
 * @version 
 *
 */
@Service
public class ProjectAppTaskService {
	
	@Autowired
	ProjectAppTaskDao projectAppTaskDao;
	@Autowired
	ApplicationService applicationService;
	
	@Value("${project.task.rateSearch}")
	private String url;
	
	public int insertProjectAppTask(ProjectAppTask projectAppTask){
		return projectAppTaskDao.insertProjectAppTask(projectAppTask);
	}
	
	public int updateProjectAppTask(ProjectAppTask projectAppTask){
		return projectAppTaskDao.updateProjectAppTask(projectAppTask);
	}
	
	public Map<String, Object> selectProjectAppTask(Integer page , Integer strip , Integer project_id , String searchWord , User user){
		PageHelper.startPage(page, strip);
		List<ProjectAppTask> projectAppTasks = projectAppTaskDao.selectProjectAppTask(project_id , searchWord);
		
		Iterator<ProjectAppTask> iterator = projectAppTasks.iterator();
		while(iterator.hasNext()){//遍历分页查询出的每一个应用结果
			ProjectAppTask projectAppTask = (ProjectAppTask)iterator.next();
			
			//需要根据taskId网络请求运行结果进度
			List<Map<String, Object>> appTaskRequest = new ArrayList<Map<String, Object>>();
			Map<String, Object> appTaskRequestTemp = new HashMap<String,Object>();//构造请求数据
			appTaskRequestTemp.put("userId", user.getId());
			appTaskRequestTemp.put("taskId", projectAppTask.getId());
			appTaskRequestTemp.put("username", user.getUsername());
			appTaskRequest.add(appTaskRequestTemp);
			String urlTemp = new String(url);
			Application application = applicationService.selectByPrimaryKey(Integer.valueOf(projectAppTask.getApp_id()));
			if(application.getComputeNodeName() != null){
				urlTemp += "?server="+application.getComputeNodeName();//每个应用结果的计算节点都不一样，请求的接口也不一样
			}
			String result = sendPostRequest(urlTemp, new Gson().toJson(appTaskRequest));//发送请求并获取返回的内容
			if(result != null && !result.trim().equals("")){
				JsonObject jsonObject = (JsonObject)new JsonParser().parse(result);
				if(jsonObject.get("code").getAsString().equals("1")){
					JsonElement jsonElement = jsonObject.get("task");
					AppTaskProgressResult[] appTaskProgressResults = new Gson().fromJson(jsonElement, AppTaskProgressResult[].class);
					for(int i=0;i<appTaskProgressResults.length;i++){
						if(appTaskProgressResults[i].getId().equals(String.valueOf(projectAppTask.getId()))){
							projectAppTask.setProgress(appTaskProgressResults[i].getProgress());//设置应用结果进度
						}
					}
				}
			}
		}
		PageInfo<ProjectAppTask> pageInfo = new PageInfo<ProjectAppTask>(projectAppTasks);
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("list", projectAppTasks);
		map.put("total", pageInfo.getTotal());
		return map;
	}
	
	public List<ProjectAppTask> selectReleasedProjectAppTask(Integer project_id){
		return projectAppTaskDao.selectReleasedProjectAppTask(project_id);
	}
	
	public ProjectAppTask getProjectAppTask(Integer id){
		return projectAppTaskDao.getProjectAppTask(id);
	}
	
	public int updateIsRelease(Integer id , Integer isRelease){
		return projectAppTaskDao.updateIsRelease(id, isRelease);
	}
	
	public int deleteProjectAppTask(Integer id){
		return projectAppTaskDao.deleteProjectAppTask(id);
	}
	
	/**
	 * 构建http请求，查询运行结果进度
	 * @param url
	 * @param param
	 * @return
	 */
	public static String sendPostRequest(String url,String param){
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null; //写
		String result="";
		try{
		    URL sendUrl = new URL(url);
		    httpURLConnection = (HttpURLConnection)sendUrl.openConnection();
		    httpURLConnection.setRequestMethod("POST");
		    httpURLConnection.setRequestProperty("headerdata", "ceshiyongde");
		    httpURLConnection.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
		    httpURLConnection.setDoOutput(true);
		    httpURLConnection.setUseCaches(false);
		    httpURLConnection.setConnectTimeout(30000); //30秒连接超时
		    httpURLConnection.setReadTimeout(30000);    //30秒读取超时
		    //获取输出流
		    out = httpURLConnection.getOutputStream();
		    //输出流里写入POST参数
		    out.write(param.getBytes());
		    out.flush();
		    out.close();
		    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
		        result =br.readLine();
		    }catch(Exception e) {
		        e.printStackTrace();
		    }
	    	return result;
	}

}
