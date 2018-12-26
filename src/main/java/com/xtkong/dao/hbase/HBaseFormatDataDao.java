/**
 * 格式数据
 * 
 * 
 */
package com.xtkong.dao.hbase;

import java.io.IOException;
import java.util.ArrayList;
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
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;

import com.xtkong.model.FormatField;
import com.xtkong.service.FormatFieldService;
import com.xtkong.service.PhoenixClient;
import com.xtkong.util.ConstantsHBase;
import com.xtkong.util.HBaseDB;

public class HBaseFormatDataDao {
	@Autowired
	static
	FormatFieldService formatFieldService;
	/**
	 * 新增格式类型：创建格式数据表
	 * 
	 * @param cs_id
	 *            采集源
	 * @param ft_id
	 *            格式类型
	 */
	public static void createFormatDataTable(String cs_id, String ft_id) {
		// 格式类型节点表
		List<String> qualifiers = new ArrayList<String>();
		qualifiers.add(ConstantsHBase.QUALIFIER_SOURCEDATAID);
		qualifiers.add(ConstantsHBase.QUALIFIER_FORMATNODEID);
		List<FormatField> fieldList = formatFieldService.getFormatFieldsByFormatTypeID(Integer.parseInt(ft_id));
		if(fieldList!=null&&fieldList.size()>0) {
			for(FormatField field:fieldList) {
				qualifiers.add(String.valueOf(field.getFf_id()));
			}
		}
		String tableName = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
		createFormatDataTable(tableName, new String[] { ConstantsHBase.FAMILY_INFO }, ConstantsHBase.VERSION_FORMAT);
		PhoenixClient.createView(tableName, qualifiers);
	}

	private static void createFormatDataTable(String tableName, String[] columnFamilies, int version) {
		HBaseDB db = HBaseDB.getInstance();
		db.createTable(tableName, columnFamilies, version);
	}

	/**
	 * 添加一条格式数据
	 * 
	 * @param cs_id
	 * @param ft_id
	 *            格式类型id
	 * @param sourceDataId
	 * @param formatNodeId
	 *            节点id
	 * @param formatFieldDatas
	 *            格式数据字段、 数据值
	 * @return
	 */
	public static String insertFormatData(String cs_id, String ft_id, String sourceDataId, String formatNodeId,
			Map<String, String> formatFieldDatas) {
		HBaseDB db = HBaseDB.getInstance();
		Long count = db.getNewId(ConstantsHBase.TABLE_GID, formatNodeId, ConstantsHBase.FAMILY_GID_GID,
				ConstantsHBase.QUALIFIER_GID_GID_GID);
		String tableName = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
		if(!HBaseDB.getInstance().existTable(tableName)) {
			createFormatDataTable(cs_id,ft_id);
		}
		String rowKey = formatNodeId + "_" + count;
		Put put = new Put(Bytes.toBytes(rowKey));
		for (Entry<String, String> formatFieldData : formatFieldDatas.entrySet()) {
			put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(formatFieldData.getKey()),
					Bytes.toBytes(formatFieldData.getValue()));
		}
		put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(ConstantsHBase.QUALIFIER_SOURCEDATAID),
				Bytes.toBytes(String.valueOf(sourceDataId)));
		put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(ConstantsHBase.QUALIFIER_FORMATNODEID),
				Bytes.toBytes(formatNodeId));

		if (db.putRow(tableName, put)) {
			return rowKey;
		}
		return null;
	}

	public static String insertFormatDataMeta(String cs_id, String ft_id, String sourceDataId, String formatNodeId,
			Map<String, String> formatFieldDatas) {
		HBaseDB db = HBaseDB.getInstance();
		String tableName = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
		if(!HBaseDB.getInstance().existTable(tableName)) {
			createFormatDataTable(cs_id,ft_id);
		}
		String rowKey = formatNodeId;
		Put put = new Put(Bytes.toBytes(rowKey));
		if (formatFieldDatas != null) {
			for (Entry<String, String> formatFieldData : formatFieldDatas.entrySet()) {
				put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(formatFieldData.getKey()),
						Bytes.toBytes(formatFieldData.getValue()));
			}
		}
		put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(ConstantsHBase.QUALIFIER_SOURCEDATAID),
				Bytes.toBytes(sourceDataId));
		put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(ConstantsHBase.QUALIFIER_FORMATNODEID),
				Bytes.toBytes(formatNodeId));
		if (db.putRow(tableName, put)) {
			return rowKey;
		}
		return null;
	}

	/**
	 * 列值过滤行键
	 * 
	 * @param uid
	 * @param cs_id
	 * @param formatFieldDatas
	 * @return
	 */
	public static String getFormatDataId(String cs_id, String ft_id, String formatNodeId,
			Map<String, String> formatFieldDatas) {
		String tableName = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
		String prefixFilter = formatNodeId + "_";
		String formatDataId = null;
		HBaseDB db = HBaseDB.getInstance();
		List<String> rowkeys = db.getRowkeys(tableName, prefixFilter, formatFieldDatas);
		if (!rowkeys.isEmpty()) {
			formatDataId = rowkeys.get(0);
		}
		return formatDataId;
	}

	@SuppressWarnings("unchecked")
	public static List<String> getFormatDataIds(String cs_id, String ft_id, String oldFormatNodeId) {
		List<String> formatDataIds = new ArrayList<>();
		try {
				String tableStr = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
				Map<String, Map<String, Object>> records = PhoenixClient.select("SELECT * FROM \"" + tableStr
						+ "\" WHERE ID!='" + oldFormatNodeId + "' AND " + "\"" + ConstantsHBase.FAMILY_INFO + "\".\""
						+ ConstantsHBase.QUALIFIER_FORMATNODEID + "\"='" + oldFormatNodeId + "' ");
				List<String> head = (List<String>) records.get("records").get("head");
				List<List<String>> datas = (List<List<String>>) records.get("records").get("data");
				for (List<String> data : datas) {
					for (int i = 0; i < head.size(); i++) {
						try {
							if (head.get(i).equals("ID")) {
								formatDataIds.add(data.get(i));
							}
						} catch (Exception e) {
							continue;
						}
					}
			}
		} catch (Exception e) {
			return null;
		}
		return formatDataIds;
	}

	/**
	 * 格式数据明细共用部分
	 * 
	 * @param cs_id
	 * @param ft_id
	 * @param formatNodeId
	 * @param formatFields
	 * @return formatDatas[1]:id,名，值
	 */
	public static List<List<String>> getFormatDataMetas(String cs_id, String ft_id, String formatNodeId,
			List<FormatField> formatFields) {
		List<List<String>> formatDatas = new ArrayList<>();
		try {
			HBaseDB db = HBaseDB.getInstance();
			Table table = db.getTable(ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id);
			Get get = new Get(Bytes.toBytes(formatNodeId));
			Result result = table.get(get);
			if (!result.isEmpty()) {
				for (FormatField formatField : formatFields) {
					List<String> formatData = new ArrayList<>();
					formatData.add(String.valueOf(formatField.getFf_id()));//格式字段ID
					formatData.add(formatField.getFf_name());//格式字段名
					formatData.add(Bytes.toString(result.getValue(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),
							Bytes.toBytes(String.valueOf(formatField.getFf_id())))));
					formatDatas.add(formatData);
				}
			}
			table.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return formatDatas;
	}

	public static List<List<String>> getFormatDataByIds(String cs_id, String ft_id, String formatDataIds,
			List<FormatField> formatFields) {
		List<List<String>> formatDatas = new ArrayList<>();
		try {
			HBaseDB db = HBaseDB.getInstance();
			Table table = db.getTable(ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id);
			List<Get> gets = new ArrayList<Get>();
			for (String formatDataId : formatDataIds.split(",")) {
				gets.add(new Get(Bytes.toBytes(formatDataId)));
			}
			Result[] results = table.get(gets);
			for (Result result : results) {
				List<String> formatData = new ArrayList<>();
				for (FormatField formatField : formatFields) {
					formatData.add(Bytes.toString(result.getValue(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),
							Bytes.toBytes(String.valueOf(formatField.getFf_id())))));
				}
				formatDatas.add(formatData);
			}
			table.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return formatDatas;
	}

	/**
	 * 
	 * 格式数据明细
	 * 
	 * @param cs_id
	 *            采集源id
	 * @param ft_id
	 *            格式类型id
	 * @param formatNodeId
	 *            节点id
	 * @param formatFields
	 *            格式字段
	 * @param page
	 * @param strip
	 * @return 格式数据明细数据，注：每个列表第一个值formatDataId不显示
	 */
	public static List<List<String>> getFormatDatas(String cs_id, String ft_id, String formatNodeId,
			List<FormatField> formatFields, Integer page, Integer strip) {
		if (page == null) {
			page = 1;
		}
		if (strip == null) {
			strip = 1;
		}
		List<List<String>> formatDatas = new ArrayList<>();
		try {
			String tableName = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;//FORMAT_采集源id_格式类型id
			HBaseDB db = HBaseDB.getInstance();
			Table table = db.getTable(tableName);
			Scan scan = new Scan();
			// 列簇约束结果集
			scan.addFamily(Bytes.toBytes(ConstantsHBase.FAMILY_INFO));
			// 前缀formatNodeId_过滤
			Filter filter1 = new PrefixFilter(Bytes.toBytes(formatNodeId + "_"));
			Filter filter2 = new PageFilter(strip);
			FilterList filterList = new FilterList(Operator.MUST_PASS_ALL, filter1, filter2);//Operator.MUST_PASS_ALL ---> and
			scan.setFilter(filterList);
			byte[] startRow = null;//  从头开始 
			byte[] POSTFIX = new byte[] { 0x00 }; 
			startRow = HBaseDB.getStartRow(tableName, scan, startRow, page - 1, strip);
			if (startRow != null) {
				// 非第一页
				startRow = Bytes.add(startRow, POSTFIX);
				scan.withStartRow(startRow);
			}
			ResultScanner resultScanner = table.getScanner(scan);
			Iterator<Result> iterator = resultScanner.iterator();
			while (iterator.hasNext()) {
				Result result = iterator.next();
				if (!result.isEmpty()) {
					List<String> formatData = new ArrayList<>();
					formatData.add(Bytes.toString(result.getRow()));// 获取行键formatDataId，不显示
					for (FormatField formatField : formatFields) {
						formatData.add(Bytes.toString(result.getValue(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),
								Bytes.toBytes(String.valueOf(formatField.getFf_id())))));
					}
					formatDatas.add(formatData);
				}
			}
			resultScanner.close();
			table.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return formatDatas;
	}

	public static List<List<String>> getFormatDatas(String cs_id, String ft_id, String formatNodeId,
			List<FormatField> formatFields) {
		List<List<String>> formatDatas = new ArrayList<>();
		try {
			HBaseDB db = HBaseDB.getInstance();
			Table table = db.getTable(ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id);
			Scan scan = new Scan();
			// 列簇约束结果集
			scan.addFamily(Bytes.toBytes(ConstantsHBase.FAMILY_INFO));
			// 前缀formatNodeId_过滤
			Filter filter = new PrefixFilter(Bytes.toBytes(formatNodeId + "_"));
			scan.setFilter(filter);
			ResultScanner resultScanner = table.getScanner(scan);
			Iterator<Result> iterator = resultScanner.iterator();
			while (iterator.hasNext()) {
				Result result = iterator.next();
				if (!result.isEmpty()) {
					List<String> formatData = new ArrayList<>();
					formatData.add(Bytes.toString(result.getRow()));// 获取行键formatDataId，不显示
					for (FormatField formatField : formatFields) {
						formatData.add(Bytes.toString(result.getValue(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),
								Bytes.toBytes(String.valueOf(formatField.getFf_id())))));
					}
					formatDatas.add(formatData);
				}
			}
			resultScanner.close();
			table.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return formatDatas;
	}

	/**
	 * 更新一条格式数据
	 * 
	 * @param cs_id
	 * @param ft_id
	 *            格式类型id
	 * @param sourceDataId
	 * @param formatNodeId
	 * @param formatDataId
	 * @param formatFieldDatas
	 *            格式数据字段、 数据值
	 * @return
	 */
	public static boolean updateFormatData(String cs_id, String ft_id, String formatDataId,
			Map<String, String> formatFieldDatas) {
		HBaseDB db = HBaseDB.getInstance();
		String tableName = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
		Put put = new Put(Bytes.toBytes(formatDataId));
		for (Entry<String, String> formatFieldData : formatFieldDatas.entrySet()) {
			put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(formatFieldData.getKey()),
					Bytes.toBytes(formatFieldData.getValue()));
		}
		return db.putRow(tableName, put);
	}

	public static boolean updateFormatDatas(String cs_id, String ft_id, String formatNodeId,
			Map<String, String> formatFieldDatas) {
		boolean b = false;
		try {
			HBaseDB db = HBaseDB.getInstance();
			String tableName = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
			Table table = db.getTable(tableName);
			Scan scan = new Scan();
			// 列簇约束结果集
			scan.addFamily(Bytes.toBytes(ConstantsHBase.FAMILY_INFO));
			// 值formatNodeId过滤
			Filter filter = new SingleColumnValueFilter(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),
					Bytes.toBytes(ConstantsHBase.QUALIFIER_FORMATNODEID), CompareOp.EQUAL,
					new BinaryComparator(Bytes.toBytes(formatNodeId)));
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
				for (Entry<String, String> formatFieldData : formatFieldDatas.entrySet()) {
					put.addColumn(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(formatFieldData.getKey()),
							Bytes.toBytes(formatFieldData.getValue()));
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
	
	
	
	

	/**
	 * 批量删除格式数据
	 * 
	 * @param cs_id
	 * @param ft_id
	 * @param formatDataIds
	 * @return
	 */
	public static boolean deleteFormatDatas(String cs_id, String ft_id, String formatDataIds) {
		HBaseDB db = HBaseDB.getInstance();
		for (String formatDataId : formatDataIds.split(",")) {
			if (!db.delete(ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id, formatDataId)) {
				return false;
			}
		}
		return true;
	}

	public static void deleteFormatDataTable(String cs_id, String ft_id) {
		HBaseDB.getInstance().deleteTable(ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id);

	}

}
