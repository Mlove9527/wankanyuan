package com.xtkong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.xtkong.model.UserData;

public interface UserDataDao {

	@Insert("insert into user_data_relation(uid,dataid,cs_id) " + "values(#{uid},#{dataid},#{cs_id})")
	public int insert(@Param("uid") Integer uid, @Param("dataid") String dataid, @Param("cs_id") Integer cs_id);

	@Select("select * from user_data_relation where uid=#{uid}")
	public List<UserData> select(@Param("uid") Integer uid);

	@Select("select dataid from user_data_relation where uid=#{uid} and cs_id=#{cs_id}")
	public List<String> selects(@Param("uid") Integer uid, @Param("cs_id") Integer cs_id);

	@Delete("delete from user_data_relation where  uid=#{uid} and  dataid=#{dataid} and cs_id=#{cs_id}")
	public int delete(@Param("uid") Integer uid, @Param("dataid") String dataid, @Param("cs_id") Integer cs_id);

	@Delete("delete from user_data_relation where dataid=#{dataid} and cs_id=#{cs_id}")
	public int deleteid(@Param("dataid") String dataid, @Param("cs_id") Integer cs_id);
}
