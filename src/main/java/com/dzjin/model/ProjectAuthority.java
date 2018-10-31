package com.dzjin.model;

/**
 * 项目内权限
 * 项目名称：wankangyuan 
 * 类名称：ProjectAuthority 
 * 类描述： 
 * 创建人：dzjin 
 * 创建时间：2018年6月19日 上午11:24:30 
 * 修改人：dzjin 
 * 修改时间：2018年6月19日 上午11:24:30 
 * 修改备注： 
 * @version 
 *
 */
public class ProjectAuthority {
	
	private int id;
	private String authority_name;//权限名称
	private String authority_number;//权限编号
	private String parent_id;//上一级权限编号
	
	
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAuthority_name() {
		return authority_name;
	}
	public void setAuthority_name(String authority_name) {
		this.authority_name = authority_name;
	}
	public String getAuthority_number() {
		return authority_number;
	}
	public void setAuthority_number(String authority_number) {
		this.authority_number = authority_number;
	}
	@Override
	public String toString() {
		return "ProjectAuthority [id=" + id + ", authority_name=" + authority_name + ", authority_number="
				+ authority_number + "]";
	}
	
	

}
