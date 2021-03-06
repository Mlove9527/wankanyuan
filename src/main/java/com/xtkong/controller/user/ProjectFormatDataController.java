
package com.xtkong.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xtkong.service.ProjectDataService;
import com.xtkong.service.ProjectFormatDataService;
import com.xtkong.service.ProjectNodeDataService;
import com.xtkong.service.ProjectNodeService;
import com.dzjin.model.ProjectCustomRole;
import com.dzjin.service.ProjectCustomRoleService;
import com.liutianjun.pojo.User;
import com.xtkong.controller.user.SourceDataController.SourceDataSQLInfo;
import com.xtkong.dao.hbase.HBaseFormatNodeDao;
import com.xtkong.dao.hbase.HBaseProjectDataDao;
import com.xtkong.dao.hbase.HBaseSourceDataDao;
import com.xtkong.model.FormatDataSQLInfo;
import com.xtkong.model.FormatField1;
import com.xtkong.model.Source;
import com.xtkong.model.SourceField;
import com.xtkong.service.FormatFieldService;
import com.xtkong.service.FormatTypeService;
import com.xtkong.service.PhoenixClient;
import com.xtkong.service.SourceFieldService;
import com.xtkong.service.SourceService;
import com.xtkong.util.ConstantsHBase;

@Controller
@RequestMapping("/projectFormatData")
public class ProjectFormatDataController {
	@Autowired
	SourceService sourceService;
	@Autowired
	SourceFieldService sourceFieldService;
	@Autowired
	FormatTypeService formatTypeService;
	@Autowired
	FormatFieldService formatFieldService;

	@Autowired
	ProjectDataService projectDataService;
	@Autowired
	ProjectNodeService projectNodeService;
	@Autowired
	ProjectNodeDataService projectNodeDataService;
	@Autowired
	ProjectCustomRoleService projectCustomRoleService;
	@Autowired
	SourceDataController sourceDataController;
	@Autowired
	FormatNodeController formatNodeController;
	@Autowired
	ProjectFormatDataService projectFormatDataService;
	
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insert(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			String p_id, String cs_id, String ids, boolean isAll,
    		String searchId, String searchWord,String desc_asc,String oldCondition,
            String searchFirstWord,String chooseDatas,String likeSearch,String type) {
		Integer sum = 0;
		Integer count = 0;
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<SourceField> sourceFields = sourceFieldService.getSourceFields(Integer.valueOf(cs_id));
		User user = (User) request.getAttribute("user");
		Integer uid = user.getId();
		String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;
		
		if (isAll == true) {
			Integer csId = cs_id.equals("")?null:Integer.valueOf(cs_id);
			Integer searchIdInt = searchId.equals("")?null:Integer.valueOf(searchId);
			SourceDataSQLInfo sourceDataSQLInfo=sourceDataController.getSourceDataSQL(csId,user,type,searchFirstWord,oldCondition,null,null,searchIdInt,chooseDatas,likeSearch,searchWord,true,ids);
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
            
			if(sourceDatas!=null)
			{
				for (List<String> record : sourceDatas)
				{
					String idTemp = record.get(0);
					if (projectDataService.insert(Integer.valueOf(p_id), idTemp, Integer.valueOf(cs_id)) == 1) {
						String pSourceDataId = projectFormatDataService.addProjectWholeSourceNew(p_id, cs_id, String.valueOf(uid),
								idTemp, sourceFields,user.getUsername());
						if (pSourceDataId != null) {
							if (projectDataService.updataPDataId(Integer.valueOf(p_id), idTemp, Integer.valueOf(cs_id),
									pSourceDataId) == 1) {
								count++;
							}
						}
					} else {
						count++;
					}
					sum++;
				}
			}
		} else {
			if (ids.startsWith(",")) {
				ids = ids.substring(1, ids.length()).replaceAll("check", "");
			}
			for (String sourceDataId : ids.split(",")) {
				
				if (projectDataService.insert(Integer.valueOf(p_id), sourceDataId, Integer.valueOf(cs_id)) == 1) {
					String pSourceDataId = projectFormatDataService.addProjectWholeSourceNew(p_id, cs_id, String.valueOf(uid),
							sourceDataId, sourceFields,user.getUsername());
					if (pSourceDataId != null) {
						if (projectDataService.updataPDataId(Integer.valueOf(p_id), sourceDataId, Integer.valueOf(cs_id),
								pSourceDataId) == 1) {
							count++;
						}
					}
				} else {
					count++;
				}
				sum++;
			}
			 
		}
		if (count.equals(sum)) {
			map.put("result", true);
			map.put("message", "添加成功！");
		} else {
			map.put("result", false);
			map.put("message", "成功添加" + count + "条，剩余" + (sum - count) + "条关系添加失败！");
		}
		return map;
	}
	
	
	/**
	 * 节点数据 添加到项目
	 * 
	 * @param response
	 * @param cs_id
	 * @param ft_id
	 *            1公共，0非公共
	 */
	@RequestMapping("/nodeDataToProject")
	@ResponseBody
	public Map<String, Object> nodeDataToProject(HttpServletRequest request,HttpServletResponse response,HttpSession httpSession, String cs_id, String sourceDataId, String ft_id,
			String p_id, String formatNodeId, String type, Integer page, Integer strip, Integer searchId, String desc_asc,
			String chooseDatas, String oldConditionNode8, String searchWord, String searchFirstWord, String fieldIds,
			String likeSearch,String ids,boolean isAll) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (ids.startsWith(",")) {
			ids = ids.substring(1, ids.length()).replaceAll("check4_", "");
		}
		Integer sum = 0;
		Integer count = 0;
		
		List<SourceField> sourceFields = sourceFieldService.getSourceFields(Integer.valueOf(cs_id));//采集源字段列表
		User user = (User) request.getAttribute("user");
		Integer uid = user.getId();
		String userName = user.getUsername();
		String sourceTableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;
		
		if(sourceDataId==null||"".equals(sourceDataId)) {
			map.put("result", false);
			map.put("message", "添加失败！缺少参数sourceDataId");
		}
		if(formatNodeId==null||"".equals(formatNodeId)) {
			map.put("result", false);
			map.put("message", "添加失败！缺少参数formatNodeId");
		}
		
		String nodeName = "";
		List<String> nodeList = HBaseFormatNodeDao.getFormatNodeById(cs_id,formatNodeId);
		if(nodeList!=null&&nodeList.size()>0) {
			nodeName = nodeList.get(2);
		}
		
		//source处理：判断project_data_relation是否存在p_data_id，不存在新增
		String pDataId = projectDataService.selectPDataId(Integer.valueOf(p_id), sourceDataId, Integer.valueOf(cs_id));
		if(null==pDataId||"".equals(pDataId)) {
			if (projectDataService.insert(Integer.valueOf(p_id), sourceDataId, Integer.valueOf(cs_id)) == 1) {
				String pSourceDataId = HBaseProjectDataDao.addProjectPartSource( p_id, cs_id, String.valueOf(uid),userName,sourceDataId, sourceFields);
				if (pSourceDataId != null) {
					if (projectDataService.updataPDataId(Integer.valueOf(p_id), sourceDataId, Integer.valueOf(cs_id),
							pSourceDataId) == 1) {
						pDataId = pSourceDataId;
					}
				}
			}
		}
		//node处理：判断project_node_relation是否存在p_node_id，不存在新增
		String pNodeId = projectNodeService.selectPNoId(Integer.valueOf(p_id), formatNodeId, Integer.valueOf(cs_id), Integer.valueOf(ft_id));
		if(null==pNodeId||"".equals(pNodeId)) {
			if (projectNodeService.insert(Integer.valueOf(p_id), formatNodeId, Integer.valueOf(cs_id), Integer.valueOf(ft_id), pDataId)==1) {
				String pNodeId1 = HBaseProjectDataDao.addProjectPartNode(cs_id, pDataId, formatNodeId, ft_id,nodeName);
				if (pNodeId1 != null) {
					if (projectNodeService.updadaPNodeId(Integer.valueOf(p_id), formatNodeId, Integer.valueOf(cs_id), Integer.valueOf(ft_id), pNodeId1) == 1) {
						pNodeId = pNodeId1;
					}
				}
			}
		}
		//格式数据处理
		if(isAll) {
			//全选
			List<List<String>> dataDataLists = new ArrayList<>();
			String formatTableName = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
			FormatDataSQLInfo formatDataSQLInfo = formatNodeController.getFormatDataSQL(cs_id, user, ft_id, sourceDataId, formatNodeId, httpSession, type, desc_asc, searchFirstWord, null, fieldIds, null, searchId, chooseDatas, likeSearch, searchWord, false, ids);
			Map<String, Map<String, Object>> dataDatas = PhoenixClient.select(formatDataSQLInfo.getSql());
			String dataMsg = String.valueOf((dataDatas.get("msg")).get("msg"));
			for (int j = 0; j < 6; j++) {
				dataMsg = String.valueOf((dataDatas.get("msg")).get("msg"));
				if (dataMsg.equals("success")) {
					dataDataLists = (List<List<String>>) dataDatas.get("records").get("data");
					break;
				} else {
					PhoenixClient.undefined(dataMsg, formatTableName, formatDataSQLInfo.getQualifiers(), formatDataSQLInfo.getConditionEqual(), formatDataSQLInfo.getConditionLike());
					dataDatas = PhoenixClient.select(formatDataSQLInfo.getSql());
				}
			}
			
			if(dataDataLists.size()>0) {
				for (List<String> record : dataDataLists)
				{
					String idTemp = record.get(0);
					if (projectNodeDataService.insert(Integer.valueOf(p_id), idTemp, Integer.valueOf(cs_id), Integer.valueOf(ft_id), pDataId) == 1) {
						String pFormatDataId = HBaseProjectDataDao.addProjectByData(cs_id, pDataId, ft_id, pNodeId, idTemp);
						if (pFormatDataId != null) {
							count++;
						}
					} else {
						count++;
					}
					sum++;
				}
			}
		}else {
            for (String formatDataId : ids.split(",")) {
            	if (projectNodeDataService.insert(Integer.valueOf(p_id), formatDataId, Integer.valueOf(cs_id), Integer.valueOf(ft_id), pDataId) == 1) {
					String pFormatDataId = HBaseProjectDataDao.addProjectByData(cs_id, pDataId, ft_id, pNodeId, formatDataId);
					if (pFormatDataId != null) {
						count++;
					}
				} else {
					count++;
				}
				sum++;
			}
		}
		if (count.equals(sum)) {
			map.put("result", true);
			map.put("message", "添加成功！");
		} else {
			map.put("result", false);
			map.put("message", "成功添加" + count + "条，剩余" + (sum - count) + "条关系添加失败！");
		}
		return map;

	}
	
	

	@RequestMapping("/remove")
	@ResponseBody
	public Map<String, Object> remove(HttpServletRequest request,Integer p_id, String cs_id, String ids, boolean isAll,
    		String searchId, String searchWord,String desc_asc,String oldCondition,
            String searchFirstWord,String chooseDatas,String likeSearch) {

		Map<String, Object> map = new HashMap<>();
		
		User user = (User) request.getAttribute("user");
		Integer user_id = user.getId();
		List<String> authority_numbers=new ArrayList<>();
		authority_numbers.add("31");
		ProjectCustomRole projects = projectCustomRoleService.selectProjectCustomRolesByUIDPID(user_id, p_id, authority_numbers);
		if (projects == null) {
			map.put("result", false);
			map.put("message", "权限不足");
			return map;
		}
		if (isAll == true) {
			
			String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;

			Integer csId = cs_id.equals("")?null:Integer.valueOf(cs_id);
			Integer searchIdInt = searchId.equals("")?null:Integer.valueOf(searchId);
			SourceDataSQLInfo sourceDataSQLInfo = sourceDataController.getSourceDataSQL(csId,user,"4",searchFirstWord,oldCondition,null,p_id,searchIdInt,chooseDatas,likeSearch,searchWord,true,ids);

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
			if(sourceDatas!=null&&sourceDatas.size()>0)
			{
				for (List<String> record : sourceDatas)
				{
					String idTemp = record.get(0);
					idsStr=idsStr+idTemp+",";
					try {
						projectDataService.remove(p_id, idTemp, Integer.valueOf(cs_id));
						//新增2019-02-23
						projectNodeService.removeByPDataId(p_id,Integer.valueOf(cs_id), idTemp);
						projectNodeDataService.remove(p_id,Integer.valueOf(cs_id),idTemp);
					} catch (NumberFormatException e) {
						continue;
					}
				}
				idsStr = idsStr.substring(0, idsStr.length()-1);
			}
			HBaseSourceDataDao.deleteSourceDatas(cs_id, idsStr);
			map.put("result", true);
			map.put("message", "关系解绑定成功");
		}else {
			if (ids.startsWith(",")) {
				ids = ids.substring(1, ids.length()).replaceAll("check", "");
			}
			String[] source_data_id = ids.split(",");
		for (int i = 0; i < source_data_id.length; i++) {
			try {
				projectDataService.remove(p_id, source_data_id[i], Integer.valueOf(cs_id));
				//新增2019-02-23
				projectNodeService.removeByPDataId(p_id,Integer.valueOf(cs_id), source_data_id[i]);
				projectNodeDataService.remove(p_id,Integer.valueOf(cs_id),source_data_id[i]);
			} catch (NumberFormatException e) {
				continue;
			}
		}
		HBaseSourceDataDao.deleteSourceDatas(cs_id, ids);
		map.put("result", true);
		map.put("message", "关系解绑定成功！");
		}
		
		return map;
	}

	private String getProjectSourceDataId(HttpServletRequest request, String p_id, String cs_id, String sourceDataId) {

		String pSourceDataId = null;
		if (projectDataService.insert(Integer.valueOf(p_id), sourceDataId, Integer.valueOf(cs_id)) == 1) {
			User user = (User) request.getAttribute("user");
			Integer uid = user.getId();
			String userName = user.getUsername();
			pSourceDataId = HBaseProjectDataDao.addProjectPartSource(p_id, cs_id, String.valueOf(uid), userName,sourceDataId,
					sourceFieldService.getSourceFields(Integer.valueOf(cs_id)));
			projectDataService.updataPDataId(Integer.valueOf(p_id), sourceDataId, Integer.valueOf(cs_id),
					pSourceDataId);
		}
		if (pSourceDataId == null) {
			pSourceDataId = projectDataService.selectPDataId(Integer.valueOf(p_id), sourceDataId,
					Integer.valueOf(cs_id));
		}
		return pSourceDataId;
	}

	@RequestMapping("/addFormatType")
	@ResponseBody
	public Map<String, Object> addFormatType(HttpServletRequest request, HttpServletResponse response, String p_id,
			String cs_id, String sourceDataId, String ft_ids) {
		Map<String, Object> map = new HashMap<>();
		String pSourceDataId = getProjectSourceDataId(request, p_id, cs_id, sourceDataId);
		Integer sum = 0;
		Integer count = 0;
		if (pSourceDataId != null) {
			List<String> ft_idList = new ArrayList<>();
			for (String ft_idStr : ft_ids.split(",")) {
				ft_idList.add(ft_idStr);
				sum++;
			}
			List<List<String>> formatNodes = HBaseFormatNodeDao.getFormatNodesByTypes(cs_id, sourceDataId, ft_idList);
			if (pSourceDataId != null && formatNodes != null && !formatNodes.isEmpty()) {
				for (List<String> formatNode : formatNodes) {
					try {
						String nodeId = formatNode.get(0);
						String ft_id = formatNode.get(1);
						String nodeName = formatNode.get(2);
						if (projectNodeService.insert(Integer.valueOf(p_id), nodeId, Integer.valueOf(cs_id),
								Integer.valueOf(ft_id), pSourceDataId) == 1) {
							if (HBaseProjectDataDao.addProjectWholeNode(cs_id, pSourceDataId, nodeId, ft_id,
									nodeName) != null) {
								count++;
							}
						} else {
							count++;
						}
					} catch (Exception e) {
						continue;
					}
				}
			}
		}

		if (count.equals(sum)) {
			map.put("result", true);
			map.put("message", "添加成功！");
		} else {
			map.put("result", false);
			map.put("message", "成功添加" + count + "条，剩余" + (sum - count) + "条关系添加失败！");
		}
		map.put("count", count);
		map.put("sum", sum);

		return map;
	}

	@RequestMapping("/addFormatNode")
	@ResponseBody
	public Map<String, Object> addFormatNode(HttpServletRequest request, HttpServletResponse response, String p_id,
			String cs_id, String sourceDataId, String formatNodeIds) {
		Map<String, Object> map = new HashMap<>();
		String pSourceDataId = getProjectSourceDataId(request, p_id, cs_id, sourceDataId);
		List<String> formatNodeIdList=new ArrayList<>();
		if (formatNodeIds != null) {
			for (String formatNodeId : formatNodeIds.split(",")) {
				formatNodeIdList.add(formatNodeId);
			}
		}
		Integer sum = formatNodeIdList.size();
		Integer count = 0;
		if (pSourceDataId != null) {
			List<List<String>> formatNodes = HBaseFormatNodeDao.getFormatNodesByIds(cs_id, formatNodeIdList);
			if (pSourceDataId != null && formatNodes != null && !formatNodes.isEmpty()) {
				for (List<String> formatNode : formatNodes) {
					try {
						String nodeId = formatNode.get(0);
						String ft_id = formatNode.get(1);
						String nodeName = formatNode.get(2);
						if (projectNodeService.insert(Integer.valueOf(p_id), nodeId, Integer.valueOf(cs_id),
								Integer.valueOf(ft_id), pSourceDataId) == 1) {
							List<String> ids = HBaseProjectDataDao.addProjectWholeNode(cs_id, pSourceDataId, nodeId,
									ft_id, nodeName);
							if (ids != null && !ids.isEmpty()) {
								String pFormatNodeId = ids.get(0);
								if (pFormatNodeId != null) {
									if (projectNodeService.updadaPNodeId(Integer.valueOf(p_id), nodeId,
											Integer.valueOf(cs_id), Integer.valueOf(ft_id), pFormatNodeId) == 1) {
										count++;
									}
								}
								for (int i = 1; i <= ids.size(); i++) {
									projectNodeDataService.insert(Integer.valueOf(p_id), ids.get(i),
											Integer.valueOf(cs_id), Integer.valueOf(ft_id), pSourceDataId);
								}
							}
							count++;
						}
					} catch (Exception e) {
						continue;
					}
				}
			}
		}

		if (count.equals(sum)) {
			map.put("result", true);
			map.put("message", "添加成功！");
		} else {
			map.put("result", false);
			map.put("message", "成功添加" + count + "条，剩余" + (sum - count) + "条关系添加失败！");
		}
		map.put("count", count);
		map.put("sum", sum);
		return map;
	}

	@RequestMapping("/addFormatData")
	@ResponseBody
	public Map<String, Object> addFormatData(HttpServletRequest request, HttpServletResponse response, String p_id,
			String cs_id, String sourceDataId, String formatNodeId, String formatDataIds) {
		Map<String, Object> map = new HashMap<>();
		String pSourceDataId = getProjectSourceDataId(request, p_id, cs_id, sourceDataId);
		List<String> nodedataIds = new ArrayList<>();
		for (String dataId : formatDataIds.split(",")) {
			nodedataIds.add(dataId);
		}
		Integer sum = nodedataIds.size();
		Integer count = 0;
		if (pSourceDataId != null) {
			try {
				List<String> formatNode = HBaseFormatNodeDao.getFormatNodeById(cs_id, formatNodeId);
				if (pSourceDataId != null && formatNode != null && !formatNode.isEmpty()) {
					String nodeId = formatNode.get(0);
					String ft_id = formatNode.get(1);
					String nodeName = formatNode.get(2);
					String pFormatNodeId = null;
					if (projectNodeService.insert(Integer.valueOf(p_id), nodeId, Integer.valueOf(cs_id),
							Integer.valueOf(ft_id), pSourceDataId) == 1) {
						pFormatNodeId = HBaseProjectDataDao.addProjectPartNode(cs_id, pSourceDataId, nodeId, ft_id,
								nodeName);
						projectNodeService.updadaPNodeId(Integer.valueOf(p_id), nodeId, Integer.valueOf(cs_id),
								Integer.valueOf(ft_id), pFormatNodeId);
					} else {
						pFormatNodeId = projectNodeService.selectPNoId(Integer.valueOf(p_id), nodeId,
								Integer.valueOf(cs_id), Integer.valueOf(ft_id));
					}
					if (pFormatNodeId != null) {
						HBaseProjectDataDao.updateProjectNodeDatas(cs_id, nodeId, ft_id, pFormatNodeId);
						for (String nodedataId : nodedataIds) {
								String pDataId = HBaseProjectDataDao.addProjectByData(cs_id, pSourceDataId, ft_id,
										pFormatNodeId, nodedataId);
								if (pDataId != null) {
									count++;
								}
						}
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		if (count.equals(sum)) {
			map.put("result", true);
			map.put("message", "添加成功！");
		} else {
			map.put("result", false);
			map.put("message", "成功添加" + count + "条，剩余" + (sum - count) + "条关系添加失败！");
		}
		map.put("count", count);
		map.put("sum", sum);
		return map;
	}

	//modified by tangye
	//sourceData/getSourceDatas接口看起来已经实现了数据选择的功能,直接redirect
	@RequestMapping("/getAllSourceDatas")
	public String getAllSourceDatas(HttpSession httpSession, Integer p_id, Integer cs_id) {
		return "redirect:/sourceData/getSourceDatas?type=5&p_id="+p_id+"&cs_id="+cs_id;
		/*List<Source> sources = sourceService.getSourcesForUser();
		httpSession.setAttribute("p_id", p_id);
		httpSession.setAttribute("sources", sources);// 采集源列表

		if (!sources.isEmpty()) {
			if (cs_id == null) {
				cs_id = sourceService.getSourcesForUserLimit(1).get(0).getCs_id();
			}
			Source source = sourceService.getSourceByCs_id(cs_id);
			source.setSourceFields(sourceFieldService.getSourceFields(cs_id));
			httpSession.setAttribute("source", source);// 采集源字段列表

			String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;

			List<String> qualifiers = new ArrayList<>();
			for (SourceField sourceField : source.getSourceFields()) {
				qualifiers.add(String.valueOf(sourceField.getCsf_id()));
			}
			Map<String, String> conditionEqual = new HashMap<>();
			Map<String, String> conditionLike = new HashMap<>();
			String condition = null;
			List<String> sourceDataIds = projectDataService.select(p_id, cs_id); // 源数据字段
			if (sourceDataIds != null && !sourceDataIds.isEmpty()) {
				condition = " (";
				for (String sourceDataId : sourceDataIds) {
					condition += "ID= '" + sourceDataId + "' OR ";
				}
				condition = condition.substring(0, condition.lastIndexOf("OR"));
			}
			String phoenixSQL = PhoenixClient.getPhoenixSQL(tableName, qualifiers, conditionEqual, conditionLike,
					condition, null, null);

			Map<String, Map<String, Object>> result = PhoenixClient.select(phoenixSQL);
			String resultMsg = String.valueOf((result.get("msg")).get("msg"));
			for (int j = 0; j < 6; j++) {
				resultMsg = String.valueOf((result.get("msg")).get("msg"));
				if (resultMsg.equals("success")) {
					break;
				} else {
					PhoenixClient.undefined(resultMsg, tableName, qualifiers, conditionEqual, conditionLike);
					result = PhoenixClient.select(phoenixSQL);
				}
			}
			httpSession.setAttribute("sourceDatas", result.get("records").get("data"));// 源数据字段数据，注：每个列表第一个值sourceDataId不显示

		}
		return "/jsp/project/data_reselect.jsp";*/
	}

}

