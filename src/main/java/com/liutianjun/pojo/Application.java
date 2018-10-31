package com.liutianjun.pojo;

import java.io.Serializable;
import java.util.Date;

public class Application implements Serializable {
    /**
     * 应用编号
     */
    private Integer id;

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
     * 关键字
     */
    private String keywords;

    /**
     * 版本
     */
    private String versions;

    /**
     * 是否存储系统 0-否 1-是
     */
    private Integer isSaveSystem;

    /**
     * 同步/异步 0-同步 1-异步
     */
    private Integer isAsync;

    /**
     * 是否显示 0-否 1-是
     */
    private Integer isDisplay;

    /**
     * 计算节点名称
     */
    private String computeNodeName;

    /**
     * 参数地址
     */
    private String paraAddress;

    /**
     * 结果地址
     */
    private String resultAddress;

    /**
     * 应用简介
     */
    private String appIntro;

    /**
     * 文件结果
     */
    private String fileResult;

    /**
     * 文件结果地址
     */
    private String fileResultAddress;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 应用详细描述
     */
    private String description;

    private static final long serialVersionUID = 1L;

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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions == null ? null : versions.trim();
    }

    public Integer getIsSaveSystem() {
        return isSaveSystem;
    }

    public void setIsSaveSystem(Integer isSaveSystem) {
        this.isSaveSystem = isSaveSystem;
    }

    public Integer getIsAsync() {
        return isAsync;
    }

    public void setIsAsync(Integer isAsync) {
        this.isAsync = isAsync;
    }

    public Integer getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Integer isDisplay) {
        this.isDisplay = isDisplay;
    }

    public String getComputeNodeName() {
        return computeNodeName;
    }

    public void setComputeNodeName(String computeNodeName) {
        this.computeNodeName = computeNodeName == null ? null : computeNodeName.trim();
    }

    public String getParaAddress() {
        return paraAddress;
    }

    public void setParaAddress(String paraAddress) {
        this.paraAddress = paraAddress == null ? null : paraAddress.trim();
    }

    public String getResultAddress() {
        return resultAddress;
    }

    public void setResultAddress(String resultAddress) {
        this.resultAddress = resultAddress == null ? null : resultAddress.trim();
    }

    public String getAppIntro() {
        return appIntro;
    }

    public void setAppIntro(String appIntro) {
        this.appIntro = appIntro == null ? null : appIntro.trim();
    }

    public String getFileResult() {
        return fileResult;
    }

    public void setFileResult(String fileResult) {
        this.fileResult = fileResult == null ? null : fileResult.trim();
    }

    public String getFileResultAddress() {
        return fileResultAddress;
    }

    public void setFileResultAddress(String fileResultAddress) {
        this.fileResultAddress = fileResultAddress == null ? null : fileResultAddress.trim();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", appName=").append(appName);
        sb.append(", creator=").append(creator);
        sb.append(", appType=").append(appType);
        sb.append(", keywords=").append(keywords);
        sb.append(", versions=").append(versions);
        sb.append(", isSaveSystem=").append(isSaveSystem);
        sb.append(", isAsync=").append(isAsync);
        sb.append(", isDisplay=").append(isDisplay);
        sb.append(", computeNodeName=").append(computeNodeName);
        sb.append(", paraAddress=").append(paraAddress);
        sb.append(", resultAddress=").append(resultAddress);
        sb.append(", appIntro=").append(appIntro);
        sb.append(", fileResult=").append(fileResult);
        sb.append(", fileResultAddress=").append(fileResultAddress);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}