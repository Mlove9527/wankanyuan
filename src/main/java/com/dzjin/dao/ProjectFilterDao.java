package com.dzjin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.dzjin.model.Project;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectFilterDao 
 * 类描述： 项目表头过滤
 * 创建人：dzjin 
 * 创建时间：2018年7月12日 下午11:39:29 
 * 修改人：dzjin 
 * 修改时间：2018年7月12日 下午11:39:29 
 * 修改备注： 
 * @version 
 *
 */
public interface ProjectFilterDao {
	
	
	//过滤我创建的项目
	
	@Select("select distinct p_name from project where creator=#{creator} and p_name like '%${searchWord}%'")
	public List<String> selectDistinctP_nameCreated(
			@Param("creator")Integer creator , @Param("searchWord")String searchWord);
	
	@Select("select distinct p_number from project where creator=#{creator} and p_number like '%${searchWord}%'")
	public List<String> selectDistinctP_numberCreated(
			@Param("creator")Integer creator , @Param("searchWord")String searchWord);
	
	@Select("select distinct key_words from project where creator=#{creator} and key_words like '%${searchWord}%'")
	public List<String> selectDistinctKeyWordsCreated(
			@Param("creator")Integer creator , @Param("searchWord")String searchWord);
	
	@Select("select distinct create_datetime from project where creator=#{creator} and create_datetime like '%${searchWord}%'")
	public List<String> selectDistinctCreateDatetimeCreated(
			@Param("creator")Integer creator , @Param("searchWord")String searchWord);
	
	@Select("select distinct user.username from user , project "
			+ "where project.creator=#{creator} and project.creator=user.id and user.username like '%${searchWord}%'")
	public List<String> selectDistinctCreatorCreated(
			@Param("creator")Integer creator , @Param("searchWord")String searchWord);
	
	@Select("select distinct project.p_name from user , project "
			+ "where project.creator=#{creator} and project.creator=user.id ${sql}")
	public List<String> selectDistinctP_nameCreated1(
			@Param("creator")Integer creator , @Param("sql")String sql);
	
	@Select("select distinct project.p_number from user , project "
			+ "where project.creator=#{creator} and project.creator=user.id ${sql}")
	public List<String> selectDistinctP_numberCreated1(
			@Param("creator")Integer creator , @Param("sql")String sql);
	
	@Select("select distinct project.key_words from user , project "
			+ "where project.creator=#{creator} and project.creator=user.id ${sql}")
	public List<String> selectDistinctKeyWordsCreated1(
			@Param("creator")Integer creator , @Param("sql")String sql);
	
	@Select("select distinct project.create_datetime from user , project "
			+ "where project.creator=#{creator} and project.creator=user.id ${sql}")
	public List<String> selectDistinctCreateDatetimeCreated1(
			@Param("creator")Integer creator , @Param("sql")String sql);
	
	@Select("select distinct user.username from user , project "
			+ "where project.creator=#{creator} and project.creator=user.id ${sql}")
	public List<String> selectDistinctCreatorCreated1(
			@Param("creator")Integer creator , @Param("sql")String sql);
	
	@Select("select distinct project.is_open from user , project "
			+ "where project.creator=#{creator} and project.creator=user.id ${sql}")
	public List<String> selectDistinctIsOpen1(
			@Param("creator")Integer creator , @Param("sql")String sql);
	
	//过滤我的项目
	
	@Select("select distinct project.p_name from project,project_user where project.id=project_user.project_id and project_user.user_id=#{user_id} and project.p_name like '%${searchWord}%'")
	public List<String> selectDistinctP_nameMine(
			@Param("user_id")Integer user_id , @Param("searchWord")String searchWord);
	
	@Select("select distinct project.p_number from project,project_user where project.id=project_user.project_id and project_user.user_id=#{user_id} and p_number like '%${searchWord}%'")
	public List<String> selectDistinctP_numberMine(
			@Param("user_id")Integer user_id , @Param("searchWord")String searchWord);
	
	@Select("select distinct project.key_words from project,project_user where project.id=project_user.project_id and project_user.user_id=#{user_id} and project.key_words like '%${searchWord}%'")
	public List<String> selectDistinctKeyWordsMine(
			@Param("user_id")Integer user_id , @Param("searchWord")String searchWord);
	
	@Select("select distinct project.create_datetime from project,project_user where project.id=project_user.project_id and project_user.user_id=#{user_id} and create_datetime like '%${searchWord}%'")
	public List<String> selectDistinctCreateDatetimeMine(
			@Param("user_id")Integer user_id , @Param("searchWord")String searchWord);
	
	@Select("select distinct user.username from user , project , project_user "
			+ "where  project.id=project_user.project_id and project_user.user_id=#{user_id} and user.id=project.creator and user.username like '%${searchWord}%'")
	public List<String> selectDistinctCreatorMine(
			@Param("user_id")Integer user_id , @Param("searchWord")String searchWord);
	
	
	
	//过滤我的项目
	
		@Select("select distinct project.p_name from user , project , project_user "
				+ "where  project.id=project_user.project_id and project_user.user_id=#{user_id} and user.id=project.creator ${sql}")
		public List<String> selectDistinctP_nameMine1(
				@Param("user_id")Integer user_id , @Param("sql")String sql);
		
		@Select("select distinct project.p_number from user , project , project_user "
				+ "where  project.id=project_user.project_id and project_user.user_id=#{user_id} and user.id=project.creator ${sql}")
		public List<String> selectDistinctP_numberMine1(
				@Param("user_id")Integer user_id , @Param("sql")String sql);
		
		@Select("select distinct project.key_words from user , project , project_user "
				+ "where  project.id=project_user.project_id and project_user.user_id=#{user_id} and user.id=project.creator ${sql}")
		public List<String> selectDistinctKeyWordsMine1(
				@Param("user_id")Integer user_id , @Param("sql")String sql);
		
		@Select("select distinct project.create_datetime from user , project , project_user "
				+ "where  project.id=project_user.project_id and project_user.user_id=#{user_id} and user.id=project.creator ${sql}")
		public List<String> selectDistinctCreateDatetimeMine1(
				@Param("user_id")Integer user_id , @Param("sql")String sql);
		
		@Select("select distinct user.username from user , project , project_user "
				+ "where  project.id=project_user.project_id and project_user.user_id=#{user_id} and user.id=project.creator ${sql}")
		public List<String> selectDistinctCreatorMine1(
				@Param("user_id")Integer user_id , @Param("sql")String sql);
		//公开状态
		@Select("select distinct project.is_open from user , project , project_user "
				+ "where  project.id=project_user.project_id and project_user.user_id=#{user_id} and user.id=project.creator ${sql}")
		public List<String> selectDistinctIsOpen(
				@Param("user_id")Integer user_id , @Param("sql")String sql);
		
		
	
	//过滤公开的项目
	
	@Select("select distinct p_name from project where is_open=1 and p_name like '%${searchWord}%'")
	public List<String> selectDistinctP_namePublic(@Param("searchWord")String searchWord);
	
	@Select("select distinct p_number from project where is_open=1 and p_number like '%${searchWord}%'")
	public List<String> selectDistinctP_numberPublic(@Param("searchWord")String searchWord);
	
	@Select("select distinct key_words from project where is_open=1 and key_words like '%${searchWord}%'")
	public List<String> selectDistinctKeyWordsPublic(@Param("searchWord")String searchWord);
	
	@Select("select distinct create_datetime from project where is_open=1 and create_datetime like '%${searchWord}%'")
	public List<String> selectDistinctCreateDatetimePublic( @Param("searchWord")String searchWord);
	
	@Select("select distinct user.username from user , project "
			+ "where is_open=1 and project.creator=user.id and user.username like '%${searchWord}%'")
	public List<String> selectDistinctCreatorPublic(@Param("searchWord")String searchWord);
	
	

	@Select("select distinct project.p_name from user , project "
			+ "where is_open=1 and project.creator=user.id ${sql}")
	public List<String> selectDistinctP_namePublic1(@Param("sql")String sql);
	
	@Select("select distinct project.p_number from user , project "
			+ "where is_open=1 and project.creator=user.id ${sql}")
	public List<String> selectDistinctP_numberPublic1(@Param("sql")String sql);
	
	@Select("select distinct project.key_words from user , project "
			+ "where is_open=1 and project.creator=user.id ${sql}")
	public List<String> selectDistinctKeyWordsPublic1(@Param("sql")String sql);
	
	@Select("select distinct project.create_datetime from user , project "
			+ "where is_open=1 and project.creator=user.id ${sql}")
	public List<String> selectDistinctCreateDatetimePublic1( @Param("sql")String sql);
	
	@Select("select distinct user.username from user , project "
			+ "where is_open=1 and project.creator=user.id ${sql}")
	public List<String> selectDistinctCreatorPublic1(@Param("sql")String sql);
	
	@Select("select distinct project.is_open from user , project "
			+ "where is_open=1 and project.creator=user.id ${sql}")
	public List<String> selectDistinctIsOpenPublic1( @Param("sql")String sql);
	
	
	@SelectProvider(type=ProjectSqlBuilder.class , method="buildSelectCreatedProjectByFilterCondition") 
	public List<Project> selectCreatedProjectByFilterCondition(Map<String, Object> map);
	
	@Select("${sql}")
	public List<Project> selectCreatedProjectByFilterCondition1(@Param("sql")String sql);
	
	@SelectProvider(type=ProjectSqlBuilder.class , method="buildSelectMineProjectByFilterCondition") 
	public List<Project> selectMineProjectByFilterCondition(Map<String, Object> map);
	
	@Select("${sql}")
	public List<Project> selectMineProjectByFilterCondition1(@Param("sql")String sql);

	
	@SelectProvider(type=ProjectSqlBuilder.class , method="buildSelectPublicProjectByFilterCondition") 
	public List<Project> selectPublicProjectByFilterCondition(Map<String, Object> map);
	
	@Select("${sql}")
	public List<Project> selectPublicProjectByFilterCondition1(@Param("sql")String sql);



}

