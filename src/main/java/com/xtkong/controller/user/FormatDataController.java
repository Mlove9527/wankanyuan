package com.xtkong.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xtkong.dao.hbase.HBaseFormatDataDao;
import com.xtkong.model.FormatField;
import com.xtkong.model.Source;
import com.xtkong.service.FormatFieldService;
import com.xtkong.service.FormatTypeService;
import com.xtkong.service.PhoenixClient;
import com.xtkong.service.SourceFieldService;
import com.xtkong.service.SourceService;
import com.xtkong.util.ConstantsHBase;

@Controller
@RequestMapping("/formatData")
public class FormatDataController {

	@Autowired
	SourceService sourceService;
	@Autowired
	SourceFieldService sourceFieldService;
	@Autowired
	FormatTypeService formatTypeService;
	@Autowired
	FormatFieldService formatFieldService;

	@RequestMapping("/insertFormatData")
	@ResponseBody
	public Map<String, Object> insertFormatData(String cs_id, String ft_id, String sourceDataId, String formatNodeId,
			String formatFieldDatas) {
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, String> fieldDatas = new Gson().fromJson(formatFieldDatas, new TypeToken<Map<String, String>>() {
		}.getType());

		List<FormatField> formatFields = formatFieldService.getFormatFieldsIs_meta(Integer.valueOf(ft_id),
				ConstantsHBase.IS_meta_true);
		List<List<String>> metaDatas = HBaseFormatDataDao.getFormatDataMetas(cs_id, ft_id, formatNodeId, formatFields);
		for (List<String> list : metaDatas) {
			try {
				fieldDatas.put(list.get(0), list.get(2));
			} catch (Exception e) {
				continue;
			}
		}
		if (HBaseFormatDataDao.insertFormatData(cs_id, ft_id, sourceDataId, formatNodeId, fieldDatas) != null) {
			map.put("result", true);
			map.put("message", "新增成功");
		} else {
			map.put("result", false);
			map.put("message", "新增失败");
		}
		return map;
	}

	@RequestMapping("/updateFormatData")
	@ResponseBody
	public Map<String, Object> updateFormatData(String cs_id, String ft_id, String formatNodeId, String formatDataId,
			String formatFieldDatas) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean b = false;
		if (formatDataId == null) {
			b = HBaseFormatDataDao.updateFormatDatas(cs_id, ft_id, formatNodeId,
					new Gson().fromJson(formatFieldDatas, new TypeToken<Map<String, String>>() {
					}.getType()));
		} else {
			b = HBaseFormatDataDao.updateFormatData(cs_id, ft_id, formatDataId,
					new Gson().fromJson(formatFieldDatas, new TypeToken<Map<String, String>>() {
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

	@SuppressWarnings("unchecked")
	@RequestMapping("/getFormatDataById")
	@ResponseBody
	public Map<String, Object> getFormatDataById(Integer cs_id, Integer ft_id, String formatNodeId,
			String formatDataId) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<FormatField> data = formatFieldService.getFormatFieldsIs_meta(Integer.valueOf(ft_id),
				ConstantsHBase.IS_meta_false);
		String tableName = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
		List<String> qualifiers = new ArrayList<>();
		Map<String, String> conditionEqual = new HashMap<>();
		Map<String, String> conditionLike = new HashMap<>();
		String condition = null;

		conditionEqual.put(ConstantsHBase.QUALIFIER_FORMATNODEID, formatNodeId);
		for (FormatField formatField : data) {
			qualifiers.add(String.valueOf(formatField.getFf_id()));
		}
		condition = "ID='" + formatDataId + "'";

		String phoenixSQL = PhoenixClient.getPhoenixSQL(tableName, qualifiers, conditionEqual, conditionLike, condition,
				null, null);
		Map<String, Map<String, Object>> dataDatas = PhoenixClient.select(phoenixSQL);
		List<List<String>> dataDataListTemp = new ArrayList<>();
		List<List<String>> dataDataLists = new ArrayList<>();
		String dataMsg = String.valueOf((dataDatas.get("msg")).get("msg"));
		for (int j = 0; j < 6; j++) {
			dataMsg = String.valueOf((dataDatas.get("msg")).get("msg"));
			if (dataMsg.equals("success")) {
				dataDataLists = (List<List<String>>) dataDatas.get("records").get("data");
				break;
			} else {
				PhoenixClient.undefined(dataMsg, tableName, qualifiers, conditionEqual, conditionLike);
				dataDatas = PhoenixClient.select(phoenixSQL);
			}
		}
		int i = 0;
		for (FormatField formatField : data) {
			List<String> formatData = new ArrayList<>();
			formatData.add(String.valueOf(formatField.getFf_id()));
			formatData.add(formatField.getFf_name());
			try {
				formatData.add(dataDataLists.get(0).get(++i));
			} catch (Exception e) {
				formatData.add("");
			}
			dataDataListTemp.add(formatData);
		}
		map.put("dataData", dataDataListTemp);
		return map;
	}

	@RequestMapping("/getFormatDatas")
	@ResponseBody
	public Map<String, Object> getFormatDatas(Integer cs_id, Integer ft_id, String formatNodeId, Integer page,
			Integer strip) {
		Map<String, Object> map = new HashMap<String, Object>();
		// meta数据
		List<FormatField> meta = formatFieldService.getFormatFieldsIs_meta(ft_id, ConstantsHBase.IS_meta_true);
		// List<List<String>> metaDatas =
		// HBaseFormatDataDao.getFormatDatas(Integer.toString(cs_id),
		// Integer.toString(ft_id), formatNodeId, meta);
		// data数据
		List<FormatField> data = formatFieldService.getFormatFieldsIs_meta(ft_id, ConstantsHBase.IS_meta_false);
		// List<List<String>> dataDatas =
		// HBaseFormatDataDao.getFormatDatas(Integer.toString(cs_id),
		// Integer.toString(ft_id), formatNodeId, data, page, strip);

		String tableName = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
		List<String> mateQualifiers = new ArrayList<>();
		for (FormatField formatField : meta) {
			mateQualifiers.add(String.valueOf(formatField.getFf_id()));
		}
		Map<String, String> conditionEqual = new HashMap<>();
		conditionEqual.put(ConstantsHBase.QUALIFIER_FORMATNODEID, formatNodeId);
		Map<String, String> conditionLike = new HashMap<>();
		String matephoenixSQL = PhoenixClient.getPhoenixSQL(tableName, mateQualifiers, conditionEqual, conditionLike,
				null, 1, 1);
		Map<String, Map<String, Object>> metaDatas = PhoenixClient.select(matephoenixSQL);
		// Map<String, Map<String, Object>> metaDatas =
		// PhoenixClient.select(tableName, family, mateQualifiers,
		// conditionEqual, conditionLike, 1, 1);

		List<String> dataQualifiers = new ArrayList<>();
		for (FormatField formatField : data) {
			dataQualifiers.add(String.valueOf(formatField.getFf_id()));
		}
		String dataphoenixSQL = PhoenixClient.getPhoenixSQL(tableName, dataQualifiers, conditionEqual, conditionLike,
				null, null, null);
		Integer dataCount = PhoenixClient.count(dataphoenixSQL);
		dataphoenixSQL = PhoenixClient.getPhoenixSQL(dataphoenixSQL, null, page, strip);
		Map<String, Map<String, Object>> dataDatas = PhoenixClient.select(dataphoenixSQL);
		// Map<String, Map<String, Object>> dataDatas =
		// PhoenixClient.select(tableName, family, dataQualifiers,
		// conditionEqual, conditionLike, page, strip);

		if (metaDatas != null) {
			map.put("result", true);
			String metaMsg = String.valueOf((metaDatas.get("msg")).get("msg"));
			String dataMsg = String.valueOf((dataDatas.get("msg")).get("msg"));
			for (int j = 0; j < 6; j++) {
				metaMsg = String.valueOf((metaDatas.get("msg")).get("msg"));
				dataMsg = String.valueOf((dataDatas.get("msg")).get("msg"));
				if ((metaMsg.equals("success")) && (dataMsg.equals("success"))) {
					break;
				}
				if (!(metaMsg.equals("success"))) {
					PhoenixClient.undefined(metaMsg, tableName, mateQualifiers, conditionEqual, conditionLike);
					metaDatas = PhoenixClient.select(matephoenixSQL);
					// metaDatas = PhoenixClient.reSelectWhere(metaMsg,
					// tableName, family, mateQualifiers, conditionEqual,
					// conditionLike, 1, 1);
				}
				if (!(dataMsg.equals("success"))) {
					PhoenixClient.undefined(metaMsg, tableName, mateQualifiers, conditionEqual, conditionLike);
					metaDatas = PhoenixClient.select(matephoenixSQL);
					// dataDatas = PhoenixClient.reSelectWhere(dataMsg,
					// tableName, family, dataQualifiers, conditionEqual,
					// conditionLike, page, strip);
				}
			}

		} else {
			map.put("result", false);
			map.put("message", "获取失败");
		}
		map.put("meta", meta);
		map.put("metaDatas", metaDatas.get("records").get("data"));
		map.put("data", data);
		map.put("dataDatas", dataDatas.get("records").get("data"));
		map.put("total", dataCount);
		map.put("page", page);
		map.put("rows", strip);
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/getFieldDatas")
	@ResponseBody
	public Map<String, Object> getFieldDatas(HttpServletRequest request, HttpSession httpSession, String type,
			Integer cs_id, Integer ft_id, String formatNodeId, Integer searchId, String searchWord,
			String odlCond8ition) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (type == null || cs_id == null || ft_id == null || formatNodeId == null || searchId == null) {
			map.put("result", false);
			map.put("message", "查询失败");
			return map;
		}
		Source source = sourceService.getSourceByCs_id(cs_id);
		if (source != null) {
			Map<String, Map<String, Object>> result = new HashMap<>();
			String tableName = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
			List<String> qualifiers = new ArrayList<>();
			Map<String, String> conditionEqual = new HashMap<>();
			Map<String, String> conditionLike = new HashMap<>();
			String condition = null;
			String phoenixSQL = null;

			qualifiers.add(String.valueOf(searchId));
			conditionEqual.put(ConstantsHBase.QUALIFIER_FORMATNODEID, formatNodeId);
			if (searchWord == null) {
				searchWord = "";
			}
			conditionLike.put(String.valueOf(searchId), searchWord);
			String odlCondition=(String) httpSession.getAttribute("oldCondition");
			if (odlCondition == null || odlCondition.trim().isEmpty()) {
				condition = " \"" + tableName + "\".\"ID\"!='" + formatNodeId + "' ";
			} else {
				condition = " \"" + tableName + "\".\"ID\"!='" + formatNodeId + "' AND " + odlCondition;
			}
			// phoenixSQL = PhoenixClient.getPhoenixSQL(tableName, qualifiers,
			// conditionEqual, conditionLike, condition,
			// null, null);
			if (condition == null || condition.trim().isEmpty()) {
				phoenixSQL = "SELECT DISTINCT " + PhoenixClient.getSQLFamilyColumn(String.valueOf(searchId))
						+ " FROM \"" + tableName + "\"  WHERE  "
						+ PhoenixClient.getSQLConditionEquals(tableName, conditionEqual, "AND") + " AND "
						+ PhoenixClient.getSQLConditionLikes(tableName, conditionLike, "AND") + " ORDER BY "
						+ PhoenixClient.getSQLFamilyColumn(String.valueOf(searchId));

			} else {
				phoenixSQL = "SELECT DISTINCT " + PhoenixClient.getSQLFamilyColumn(String.valueOf(searchId))
						+ " FROM \"" + tableName + "\"  WHERE  "
						+ PhoenixClient.getSQLConditionEquals(tableName, conditionEqual, "AND") + " AND "
						+ PhoenixClient.getSQLConditionLikes(tableName, conditionLike, "AND") + " AND " + condition
						+ " ORDER BY " + PhoenixClient.getSQLFamilyColumn(String.valueOf(searchId));
			}
			result = PhoenixClient.select(phoenixSQL);

			List<String> ffDatas = new ArrayList<>();

			String resultMsg = String.valueOf((result.get("msg")).get("msg"));
			for (int j = 0; j < 6; j++) {
				resultMsg = String.valueOf((result.get("msg")).get("msg"));
				if (resultMsg.equals("success")) {
					try {
						for (List<String> datas : (List<List<String>>) result.get("records").get("data")) {
							ffDatas.add(datas.get(0));
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
			map.put("ffDatas", ffDatas);
		} else {
			map.put("result", false);
			map.put("message", "查询失败");
		}
		return map;
	}

	@RequestMapping("/deleteFormatDatas")
	@ResponseBody
	public Map<String, Object> deleteFormatDatas(String cs_id, String ft_id, String formatDataIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (HBaseFormatDataDao.deleteFormatDatas(cs_id, ft_id, formatDataIds)) {
			map.put("result", true);
			map.put("message", "删除成功");
		} else {
			map.put("result", false);
			map.put("message", "删除失败");
		}
		return map;
	}

}
