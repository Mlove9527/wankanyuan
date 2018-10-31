package com.liutianjun.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Organization implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 组名
     */
    private String organizationName;

    /**
     * 父ID
     */
    private Integer parentId;

    /**
     * id结构
     */
    private String parentIds;

    /**
     * 根id
     */
    private Integer rootId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 单位简介
     */
    private String unitInfo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态 0:审核中 1:有效
     */
    private Integer status;
    
    private List<Organization> groupList;
    

    private static final long serialVersionUID = 1L;

    public List<Organization> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Organization> groupList) {
		this.groupList = groupList;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName == null ? null : organizationName.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getUnitInfo() {
        return unitInfo;
    }

    public void setUnitInfo(String unitInfo) {
        this.unitInfo = unitInfo == null ? null : unitInfo.trim();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", organizationName=").append(organizationName);
        sb.append(", parentId=").append(parentId);
        sb.append(", parentIds=").append(parentIds);
        sb.append(", rootId=").append(rootId);
        sb.append(", realName=").append(realName);
        sb.append(", creator=").append(creator);
        sb.append(", phone=").append(phone);
        sb.append(", unitInfo=").append(unitInfo);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append(", groupList=").append(groupList);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}