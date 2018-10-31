package com.xtkong.model;

import java.util.HashMap;
import java.util.List;

/**
 * 格式类型
 *
 */
public class FormatType {
	private int ft_id;// 格式类型ID
	private String ft_name; // 格式类型名
	private int cs_id; // 采集源ID
	private String floder;// 目录树叶节点
	private int create_uid;
	private String create_datetime;// 创建时间
	private int update_uid;
	private String update_datetime;// 更新时间
	private boolean is_view;// 状态

	private String updater="";// 更新人
	private String creator="";// 创建人

	private List<FormatType> formatTypeFolders;

	private List<FormatField> formatFields;//格式字段
	
	
	private HashMap<String, String>formatDataNodes=new HashMap<>();//数据节点

	public List<FormatType> getFormatTypeFolders() {
		return formatTypeFolders;
	}

	public void setFormatTypeFolders(List<FormatType> formatTypeFolders) {
		this.formatTypeFolders = formatTypeFolders;
	}


	public HashMap<String, String> getFormatDataNodes() {
		return formatDataNodes;
	}

	public void setFormatDataNodes(HashMap<String, String> formatDataNodes) {
		this.formatDataNodes = formatDataNodes;
	}

	public List<FormatField> getFormatFields() {
		return formatFields;
	}

	public void setFormatFields(List<FormatField> formatFields) {
		this.formatFields = formatFields;
	}

	public int getFt_id() {
		return ft_id;
	}

	public void setFt_id(int ft_id) {
		this.ft_id = ft_id;
	}

	public String getFt_name() {
		return ft_name;
	}

	public void setFt_name(String ft_name) {
		this.ft_name = ft_name;
	}

	public int getCs_id() {
		return cs_id;
	}

	public void setCs_id(int cs_id) {
		this.cs_id = cs_id;
	}

	public String getFloder() {
		return floder;
	}

	public void setFloder(String floder) {
		this.floder = floder;
	}

	public int getCreate_uid() {
		return create_uid;
	}

	public void setCreate_uid(int create_uid) {
		this.create_uid = create_uid;
	}

	public String getCreate_datetime() {
		return create_datetime;
	}

	public void setCreate_datetime(String create_datetime) {
		this.create_datetime = create_datetime;
	}

	public int getUpdate_uid() {
		return update_uid;
	}

	public void setUpdate_uid(int update_uid) {
		this.update_uid = update_uid;
	}

	public String getUpdate_datetime() {
		return update_datetime;
	}

	public void setUpdate_datetime(String update_datetime) {
		this.update_datetime = update_datetime;
	}

	public boolean isIs_view() {
		return is_view;
	}

	public void setIs_view(boolean is_view) {
		this.is_view = is_view;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}



}
