package com.xtkong.controller.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import com.liutianjun.pojo.User;
import com.xtkong.controller.user.ImportController;
import com.xtkong.dao.UserDataDao;
import com.xtkong.dao.hbase.HBaseFormatDataDao;
import com.xtkong.dao.hbase.HBaseFormatNodeDao;
import com.xtkong.dao.hbase.HBaseSourceDataDao;
import com.xtkong.model.Analysis;
import com.xtkong.model.FormatField;
import com.xtkong.model.ImportBean;
import com.xtkong.model.SourceField;
import com.xtkong.service.FormatFieldService;
import com.xtkong.service.FormatTypeService;
import com.xtkong.service.SourceFieldService;
import com.xtkong.service.SourceService;
import com.xtkong.util.CommonUtils;
import com.xtkong.util.ConstantsHBase;
import com.xtkong.util.HBaseDB;

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
	@Autowired
	UserDataDao userDataDao;
	@Autowired
	ImportController importController;

	@RequestMapping(value = "/import")
	@ResponseBody
	public Map<String, Object> test(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();

		ImportBean importBean = null; 
		try {
			importBean = new ImportBean(jsonStr);
		} catch (Exception e) {
			map.put("code", "1");
			map.put("message", "failed");
			map.put("info","格式错误！");
			return map;
		}

		if (importBean != null) {
			if ((importBean.getSourceid() != null) && (importBean.getUserid() != null)) {
				//校验数据正确性
				System.out.println(sourceService.getSourceId(importBean.getSourceid()));
				Integer cs_id = sourceService.getSourceId(importBean.getSourceid());
				String userName = importBean.getUserid();
				Integer userId = null;//用户ID
				List<User> userList = userDataDao.selectUserByUserName(userName);
				if(userList!=null&&userList.size()>0) {
					userId = (userList.get(0).getId())==null?null:userList.get(0).getId();
				}
				if(null==cs_id) {
					map.put("code", "1");
					map.put("message", "failed");
					map.put("info",importBean.getSourceid()+"没找到对应cs_id");
					return map;
				}
				if(null==userId) {
					map.put("code", "1");
					map.put("message", "failed");
					map.put("info",importBean.getUserid()+"没找到对应userId");
					return map;
				}
				//处理数据
				if (importBean.getBasic() != null) {
					map = sourceData(importBean.getSourceid(),cs_id, String.valueOf(userId),userName,
	        		importBean.getBasic().getFileurl(), importBean.getBasic().getSrcCol(),
					importBean.getBasic().getDistCol(), importBean.getBasic().getDefUnique());
					if(map.get("code").equals("1")) {
						//Source问题返回
						return map;
					}

				}
				
				if (importBean.getAnalysisList() != null) {
					for (Analysis analysis : importBean.getAnalysisList()) {
						if (analysis.getToBCol() != null&&analysis.getToBCol().size()>0) {
							map = formatDataToBCol(importBean.getSourceid(), cs_id, userName,String.valueOf(userId), analysis.getName(),
									analysis.getNodeName(), analysis.getFileurl(), analysis.getBCol(),
									analysis.getToBCol(), analysis.getSrcMCol(), analysis.getDistMCol(),
									analysis.getSrcDCol(), analysis.getDistDCol(), analysis.getDefUnique());
						} else if (analysis.getToBColValue() != null) {
							map = formatDataToBColValue(importBean.getSourceid(), cs_id,userName, String.valueOf(userId),
									analysis.getName(), analysis.getNodeName(), analysis.getFileurl(),
									analysis.getBCol(), analysis.getToBColValue(), analysis.getSrcMCol(),
									analysis.getDistMCol(), analysis.getSrcDCol(), analysis.getDistDCol(),
									analysis.getDefUnique());
						}
					}
				}
				if (map.size() == 0) {
					map.put("code", "1");
					map.put("message", "failed");
					map.put("info","格式错误！");
				}
				return map;
			}
		}
		map.put("code", "1");
		map.put("message", "failed");
		map.put("info","格式错误！");
		return map;
	}

	@SuppressWarnings("unused")
	private Map<String, Object> sourceData(String sourceid,Integer cs_id, String userid,String userName, String fileurl, List<String> srcCol,
			List<String> distCol, List<String> defUnique)  {
		Map<String, Object> map = new HashMap<String, Object>();
		//读取文件TXT
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream(fileurl);
			Scanner scanner = new Scanner(fileIn);
			HashMap<Integer,SourceField> csfIndex_IdMap = new HashMap<>();
			if (scanner.hasNextLine()) {
				int i = 0;
				String[] headLIst = null;
				headLIst = scanner.nextLine().split("\t");
			for (String head : headLIst) {
				if (srcCol.contains(head)) {
					String csf_Id = null;
					String type = null;
					SourceField sourceField = sourceFieldService.getSourceFieldInfo(cs_id, distCol.get(srcCol.indexOf(head)));
					if(null!=sourceField) {
						csf_Id = String.valueOf(sourceField.getCsf_id());
						type = String.valueOf(sourceField.getType());
					}
					if(null!=sourceField&&null!=csf_Id&&!"".equals(csf_Id)) {
						csfIndex_IdMap.put(i, sourceField);
					}else {
						scanner.close();
						map.put("code", "1");
						map.put("message", "failed");
						map.put("info",srcCol.indexOf(head)+"字段没有找到对应的csf_id");
						return map;
					}
				  }
				  i++;
				}
			}
			if(null==csfIndex_IdMap||csfIndex_IdMap.size()==0) {
				map.put("code", "1");
			    map.put("message", "failed");
			    map.put("info","待导入文件中要导入的采集源字段名有误");
				scanner.close();
				return map;
			}
			int j=1;//导入数据行数
			//source表是否存在
			if(!HBaseDB.getInstance().existTable(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id)) {
				sourceService.insertSourceByCsId(cs_id);
			};
			while (scanner.hasNextLine()) {
				Map<String, String> sourceFieldDatas = new HashMap<>();
				String[] datas = scanner.nextLine().split("\t");
				for (Entry<Integer, SourceField> sourceFieldId : csfIndex_IdMap.entrySet()) {
					SourceField sourceFied = sourceFieldId.getValue();
					String dataInfo = datas[sourceFieldId.getKey()];//txt中数据
					String type = sourceFied.getType();
					//校验txt数据格式
					Map<String,Object> validateMap = this.doValidDataSourceField(sourceFieldDatas,sourceFied,dataInfo,type,userName,userid,j);
					if(validateMap!=null&&!validateMap.get("code").equals("0")) {
						scanner.close();
						return validateMap;
					}
				}
				if (!sourceFieldDatas.isEmpty()) {
					HBaseSourceDataDao.insertSourceData(String.valueOf(cs_id), userid, sourceFieldDatas,userName);
				}
				j++;
			}
			scanner.close();
			map.put("code", "0");
			map.put("message", "success");
			map.put("info","共导入Source数据"+j+"行");
			 
		} catch (IOException e) {
			e.printStackTrace();
			map.put("code", "1");
			map.put("message", "failed");
			map.put("info",fileurl+"没找到对应文件");
			return map;
		}
		return map;
	}

	@SuppressWarnings("unused")
	private Map<String, Object> formatDataToBCol(String sourceid,Integer cs_id,String userName, String userid, String name, String nodeName,
			String fileurl, List<String> bCol, List<String> toBCol, List<String> srcMCol, List<String> distMCol,
			List<String> srcDCol, List<String> distDCol, List<String> defUnique) {
		//此情况为toBColValue没值时，读取txt中每行bCol对应的值来查询出对应的sourceId
		//srcMCol/distMCol暂时没用到（meta相关需从数据库中查询format_field的is_meta）
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Integer ft_id = formatTypeService.getFormatTypeId(cs_id, name);
			if(null==ft_id||ft_id==0) {
				map.put("code", "1");
				map.put("message", "failed");
				map.put("info",name+"没在表format_type中");
				return map;
			}
			FileInputStream fileIn;
			fileIn = new FileInputStream(fileurl);
			Scanner scanner = new Scanner(fileIn);
			HashMap<Integer, String> csfIndex_IdMap = new HashMap<>();// 与采集源基础信息关联所用的字段
			HashMap<Integer, FormatField> metaIndex_IdMap = new HashMap<>();// metainfo字段
			HashMap<Integer, FormatField> dataIndex_IdMap = new HashMap<>();// data字段
			if (scanner.hasNextLine()) {
				int i = 0;
				String[] headLIst = null;
				headLIst = scanner.nextLine().split("\t");
			for (String head : headLIst) {
				if (bCol.contains(head)) {
					String csf_Id = null;
					if ((csf_Id = String
							.valueOf(sourceFieldService.getSourceFieldId(cs_id, toBCol.get(bCol.indexOf(head)))))
									.equals("null")) {
						scanner.close();
						map.put("code", "1");
						map.put("message", "failed");
						map.put("info",head+"没在表collection_source_field中");
						return map;
					}
					csfIndex_IdMap.put(i, csf_Id);
				} 
				if (srcDCol.contains(head)) {
					String ff_Id = null;
					String type = null;
					boolean isMeta = false;
					FormatField formatField = formatFieldService.getFormatFieldInfo(ft_id, distDCol.get(srcDCol.indexOf(head)));
					if(null!=formatField) {
						ff_Id = String.valueOf(formatField.getFf_id());
						type = String.valueOf(formatField.getType());
						isMeta = formatField.isIs_meta();
					}
					//判断是否meta字段
					if(null!=formatField&&null!=ff_Id&&!"".equals(ff_Id)&&true==isMeta) {
						metaIndex_IdMap.put(i, formatField);
					}else if(null!=formatField&&null!=ff_Id&&!"".equals(ff_Id)&&false==isMeta) {
						dataIndex_IdMap.put(i, formatField);
					}else {
						scanner.close();
						map.put("code", "1");
						map.put("message", "failed");
						map.put("info",srcDCol.indexOf(head)+"字段没有找到对应的ff_Id");
						return map;
					}
				}
				i++;
			 }
		}
		Map<Map<String, String>, String> sourceDataIds = new HashMap<>();
		Map<String, String> formatNodeIds = new HashMap<>();
	    int j = 0;//数据行数
	    //node、format表是否存在
		if(!HBaseDB.getInstance().existTable(ConstantsHBase.TABLE_PREFIX_NODE_ + cs_id)) {
			sourceService.insertNodeByCsId(cs_id);
		};
		if(!HBaseDB.getInstance().existTable(ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id)) {
			sourceService.insertFormatByCsId(cs_id,ft_id);
		};
	    while (scanner.hasNextLine()) {
	    	try {
	    		j = j+1;
			String[] datas = scanner.nextLine().split("\t");
			String sourceDataId = null;
			Map<String, String> sourceFieldDatas = new HashMap<>();
			Map<String, String> metaDatas = new HashMap<>();
			Map<String, String> dataDatas = new HashMap<>();
			
			for (Entry<Integer, String> csfId : csfIndex_IdMap.entrySet()) {
				String csf_value = datas[csfId.getKey()];
				sourceFieldDatas.put(csfId.getValue(), csf_value);//{{216,col1_1},{217,col2_1}}
			}
			
			if (sourceDataIds.containsKey(sourceFieldDatas)) {// 记录id，减少数据库查询
				sourceDataId = sourceDataIds.get(sourceFieldDatas);
			} else {
				sourceDataId = HBaseSourceDataDao.getSourceDataId( String.valueOf(cs_id),String.valueOf(userid),
						sourceFieldDatas);
				if(null!=sourceDataId&&!sourceDataId.equals("")) {
					sourceDataIds.put(sourceFieldDatas, sourceDataId);
				}else {
					scanner.close();
					map.put("code", "1");
					map.put("message", "failed");
					map.put("info","第"+j+"行数据没找到对应sourceDataId，请检查与采集源基础信息关联所用的字段是否正确");
					return map;
				}
			}
			
			for (Entry<Integer, FormatField> metaId : metaIndex_IdMap.entrySet()) {
				FormatField formatField = metaId.getValue();
				String dataInfo = datas[metaId.getKey()];//txt中数据
				String type = formatField.getType();
				//校验txt数据格式
				Map<String,Object> validateMap = this.doValidDataFormatField(metaDatas,formatField,dataInfo,type,userName,userid,j);
				if(validateMap!=null&&!validateMap.get("code").equals("0")) {
					scanner.close();
					return validateMap;
				}
			}
			for (Entry<Integer, FormatField> formatFieldId : dataIndex_IdMap.entrySet()) {
				FormatField formatField = formatFieldId.getValue();
				String dataInfo = datas[formatFieldId.getKey()];//txt中数据
				String type = formatField.getType();
				//校验txt数据格式
				Map<String,Object> validateMap = this.doValidDataFormatField(dataDatas,formatField,dataInfo,type,userName,userid,j);
				if(validateMap!=null&&!validateMap.get("code").equals("0")) {
					scanner.close();
					return validateMap;
				}
			}
			 
			//HBase NODE_csId表处理
			String formatNodeId = null;
			if (formatNodeIds.containsKey(sourceDataId)) {// 记录id，减少数据库查询
				formatNodeId = formatNodeIds.get(sourceDataId);
			} else {
				//获取NODE表ID，如果没有则新增一条
				formatNodeId = HBaseFormatNodeDao.getFormatNodeId(String.valueOf(cs_id), sourceDataId,
						String.valueOf(ft_id), nodeName);
				if (formatNodeId == null) {
					//新增node表一条数据，生成nodeid；format表也新增一条数据，并且nodeid作为format表的id，并插入meta数据
					formatNodeId = HBaseFormatNodeDao.insertFormatNode(String.valueOf(cs_id), sourceDataId, String.valueOf(ft_id),
						nodeName, metaDatas);
				}
				formatNodeIds.put(sourceDataId, formatNodeId);
			}
		    
			//HBase FORMAT_csId_ftId表处理
			//meta数据保存在format表中id与node表的id一样的，一般会在新增Node表数据时同时新增一条format的meta数据。所以下面只需要更新meta数据，新增非meata数据。
			if (!metaDatas.isEmpty()) {
					HBaseFormatDataDao.updateFormatData(String.valueOf(cs_id), String.valueOf(ft_id), formatNodeId,
							metaDatas);
			}
			if (!dataDatas.isEmpty()) {
					HBaseFormatDataDao.insertFormatData(String.valueOf(cs_id), String.valueOf(ft_id), sourceDataId,
							formatNodeId, dataDatas);
			}
	    	}catch(Exception e) {
	    		e.printStackTrace();
				map.put("code", "1");
				map.put("message", "failed");
				map.put("info",e);
				return map;
	    	}
		}
			scanner.close();
			map.put("code", "0");
			map.put("message", "success");
			map.put("info","共导入format数据"+j+"行");
		} catch (IOException e1) {
			if(e1 instanceof FileNotFoundException) {
				e1.printStackTrace();
				map.put("code", "1");
				map.put("message", "failed");
				map.put("info",fileurl+"没找到对应文件");
			}else {
				e1.printStackTrace();
				map.put("code", "1");
				map.put("message", "failed");
				map.put("info",e1);
			}
			return map;
		}
		return map;
	}

	@SuppressWarnings("unused")
	private Map<String, Object> formatDataToBColValue(String sourceid,Integer cs_id,String userName, String userid, String name, String nodeName,
			String fileurl, List<String> toBCol, List<String> toBColValue, List<String> srcMCol, List<String> distMCol,
			List<String> srcDCol, List<String> distDCol, List<String> defUnique) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//配置信息校验
			Integer ft_id = formatTypeService.getFormatTypeId(cs_id, name);
			if(null==ft_id||ft_id==0) {
				map.put("code", "1");
				map.put("message", "failed");
				map.put("info",name+"没在表format_type中");
				return map;
			}
			Map<String, String> sourceFieldDatas = new HashMap<>();
			for (int i = 0; i < toBCol.size(); i++) {
				Integer field = sourceFieldService.getSourceFieldId(cs_id, toBCol.get(i));
				if (null==field||String.valueOf(field).equals("null")) {
					map.put("code", "1");
					map.put("message", "failed");
					map.put("info","toBCol中"+toBCol.get(i)+"没对应csf_id");
					return map;
				}
				sourceFieldDatas.put(String.valueOf(field), toBColValue.get(i));//("216","col1_1")
			}
			//txt处理
			FileInputStream fileIn;
			fileIn = new FileInputStream(fileurl);
			Scanner scanner = new Scanner(fileIn);
			String sourceDataId = HBaseSourceDataDao.getSourceDataId( String.valueOf(cs_id),userid,
					sourceFieldDatas);
			if ((sourceDataId != null) && (!sourceDataId.isEmpty())) {

				HashMap<Integer, FormatField> metaIndex_IdMap = new HashMap<>();// metainfo字段
				HashMap<Integer, FormatField> dataIndex_IdMap = new HashMap<>();// data字段
				if (scanner.hasNextLine()) {
					int i = 0;
					for (String head : scanner.nextLine().split("\t")) {
						if (srcDCol.contains(head)) {
							String ff_Id = null;
							String type = null;
							boolean isMeta = false;
							FormatField formatField = formatFieldService.getFormatFieldInfo(ft_id, distDCol.get(srcDCol.indexOf(head)));
							if(null!=formatField) {
								ff_Id = String.valueOf(formatField.getFf_id());
								type = String.valueOf(formatField.getType());
								isMeta = formatField.isIs_meta();
							}
							//判断是否meta字段
							if(null!=formatField&&null!=ff_Id&&!"".equals(ff_Id)&&true==isMeta) {
								metaIndex_IdMap.put(i, formatField);
							}else if(null!=formatField&&null!=ff_Id&&!"".equals(ff_Id)&&false==isMeta) {
								dataIndex_IdMap.put(i, formatField);
							}else {
								scanner.close();
								map.put("code", "1");
								map.put("message", "failed");
								map.put("info",srcDCol.indexOf(head)+"字段没有找到对应的ff_Id");
								return map;
							}
						}
						i++;
					}
				}
				Map<String, String> formatNodeIds = new HashMap<>();
				int j=0;//数据行数
				//node、format表是否存在
				if(!HBaseDB.getInstance().existTable(ConstantsHBase.TABLE_PREFIX_NODE_ + cs_id)) {
					sourceService.insertNodeByCsId(cs_id);
				};
				if(!HBaseDB.getInstance().existTable(ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id)) {
					sourceService.insertFormatByCsId(cs_id,ft_id);
				};
				while (scanner.hasNextLine()) {
					j = j+1;
					String[] datas = scanner.nextLine().split("\t");
					Map<String, String> metaDatas = new HashMap<>();
					Map<String, String> dataDatas = new HashMap<>();

					for (Entry<Integer, FormatField> metaId : metaIndex_IdMap.entrySet()) {
						FormatField formatField = metaId.getValue();
						String dataInfo = datas[metaId.getKey()];//txt中数据
						String type = formatField.getType();
						//校验txt数据格式
						Map<String,Object> validateMap = this.doValidDataFormatField(metaDatas,formatField,dataInfo,type,userName,userid,j);
						if(validateMap!=null&&!validateMap.get("code").equals("0")) {
							scanner.close();
							return validateMap;
						}
					}
					for (Entry<Integer, FormatField> formatFieldId : dataIndex_IdMap.entrySet()) {
						FormatField formatField = formatFieldId.getValue();
						String dataInfo = datas[formatFieldId.getKey()];//txt中数据
						String type = formatField.getType();
						//校验txt数据格式
						Map<String,Object> validateMap = this.doValidDataFormatField(dataDatas,formatField,dataInfo,type,userName,userid,j);
						if(validateMap!=null&&!validateMap.get("code").equals("0")) {
							scanner.close();
							return validateMap;
						}
					}
					String formatNodeId = null;
					if (formatNodeIds.containsKey(sourceDataId)) {// 记录id，减少数据库查询
						formatNodeId = formatNodeIds.get(sourceDataId);
					} else {
						formatNodeId = HBaseFormatNodeDao.getFormatNodeId(String.valueOf(cs_id), sourceDataId,
							String.valueOf(ft_id), nodeName);
						if (formatNodeId == null) {
							formatNodeId = HBaseFormatNodeDao.insertFormatNode(String.valueOf(cs_id), sourceDataId,
									String.valueOf(ft_id), nodeName, metaDatas);
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
				map.put("code", "0");
				map.put("message", "success");
				map.put("info","共导入format数据"+j+"行");
				scanner.close();
			} else {
				scanner.close();
				map.put("code", "1");
				map.put("message", "failed");
				map.put("info","没找到对应sourceDataId，请检查与采集源基础信息关联所用的字段是否正确");
				return map;
			}
		} catch (IOException e1) {
			if(e1 instanceof FileNotFoundException) {
				e1.printStackTrace();
				map.put("code", "1");
				map.put("message", "failed");
				map.put("info",fileurl+"没找到对应文件");
			}else {
				e1.printStackTrace();
				map.put("code", "1");
				map.put("message", "failed");
				map.put("info",e1);
			}
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

	/***txt数据根据格式类型校验   SourceField***/
	public Map<String,Object> doValidDataSourceField(Map<String, String> sourceFieldDatas,SourceField sourceFied,String dataInfo,String type
			,String userName,String userid,int j) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(null==dataInfo||"".equals(dataInfo)) {
			//如为空不做校验
			sourceFieldDatas.put(String.valueOf(sourceFied.getCsf_id()), dataInfo);
		}else {
			if(type.equals(ConstantsHBase.DATA_TYPE_TUPIAN)||type.equals(ConstantsHBase.DATA_TYPE_WENJIAN)) {
				//数据类型为文件或图片
				//数据非空，从临时目录转到该存的地方，生成MD5保存在数据库
				try {
					Entry<String,String> fileResult =importController.importFile(userName,Integer.valueOf(userid),dataInfo);
					String md5Vaule = fileResult.getKey();
					sourceFieldDatas.put(String.valueOf(sourceFied.getCsf_id()), md5Vaule);
				} catch (Exception e) {
					map.put("code", "1");
					map.put("message", "failed");
					map.put("info","第"+j+"行数据出错！"+dataInfo+"没找到对应文件:"+e.getMessage());
					return map;
				}
			}else if(type.equals(ConstantsHBase.DATA_TYPE_RIQI)) {
				//日期格式校验yyyy-MM-dd HH:mm:ss或yyyy-MM-dd
				if(CommonUtils.isValidDate(dataInfo)) {
					sourceFieldDatas.put(String.valueOf(sourceFied.getCsf_id()), dataInfo);
				}else {
					map.put("code", "1");
					map.put("message", "failed");
					map.put("info","第"+j+"行数据出错！"+dataInfo+"不是日期格式yyyy-MM-dd HH:mm:ss或yyyy-MM-dd");
					return map;
				}
			}else if(type.equals(ConstantsHBase.DATA_TYPE_SHUZHI)) {
				if(CommonUtils.isValidNum(dataInfo)) {
					sourceFieldDatas.put(String.valueOf(sourceFied.getCsf_id()), dataInfo);
				}else {
					map.put("code", "1");
					map.put("message", "failed");
					map.put("info","第"+j+"行数据出错！"+dataInfo+"不是数值格式");
					return map;
				}
			}else {
				sourceFieldDatas.put(String.valueOf(sourceFied.getCsf_id()), dataInfo);
			}
		}
		map.put("code", "0");
		map.put("message", "success");
		return map;
	}
	
	/***txt数据根据格式类型校验   FormatField***/
	private Map<String, Object> doValidDataFormatField(Map<String, String> sourceFieldDatas, FormatField formatField,
			String dataInfo, String type,String userName, String userid, int j) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(null==dataInfo||"".equals(dataInfo)) {
			//如为空不做校验
			sourceFieldDatas.put(String.valueOf(formatField.getFf_id()), dataInfo);
		}else {
			if(type.equals(ConstantsHBase.DATA_TYPE_TUPIAN)||type.equals(ConstantsHBase.DATA_TYPE_WENJIAN)) {
				//数据类型为文件或图片
				//数据非空，从临时目录转到该存的地方，生成MD5保存在数据库
				try {
					Entry<String,String> fileResult =importController.importFile(userName,Integer.valueOf(userid),dataInfo);
					String md5Vaule = fileResult.getKey();
					sourceFieldDatas.put(String.valueOf(formatField.getFf_id()), md5Vaule);
				} catch (Exception e) {
					map.put("code", "1");
					map.put("message", "failed");
					map.put("info","第"+j+"行数据出错！"+dataInfo+"没找到对应文件:"+e.getMessage());
					return map;
				}
			}else if(type.equals(ConstantsHBase.DATA_TYPE_RIQI)) {
				//日期格式校验yyyy-MM-dd HH:mm:ss或yyyy-MM-dd
				if(CommonUtils.isValidDate(dataInfo)) {
					sourceFieldDatas.put(String.valueOf(formatField.getFf_id()), dataInfo);
				}else {
					map.put("code", "1");
					map.put("message", "failed");
					map.put("info","第"+j+"行数据出错！"+dataInfo+"不是日期格式yyyy-MM-dd HH:mm:ss或yyyy-MM-dd");
					return map;
				}
			}else if(type.equals(ConstantsHBase.DATA_TYPE_SHUZHI)) {
				if(CommonUtils.isValidNum(dataInfo)) {
					sourceFieldDatas.put(String.valueOf(formatField.getFf_id()), dataInfo);
				}else {
					map.put("code", "1");
					map.put("message", "failed");
					map.put("info","第"+j+"行数据出错！"+dataInfo+"不是数值格式");
					return map;
				}
			}else {
				sourceFieldDatas.put(String.valueOf(formatField.getFf_id()), dataInfo);
			}
		}
		map.put("code", "0");
		map.put("message", "success");
		return map;
	}
    
}
