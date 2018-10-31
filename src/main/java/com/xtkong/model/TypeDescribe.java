package com.xtkong.model;

import java.util.List;
import java.util.Map;

public class TypeDescribe {

	private String typeName;
	private List<String> columns;

	@SuppressWarnings("unchecked")
	public TypeDescribe(Map<String, Object> typeDescribe) {
		if (typeDescribe.containsKey("type")) {
			typeName = typeDescribe.get("type").toString();
		}
		if (typeDescribe.containsKey("columns")) {
			columns = (List<String>) typeDescribe.get("columns");
		}
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	

}
