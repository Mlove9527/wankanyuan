package com.dzjin.model;

import java.util.Map;

/**
 * 项目内角色
 * 项目名称：wankangyuan 
 * 类名称：ProjectRole 
 * 类描述： 
 * 创建人：dzjin 
 * 创建时间：2018年6月19日 上午11:27:08 
 * 修改人：dzjin 
 * 修改时间：2018年6月19日 上午11:27:08 
 * 修改备注： 
 * @version 
 *
 */
public class ProjectRole {
	
	private int id;
	private String role_name;
	private String create_datetime;
	private String update_datetime;
	private Map<String, Object> authoritys;
	
	public String getCreate_datetime() {
		return create_datetime;
	}

	public void setCreate_datetime(String create_datetime) {
		this.create_datetime = create_datetime;
	}

	public String getUpdate_datetime() {
		return update_datetime;
	}

	public void setUpdate_datetime(String update_datetime) {
		this.update_datetime = update_datetime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public Map<String, Object> getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(Map<String, Object> authoritys) {
		this.authoritys = authoritys;
	}

	@Override
	public String toString() {
		return "ProjectRole [id=" + id + ", role_name=" + role_name + ", authoritys=" + authoritys + "]";
	}
	
}
