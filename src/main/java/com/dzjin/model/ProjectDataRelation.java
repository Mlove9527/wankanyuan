package com.dzjin.model;
/** 
* @Author dzjin 项目-格式数据关系表
* @Time 2018年6月4日 上午8:57:34 
* @Version 1.0
* <p>Description:</p>
*/
public class ProjectDataRelation {
	
	private int p_id;
	private String source_data_id;

	public ProjectDataRelation(int p_id, String source_data_id) {
		super();
		this.p_id = p_id;
		this.source_data_id = source_data_id;
	}

	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public String getSource_data_id() {
		return source_data_id;
	}

	public void setSource_data_id(String source_data_id) {
		this.source_data_id = source_data_id;
	}
	

}
