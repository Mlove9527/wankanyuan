package com.xtkong.controller.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xtkong.dao.hbase.HBaseFormatDataDao;
import com.xtkong.dao.hbase.HBaseFormatNodeDao;
import com.xtkong.dao.hbase.HBaseSourceDataDao;
import com.xtkong.model.Analysis;
import com.xtkong.model.ImportBean;
import com.xtkong.service.FormatFieldService;
import com.xtkong.service.FormatTypeService;
import com.xtkong.service.SourceFieldService;
import com.xtkong.service.SourceService;

@Controller
@RequestMapping("/common")
public class ImportAdminController {
	@Autowired
	SourceService sourceService;
	@Autowired
	SourceFieldService sourceFieldService;
	@Autowired
	FormatTypeService formatTypeService;
	@Autowired
	FormatFieldService formatFieldService;

	@RequestMapping(value = "/import")
	@ResponseBody
	public Map<String, Object> test(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();

		ImportBean importBean = null;
		try {
			importBean = new ImportBean(jsonStr);
		} catch (Exception e) {
			map.put("result", "格式错误！");
			return map;
		}

		if (importBean != null) {
			if ((importBean.getSourceid() != null) && (importBean.getUserid() != null)) {
				if (importBean.getBasic() != null) {
					map = sourceData(importBean.getSourceid(), importBean.getUserid(),
							importBean.getBasic().getFileurl(), importBean.getBasic().getSrcCol(),
							importBean.getBasic().getDistCol(), importBean.getBasic().getDefUnique());

				}
				if (importBean.getAnalysisList() != null) {
					for (Analysis analysis : importBean.getAnalysisList()) {
						if (analysis.getToBCol() != null) {
							map = formatDataToBCol(importBean.getSourceid(), importBean.getUserid(), analysis.getName(),
									analysis.getNodeName(), analysis.getFileurl(), analysis.getBCol(),
									analysis.getToBCol(), analysis.getSrcMCol(), analysis.getDistMCol(),
									analysis.getSrcDCol(), analysis.getDistDCol(), analysis.getDefUnique());
						} else if (analysis.getToBColValue() != null) {
							map = formatDataToBColValue(importBean.getSourceid(), importBean.getUserid(),
									analysis.getName(), analysis.getNodeName(), analysis.getFileurl(),
									analysis.getBCol(), analysis.getToBColValue(), analysis.getSrcMCol(),
									analysis.getDistMCol(), analysis.getSrcDCol(), analysis.getDistDCol(),
									analysis.getDefUnique());
						}
					}
				}
				if (map.size() == 0) {
					map.put("result", "格式错误！");
				}
				return map;
			}
		}
		map.put("result", "格式错误！");
		return map;
	}

	private Map<String, Object> sourceData(String sourceid, String userid, String fileurl, List<String> srcCol,
			List<String> distCol, List<String> defUnique) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Integer cs_id = sourceService.getSourceId(sourceid);
			Scanner scanner = new Scanner(new FileInputStream(fileurl));
			HashMap<Integer, String> csfIndex_IdMap = new HashMap<>();
			if (scanner.hasNextLine()) {
				int i = 0;
				for (String head : scanner.nextLine().split("\t")) {
					if (srcCol.contains(head)) {
						String csf_Id = null;
						if ((csf_Id = String
								.valueOf(sourceFieldService.getSourceFieldId(cs_id, distCol.get(srcCol.indexOf(head)))))
										.equals("null")) {
							scanner.close();
							map.put("result", "失败");
							return map;
						}
						csfIndex_IdMap.put(i, csf_Id);
					}
					i++;
				}
			}
			while (scanner.hasNextLine()) {
				Map<String, String> sourceFieldDatas = new HashMap<>();
				String[] datas = scanner.nextLine().split("\t");
				for (Entry<Integer, String> sourceFieldId : csfIndex_IdMap.entrySet()) {
					sourceFieldDatas.put(sourceFieldId.getValue(), datas[sourceFieldId.getKey()]);
				}
				if (!sourceFieldDatas.isEmpty()) {
						HBaseSourceDataDao.insertSourceData1(String.valueOf(cs_id), userid, sourceFieldDatas);
				}
			}
			scanner.close();
			map.put("result", "成功");
		} catch (IOException e1) {
			map.put("result", "失败");
		}
		return map;
	}

	private Map<String, Object> formatDataToBCol(String sourceid, String userid, String name, String nodeName,
			String fileurl, List<String> bCol, List<String> toBCol, List<String> srcMCol, List<String> distMCol,
			List<String> srcDCol, List<String> distDCol, List<String> defUnique) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Integer cs_id = sourceService.getSourceId(sourceid);
			Integer ft_id = formatTypeService.getFormatTypeId(cs_id, name);
			Scanner scanner = new Scanner(new FileInputStream(fileurl));
			HashMap<Integer, String> csfIndex_IdMap = new HashMap<>();// 与采集源基础信息关联所用的字段
			HashMap<Integer, String> metaIndex_IdMap = new HashMap<>();// metainfo字段
			HashMap<Integer, String> dataIndex_IdMap = new HashMap<>();// data字段
			if (scanner.hasNextLine()) {
				int i = 0;
				for (String head : scanner.nextLine().split("\t")) {
					if (bCol.contains(head)) {
						String csf_Id = null;
						if ((csf_Id = String
								.valueOf(sourceFieldService.getSourceFieldId(cs_id, toBCol.get(bCol.indexOf(head)))))
										.equals("null")) {
							scanner.close();
							map.put("result", "失败");
							return map;
						}
						csfIndex_IdMap.put(i, csf_Id);
					} else if (srcMCol.contains(head)) {
						String meta_Id = null;
						if ((meta_Id = String.valueOf(
								formatFieldService.getFormatField_ff_id(ft_id, distMCol.get(srcMCol.indexOf(head)))))
										.equals("null")) {
							scanner.close();
							map.put("result", "失败");
							return map;
						}
						metaIndex_IdMap.put(i, meta_Id);
					} else if (srcDCol.contains(head)) {
						String data_Id = null;
						if ((data_Id = String.valueOf(
								formatFieldService.getFormatField_ff_id(ft_id, distDCol.get(srcDCol.indexOf(head)))))
										.equals("null")) {
							scanner.close();
							map.put("result", "失败");
							return map;
						}
						dataIndex_IdMap.put(i, data_Id);
					}
					i++;
				}
			}
			Map<Map<String, String>, String> sourceDataIds = new HashMap<>();
			Map<String, String> formatNodeIds = new HashMap<>();
			while (scanner.hasNextLine()) {
				String[] datas = scanner.nextLine().split("\t");
				String sourceDataId = null;
				Map<String, String> sourceFieldDatas = new HashMap<>();
				Map<String, String> metaDatas = new HashMap<>();
				Map<String, String> dataDatas = new HashMap<>();

				for (Entry<Integer, String> csfId : csfIndex_IdMap.entrySet()) {
					String csf_value = datas[csfId.getKey()];
					sourceFieldDatas.put(csfId.getValue(), csf_value);
				}

				if (sourceDataIds.containsKey(sourceFieldDatas)) {// 记录id，减少数据库查询
					sourceDataId = sourceDataIds.get(sourceFieldDatas);
				} else {
					sourceDataId = HBaseSourceDataDao.getSourceDataId(String.valueOf(userid), String.valueOf(cs_id),
							sourceFieldDatas);
					sourceDataIds.put(sourceFieldDatas, sourceDataId);
				}

				for (Entry<Integer, String> metaId : metaIndex_IdMap.entrySet()) {
					metaDatas.put(metaId.getValue(), datas[metaId.getKey()]);
				}
				for (Entry<Integer, String> formatFieldId : dataIndex_IdMap.entrySet()) {
					dataDatas.put(formatFieldId.getValue(), datas[formatFieldId.getKey()]);
				}

				String formatNodeId = null;
				if (formatNodeIds.containsKey(sourceDataId)) {// 记录id，减少数据库查询
					formatNodeId = formatNodeIds.get(sourceDataId);
				} else {
					formatNodeId = HBaseFormatNodeDao.getFormatNodeId(String.valueOf(cs_id), sourceDataId,
							String.valueOf(ft_id), nodeName);
					if (formatNodeId == null) {
						HBaseFormatNodeDao.insertFormatNode(String.valueOf(cs_id), sourceDataId, String.valueOf(ft_id),
								nodeName, metaDatas);
						formatNodeId = HBaseFormatNodeDao.getFormatNodeId(String.valueOf(cs_id), sourceDataId,
								String.valueOf(ft_id), nodeName);
					}
					formatNodeIds.put(sourceDataId, formatNodeId);
				}

				if (!metaDatas.isEmpty()) {
						HBaseFormatDataDao.updateFormatData(String.valueOf(cs_id), String.valueOf(ft_id), formatNodeId,
								metaDatas);
				}
				if (!dataDatas.isEmpty()) {
						HBaseFormatDataDao.insertFormatData(String.valueOf(cs_id), String.valueOf(ft_id), sourceDataId,
								formatNodeId, dataDatas);
				}
			}

			scanner.close();
			map.put("result", "成功");
		} catch (IOException e1) {
			map.put("result", "失败");
			return map;
		}
		return map;
	}

	private Map<String, Object> formatDataToBColValue(String sourceid, String userid, String name, String nodeName,
			String fileurl, List<String> toBCol, List<String> toBColValue, List<String> srcMCol, List<String> distMCol,
			List<String> srcDCol, List<String> distDCol, List<String> defUnique) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Integer cs_id = sourceService.getSourceId(sourceid);
			Integer ft_id = formatTypeService.getFormatTypeId(cs_id, name);
			Map<String, String> sourceFieldDatas = new HashMap<>();
			for (int i = 0; i < toBCol.size(); i++) {
				String field = null;
				if ((String.valueOf(sourceFieldService.getSourceFieldId(cs_id, toBCol.get(i)))).equals("null")) {
					map.put("result", "失败");
					return map;
				}
				sourceFieldDatas.put(field, toBColValue.get(i));
			}
			Scanner scanner = new Scanner(new FileInputStream(fileurl));
			String sourceDataId = HBaseSourceDataDao.getSourceDataId(String.valueOf(userid), String.valueOf(cs_id),
					sourceFieldDatas);
			if ((sourceDataId != null) && (!sourceDataId.isEmpty())) {

				HashMap<Integer, String> metaIndex_IdMap = new HashMap<>();// metainfo字段
				HashMap<Integer, String> dataIndex_IdMap = new HashMap<>();// data字段
				if (scanner.hasNextLine()) {
					int i = 0;
					for (String head : scanner.nextLine().split("\t")) {
						if (srcMCol.contains(head)) {
							String meta_Id = null;
							if ((meta_Id = String.valueOf(formatFieldService.getFormatField_ff_id(ft_id,
									distMCol.get(srcMCol.indexOf(head))))).equals("null")) {
								scanner.close();
								map.put("result", "失败");
								return map;
							}
							metaIndex_IdMap.put(i, meta_Id);
						} else if (srcDCol.contains(head)) {
							String data_Id = null;
							if ((data_Id = String.valueOf(formatFieldService.getFormatField_ff_id(ft_id,
									distDCol.get(srcDCol.indexOf(head))))).equals("null")) {
								scanner.close();
								map.put("result", "失败");
								return map;
							}
							dataIndex_IdMap.put(i, data_Id);
						}
						i++;
					}
				}
				Map<String, String> formatNodeIds = new HashMap<>();
				while (scanner.hasNextLine()) {
					String[] datas = scanner.nextLine().split("\t");
					Map<String, String> metaDatas = new HashMap<>();
					Map<String, String> dataDatas = new HashMap<>();

					for (Entry<Integer, String> metaId : metaIndex_IdMap.entrySet()) {
						metaDatas.put(metaId.getValue(), datas[metaId.getKey()]);
					}
					for (Entry<Integer, String> formatFieldId : dataIndex_IdMap.entrySet()) {
						dataDatas.put(formatFieldId.getValue(), datas[formatFieldId.getKey()]);
					}
					String formatNodeId = null;
					if (formatNodeIds.containsKey(sourceDataId)) {// 记录id，减少数据库查询
						formatNodeId = formatNodeIds.get(sourceDataId);
					} else {
						formatNodeId = HBaseFormatNodeDao.getFormatNodeId(String.valueOf(cs_id), sourceDataId,
								String.valueOf(ft_id), nodeName);
						if (formatNodeId == null) {
							HBaseFormatNodeDao.insertFormatNode(String.valueOf(cs_id), sourceDataId,
									String.valueOf(ft_id), nodeName, metaDatas);
							formatNodeId = HBaseFormatNodeDao.getFormatNodeId(String.valueOf(cs_id), sourceDataId,
									String.valueOf(ft_id), nodeName);
						}
						formatNodeIds.put(sourceDataId, formatNodeId);
					}
					if (!metaDatas.isEmpty()) {
							HBaseFormatDataDao.updateFormatData(String.valueOf(cs_id), String.valueOf(ft_id),
									formatNodeId, metaDatas);
					}
					if (!dataDatas.isEmpty()) {
							HBaseFormatDataDao.insertFormatData(String.valueOf(cs_id), String.valueOf(ft_id),
									sourceDataId, formatNodeId, dataDatas);
					}
				}
			} else {
				map.put("result", "失败");
				scanner.close();
				return map;
			}
			scanner.close();
			map.put("result", "成功");
		} catch (IOException e1) {
			map.put("result", "失败");
			return map;
		}
		return map;
	}

	@RequestMapping(value = "/sourceData")
	@ResponseBody
	public Map<String, Object> sourceData(String uname, String sourceName, String fieldMap, String filePath,
			String charsetName) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Integer cs_id = sourceService.getSourceId(sourceName);
			Map<String, String> csFieldMap = new Gson().fromJson(fieldMap, new TypeToken<Map<String, String>>() {
			}.getType());

			Scanner scanner = null;
			try {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), charsetName);
				scanner = new Scanner(isr);
			} catch (Exception e) {
				scanner = new Scanner(new FileInputStream(filePath));
			}
			HashMap<Integer, String> sourceFieldIdMap = new HashMap<>();
			if (scanner.hasNextLine()) {
				int i = 0;
				for (String head : scanner.nextLine().split("\t")) {
					if (csFieldMap.containsKey(head)) {
						sourceFieldIdMap.put(i,
								String.valueOf(sourceFieldService.getSourceFieldId(cs_id, csFieldMap.get(head))));
					}
					i++;
				}
			}
			Map<String, String> sourceFieldDatas = new HashMap<>();
			while (scanner.hasNextLine()) {
				String[] datas = scanner.nextLine().split("\t");
				for (Entry<Integer, String> sourceFieldId : sourceFieldIdMap.entrySet()) {
					sourceFieldDatas.put(sourceFieldId.getValue(), datas[sourceFieldId.getKey()]);
				}
				HBaseSourceDataDao.insertSourceData1(String.valueOf(cs_id), uname, sourceFieldDatas);
			}
			scanner.close();
			map.put("message", "成功");
		} catch (IOException e1) {
			map.put("message", "失败");
			return map;
		}
		return map;
	}
	@RequestMapping(value = "/formatData")
	@ResponseBody
	public Map<String, Object> formatData(String uname, String sourceName, String formatType, String sourceMap,
			String nodeMap, String metaFieldMap, String dataFieldMap, String filePath, String charsetName) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Integer cs_id = sourceService.getSourceId(sourceName);
			Integer ft_id = formatTypeService.getFormatTypeId(cs_id, formatType);
			Map<String, String> keyMap = new Gson().fromJson(sourceMap, new TypeToken<Map<String, String>>() {
			}.getType());
			Map<String, String> formatNodeMap = new Gson().fromJson(nodeMap, new TypeToken<Map<String, String>>() {
			}.getType());
			Map<String, String> metaMap = new Gson().fromJson(metaFieldMap, new TypeToken<Map<String, String>>() {
			}.getType());
			Map<String, String> formatFieldMap = new Gson().fromJson(dataFieldMap,
					new TypeToken<Map<String, String>>() {
					}.getType());
			Scanner scanner = null;
			try {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), charsetName);
				scanner = new Scanner(isr);
			} catch (Exception e) {
				scanner = new Scanner(new FileInputStream(filePath));
			}
			HashMap<Integer, String> rowkeyldIdMap = new HashMap<>();
			Integer formatNodeIndex = 0;
			HashMap<Integer, String> metaIdMap = new HashMap<>();
			HashMap<Integer, String> formatFieldIdMap = new HashMap<>();
			if (scanner.hasNextLine()) {
				int i = 0;
				for (String head : scanner.nextLine().split("\t")) {
					if (keyMap.containsKey(head)) {
						rowkeyldIdMap.put(i,
								String.valueOf(sourceFieldService.getSourceFieldId(cs_id, keyMap.get(head))));
					} else if (formatNodeMap.containsKey(head)) {
						formatNodeIndex = i;
					} else if (metaMap.containsKey(head)) {
						metaIdMap.put(i, String
								.valueOf(formatFieldService.getFormatField_ff_id(ft_id, formatFieldMap.get(head))));
					} else if (formatFieldMap.containsKey(head)) {
						formatFieldIdMap.put(i, String
								.valueOf(formatFieldService.getFormatField_ff_id(ft_id, formatFieldMap.get(head))));
					}
					i++;
				}
			}
			// Map<String, String> sourceDataIds = new HashMap<>();
			Map<Map<String, String>, String> sourceDataIds = new HashMap<>();
			Map<String, String> formatNodeIds = new HashMap<>();
			Map<String, String> metaDatas = new HashMap<>();
			Map<String, String> formatFieldDatas = new HashMap<>();
			while (scanner.hasNextLine()) {
				String[] datas = scanner.nextLine().split("\t");
				String sourceDataId = null;
				Map<String, String> sourceFieldDatas = new HashMap<>();
				for (Entry<Integer, String> rowkeyldId : rowkeyldIdMap.entrySet()) {
					String csf_value = datas[rowkeyldId.getKey()];
					sourceFieldDatas.put(rowkeyldId.getValue(), csf_value);
				}
				if (sourceDataIds.containsKey(sourceFieldDatas)) {
					sourceDataId = sourceDataIds.get(sourceFieldDatas);
				} else {
					sourceDataId = HBaseSourceDataDao.getSourceDataId(String.valueOf(uname), String.valueOf(cs_id),
							sourceFieldDatas);
					sourceDataIds.put(sourceFieldDatas, sourceDataId);
				}
				String nodeName = datas[formatNodeIndex];
				String formatNodeId = null;
				String key = sourceDataId + "_" + ft_id + "_" + nodeName;
				if (formatNodeIds.containsKey(key)) {
					formatNodeId = formatNodeIds.get(key);
				} else {
					if (nodeName.equals("")) {
						HBaseFormatNodeDao.insertFormatNode(String.valueOf(cs_id), sourceDataId, String.valueOf(ft_id),
								nodeName, metaDatas);
					}
					formatNodeId = HBaseFormatNodeDao.getFormatNodeId(String.valueOf(cs_id), sourceDataId,
							String.valueOf(ft_id), nodeName);
					formatNodeIds.put(key, formatNodeId);
				}
				for (Entry<Integer, String> metaId : metaIdMap.entrySet()) {
					metaDatas.put(metaId.getValue(), datas[metaId.getKey()]);
				}
				HBaseFormatDataDao.updateFormatData(String.valueOf(cs_id), String.valueOf(ft_id), formatNodeId,
						metaDatas);

				for (Entry<Integer, String> formatFieldId : formatFieldIdMap.entrySet()) {
					formatFieldDatas.put(formatFieldId.getValue(), datas[formatFieldId.getKey()]);
				}

				HBaseFormatDataDao.insertFormatData(String.valueOf(cs_id), String.valueOf(ft_id), sourceDataId,
						formatNodeId, formatFieldDatas);

			}

			scanner.close();
			map.put("message", "成功");
		} catch (IOException e1) {
			map.put("message", "失败");
			return map;
		}

		return map;
	}

}
