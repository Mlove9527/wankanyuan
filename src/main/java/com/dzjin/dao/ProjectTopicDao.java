package com.dzjin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dzjin.model.ProjectTopic;
import com.dzjin.model.ProjectTopicFollow;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectTopicDao 
 * 类描述： 项目内主题dao
 * 创建人：dzjin 
 * 创建时间：2018年6月14日 下午1:41:58 
 * 修改人：dzjin 
 * 修改时间：2018年6月14日 下午1:41:58 
 * 修改备注： 
 * @version 
 *
 */
public interface ProjectTopicDao {
	
	@Insert("insert into project_topic(project_id,user_id,content,create_datetime,last_datetime) "
			+ "values(#{project_id},#{user_id},#{content},#{create_datetime},#{last_datetime})")
	public int insertProjectTopic(ProjectTopic projectTopic);
	
	@Select("select project_topic.* , user.username , user.headimg from project_topic,user "
			+ "where project_topic.user_id=user.id and project_topic.id=#{id} order by id desc")
	public ProjectTopic getProjectTopicById(@Param("id") Integer id);
	
	@Select("select project_topic.* , user.username ,user.headimg from project_topic,user "
			+ "where project_topic.user_id=user.id "
			+ "and project_topic.project_id=#{project_id} "
			+ "order by create_datetime ${timeRadio},follow_up_num ${followRadio},look_num ${lookRadio}")
	public List<ProjectTopic> selectProjectTopic(
			@Param("project_id")Integer project_id,
			@Param("timeRadio")String timeRadio,
			@Param("followRadio")String followRadio,
			@Param("lookRadio")String lookRadio);
	
	@Delete("delete from project_topic where id=#{project_topic_id}")
	public int deleteProjectTopic(@Param("project_topic_id")Integer project_topic_id);
	
	//更新浏览以及回复数量
	@Update("update project_topic set look_num=look_num+1 where id=#{id}")
	public int upLookNum(@Param("id")Integer id);
	
	@Update("update project_topic set follow_up_num=follow_up_num+1 where id=#{id}")
	public int upFollowNum(@Param("id")Integer id);
	
	@Update("update project_topic set follow_up_num=follow_up_num-1 where id=#{id}")
	public int downFollowNum(@Param("id")Integer id);
	
	//更新最近跟帖时间
	@Update("update project_topic set last_datetime=#{last_datetime} where id=#{id}")
	public int updateLastDatetime(ProjectTopic projectTopic);
	

	@Insert("insert into project_topic_follow(project_topic_id,user_id,content,create_datetime) "
			+ "values(#{project_topic_id},#{user_id},#{content},#{create_datetime})")
	public int insertProjectTopicFollow(ProjectTopicFollow projectTopicFollow);
	
	@Select("select project_topic_follow.* , user.username,user.headimg "
			+ "from project_topic_follow , user "
			+ "where project_topic_id=#{project_topic_id} "
			+ "and project_topic_follow.user_id = user.id order by project_topic_follow.id asc")
	public List<ProjectTopicFollow> selectProjectTopicFollow(@Param("project_topic_id")Integer project_topic_id);
	
	@Delete("delete from project_topic_follow where id=#{id}")
	public int deleteProjectTopicFollow(@Param("id") Integer id);
	
}
