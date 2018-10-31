package com.liutianjun.pojo;

import java.io.Serializable;
import java.util.Date;

public class OrgMember implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 组内成员名
     */
    private String username;

    /**
     * 头像
     */
    private String headimg;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 组内角色
     */
    private String orgRole;

    /**
     * 组织机构ID
     */
    private Integer orgId;

    /**
     * 组ID
     */
    private Integer groupId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg == null ? null : headimg.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getOrgRole() {
        return orgRole;
    }

    public void setOrgRole(String orgRole) {
        this.orgRole = orgRole == null ? null : orgRole.trim();
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", username=").append(username);
        sb.append(", headimg=").append(headimg);
        sb.append(", email=").append(email);
        sb.append(", orgRole=").append(orgRole);
        sb.append(", orgId=").append(orgId);
        sb.append(", groupId=").append(groupId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}