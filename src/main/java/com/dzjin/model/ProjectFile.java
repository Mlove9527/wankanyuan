package com.dzjin.model;

	/**
	* 表名：project_file
	* 作者：dzjin
	* 联系方式：dzjin5678@163.com
	* 创建时间：2018-05-08 19:24:46
	*/

public class ProjectFile {

	private int id;	//文件ID
	private int floder_id;	//父文件夹ID
	private String file_name;	//文件名称
	private String file_location;	//文件存储地址
	private String file_type;	//文件类型，具体是什么文件类型
	private String file_size;	//文件大小
	private String create_datetime;		//文件上传时间
	private int creator_id;		//文件创建者ID
	private String username;
	private int p_id;	//所在项目ID
	
	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFloder_id() {
		return floder_id;
	}
	public void setFloder_id(int floder_id) {
		this.floder_id = floder_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_location() {
		return file_location;
	}
	public void setFile_location(String file_location) {
		this.file_location = file_location;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public String getCreate_datetime() {
		return create_datetime;
	}
	public void setCreate_datetime(String create_datetime) {
		this.create_datetime = create_datetime;
	}
	public int getCreator_id() {
		return creator_id;
	}
	public void setCreator_id(int creator_id) {
		this.creator_id = creator_id;
	}
	@Override
	public String toString() {
		return "ProjectFile [id=" + id + ", floder_id=" + floder_id + ", file_name=" + file_name + ", file_location="
				+ file_location + ", file_type=" + file_type + ", file_size=" + file_size + ", create_datetime="
				+ create_datetime + ", creator_id=" + creator_id + "]";
	}
	
}

