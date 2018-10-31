package com.dzjin.model;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectCustomRole 
 * 类描述： 项目内实际角色
 * 创建人：dzjin 
 * 创建时间：2018年6月29日 下午3:02:42 
 * 修改人：dzjin 
 * 修改时间：2018年6月29日 下午3:02:42 
 * 修改备注： 
 * @version 
 *
 */
public class ProjectCustomRole {
	
	private int id;
	private String rolename;
	private int p_id;
	private String p_name;	//项目名称
	private String authorities;
	private int creator_id;
	private String create_datetime;
	private int updater_id;
	private String update_datetime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	public String getAuthorities() {
		return authorities;
	}
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	public int getCreator_id() {
		return creator_id;
	}
	public void setCreator_id(int creator_id) {
		this.creator_id = creator_id;
	}
	public String getCreate_datetime() {
		return create_datetime;
	}
	public void setCreate_datetime(String create_datetime) {
		this.create_datetime = create_datetime;
	}
	public int getUpdater_id() {
		return updater_id;
	}
	public void setUpdater_id(int updater_id) {
		this.updater_id = updater_id;
	}
	public String getUpdate_datetime() {
		return update_datetime;
	}
	public void setUpdate_datetime(String update_datetime) {
		this.update_datetime = update_datetime;
	}
	@Override
	public String toString() {
		return "ProjectCustomRole [id=" + id + ", rolename=" + rolename + ", p_id=" + p_id +",p_name="+p_name+ ", authorities="
				+ authorities + ", creator_id=" + creator_id + ", create_datetime=" + create_datetime + ", updater_id="
				+ updater_id + ", update_datetime=" + update_datetime + "]";
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
}
