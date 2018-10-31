package com.dzjin.model;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectApplication 
 * 类描述： 项目内使用的Application实体类，只供项目内应用筛选使用
 * 创建人：dzjin 
 * 创建时间：2018年7月15日 下午5:01:54 
 * 修改人：dzjin 
 * 修改时间：2018年7月15日 下午5:01:54 
 * 修改备注： 
 * @version 
 *
 */
public class ProjectApplication {
	
	private Integer id;
	private String appName;
	private String creator;
	private String createTime;
	private Integer isDisplay;
	private Integer isAsync;
	private String keywords;
	private String appIntro;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	public Integer getIsAsync() {
		return isAsync;
	}
	public void setIsAsync(Integer isAsync) {
		this.isAsync = isAsync;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getAppIntro() {
		return appIntro;
	}
	public void setAppIntro(String appIntro) {
		this.appIntro = appIntro;
	}
	
	@Override
	public String toString() {
		return "ProjectApplication [id=" + id + ", appName=" + appName + ", creator=" + creator + ", createTime="
				+ createTime + ", isDisplay=" + isDisplay + ", isAsync=" + isAsync + ", keywords=" + keywords
				+ ", appIntro=" + appIntro + "]";
	}

}
