package com.xtkong.controller.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xtkong.controller.common.sqlparser.Select;
import com.xtkong.model.SourceField;
import org.apache.hadoop.hbase.client.Scan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dzjin.service.ProjectService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.xtkong.dao.hbase.HBaseFormatDataDao;
import com.xtkong.dao.hbase.HBaseFormatNodeDao;
import com.xtkong.dao.hbase.HBaseSourceDataDao;
import com.xtkong.model.FormatField;
import com.xtkong.model.FormatType;
import com.xtkong.model.Source;
import com.xtkong.service.FormatFieldService;
import com.xtkong.service.FormatTypeService;
import com.xtkong.service.PhoenixClient;
import com.xtkong.service.SourceFieldService;
import com.xtkong.service.SourceService;
import com.xtkong.util.ConstantsHBase;
import com.xtkong.util.HBaseDB;

@Controller
@RequestMapping("/common")
public class CommonSelect {

	@Autowired
	SourceService sourceService;
	@Autowired
	SourceFieldService sourceFieldService;
	@Autowired
	FormatTypeService formatTypeService;
	@Autowired
	FormatFieldService formatFieldService;
	@Autowired
	ProjectService projectService;

	@RequestMapping("/selectCondition")
	@ResponseBody
	public Map<String, Object> selectCondition(String cs_id, String sourceDataIds) {
		String selectContdation = "";
		if (sourceDataIds != null) {
			for (String sourceDataId : sourceDataIds.split(",")) {
				selectContdation += ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id + ".id='" + sourceDataId + "' OR ";
			}
			selectContdation = selectContdation.substring(0, selectContdation.lastIndexOf("OR"));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", selectContdation);
		return map;
	}

	private Select getSQLAfterReplace(String sql) throws Exception
	{
		Map<String,String> colMapping=new HashMap<>();

		Select select=new Select(sql);
		//只有一级表
		if(select.getFrom().getSchema()==null || select.getFrom().getSchema().trim().equals(""))
		{
			Source source=sourceService.getSourceByCs_Name(select.getFrom().getTableName());
			if(source==null)
			{
				throw new Exception("Source不存在: "+select.getFrom().getTableName());
			}
			for(SourceField sf : sourceFieldService.getSourceFields(source.getCs_id()))
			{
				colMapping.put(sf.getCsf_name(),"\""+ConstantsHBase.FAMILY_INFO+"\".\""+sf.getCsf_id()+"\"");
				//TODO 还有一些额外的字段
			}
			//替换后表名
			String newFrom="\""+ConstantsHBase.TABLE_PREFIX_SOURCE_+source.getCs_id()+"\"";
			select.replace(newFrom,colMapping);

			//return select.getNewSQL();
		}
		//有二级表,schema是SourceName,table是FormatType
		else
		{
			Source source=sourceService.getSourceByCs_Name(select.getFrom().getSchema());
			if(source==null)
			{
				throw new Exception("Source不存在: "+select.getFrom().getSchema());
			}
			FormatType formatType=formatTypeService.getFormatType(source.getCs_id(),select.getFrom().getTableName());
			if(formatType==null)
			{
				throw new Exception("Source "+source.getCs_name()+" 下 FormatType不存在: "+select.getFrom().getTableName());
			}

			for(FormatField ff : formatFieldService.getFormatFieldsByFormatTypeID(formatType.getFt_id()))
			{
				colMapping.put(ff.getFf_name(),"\""+ConstantsHBase.FAMILY_INFO+"\".\""+ff.getFf_id()+"\"");
				//TODO 还有一些额外的字段
			}
			//替换后表名
			String newFrom="\""+ConstantsHBase.TABLE_PREFIX_FORMAT_+source.getCs_id()+"_"+formatType.getFt_id()+"\"";
			select.replace(newFrom,colMapping);

			//return select.getNewSQL();
		}

		return select;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/commonSelectJson")
	@ResponseBody
	public String commonSelect(String jsonStr) {
		
		String s = jsonStr.replaceAll("%20", "");
		List<String> userid = null;
		List<String> projectid = null;
		String condition = null;
		//Boolean isAddWhere = false;
		Map<String, String> conditionEqual = null;
		Map<String, String> conditionLike = null;
		Integer currPage = null;
		Integer pageSize = null;
		String selectContdation = null;

		Map<String, Object> msg = new HashMap<>();
		try {
			Map<String, Object> gsonMap = new Gson().fromJson(s, new TypeToken<Map<String, Object>>() {
			}.getType());
			if (gsonMap.containsKey("userid")) {
				userid = (List<String>) gsonMap.get("userid");
			}
			if (gsonMap.containsKey("projectid")) {
				projectid = (List<String>) gsonMap.get("projectid");
			}
			if (gsonMap.containsKey("select")) {
				Map<String, String> select = (Map<String, String>) gsonMap.get("select");
				if (select.containsKey("condition")) {
					condition = select.get("condition");
				}
			} else {
				msg.put("msg", "查询语句缺失！");
				return new Gson().toJson(msg).toString();
			}
//			if (gsonMap.containsKey("isAddWhere")) {
//				isAddWhere = (Boolean) gsonMap.get("isAddWhere");
//			}
			if (gsonMap.containsKey("conditionEqual")) {
				conditionEqual = (Map<String, String>) gsonMap.get("conditionEqual");
			}
			if (gsonMap.containsKey("conditionLike")) {
				conditionLike = (Map<String, String>) gsonMap.get("conditionLike");
			}
			if (gsonMap.containsKey("page")) {
				Map<String, Double> page = (Map<String, Double>) gsonMap.get("page");
				if (page.containsKey("currPage")) {
					currPage = page.get("currPage").intValue();
				}
				if (page.containsKey("pageSize")) {
					pageSize = page.get("pageSize").intValue();
				}
			}
			if (gsonMap.containsKey("selectContdation")) {
				selectContdation = gsonMap.get("selectContdation").toString();
			}
			Select selectAfterParse=getSQLAfterReplace(condition);
			if (conditionEqual != null && conditionLike != null) {
				return commonSelect(userid, projectid, selectAfterParse, /*isAddWhere, */ conditionEqual, conditionLike, currPage,
						pageSize);
			} else {
				return commonSelect(userid, projectid, selectAfterParse, /*isAddWhere,*/  selectContdation, currPage, pageSize);
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			msg.put("msg", "Json解析异常，请核对Json格式！  Json:" + jsonStr + " 提示：" + e.getMessage());
			return new Gson().toJson(msg).toString();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			msg.put("msg", "异常:  Json:" + jsonStr + " 提示：" + e.getMessage());
			return new Gson().toJson(msg).toString();
		}
	}

	@RequestMapping("/commonSelect1")
	@ResponseBody
	public String commonSelect(List<String> userid, List<String> projectid, Select select, /*Boolean isAddWhere,*/
							   String selectContdation, Integer currPage, Integer pageSize) {
//		if (isAddWhere == null) {
//			isAddWhere = false;
//		}
//		if (isAddWhere) {
//			select += " WHERE ";
//		} else {
//			select += " AND ";
//		}
		String sqlStr=select.getNewSQL();
		if (select.getWhere()==null) {
			sqlStr += " WHERE ";
		} else {
			sqlStr += " AND ";
		}
		if (userid != null && !userid.isEmpty()) {
			sqlStr += "(";
			for (String string : userid) {
				sqlStr += " \"" + ConstantsHBase.QUALIFIER_USER + "\"= '" + string + "' OR ";
			}
			sqlStr = sqlStr.substring(0, sqlStr.lastIndexOf("OR")) + ") AND ";
		}
		if (projectid != null && !projectid.isEmpty()) {
			sqlStr += " (";
			for (String pid : projectid) {
				sqlStr += " \"" + ConstantsHBase.QUALIFIER_PROJECT + "\"= '" + pid + "' OR ";
			}
			sqlStr = sqlStr.substring(0, sqlStr.lastIndexOf("OR")) + ") AND ";
		}
		if (selectContdation != null) {
			sqlStr += " " + selectContdation + " ";
		}
		if (sqlStr.trim().endsWith("WHERE")) {
			sqlStr = sqlStr.substring(0, sqlStr.lastIndexOf("WHERE"));
		}
		if (sqlStr.trim().endsWith("AND")) {
			sqlStr = sqlStr.substring(0, sqlStr.lastIndexOf("AND"));
		}
		if (currPage == null) {
			currPage = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		return new Gson().toJson(PhoenixClient.commonSelect(sqlStr, select.getFromStartIndex(),currPage, pageSize)).toString();
	}

	/**
	 * 
	 * @param userid
	 * @param projectid
	 * @param select
	 * @param conditionEqual
	 * @param conditionLike
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/commonSelect")
	@ResponseBody
	public String commonSelect(List<String> userid, List<String> projectid, Select select, /*boolean isAddWhere,*/
			Map<String, String> conditionEqual, Map<String, String> conditionLike, Integer currPage, Integer pageSize) {

		boolean and = false;
//		if (isAddWhere) {
//			select += " WHERE ";
//			isAddWhere = false;
//		} else {
//			select += " AND ";
//			and = true;
//		}
		String sqlStr=select.getNewSQL();
		if (select.getWhere()==null) {
			sqlStr += " WHERE ";
		} else {
			sqlStr += " AND ";
		}

		if (userid != null && !userid.isEmpty()) {
			sqlStr += "(";
			for (String string : userid) {
				sqlStr += " \"" + ConstantsHBase.QUALIFIER_USER + "\"= '" + string + "' OR";
			}
			sqlStr = sqlStr.substring(0, sqlStr.lastIndexOf("OR")) + ") AND ";
			and = true;
		}
		if (projectid != null && !projectid.isEmpty()) {
			sqlStr += " (";
			for (String string : projectid) {
				sqlStr += " \"" + ConstantsHBase.QUALIFIER_PROJECT + "\"= '" + string + "' OR";
			}
			sqlStr = sqlStr.substring(0, sqlStr.lastIndexOf("OR")) + ") AND ";
			and = true;
		}
		if ((conditionEqual != null) && (!conditionEqual.isEmpty())) {
			for (Entry<String, String> eqlual : conditionEqual.entrySet()) {
				sqlStr += eqlual.getKey() + "='" + eqlual.getValue() + "' AND ";
				and = true;
			}
		}
		if ((conditionLike != null) && (!conditionLike.isEmpty())) {
			for (Entry<String, String> like : conditionLike.entrySet()) {
				sqlStr += like.getKey() + " like '%" + like.getValue() + "%' AND ";
				and = true;
			}
		}
		if (and) {
			sqlStr = sqlStr.substring(0, sqlStr.lastIndexOf("AND"));
		}
		if (currPage == null) {
			currPage = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		return new Gson()
				.toJson(PhoenixClient.commonSelect(sqlStr,select.getFromStartIndex(), Integer.valueOf(currPage), Integer.valueOf(pageSize)))
				.toString();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		CommonSelect commonSl = new CommonSelect();
		List<String> userid = new ArrayList<>();
		// userid.add("45");
		// userid.add("5");
		List<String> projectid = new ArrayList<>();
		// projectid.add("94");
		String select = null;
		select = "SELECT SOURCE_62.PROJECT,SOURCE_62.user,SOURCE_62.\"89\",SOURCE_62.\"90\",SOURCE_62.\"91\","
				+ "SOURCE_62.\"92\",SOURCE_62.\"93\" FROM \"SOURCE_62\" WHERE (		 \"USER\"= '45' OR \"USER\"= '1' ) AND"
				+ " ( \"PROJECT\"= '94' OR \"PROJECT\"= '114' ) AND "
				+ "INFO.\"92\"='value92' AND INFO.\"93\"='value93' AND " + "INFO.\"90\" like '%value90%' " + " LIMIT "
				+ 20 + " OFFSET " + 20 * (1 - 1);
		select = "SELECT * FROM \"FORMAT_62_45\"";
		boolean isAddWhere = false;
		Map<String, String> conditionEqual = new HashMap<>();
		// conditionEqual.put("INFO.PROJECT", "114");
		Map<String, String> conditionLike = new HashMap<>();
		// conditionLike.put("\"89\"", "e");
		String currPage = null;
		String pageSize = null;
		String jsonStr = null;
		String s = null;
		jsonStr = "{\"userid\":[\"45\",\"1\"],\"projectid\":[\"94\",\"114\"],"
				+ "\"select\":\"SELECT SOURCE_62.PROJECT,SOURCE_62.user,SOURCE_62.\"89\","
				+ "SOURCE_62.\"90\",SOURCE_62.\"91\",SOURCE_62.\"92\",SOURCE_62.\"93\","
				+ "FORMAT_62_45.FORMAT_62_45 FROM \"SOURCE_62\" \",\"isAddWhere\":true,"
				+ "\"conditionEqual\":{\"SOURCE_62.\"92\"\":\"value92\",\"SOURCE_62.\"93\"\":\"SOURCE_62.id\"},"
				+ "\"conditionLike\":{\"SOURCE_62.\"90\"\":\"value91\"},\"currPage\":\"1\",\"pageSize\":\"20\"}";
		 s=jsonStr.replaceAll(":", "：").replace("/", "");
		 
		String result = null;
		//
		// result = commonSl.commonSelect(userid, projectid, select, isAddWhere,
		// conditionEqual, conditionLike, currPage,
		// pageSize);

		result = commonSl.commonSelect(s);
		System.out.println("\n" + result + "\n");
	}

	/**
	 * @param table
	 *            [{ 表类型,采集源,格式类型},{}]
	 * @param tableCol
	 *            [{表,列1,列2},{}]
	 */
	@RequestMapping("/common1")
	@ResponseBody
	public String commonSelect(List<List<String>> table, List<List<String>> tableCol) {
		for (List<String> list : table) {
			switch (list.get(0)) {
			case "source":

				break;
			case "type":
				break;
			default:
				return "";
			}
		}
		return null;
	}

	@RequestMapping("/commonHBaseGetRowkeys")
	@ResponseBody
	public List<String> commonHBaseGetRowkeys(String tableName, String prefixFilter,
			Map<String, String> columnValueFilters) {
		return HBaseDB.getInstance().getRowkeys(tableName, prefixFilter, columnValueFilters);

	}

	/**
	 * 
	 * @param tableName
	 * @param scan
	 * @param qualifiers
	 * @return
	 */
	@RequestMapping("/commonHBaseScan")
	@ResponseBody
	public List<List<String>> commonHBaseScan(String tableName, Scan scan, List<String> qualifiers) {

		HBaseDB.getInstance();
		return HBaseDB.getQuelifierValues(tableName, scan, qualifiers);
	}

	/**
	 * sources 采集源列表
	 * 
	 * source 选中采集源的字段列表
	 * 
	 * sourceDatas 源数据字数据，注：每个列表第一个值sourceDataId不显示
	 *
	 * @param type
	 *            "1":我的 "2":我创建的 "3":公开
	 * @param page
	 * @param strip
	 * @return
	 */
	@RequestMapping("/getSourceDatas")
	@ResponseBody
	public Map<String, Object> getSourceDatas(String uname, String sourceName, String type, Integer page,
			Integer strip) {
		Map<String, Object> map = new HashMap<String, Object>();

		Integer cs_id = sourceService.getSourceId(sourceName);

		List<Source> sources = sourceService.getSourcesForUser();

		// 源数据字段
		List<List<String>> sourceDatas = new ArrayList<>();
		if (sources != null) {
			Source source = sourceService.getSourceByCs_id(cs_id);
			source.setSourceFields(sourceFieldService.getSourceFields(cs_id));

			map.put("sourceFields", source.getSourceFields());
			// 源数据字段数据，注：每个列表第一个值sourceDataId不显示
			switch (type) {
			case "1":
				sourceDatas = HBaseSourceDataDao.getSourceDatasByUid(Integer.toString(cs_id), String.valueOf(uname),
						source.getSourceFields(), null, page, strip, null);
				map.put("sourceDatas", sourceDatas);

			case "2":
				sourceDatas = HBaseSourceDataDao.getSourceDatasCreated(Integer.toString(cs_id), String.valueOf(uname),
						source.getSourceFields(), page, strip);
				map.put("sourceDatas", sourceDatas);
			case "3":
				sourceDatas = HBaseSourceDataDao.getSourceDatasPublic(Integer.toString(cs_id), source.getSourceFields(),
						page, strip);
				map.put("sourceDatas", sourceDatas);
			}
		}
		return map;

	}

	/**
	 * 通过sourceDataId获取一条源数据
	 *
	 * @param cs_id
	 * @param sourceDataId
	 * @return
	 */
	@RequestMapping("/getSourceDataById")
	@ResponseBody
	public Map<String, Object> getSourceDataById(String cs_id, String sourceDataId, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		Source source = sourceService.getSourceByCs_id(Integer.valueOf(cs_id));
		source.setSourceFields(sourceFieldService.getSourceFields(Integer.valueOf(cs_id)));

		map.put("source", source);// 采集源字段列表

		List<String> sourceData = HBaseSourceDataDao.getSourceDataById(cs_id, sourceDataId, source.getSourceFields());
		map.put("sourceData", sourceData);

		HashMap<String, FormatType> formatTypeMap = new HashMap<>();
		List<FormatType> formatTypes = formatTypeService.getFormatTypes(Integer.valueOf(cs_id));
		for (FormatType formatType : formatTypes) {
			formatTypeMap.put(String.valueOf(formatType.getFt_id()), formatType);
		}
		List<FormatType> formatTypeFolders = HBaseFormatNodeDao.getFormatTypeFolders(cs_id, sourceDataId,
				formatTypeMap);
		map.put("formatTypeFolders", formatTypeFolders);
		return map;
	}

	/**
	 * * 获取节点树 List<FormatType>
	 * 
	 * formatType.ft_id： 不显示
	 * 
	 * formatType.ft_name：显示
	 * 
	 * formatType.List<FormatDataNode>:formatNodeId（节点id）： 不显示,节点名： 显示
	 * 
	 * @param cs_id
	 * @param sourceDataId
	 * @return
	 */
	@RequestMapping("/getformatTypeFolders")
	@ResponseBody
	public Map<String, Object> getformatTypeFolders(String cs_id, String sourceDataId) {
		Map<String, Object> map = new HashMap<String, Object>();
		HashMap<String, FormatType> formatTypeMap = new HashMap<>();
		List<FormatType> formatTypes = formatTypeService.getFormatTypes(Integer.valueOf(cs_id));
		for (FormatType formatType : formatTypes) {
			formatTypeMap.put(String.valueOf(formatType.getFt_id()), formatType);
		}
		List<FormatType> formatTypeFolders = HBaseFormatNodeDao.getFormatTypeFolders(cs_id, sourceDataId,
				formatTypeMap);
		map.put("formatTypeFloders", formatTypeFolders);
		return map;
	}

	/**
	 * 通过formatNodeId获取一个数据节点
	 *
	 * @param cs_id
	 * @param sourceDataId
	 * @param ft_id
	 * @param formatNodeId
	 * @param type
	 * @return
	 */
	@RequestMapping("/getFormatNodeById")
	@ResponseBody
	public Map<String, Object> getFormatNodeById(String cs_id, String sourceDataId, String ft_id, String formatNodeId,
			String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		HashMap<String, FormatType> formatTypeMap = new HashMap<>();
		List<FormatType> formatTypes = formatTypeService.getFormatTypes(Integer.valueOf(cs_id));
		for (FormatType formatType : formatTypes) {
			formatTypeMap.put(String.valueOf(formatType.getFt_id()), formatType);
		}
		List<FormatType> formatTypeFolders = HBaseFormatNodeDao.getFormatTypeFolders(cs_id, sourceDataId,
				formatTypeMap);
		map.put("formatTypeFolders", formatTypeFolders);

		// meta数据
		List<FormatField> meta = formatFieldService.getFormatFieldsIs_meta(Integer.valueOf(ft_id),
				ConstantsHBase.IS_meta_true);
		map.put("formatNodeId", formatNodeId);
		List<List<String>> metaDatas = HBaseFormatDataDao.getFormatDataMetas(cs_id, ft_id, formatNodeId, meta);
		map.put("metaDatas", metaDatas);
		// data数据
		List<FormatField> data = formatFieldService.getFormatFieldsIs_meta(Integer.valueOf(ft_id),
				ConstantsHBase.IS_meta_false);
		map.put("data", data);
		List<List<String>> dataDatas = HBaseFormatDataDao.getFormatDatas(cs_id, ft_id, formatNodeId, data);
		map.put("dataDatas", dataDatas);
		map.put("sourceDataId", sourceDataId);
		return map;

	}

	/**
	 * * 获取格式数据明细 List<List<String>>
	 * 
	 * 格式数据明细数据，注：每个列表第一个值formatDataId不显示
	 * 
	 * @param cs_id
	 *            采集源id
	 * @param ft_id
	 *            格式类型id
	 * @param formatNodeId
	 *            节点id
	 * @return
	 */
	@RequestMapping("/getformatDatas")
	@ResponseBody
	public Map<String, Object> getformatDatas(Integer cs_id, Integer ft_id, String formatNodeId, Integer page,
			Integer strip) {
		Map<String, Object> map = new HashMap<String, Object>();
		// meta数据
		List<FormatField> meta = formatFieldService.getFormatFieldsIs_meta(ft_id, ConstantsHBase.IS_meta_true);
		List<List<String>> metaDatas = HBaseFormatDataDao.getFormatDatas(Integer.toString(cs_id),
				Integer.toString(ft_id), formatNodeId, meta);
		// data数据
		List<FormatField> data = formatFieldService.getFormatFieldsIs_meta(ft_id, ConstantsHBase.IS_meta_false);
		List<List<String>> dataDatas = HBaseFormatDataDao.getFormatDatas(Integer.toString(cs_id),
				Integer.toString(ft_id), formatNodeId, data, page, strip);
		map.put("meta", meta);
		map.put("metaDatas", metaDatas);
		map.put("data", data);
		map.put("dataDatas", dataDatas);
		return map;
	}

}
