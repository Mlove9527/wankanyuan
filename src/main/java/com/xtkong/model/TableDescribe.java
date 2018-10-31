package com.xtkong.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableDescribe {
	private String sourceName;
	private List<String>  columns;
	private List<TypeDescribe> analysis;
	
	@SuppressWarnings("unchecked")
	public TableDescribe(Map<String, Object> sourceDescribe) {
		if (sourceDescribe.containsKey("source")) {
			sourceName = sourceDescribe.get("source").toString();
		}
		if (sourceDescribe.containsKey("columns")) {
			columns = (List<String>) sourceDescribe.get("columns");
		}if (sourceDescribe.containsKey("analysis")) {
//			analysis=(List<TypeDescribe>) sourceDescribe.get("analysis");	
			analysis=new ArrayList<>();
			List<Map<String, Object>> typeDescribes=(List<Map<String, Object>>) sourceDescribe.get("analysis");
			for (Map<String, Object> typeDescribe : typeDescribes) {
				analysis.add(new TypeDescribe(typeDescribe));
			}
		}
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSource(String source) {
		this.sourceName = source;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public List<TypeDescribe> getAnalysis() {
		return analysis;
	}

	public void setAnalysis(List<TypeDescribe> analysis) {
		this.analysis = analysis;
	}
	
	
}
