package com.xtkong.model;

/**
 * 格式字段
 *
 */
public class FormatField {
	private int ff_id;// 格式字段ID
	private String ff_name; // 格式字段名
	private int ft_id; // 格式类型ID
	private boolean is_meta;//是否 meta
	private String type; // 类型
	private String check_rule; // 校验规则
	private boolean enumerated; // 是否可枚举 0 不可；1 可枚举
	private boolean not_null; // 是否必填 0 可空；1 必填
	private boolean is_view;// 是否显示到前端 0不显示；1显示
	private String description;// 字段描述信息
	private String error_msg; // 错误信息提示
	private String create_datetime; // 创建时间
	private int create_uid; // 创建人
	private String update_datetime; // 更新时间
	private int update_uid; // 更新人

	private String updater="";// 更新人
	private String creator="";// 创建人
	
	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdater() {
		return updater;
	}

	public String getCreator() {
		return creator;
	}

	public int getFf_id() {
		return ff_id;
	}

	public void setFf_id(int ff_id) {
		this.ff_id = ff_id;
	}

	public int getFt_id() {
		return ft_id;
	}

	public void setFt_id(int ft_id) {
		this.ft_id = ft_id;
	}

	public String getFf_name() {
		return ff_name;
	}

	public void setFf_name(String ff_name) {
		this.ff_name = ff_name;
	}

	public boolean isIs_meta() {
		return is_meta;
	}

	public void setIs_meta(boolean is_meta) {
		this.is_meta = is_meta;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCheck_rule() {
		return check_rule;
	}

	public void setCheck_rule(String check_rule) {
		this.check_rule = check_rule;
	}

	public boolean isEnumerated() {
		return enumerated;
	}

	public void setEnumerated(boolean enumerated) {
		this.enumerated = enumerated;
	}

	public boolean isNot_null() {
		return not_null;
	}

	public void setNot_null(boolean not_null) {
		this.not_null = not_null;
	}

	public boolean isIs_view() {
		return is_view;
	}

	public void setIs_view(boolean is_view) {
		this.is_view = is_view;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getCreate_datetime() {
		return create_datetime;
	}

	public void setCreate_datetime(String create_datetime) {
		this.create_datetime = create_datetime;
	}

	public int getCreate_uid() {
		return create_uid;
	}

	public void setCreate_uid(int create_uid) {
		this.create_uid = create_uid;
	}

	public String getUpdate_datetime() {
		return update_datetime;
	}

	public void setUpdate_datetime(String update_datetime) {
		this.update_datetime = update_datetime;
	}

	public int getUpdate_uid() {
		return update_uid;
	}

	public void setUpdate_uid(int update_uid) {
		this.update_uid = update_uid;
	}

}
