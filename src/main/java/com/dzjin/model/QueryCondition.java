package com.dzjin.model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectQueryCondition 
 * 类描述： 项目、应用运行结果、应用以及成员筛选查询条件
 * 创建人：dzjin 
 * 创建时间：2018年7月12日 下午8:54:18 
 * 修改人：dzjin 
 * 修改时间：2018年7月12日 下午8:54:18 
 * 修改备注： 
 * @version 
 *
 */

public class QueryCondition {
	
	private String isFilter;//是否筛选字段，用于回显后保存筛选状态，然后在翻页的时候依旧执行筛选，而不是简单的查询翻页；
	private String columnName;//筛选的字段名，其中一个包含p_name , p_number , creator , create_datetime , is_open , key_words 六个字段
								//对应于前端打开的某字段筛选界面，
	private String order;//该筛选字段是否排序，desc 或者是 asc，前端中选中的排序方式
	private String filter;//该字段的过滤条件，前端中输入的过滤条件
	private String values;//该字段的筛选值，也就是前段中勾选的筛选值
	private List<String> strings;//该字段的筛选值list形式，便于操作，public void setStrings(String strValues) 用于赋值
	
	public String getIsFilter() {
		return isFilter;
	}
	public void setIsFilter(String isFilter) {
		this.isFilter = isFilter;
	}
	public List<String> getStrings() {
		return strings;
	}
	public void setStrings(List<String> strings) {
		this.strings = strings;
	}
	public void setStrings(String strValues) {
		this.strings = new ArrayList<String>();
		if(strValues != null){
			String[] strs = strValues.split(",");
			for(int i=0 ; i<strs.length ; i++){
				if(strs[i] != null && !strs[i].equals("")){
					this.strings.add(strs[i]);
				}
			}

		}
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getValues() {
		/*try {
			values= new String(values.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return values;
	}
	public void setValues(String values) {
		this.values = URLDecoder.decode(values);
	}
	@Override
	public String toString() {
		return "ProjectQueryCondition [columnName=" + columnName + ", order=" + order + ", filter=" + filter
				+ ", values=" + values + "]";
	}
}
