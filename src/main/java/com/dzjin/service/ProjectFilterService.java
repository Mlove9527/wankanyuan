package com.dzjin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzjin.dao.ProjectFilterDao;
import com.dzjin.model.Project;
import com.dzjin.model.QueryCondition;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * 项目名称：wankangyuan 
 * 类名称：ProjectFilterService 
 * 类描述： 项目表头筛选处理类
 * 创建人：dzjin 
 * 创建时间：2018年7月12日 下午11:43:57 
 * 修改人：dzjin 
 * 修改时间：2018年7月12日 下午11:43:57 
 * 修改备注： 
 * @version 
 *
 */
@Service
public class ProjectFilterService {
	
	@Autowired
	ProjectFilterDao projectFilterDao;
	
	/**
	 * 根据过滤条件筛选我创建的项目的某个字段的值
	 * @param columnName 字段名
	 * @param creator 创建人
	 * @param searchWord 过滤条件
	 * @return
	 */
	public List<String> selectDistinctColumnValueCreated(String columnName , Integer creator , String searchWord){
		switch(columnName){
			case "p_name":
				return projectFilterDao.selectDistinctP_nameCreated(creator, searchWord);
			case "p_number":
				return projectFilterDao.selectDistinctP_numberCreated(creator, searchWord);
			case "creator":
				return projectFilterDao.selectDistinctCreatorCreated(creator, searchWord);
			case "create_datetime":
				return projectFilterDao.selectDistinctCreateDatetimeCreated(creator, searchWord);
			case "is_open":
				List<String> strings = new ArrayList<String>();
				strings.add("已公开");
				strings.add("未公开");
				return strings;
			case "key_words":
				return projectFilterDao.selectDistinctKeyWordsCreated(creator, searchWord);
			default:
				return null;
		}
	}
	
	/**
	 * 根据过滤条件筛选我创建的项目的某个字段的值
	 * @param columnName 字段名
	 * @param creator 创建人
	 * @param searchWord 过滤条件
	 * @return
	 */
	public List<String> selectDistinctColumnValueCreated1(String columnName , Integer creator , String searchWord, String pNameGl, String pNumberGl, String pCreatorGl,
			String createDatetimeGl, String keyWordsGl,
			String pName, String pNumber, String pCreator,String createDatetime, String keyWords, String isOpen,int pageIndex,int pageSize){
		String sql = "";
		if(pNameGl != null && !pNameGl.equals("")){
			sql += " and project.p_name like '%"+pNameGl+"%' ";
		}
		if(pNumberGl != null && !pNumberGl.equals("")){
			sql += " and project.p_number like '%"+pNumberGl+"%' ";
		}
		if(pCreatorGl != null && !pCreatorGl.equals("")){
			sql += " and user.username like '%"+pCreatorGl+"%' ";
		}
		if(createDatetimeGl != null && !createDatetimeGl.equals("")){
			sql += " and project.create_datetime like '%"+createDatetimeGl+"%' ";
		}
		if(keyWordsGl != null && !keyWordsGl.equals("")){
			sql += " and project.key_words like '%"+keyWordsGl+"%' ";
		}
		
		if(pName != null && !pName.equals("")&&!"p_name".equals(columnName)){
			if(pName.indexOf(",")>-1){
				sql += "and (";
				String[] pname = pName.split(",");
				for(int i=0;i<pname.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " p_name"+"='"+String.valueOf(pname[i])+"'";
						if("null".equals(pname[i])) {
							sql += " or p_name is null or p_name='' or p_name='null' ";
						}
						if(i != pname.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(pName)) {
					sql += " and (p_name is null or p_name='' or p_name='null') ";
				}else {
					sql += " and p_name in('"+pName+"')";
				}
			}
			
		}
		
		if(pNumber != null && !pNumber.equals("")&&!"p_number".equals(columnName)){
			if(pNumber.indexOf(",")>-1){
				sql += "and (";
				String[] pnumber = pNumber.split(",");
				for(int i=0;i<pnumber.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "p_number"+"='"+String.valueOf(pnumber[i])+"'";
						if("null".equals(pnumber[i])) {
							sql += " or p_number is null or p_number='' or p_number='null' ";
						}
						if(i != pnumber.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(pNumber)) {
					sql += " and ( p_number is null or p_number=''  or p_number='null') ";
				}else {
					sql += " and p_number in('"+pNumber+"')";
				}
			}
			
		}
		
		if(pCreator != null && !pCreator.equals("")&&!"username".equals(columnName)){
			if(pCreator.indexOf(",")>-1){
				sql += "and (";
				String[] create = pCreator.split(",");
				for(int i=0;i<create.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "username"+"='"+String.valueOf(create[i])+"'";
						if("null".equals(create[i])) {
							sql += " or username is null or username=''  or username='null' ";
						}
						if(i != create.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(pCreator)) {
					sql += " and username is null or username='' or username='null' ";
				}else {
					sql += " and username in('"+pCreator+"')";
				}
			}
			
		}
		
		if(createDatetime != null && !createDatetime.equals("")&&!"create_datetime".equals(columnName)){
			if(createDatetime.indexOf(",")>-1){
				sql += "and (";
				String[] createdatetime = createDatetime.split(",");
				for(int i=0;i<createdatetime.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "create_datetime"+"='"+String.valueOf(createdatetime[i])+"'";
						if("null".equals(createdatetime[i])) {
							sql += " or create_datetime is null or create_datetime='' ";
						}
						if(i != createdatetime.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(createDatetime)) {
					sql += " and (create_datetime is null or create_datetime='') ";
				}else {
					sql += " and create_datetime in('"+createDatetime+"')";
				}
			}
			
		}
		
		if(keyWords != null && !keyWords.equals("")&&!"key_words".equals(columnName)){
			if(keyWords.indexOf(",")>-1){
				sql += "and (";
				String[] keywords = keyWords.split(",");
				for(int i=0;i<keywords.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "key_words"+"='"+String.valueOf(keywords[i])+"'";
						if("null".equals(keywords[i])) {
							sql += " or key_words is null or key_words='' ";
						}
						if(i != keywords.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(keyWords)) {
					sql += " and (key_words is null or key_words='' or key_words='null'  ) ";
				}else {
					sql += " and key_words in('"+keyWords+"')";
				}
			}
			
		}
		
		if(isOpen != null && !isOpen.equals("")&&!"is_open".equals(columnName)){
			if(isOpen.indexOf(",")>-1){
				sql += "and (";
				String[] isopen = isOpen.split(",");
				for(int i=0;i<isopen.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
					if(String.valueOf(isopen[i]).equals("已公开")){
						sql += "is_open=1";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}
					if(String.valueOf(isopen[i]).equals("未公开")){
						sql += "is_open=1";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}
					if(String.valueOf(isopen[i]).equals("null")){
						sql += "is_open is null or is_open='' ";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}
				}
				sql += ")";
			}else{
				if(isOpen.equals("已公开")){
					sql += " and is_open = 1";
				}else if(isOpen.equals("未公开")){
					sql += " and is_open = 0";
				}else if(isOpen.equals("null")) {
					sql += " and (is_open is null or is_open='' )";
				}
				
			}
			
		}
		sql+=" limit "+(pageIndex-1)*pageSize+","+pageSize;
		switch(columnName){
			case "p_name":
				return projectFilterDao.selectDistinctP_nameCreated1(creator, sql);
			case "p_number":
				return projectFilterDao.selectDistinctP_numberCreated1(creator, sql);
			case "creator":
				return projectFilterDao.selectDistinctCreatorCreated1(creator, sql);
			case "create_datetime":
				return projectFilterDao.selectDistinctCreateDatetimeCreated1(creator, sql);
			case "is_open":
				
				List<String> strings = new ArrayList<String>();
				List<String> queryStrings =projectFilterDao.selectDistinctIsOpen1(creator,sql);
				if(queryStrings.contains("1")) {
					strings.add("已公开");
				}
				if(queryStrings.contains("0")) {
					strings.add("未公开");
				}
				if(queryStrings.contains("")||queryStrings.contains(null)) {
					strings.add("");
				}
				return strings;
				
				/*List<String> strings = new ArrayList<String>();
				strings.add("已公开");
				strings.add("未公开");
				return strings;*/
			case "key_words":
				return projectFilterDao.selectDistinctKeyWordsCreated1(creator, sql);
			default:
				return null;
		}
	}
	
	/**
	 * 根据过滤条件筛选我的项目的某个字段的值
	 * @param columnName 字段名
	 * @param user_id 用户名
	 * @param searchWord 过滤条件
	 * @return
	 */
	public List<String> selectDistinctColumnValueMine(String columnName , Integer user_id , String searchWord){
		switch(columnName){
			case "p_name":
				return projectFilterDao.selectDistinctP_nameMine(user_id, searchWord);
			case "p_number":
				return projectFilterDao.selectDistinctP_numberMine(user_id, searchWord);
			case "creator":
				return projectFilterDao.selectDistinctCreatorMine(user_id, searchWord);
			case "create_datetime":
				return projectFilterDao.selectDistinctCreateDatetimeMine(user_id, searchWord);
			case "is_open":
				List<String> strings = new ArrayList<String>();
				strings.add("已公开");
				strings.add("未公开");
				return strings;
			case "key_words":
				return projectFilterDao.selectDistinctKeyWordsMine(user_id, searchWord);
			default:
				return null;
		}
	}
	
	/**
	 * 根据过滤条件筛选我的项目的某个字段的值
	 * @param columnName 字段名
	 * @param user_id 用户名
	 * @param searchWord 过滤条件
	 * @return
	 */
	public List<String> selectDistinctColumnValueMine1(String columnName , Integer user_id , String searchWord, 
			String pNameGl, String pNumberGl, String pCreatorGl, String createDatetimeGl, String keyWordsGl,
			String pName, String pNumber, String pCreator,String createDatetime, String keyWords, String isOpen,int  pageIndex,int pageSize){
		String sql = "";
		if(pNameGl != null && !pNameGl.equals("")){
			sql += " and project.p_name like '%"+pNameGl+"%' ";
		}
		if(pNumberGl != null && !pNumberGl.equals("")){
			sql += " and project.p_number like '%"+pNumberGl+"%' ";
		}
		if(pCreatorGl != null && !pCreatorGl.equals("")){
			sql += " and user.username like '%"+pCreatorGl+"%' ";
		}
		if(createDatetimeGl != null && !createDatetimeGl.equals("")){
			sql += " and project.create_datetime like '%"+createDatetimeGl+"%' ";
		}
		if(keyWordsGl != null && !keyWordsGl.equals("")){
			sql += " and project.key_words like '%"+keyWordsGl+"%' ";
		}
		
		if(pName != null && !pName.equals("")&&!"p_name".equals(columnName)){
			if(pName.indexOf(",")>-1){
				sql += "and (";
				String[] pname = pName.split(",");
				sql += "p_name in (";
				for(int i=0;i<pname.length;i++){
					if("null".equals(pname[i])) {
						sql += " '',null, ";
					}
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " '"+String.valueOf(pname[i])+"' ";
						if(i != pname.length-1){
							sql += " , ";	
						}
					
				}
				sql += ") )";
			}else{
				sql += " and p_name in('"+pName+"'";
				if("null".equals(pName)) {
					sql += " ,'',null ";
				}
				sql += " )";
			}
			
		}
		
		if(pNumber != null && !pNumber.equals("")/*&&!"p_number".equals(columnName)*/){
			if(pNumber.indexOf(",")>-1){
				sql += "and (";
				String[] pnumber = pNumber.split(",");
				sql += "p_number in (";
				for(int i=0;i<pnumber.length;i++){
					if("null".equals(pnumber[i])) {
						sql += " '',null, ";
					}
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " '"+String.valueOf(pnumber[i])+"' ";
						if(i != pnumber.length-1){
							sql += " , ";	
						}
					
				}
				sql += ") )";
			}else{
				sql += " and p_number in('"+pNumber+"'";
				if("null".equals(pNumber)) {
					sql += " ,'',null ";
				}
				sql += " )";
			}
			
		}
		
		if(pCreator != null && !pCreator.equals("")&&!"creator".equals(columnName)){
			if(pCreator.indexOf(",")>-1){
				sql += "and (";
				String[] create = pCreator.split(",");
				sql += "username in (";
				for(int i=0;i<create.length;i++){
					if("null".equals(create[i])) {
						sql += " '',null, ";
					}
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " '"+String.valueOf(create[i])+"' ";
						if(i != create.length-1){
							sql += " , ";	
						}
				}
				sql += ") )";
			}else{
				sql += " and username in('"+pCreator+"'";
				if("null".equals(pCreator)) {
					sql += " ,'',null ";
				}
				sql += ")";
			}
			
		}
		
		if(createDatetime != null && !createDatetime.equals("")&&!"create_datetime".equals(columnName)){
			if(createDatetime.indexOf(",")>-1){
				sql += "and (";
				String[] createdatetime = createDatetime.split(",");
				sql += "create_datetime in (";
				for(int i=0;i<createdatetime.length;i++){
					if("null".equals(createdatetime[i])) {
						sql += " '',null, ";
					}
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " '"+String.valueOf(createdatetime[i])+"'";
						if(i != createdatetime.length-1){
							sql += " , ";	
						}
					
				}
				sql += "))";
			}else{
				sql += " and create_datetime in('"+createDatetime+"'";
				if("null".equals(createDatetime)) {
					sql += " ,'',null ";
				}
				sql += " )";
			}
			
		}
		
		if(keyWords != null && !keyWords.equals("")&&!"key_words".equals(columnName)){
			if(keyWords.indexOf(",")>-1){
				sql += "and (";
				String[] keywords = keyWords.split(",");
				sql += "key_words in (";
				for(int i=0;i<keywords.length;i++){
					if("null".equals(keywords[i])) {
						sql += " '',null, ";
					}
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " '"+String.valueOf(keywords[i])+"'";
						if(i != keywords.length-1){
							sql += " , ";	
						}
					
				}
				sql += "))";
			}else{
				sql += " and key_words in('"+keyWords+"'";
				if("null".equals(keyWords)) {
					sql += " ,'',null ";
				}
				sql += " )";
			}
			
		}
		
		if(isOpen != null && !isOpen.equals("")&&!"is_open".equals(columnName)){
			if(isOpen.indexOf(",")>-1){
				sql += "and (";
				String[] isopen = isOpen.split(",");
				for(int i=0;i<isopen.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
					if(String.valueOf(isopen[i]).equals("已公开")){
						sql += "is_open=1";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}else if(String.valueOf(isopen[i]).equals("未公开")){
						sql += "is_open=0";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}else if(String.valueOf(isopen[i]).equals("null")){
						sql += "is_open is null";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}
				}
				sql += ")";
			}else{
				if(isOpen.equals("已公开")){
					sql += " and is_open =1";
				}else if(isOpen.equals("未公开")){
					sql += " and is_open =0";
				}else if(isOpen.equals("null")){
					sql += " and (is_open is null or is_open='')";
				}
			}
			
		}
		sql+=" limit "+(pageIndex-1)*pageSize+","+pageSize;
		switch(columnName){
			case "p_name":
				return projectFilterDao.selectDistinctP_nameMine1(user_id, sql);
			case "p_number":
				return projectFilterDao.selectDistinctP_numberMine1(user_id, sql);
			case "creator":
				return projectFilterDao.selectDistinctCreatorMine1(user_id, sql);
			case "create_datetime":
				return projectFilterDao.selectDistinctCreateDatetimeMine1(user_id, sql);
			case "is_open":
				List<String> returnStrings=new ArrayList<>();
				List<String> strings = projectFilterDao.selectDistinctIsOpen(user_id, sql);
				if(strings.contains("0")){
					returnStrings.add("未公开");
				}
				if(strings.contains("1")){
					returnStrings.add("已公开");
				}
				if(strings.contains("")){
					returnStrings.add("");
				}
				
				return returnStrings;
			case "key_words":
				return projectFilterDao.selectDistinctKeyWordsMine1(user_id, sql);
			default:
				return null;
		}
	}
	
	/**
	 * 根据过滤条件筛选公开项目的某个字段值
	 * @param columnName 字段名
	 * @param searchWord 过滤条件
	 * @return
	 */
	public List<String> selectDistinctColumnValuePublic(String columnName , String searchWord){
		switch(columnName){
			case "p_name":
				return projectFilterDao.selectDistinctP_namePublic(searchWord);
			case "p_number":
				return projectFilterDao.selectDistinctP_numberPublic(searchWord);
			case "creator":
				return projectFilterDao.selectDistinctCreatorPublic(searchWord);
			case "create_datetime":
				return projectFilterDao.selectDistinctCreateDatetimePublic(searchWord);
			case "is_open":
				List<String> strings = new ArrayList<String>();
				strings.add("已公开");
				strings.add("未公开");
				return strings;
			case "key_words":
				return projectFilterDao.selectDistinctKeyWordsPublic(searchWord);
			default:
				return null;
		}
	}
	
	/**
	 * 根据过滤条件筛选公开项目的某个字段值
	 * @param columnName 字段名
	 * @param searchWord 过滤条件
	 * @return
	 */
	public List<String> selectDistinctColumnValuePublic1(String columnName , String searchWord, 
			String pNameGl, String pNumberGl, String pCreatorGl, String createDatetimeGl, String keyWordsGl,
			String pName, String pNumber, String pCreator,String createDatetime, String keyWords, String isOpen,int pageIndex,int pageSize){
		String sql = "";
		if(pNameGl != null && !pNameGl.equals("")){
			sql += " and project.p_name like '%"+pNameGl+"%' ";
		}
		if(pNumberGl != null && !pNumberGl.equals("")){
			sql += " and project.p_number like '%"+pNumberGl+"%' ";
		}
		if(pCreatorGl != null && !pCreatorGl.equals("")){
			sql += " and user.username like '%"+pCreatorGl+"%' ";
		}
		if(createDatetimeGl != null && !createDatetimeGl.equals("")){
			sql += " and project.create_datetime like '%"+createDatetimeGl+"%' ";
		}
		if(keyWordsGl != null && !keyWordsGl.equals("")){
			sql += " and project.key_words like '%"+keyWordsGl+"%' ";
		}
		
		if(pName != null && !pName.equals("")&&!"p_name".equals(columnName)){
			if(pName.indexOf(",")>-1){
				sql += "and (";
				String[] pname = pName.split(",");
				for(int i=0;i<pname.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " p_name"+"='"+String.valueOf(pname[i])+"'";
						if("null".equals(pname[i])) {
							sql += " or p_name is null or p_name='' ";
						}
						if(i != pname.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(pName)) {
					sql += " and ( p_name is null or p_name='' or p_name='null' )";
				}else {
					sql += " and p_name in('"+pName+"')";
				}
			}
			
		}
		
		if(pNumber != null && !pNumber.equals("")&&!"p_number".equals(columnName)){
			if(pNumber.indexOf(",")>-1){
				sql += "and (";
				String[] pnumber = pNumber.split(",");
				for(int i=0;i<pnumber.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "p_number"+"='"+String.valueOf(pnumber[i])+"'";
						if("null".equals(pnumber[i])) {
							sql += " or p_number is null or p_number='' ";
						}
						if(i != pnumber.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(pNumber)) {
					sql += " and ( p_number is null or p_number='' or p_number='null' )";
				}else {
					sql += " and p_number in('"+pNumber+"')";
				}
			}
			
		}
		
		if(pCreator != null && !pCreator.equals("")&&!"username".equals(columnName)){
			if(pCreator.indexOf(",")>-1){
				sql += "and (";
				String[] create = pCreator.split(",");
				for(int i=0;i<create.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "username"+"='"+String.valueOf(create[i])+"'";
						if("null".equals(create[i])) {
							sql += " or username is null or username='' ";
						}
						if(i != create.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(pCreator)) {
					sql += " and ( username is null or username='' or username='null' )";
				}else {
					sql += " and username in('"+pCreator+"')";
				}
			}
			
		}
		
		if(createDatetime != null && !createDatetime.equals("")&&!"create_datetime".equals(columnName)){
			if(createDatetime.indexOf(",")>-1){
				sql += "and (";
				String[] createdatetime = createDatetime.split(",");
				for(int i=0;i<createdatetime.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "create_datetime"+"='"+String.valueOf(createdatetime[i])+"'";
						if("null".equals(createdatetime[i])) {
							sql += " or create_datetime is null or create_datetime='' ";
						}
						if(i != createdatetime.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(createDatetime)) {
					sql += " and ( create_datetime is null or create_datetime='' or create_datetime='null' )";
				}else {
					sql += " and create_datetime in('"+createDatetime+"')";
				}
			}
			
		}
		
		if(keyWords != null && !keyWords.equals("")&&!"key_words".equals(columnName)){
			if(keyWords.indexOf(",")>-1){
				sql += "and (";
				String[] keywords = keyWords.split(",");
				for(int i=0;i<keywords.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "key_words"+"='"+String.valueOf(keywords[i])+"'";
						if("null".equals(keywords[i])) {
							sql += " or key_words is null or key_words='' ";
						}
						if(i != keywords.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(keyWords)) {
					sql += " and ( key_words is null or key_words='' or key_words='null' )";
				}else {
					sql += " and key_words in('"+keyWords+"')";
				}
			}
			
		}
		
		if(isOpen != null && !isOpen.equals("")&&!"is_open".equals(columnName)){
			if(isOpen.indexOf(",")>-1){
				sql += "and (";
				String[] isopen = isOpen.split(",");
				for(int i=0;i<isopen.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
					if(String.valueOf(isopen[i]).equals("已公开")){
						sql += "is_open=1";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}else if(String.valueOf(isopen[i]).equals("未公开")){
						sql += "is_open=0";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}else if(String.valueOf(isopen[i]).equals("null")){
						sql += "( is_open is null or is_open ='' )";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}
				}
				sql += ")";
			}else{
				if(isOpen.equals("已公开")){
					sql += " and is_open = 1";
				} if(isOpen.equals("未公开")){
					sql += " and is_open = 0";
				}else if(isOpen.equals("null")){
					sql += " and (is_open = '' or is_open is null )";
				}
				
			}
			
		}
		sql+=" limit "+(pageIndex-1)*pageSize+","+pageSize;
		switch(columnName){
			case "p_name":
				return projectFilterDao.selectDistinctP_namePublic1(sql);
			case "p_number":
				return projectFilterDao.selectDistinctP_numberPublic1(sql);
			case "creator":
				return projectFilterDao.selectDistinctCreatorPublic1(sql);
			case "create_datetime":
				return projectFilterDao.selectDistinctCreateDatetimePublic1(sql);
			case "is_open":
				List<String> strings = new ArrayList<String>();
				List<String> queryStrings =projectFilterDao.selectDistinctIsOpenPublic1(sql);
				if(queryStrings.contains("1")) {
					strings.add("已公开");
				}
				if(queryStrings.contains("0")) {
					strings.add("未公开");
				}
				if(queryStrings.contains("")||queryStrings.contains(null)) {
					strings.add("");
				}
				return strings;
			case "key_words":
				return projectFilterDao.selectDistinctKeyWordsPublic1(sql);
			default:
				return null;
		}
	}
	
	/**
	 * 根据筛选条件查询我创建的项目
	 * @return	筛选后的我创建的项目
	 */
	public Map<String, Object> selectCreatedProjectByFilterCondition(Integer creator ,  
			Integer page , Integer strip , 
			String searchWord ,QueryCondition projectQueryCondition){
		PageHelper.startPage(page, strip);
		Map<String, Object> map = new HashMap<String , Object>();
		//将相关的筛选条件放入到map中，方便动态构造sql语句的时候使用
		map.put("creator", String.valueOf(creator));//项目创建者ID
		map.put("searchWord", searchWord);//搜索关键字，对应前端右上角的搜索框中的值
		map.put("columnName", projectQueryCondition.getColumnName());//筛选的字段名
		map.put("order", projectQueryCondition.getOrder());//筛选字段排序方式
		map.put("filter", projectQueryCondition.getFilter());//筛选字段过滤条件
		map.put("values", projectQueryCondition.getStrings());//筛选值，list形式
		
		List<Project> projects = projectFilterDao.selectCreatedProjectByFilterCondition(map);
		PageInfo<Project> pageInfo = new PageInfo<Project>(projects);
		Map<String, Object> result = new HashMap<String , Object>();
		result.put("total", pageInfo.getTotal());
		result.put("list", projects);
		return result;
	}
	
	/**
	 * 根据筛选条件查询我创建的项目
	 * @return	筛选后的我创建的项目
	 */
	public Map<String, Object> selectCreatedProjectByFilterCondition1(Integer creator ,  
			Integer page , Integer strip , 
			String searchWord ,QueryCondition projectQueryCondition, String pName, String pNumber, String pCreator,
			String createDatetime, String keyWords, String isOpen, String pNameGl, String pNumberGl, String pCreatorGl,
			String createDatetimeGl, String keyWordsGl, String isOpenGl){
		PageHelper.startPage(page, strip);
		Map<String, Object> map = new HashMap<String , Object>();
		//将相关的筛选条件放入到map中，方便动态构造sql语句的时候使用
		map.put("creator", String.valueOf(creator));//项目创建者ID
		map.put("searchWord", searchWord);//搜索关键字，对应前端右上角的搜索框中的值
		map.put("columnName", projectQueryCondition.getColumnName());//筛选的字段名
		map.put("order", projectQueryCondition.getOrder());//筛选字段排序方式
		map.put("filter", projectQueryCondition.getFilter());//筛选字段过滤条件
		map.put("values", projectQueryCondition.getStrings());//筛选值，list形式
		
		String columnName = (String)map.get("columnName");//字段名
		String filter = (String)map.get("filter");//过滤内容
		String order = (String)map.get("order");//是否排序
		@SuppressWarnings("unchecked")
		List<String> valueList = (List<String>)map.get("values");
		
		if(columnName.equals("creator")){//因为搜索值以及过滤值都是真实的姓名，而不是ID，所以此处转换一下，字段改为user表中的username
			columnName = new String("username");
		}
		if(columnName.equals("is_open")){//如果是公开状态，需要将筛选条件转换成对应的数据库中0-1值
			if(filter.contains("公") || filter.contains("开") || filter.contains("已")){
				//如果筛选条件中包含以上三个字，认为是要筛选公开的项目，设置为数据库中is_open的数值1
				filter = "1";
			}
			if(filter.contains("未")){
				//如果筛选条件中包含未，认为是要筛选未公开的项目，设置为数据库中is_open的数值0
				filter = "0";
			}
		}
		
		//构建动态sql
		String sql = "";
		
		sql += "select project.* , user.username as creatorName from project,user where ";
		sql += "project.creator=user.id and creator="+creator+" and p_name like '"+"%"+searchWord+"%' ";//创建者ID以及搜索条件
		sql += "and "+columnName+" like '%"+filter+"%' ";//某字段过滤条件
		
		if(pNameGl!= null && !pNameGl.equals("") && (pName == null || pName.equals(""))){
			sql += "and p_name like '%"+pNameGl+"%' ";
		}
		if(pNumberGl!= null && !pNumberGl.equals("") && (pNumber == null || pNumber.equals(""))){
			sql += "and p_number like '%"+pNumberGl+"%' ";
		}
		if(pCreatorGl!= null && !pCreatorGl.equals("") && (pCreator == null || pCreator.equals(""))){
			sql += "and username like '%"+pCreatorGl+"%' ";
		}
		if(createDatetimeGl!= null && !createDatetimeGl.equals("") && (createDatetime == null || createDatetime.equals(""))){
			sql += "and create_datetime like '%"+createDatetimeGl+"%' ";
		}
		if(keyWordsGl!= null && !keyWordsGl.equals("") && (keyWords == null || keyWords.equals(""))){
			sql += "and key_words like '%"+keyWordsGl+"%' ";
		}
		/*if(isOpenGl!= null && !isOpenGl.equals("") && (pName == null || pName.equals(""))){
			sql += "and is_open like '%"+isOpenGl+"%' ";
		}*/
		
		if(pName != null && !pName.equals("")){
			if(pName.indexOf(",")>-1){
				sql += "and (";
				String[] pname = pName.split(",");
				for(int i=0;i<pname.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " p_name"+"='"+String.valueOf(pname[i])+"'";
						if("null".equals(pname[i])) {
							sql += " or p_name is null or p_name='' ";
						}
						if(i != pname.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				
				sql += " and (p_name in('"+pName+"') ";
				if("null".equals(pName)) {
					sql += " or p_name is null or p_name=''  or p_name='null' ";
				}
				sql += ")";
				
			}
			
		}
		
		if(pNumber != null && !pNumber.equals("")){
			if(pNumber.indexOf(",")>-1){
				sql += "and (";
				String[] pnumber = pNumber.split(",");
				for(int i=0;i<pnumber.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " p_number"+"='"+String.valueOf(pnumber[i])+"'";
						if("null".equals(pnumber[i])) {
							sql += " or p_number is null  or p_number=''  ";
						}
						if(i != pnumber.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				sql += " and (p_number in('"+pNumber+"') ";
				if("null".equals(pNumber)) {
					sql += " or p_number is null or p_number=''  ";
				}
				sql += ")";
			}
			
		}
		
		if(pCreator != null && !pCreator.equals("")){
			if(pCreator.indexOf(",")>-1){
				sql += "and (";
				String[] create = pCreator.split(",");
				for(int i=0;i<create.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "username"+"='"+String.valueOf(create[i])+"'";
						if("null".equals(create[i])) {
							sql += " or username is null  or username='' ";
						}
						if(i != create.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				sql += " and (username in('"+pCreator+"') ";
				if("null".equals(pCreator)) {
					sql += " or username is null or username='' ";
				}
				sql += ")";
				
			}
			
		}
		
		if(createDatetime != null && !createDatetime.equals("")){
			if(createDatetime.indexOf(",")>-1){
				sql += "and (";
				String[] createdatetime = createDatetime.split(",");
				for(int i=0;i<createdatetime.length;i++){
					
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "create_datetime"+"='"+String.valueOf(createdatetime[i])+"'";
						if("null".equals(createdatetime[i])) {
							sql += " or create_datetime is null  or create_datetime='' ";
						}
						if(i != createdatetime.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				sql += " and (create_datetime in('"+createDatetime+"') ";
				if("null".equals(createDatetime)) {
					sql += " or create_datetime is null or create_datetime='' ";
				}
				sql += ")";
				
			}
			
		}
		
		if(keyWords != null && !keyWords.equals("")){
			if(keyWords.indexOf(",")>-1){
				sql += "and (";
				String[] keywords = keyWords.split(",");
				for(int i=0;i<keywords.length;i++){
					
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "key_words"+"='"+String.valueOf(keywords[i])+"'";
						if("null".equals(keywords[i])) {
							sql += " or key_words is null  or key_words='' ";
						}
						if(i != keywords.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				sql += " and (key_words in('"+keyWords+"') ";
				if("null".equals(keyWords)) {
					sql += " or key_words is null or key_words='' or key_words='null' ";
				}
				sql += ")";
			
			}
			
		}
		
		if(isOpen != null && !isOpen.equals("")){
			if(isOpen.indexOf(",")>-1){
				sql += "and (";
				String[] isopen = isOpen.split(",");
				for(int i=0;i<isopen.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
					if(String.valueOf(isopen[i]).equals("已公开")){
						sql += " is_open=1 ";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}else if(String.valueOf(isopen[i]).equals("未公开")){
						sql += " is_open=1 ";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}else if(String.valueOf(isopen[i]).equals("null")){
						sql += " is_open is null or is_open='' ";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}
				}
				sql += ")";
			}else{
				if(isOpen.equals("已公开")){
					sql += " and is_open = 1 ";
				}else if(isOpen.equals("未公开")){
					sql += " and is_open = 0 ";
				}else if(isOpen.equals("null")){
					sql += " and (is_open is null or is_open='' )";
				}
			}
			
		}
		
		//字段是否需要排序
		if(order != null && (order.equals("desc") || order.equals("asc"))){
			sql += "order by "+columnName+" "+order;
		}
		
		List<Project> projects = projectFilterDao.selectCreatedProjectByFilterCondition1(sql);
		PageInfo<Project> pageInfo = new PageInfo<Project>(projects);
		Map<String, Object> result = new HashMap<String , Object>();
		result.put("total", pageInfo.getTotal());
		result.put("list", projects);
		return result;
	}
	
	/**
	 * 根据筛选条件查询我的项目
	 * @return	筛选后的我的项目
	 */
	public Map<String, Object> selectMineProjectByFilterCondition(Integer user_id ,  
			Integer page , Integer strip , 
			String searchWord ,QueryCondition projectQueryCondition){
		PageHelper.startPage(page, strip);
		Map<String, Object> map = new HashMap<String , Object>();
		//将相关的筛选条件放入到map中，方便动态构造sql语句的时候使用
		map.put("user_id", String.valueOf(user_id));//我的ID
		map.put("searchWord", searchWord);//搜索关键字，对应前端右上角的搜索框中的值
		map.put("columnName", projectQueryCondition.getColumnName());//筛选的字段名
		map.put("order", projectQueryCondition.getOrder());//筛选字段排序方式
		map.put("filter", projectQueryCondition.getFilter());//筛选字段过滤条件
		map.put("values", projectQueryCondition.getStrings());//筛选值，list形式
		
		List<Project> projects = projectFilterDao.selectMineProjectByFilterCondition(map);
		PageInfo<Project> pageInfo = new PageInfo<Project>(projects);
		Map<String, Object> result = new HashMap<String , Object>();
		result.put("total", pageInfo.getTotal());
		result.put("list", projects);
		return result;
	}
	
	/**
	 * 根据筛选条件查询我的项目
	 * @return	筛选后的我的项目
	 */
	public Map<String, Object> selectMineProjectByFilterCondition1(Integer user_id ,  
			Integer page , Integer strip , 
			String searchWord ,QueryCondition projectQueryCondition, String pName, String pNumber, String pCreator,
			String createDatetime, String keyWords, String isOpen, String pNameGl, String pNumberGl, String pCreatorGl,
			String createDatetimeGl, String keyWordsGl, String isOpenGl){
		PageHelper.startPage(page, strip);
		Map<String, Object> map = new HashMap<String , Object>();
		//将相关的筛选条件放入到map中，方便动态构造sql语句的时候使用
		map.put("user_id", String.valueOf(user_id));//我的ID
		map.put("searchWord", searchWord);//搜索关键字，对应前端右上角的搜索框中的值
		map.put("columnName", projectQueryCondition.getColumnName());//筛选的字段名
		map.put("order", projectQueryCondition.getOrder());//筛选字段排序方式
		map.put("filter", projectQueryCondition.getFilter());//筛选字段过滤条件
		map.put("values", projectQueryCondition.getStrings());//筛选值，list形式
		

		String columnName = (String)map.get("columnName");//字段名
		String filter = (String)map.get("filter");//过滤内容
		String order = (String)map.get("order");//是否排序
		@SuppressWarnings("unchecked")
		List<String> valueList = (List<String>)map.get("values");
		
		if(columnName.equals("creator")){//因为搜索值以及过滤值都是真实的姓名，而不是ID，所以此处转换一下，字段改为user表中的username
			columnName = new String("username");
		}
		if(columnName.equals("is_open")){//如果是公开状态，需要将筛选条件转换成对应的数据库中0-1值
			if(filter.contains("公") || filter.contains("开") || filter.contains("已")){
				//如果筛选条件中包含以上三个字，认为是要筛选公开的项目，设置为数据库中is_open的数值1
				filter = "1";
			}
			if(filter.contains("未")){
				//如果筛选条件中包含未，认为是要筛选未公开的项目，设置为数据库中is_open的数值0
				filter = "0";
			}
		}
		
		//构建动态sql
		String sql = "";
		
		sql += "select project.*,user.username as creatorName from project , project_user,user where ";
		sql += "project.id=project_user.project_id and project.creator=user.id and project_user.user_id="+user_id+" and p_name like '"+"%"+searchWord+"%' ";//创建者ID以及搜索条件
		sql += "and "+columnName+" like '%"+filter+"%' ";//某字段过滤条件
		
		
		if(pNameGl!= null && !pNameGl.equals("") && (pName == null || pName.equals(""))){
			sql += "and p_name like '%"+pNameGl+"%' ";
		}
		if(pNumberGl!= null && !pNumberGl.equals("") && (pNumber == null || pNumber.equals(""))){
			sql += "and p_number like '%"+pNumberGl+"%' ";
		}
		if(pCreatorGl!= null && !pCreatorGl.equals("") && (pCreator == null || pCreator.equals(""))){
			sql += "and username like '%"+pCreatorGl+"%' ";
		}
		if(createDatetimeGl!= null && !createDatetimeGl.equals("") && (createDatetime == null || createDatetime.equals(""))){
			sql += "and create_datetime like '%"+createDatetimeGl+"%' ";
		}
		if(keyWordsGl!= null && !keyWordsGl.equals("") && (keyWords == null || keyWords.equals(""))){
			sql += "and key_words like '%"+keyWordsGl+"%' ";
		}
		/*if(isOpenGl!= null && !isOpenGl.equals("") && (pName == null || pName.equals(""))){
			sql += "and is_open like '%"+isOpenGl+"%' ";
		}*/
		
		if(pName != null && !pName.equals("")){
			if(pName.indexOf(",")>-1){
//				sql += "and (";
				String[] pname = pName.split(",");
				sql+=" and p_name in ( ";
				for(int i=0;i<pname.length;i++){
					if("null".equals(pname[i])) {
						sql+="'',null,";
					}
					sql+="'"+pname[i]+"',";
				}
				sql=sql.substring(0, sql.length()-1);
				sql+=")";
				/*for(int i=0;i<pname.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "p_name"+"='"+String.valueOf(pname[i])+"'";
						if(i != pname.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";*/
			}else{
				sql += " and p_name in('"+pName+"')";
			}
			
		}
		
		if(pNumber != null && !pNumber.equals("")){
//			if(pNumber.indexOf(",")>-1){
//				sql += "and (";
				String[] pnumber = pNumber.split(",");
				sql+=" and p_number in ( ";
				for(int i=0;i<pnumber.length;i++){
					if("null".equals(pnumber[i])) {
						sql+="'',null,";
					}
					sql+="'"+pnumber[i]+"',";
				}
				/*for(int i=0;i<pnumber.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "p_number"+"='"+String.valueOf(pnumber[i])+"'";
						if(i != pnumber.length-1){
							sql += " or ";	
						}
				}*/
				sql=sql.substring(0, sql.length()-1);
				sql+=")";
			/*}else{
				sql += " and p_number in('"+pNumber+"')";
			}*/
			
		}
		
		if(pCreator != null && !pCreator.equals("")){
//			if(pCreator.indexOf(",")>-1){
//				sql += "and (";
				String[] create = pCreator.split(",");
				sql+=" and username in ( ";
				for(int i=0;i<create.length;i++){
					if("null".equals(create[i])) {
						sql+="'',null,";
					}
					sql+="'"+create[i]+"',";
					
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						/*sql += "username"+"='"+String.valueOf(create[i])+"'";
						if(i != create.length-1){
							sql += " or ";	
						}*/
				}
				sql=sql.substring(0, sql.length()-1);
				sql += ")";
			}/*else{
				sql += " and username in('"+pCreator+"')";
			}*/
			
		
		if(createDatetime != null && !createDatetime.equals("")){
			/*String[] createdatetime = createDatetime.split(",");
			sql+=" and create_datetime in ( ";
			for(int i=0;i<createdatetime.length;i++){
				if("null".equals(createdatetime[i])) {
					sql+="'',null,";
				}
				sql+="'"+createdatetime[i]+"',";
			}
			sql=sql.substring(0, sql.length()-1);
			sql+=")";*/
			String[] createdatetime = createDatetime.split(",");
			if(createdatetime.length==2){
				sql += "and (";
				sql += " create_datetime >='"+String.valueOf(createdatetime[0])+"' "
						+ " and create_datetime <='"+String.valueOf(createdatetime[1])+"' ";
				
				sql += ")";
			}
			
		}
		
		if(keyWords != null && !keyWords.equals("")){
			String[] keywords = keyWords.split(",");
			sql+=" and key_words in ( ";
			for(int i=0;i<keywords.length;i++){
				if("null".equals(keywords[i])) {
					sql+="'',null,";
				}
				sql+="'"+keywords[i]+"',";
			}
			sql=sql.substring(0, sql.length()-1);
			sql+=")";
			/*if(keyWords.indexOf(",")>-1){
				sql += "and (";
				String[] keywords = keyWords.split(",");
				for(int i=0;i<keywords.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "key_words"+"='"+String.valueOf(keywords[i])+"'";
						if(i != keywords.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				sql += " and key_words in('"+keyWords+"')";
			}*/
			
		}
		
		if(isOpen != null && !isOpen.equals("")){
			if(isOpen.indexOf(",")>-1){
				sql += "and (";
				String[] isopen = isOpen.split(",");
				
				for(int i=0;i<isopen.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
					if(String.valueOf(isopen[i]).equals("已公开")){
						sql += "is_open=1";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}else if(String.valueOf(isopen[i]).equals("未公开")){
						sql += "is_open=1";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}else if(String.valueOf(isopen[i]).equals("null")){
						sql += "is_open is null";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}
				}
				sql += ")";
			}else{
				if(isOpen.equals("已公开")){
					sql += " and is_open = 1";
				}else if(isOpen.equals("未公开")){
					sql += " and is_open =0";
				}else if(isOpen.equals("null")){
					sql += " and is_open is null";
				}
			}
			
		}
		
		//字段是否需要排序
		if(order != null && (order.equals("desc") || order.equals("asc"))){
			sql += "order by "+columnName+" "+order;
		}
		List<Project> projects = projectFilterDao.selectMineProjectByFilterCondition1(sql);
		PageInfo<Project> pageInfo = new PageInfo<Project>(projects);
		Map<String, Object> result = new HashMap<String , Object>();
		result.put("total", pageInfo.getTotal());
		result.put("list", projects);
		return result;
	}
	
	/**
	 * 根据筛选条件查询公开的项目
	 * @return	筛选后的公开的项目
	 */
	public Map<String, Object> selectPublicProjectByFilterCondition(
			Integer page , Integer strip , 
			String searchWord ,QueryCondition projectQueryCondition){
		PageHelper.startPage(page, strip);
		Map<String, Object> map = new HashMap<String , Object>();
		//将相关的筛选条件放入到map中，方便动态构造sql语句的时候使用
		map.put("searchWord", searchWord);//搜索关键字，对应前端右上角的搜索框中的值
		map.put("columnName", projectQueryCondition.getColumnName());//筛选的字段名
		map.put("order", projectQueryCondition.getOrder());//筛选字段排序方式
		map.put("filter", projectQueryCondition.getFilter());//筛选字段过滤条件
		map.put("values", projectQueryCondition.getStrings());//筛选值，list形式
		
		List<Project> projects = projectFilterDao.selectPublicProjectByFilterCondition(map);
		PageInfo<Project> pageInfo = new PageInfo<Project>(projects);
		Map<String, Object> result = new HashMap<String , Object>();
		result.put("total", pageInfo.getTotal());
		result.put("list", projects);
		return result;
	}
	
	/**
	 * 根据筛选条件查询公开的项目
	 * @return	筛选后的公开的项目
	 */
	public Map<String, Object> selectPublicProjectByFilterCondition1(
			Integer page , Integer strip , 
			String searchWord ,QueryCondition projectQueryCondition, String pName, String pNumber, String pCreator,
			String createDatetime, String keyWords, String isOpen, String pNameGl, String pNumberGl, String pCreatorGl,
			String createDatetimeGl, String keyWordsGl, String isOpenGl){
		PageHelper.startPage(page, strip);
		Map<String, Object> map = new HashMap<String , Object>();
		//将相关的筛选条件放入到map中，方便动态构造sql语句的时候使用
		map.put("searchWord", searchWord);//搜索关键字，对应前端右上角的搜索框中的值
		map.put("columnName", projectQueryCondition.getColumnName());//筛选的字段名
		map.put("order", projectQueryCondition.getOrder());//筛选字段排序方式
		map.put("filter", projectQueryCondition.getFilter());//筛选字段过滤条件
		map.put("values", projectQueryCondition.getStrings());//筛选值，list形式
		
		String columnName = (String)map.get("columnName");//字段名
		String filter = (String)map.get("filter");//过滤内容
		String order = (String)map.get("order");//是否排序
		@SuppressWarnings("unchecked")
		List<String> valueList = (List<String>)map.get("values");
		
		if(columnName.equals("creator")){//因为搜索值以及过滤值都是真实的姓名，而不是ID，所以此处转换一下，字段改为user表中的username
			columnName = new String("username");
		}
		if(columnName.equals("is_open")){//如果是公开状态，需要将筛选条件转换成对应的数据库中0-1值
			if(filter.contains("公") || filter.contains("开") || filter.contains("已")){
				//如果筛选条件中包含以上三个字，认为是要筛选公开的项目，设置为数据库中is_open的数值1
				filter = "1";
			}
			if(filter.contains("未")){
				//如果筛选条件中包含未，认为是要筛选未公开的项目，设置为数据库中is_open的数值0
				filter = "0";
			}
		}
		
		//构建动态sql
		String sql = "";
		
		sql += "select project.* , user.username as creatorName from project,user ";
		sql += "where project.creator=user.id and is_open=1 and p_name like '"+"%"+searchWord+"%' ";//创建者ID以及搜索条件
		sql += "and "+columnName+" like '%"+filter+"%' ";//某字段过滤条件
		
		if(pNameGl!= null && !pNameGl.equals("") && (pName == null || pName.equals(""))){
			sql += "and p_name like '%"+pNameGl+"%' ";
		}
		if(pNumberGl!= null && !pNumberGl.equals("") && (pNumber == null || pNumber.equals(""))){
			sql += "and p_number like '%"+pNumberGl+"%' ";
		}
		if(pCreatorGl!= null && !pCreatorGl.equals("") && (pCreator == null || pCreator.equals(""))){
			sql += "and username like '%"+pCreatorGl+"%' ";
		}
		if(createDatetimeGl!= null && !createDatetimeGl.equals("") && (createDatetime == null || createDatetime.equals(""))){
			sql += "and create_datetime like '%"+createDatetimeGl+"%' ";
		}
		if(keyWordsGl!= null && !keyWordsGl.equals("") && (keyWords == null || keyWords.equals(""))){
			sql += "and key_words like '%"+keyWordsGl+"%' ";
		}
		/*if(isOpenGl!= null && !isOpenGl.equals("") && (pName == null || pName.equals(""))){
			sql += "and is_open like '%"+isOpenGl+"%' ";
		}*/
		
		if(pName != null && !pName.equals("")){
			if(pName.indexOf(",")>-1){
				sql += "and (";
				String[] pname = pName.split(",");
				for(int i=0;i<pname.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " p_name"+"='"+String.valueOf(pname[i])+"'";
						if("null".equals(pname[i])) {
							sql += " or p_name is null or p_name='' ";
						}
						if(i != pname.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(pName)) {
					sql += " and( p_name is null or p_name='' or p_name='null') ";
				}else {
					sql += " and p_name in('"+pName+"')";
				}
			}
			
		}
		
		if(pNumber != null && !pNumber.equals("")){
			if(pNumber.indexOf(",")>-1){
				sql += "and (";
				String[] pnumber = pNumber.split(",");
				for(int i=0;i<pnumber.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " p_number"+"='"+String.valueOf(pnumber[i])+"'";
						if("null".equals(pnumber[i])) {
							sql += " or  p_number is null or p_number='' ";
						}
						if(i != pnumber.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(pNumber)) {
					sql += " and (p_number is null or p_number='' or p_number='null' )";
				}else {
					sql += " and p_number in('"+pNumber+"')";
				}
			}
			
		}
		
		if(pCreator != null && !pCreator.equals("")){
			if(pCreator.indexOf(",")>-1){
				sql += "and (";
				String[] create = pCreator.split(",");
				for(int i=0;i<create.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " username"+"='"+String.valueOf(create[i])+"'";
						if("null".equals(create[i])) {
							sql += " or username is null or username='' ";
						}
						if(i != create.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(pCreator)) {
					sql += "and ( username is null or username='' or username='null') ";
				}else {
					sql += " and username in('"+pCreator+"')";
				}
			}
			
		}
		
		if(createDatetime != null && !createDatetime.equals("")){
			if(createDatetime.indexOf(",")>-1){
				sql += "and (";
				String[] createdatetime = createDatetime.split(",");
				
				for(int i=0;i<createdatetime.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " create_datetime"+"='"+String.valueOf(createdatetime[i])+"'";
						if("null".equals(createdatetime[i])) {
							sql += " or  create_datetime is null or create_datetime='' ";
						}
						if(i != createdatetime.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(createDatetime)) {
					sql += " and ( create_datetime is null or create_datetime='') ";
				}else {
					sql += " and create_datetime in('"+createDatetime+"')";
				}
			}
			
		}
		
		if(keyWords != null && !keyWords.equals("")){
			if(keyWords.indexOf(",")>-1){
				sql += " and (";
				String[] keywords = keyWords.split(",");
				for(int i=0;i<keywords.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " key_words"+"='"+String.valueOf(keywords[i])+"'";
						if("null".equals(keywords[i])) {
							sql += " or  key_words is null or key_words='' ";
						}
						if(i != keywords.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				if("null".equals(keyWords)) {
					sql += " and (key_words is null or key_words='') ";
				}else {
					sql += " and key_words in('"+keyWords+"')";
				}
			}
			
		}
		
		if(isOpen != null && !isOpen.equals("")){
			if(isOpen.indexOf(",")>-1){
				sql += "and (";
				String[] isopen = isOpen.split(",");
				for(int i=0;i<isopen.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
					if(String.valueOf(isopen[i]).equals("已公开")){
						sql += " is_open=1";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}else if(String.valueOf(isopen[i]).equals("未公开")){
						sql += " is_open=1";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}else if(String.valueOf(isopen[i]).equals("null")){
						sql += " is_open is null or is_open='' ";
						if(i != isopen.length-1){
							sql += " or ";	
						}
					}
				}
				sql += ")";
			}else{
				if(isOpen.equals("已公开")){
					sql += " and is_open =1 ";
				}else if(isOpen.equals("未公开")){
					sql += " and is_open =0 ";
				}else if(isOpen.equals("null")){
					sql += " and (is_open is null or is_open='') ";
				}
			}
			
		}
		
		//字段是否需要排序
		if(order != null && (order.equals("desc") || order.equals("asc"))){
			sql += "order by "+columnName+" "+order;
		}
		
	
		
		List<Project> projects = projectFilterDao.selectPublicProjectByFilterCondition1(sql);
		PageInfo<Project> pageInfo = new PageInfo<Project>(projects);
		Map<String, Object> result = new HashMap<String , Object>();
		result.put("total", pageInfo.getTotal());
		result.put("list", projects);
		return result;
	}

}
