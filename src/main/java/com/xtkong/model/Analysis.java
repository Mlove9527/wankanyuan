package com.xtkong.model;

import java.util.List;
import java.util.Map;

public class Analysis {
	private String name;// 配置的分析结果名称
	private String nodeName;// 结果中自命名的节点名称（如果没有则创建，有则导入到该节点下）
	private String fileurl;// 待导入的文件
	private List<String> bCol;// 该文件结果中与采集源基础信息关联所用的字段
	private List<String> toBCol; // bCol在配置文件中对应的字段
	private List<String> toBColValue;// bCol给固定采集源的值导入数据，该参数与bCol不会同时出现
	private List<String> srcMCol;// 定义待导入文件中的metainfo字段
	private List<String> distMCol;// 映射配置表中的metainfo字段
	private List<String> srcDCol;// 待导入文件中要导入的列的列名
	private List<String> distDCol;// srcCol这些列对应的配置表中的字段
	private List<String> defUnique;// 定义该批数据导入到设定采集源的设定结果下的设定节点中的唯一识别方式

	@SuppressWarnings("unchecked")
	public Analysis(Map<String, Object> analysisMap) {
		if (analysisMap.containsKey("name")) {
			name = analysisMap.get("name").toString();
		}
		if (analysisMap.containsKey("nodeName")) {
			nodeName = analysisMap.get("nodeName").toString();
		}
		if (analysisMap.containsKey("fileurl")) {
			fileurl = analysisMap.get("fileurl").toString();
		}
		if (analysisMap.containsKey("bCol")) {
			bCol = (List<String>) analysisMap.get("bCol");
		}
		if (analysisMap.containsKey("toBCol")) {
			toBCol = (List<String>) analysisMap.get("toBCol");
		}
		if (analysisMap.containsKey("toBColValue")) {
			toBColValue = (List<String>) analysisMap.get("toBColValue");
		}
		if (analysisMap.containsKey("srcMCol")) {
			srcMCol = (List<String>) analysisMap.get("srcMCol");
		}
		if (analysisMap.containsKey("distMCol")) {
			distMCol = (List<String>) analysisMap.get("distMCol");
		}
		if (analysisMap.containsKey("srcDCol")) {
			srcDCol = (List<String>) analysisMap.get("srcDCol");
		}
		if (analysisMap.containsKey("distDCol")) {
			distDCol = (List<String>) analysisMap.get("distDCol");
		}
		if (analysisMap.containsKey("defUnique")) {
			defUnique = (List<String>) analysisMap.get("defUnique");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	

	public List<String> getBCol() {
		return bCol;
	}

	public void setBCol(List<String> bCol) {
		this.bCol = bCol;
	}

	public List<String> getToBCol() {
		return toBCol;
	}

	public void setToBCol(List<String> toBCol) {
		this.toBCol = toBCol;
	}

	public List<String> getToBColValue() {
		return toBColValue;
	}

	public void setToBColValue(List<String> toBColValue) {
		this.toBColValue = toBColValue;
	}

	public List<String> getSrcMCol() {
		return srcMCol;
	}

	public void setSrcMCol(List<String> srcMCol) {
		this.srcMCol = srcMCol;
	}

	public List<String> getDistMCol() {
		return distMCol;
	}

	public void setDistMCol(List<String> distMCol) {
		this.distMCol = distMCol;
	}

	public List<String> getSrcDCol() {
		return srcDCol;
	}

	public void setSrcDCol(List<String> srcDCol) {
		this.srcDCol = srcDCol;
	}

	public List<String> getDistDCol() {
		return distDCol;
	}

	public void setDistDCol(List<String> distDCol) {
		this.distDCol = distDCol;
	}

	public List<String> getDefUnique() {
		return defUnique;
	}

	public void setDefUnique(List<String> defUnique) {
		this.defUnique = defUnique;
	}

}
