package com.dzjin.model;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectTopicFollow 
 * 类描述： 项目主题跟帖
 * 创建人：dzjin 
 * 创建时间：2018年6月14日 下午3:52:23 
 * 修改人：dzjin 
 * 修改时间：2018年6月14日 下午3:52:23 
 * 修改备注： 
 * @version 
 *
 */
public class ProjectTopicFollow {
	
	private Integer id;
	private Integer project_topic_id;
	private Integer user_id;
	private String username;
	private String headimg;
	private String content;
	private String create_datetime;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProject_topic_id() {
		return project_topic_id;
	}
	public void setProject_topic_id(Integer project_topic_id) {
		this.project_topic_id = project_topic_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreate_datetime() {
		return create_datetime;
	}
	public void setCreate_datetime(String create_datetime) {
		this.create_datetime = create_datetime;
	}
	@Override
	public String toString() {
		return "ProjectTopicFollow [id=" + id + ", project_topic_id=" + project_topic_id + ", user_id=" + user_id
				+ ", username=" + username + ", headimg=" + headimg + ", content=" + content + ", create_datetime="
				+ create_datetime + "]";
	}
	
	
}
