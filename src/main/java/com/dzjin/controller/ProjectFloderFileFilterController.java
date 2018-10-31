package com.dzjin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzjin.model.QueryCondition;
import com.dzjin.service.ProjectFileFilterService;

@Controller
@RequestMapping("/projectFloderFileFilter")
public class ProjectFloderFileFilterController {
	@Autowired
	private ProjectFileFilterService projectFileFilterService;
	
	@Value("${project.file.download}")
	private String fileDownloadLocation;
	

	/**
	 * 根据过滤条件筛选某个字段的值我的项目
	 * @param session
	 * @param request
	 * @param projectQueryCondition
	 * @return
	 */
	@PostMapping("/getColumns")
	@ResponseBody
	public Map<String, Object> getDistinctColumnValueByColumnNameAndUidMine1(
			 HttpServletRequest request,String columnName,Integer floderId,String filter,
			 String fileName, String fileType,String fileSize,String createDatetime,String creator
			 ){
		List<String> strings = projectFileFilterService.selectFileColumns(floderId,columnName,filter
				, fileName,  fileType, fileSize, createDatetime, creator
				);
		Map<String, Object> map = new HashMap<>();
		map.put("result", true);
		map.put("message", strings);
		return map;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	@ResponseBody
	@PostMapping("/selectFilesByFilterCondition")
	public Map<String, Object> selectFilesByFilterCondition(HttpSession httpSession, QueryCondition query,Integer floderId,
			String searchWord,String fileName,String fileType,String fileSize,String createDatetime,String creator) {
		if(query == null){
			query = new QueryCondition();
		}
		query.setStrings(query.getValues());//将筛选值的字符串形式切割成list形式，便于操作
		httpSession.setAttribute("projectQueryCondition", query);//设置筛选条件
		
		Map<String, Object> map = new HashMap<>();
		map.put("result", true);
		map.put("projectFiles", projectFileFilterService.selectProjectFileByFloderId(query,floderId,searchWord,fileName,fileType,fileSize,createDatetime,creator));
		map.put("fileDownloadLocation", fileDownloadLocation);
		return map;
	}
}
