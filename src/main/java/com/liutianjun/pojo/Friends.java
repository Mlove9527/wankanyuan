package com.liutianjun.pojo;

import java.io.Serializable;
import java.util.Date;

public class Friends implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 好友ID
     */
    private Integer friendId;

    /**
     * 好友组织id
     */
    private Integer friendOrgId;

    /**
     * 好友名
     */
    private String friendName;

    /**
     * 角色名
     */
    private String friendRolename;

    /**
     * 好友头像
     */
    private String friendHeadimg;

    /**
     * 好友邮箱
     */
    private String friendEmail;

    /**
     * 好友简介
     */
    private String friendProfile;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间 
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

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getFriendOrgId() {
        return friendOrgId;
    }

    public void setFriendOrgId(Integer friendOrgId) {
        this.friendOrgId = friendOrgId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName == null ? null : friendName.trim();
    }

    public String getFriendRolename() {
        return friendRolename;
    }

    public void setFriendRolename(String friendRolename) {
        this.friendRolename = friendRolename == null ? null : friendRolename.trim();
    }

    public String getFriendHeadimg() {
        return friendHeadimg;
    }

    public void setFriendHeadimg(String friendHeadimg) {
        this.friendHeadimg = friendHeadimg == null ? null : friendHeadimg.trim();
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail == null ? null : friendEmail.trim();
    }

    public String getFriendProfile() {
        return friendProfile;
    }

    public void setFriendProfile(String friendProfile) {
        this.friendProfile = friendProfile == null ? null : friendProfile.trim();
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
        sb.append(", friendId=").append(friendId);
        sb.append(", friendOrgId=").append(friendOrgId);
        sb.append(", friendName=").append(friendName);
        sb.append(", friendRolename=").append(friendRolename);
        sb.append(", friendHeadimg=").append(friendHeadimg);
        sb.append(", friendEmail=").append(friendEmail);
        sb.append(", friendProfile=").append(friendProfile);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}