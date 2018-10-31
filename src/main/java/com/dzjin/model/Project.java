package com.dzjin.model;

import java.util.List;

/**
	* 表名：project 项目基础信息表
	* 作者：dzjin
	* 联系方式：dzjin5678@163.com
	* 创建时间：2018-05-07 14:13:00
	* 
	* 说明：
	* 	前期项目的基础信息字段比较少，后期可能会进行增加，注意。
	*/

public class Project {
	
	private int id;	//项目ID
	private String p_name;	//项目名称
	private String p_number;	//项目编号
	private String creator;	//项目创建人ID
	private String creatorName;
	private String create_datetime; //项目创建时间
	private short is_open;	//项目是否公开 0不公开；1公开
	private short is_asy;	//同步异步0同步；1异步
	private short is_show;	//是否显示在欢迎页
	private String key_words;	//项目关键字
	private String introduction;	//项目简介
	
	private Integer fileNum;
	private Integer appNum;
	private Integer appResultNum;
	private Integer memberNum;
	
	private List<ProjectAppTask> projectAppTasks = null;
	
	
	public List<ProjectAppTask> getProjectAppTasks() {
		return projectAppTasks;
	}
	public void setProjectAppTasks(List<ProjectAppTask> projectAppTasks) {
		this.projectAppTasks = projectAppTasks;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public short getIs_show() {
		return is_show;
	}
	public void setIs_show(short is_show) {
		this.is_show = is_show;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getP_number() {
		return p_number;
	}
	public void setP_number(String p_number) {
		this.p_number = p_number;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreate_datetime() {
		return create_datetime;
	}
	public void setCreate_datetime(String create_datetime) {
		this.create_datetime = create_datetime;
	}
	public short getIs_open() {
		return is_open;
	}
	public void setIs_open(short is_open) {
		this.is_open = is_open;
	}
	public short getIs_asy() {
		return is_asy;
	}
	public void setIs_asy(short is_asy) {
		this.is_asy = is_asy;
	}
	public String getKey_words() {
		return key_words;
	}
	public void setKey_words(String key_words) {
		this.key_words = key_words;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Integer getFileNum() {
		return fileNum;
	}
	public void setFileNum(Integer fileNum) {
		this.fileNum = fileNum;
	}
	public Integer getAppNum() {
		return appNum;
	}
	public void setAppNum(Integer appNum) {
		this.appNum = appNum;
	}
	public Integer getAppResultNum() {
		return appResultNum;
	}
	public void setAppResultNum(Integer appResultNum) {
		this.appResultNum = appResultNum;
	}
	public Integer getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(Integer memberNum) {
		this.memberNum = memberNum;
	}
	

}

