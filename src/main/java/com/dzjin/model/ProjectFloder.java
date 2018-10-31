package com.dzjin.model;

import java.util.List;

/**
	* 表名：project_floder
	* 作者：dzjin
	* 联系方式：dzjin5678@163.com
	* 创建时间：2018-05-08 19:28:36
	*/

public class ProjectFloder {

	private int id;	//文件夹ID
	private String floder_name;	//文件夹名字
	private Integer parent_id;	//父文件夹ID
	private Short is_root;	//是否是根文件夹
	private Integer p_id;	//项目ID
	
	private List<ProjectFloder> projectFloders;	//子文件夹列表
	
	
	public List<ProjectFloder> getProjectFloders() {
		return projectFloders;
	}
	public void setProjectFloders(List<ProjectFloder> projectFloders) {
		this.projectFloders = projectFloders;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFloder_name() {
		return floder_name;
	}
	public void setFloder_name(String floder_name) {
		this.floder_name = floder_name;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public Short getIs_root() {
		return is_root;
	}
	public void setIs_root(Short is_root) {
		this.is_root = is_root;
	}
	public Integer getP_id() {
		return p_id;
	}
	public void setP_id(Integer p_id) {
		this.p_id = p_id;
	}
	
	@Override
	public String toString() {
		return "ProjectFloder [id=" + id + ", floder_name=" + floder_name + ", parent_id=" + parent_id + ", is_root="
				+ is_root + ", p_id=" + p_id + ", projectFloders=" + projectFloders + "]";
	}

}

