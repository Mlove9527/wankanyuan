package com.xtkong.model;

import java.util.List;
import java.util.Map;

public class FormatDataSQLInfo {
	private String sql;
	private List<String> qualifiers;
	private Map<String, String> conditionEqual;
	private Map<String, String> conditionLike;
	private List<FormatType> formatTypeFolders;
	private List<FormatField1> metaDataListTemp;
	private List<FormatField> data;
	private List<FormatField1> data1;
	private List<List<String>> dataDataLists;
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<String> getQualifiers() {
		return qualifiers;
	}
	public void setQualifiers(List<String> qualifiers) {
		this.qualifiers = qualifiers;
	}
	public Map<String, String> getConditionEqual() {
		return conditionEqual;
	}
	public void setConditionEqual(Map<String, String> conditionEqual) {
		this.conditionEqual = conditionEqual;
	}
	public Map<String, String> getConditionLike() {
		return conditionLike;
	}
	public void setConditionLike(Map<String, String> conditionLike) {
		this.conditionLike = conditionLike;
	}
	public List<FormatType> getFormatTypeFolders() {
		return formatTypeFolders;
	}
	public void setFormatTypeFolders(List<FormatType> formatTypeFolders) {
		this.formatTypeFolders = formatTypeFolders;
	}
	public List<FormatField1> getMetaDataListTemp() {
		return metaDataListTemp;
	}
	public void setMetaDataListTemp(List<FormatField1> metaDataListTemp) {
		this.metaDataListTemp = metaDataListTemp;
	}
	public List<FormatField> getData() {
		return data;
	}
	public void setData(List<FormatField> data) {
		this.data = data;
	}
	public List<FormatField1> getData1() {
		return data1;
	}
	public void setData1(List<FormatField1> data1) {
		this.data1 = data1;
	}
	public List<List<String>> getDataDataLists() {
		return dataDataLists;
	}
	public void setDataDataLists(List<List<String>> dataDataLists) {
		this.dataDataLists = dataDataLists;
	}
	
	
	
}
