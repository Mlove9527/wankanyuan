/**
 * HBaseDB工具类
 * */
package com.xtkong.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

public class HBaseDB {
	private static final Logger logger  =  Logger.getLogger(HBaseDB.class );

	// 避免多线程导致生成多个实例
	private static final HBaseDB hBaseDBTool = new HBaseDB();
	private static Connection connection = null;
	static {
		Configuration configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.quorum", ConstantsHBase.HBASE_ZOOKEEPER_QUORUM);
		configuration.set("hbase.master", ConstantsHBase.HBASE_MASTER);
		configuration.set("hbase.regionserver", ConstantsHBase.HBASE_REGIONSERVER);
		//configuration.set("hbase.zookeeper.property.clientPort", ConstantsHBase.HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT);
		//configuration.set("hbase.master.info.port", ConstantsHBase.HBASE_MASTER_INFO_PORT);
		try {
			connection = ConnectionFactory.createConnection(configuration);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static HBaseDB getInstance() {
		return hBaseDBTool;
	}

	/***
	 * HBase表是否存在***/
	public boolean existTable(String tableName) {
		boolean flag = false;
		try {
			Admin admin = connection.getAdmin();
			TableName tableNameObject = TableName.valueOf(tableName);
			if (admin.tableExists(tableNameObject)) {
				flag = true;
			} 
			admin.close();
		} catch (Exception e) {
		}
		return flag;
	}
	
	/**
	 * 创建HBase表
	 * 
	 * @param tableName
	 *            表名称
	 * @param columnFamilies
	 *            列簇，可能有多个
	 * @param version
	 *            列表存储的版本数量
	 */
	public void createTable(String tableName, String[] columnFamilies, int version) {
		try {
			Admin admin = connection.getAdmin();
			TableName tableNameObject = TableName.valueOf(tableName);
			if (admin.tableExists(tableNameObject)) {
			} else {
				HTableDescriptor tableDescriptor = new HTableDescriptor(tableNameObject);
				for (String columnFamily : columnFamilies) {
					// 添加列簇
					tableDescriptor.addFamily(new HColumnDescriptor(columnFamily).setMaxVersions(version));
				}
				admin.createTable(tableDescriptor);
			}
			admin.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 删除HBase表
	 * 
	 * @param tableName
	 *            表名称
	 */
	public void deleteTable(String tableName) {
		try {
			Admin admin = connection.getAdmin();
			TableName tableNameObject = TableName.valueOf(tableName);
			if (admin.tableExists(tableNameObject)) {
				admin.disableTable(tableNameObject);
				admin.deleteTable(tableNameObject);
			} else {
			}
			admin.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 根据表名获取Table对象
	 * 
	 * @param tableName
	 *            表名称
	 */
	public Table getTable(String tableName) {
		Table table = null;
		try {
			Admin admin = connection.getAdmin();
			TableName tableNameObject = TableName.valueOf(tableName);
			table = connection.getTable(tableNameObject);
			admin.close();
		} catch (Exception e) {
		}
		return table;
	}

	/**
	 * 获取一个计数器的值，作为表的行键 计数器表已创建，列簇、列已知
	 * 
	 * @param tableName
	 *            表名称
	 * @param rowKey
	 *            行键
	 * @param family
	 *            列簇
	 * @param qualifier
	 *            列
	 */
	public Long getNewId(String tableName, String rowKey, String family, String qualifier) {
		long id = 0;
		int tryCnt=0;
		do {
			try {
				Table table = getTable(tableName);
				id = table.incrementColumnValue(Bytes.toBytes(rowKey), Bytes.toBytes(family), Bytes.toBytes(qualifier), 1);
				table.close();
				tryCnt=5;
			} catch (Exception e) {
				if (e instanceof TableNotFoundException) {
					String cf[]=new String[]{family};
					HBaseDB.getInstance().createTable(tableName,cf,1);
				}
				else
				{
					logger.error(e);
				}
				tryCnt++;
			}
		}
		while(tryCnt<5);
		return id;
	}

	/**
	 * 向表中添加一条数据 Object.toString()
	 * 
	 * @param tableName
	 *            表名称
	 * @param rowKey
	 *            行键
	 * @param family
	 *            列簇
	 * @param quelifier
	 *            列
	 * @param value
	 *            列值
	 */
	public boolean putCell(Object tableName, Object rowKey, Object family, Object quelifier, Object value) {
		try {
			Table table = getTable(tableName.toString());
			// 设置行键
			Put put = new Put(Bytes.toBytes(rowKey.toString()));
			put.addColumn(Bytes.toBytes(family.toString()), Bytes.toBytes(quelifier.toString()),
					Bytes.toBytes(value.toString()));
			table.put(put);
			System.out.println("tableName:" + tableName + ",rowKey:" + rowKey + ",family:" + family + ",quelifier:"
					+ quelifier + ",value:" + value);
			table.close();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean putRow(Object tableName, List<Put> puts) {
		try {
			Table table = getTable(tableName.toString());
			table.put(puts);
			table.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean putRow(Object tableName, Put put) {
		try {
			Table table = getTable(tableName.toString());
			table.put(put);
			table.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 列值过滤，获取行键
	 * 
	 * @param tableName
	 * @param prefixFilter
	 *            行键前缀
	 * @param columnValueFilters
	 *            列、值
	 * @return
	 */
	public List<String> getRowkeys(String tableName, String prefixFilter, Map<String, String> columnValueFilters) {
		Scan scan = new Scan();
		scan.addFamily(Bytes.toBytes(ConstantsHBase.FAMILY_INFO));
		// 前缀uid+"_"+source+"_"过滤
		Filter filter1 = new PrefixFilter(Bytes.toBytes(prefixFilter));
		FilterList filterList = new FilterList(Operator.MUST_PASS_ALL, filter1);
		for (Entry<String, String> columnValueFilter : columnValueFilters.entrySet()) {
			filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(ConstantsHBase.FAMILY_INFO),
					Bytes.toBytes(columnValueFilter.getKey()), CompareOp.EQUAL,
					new BinaryComparator(Bytes.toBytes(columnValueFilter.getValue()))));
		}
		scan.setFilter(filterList);

		return getRowkey(tableName, scan);
	}

	/**
	 * 获取行键
	 * 
	 * @param tableName
	 * @param scan
	 * @return
	 */
	public List<String> getRowkey(String tableName, Scan scan) {
		List<String> rowkey = new ArrayList<>();
		try {
			Table table = getTable(tableName);
			ResultScanner resultScanner = table.getScanner(scan);
			Iterator<Result> iterator = resultScanner.iterator();
			while (iterator.hasNext()) {
				Result result = iterator.next();
				if (!result.isEmpty()) {
					// 获取行键
					rowkey.add(Bytes.toString(result.getRow()));
				}
			}
			resultScanner.close();
			table.close();
		} catch (IOException e) {
		}

		return rowkey;
	}

	/**
	 * 根据扫描条件Scan，获取行键及列值
	 * 
	 * @param tableName
	 *            表名
	 * @param scan
	 *            扫描条件
	 * @param qualifiers
	 *            列名
	 * @return 行键、列值、列值、列值
	 */
	public static List<List<String>> getQuelifierValues(String tableName, Scan scan, List<String> qualifiers) {
		List<List<String>> quelifierValues = new ArrayList<List<String>>();
		try {
			HBaseDB db = HBaseDB.getInstance();
			Table table = db.getTable(tableName);
			ResultScanner resultScanner = table.getScanner(scan);
			Iterator<Result> iterator = resultScanner.iterator();
			while (iterator.hasNext()) {
				Result result = iterator.next();
				if (!result.isEmpty()) {
					List<String> quelifierValue = new ArrayList<>();
					// 获取行键
					quelifierValue.add(Bytes.toString(result.getRow()));
					for (String qualifier : qualifiers) {
						quelifierValue.add(Bytes.toString(
								result.getValue(Bytes.toBytes(ConstantsHBase.FAMILY_INFO), Bytes.toBytes(qualifier))));
					}
					quelifierValues.add(quelifierValue);
				}
			}
			resultScanner.close();
			table.close();
		} catch (IOException e) {
		}
		return quelifierValues;
	}

	/**
	 * 从表中删除一条数据 Object.toString()
	 * 
	 * @param tableName
	 * @param rowKey
	 * @return
	 */
	public boolean delete(Object tableName, Object rowKey) {
		try {
			Table table = getTable(tableName.toString());
			// 设置行键
			Delete delete = new Delete(Bytes.toBytes(rowKey.toString()));
			table.delete(delete);
			table.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean checkAndDelete(Object tableName, Object rowKey, Object family, Object quelifier, Object value) {
		try {
			Table table = getTable(tableName.toString());
			Delete delete = new Delete(Bytes.toBytes(rowKey.toString()));
			table.checkAndDelete(Bytes.toBytes(rowKey.toString()), Bytes.toBytes(family.toString()),
					Bytes.toBytes(quelifier.toString()), Bytes.toBytes(value.toString()), delete);
			table.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 跳过页
	 * 
	 * @param tableName
	 * @param scan
	 * @param startRow
	 * @param pages
	 * @param strip
	 * @return
	 */
	public static byte[] getStartRow(String tableName, Scan scan, byte[] startRow, Integer pages, Integer strip) {
		try {
			HBaseDB db = HBaseDB.getInstance();
			Table table = db.getTable(tableName);

			byte[] POSTFIX = new byte[] { 0x00 };
			for (int i = 0; i < pages; i++) {
				if (startRow != null) {
					// 非第一页
					startRow = Bytes.add(startRow, POSTFIX);
					scan.withStartRow(startRow);
				}
				ResultScanner resultScanner = table.getScanner(scan);
				Result[] results = resultScanner.next(strip);
				// 记录上一页的最后一条数据的行键值
				startRow = results[results.length - 1].getRow();
				resultScanner.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return startRow;
	}
	
	
	
	public String doubleSpread(String all_select, String now_selected, String no_selected) {  
        //获取当前选中的项目加入al  
        List al = new ArrayList();  
        if(all_select != "" && all_select != null)  
        {  
            String[] all_select_str = all_select.split(",");  
            for(int a=0;a<all_select_str.length;a++)  
            {  
                al.add(all_select_str[a]);  
            }  
        }  
          
        //将当前选中项目加入列表  
        if (now_selected != "" && now_selected != null) {  
            String[] all_now_select = now_selected.split(",");  
            for (int i = 1; i < all_now_select.length; i++) {  
                if (!al.contains(all_now_select[i])) {  
                    al.add(all_now_select[i]);  
                }  
            }  
        }  
          
        //将当前未选中项目从列表中删除  
        if (no_selected != "" && no_selected != null) {  
            String[] all_now_no_select = no_selected.split(",");  
            for (int i = 1; i < all_now_no_select.length; i++) {  
                if (al.contains(all_now_no_select[i])) {  
                    al.remove(all_now_no_select[i]);  
                }  
            }  
        }  
          
        all_select=al.toString().replaceAll(" +","");;  
        all_select=all_select.substring(1,all_select.length()-1)+",";  
          
        return all_select;  
    }  

}
