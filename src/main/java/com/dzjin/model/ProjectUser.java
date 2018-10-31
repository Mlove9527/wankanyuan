package com.dzjin.model;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectUser 
 * 类描述： 项目成员表
 * 创建人：dzjin 
 * 创建时间：2018年6月14日 下午1:05:00 
 * 修改人：dzjin 
 * 修改时间：2018年6月14日 下午1:05:00 
 * 修改备注： 
 * @version 
 *
 */
public class ProjectUser {
	
	private int id;
	private int project_id;
	private int user_id;
	private String username;
	private int linkman_id;
	private String linkman_username;
	private String bind_date_time;
	private int role_id;
	private String role_name;
	private int file_num;
	private int topic_num;
	private int topic_follow_num;
	
	public int getFile_num() {
		return file_num;
	}
	public void setFile_num(int file_num) {
		this.file_num = file_num;
	}
	public int getTopic_num() {
		return topic_num;
	}
	public void setTopic_num(int topic_num) {
		this.topic_num = topic_num;
	}
	public int getTopic_follow_num() {
		return topic_follow_num;
	}
	public void setTopic_follow_num(int topic_follow_num) {
		this.topic_follow_num = topic_follow_num;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLinkman_username() {
		return linkman_username;
	}
	public void setLinkman_username(String linkman_username) {
		this.linkman_username = linkman_username;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getLinkman_id() {
		return linkman_id;
	}
	public void setLinkman_id(int linkman_id) {
		this.linkman_id = linkman_id;
	}
	public String getBind_date_time() {
		return bind_date_time;
	}
	public void setBind_date_time(String bind_date_time) {
		this.bind_date_time = bind_date_time;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	@Override
	public String toString() {
		return "ProjectUser [id=" + id + ", project_id=" + project_id + ", user_id=" + user_id + ", username="
				+ username + ", linkman_id=" + linkman_id + ", linkman_username=" + linkman_username
				+ ", bind_date_time=" + bind_date_time + ", role_id=" + role_id + ", role_name=" + role_name
				+ ", file_num=" + file_num + ", topic_num=" + topic_num + ", topic_follow_num=" + topic_follow_num
				+ "]";
	}

}
