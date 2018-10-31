package com.dzjin.model;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：AppTaskProgressResult 
 * 类描述： 
 * 创建人：dzjin 
 * 创建时间：2018年7月5日 上午9:30:57 
 * 修改人：dzjin 
 * 修改时间：2018年7月5日 上午9:30:57 
 * 修改备注： 
 * @version 
 *
 */
public class AppTaskProgressResult {
	
	
	private String pinfoDetail;
	private String pinfo;
	private String pcode;
	private String name;
	private String progressNum;
	private String progress;
	private String id;
	
	public String getPinfoDetail() {
		return pinfoDetail;
	}
	public void setPinfoDetail(String pinfoDetail) {
		this.pinfoDetail = pinfoDetail;
	}
	public String getPinfo() {
		return pinfo;
	}
	public void setPinfo(String pinfo) {
		this.pinfo = pinfo;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProgressNum() {
		return progressNum;
	}
	public void setProgressNum(String progressNum) {
		this.progressNum = progressNum;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "AppTaskProgressResult [pinfoDetail=" + pinfoDetail + ", pinfo=" + pinfo + ", pcode=" + pcode + ", name="
				+ name + ", progressNum=" + progressNum + ", progress=" + progress + ", id=" + id + "]";
	}
	
	
	

}
