package com.xtkong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xtkong.model.SourceField;

public interface SourceFieldDao {
	@Insert("insert into collection_source_field(csf_name,cs_id,type,check_rule,enumerated,not_null,description,error_msg,create_datetime,create_uid,update_datetime,update_uid,is_view,emvalue) "
			+ "values(#{csf_name},#{cs_id},#{type},#{check_rule},#{enumerated},#{not_null},#{description},#{error_msg},#{create_datetime},#{create_uid},#{update_datetime},#{update_uid},#{is_view},#{emvalue})")
	public int insertSourceField(SourceField sourceField);

	@Update("update collection_source_field " + "set csf_name=#{csf_name} , type=#{type} , check_rule=#{check_rule},"
			+ " enumerated=#{enumerated}, not_null=#{not_null}, description=#{description},"
			+ "error_msg=#{error_msg},update_datetime=#{update_datetime},update_uid=#{update_uid},"
			+ "is_view=#{is_view},emvalue=#{emvalue}  " + "where csf_id=#{csf_id}")
	public int updateSourceField(SourceField sourceField);

	/**
	 * * 选取采集源字段列表
	 * 
	 * @param cs_id
	 *            采集源
	 * @return 采集源字段列表
	 */
	@Select("select * from view_collection_source_field where cs_id=#{cs_id} and  is_view=1 order by csf_id ")
	public List<SourceField> getSourceFields(@Param("cs_id") int cs_id);

	@Select("select csf.* from collection_source cs inner join collection_source_field csf on cs.cs_id=csf.cs_id where cs.cs_name=#{cs_name} and  csf.is_view=1 order by csf.csf_id ")
	public List<SourceField> getSourceFieldsBySourceName(@Param("cs_name") String cs_name);

	@Select("select * from view_collection_source_field where cs_id=#{cs_id} order by csf_id ")
	public List<SourceField> getSourceFieldsForAdmin(@Param("cs_id") int cs_id);

	@Select("select csf_id from collection_source_field where cs_id=#{cs_id} and csf_name=#{csf_name} ORDER BY csf_id ASC LIMIT 1 ")
	public Integer getSourceFieldId(@Param("cs_id") int cs_id, @Param("csf_name") String csf_name);

	@Select("select * from view_collection_source_field where csf_id=#{csf_id}")
	public SourceField getSourceField(Integer csf_id);

	@Delete("delete from collection_source_field where csf_id=#{csf_id}")
	public int deleteSourceField(@Param("csf_id") Integer csf_id);
	
	@Select("SELECT v.description FROM collection_source_field v WHERE v.csf_id=#{csf_id} AND v.cs_id=#{cs_id}")
	public SourceField getSourceFieldMsg(@Param("cs_id")int cs_id,@Param("csf_id")int csf_id);

}
