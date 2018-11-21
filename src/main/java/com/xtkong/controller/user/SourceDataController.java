package com.xtkong.controller.user;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.liutianjun.pojo.UserDataRelation;
import com.xtkong.model.FormatFile;
import com.xtkong.service.*;
import com.xtkong.util.MyFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dzjin.model.ProjectCustomRole;
import com.dzjin.service.ProjectCustomRoleService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liutianjun.pojo.User;
import com.xtkong.dao.hbase.HBaseFormatNodeDao;
import com.xtkong.dao.hbase.HBaseSourceDataDao;
import com.xtkong.model.FormatType;
import com.xtkong.model.Source;
import com.xtkong.model.SourceField;
import com.xtkong.util.ConstantsHBase;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("/sourceData")
public class SourceDataController {

	private static final Logger logger  =  Logger.getLogger(SourceDataController.class );

	static class SourceDataSQLInfo
	{
		private String sql;
		private List<String> qualifiers;
		private Map<String, String> conditionEqual;
		private Map<String, String> conditionLike;
		private Source source;

		public String getSql() {
			return sql;
		}

		public void setSql(String sql) {
			this.sql = sql;
		}

		public List<String> getQualifiers() {
			return qualifiers;
		}

		public void setQualifiers(List<String> qualifiers) {
			this.qualifiers = qualifiers;
		}

		public Map<String, String> getConditionEqual() {
			return conditionEqual;
		}

		public void setConditionEqual(Map<String, String> conditionEqual) {
			this.conditionEqual = conditionEqual;
		}

		public Map<String, String> getConditionLike() {
			return conditionLike;
		}

		public void setConditionLike(Map<String, String> conditionLike) {
			this.conditionLike = conditionLike;
		}

		public Source getSource() {
			return source;
		}

		public void setSource(Source source) {
			this.source = source;
		}
	}

	@Autowired
	SourceService sourceService;
	@Autowired
	SourceFieldService sourceFieldService;
	@Autowired
	FormatTypeService formatTypeService;
	@Autowired
	FormatFieldService formatFieldService;
	@Autowired
	FormatFileService formatFileService;
	@Autowired
	ProjectCustomRoleService projectCustomRoleService;
	@Autowired
	UserDataService userDataService;

	@Autowired
	ProjectDataService projectDataService;

	@Value("${formatData.file.location}")
	private String dataFileLocation;
	
	@RequestMapping("/firstIn")
	public String firstIn(HttpServletRequest request, HttpSession httpSession, String type, Integer page,
			Integer strip,Integer cs_id, String block) {

		return getSourceDatas(request, httpSession, type, cs_id, page, strip, null, null, null, null, null, null, null,
				null, null, null, false,block);
	}

	public SourceDataSQLInfo getSourceDataSQL(Integer cs_id,
											   User user,
											   String type,
											   String searchFirstWord,
											   String oldCondition,
											   String fieldIds,
											   Integer p_id,
											   Integer searchId,
											   String chooseDatas,
											   String likeSearch,
											   String searchWord,
											   boolean isOnlySelectPK,
											   String excludeIDs)
	{
		SourceDataSQLInfo result=new SourceDataSQLInfo();
		Source source=sourceService.getSourceByCs_id(cs_id);
		source.setSourceFields(sourceFieldService.getSourceFields(cs_id));
		String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;// 表名
		List<String> qualifiers = new ArrayList<>();    //
		Map<String, String> conditionEqual = new HashMap<>();
		Map<String, String> conditionLike = new HashMap<>();
		String condition = "";

		SourceField creator = new SourceField();
		creator.setCs_id(cs_id);  //采集源cs_id>创建者
		creator.setCsf_id(Integer.valueOf(ConstantsHBase.QUALIFIER_CREATOR));//简历采集源字段
		creator.setCsf_name("创建人");//采集字段名>创建人
		source.getSourceFields().add(creator);//
		SourceField createDate = new SourceField();
		createDate.setCs_id(cs_id);
		createDate.setCsf_id(Integer.valueOf(ConstantsHBase.QUALIFIER_CREATE_DATETIME));
		createDate.setCsf_name("创建时间");
		source.getSourceFields().add(createDate);
		for (SourceField sourceField : source.getSourceFields()) {
			qualifiers.add(String.valueOf(sourceField.getCsf_id()));//获取采集源字段的值添加到qualifiers数组，（获取表中的值）
		}
		// 源数据字段数据，注：每个列表第一个值sourceDataId不显示
		switch (type) {
			case "1":
				// conditionEqual.put(ConstantsHBase.QUALIFIER_CREATE,
				// String.valueOf(user.getId()));
				condition = "(" + PhoenixClient.getSQLFamilyColumn(ConstantsHBase.QUALIFIER_CREATE) + "='"
						+ String.valueOf(user.getId()) + "' ";
				List<String> sourceDataIds = userDataService.selects(user.getId(), cs_id);
				if (!sourceDataIds.isEmpty()) {
					condition += " OR (";
					for (String sourceDataId : sourceDataIds) {
						condition += "ID='" + sourceDataId + "' OR ";
					}
					if (condition.trim().endsWith("OR")) {
						condition = condition.substring(0, condition.lastIndexOf("OR")) + " ) ";
					}
				}
				condition += ")";
				break;
			case "2":
				SourceField publicStatus = new SourceField();
				publicStatus.setCs_id(cs_id);
				publicStatus.setCsf_name("公开状态");
				source.getSourceFields().add(publicStatus);
				qualifiers.add(ConstantsHBase.QUALIFIER_PUBLIC);
				conditionEqual.put(ConstantsHBase.QUALIFIER_CREATE, String.valueOf(user.getId()));
				break;
			case "3":
				conditionEqual.put(ConstantsHBase.QUALIFIER_PUBLIC, String.valueOf(ConstantsHBase.VALUE_PUBLIC_TRUE));
				break;
			case "4":
				if(p_id!=null)
				{
					conditionEqual.put(ConstantsHBase.QUALIFIER_PROJECT, String.valueOf(p_id));
				}
				break;
			case "5":
				if(p_id!=null)
				{
					conditionEqual.put(ConstantsHBase.QUALIFIER_PROJECT, String.valueOf(p_id));
				}
				break;
		}
		// 头筛选
		if (searchFirstWord != null && !searchFirstWord.trim().isEmpty()) {
			if (oldCondition == null) {
				oldCondition = " ";
			} else if (oldCondition.trim().isEmpty()) {
				oldCondition = " ";
			} else {
				oldCondition += " AND ";
			}
			if (fieldIds != null && !fieldIds.trim().isEmpty()) {
				Map<String, String> like = new HashMap<>();
				for (String fieldId : fieldIds.split(",")) {
					if (qualifiers.contains(fieldId)) {
						like.put(fieldId, searchFirstWord);
					}
				}
				oldCondition += PhoenixClient.getSQLConditionLikes(tableName, like, "OR");
			} else if (!source.getSourceFields().isEmpty()) {
				Map<String, String> like = new HashMap<>();
				for (String qualifier : qualifiers) {
					like.put(qualifier, searchFirstWord);
				}
				oldCondition += PhoenixClient.getSQLConditionLikes(tableName, like, "OR");
			}
		}
		// 筛选
		// oldCondition = (String) httpSession.getAttribute("oldCondition");
		if (searchId != null) {
			if (oldCondition == null) {
				oldCondition = " ";
			} else if (oldCondition.trim().isEmpty()) {
				oldCondition = " ";
			} else {
				oldCondition += " AND ";
			}
			if (chooseDatas != null && !chooseDatas.trim().isEmpty()) {
				oldCondition += "( ";
				for (String csfChooseData : chooseDatas.split(",")) {
					if (csfChooseData.equals("空值")) {
						oldCondition += "\"" + ConstantsHBase.FAMILY_INFO + "\".\"" + String.valueOf(searchId)
								+ "\" IS NULL OR ";
					} else {
						oldCondition += "\"" + ConstantsHBase.FAMILY_INFO + "\".\"" + String.valueOf(searchId)
								+ "\"='" + csfChooseData + "' OR ";
					}
				}
				if (oldCondition.trim().endsWith("OR")) {
					oldCondition = oldCondition.substring(0, oldCondition.lastIndexOf("OR")) + " ) ";
				}
			} else if (likeSearch != null && likeSearch.equals("1") && searchWord != null) {
				if (searchWord.startsWith("_")) {
					String search =searchWord.replaceAll("_", "\"_");
					oldCondition += "(\"" + ConstantsHBase.FAMILY_INFO + "\".\"" + String.valueOf(searchId)
							+ "\" LIKE '%" + search + "%') ";
				}else if (searchWord.startsWith("&")) {
					String search =searchWord.replaceAll("%", "%%");
					oldCondition += "(\"" + ConstantsHBase.FAMILY_INFO + "\".\"" + String.valueOf(searchId)
							+ "\" LIKE '%" + search + "%') ";
				}else{
					oldCondition += "(\"" + ConstantsHBase.FAMILY_INFO + "\".\"" + String.valueOf(searchId)
							+ "\" LIKE '%" + searchWord + "%') ";
				}

			}
			if (oldCondition.trim().endsWith("AND")) {
				oldCondition = oldCondition.substring(0, oldCondition.lastIndexOf("AND"));
			}
		}

		//添加排除ID列表
		String excludeCondition="ID NOT IN (";
		if(excludeIDs!=null && !excludeIDs.trim().equals(""))
		{
			String[] splitIds=excludeIDs.trim().replaceAll("check", "").split(",");
			if(splitIds.length>0)
			{
				for(String id : splitIds)
				{
					excludeCondition+="'"+id.trim()+"',";
				}
				//去掉最后的逗号
				excludeCondition=excludeCondition.substring(0,excludeCondition.length()-1);
				excludeCondition+=")";

				oldCondition+=" "+excludeCondition;
			}
		}

		// if (csfCondition != null&& !csfCondition.trim().isEmpty()) {
		// condition = oldCondition;
		// }
		if (oldCondition != null && !oldCondition.trim().isEmpty()) {
			if (type.equals("1")) {
				condition = condition + " AND " + oldCondition;
			} else {
				condition = oldCondition;
			}
		}

		//****************************************************
		String phoenixSQL = PhoenixClient.getPhoenixSQL(tableName, isOnlySelectPK ? null : qualifiers, conditionEqual, conditionLike,
				condition, null, null);

		result.setSql(phoenixSQL);
		result.setQualifiers(qualifiers);
		result.setConditionEqual(conditionEqual);
		result.setConditionLike(conditionLike);
		result.setSource(source);
		logger.info("---------------"+condition);
		logger.info("---------------"+oldCondition);
		logger.info("---------------"+phoenixSQL);
		return result;
	}

	/**
	 * 获取源数据
	 * 
	 * @param request
	 * @param httpSession
	 * @param type
	 *            "1":我的 "2":我创建的 "3":公开"4":项目"5":项目数据
	 * @param cs_id
	 * @param page
	 * @param strip
	 * @param searchId
	 * @param desc_asc
	 * @param searchWord
	 * @param chooseDatas
	 * @param oldCond8ition
	 * @param p_id
	 * @param searchFirstWord
	 * @return sources 采集源列表 source 选中采集源的字段列表 sourceDatas
	 *         源数据字数据，注：每个列表第一个值sourceDataId不显示
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSourceDatas")
	public String getSourceDatas(HttpServletRequest request, HttpSession httpSession, String type, Integer cs_id,
			Integer page, Integer strip, Integer searchId, String desc_asc, String searchWord, String chooseDatas,
			String oldCond8ition, Integer p_id, String searchFirstWord, String fieldIds, String likeSearch, String ids,
			boolean isAll, String block) {
		//logger.info("SourceDataController-getSourceDatas: "+ids);
		User user = (User) request.getAttribute("user");

		if (page == null) {
			page = 1;
		}
		if (strip == null) {
			strip = 12;
		}
		List<Source> sources = sourceService.getSourcesForUser();// 选取用户源
		List<List<String>> sourceDatas = new ArrayList<>();//
		Integer total = 0; // 总数为零

		Source source = null; // 采集源为空
		String oldCondition = null; // 原来的状态
		if (!sources.isEmpty()) { // 用户源不为空，不为null
			try {
				if ((type.equals((String) httpSession.getAttribute("oldSourceType")))// 类型为原类型
						&& (cs_id.equals((Integer) httpSession.getAttribute("thiscs_id")))) {// 采集源id为原id
					oldCondition = (String) httpSession.getAttribute("oldCondition");// 状态为原来的状态
				}
			} catch (Exception e1) {
			}
			if (cs_id == null) {// 采集源为null
				cs_id = sourceService.getSourcesForUserLimit(1).get(0).getCs_id();
				oldCondition = null;
			}

			//source = sourceService.getSourceByCs_id(cs_id);// 选取管理源
			//source.setSourceFields(sourceFieldService.getSourceFields(cs_id));
			/*
			 * 1，先判断cs_id是否有值
			 * 		1.1有值的情况直接将值存到session中就行，到其他页面来获取
			 * 		1.2没值的话，则判断session中是否有cs_id的值
			 * 			1.2.1有值的话直接从session中获取这个cs_id
			 * 			1.2.2没值的话   则不设置
			 * 
			 */

			String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;// 表名
			Map<String, Map<String, Object>> result = new HashMap<>();

			SourceDataSQLInfo sourceDataSQLInfo=getSourceDataSQL(cs_id,user,type,searchFirstWord,oldCondition,fieldIds,p_id,searchId,chooseDatas,likeSearch,searchWord,false,null);
			source=sourceDataSQLInfo.getSource();

			//不知道这代码什么意思,暂时放在这
			if(cs_id!=0) {
				source.setCs_id(cs_id);
			}else {
				Source attribute = (Source) httpSession.getAttribute("source");
				if(attribute.getCs_id()!=0) {
					source.setCs_id(attribute.getCs_id());
				}
			}

			String phoenixSQL=sourceDataSQLInfo.getSql();
			//logger.info(phoenixSQL);
			total = PhoenixClient.count(phoenixSQL);

			// 排序
			String condition = null;

			try {
				switch (desc_asc) {
				case "DESC":
					break;
				case "ASC":
					break;
				default:
					desc_asc = (String) httpSession.getAttribute("desc_asc");
				}
				if (desc_asc == null) {
					desc_asc = "ASC";
				}
				switch (desc_asc) {
				case "DESC":
					break;
				case "ASC":
					break;
				default:
					desc_asc = "ASC";
				}
			} catch (Exception e) {
				desc_asc = "ASC";
			}
			if (searchId != null) {
				condition = " ORDER BY " + PhoenixClient.getSQLFamilyColumn(String.valueOf(searchId)) + " " + desc_asc
						+ " ";
			} else {
				Integer id = (Integer) httpSession.getAttribute("searchId");
				if (sourceDataSQLInfo.getQualifiers().contains(String.valueOf(id))) {
					condition = " ORDER BY " + PhoenixClient.getSQLFamilyColumn(String.valueOf(id)) + " " + desc_asc
							+ " ";
				}
			}
			phoenixSQL = PhoenixClient.getPhoenixSQL(phoenixSQL, condition, page, strip);

			result = PhoenixClient.select(phoenixSQL);

			String resultMsg = String.valueOf((result.get("msg")).get("msg"));
			for (int j = 0; j < 6; j++) {
				resultMsg = String.valueOf((result.get("msg")).get("msg"));
				if (resultMsg.equals("success")) {
					sourceDatas = (List<List<String>>) result.get("records").get("data");
					break;
				} else {
					PhoenixClient.undefined(resultMsg, tableName, sourceDataSQLInfo.getQualifiers(), sourceDataSQLInfo.getConditionEqual(), sourceDataSQLInfo.getConditionLike());
					result = PhoenixClient.select(phoenixSQL);
				}
			}
			httpSession.setAttribute("sources", sources);// 采集源列表
			httpSession.setAttribute("thiscs_id", source.getCs_id());
			httpSession.setAttribute("source", source);// 采集源字段列表
			httpSession.setAttribute("sourceDatas", sourceDatas);// 源数据字段数据，注：每个列表第一个值sourceDataId不显示
			httpSession.setAttribute("total", total);
			httpSession.setAttribute("page", page);
			httpSession.setAttribute("rows", strip);
			httpSession.setAttribute("searchId", searchId);
			httpSession.setAttribute("desc_asc", desc_asc);
			httpSession.setAttribute("oldCondition", oldCondition);
			httpSession.setAttribute("oldSourceType", type);
			httpSession.setAttribute("searchFirstWord", searchFirstWord);
			httpSession.setAttribute("chooseDatas", chooseDatas);
			httpSession.setAttribute("likeSearch", likeSearch);

		}
		httpSession.setAttribute("allids", ids);
		httpSession.setAttribute("isAll1", isAll);
		switch (type) {
		case "1":
			List<String> authority_numbers = new ArrayList<>();
//			authority_numbers.add("30");
			/*
			 * 添加格式数据，应该是32，而不是30和31
			 */
			authority_numbers.add("32");
			List<ProjectCustomRole> projects = projectCustomRoleService.selectProjectCustomRolesByUID(user.getId(),
					authority_numbers);
			// projects =
			// projectCustomRoleService.selectMyProject(user.getId());
			httpSession.setAttribute("projects", projects);// 项目列表
			if(null!=block&&block.equals("2")) {
				//方块格式
				return "redirect:/jsp/formatdata/data_mine2.jsp";
			}else {
				//列表格式
				return "redirect:/jsp/formatdata/data_mine.jsp";
			}
		case "2":
			if(null!=block&&block.equals("2")) {
				//方块格式
				return "redirect:/jsp/formatdata/data_create2.jsp";
			}else {
				//列表格式
				return "redirect:/jsp/formatdata/data_create.jsp";
			}
		case "3":
			if(null!=block&&block.equals("2")) {
				//方块格式
				return "redirect:/jsp/formatdata/data_public2.jsp";
			}else {
				//列表格式
				return "redirect:/jsp/formatdata/data_public.jsp";
			}
		case "4":
			return "redirect:/jsp/project/project_data.jsp";
		case "5":
			return "redirect:/jsp/project/data_reselect.jsp";
		default:
			return "redirect:/jsp/formatdata/data_public.jsp";
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/getSourceDatas1")

	public String getSourceDatas1(HttpServletRequest request, HttpSession httpSession, String type, Integer cs_id,
			Integer page, Integer strip, Integer searchId, String desc_asc, String searchWord, String chooseDatas,
			String oldCond8ition, Integer p_id, String searchFirstWord, String fieldIds, String likeSearch,
			String typeID, String ids, boolean isAll) {
		User user = (User) request.getAttribute("user");

		if (page == null) {
			page = 1;
		}
		if (strip == null) {
			strip = 12;
		}
		List<Source> sources = sourceService.getSourcesForUser();// 选取用户源
		List<List<String>> sourceDatas = new ArrayList<>();//
		Integer total = 0; // 总数为零

		Source source = null; // 采集源为空
		String oldCondition = null; // 原来的状态
		if (!sources.isEmpty()) { // 用户源不为空，不为null
			try {
				if ((type.equals((String) httpSession.getAttribute("oldSourceType")))// 类型为原类型
						&& (cs_id.equals((Integer) httpSession.getAttribute("thiscs_id")))) {// 采集源id为原id
					oldCondition = (String) httpSession.getAttribute("oldCondition");// 状态为原来的状态
				}
			} catch (Exception e1) {
			}
			if (cs_id == null) {// 采集源为null
				cs_id = sourceService.getSourcesForUserLimit(1).get(0).getCs_id();
				oldCondition = null;
			}

			//source = sourceService.getSourceByCs_id(cs_id);// 选取管理源
			//source.setSourceFields(sourceFieldService.getSourceFields(cs_id));

//			Map<String, Map<String, Object>> result = new HashMap<>();
//			String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;// 表名
//			List<String> qualifiers = new ArrayList<>(); //
//			Map<String, String> conditionEqual = new HashMap<>();
//			Map<String, String> conditionLike = new HashMap<>();
//			String condition = "";
//
//			SourceField creator = new SourceField();
//			creator.setCs_id(cs_id); // 采集源cs_id>创建者
//			creator.setCsf_id(Integer.valueOf(ConstantsHBase.QUALIFIER_CREATOR));// 简历采集源字段
//			creator.setCsf_name("创建人");// 采集字段名>创建人
//			source.getSourceFields().add(creator);//
//			SourceField createDate = new SourceField();
//			createDate.setCs_id(cs_id);
//			createDate.setCsf_id(Integer.valueOf(ConstantsHBase.QUALIFIER_CREATE_DATETIME));
//			createDate.setCsf_name("创建时间");
//			source.getSourceFields().add(createDate);
//			for (SourceField sourceField : source.getSourceFields()) {
//				qualifiers.add(String.valueOf(sourceField.getCsf_id()));// 获取采集源字段的值添加到qualifiers数组，（获取表中的值）
//			}
//			// 源数据字段数据，注：每个列表第一个值sourceDataId不显示
//			switch (type) {
//			case "1":
//				// conditionEqual.put(ConstantsHBase.QUALIFIER_CREATE,
//				// String.valueOf(user.getId()));
//				condition = "(" + PhoenixClient.getSQLFamilyColumn(ConstantsHBase.QUALIFIER_CREATE) + "='"
//						+ String.valueOf(user.getId()) + "' ";
//				List<String> sourceDataIds = userDataService.selects(user.getId(), cs_id);
//				if (!sourceDataIds.isEmpty()) {
//					condition += " OR (";
//					for (String sourceDataId : sourceDataIds) {
//						condition += "ID='" + sourceDataId + "' OR ";
//					}
//					if (condition.trim().endsWith("OR")) {
//						condition = condition.substring(0, condition.lastIndexOf("OR")) + " ) ";
//					}
//				}
//				condition += ")";
//				break;
//			case "2":
//				SourceField publicStatus = new SourceField();
//				publicStatus.setCs_id(cs_id);
//				publicStatus.setCsf_name("公开状态");
//				source.getSourceFields().add(publicStatus);
//				qualifiers.add(ConstantsHBase.QUALIFIER_PUBLIC);
//				conditionEqual.put(ConstantsHBase.QUALIFIER_CREATE, String.valueOf(user.getId()));
//				break;
//			case "3":
//				conditionEqual.put(ConstantsHBase.QUALIFIER_PUBLIC, String.valueOf(ConstantsHBase.VALUE_PUBLIC_TRUE));
//				break;
//			case "4":
//				conditionEqual.put(ConstantsHBase.QUALIFIER_PROJECT, String.valueOf(p_id));
//				break;
//			case "5":
//				conditionEqual.put(ConstantsHBase.QUALIFIER_PROJECT, String.valueOf(p_id));
//				break;
//			}
//			// 头筛选
//			if (searchFirstWord != null && !searchFirstWord.trim().isEmpty()) {
//				if (oldCondition == null) {
//					oldCondition = " ";
//				} else if (oldCondition.trim().isEmpty()) {
//					oldCondition = " ";
//				} else {
//					oldCondition += " AND ";
//				}
//				if (fieldIds != null && !fieldIds.trim().isEmpty()) {
//					Map<String, String> like = new HashMap<>();
//					for (String fieldId : fieldIds.split(",")) {
//						if (qualifiers.contains(fieldId)) {
//							like.put(fieldId, searchFirstWord);
//						}
//					}
//					oldCondition += PhoenixClient.getSQLConditionLikes(tableName, like, "OR");
//				} else if (!source.getSourceFields().isEmpty()) {
//					Map<String, String> like = new HashMap<>();
//					for (String qualifier : qualifiers) {
//						like.put(qualifier, searchFirstWord);
//					}
//					oldCondition += PhoenixClient.getSQLConditionLikes(tableName, like, "OR");
//				}
//			}
//			// 筛选
//			// oldCondition = (String) httpSession.getAttribute("oldCondition");
//			if (searchId != null) {
//				if (oldCondition == null) {
//					oldCondition = " ";
//				} else if (oldCondition.trim().isEmpty()) {
//					oldCondition = " ";
//				} else {
//					oldCondition += " AND ";
//				}
//				if (chooseDatas != null && !chooseDatas.trim().isEmpty()) {
//					oldCondition += "( ";
//					for (String csfChooseData : chooseDatas.split(",")) {
//						if (csfChooseData.equals("空值")) {
//							oldCondition += "\"" + ConstantsHBase.FAMILY_INFO + "\".\"" + String.valueOf(searchId)
//									+ "\" IS NULL OR ";
//						} else {
//							oldCondition += "\"" + ConstantsHBase.FAMILY_INFO + "\".\"" + String.valueOf(searchId)
//									+ "\"='" + csfChooseData + "' OR ";
//						}
//					}
//					if (oldCondition.trim().endsWith("OR")) {
//						oldCondition = oldCondition.substring(0, oldCondition.lastIndexOf("OR")) + " ) ";
//					}
//				} else if (likeSearch != null && likeSearch.equals("1") && searchWord != null) {
//					if (searchWord.startsWith("_")) {
//						searchWord = "\\" + searchWord;
//						oldCondition += "(\"" + ConstantsHBase.FAMILY_INFO + "\".\"" + String.valueOf(searchId)
//								+ "\" LIKE '%" + searchWord + "%') ";
//					} else if (searchWord.startsWith("&")) {
//						String search = searchWord.replaceAll("%", "%%");
//						oldCondition += "(\"" + ConstantsHBase.FAMILY_INFO + "\".\"" + String.valueOf(searchId)
//								+ "\" LIKE '%" + search + "%') ";
//					} else {
//						oldCondition += "(\"" + ConstantsHBase.FAMILY_INFO + "\".\"" + String.valueOf(searchId)
//								+ "\" LIKE '%" + searchWord + "%') ";
//					}
//
//				}
//				if (oldCondition.trim().endsWith("AND")) {
//					oldCondition = oldCondition.substring(0, oldCondition.lastIndexOf("AND"));
//				}
//			}
//
//			if (oldCondition != null && !oldCondition.trim().isEmpty()) {
//				if (type.equals("1")) {
//					condition = condition + " AND " + oldCondition;
//				} else {
//					condition = oldCondition;
//				}
//			}
//
//			String phoenixSQL = PhoenixClient.getPhoenixSQL(tableName, qualifiers, conditionEqual, conditionLike,
//					condition, null, null);

			String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;// 表名
			Map<String, Map<String, Object>> result = new HashMap<>();

			SourceDataSQLInfo sourceDataSQLInfo=getSourceDataSQL(cs_id,user,type,searchFirstWord,oldCondition,fieldIds,p_id,searchId,chooseDatas,likeSearch,searchWord,false,null);
			source=sourceDataSQLInfo.getSource();

			String phoenixSQL=sourceDataSQLInfo.getSql();

			// 排序
			String condition = null;

			total = PhoenixClient.count(phoenixSQL);

			// 排序
			condition = null;

			try {
				switch (desc_asc) {
				case "DESC":
					break;
				case "ASC":
					break;
				default:
					desc_asc = (String) httpSession.getAttribute("desc_asc");
				}
				if (desc_asc == null) {
					desc_asc = "ASC";
				}
				switch (desc_asc) {
				case "DESC":
					break;
				case "ASC":
					break;
				default:
					desc_asc = "ASC";
				}
			} catch (Exception e) {
				desc_asc = "ASC";
			}
			if (searchId != null) {
				condition = " ORDER BY " + PhoenixClient.getSQLFamilyColumn(String.valueOf(searchId)) + " " + desc_asc
						+ " ";
			} else {
				Integer id = (Integer) httpSession.getAttribute("searchId");
				if (sourceDataSQLInfo.getQualifiers().contains(String.valueOf(id))) {
					condition = " ORDER BY " + PhoenixClient.getSQLFamilyColumn(String.valueOf(id)) + " " + desc_asc
							+ " ";
				}
			}
			phoenixSQL = PhoenixClient.getPhoenixSQL(phoenixSQL, condition, page, strip);

			result = PhoenixClient.select(phoenixSQL);

			String resultMsg = String.valueOf((result.get("msg")).get("msg"));
			for (int j = 0; j < 6; j++) {
				resultMsg = String.valueOf((result.get("msg")).get("msg"));
				if (resultMsg.equals("success")) {
					sourceDatas = (List<List<String>>) result.get("records").get("data");

					break;
				} else {
					PhoenixClient.undefined(resultMsg, tableName, sourceDataSQLInfo.getQualifiers(), sourceDataSQLInfo.getConditionEqual(), sourceDataSQLInfo.getConditionLike());
					result = PhoenixClient.select(phoenixSQL);
				}
			}
			httpSession.setAttribute("sources", sources);// 采集源列表
			httpSession.setAttribute("thiscs_id", cs_id);
			httpSession.setAttribute("source", source);// 采集源字段列表
			httpSession.setAttribute("sourceDatas", sourceDatas);// 源数据字段数据，注：每个列表第一个值sourceDataId不显示
			httpSession.setAttribute("total", total);
			httpSession.setAttribute("page", page);
			httpSession.setAttribute("rows", strip);
			httpSession.setAttribute("searchId", searchId);
			httpSession.setAttribute("desc_asc", desc_asc);
			httpSession.setAttribute("oldCondition", oldCondition);
			httpSession.setAttribute("oldSourceType", type);
			httpSession.setAttribute("typeID", typeID);
			httpSession.setAttribute("searchFirstWord", searchFirstWord);
			httpSession.setAttribute("allids", ids);
			httpSession.setAttribute("isAll1", isAll);
		}
		switch (type) {
		case "1":
			List<String> authority_numbers = new ArrayList<>();
			authority_numbers.add("30");
			authority_numbers.add("31");
			List<ProjectCustomRole> projects = projectCustomRoleService.selectProjectCustomRolesByUID(user.getId(),
					authority_numbers);
			// projects =
			// projectCustomRoleService.selectMyProject(user.getId());
			httpSession.setAttribute("projects", projects);// 项目列表
			return "redirect:/jsp/formatdata/data_mine2.jsp";
		case "2":
			return "redirect:/jsp/formatdata/data_create2.jsp";
		case "3":
			return "redirect:/jsp/formatdata/data_public2.jsp";
		case "4":
			return "redirect:/jsp/project/project_data2.jsp";
		case "5":
			return "redirect:/jsp/project/data_reselect.jsp";
		default:
			return "redirect:/jsp/formatdata/data_public.jsp";
		}

	}

	@RequestMapping("/reset")
	@ResponseBody
	public Map<String, Object> reset(HttpSession httpSession) {
		Map<String, Object> map = new HashMap<String, Object>();
		httpSession.setAttribute("oldCondition", null);
		map.put("result", true);
		map.put("message", "重置成功");
		return map;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/getSourceFieldDatas")
	@ResponseBody
	public Map<String, Object> getSourceFieldDatas(HttpServletRequest request, HttpSession httpSession, String type,
			Integer cs_id, Integer searchId, String searchWord, String oldCondition, Integer p_id,
			String searchFirstWord) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (cs_id == null || searchId == null || type == null) {
			map.put("result", false);
			map.put("message", "查询失败");
			return map;
		}
		Source source = sourceService.getSourceByCs_id(cs_id);
		if (source != null) {
			try {
				if ((type.equals((String) httpSession.getAttribute("oldSourceType")))
						&& (cs_id.equals((Integer) httpSession.getAttribute("thiscs_id")))) {
					oldCondition = (String) httpSession.getAttribute("oldCondition");
				}
			} catch (Exception e1) {
			}
			User user = (User) request.getAttribute("user");
			Map<String, Map<String, Object>> result = new HashMap<>();
			String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;
			List<String> qualifiers = new ArrayList<>();
			Map<String, String> conditionEqual = new HashMap<>();
			Map<String, String> conditionLike = new HashMap<>();
			String condition = "";
			String phoenixSQL = null;

			qualifiers.add(String.valueOf(searchId));

			switch (type) {
			case "1":
				condition = "(" + PhoenixClient.getSQLFamilyColumn(ConstantsHBase.QUALIFIER_CREATE) + "='"
						+ String.valueOf(user.getId()) + "' ";
				List<String> sourceDataIds = userDataService.selects(user.getId(), cs_id);
				if (!sourceDataIds.isEmpty()) {
					condition += " OR (";
					for (String sourceDataId : sourceDataIds) {
						condition += "ID='" + sourceDataId + "' OR ";
					}
					if (condition.trim().endsWith("OR")) {
						condition = condition.substring(0, condition.lastIndexOf("OR")) + " ) ";
					}
				}
				condition += ")";
				break;
			case "2":
				conditionEqual.put(ConstantsHBase.QUALIFIER_CREATE, String.valueOf(user.getId()));
				break;
			case "3":
				conditionEqual.put(ConstantsHBase.QUALIFIER_PUBLIC, String.valueOf(ConstantsHBase.VALUE_PUBLIC_TRUE));
				break;
			case "4":
				if (searchFirstWord == null) {
					searchFirstWord = new String("");
				}
				conditionEqual.put(ConstantsHBase.QUALIFIER_PROJECT, String.valueOf(p_id));
				List<SourceField> csfs = sourceFieldService.getSourceFields(cs_id);
				if (csfs != null && !csfs.isEmpty()) {
					conditionLike.put(String.valueOf(csfs.get(0).getCsf_id()), searchFirstWord);
				}
				break;

			}

			if (searchWord == null) {
				searchWord = "";
			}
			if (searchWord.startsWith("_")) {
				searchWord = "\\" + searchWord;
			}
			conditionLike.put(String.valueOf(searchId), searchWord);
			if (oldCondition != null && !oldCondition.trim().isEmpty()) {
				if (type.equals("1")) {
					condition = condition + "AND" + oldCondition;
				} else {
					condition = oldCondition;
				}
			}

			String select = "SELECT DISTINCT ";// 去重
			phoenixSQL = PhoenixClient.getPhoenixSQL(tableName, select, qualifiers, conditionEqual, conditionLike,
					condition, null, null) + " ORDER BY " + PhoenixClient.getSQLFamilyColumn(String.valueOf(searchId));

			result = PhoenixClient.select(phoenixSQL);

			List<String> csfDatas = new ArrayList<>();

			String resultMsg = String.valueOf((result.get("msg")).get("msg"));
			for (int j = 0; j < 6; j++) {
				resultMsg = String.valueOf((result.get("msg")).get("msg"));
				if (resultMsg.equals("success")) {
					try {
						for (List<String> datas : (List<List<String>>) result.get("records").get("data")) {
							csfDatas.add(datas.get(0));
						}
					} catch (Exception e) {
						continue;
					}
					break;
				} else {
					PhoenixClient.undefined(resultMsg, tableName, qualifiers, conditionEqual, conditionLike);
					result = PhoenixClient.select(phoenixSQL);
				}
			}
			map.put("result", true);
			map.put("csfDatas", csfDatas);
		} else {
			map.put("result", false);
			map.put("message", "查询失败");
		}
		return map;
	}

	/**
	 * 获取添加源数据表单
	 *
	 * @param cs_id
	 * @return
	 */
	@RequestMapping("/getInsertSourceDataForm")
	@ResponseBody
	public Map<String, Object> getInsertSourceDataForm(Integer cs_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Source source = sourceService.getSourceByCs_id(cs_id);
		if (source != null) {
			source.setSourceFields(sourceFieldService.getSourceFields(cs_id));
			map.put("result", true);
			map.put("source", source);
		} else {
			map.put("result", false);
			map.put("message", "查询失败");
		}
		return map;
	}

	/**
	 * 添加一条源数据
	 * 
	 * @param cs_id
	 *            采集源
	 * @param request
	 */
	@SuppressWarnings({ "rawtypes", "null" })
	@RequestMapping("/insertSourceData")
	@ResponseBody
	public Map<String, Object> insertSourceData(
			String cs_id, HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) request.getAttribute("user");
		Map<String,String> resultMap = new HashMap<String, String>();
		
		 Map param = request.getParameterMap();
		 Iterator entries = param.entrySet().iterator(); 
		 while (entries.hasNext()) { 
		   Map.Entry entry = (Map.Entry) entries.next(); 
		   String key = (String)entry.getKey(); 
		   String[] value = (String[])entry.getValue(); 
		   resultMap.put(key, value[0].equals("undefined")?"":value[0]);
		   System.out.println("Key = " + key + ", Value = " + value[0]); 
		 }
	   MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
       Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
       if(fileMap != null || fileMap.size() > 0){
    	   Collection<MultipartFile> files = fileMap.values();
           for(MultipartFile file:files){
        	   String key = file.getName();
               String req = file.getOriginalFilename();
               if(StringUtils.isBlank(req)){
            	   resultMap.put(key, "");
                   continue;
               }
            //文件上传地址
            //String contexPath= request.getSession().getServletContext().getRealPath("\\")+user.getUsername()+"\\";
          	String path =this.dataFileLocation;
           	File temp = new File(path);
           	if(!temp.exists() && !temp.isDirectory()){
           		temp.mkdir();
            }



               try {
				   String fileName = file.getOriginalFilename();
				   String md5Code=MyFileUtil.FileMD5(file.getBytes());
				   String relaPath=md5Code;

					FormatFile formatFileExists=formatFileService.SelectFormatFileByMD5Code(md5Code);
				   //如果没有这个文件的MD5,则新建并导入数据
				   if(formatFileExists==null)
				   {
					   FormatFile formatFile=new FormatFile();
					   //formatFile.setCs_id(Integer.valueOf(cs_id));
					   formatFile.setFilename(fileName);
					   formatFile.setMd5code(md5Code);
					   formatFile.setUid(user.getId());
					   formatFile.setPath(relaPath);
					   //formatFile.setCsf_id(Integer.valueOf(key));
					   try
					   {
						   formatFileService.insertFormatFile(formatFile);
					   }
					   catch(Exception e)
					   {
						   //如果异常不是因为文件已存在，则报错退出
						   if(!e.getMessage().toLowerCase().contains("ak_md5code"))
						   {
							   logger.error(e);
							   map.put("result", false);
							   map.put("message", "操作数据库出错!");
							   return map;
						   }
					   }

					   String path1 =this.dataFileLocation+File.separator+relaPath;
					   logger.info("path1-------------"+path1);
					   //File temp1 = new File(path1);
//					   if(!temp1.exists() && !temp1.isDirectory()){
//						   temp1.mkdir();
//					   }

					   File dest = new File(path1);
					   if(!dest.getParentFile().exists()){//判断文件父目录是否存在
						   if(!dest.getParentFile().mkdir() || !dest.createNewFile())
						   {
							   map.put("result", false);
							   map.put("message", "无法创建文件: "+path1);
							   return map;
						   }
					   }
					   file.transferTo(dest); //保存文件
					   System.out.println(dest.getAbsolutePath());
				   }
				   //如果有,则不新建，直接导入数据

//       			resultMap.put(key, user.getId()+"\\"+fileName);
       				resultMap.put(key, md5Code+"_"+fileName);

		   		} catch (Exception e) {
		   			e.printStackTrace();
		   			map.put("result", false);
		   	        map.put("message", "文件保存失败: "+e.getMessage());
		   	        return map;
		   		}
           }
       }
       String rowKey=HBaseSourceDataDao.insertSourceData(cs_id, String.valueOf(user.getId()),resultMap, user.getUsername());
       if (rowKey != null) {
			map.put("result", true);
			map.put("message", "新增成功");
		} else {
			map.put("result", false);
			map.put("message", "新增失败");
		}
	   return map;
               
	}

 
	/**
	 * 更新一条源数据
	 * 
	 * @param cs_id
	 *            采集源
	 * @param sourceDataId
	 * @param soufieldDatas
	 *            采集源字段id、 数据值
	 */
	@RequestMapping("/updateSourceData")
	@ResponseBody
	public Map<String, Object> updateSourceData(String cs_id, String sourceDataId, String soufieldDatas) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (HBaseSourceDataDao.updateSourceData(cs_id, sourceDataId,
				new Gson().fromJson(soufieldDatas, new TypeToken<Map<String, String>>() {
				}.getType()))) {
			map.put("result", true);
			map.put("message", "更新成功");
		} else {
			map.put("result", false);
			map.put("message", "更新失败");
		}
		return map;
	}
	
	/**
	 * 更新一条源数据 带文件类型
	 * 
	 * @param cs_id
	 *            采集源
	 * @param sourceDataId
	 * @param request
	 *            采集源字段id、 数据值
	 */
	@SuppressWarnings({ "rawtypes", "null" })
	@RequestMapping("/updateSourceAndFile")
	@ResponseBody
	public Map<String, Object> updateSourceAndFile(
			String cs_id, String sourceDataId, HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) request.getAttribute("user");
		Map<String,String> resultMap = new HashMap<String, String>();
		
		 Map param = request.getParameterMap();
		 Iterator entries = param.entrySet().iterator(); 
		 while (entries.hasNext()) { 
		   Map.Entry entry = (Map.Entry) entries.next(); 
		   String key = (String)entry.getKey(); 
		   String[] value = (String[])entry.getValue(); 
		   //undefined和为空的不更新
		   if(value[0].equals("undefined")||"".equals(value[0])) {
			   continue; 
		   }else {
			   resultMap.put(key, value[0].equals("undefined")?"":value[0]);
		   }
		   System.out.println("Key = " + key + ", Value = " + value[0]); 
		 }
	   MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
       Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
       if(fileMap != null || fileMap.size() > 0){
    	   Collection<MultipartFile> files = fileMap.values();
           for(MultipartFile file:files){
        	   String key = file.getName();
               String req = file.getOriginalFilename();
               if(StringUtils.isBlank(req)){
//            	   resultMap.put(key, "");
                   continue;
               }
            //文件上传地址
            //String contexPath= request.getSession().getServletContext().getRealPath("\\")+user.getUsername()+"\\";
          	String path =this.dataFileLocation;
           	File temp = new File(path);
           	if(!temp.exists() && !temp.isDirectory()){
           		temp.mkdir();
            }
          		

               try {
				   String md5Code= MyFileUtil.FileMD5(file.getBytes());
				   String relaPath=md5Code;
				   String fileName = file.getOriginalFilename();

				   FormatFile formatFileExists=formatFileService.SelectFormatFileByMD5Code(md5Code);
				   //如果没有这个文件的MD5,则新建并导入数据
				   if(formatFileExists==null)
				   {
					   FormatFile formatFile=new FormatFile();
					   //formatFile.setCs_id(Integer.valueOf(cs_id));
					   formatFile.setFilename(fileName);
					   formatFile.setMd5code(md5Code);
					   formatFile.setUid(user.getId());
					   formatFile.setPath(relaPath);
					   //formatFile.setCsf_id(Integer.valueOf(key));
					   try
					   {
						   formatFileService.insertFormatFile(formatFile);
					   }
					   catch(Exception e)
					   {
						   //如果异常不是因为文件已存在，则报错退出
						   if(!e.getMessage().toLowerCase().contains("ak_md5code"))
						   {
							   logger.error(e);
							   map.put("result", false);
							   map.put("message", "操作数据库出错!");
							   return map;
						   }
					   }

					   String path1 =this.dataFileLocation+File.separator+relaPath;
					   File temp1 = new File(path1);
					   if(!temp1.exists() && !temp1.isDirectory()){
						   temp1.mkdir();
					   }

					   File dest = new File(path1);
					   if(!dest.getParentFile().exists()){//判断文件父目录是否存在
						   dest.getParentFile().mkdir();
					   }

					   file.transferTo(dest); //保存文件
					   System.out.println(dest.getAbsolutePath());
				   }
				   //如果有,则不新建，直接导入数据

//       			resultMap.put(key, user.getId()+"\\"+fileName);
       			resultMap.put(key, md5Code+"_"+fileName);
				   //String oldMD5Code=oldFileName.substring(0,oldFileName.indexOf("_"));
				   //logger.info("oldMD5Code: "+oldMD5Code);

		   		} catch (Exception e) {
		   			e.printStackTrace();
		   			map.put("result", false);
		   	        map.put("message", "文件保存失败");
		   	        return map;
		   		}
           }
       }
       
       if( HBaseSourceDataDao.updateSourceData(cs_id, sourceDataId ,resultMap)) {
			map.put("result", true);
			map.put("message", "更新成功");
		} else {
			map.put("result", false);
			map.put("message", "更新失败");
		}
	   return map;
               
	}

	/**
	 * 通过sourceDataId获取一条源数据
	 * 
	 * @param httpSession
	 * @param cs_id
	 * @param sourceDataId
	 * @return
	 */
	@RequestMapping("/getSourceDataById")
	public String getSourceDataById(HttpServletRequest request, HttpSession httpSession, String cs_id,
			String sourceDataId, String type) {
		User user = (User) request.getAttribute("user");
		Source source = sourceService.getSourceByCs_id(Integer.valueOf(cs_id));
		source.setSourceFields(sourceFieldService.getSourceFields(Integer.valueOf(cs_id)));

		httpSession.setAttribute("source", source);// 采集源字段列表

		List<String> sourceData = HBaseSourceDataDao.getSourceDataById(cs_id, sourceDataId, source.getSourceFields());
		httpSession.setAttribute("sourceData", sourceData);

		HashMap<String, FormatType> formatTypeMap = new HashMap<>();
		List<FormatType> formatTypes = formatTypeService.getFormatTypes(Integer.valueOf(cs_id));
		for (FormatType formatType : formatTypes) {
			formatTypeMap.put(String.valueOf(formatType.getFt_id()), formatType);
		}
		List<FormatType> formatTypeFolders = HBaseFormatNodeDao.getFormatTypeFolders(cs_id, sourceDataId,
				formatTypeMap);
		httpSession.setAttribute("formatTypeFolders", formatTypeFolders);
		httpSession.setAttribute("type123", type);

		switch (type) {
		case "1":
			List<String> authority_numbers = new ArrayList<>();
			authority_numbers.add("30");
			authority_numbers.add("31");
			List<ProjectCustomRole> projects = projectCustomRoleService.selectProjectCustomRolesByUID(user.getId(),
					authority_numbers);
			// projects = projectCustomRoleService.selectMyProject(user.getId());
			httpSession.setAttribute("projects", projects);// 项目列表

			return "redirect:/jsp/formatdata/data_datain.jsp";
		case "2":
			return "redirect:/jsp/formatdata/data_datain2.jsp";
		case "3":
			return "redirect:/jsp/formatdata/data_datain3.jsp";
		case "4":
			return "redirect:/jsp/project/project_datain.jsp";
		case "5":
			return "redirect:/jsp/project/data_reselect_in.jsp";
		default:
			return "redirect:/jsp/formatdata/data_datain.jsp";
		}

	}

	/**
	 * 批量删除源数据
	 * @param request
	 * @param type
	 * @param cs_id
	 * @param ids
	 * @param isAll
	 * @param searchId
	 * @param searchWord
	 * @param desc_asc
	 * @param oldCondition
	 * @param searchFirstWord
	 * @param chooseDatas
	 * @param likeSearch
	 * @return
	 */
	@RequestMapping("/deleteSourceDatas")
	@ResponseBody
	public Map<String, Object> deleteSourceDatas(HttpServletRequest request,String type,String cs_id, String ids, boolean isAll,
    		String searchId, String searchWord,String desc_asc,String oldCondition,
            String searchFirstWord,String chooseDatas,String likeSearch) {
		//logger.info("SourceDataController-deleteSourceDatas: "+ids);
		Map<String, Object> map = new HashMap<String, Object>();
		if (isAll == true) {// true 全选状态下 选中的数据rowkey-ids
			User user = (User) request.getAttribute("user");
			Integer uid = user.getId();
			String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;

			Integer csId = cs_id.equals("")?null:Integer.valueOf(cs_id);
			Integer searchIdInt = searchId.equals("")?null:Integer.valueOf(searchId);
			SourceDataSQLInfo sourceDataSQLInfo=getSourceDataSQL(csId,user,"2",searchFirstWord,oldCondition,null,null,searchIdInt,chooseDatas,likeSearch,searchWord,true,ids);

			Map<String, Map<String, Object>> result = PhoenixClient.select(sourceDataSQLInfo.getSql());

			List<List<String>> sourceDatas = null;
			String resultMsg;
			for (int j = 0; j < 6; j++) {
				resultMsg = String.valueOf((result.get("msg")).get("msg"));
				if (resultMsg.equals("success")) {
					sourceDatas = (List<List<String>>) result.get("records").get("data");
					break;
				} else {
					PhoenixClient.undefined(resultMsg, tableName, sourceDataSQLInfo.getQualifiers(), sourceDataSQLInfo.getConditionEqual(), sourceDataSQLInfo.getConditionLike());
					result = PhoenixClient.select(sourceDataSQLInfo.getSql());
				}
			}
            
			String idsStr = "";
			if(sourceDatas!=null)
			{
				for (List<String> record : sourceDatas)
				{
					String idTemp = record.get(0);
					idsStr=idsStr+idTemp+",";
				}
				idsStr = idsStr.substring(0, idsStr.length()-1);
			}
			if (HBaseSourceDataDao.deleteSourceDatas(cs_id, idsStr)) {
				map.put("result", true);
				map.put("message", "删除成功");
			} else {
				map.put("result", false);
				map.put("message", "删除失败");
			}
			return map;
		} else { // 非全选状态 只操作ids中的数据 以“,”开头 去掉
			if (ids.startsWith(",")) {
				ids = ids.substring(1, ids.length()).replaceAll("check", "");
			}
			for (String sourceDataId : ids.split(",")) {
				try {
					userDataService.deleteid(sourceDataId, Integer.valueOf(cs_id));
				} catch (Exception e) {
					continue;
				}

			}
			if (HBaseSourceDataDao.deleteSourceDatas(cs_id, ids)) {
				map.put("result", true);
				map.put("message", "删除成功");
			} else {
				map.put("result", false);
				map.put("message", "删除失败");
			}
			return map;
		}

	}

	/**
	 * 公开
	 * @param request
	 * @param cs_id
	 * @param ids
	 * @param isAll
	 * @return
	 */
	@RequestMapping("/open")
	@ResponseBody
	public Map<String, Object> open(HttpServletRequest request,String cs_id, String ids, boolean isAll,
    		String searchId, String searchWord,String desc_asc,String oldCondition,
            String searchFirstWord,String chooseDatas,String likeSearch) {
		//logger.info("SourceDataController-open: "+ids);
		Map<String, Object> map = new HashMap<String, Object>();
		if (isAll == true) {
			
			User user = (User) request.getAttribute("user");
			Integer uid = user.getId();
			String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;

			Integer csId = cs_id.equals("")?null:Integer.valueOf(cs_id);
			Integer searchIdInt = searchId.equals("")?null:Integer.valueOf(searchId);
			SourceDataSQLInfo sourceDataSQLInfo=getSourceDataSQL(csId,user,"2",searchFirstWord,oldCondition,null,null,searchIdInt,chooseDatas,likeSearch,searchWord,true,ids);

			Map<String, Map<String, Object>> result = PhoenixClient.select(sourceDataSQLInfo.getSql());

			List<List<String>> sourceDatas = null;
			String resultMsg;
			for (int j = 0; j < 6; j++) {
				resultMsg = String.valueOf((result.get("msg")).get("msg"));
				if (resultMsg.equals("success")) {
					sourceDatas = (List<List<String>>) result.get("records").get("data");
					break;
				} else {
					PhoenixClient.undefined(resultMsg, tableName, sourceDataSQLInfo.getQualifiers(), sourceDataSQLInfo.getConditionEqual(), sourceDataSQLInfo.getConditionLike());
					result = PhoenixClient.select(sourceDataSQLInfo.getSql());
				}
			}
            
			String idsStr = "";
			if(sourceDatas!=null)
			{
				for (List<String> record : sourceDatas)
				{
					String idTemp = record.get(0);
					idsStr=idsStr+idTemp+",";
				}
				idsStr = idsStr.substring(0, idsStr.length()-1);
			}
			if (HBaseSourceDataDao.open(cs_id, idsStr)) {
				map.put("result", true);
				map.put("message", "公开成功");
			} else {
				map.put("result", false);
				map.put("message", "公开失败");
			}
			return map;
		} else {
			if (ids.startsWith(",")) {
				ids = ids.substring(1, ids.length()).replaceAll("check", "");
			}
			if (HBaseSourceDataDao.open(cs_id, ids)) {
				map.put("result", true);
				map.put("message", "公开成功");
			} else {
				map.put("result", false);
				map.put("message", "公开失败");
			}
			return map;
		}

	}

	/**
	 * 取消公开
	 *
	 * @param request
	 * @param cs_id
	 * @param ids
	 * @param isAll
	 * @return
	 */
	@RequestMapping("/notOpen")
	@ResponseBody
	public Map<String, Object> notOpen(HttpServletRequest request,String cs_id, String ids, boolean isAll,
    		String searchId, String searchWord,String desc_asc,String oldCondition,
            String searchFirstWord,String chooseDatas,String likeSearch) {
		//logger.info("SourceDataController-notOpen: "+ids);
		Map<String, Object> map = new HashMap<String, Object>();
		if (isAll == true) {
			User user = (User) request.getAttribute("user");
			Integer uid = user.getId();
			String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;

			Integer csId = cs_id.equals("")?null:Integer.valueOf(cs_id);
			Integer searchIdInt = searchId.equals("")?null:Integer.valueOf(searchId);
			SourceDataSQLInfo sourceDataSQLInfo=getSourceDataSQL(csId,user,"2",searchFirstWord,oldCondition,null,null,searchIdInt,chooseDatas,likeSearch,searchWord,true,ids);

			Map<String, Map<String, Object>> result = PhoenixClient.select(sourceDataSQLInfo.getSql());

			List<List<String>> sourceDatas = null;
			String resultMsg;
			for (int j = 0; j < 6; j++) {
				resultMsg = String.valueOf((result.get("msg")).get("msg"));
				if (resultMsg.equals("success")) {
					sourceDatas = (List<List<String>>) result.get("records").get("data");
					break;
				} else {
					PhoenixClient.undefined(resultMsg, tableName, sourceDataSQLInfo.getQualifiers(), sourceDataSQLInfo.getConditionEqual(), sourceDataSQLInfo.getConditionLike());
					result = PhoenixClient.select(sourceDataSQLInfo.getSql());
				}
			}
            
			String idsStr = "";
			if(sourceDatas!=null)
			{
				for (List<String> record : sourceDatas)
				{
					String idTemp = record.get(0);
					idsStr=idsStr+idTemp+",";
				}
				idsStr = idsStr.substring(0, idsStr.length()-1);
			}
			if (HBaseSourceDataDao.notopen(cs_id, idsStr)) {
				map.put("result", true);
				map.put("message", "取消公开成功");
			} else {
				map.put("result", false);
				map.put("message", "取消公开失败");
			}
			return map;
		} else {

			if (ids.startsWith(",")) {
				ids = ids.substring(1, ids.length()).replaceAll("check", "");
			}
			if (HBaseSourceDataDao.notopen(cs_id, ids)) {
				map.put("result", true);
				map.put("message", "取消公开成功");
			} else {
				map.put("result", false);
				map.put("message", "取消公开失败");
			}
			return map;
		}

	}

	/**
	 * 添加全选的采集源数据
	 *
	 * @param request
	 * @param cs_id   采集数据源ID
	 * @param type    类型,1为我的,2为我创建的,3为公共的,4和5待扩展
	 * @param searchFirstWord  为搜索关键字
	 * @param oldCondition  为原本所带的查询条件
	 * @param fieldIds  为指定搜索字段id,以逗号分隔,如果为空则指定所有字段
	 * @param p_id   为项目id
	 * @param searchId  为检索字段id,和下面三个参数一起用
	 * @param chooseDatas  为对searchId字段列举值列表搜索,要包含NULL用"空值"
	 * @param likeSearch  标识是否对searchId进行like查询,1为是,null为不是,与searchWord配合使用
	 * @param searchWord  为上述like查询关键字
	 * @return
	 */
	@RequestMapping("/addSourceDataAll")
	@ResponseBody
	public Map<String, Object> addSourceDataAll(HttpServletRequest request,
												  Integer cs_id,//采集数据源ID
												  String ids,
												  String type,//类型,1为我的,2为我创建的,3为公共的,4和5待扩展
												  String searchFirstWord,//为搜索关键字
												  String oldCondition,//为原本所带的查询条件
												  String fieldIds,//为指定搜索字段id,以逗号分隔,如果为空则指定所有字段
												  Integer p_id,//为项目id
												  Integer searchId,//为检索字段id,和下面三个参数一起用
												  String chooseDatas,//为对searchId字段列举值列表搜索,要包含NULL用"空值"
												  String likeSearch,//标识是否对searchId进行like查询,1为是,null为不是,与searchWord配合使用
												  String searchWord//为上述like查询关键字
	){
		User user = (User) request.getAttribute("user");
		Integer uid = user.getId();
		String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;

		SourceDataSQLInfo sourceDataSQLInfo=getSourceDataSQL(cs_id,user,type,searchFirstWord,oldCondition,fieldIds,p_id,searchId,chooseDatas,likeSearch,searchWord,true,ids);

		Map<String, Map<String, Object>> result = PhoenixClient.select(sourceDataSQLInfo.getSql());

		List<List<String>> sourceDatas = null;
		String resultMsg;
		for (int j = 0; j < 6; j++) {
			resultMsg = String.valueOf((result.get("msg")).get("msg"));
			if (resultMsg.equals("success")) {
				sourceDatas = (List<List<String>>) result.get("records").get("data");
				break;
			} else {
				PhoenixClient.undefined(resultMsg, tableName, sourceDataSQLInfo.getQualifiers(), sourceDataSQLInfo.getConditionEqual(), sourceDataSQLInfo.getConditionLike());
				result = PhoenixClient.select(sourceDataSQLInfo.getSql());
			}
		}

		List<UserDataRelation> userDataRelationList=new ArrayList();
		if(sourceDatas!=null)
		{
			for (List<String> record : sourceDatas)
			{
				UserDataRelation entity=new UserDataRelation();
				entity.setUid(uid);
				entity.setCs_id(cs_id);
				entity.setDataId(record.get(0));

				userDataRelationList.add(entity);
			}
		}

		Map<String, Object> map = new HashMap();

		try
		{
			userDataService.insertBatch(userDataRelationList);
		}
		catch(Exception e)
		{
			map.put("result", true);
			map.put("message", "添加失败,错误信息为: "+e.getMessage());
			return map;
		}

		map.put("result", true);
		map.put("message", "添加成功,数量为; "+userDataRelationList.size());

		return map;
	}

	/**
	 * 添加到“我的”
	 * 
	 * @param request
	 * @param cs_id
	 * @param sourceDataIds
	 * @return
	 */
	@RequestMapping("/addMySource")
	@ResponseBody
	public Map<String, Object> addMySource(HttpServletRequest request, String cs_id, String sourceDataIds, String ids,
			boolean isAll) {
		User user = (User) request.getAttribute("user");
		Integer uid = user.getId();
		List<String> old = userDataService.selects(uid, Integer.valueOf(cs_id));
		boolean b = true;
		if (isAll == true) {
			String idss = ids.substring(1, ids.length()).replaceAll("check", "");
			String[] ids1 = idss.split(",");
			for (int i = 0; i < ids1.length; i++) {
				sourceDataIds = sourceDataIds.replaceAll(ids1[i] + ",", "");
			}
			for (String sourceDataId : sourceDataIds.split(",")) {
				if ((!old.contains(sourceDataId)) && (!sourceDataId.startsWith(uid + "_" + cs_id + "_"))) {

					if (userDataService.insert(uid, sourceDataId, Integer.valueOf(cs_id)) != 1) {
						b = false;
					}
				}
			}

			Map<String, Object> map = new HashMap<String, Object>();

			if (b) {
				map.put("result", true);
				map.put("message", "添加成功");
			} else {
				map.put("result", false);
				map.put("message", "添加失败");
			}
			return map;

		} else {
			if (ids.startsWith(",")) {
				ids = ids.substring(1, ids.length()).replaceAll("check", "");
			}
			for (String sourceDataId : ids.split(",")) {
				if ((!old.contains(sourceDataId)) && (!sourceDataId.startsWith(uid + "_" + cs_id + "_"))) {

					if (userDataService.insert(uid, sourceDataId, Integer.valueOf(cs_id)) != 1) {
						b = false;
					}
				}
			}

			Map<String, Object> map = new HashMap<String, Object>();

			if (b) {
				map.put("result", true);
				map.put("message", "添加成功");
			} else {
				map.put("result", false);
				map.put("message", "添加失败");
			}
			return map;
		}

	}

	/**
	 * 移出
	 * 
	 * @param request
	 * @param cs_id
	 * @param ids
	 * @return
	 */
	@RequestMapping("/removeSourceDatas")
	@ResponseBody
	public Map<String, Object> removeSourceDatas(HttpServletRequest request,String type,String cs_id, String ids, boolean isAll,
    		String searchId, String searchWord,String desc_asc,String oldCondition,
            String searchFirstWord,String chooseDatas,String likeSearch) {
		//logger.info("SourceDataController-removeSourceDatas: "+ids);
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) request.getAttribute("user");
		Integer uid = user.getId();
		boolean b = true;
		List<String> old = userDataService.selects(uid, Integer.valueOf(cs_id));
		if (isAll == true) {
			
			String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;
			Integer csId = cs_id.equals("")?null:Integer.valueOf(cs_id);
			Integer searchIdInt = searchId.equals("")?null:Integer.valueOf(searchId);
			SourceDataSQLInfo sourceDataSQLInfo=getSourceDataSQL(csId,user,type,searchFirstWord,oldCondition,null,null,searchIdInt,chooseDatas,likeSearch,searchWord,true,ids);

			Map<String, Map<String, Object>> result = PhoenixClient.select(sourceDataSQLInfo.getSql());

			List<List<String>> sourceDatas = null;
			String resultMsg;
			for (int j = 0; j < 6; j++) {
				resultMsg = String.valueOf((result.get("msg")).get("msg"));
				if (resultMsg.equals("success")) {
					sourceDatas = (List<List<String>>) result.get("records").get("data");
					break;
				} else {
					PhoenixClient.undefined(resultMsg, tableName, sourceDataSQLInfo.getQualifiers(), sourceDataSQLInfo.getConditionEqual(), sourceDataSQLInfo.getConditionLike());
					result = PhoenixClient.select(sourceDataSQLInfo.getSql());
				}
			}
            
			String idsStr = "";
			if(sourceDatas!=null)
			{
				for (List<String> record : sourceDatas)
				{
					String idTemp = record.get(0);
					idsStr=idsStr+idTemp+",";
				}
				idsStr = idsStr.substring(0, idsStr.length()-1);
			}
			for (String sourceDataId : idsStr.split(",")) {
				try {
					if (old.contains(sourceDataId)) {
						if (userDataService.delete(uid, sourceDataId, Integer.valueOf(cs_id)) != 1) {
							b = false;
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
			
			
//			String idss = ids.substring(1, ids.length()).replaceAll("check", "");
//			String[] ids1 = idss.split(",");
//			for (int i = 0; i < ids1.length; i++) {
//				sourceDataIds = sourceDataIds.replaceAll(ids1[i] + ",", "");
//			}
//			for (String sourceDataId : sourceDataIds.split(",")) {
//				try {
//					if (old.contains(sourceDataId)) {
//						if (userDataService.delete(uid, sourceDataId, Integer.valueOf(cs_id)) != 1) {
//							b = false;
//						}
//					}
//				} catch (Exception e) {
//					continue;
//				}
//			}
			if (b) {
				map.put("result", true);
				map.put("message", "移出成功,只能移出公共的");
			} else {
				map.put("result", false);
				map.put("message", "移出失败");
			}
			return map;
		} else {
			if (ids.startsWith(",")) {
				ids = ids.substring(1, ids.length()).replaceAll("check", "");
			}
			for (String sourceDataId : ids.split(",")) {
				try {
					if (old.contains(sourceDataId)) {
						if (userDataService.delete(uid, sourceDataId, Integer.valueOf(cs_id)) != 1) {
							b = false;
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
			if (b) {
				map.put("result", true);
				map.put("message", "移出成功,只能移出公共的");
			} else {
				map.put("result", false);
				map.put("message", "移出失败");
			}
			return map;
		}
	}

	@RequestMapping("/upData")
	@ResponseBody
	public Map<String, Object> updateFormatData(String cs_id, String ids, String sourceFieldDatas) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean b = false;
		if (ids.startsWith(",")) {
			ids = ids.substring(1, ids.length()).replaceAll("check", "");
		}

		if (ids == null) {
			b = HBaseSourceDataDao.updateDatas(cs_id, ids,
					new Gson().fromJson(sourceFieldDatas, new TypeToken<Map<String, String>>() {
					}.getType()));
		} else {
			b = HBaseSourceDataDao.updateData(cs_id, ids,
					new Gson().fromJson(sourceFieldDatas, new TypeToken<Map<String, String>>() {
					}.getType()));
		}
		if (b) {
			map.put("result", true);
			map.put("message", "更新成功");
		} else {
			map.put("result", false);
			map.put("message", "更新失败");
		}
		return map;
	}

}
