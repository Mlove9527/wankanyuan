package com.liutianjun.pojo;

import java.io.Serializable;

public class ProjectAppRelation implements Serializable {
    /**
     * 项目-应用关系表ID
     */
    private Integer id;

    /**
     * 项目ID
     */
    private Integer projectId;

    /**
     * 应用ID
     */
    private Integer appId;

    /**
     * 绑定时间
     */
    private String bindDatetime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getBindDatetime() {
        return bindDatetime;
    }

    public void setBindDatetime(String bindDatetime) {
        this.bindDatetime = bindDatetime == null ? null : bindDatetime.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", appId=").append(appId);
        sb.append(", bindDatetime=").append(bindDatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}