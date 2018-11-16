package com.xtkong.dao;

import com.xtkong.model.FormatFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * Created by tangye on 2018/11/14.
 */
public interface FormatFileDao {

    @Insert("insert into format_file(md5code,uid,filename,path) "
            + "values(#{md5code},#{uid},#{filename},#{path})")
    public int insertFormatFile(FormatFile formatFile);

    //@Insert("update format_file set md5code=#{md5code},filename=#{filename},path=#{path} where uid=#{uid} and csf_id=#{csf_id}")
    //public int updateFormatFileByUidAndUid(Integer uid,Integer csf_id,FormatFile newFormatFile);
    @Select("select * from format_file where md5code=#{md5code}")
    FormatFile selectFormatFileByMD5Code(String md5code);
}
