package com.xtkong.model;

import java.util.List;

/**
 * 采集源
 *
 */
public class Source {
	private int cs_id;// 采集源ID
	private String cs_name;// 采集源名
	private boolean is_view;// 是否显示到前端 0不显示；1显示

	private List<SourceField> sourceFields;

	private List<FormatType> formatTypes;

	public List<FormatType> getFormatTypes() {
		return formatTypes;
	}

	public void setFormatTypes(List<FormatType> formatTypes) {
		this.formatTypes = formatTypes;
	}

	public List<SourceField> getSourceFields() {
		return sourceFields;
	}

	public void setSourceFields(List<SourceField> sourceFields) {
		this.sourceFields = sourceFields;
	}

	public int getCs_id() {
		return cs_id;
	}

	public void setCs_id(int cs_id) {
		this.cs_id = cs_id;
	}

	public String getCs_name() {
		return cs_name;
	}

	public void setCs_name(String cs_name) {
		this.cs_name = cs_name;
	}

	public boolean isIs_view() {
		return is_view;
	}

	public void setIs_view(boolean is_view) {
		this.is_view = is_view;
	}
}