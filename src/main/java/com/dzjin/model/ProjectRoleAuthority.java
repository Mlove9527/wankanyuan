package com.dzjin.model;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectRoleAuthority 
 * 类描述： 
 * 创建人：dzjin 
 * 创建时间：2018年6月19日 上午11:36:58 
 * 修改人：dzjin 
 * 修改时间：2018年6月19日 上午11:36:58 
 * 修改备注： 
 * @version 
 *
 */
public class ProjectRoleAuthority {
	
	private int id;
	private int role_id;
	private int authority_id;
	private String bind_datetime;;
	private int user_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public int getAuthority_id() {
		return authority_id;
	}
	public void setAuthority_id(int authority_id) {
		this.authority_id = authority_id;
	}
	public String getBind_datetime() {
		return bind_datetime;
	}
	public void setBind_datetime(String bind_datetime) {
		this.bind_datetime = bind_datetime;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "ProjectRoleAuthority [id=" + id + ", role_id=" + role_id + ", authority_id=" + authority_id
				+ ", bind_datetime=" + bind_datetime + ", user_id=" + user_id + "]";
	}
	
	

}
