package com.dzjin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzjin.dao.ProjectFileDao;
import com.dzjin.dao.ProjectFloderDao;
import com.dzjin.dao.ProjectFloderFileFilterDao;
import com.dzjin.model.ProjectFile;
import com.dzjin.model.ProjectFloder;
import com.dzjin.model.QueryCondition;

/**
 * 过滤文件Service
 * @author admin
 *
 */
@Service
public class ProjectFileFilterService {
	@Autowired
	private ProjectFloderFileFilterDao projectFileFilterDao;
	@Autowired
	private ProjectFileDao projectFileDao;
	@Autowired
	private ProjectFloderDao projectFloderDao;
	@Autowired
	private ProjectFloderService projectFloderService;
	
	
	
	public List<String> selectFileColumns(Integer floder_id,String columnName,String filter ,
			String fileName, String fileType,String fileSize,String createDatetime,String creator
			){
		List<String> filesName=new ArrayList<>();
		getFileColumns(floder_id,columnName,filter,fileName,fileType,fileSize,createDatetime,creator,filesName);
		return filesName;
	}
	
	public void getFileColumns(Integer floder_id,String columnName,String filter ,
			String fileName, String fileType,String fileSize,String createDatetime,String creator,List<String> filesName
			) {
		List<ProjectFloder> projectFloders = projectFloderDao.selectProjectFloderByParentId(floder_id);
		if(projectFloders.size()>0){
			filesName.addAll(selectProjectFileColumns(floder_id,columnName,filter,fileName,fileType,fileSize,createDatetime,creator));
			for(ProjectFloder projectFloder :projectFloders) {
				getFileColumns(projectFloder.getId(),columnName,filter,fileName,fileType,fileSize,createDatetime,creator,filesName);
			}
		}else{
			//查看下面包含的文件
			filesName.addAll(selectProjectFileColumns(floder_id,columnName,filter,fileName,fileType,fileSize,createDatetime,creator));
		}
	}
	
	public List<String> selectProjectFileColumns(Integer floder_id,String columnName,String filter ,
			String fileName, String fileType,String fileSize,String createDatetime,String creator
			){
		List<String> filesName=new ArrayList<>();
		String sql = "";
		if(fileName != null &&!"".equals(fileName)&&!"file_name".equals(columnName)){
			String[] fileNames = fileName.split(",");
			sql+=" and file_name in ( ";
			for(int i=0;i<fileNames.length;i++){
				if("空值".equals(fileNames[i])) {
					sql+="'',";
				}
				sql+="'"+fileNames[i]+"',";
			}
			sql=sql.substring(0, sql.length()-1);
			sql+=")";
		}
		if(fileType != null &&!"".equals(fileType)&&!"file_type".equals(columnName)){
			String[] fileTypes = fileType.split(",");
			sql+=" and file_type in ( ";
			for(int i=0;i<fileTypes.length;i++){
				if("空值".equals(fileTypes[i])) {
					sql+="'',null";
				}
				sql+="'"+fileTypes[i]+"',";
			}
			sql=sql.substring(0, sql.length()-1);
			sql+=")";
		}
		if(fileSize != null &&!"".equals(fileSize)&&!"file_size".equals(columnName)){
			String[] fileSizes = fileSize.split(",");
			sql+=" and file_size in ( ";
			for(int i=0;i<fileSizes.length;i++){
				if("空值".equals(fileSizes[i])) {
					sql+="'',";
				}
				sql+="'"+fileSizes[i]+"',";
			}
			sql=sql.substring(0, sql.length()-1);
			sql+=")";
		}
		if(creator != null &&!"".equals(creator)&&!"creator".equals(columnName)){
			String[] creators = creator.split(",");
			sql+=" and creator in ( ";
			for(int i=0;i<creators.length;i++){
				if("空值".equals(creators[i])) {
					sql+="'',";
				}
				sql+="'"+creators[i]+"',";
			}
			sql=sql.substring(0, sql.length()-1);
			sql+=")";
		}
		if(createDatetime != null &&!"".equals(createDatetime)&&!"create_datetime".equals(columnName)){
			String[] createDatetimes = createDatetime.split(",");
			sql+=" and create_datetime in ( ";
			for(int i=0;i<createDatetimes.length;i++){
				if("空值".equals(createDatetimes[i])) {
					sql+="'',";
				}
				sql+="'"+createDatetimes[i]+"',";
			}
			sql=sql.substring(0, sql.length()-1);
			sql+=")";
		}
		switch(columnName){
			//按文件名称
			case "file_name":
				sql+=" and project_file.file_name like '%"+filter+"%' ";
				filesName= projectFileFilterDao.selectFileNameByFloderId(floder_id,sql);
				break;
				//按文件类型
			case "file_type":
				sql+=" and project_file.file_type like '%"+filter+"%' ";
				filesName= projectFileFilterDao.selectFileTypeByFloderId(floder_id,sql);
				break;
				//按创建人
			case "creator":
				sql+=" and user.username like '%"+filter+"%' ";
				filesName= projectFileFilterDao.selectCreatorNameByFloderId(floder_id,sql);
				break;
				//按创建时间
			case "create_datetime":
				if(null!=filter&&filter.trim().length()>0) {
					sql+=" and project_file.create_datetime = '"+filter+"' ";
				}
				filesName= projectFileFilterDao.selectCreateDatetimeByFloderId(floder_id,sql);
				break;
				//按文件大小
			case "file_size":
				if(null!=filter&&filter.trim().length()>0) {
					sql+=" and project_file.file_size = '"+filter+"' ";
				}
				filesName= projectFileFilterDao.selectFileSizeByFloderId(floder_id,sql);
				break;
		}
		return filesName;
	}
	
	/**
	 * 过滤某个文件夹下面的文件
	 * @param floder_id
	 * @return
	 */
	public List<ProjectFile> selectProjectFileByFloderId(QueryCondition query,Integer floderId,String searchWord,String fileName,String fileType,String fileSize,String createDatetime,String creator){
		List<ProjectFile> projectFiles = new ArrayList<ProjectFile>();
		getProjectFiles(query,floderId, projectFiles,fileName,fileType,fileSize,createDatetime,creator);
		return projectFiles;
	}
	
	public void getProjectFiles(QueryCondition query,Integer floderId , List<ProjectFile> projectFiles,String fileName,String fileType,String fileSize,String createDatetime,String creator){
		List<ProjectFloder> projectFloders = projectFloderDao.selectProjectFloderByParentId(floderId);
		if(projectFloders.size()>0){
			projectFiles.addAll(filterFiles(query,floderId,fileName,fileType,fileSize,createDatetime,creator));
			//然后进行遍历
			Iterator<ProjectFloder> iterator = projectFloders.iterator();
			while(iterator.hasNext()){
				ProjectFloder projectFloder = (ProjectFloder) iterator.next();
				getProjectFiles(query,projectFloder.getId(), projectFiles,fileName,fileType,fileSize,createDatetime,creator);
			}
		}else{
			//查看下面包含的文件
			projectFiles.addAll(filterFiles(query,floderId,fileName,fileType,fileSize,createDatetime,creator));
		}
	}
	/**
	 * 查询过滤的数据
	 * @param query
	 * @param floderId
	 * @param fileName
	 * @param fileType
	 * @param fileSize
	 * @param createDatetime
	 * @param creator
	 * @return
	 */
	public List<ProjectFile> filterFiles(QueryCondition query,Integer floderId,String fileName,String fileType,String fileSize,String createDatetime,String creator ){
		String sql = "select project_file.* , user.username from project_file,user where user.id=project_file.creator_id  "
				+ " and  floder_id="+floderId;
		if(fileName != null && !fileName.equals("")){
			if(fileName.indexOf(",")>-1){
				sql += " and (";
				String[] fileNames = fileName.split(",");
				for(int i=0;i<fileNames.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += "file_name"+"='"+String.valueOf(fileNames[i])+"'";
						if(i != fileNames.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				sql += " and file_name in('"+fileName+"')";
			}
		}
		if(fileType != null && !fileType.equals("")){
			if(fileType.indexOf(",")>-1){
				sql += " and (";
				String[] fileTypes = fileType.split(",");
				for(int i=0;i<fileTypes.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " file_type"+"='"+String.valueOf(fileTypes[i])+"'";
						if(i != fileTypes.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				sql += " and file_type in('"+fileType+"')";
			}
		}
		if(fileSize != null && !fileSize.equals("")){
			if(fileSize.indexOf(",")>-1){
				sql += " and (";
				String[] sizes = fileSize.split(",");
				for(int i=0;i<sizes.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " file_size"+"='"+String.valueOf(sizes[i])+"'";
						if(i != sizes.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				sql += " and file_size in('"+fileSize+"')";
			}
		}
		if(createDatetime != null && !createDatetime.equals("")){
			if(createDatetime.indexOf(",")>-1){
				sql += " and (";
				String[] times = createDatetime.split(",");
				for(int i=0;i<times.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " project_file.create_datetime"+"='"+String.valueOf(times[i])+"'";
						if(i != times.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				sql += " and project_file.create_datetime in('"+createDatetime+"')";
			}
		}
		if(creator != null && !creator.equals("")){
			if(creator.indexOf(",")>-1){
				sql += " and (";
				String[] creators = creator.split(",");
				for(int i=0;i<creators.length;i++){
					//如果筛选字段是is_open，同样需要将可视的字段值转换成数据库中对应的0-1值
						//其他字段不需要进行转换
						sql += " user.username"+"='"+String.valueOf(creators[i])+"'";
						if(i != creators.length-1){
							sql += " or ";	
						}
					
				}
				sql += ")";
			}else{
				sql += " and user.username in('"+creator+"')";
			}
		}
		//字段是否需要排序
		if(query.getOrder() != null && (query.getOrder().equals("desc") || query.getOrder().equals("asc"))){
			sql += " order by "+query.getColumnName()+" "+query.getOrder();
		}
		List<ProjectFile> projectFiles=projectFileFilterDao.selectProjectFileByFilter(sql);
		return projectFiles;
	}
	
}
