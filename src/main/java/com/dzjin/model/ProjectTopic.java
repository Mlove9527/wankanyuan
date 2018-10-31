package com.dzjin.model;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectTopic 
 * 类描述： 项目主题表
 * 创建人：dzjin 
 * 创建时间：2018年6月14日 下午1:32:54 
 * 修改人：dzjin 
 * 修改时间：2018年6月14日 下午1:32:54 
 * 修改备注： 
 * @version 
 *
 */
public class ProjectTopic {

	
	private int id;
	//项目ID
	private int project_id;
	//用户ID
	private int user_id;
	//用户名
	private String username;
	//用户头像
	private String headimg;
	//帖子内容
	private String content;
	//创建时间
	private String create_datetime;
	//最后发言时间
	private String last_datetime;
	//跟帖数量
	private int follow_up_num;
	//查看次数
	private int look_num;
	
	
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getCreate_datetime() {
		return create_datetime;
	}
	public void setCreate_datetime(String create_datetime) {
		this.create_datetime = create_datetime;
	}
	public String getLast_datetime() {
		return last_datetime;
	}
	public void setLast_datetime(String last_datetime) {
		this.last_datetime = last_datetime;
	}
	public int getFollow_up_num() {
		return follow_up_num;
	}
	public void setFollow_up_num(int follow_up_num) {
		this.follow_up_num = follow_up_num;
	}
	public int getLook_num() {
		return look_num;
	}
	public void setLook_num(int look_num) {
		this.look_num = look_num;
	}
	@Override
	public String toString() {
		return "ProjectTopic [id=" + id + ", user_id=" + user_id + ", content=" + content + ", create_datetime="
				+ create_datetime + ", last_datetime=" + last_datetime + ", follow_up_num=" + follow_up_num
				+ ", look_num=" + look_num + "]";
	}
	

}
