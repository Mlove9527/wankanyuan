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

import com.dzjin.dao.ProjectAppTaskFilterDao;
import com.dzjin.model.AppTaskProgressResult;
import com.dzjin.model.ProjectAppTask;
import com.dzjin.model.QueryCondition;
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
 * 类名称：ProjectAppTaskFilterService 
 * 类描述： 项目内应用运行结果筛选service
 * 创建人：dzjin 
 * 创建时间：2018年7月14日 下午10:25:00 
 * 修改人：dzjin 
 * 修改时间：2018年7月14日 下午10:25:00 
 * 修改备注： 
 * @version 
 *
 */
@Service
public class ProjectAppTaskFilterService {
	
	@Autowired
	ProjectAppTaskFilterDao projectAppTaskFilterDao;
	@Autowired
	ApplicationService applicationService;
	
	@Value("${project.task.rateSearch}")
	private String url;
	
	/**
	 * 根据过滤条件获得筛选值
	 * @param columnName
	 * @param project_id
	 * @param filter
	 * @return
	 */
	public List<String> selectAppTaskDistinctColumnValue(String columnName , Integer project_id , String filter){
		
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("columnName", columnName);
		map.put("project_id", String.valueOf(project_id));
		map.put("filter", filter);
		
		switch(columnName){
			case "isRelease":
				List<String> strings = new ArrayList<String>();
				strings.add("已发布");
				strings.add("未发布");
				return strings;
			case "taskName":
				return projectAppTaskFilterDao.selectAppTaskDistinctColumnValue(map);
			case "app_name":
				return projectAppTaskFilterDao.selectAppTaskDistinctColumnValue(map);
			case "username":
				return projectAppTaskFilterDao.selectAppTaskDistinctColumnValue(map);
			case "create_datetime":
				return projectAppTaskFilterDao.selectAppTaskDistinctColumnValue(map);
			case "taskDescription":
				return projectAppTaskFilterDao.selectAppTaskDistinctColumnValue(map);
			case "is_async":
				List<String> strings1 = new ArrayList<String>();
				strings1.add("异步");
				strings1.add("即时");
				return strings1;
			default:
				return null;
		}
	}
	
	/**
	 * 筛选数据
	 * @param page
	 * @param strip
	 * @param searchWord
	 * @param projectQueryCondition
	 * @param project_id
	 * @return
	 */
	public Map<String, Object> selectAppTaskByFilterCondition(
			Integer page , Integer strip , 
			String searchWord ,QueryCondition projectQueryCondition , Integer project_id , User user){
		PageHelper.startPage(page, strip);
		Map<String, Object> map = new HashMap<String , Object>();
		//将相关的筛选条件放入到map中，方便动态构造sql语句的时候使用
		map.put("project_id", String.valueOf(project_id));//项目ID
		map.put("searchWord", searchWord);//搜索关键字，对应前端右上角的搜索框中的值
		map.put("columnName", projectQueryCondition.getColumnName());//筛选的字段名
		map.put("order", projectQueryCondition.getOrder());//筛选字段排序方式
		map.put("filter", projectQueryCondition.getFilter());//筛选字段过滤条件
		map.put("values", projectQueryCondition.getStrings());//筛选值，list形式
		
		List<ProjectAppTask> projectAppTasks = projectAppTaskFilterDao.selectProjectAppTaskByFilterCondition(map);
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
		Map<String, Object> result = new HashMap<String , Object>();
		result.put("total", pageInfo.getTotal());
		result.put("list", projectAppTasks);
		return result;
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
