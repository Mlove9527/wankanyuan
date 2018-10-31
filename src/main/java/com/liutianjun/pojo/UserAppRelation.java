package com.liutianjun.pojo;

import java.io.Serializable;
import java.util.Date;

public class UserAppRelation implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private Integer userId;

    /**
     * 我的应用id
     */
    private Integer appId;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 应用类别
     */
    private String appType;

    /**
     * 同步/异步 0-同步 1-异步
     */
    private Integer isAsync;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 应用概述
     */
    private String appOverview;
    
    /*public UserAppRelation(String appName,String creator) {
    	
    }*/
    

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType == null ? null : appType.trim();
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
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAppOverview() {
        return appOverview;
    }

    public void setAppOverview(String appOverview) {
        this.appOverview = appOverview == null ? null : appOverview.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", appId=").append(appId);
        sb.append(", appName=").append(appName);
        sb.append(", creator=").append(creator);
        sb.append(", appType=").append(appType);
        sb.append(", isAsync=").append(isAsync);
        sb.append(", keywords=").append(keywords);
        sb.append(", createTime=").append(createTime);
        sb.append(", appOverview=").append(appOverview);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
    
    
}