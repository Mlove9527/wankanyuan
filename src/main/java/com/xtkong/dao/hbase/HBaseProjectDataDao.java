package com.xtkong.dao.hbase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.xtkong.model.SourceField;
import com.xtkong.service.PhoenixClient;
import com.xtkong.util.ConstantsHBase;
import com.xtkong.util.HBaseDB;

public class HBaseProjectDataDao {

	@SuppressWarnings("unchecked")
	public static String updateProjectNodeDatas(String cs_id, String oldFormatNodeId, String ft_id,
			String formatNodeId) {
		String pFormatNodeId = null;
		try {
			String tableStr = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
			Map<String, Map<String, Object>> records = PhoenixClient
					.select("SELECT * FROM \"" + tableStr + "\" WHERE ID='" + oldFormatNodeId + "'");
			List<String> head = (List<String>) records.get("records").get("head");
			List<List<String>> datas = (List<List<String>>) records.get("records").get("data");

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
				HBaseFormatDataDao.updateFormatDatas(cs_id, ft_id, formatNodeId, formatFieldDatas);
			}
		} catch (Exception e) {
			return null;
		}
		return pFormatNodeId;
	}

	/**
	 * 添加分析数据
	 * 
	 * @param cs_id
	 * @param pSourceDataId
	 * @param ft_id
	 * @param pFormatNodeId
	 * @param oldFormatDataId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String addProjectByData(String cs_id, String pSourceDataId, String ft_id, String pFormatNodeId,
			String oldFormatDataId) {
		String newId = "";
		try {
			String tableStr = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
			Map<String, Map<String, Object>> records = PhoenixClient
					.select("SELECT * FROM \"" + tableStr + "\" WHERE ID='" + oldFormatDataId + "'  ");
			List<String> head = (List<String>) records.get("records").get("head");
			List<List<String>> datas = (List<List<String>>) records.get("records").get("data");
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
				newId = HBaseFormatDataDao.insertFormatData(cs_id, ft_id, pSourceDataId, pFormatNodeId, formatFieldDatas);
			}
		} catch (Exception e) {
			return null;
		}
		return newId;
	}

	/**
	 * 添加结点
	 * 
	 * @param cs_id
	 * @param pSourceDataId
	 * @param oldFormatNodeId
	 * @param ft_id
	 * @param nodeName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String addProjectPartNode(String cs_id, String pSourceDataId, String oldFormatNodeId, String ft_id,
			String nodeName) {
		String pFormatNodeId = null;
		try {
			String tableStr = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id; // 格式类型+采集源id_格式类型id
			Map<String, Map<String, Object>> records = PhoenixClient
					.select("SELECT * FROM \"" + tableStr + "\" WHERE ID='" + oldFormatNodeId + "'");//查询语句
			List<String> head = (List<String>) records.get("records").get("head");
			List<List<String>> datas = (List<List<String>>) records.get("records").get("data");

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
				pFormatNodeId = HBaseFormatNodeDao.insertFormatNode(cs_id, pSourceDataId, ft_id, nodeName,
						formatFieldDatas);
			}
		} catch (Exception e) {
			return null;
		}
		return pFormatNodeId;
	}

	/**
	 * 添加结点及结点下所有数据
	 * 
	 * @param cs_id
	 * @param pSourceDataId
	 * @param oldFormatNodeId
	 * @param ft_id
	 * @param nodeName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> addProjectWholeNode(String cs_id, String pSourceDataId, String oldFormatNodeId,
			String ft_id, String nodeName) {
		List<String> idStrings = new ArrayList<>();
		String pFormatNodeId = null;
		try {
			pFormatNodeId = HBaseProjectDataDao.addProjectPartNode(cs_id, pSourceDataId, oldFormatNodeId, ft_id,
					nodeName);
			if (pFormatNodeId != null) {
				idStrings.add(pFormatNodeId);
				String tableStr = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
				Map<String, Map<String, Object>> records = PhoenixClient.select("SELECT * FROM \"" + tableStr
						+ "\" WHERE ID!='" + oldFormatNodeId + "' AND " + "\"" + ConstantsHBase.FAMILY_INFO + "\".\""
						+ ConstantsHBase.QUALIFIER_FORMATNODEID + "\"='" + oldFormatNodeId + "' ");
				List<String> head = (List<String>) records.get("records").get("head");
				List<List<String>> datas = (List<List<String>>) records.get("records").get("data");
				for (List<String> data : datas) {
					Map<String, String> formatFieldDatas = new HashMap<>();
					for (int i = 0; i < head.size(); i++) {
						try {
							if (head.get(i).equals("ID")) {
								idStrings.add(data.get(i));
							} else {
								formatFieldDatas.put(head.get(i), data.get(i));
							}
						} catch (Exception e) {
							continue;
						}
					}
					HBaseFormatDataDao.insertFormatData(cs_id, ft_id, pSourceDataId, pFormatNodeId, formatFieldDatas);
				}
			}
		} catch (Exception e) {
			return null;
		}
		return idStrings;
	}

	/**
	 * 只添加源数据
	 * 
	 * @param p_id
	 * @param cs_id
	 * @param uid
	 * @param sourceDataId
	 * @param sourceFields
	 * @return
	 */
	public static String addProjectPartSource(String p_id, String cs_id, String uid,String username, String sourceDataId,
			List<SourceField> sourceFields) {
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
				return psourceDataId;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	
	/**
	 * 添加源数据及其所有下层数据
	 * 
	 * @param p_id
	 * @param cs_id
	 * @param uid
	 * @param sourceDataId
	 * @param sourceFields
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String addProjectWholeSource(String p_id, String cs_id, String uid, String sourceDataId,
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
						}
						if (formatNodeId != null) {
							records = PhoenixClient.select("SELECT * FROM \"" + tableStr + "\" WHERE ID!='"
									+ oldFormatNodeId + "' AND " + "\"" + ConstantsHBase.FAMILY_INFO + "\".\""
									+ ConstantsHBase.QUALIFIER_FORMATNODEID + "\"='" + oldFormatNodeId + "'  ");
							head = (List<String>) records.get("records").get("head");
							datas = (List<List<String>>) records.get("records").get("data");
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
								HBaseFormatDataDao.insertFormatData(cs_id, ft_id, psourceDataId, formatNodeId,
										formatFieldDatas);
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

	/**
	 * 添加源数据及其所有下层数据
	 * 
	 * @param p_id
	 * @param cs_id
	 * @param uid
	 * @param sourceDataIds
	 * @param sourceFields
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean addProjectWholeSources(String p_id, String cs_id, String uid, List<String> sourceDataIds,
			List<SourceField> sourceFields) {
		try {
			HBaseDB db = HBaseDB.getInstance();
			String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;
			String family = ConstantsHBase.FAMILY_INFO;
			Table table = db.getTable(tableName);
			List<Get> gets = new ArrayList<Get>();
			for (String sourceDataId : sourceDataIds) {
				gets.add(new Get(Bytes.toBytes(sourceDataId)));
			}
			// 存放批量操作的结果
			Result[] results = table.get(gets);
			Boolean b = true;
			for (Result result : results) {
				Long count = db.getNewId(ConstantsHBase.TABLE_GID, uid + "_" + cs_id, ConstantsHBase.FAMILY_GID_GID,
						ConstantsHBase.QUALIFIER_GID_GID_GID);
				String psourceDataId = uid + "_" + cs_id + "_" + count;
				Put put = new Put(Bytes.toBytes(psourceDataId));
				put.addColumn(Bytes.toBytes(family), Bytes.toBytes(ConstantsHBase.QUALIFIER_PUBLIC),
						Bytes.toBytes(ConstantsHBase.VALUE_PUBLIC_FALSE));
				put.addColumn(Bytes.toBytes(family), Bytes.toBytes(ConstantsHBase.QUALIFIER_PROJECT),
						Bytes.toBytes(String.valueOf(p_id)));

				String oldSourceDataId = Bytes.toString(result.getRow());
				put.addColumn(Bytes.toBytes(family), Bytes.toBytes(ConstantsHBase.QUALIFIER_SOURCEDATAID),
						Bytes.toBytes(String.valueOf(oldSourceDataId)));
				for (SourceField sourceField : sourceFields) {
					put.addColumn(Bytes.toBytes(family), Bytes.toBytes(String.valueOf(sourceField.getCsf_id())), result
							.getValue(Bytes.toBytes(family), Bytes.toBytes(String.valueOf(sourceField.getCsf_id()))));
				}
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
								formatNodeId = HBaseFormatNodeDao.insertFormatNode(cs_id, psourceDataId, ft_id,
										nodeName, formatFieldDatas);
							}
							if (formatNodeId != null) {
								records = PhoenixClient.select("SELECT * FROM \"" + tableStr + "\" WHERE ID!='"
										+ oldFormatNodeId + "' AND " + "\"" + ConstantsHBase.FAMILY_INFO + "\".\""
										+ ConstantsHBase.QUALIFIER_FORMATNODEID + "\"='" + oldFormatNodeId + "'  ");
								head = (List<String>) records.get("records").get("head");
								datas = (List<List<String>>) records.get("records").get("data");
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
									HBaseFormatDataDao.insertFormatData(cs_id, ft_id, psourceDataId, formatNodeId,
											formatFieldDatas);
								}
							}
						}
					}
				} else {
					b = false;
				}
			}
			table.close();
			return b;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public static boolean addProjectByNodes(String cs_id, String sourceDataId, List<List<String>> formatNodes) {
		try {
			Boolean b = true;
			for (List<String> formatNode : formatNodes) {
				try {
					String oldFormatNodeId = formatNode.get(0);
					String ft_id = formatNode.get(1);
					String nodeName = formatNode.get(2);
					if (oldFormatNodeId != null) {
						String tableStr = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
						Map<String, Map<String, Object>> records = PhoenixClient
								.select("SELECT * FROM \"" + tableStr + "\" WHERE ID='" + oldFormatNodeId + "'");
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
							formatNodeId = HBaseFormatNodeDao.insertFormatNode(cs_id, sourceDataId, ft_id, nodeName,
									formatFieldDatas);
						}
						if (formatNodeId != null) {
							records = PhoenixClient.select("SELECT * FROM \"" + tableStr + "\" WHERE ID!='"
									+ oldFormatNodeId + "' AND " + "\"" + ConstantsHBase.FAMILY_INFO + "\".\""
									+ ConstantsHBase.QUALIFIER_FORMATNODEID + "\"='" + oldFormatNodeId + "'  ");
							head = (List<String>) records.get("records").get("head");
							datas = (List<List<String>>) records.get("records").get("data");
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
								HBaseFormatDataDao.insertFormatData(cs_id, ft_id, sourceDataId, formatNodeId,
										formatFieldDatas);
							}
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
			return b;
		} catch (Exception e) {
			return false;
		}
	}

}
