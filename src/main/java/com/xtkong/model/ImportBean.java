package com.xtkong.model;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ImportBean {
	private String sourceid;//待导入的采集源
	private String userid;//给哪个用户导入数据
	private Basic basic;//采集源基础信息
	private List<Analysis> analysisList;//配置的分析结果
	@SuppressWarnings("unchecked")
	public ImportBean(String jsonStr) {
		Map<String, Object> gsonMap = new Gson().fromJson(jsonStr, new TypeToken<Map<String, Object>>() {
		}.getType());

		if (gsonMap.containsKey("sourceid")) {
			sourceid = gsonMap.get("sourceid").toString();
		}
		if (gsonMap.containsKey("userid")) {
			userid = gsonMap.get("userid").toString();
		}
		if (gsonMap.containsKey("basic")) {
			basic = new Basic((Map<String, Object>) gsonMap.get("basic"));
		}if (gsonMap.containsKey("analysis")) {
			analysisList=(List<Analysis>) gsonMap.get("analysis");
			
			
		}

	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Basic getBasic() {
		return basic;
	}

	public void setBasic(Basic basic) {
		this.basic = basic;
	}

	public List<Analysis> getAnalysisList() {
		return analysisList;
	}

	public void setAnalysisList(List<Analysis> analysisList) {
		this.analysisList = analysisList;
	}

}