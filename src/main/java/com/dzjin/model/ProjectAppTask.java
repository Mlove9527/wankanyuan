package com.dzjin.model;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectAppTask 
 * 类描述： 应用运行结果
 * 创建人：dzjin 
 * 创建时间：2018年6月17日 上午10:30:55 
 * 修改人：dzjin 
 * 修改时间：2018年6月17日 上午10:30:55 
 * 修改备注： 
 * @version 
 *
 */
public class ProjectAppTask {
	
	//结果ID
	private String id;
	//结果名称
	private String taskName;
	//项目ID
	private String project_id;
	//应用ID
	private String app_id;
	//应用名称
	private String app_name;
	//用户ID，创建者
	private String user_id;
	//用户名，创建者
	private String username;
	//创建时间
	private String create_datetime;
	//运行使用参数
	private String param;
	//运行结果描述
	private String taskDescription;
	//进度
	private String progress;
	//发布状态
	private String isRelease;
	//异步/即时
	private String is_async;
	//结果地址
	private String result_address;
	
	public String getResult_address() {
		return result_address;
	}
	public void setResult_address(String result_address) {
		this.result_address = result_address;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public String getCreate_datetime() {
		return create_datetime;
	}
	public void setCreate_datetime(String create_datetime) {
		this.create_datetime = create_datetime;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getIsRelease() {
		return isRelease;
	}
	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
	}
	public String getIs_async() {
		return is_async;
	}
	public void setIs_async(String is_async) {
		this.is_async = is_async;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	
	

}
