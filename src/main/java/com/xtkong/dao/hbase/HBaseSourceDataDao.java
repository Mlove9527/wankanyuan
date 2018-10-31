package com.xtkong.dao.hbase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import com.xtkong.model.SourceField;
import com.xtkong.service.PhoenixClient;
import com.xtkong.util.ConstantsHBase;
import com.xtkong.util.HBaseDB;

public class HBaseSourceDataDao {

	/***
	 * 新增采集源：创建源数据基础信息表
	 * 
	 * @param cs_id
	 *            采集源id
	 */
	public static void createSourceDataTable(String cs_id) {
		// 源数据基础信息表
		String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;
		createSourceDataTable(tableName, new String[] { ConstantsHBase.FAMILY_INFO }, ConstantsHBase.VERSION_SOURCE);
		
	}

	private static void createSourceDataTable(String tableName, String[] columnFamilies, int version) {
		HBaseDB db = HBaseDB.getInstance();
		db.createTable(tableName, columnFamilies, version);
	}

	/**
	 * 添加一条源数据
	 * 
	 * @param cs_id
	 *            采集源
	 * @param uid
	 *            用户
	 * @param sourceFieldDatas
	 *            采集源字段、 数据值
	 */

	
	public static String insertSourceData1(String cs_id, String uid, Map<String, String> sourceFieldDatas) {
		HBaseDB db = HBaseDB.getInstance();
		Long count = db.getNewId(ConstantsHBase.TABLE_GID, uid + "_" + cs_id, ConstantsHBase.FAMILY_GID_GID,
				ConstantsHBase.QUALIFIER_GID_GID_GID);
		String rowKey = uid + "_" + cs_id + "_" + count;
		Put put = new Put(Bytes.toBytes(rowKey));
		put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(ConstantsHBase.QUALIFIER_CREATE),
				Bytes.toBytes(String.valueOf(uid)));
		put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(ConstantsHBase.QUALIFIER_USER),
				Bytes.toBytes(String.valueOf(uid)));
		put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(ConstantsHBase.QUALIFIER_PUBLIC),
				Bytes.toBytes(ConstantsHBase.VALUE_PUBLIC_FALSE));
		for (Entry<String, String> sourceFieldData : sourceFieldDatas.entrySet()) {
			put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(sourceFieldData.getKey()),
					Bytes.toBytes(sourceFieldData.getValue()));
		}
		if (db.putRow(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id, put)) {
			return rowKey;
		}
		return null;
	}


	public static String insertSourceData(String cs_id, String uid, Map<String, String> sourceFieldDatas,
			String username) {
		HBaseDB db = HBaseDB.getInstance();
		Long count = db.getNewId(ConstantsHBase.TABLE_GID, uid + "_" + cs_id, ConstantsHBase.FAMILY_GID_GID,
				ConstantsHBase.QUALIFIER_GID_GID_GID);
		String rowKey = uid + "_" + cs_id + "_" + count;
		Put put = new Put(Bytes.toBytes(rowKey));
		put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(ConstantsHBase.QUALIFIER_CREATE),//格式数据--我创建的
				Bytes.toBytes(String.valueOf(uid)));
		put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(ConstantsHBase.QUALIFIER_USER),//格式数据--我的
				Bytes.toBytes(String.valueOf(uid)));
		put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(ConstantsHBase.QUALIFIER_PUBLIC), //格式数据--公共
				Bytes.toBytes(ConstantsHBase.VALUE_PUBLIC_FALSE));
		put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(ConstantsHBase.QUALIFIER_CREATOR),//格式数据--添加创建人 
				Bytes.toBytes(username));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),//格式数据--添加创建时间
				Bytes.toBytes(ConstantsHBase.QUALIFIER_CREATE_DATETIME),
				Bytes.toBytes(String.valueOf(simpleDateFormat.format(new Date()))));
		for (Entry<String, String> sourceFieldData : sourceFieldDatas.entrySet()) {
			put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(sourceFieldData.getKey()),
					Bytes.toBytes(sourceFieldData.getValue()));
		}
		if (db.putRow(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id, put)) {
			return rowKey;
		}
		return null;
	}

	/**
	 * sourcedata获取通用
	 * 
	 * @param tableName
	 * @param scan
	 * @param sourceFields
	 * @return
	 */
	public static List<List<String>> getSourceDatas(String tableName, Scan scan, List<SourceField> sourceFields) {
		List<List<String>> sourceDatas = new ArrayList<List<String>>();
		try {
			HBaseDB db = HBaseDB.getInstance();
			Table table = db.getTable(tableName);
			ResultScanner resultScanner = table.getScanner(scan);
			Iterator<Result> iterator = resultScanner.iterator();
			while (iterator.hasNext()) {
				Result result = iterator.next();
				if (!result.isEmpty()) {
					List<String> sourceData = new ArrayList<>();
					// 获取行键sourceDataId
					sourceData.add(Bytes.toString(result.getRow()));
					for (SourceField sourceField : sourceFields) {
						sourceData.add(Bytes.toString(result.getValue(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),
								Bytes.toBytes(String.valueOf(sourceField.getCsf_id())))));
					}
					sourceDatas.add(sourceData);
				}
			}
			resultScanner.close();
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sourceDatas;
	}

	public static Map<String, Map<String, Object>> getSourceDatasByUidP(String cs_id, String uid,
			List<String> qualifiers, Integer page, Integer strip) {
		String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;

		Map<String, String> conditionEqual = new HashMap<>();
		conditionEqual.put(ConstantsHBase.QUALIFIER_USER, String.valueOf(uid));

		Map<String, String> conditionLike = new HashMap<>();
		String phoenixSQL = PhoenixClient.getPhoenixSQL(tableName, qualifiers, conditionEqual, conditionLike, null,
				page, strip);

		Map<String, Map<String, Object>> result = PhoenixClient.select(phoenixSQL);
		return result;
	}

	public static Map<String, Map<String, Object>> getSourceDatasCreatedP(String cs_id, String uid,
			List<String> qualifiers, Integer page, Integer strip) {
		String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;

		Map<String, String> conditionEqual = new HashMap<>();
		conditionEqual.put(ConstantsHBase.QUALIFIER_CREATE, String.valueOf(uid));

		Map<String, String> conditionLike = new HashMap<>();
		String phoenixSQL = PhoenixClient.getPhoenixSQL(tableName, qualifiers, conditionEqual, conditionLike, null,
				page, strip);

		Map<String, Map<String, Object>> result = PhoenixClient.select(phoenixSQL);
		return result;

	}
	
	public static Map<String, Map<String, Object>> getSourceDatasProjrct(String cs_id, String uid,
	List<String> qualifiers, Integer page, Integer strip) {
		String tableName= ConstantsHBase.TABLE_PREFIX_SOURCE_+cs_id;
		Map<String, String> conditionEqual = new HashMap<>();
		conditionEqual.put(ConstantsHBase.QUALIFIER_PROJECT, String.valueOf(uid));

		Map<String, String> conditionLike = new HashMap<>();
		String phoenixSQL = PhoenixClient.getPhoenixSQL(tableName, qualifiers, conditionEqual, conditionLike, null,
				page, strip);

		Map<String, Map<String, Object>> result = PhoenixClient.select(phoenixSQL);
		return result;
	}

	public static Map<String, Map<String, Object>> getSourceDatasPublicP(String cs_id, List<String> qualifiers,
			Integer page, Integer strip) {
		String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;

		Map<String, String> conditionEqual = new HashMap<>();
		conditionEqual.put(ConstantsHBase.QUALIFIER_PUBLIC, String.valueOf(ConstantsHBase.VALUE_PUBLIC_TRUE));

		Map<String, String> conditionLike = new HashMap<>();
		String phoenixSQL = PhoenixClient.getPhoenixSQL(tableName, qualifiers, conditionEqual, conditionLike, null,
				page, strip);

		Map<String, Map<String, Object>> result = PhoenixClient.select(phoenixSQL);
		return result;
	}

	public static List<List<String>> getSourceDatasByUid(String cs_id, String uid, List<SourceField> sourceFields,
			Integer currPage, Integer nextPage, Integer strip, String startRowStr) {
		if (currPage == null) {
			currPage = 0;
		}
		if (nextPage == null) {
			nextPage = 1;
		}
		if (strip == null) {
			strip = 12;
		}
		String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;
		Scan scan = new Scan();
		// 列簇约束结果集
		scan.addFamily(Bytes.toBytes(ConstantsHBase.FAMILY_INFO));
		// 前缀uid+"_"+source+"_"过滤
		Filter filter1 = new PrefixFilter(Bytes.toBytes(uid + "_" + cs_id + "_"));
		Filter filter2 = new PageFilter(strip);
		FilterList filterList = new FilterList(Operator.MUST_PASS_ALL, filter1, filter2);
		scan.setFilter(filterList);

		byte[] startRow;
		try {
			startRow = Bytes.toBytes(startRowStr);
		} catch (Exception e) {
			startRow = null;
		}
		byte[] POSTFIX = new byte[] { 0x00 };
		if (nextPage - currPage != 1) {
			startRow = HBaseDB.getStartRow(tableName, scan, startRow, nextPage - currPage - 1, strip);
		}
		if (startRow != null) {
			// 非第一页
			startRow = Bytes.add(startRow, POSTFIX);
			scan.withStartRow(startRow);
		}
		return getSourceDatas(tableName, scan, sourceFields);
	}

	public static List<List<String>> getSourceDatasCreated(String cs_id, String uid, List<SourceField> sourceFields,
			Integer page, Integer strip) {
		if (page == null) {
			page = 1;
		}
		if (strip == null) {
			strip = 1;
		}
		String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;
		Scan scan = new Scan();
		scan.addFamily(Bytes.toBytes(ConstantsHBase.FAMILY_INFO));
		// 前缀uid+"_"+source+"_"过滤
		Filter filter1 = new PrefixFilter(Bytes.toBytes(uid + "_" + cs_id + "_"));
		// 单值过滤,获取行数据
		Filter filter2 = new SingleColumnValueFilter(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),
				Bytes.toBytes(ConstantsHBase.QUALIFIER_ADD), CompareOp.EQUAL,
				new BinaryComparator(Bytes.toBytes(ConstantsHBase.VALUE_ADD_FALSE)));
		Filter filter3 = new PageFilter(strip);
		FilterList filterList = new FilterList(Operator.MUST_PASS_ALL, filter1, filter2, filter3);
		scan.setFilter(filterList);

		byte[] startRow = null;
		byte[] POSTFIX = new byte[] { 0x00 };
		startRow = HBaseDB.getStartRow(tableName, scan, startRow, page - 1, strip);
		if (startRow != null) {
			// 非第一页
			startRow = Bytes.add(startRow, POSTFIX);
			scan.withStartRow(startRow);
		}
		return getSourceDatas(tableName, scan, sourceFields);
	}

	public static List<List<String>> getSourceDatasPublic(String cs_id, List<SourceField> sourceFields, Integer page,
			Integer strip) {
		if (page == null) {
			page = 1;
		}
		if (strip == null) {
			strip = 1;
		}
		String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;
		Scan scan = new Scan();
		// 列簇约束结果集
		scan.addFamily(Bytes.toBytes(ConstantsHBase.FAMILY_INFO));
		// 单值过滤,获取行数据
		Filter filter1 = new SingleColumnValueFilter(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),
				Bytes.toBytes(ConstantsHBase.QUALIFIER_PUBLIC), CompareOp.EQUAL,
				new BinaryComparator(Bytes.toBytes(ConstantsHBase.VALUE_PUBLIC_TRUE)));
		Filter filter2 = new PageFilter(strip);
		FilterList filterList = new FilterList(Operator.MUST_PASS_ALL, filter1, filter2);
		scan.setFilter(filterList);
		byte[] startRow = null;
		byte[] POSTFIX = new byte[] { 0x00 };
		startRow = HBaseDB.getStartRow(tableName, scan, startRow, page - 1, strip);
		if (startRow != null) {
			// 非第一页
			startRow = Bytes.add(startRow, POSTFIX);
			scan.withStartRow(startRow);
		}
		return getSourceDatas(tableName, scan, sourceFields);

	}

	/**
	 * 通过sourceDataId获取一条源数据
	 * 
	 * @param cs_id
	 * @param sourceDataId
	 * @param sourceFields
	 * @return
	 */
	public static List<String> getSourceDataById(String cs_id, String sourceDataId, List<SourceField> sourceFields) {
		List<String> sourceData = new ArrayList<>();
		try {
			HBaseDB db = HBaseDB.getInstance();
			Table table = db.getTable(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id);
			Get get = new Get(Bytes.toBytes(sourceDataId));
			Result result = table.get(get);
			if (!result.isEmpty()) {
				// 获取行键sourceDataId
				sourceData.add(Bytes.toString(result.getRow()));
				for (SourceField sourceField : sourceFields) {
					sourceData.add(Bytes.toString(result.getValue(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),
							Bytes.toBytes(String.valueOf(sourceField.getCsf_id())))));
				}
			}
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sourceData;
	}

	public static List<List<String>> getSourceDatasByIds(String cs_id, String sourceDataIds,
			List<SourceField> sourceFields) {
		List<String> sourceDataIdList = new ArrayList<>();
		for (String sourceDataId : sourceDataIds.split(",")) {
			if (sourceDataId != null)
				sourceDataIdList.add(sourceDataId);
		}
		return getSourceDatasByIds(cs_id, sourceDataIdList, sourceFields);
	}

	
	/**
	 * 更新一条源数据
	 * 
	 * @param cs_id
	 *            采集源
	 * @param sourceDataId
	 * @param sourceFieldDatas
	 *            采集源字段、 数据值
	 */
	public static boolean updateSourceData(String cs_id, String sourceDataId, Map<String, String> sourceFieldDatas) {
		HBaseDB db = HBaseDB.getInstance();
		Put put = new Put(Bytes.toBytes(sourceDataId));
		for (Entry<String, String> sourceFieldData : sourceFieldDatas.entrySet()) {
			put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(sourceFieldData.getKey()),
					Bytes.toBytes(sourceFieldData.getValue()));
		}
		return db.putRow(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id, put);
	}

	/**
	 * 批量删除源数据
	 * 
	 * @param cs_id
	 * @param sourceDataIds
	 * @return
	 */
	public static boolean deleteSourceDatas(String cs_id, String sourceDataIds) {
		HBaseDB db = HBaseDB.getInstance();
		for (String sourceDataId : sourceDataIds.split(",")) {
			if (!db.delete(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id, sourceDataId)) {
				return false;
			}
		}
		return true;
	}

	public static boolean open(String cs_id, String sourceDataIds) {
		HBaseDB db = HBaseDB.getInstance();
		for (String sourceDataId : sourceDataIds.split(",")) {
			if (!db.putCell(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id, sourceDataId, ConstantsHBase.FAMILY_INFO,
					ConstantsHBase.QUALIFIER_PUBLIC, ConstantsHBase.VALUE_PUBLIC_TRUE)) {
				return false;
			}
		}
		return true;
	}

	public static boolean notopen(String cs_id, String sourceDataIds) {
		HBaseDB db = HBaseDB.getInstance();
		for (String sourceDataId : sourceDataIds.split(",")) {
			if (!db.putCell(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id, sourceDataId, ConstantsHBase.FAMILY_INFO,
					ConstantsHBase.QUALIFIER_PUBLIC, ConstantsHBase.VALUE_PUBLIC_FALSE)) {
				return false;
			}
		}
		return true;
	}

	public static boolean addMySource(String cs_id, String uid, String sourceDataIds, List<SourceField> sourceFields) {
		List<String> sourceDataIdList = new ArrayList<>();
		for (String sourceDataId : sourceDataIds.split(",")) {
			if (!sourceDataId.startsWith(uid + "_" + cs_id + "_"))
				sourceDataIdList.add(sourceDataId);
		}
		return addMySource(cs_id, uid, sourceDataIdList, sourceFields);
	}

	@SuppressWarnings("unchecked")
	public static boolean addMySource(String cs_id, String uid, List<String> sourceDataIds,
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
			for (Result result : results) {
				String oldSourceDataId = Bytes.toString(result.getRow());
				Long count = db.getNewId(ConstantsHBase.TABLE_GID, uid + "_" + cs_id, ConstantsHBase.FAMILY_GID_GID,
						ConstantsHBase.QUALIFIER_GID_GID_GID);
				String sourceDataId = uid + "_" + cs_id + "_" + count;
				Put put = new Put(Bytes.toBytes(sourceDataId));
				put.addColumn(Bytes.toBytes(family), Bytes.toBytes(ConstantsHBase.QUALIFIER_USER), Bytes.toBytes(uid));
				put.addColumn(Bytes.toBytes(family), Bytes.toBytes(ConstantsHBase.QUALIFIER_PUBLIC),
						Bytes.toBytes(ConstantsHBase.VALUE_PUBLIC_FALSE));
				put.addColumn(Bytes.toBytes(family), Bytes.toBytes(ConstantsHBase.QUALIFIER_ADD),
						Bytes.toBytes(ConstantsHBase.VALUE_ADD_TRUE));
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
					}
				} else {
					return false;
				}
			}
			table.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean addProject(String p_id, String cs_id, String uid, String sourceDataIds,
			List<SourceField> sourceFields) {
		List<String> sourceDataIdList = new ArrayList<>();
		for (String sourceDataId : sourceDataIds.split(",")) {
			sourceDataIdList.add(sourceDataId);
		}
		return addProject(p_id, cs_id, uid, sourceDataIdList, sourceFields);
	}

	@SuppressWarnings("unchecked")
	public static boolean addProject(String p_id, String cs_id, String uid, List<String> sourceDataIds,
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
				String sourceDataId = uid + "_" + cs_id + "_" + count;
				Put put = new Put(Bytes.toBytes(sourceDataId));
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
					// String oldSourceDataId = Bytes.toString(result.getRow());
					List<List<String>> formatNodes = HBaseFormatNodeDao.getFormatNodesBySourceDataId(cs_id,
							oldSourceDataId);
					for (List<String> formatNode : formatNodes) {
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

	/**
	 * 获取行键
	 * 
	 * @param tableName
	 * @param scan
	 * @return
	 */
	private static String getSourceDataId(String tableName, Scan scan) {
		String sourceDataId = null;
		try {
			HBaseDB db = HBaseDB.getInstance();
			Table table = db.getTable(tableName);
			ResultScanner resultScanner = table.getScanner(scan);
			Iterator<Result> iterator = resultScanner.iterator();
			if (iterator.hasNext()) {
				Result result = iterator.next();
				if (!result.isEmpty()) {
					// 获取行键sourceDataId
					sourceDataId = Bytes.toString(result.getRow());
				}
			}
			resultScanner.close();
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sourceDataId;
	}

	/**
	 * 前缀、列值过滤行键
	 * 
	 * @param uid
	 * @param cs_id
	 * @param sourceFieldDatas
	 * @return
	 */
	public static String getSourceDataId(String cs_id, String uid, Map<String, String> sourceFieldDatas) {

		String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;
		Scan scan = new Scan();
		// scan.addFamily(Bytes.toBytes(ConstantsHBase.FAMILY_INFO));
		// 前缀uid+"_"+source+"_"过滤
		Filter filter1 = new PrefixFilter(Bytes.toBytes(uid + "_" + cs_id + "_"));
		FilterList filterList = new FilterList(Operator.MUST_PASS_ALL, filter1);
		for (Entry<String, String> sourceFieldData : sourceFieldDatas.entrySet()) {
			filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),
					Bytes.toBytes(sourceFieldData.getKey()), CompareOp.EQUAL,
					new BinaryComparator(Bytes.toBytes(sourceFieldData.getValue()))));
		}
		scan.setFilter(filterList);
		return getSourceDataId(tableName, scan);
	}

	/**
	 * 列值过滤
	 * 
	 * @param cs_id
	 * @param oldSourceDataId
	 * @param p_id
	 * @return
	 */
	public static String getSourceDataIdIncludeNULL(String cs_id, Map<String, String> sourceFieldDatas) {
		String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;
		Scan scan = new Scan();
		FilterList filterList = new FilterList(Operator.MUST_PASS_ALL);
		for (Entry<String, String> sourceFieldData : sourceFieldDatas.entrySet()) {
			filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),
					Bytes.toBytes(sourceFieldData.getKey()), CompareOp.EQUAL,
					new BinaryComparator(Bytes.toBytes(sourceFieldData.getValue()))));
		}
		scan.setFilter(filterList);
		return getSourceDataId(tableName, scan);
	}

	// ---------------------------------
	public static List<List<String>> getSourceDatasByIds(String cs_id, List<String> sourceDataIds,
			List<SourceField> sourceFields) {
		List<List<String>> sourceDatas = new ArrayList<>();
		try {
			HBaseDB db = HBaseDB.getInstance();
			Table table = db.getTable(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id);
			List<Get> gets = new ArrayList<Get>();
			for (String sourceDataId : sourceDataIds) {
				if (sourceDataId != null)
					gets.add(new Get(Bytes.toBytes(sourceDataId)));
			}
			Result[] results = table.get(gets);
			for (Result result : results) {
				List<String> sourceData = new ArrayList<>();
				// 获取行键sourceDataId
				sourceData.add(Bytes.toString(result.getRow()));
				for (SourceField sourceField : sourceFields) {
					sourceData.add(Bytes.toString(result.getValue(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),
							Bytes.toBytes(String.valueOf(sourceField.getCsf_id())))));
				}
				sourceDatas.add(sourceData);
			}
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sourceDatas;
	}

	public static void deleteSourceDataTable(String cs_id) {
		HBaseDB.getInstance().deleteTable(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id);
	}
	
	

	public static boolean removeSourceDatas(String cs_id, String uid, String sourceDataIds) {
		HBaseDB db = HBaseDB.getInstance();
		for (String sourceDataId : sourceDataIds.split(",")) {
			if (!db.checkAndDelete(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id, sourceDataId,
					ConstantsHBase.FAMILY_INFO, ConstantsHBase.QUALIFIER_ADD, ConstantsHBase.VALUE_ADD_TRUE)) {
				return false;
			}
		}
		return true;
	}

	public static boolean removeSourceDatas(String cs_id, String uid, List<String> sourceDataIds) {
		HBaseDB db = HBaseDB.getInstance();
		for (String sourceDataId : sourceDataIds) {
			if (!db.checkAndDelete(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id, sourceDataId,
					ConstantsHBase.FAMILY_INFO, ConstantsHBase.QUALIFIER_ADD, ConstantsHBase.VALUE_ADD_TRUE)) {
				return false;
			}
		}
		return true;
	}
	
	
	
	
	
	
	
	
	//修改
	public static boolean updateData(String cs_id , String sourceDataId,
			Map<String, String> sourceFieldDatas) {
		HBaseDB db = HBaseDB.getInstance();
		String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id ;
		Put put = new Put(Bytes.toBytes(sourceDataId));
		for (Entry<String, String> sourceFieldData :sourceFieldDatas.entrySet()) {
			put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(sourceFieldData.getKey()),
					Bytes.toBytes(sourceFieldData.getValue()));
		}
		return db.putRow(tableName, put);
	}
	
	public static boolean updateDatas(String cs_id, String sourceDataId,
			Map<String, String> sourceFieldDatas) {
		boolean b = false;
		try {
			HBaseDB db = HBaseDB.getInstance();
			String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;
			Table table = db.getTable(tableName);
			Scan scan = new Scan();
			
			scan.addFamily(Bytes.toBytes(ConstantsHBase.FAMILY_INFO));
			
			Filter filter = new SingleColumnValueFilter(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),
					Bytes.toBytes(ConstantsHBase.QUALIFIER_SOURCEDATAID), CompareOp.EQUAL,
					new BinaryComparator(Bytes.toBytes(sourceDataId)));
			scan.setFilter(filter);

			ResultScanner resultScanner = table.getScanner(scan);
			Iterator<Result> iterator = resultScanner.iterator();
			List<byte[]> rowkeys = new ArrayList<>();
			while (iterator.hasNext()) {
				Result result = iterator.next();
				if (!result.isEmpty()) {
					rowkeys.add(result.getRow());
				}
			}

			resultScanner.close();
			for (byte[] rowkey : rowkeys) {
				Put put = new Put(rowkey);
				for (Entry<String, String> sourceFieldData : sourceFieldDatas.entrySet()) {
					put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(sourceFieldData.getKey()),
							Bytes.toBytes(sourceFieldData.getValue()));
				}
				if (db.putRow(tableName, put)) {
					b = true;
				} else {
					return false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
