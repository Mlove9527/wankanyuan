package com.dzjin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dzjin.model.Project;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：AdminProjectDao 
 * 类描述： 后台项目Dao
 * 创建人：dzjin 
 * 创建时间：2018年6月26日 下午5:13:59 
 * 修改人：dzjin 
 * 修改时间：2018年6月26日 下午5:13:59 
 * 修改备注： 
 * @version 
 *
 */
public interface AdminProjectDao {
	
	@Select("select project.* , user.username as creatorName "
			+ "from project,user "
			+ "where project.creator=user.id and p_name like '%${searchWord}%'")
	public List<Project> selectProject(@Param("searchWord")String searchWord);
	
	@Select("select project.* , user.username as creatorName "
			+ "from project,user "
			+ "where project.creator=user.id "
			+ "and project.is_show=#{is_show} "
			+ "and p_name like '%${searchWord}%'")
	public List<Project> selectIsShowProject(@Param("searchWord")String searchWord , @Param("is_show")Integer is_show);
	
	@Update("update project set is_show=1 where id=#{id}")
	public int updateProjectIsShow1(@Param("id")Integer id);
	
	@Update("update project set is_show=0 where id=#{id}")
	public int updateProjectIsShow0(@Param("id")Integer id);

}
