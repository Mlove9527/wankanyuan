package com.xtkong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xtkong.model.FormatField;

public interface FormatFieldDao {
	@Insert("insert into format_field(ff_name,ft_id,is_meta,type,check_rule,enumerated,not_null,is_view,description,error_msg,create_datetime,create_uid) "
			+ "values(#{ff_name},#{ft_id},#{is_meta},#{type},#{check_rule},#{enumerated},#{not_null},#{is_view},#{description},#{error_msg},#{create_datetime},#{create_uid})")
	public int insertFormatField(FormatField formatField);

	@Update("update format_field "
			+ "set ff_name=#{ff_name},is_meta=#{is_meta},type=#{type},check_rule=#{check_rule},enumerated=#{enumerated},not_null=#{not_null},is_view=#{is_view},description=#{description},error_msg=#{error_msg},update_datetime=#{update_datetime},update_uid=#{update_uid} "
			+ "where ff_id=#{ff_id}")
	public int updateFormatField(FormatField formatField);

	/**
	 * 
	 * 选取格式字段列表
	 * 
	 * @param ft_id
	 *            格式类别
	 * @return 格式字段列表
	 */
	@Select("select * from view_format_field where ft_id=#{ft_id} order by ff_id ")
	public List<FormatField> getFormatFieldsForAdmin(@Param("ft_id") Integer ft_id);

	@Select("select * from format_field where ft_id=#{ft_id} and is_view=1 order by ff_id ")
	public List<FormatField> getFormatFieldsForUser(@Param("ft_id") Integer ft_id);

	@Select("select * from format_field where ft_id=#{ft_id} and is_view=1 and is_meta=#{is_meta} order by ff_id ")
	public List<FormatField> getFormatFieldsIs_meta(@Param("ft_id") Integer ft_id, @Param("is_meta") Integer is_meta);

	@Select("select * from format_field where ff_id=#{ff_id} ")
	public FormatField getFormatField(@Param("ff_id") Integer ff_id);

	@Select("select ft_id from format_field where ff_id=#{ff_id} ")
	public Integer getFormatField_ft_id(@Param("ff_id") Integer ff_id);

	@Select("select ff_id from format_field where ft_id=#{ft_id}  and ff_name=#{ff_name} ORDER BY ff_id ASC LIMIT 1")
	public Integer getFormatField_ff_id(@Param("ft_id") Integer ft_id, @Param("ff_name") String ff_name);

	@Delete("delete from format_field where ff_id=#{ff_id}")
	public int deleteFormatField(@Param("ff_id") Integer ff_id);

}
