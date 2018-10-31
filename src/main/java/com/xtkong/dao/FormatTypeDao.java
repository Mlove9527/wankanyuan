package com.xtkong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xtkong.model.FormatType;

public interface FormatTypeDao {

	@Insert("insert into format_type(ft_name,cs_id,floder,create_datetime,create_uid,is_view,update_uid,update_datetime) values(#{ft_name},#{cs_id},#{floder},#{create_datetime},#{create_uid},#{is_view},#{update_uid},#{update_datetime})")
	public int insertFormatType(FormatType formatType);

	@Update("update format_type "
			+ "set ft_name=#{ft_name} , floder=#{floder} ,update_datetime=#{update_datetime},update_uid=#{update_uid} , is_view=#{is_view} "
			+ "where ft_id=#{ft_id}")
	public int updateFormatType(FormatType FormatType);

	/**
	 * 选取格式类型列表
	 * 
	 * @param cs_id
	 *            采集源
	 * @return 格式类型列表
	 */
	@Select("select * from view_format_type where cs_id=#{cs_id} and is_view=1 order by ft_id ")
	public List<FormatType> getFormatTypes(@Param("cs_id") Integer cs_id);

	@Select("select * from view_format_type where cs_id=#{cs_id} order by ft_id ")
	public List<FormatType> getFormatTypesForAdmin(@Param("cs_id") Integer cs_id);

	@Select("select * from view_format_type where ft_id=#{ft_id}  ")
	public FormatType getFormatType(@Param("ft_id") Integer ft_id);

	@Select("select cs_id from format_type where ft_id=#{ft_id}  ")
	public Integer getFormatType_cs_id(@Param("ft_id") Integer ft_id);

	@Select("select ft_id from format_type where cs_id=#{cs_id} and ft_name=#{ft_name} ORDER BY ft_id ASC LIMIT 1")
	public Integer getFormatTypeId(@Param("cs_id") Integer cs_id, @Param("ft_name") String ft_name);

	@Delete("delete from format_type where ft_id=#{ft_id}")
	public int deleteFormatType(@Param("ft_id") Integer ft_id);
}
