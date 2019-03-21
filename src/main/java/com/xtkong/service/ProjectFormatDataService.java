package com.xtkong.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtkong.dao.ProjectNodeDataDao;
import com.xtkong.dao.hbase.HBaseFormatDataDao;
import com.xtkong.dao.hbase.HBaseFormatNodeDao;
import com.xtkong.model.SourceField;
import com.xtkong.util.ConstantsHBase;
import com.xtkong.util.HBaseDB;

@Service
public class ProjectFormatDataService {
	@Autowired
	ProjectNodeDataDao projectNodeDataDao;
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
	
	/**
	 * 添加源数据及其所有下层数据
	 * 20190320新增，增加mysql 关系数据，并且判断关系表中是否存在，存在则不加，不存在则加
	 * 
	 * @param p_id
	 * @param cs_id
	 * @param uid
	 * @param sourceDataId
	 * @param sourceFields
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  String addProjectWholeSourceNew(String p_id, String cs_id, String uid, String sourceDataId,
			List<SourceField> sourceFields,String username) {
		try {
			HBaseDB db = HBaseDB.getInstance();
			String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;
			String family = ConstantsHBase.FAMILY_INFO;
			Table table = db.getTable(tableName);
			Get get = new Get(Bytes.toBytes(sourceDataId));
			// 存放批量操作的结果
			Result result = table.get(get);
			Long count = db.getNewId(ConstantsHBase.TABLE_GID, uid + "_" + cs_id, ConstantsHBase.FAMILY_GID_GID,
					ConstantsHBase.QUALIFIER_GID_GID_GID);
			String psourceDataId = uid + "_" + cs_id + "_" + count;
			Put put = new Put(Bytes.toBytes(psourceDataId));
			put.addColumn(Bytes.toBytes(family), Bytes.toBytes(ConstantsHBase.QUALIFIER_PUBLIC),
					Bytes.toBytes(ConstantsHBase.VALUE_PUBLIC_FALSE));
			put.addColumn(Bytes.toBytes(family), Bytes.toBytes(ConstantsHBase.QUALIFIER_PROJECT),
					Bytes.toBytes(String.valueOf(p_id)));
			
			put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(ConstantsHBase.QUALIFIER_CREATOR),//格式数据--添加创建人 
					Bytes.toBytes(username));
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),//格式数据--添加创建时间
					Bytes.toBytes(ConstantsHBase.QUALIFIER_CREATE_DATETIME),
					Bytes.toBytes(String.valueOf(simpleDateFormat.format(new Date()))));
			
			String oldSourceDataId = Bytes.toString(result.getRow());
			put.addColumn(Bytes.toBytes(family), Bytes.toBytes(ConstantsHBase.QUALIFIER_SOURCEDATAID),
					Bytes.toBytes(String.valueOf(oldSourceDataId)));
			for (SourceField sourceField : sourceFields) {
				put.addColumn(Bytes.toBytes(family), Bytes.toBytes(String.valueOf(sourceField.getCsf_id())),
						result.getValue(Bytes.toBytes(family), Bytes.toBytes(String.valueOf(sourceField.getCsf_id()))));
			}
			table.close();
			if (db.putRow(tableName, put)) {

				List<List<String>> formatNodes = HBaseFormatNodeDao.getFormatNodesBySourceDataId(cs_id,
						oldSourceDataId);
				for (List<String> formatNode : formatNodes) {
					String oldFormatNodeId = formatNode.get(0);
					String ft_id = formatNode.get(1);
					String nodeName = formatNode.get(2);
					if (oldFormatNodeId != null) {
						String tableStr = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
						Map<String, Map<String, Object>> records = PhoenixClient
								.select("SELECT *  FROM \"" + tableStr + "\" WHERE ID='" + oldFormatNodeId + "'");
						List<String> head = (List<String>) records.get("records").get("head");
						List<List<String>> datas = (List<List<String>>) records.get("records").get("data");

						String formatNodeId = null;
						for (List<String> data : datas) {
							Map<String, String> formatFieldDatas = new HashMap<>();
							for (int i = 0; i < head.size(); i++) {
								if (!head.get(i).equals("ID")) {
									try {
										formatFieldDatas.put(head.get(i), data.get(i));
									} catch (Exception e) {
										continue;
									}
								}
							}
							formatNodeId = HBaseFormatNodeDao.insertFormatNode(cs_id, psourceDataId, ft_id, nodeName,
									formatFieldDatas);
							//新增node关系表数据
							projectNodeService.insertAll(Integer.valueOf(p_id), oldFormatNodeId, Integer.valueOf(cs_id), Integer.valueOf(ft_id),formatNodeId, psourceDataId);
						}
						if (formatNodeId != null) {
							records = PhoenixClient.select("SELECT * FROM \"" + tableStr + "\" WHERE ID!='"
									+ oldFormatNodeId + "' AND " + "\"" + ConstantsHBase.FAMILY_INFO + "\".\""
									+ ConstantsHBase.QUALIFIER_FORMATNODEID + "\"='" + oldFormatNodeId + "'  ");
							head = (List<String>) records.get("records").get("head");
							datas = (List<List<String>>) records.get("records").get("data");
							
							for (List<String> data : datas) {
								Map<String, String> formatFieldDatas = new HashMap<>();
								String oldFormatDataId = "";
								for (int i = 0; i < head.size(); i++) {
									if (!head.get(i).equals("ID")) {
										try {
											formatFieldDatas.put(head.get(i), data.get(i));
										} catch (Exception e) {
											continue;
										}
									}else {
										oldFormatDataId = data.get(i);
									}
								}
								String newFormatDataId = HBaseFormatDataDao.insertFormatData(cs_id, ft_id, psourceDataId, formatNodeId,
										formatFieldDatas);
								//新增节点数据关系表数据
								projectNodeDataService.insert(Integer.valueOf(p_id), oldFormatDataId, Integer.valueOf(cs_id), Integer.valueOf(ft_id), psourceDataId);
							}
						}
					}
				}

			} else {
				return null;
			}
			return psourceDataId;
		} catch (Exception e) {
			return null;
		}
	}
}
