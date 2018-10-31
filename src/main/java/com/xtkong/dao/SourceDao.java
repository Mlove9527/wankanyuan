package com.xtkong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xtkong.model.Source;

public interface SourceDao {

	@Insert("insert into collection_source(cs_name , is_view) " + "values(#{cs_name},#{is_view})")
	public int insertSource(Source source);

	@Update("update collection_source " + "set cs_name=#{cs_name} , is_view=#{is_view} where cs_id=#{cs_id}")
	public int updateSource(Source source);

	/**
	 * 选取数据源
	 * 
	 * @return 数据源列表
	 */
	@Select("select * from collection_source order by cs_id")
	public List<Source> getSourcesForAdmin();

	@Select("select * from collection_source where is_view=1 order by cs_id")
	public List<Source> getSourcesForUser();

	@Select("select * from collection_source where is_view=1 order by cs_id asc	limit #{num}")
	public List<Source> getSourcesForUserLimit(@Param("num") Integer num);

	@Select("select cs_id from collection_source where cs_name=#{cs_name} order by	cs_id desc	limit 1")
	public Integer getSourceId(@Param("cs_name") String cs_name);

	@Select("select * from collection_source where cs_id=#{cs_id}")
	public Source getSourceByCs_id(@Param("cs_id") Integer cs_id);

	@Select("	select *	from collection_source	order by	cs_id asc	limit #{num}")
	public Source getSourceLimit(@Param("num") Integer num);

	@Delete("delete from collection_source where cs_id=#{cs_id}")
	public int deleteSource(@Param("cs_id") Integer cs_id);
}
