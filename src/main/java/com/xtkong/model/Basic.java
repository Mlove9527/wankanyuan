package com.xtkong.model;

import java.util.List;
import java.util.Map;

public class Basic {
	private String fileurl;//采集源基础信息待导入的文件
	private List<String> srcCol;//待导入文件中要导入的列的列名
	private List<String> distCol;//srcCol这些列对应的配置表中的字段
	private List<String> defUnique;//定义该批数据导入到设定采集源的基础信息的唯一识别方式

	@SuppressWarnings("unchecked")
	public Basic(Map<String, Object> basicMap) {
		if (basicMap.containsKey("fileurl")) {
			fileurl = basicMap.get("fileurl").toString();
		}
		if (basicMap.containsKey("srcCol")) {
			srcCol = (List<String>) basicMap.get("srcCol");
		}
		if (basicMap.containsKey("distCol")) {
			distCol = (List<String>) basicMap.get("distCol");
		}
		if (basicMap.containsKey("defUnique")) {
			defUnique = (List<String>) basicMap.get("defUnique");
		}
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public List<String> getSrcCol() {
		return srcCol;
	}

	public void setSrcCol(List<String> srcCol) {
		this.srcCol = srcCol;
	}

	public List<String> getDistCol() {
		return distCol;
	}

	public void setDistCol(List<String> distCol) {
		this.distCol = distCol;
	}

	public List<String> getDefUnique() {
		return defUnique;
	}

	public void setDefUnique(List<String> defUnique) {
		this.defUnique = defUnique;
	}

}
