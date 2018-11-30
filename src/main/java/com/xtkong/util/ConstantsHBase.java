/**
 * HBase常量参数
 * */
package com.xtkong.util;

import java.util.Properties;

public class ConstantsHBase {

	public static String HBASE_ZOOKEEPER_QUORUM = "60.29.25.134:52181,60.29.25.134:52182";
	public static String HBASE_MASTER = "60.29.25.134:56000,60.29.25.134:56001";
	public static String HBASE_REGIONSERVER = "60.29.25.134:56020,60.29.25.134:56021";
	public static String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT = "52181";
	public static String HBASE_MASTER_INFO_PORT = "56010";
	public static String PhoenixDriver = "org.apache.phoenix.jdbc.PhoenixDriver";
	public static String PhoenixJDBC = "jdbc:phoenix:hpc03:52181";
	

	/** 表名前缀 */
	public static final String TABLE_PREFIX_SOURCE_ = "SOURCE_";// 采集源
	public static final String TABLE_PREFIX_NODE_ = "NODE_";// 节点
	public static final String TABLE_PREFIX_FORMAT_ = "FORMAT_";// 格式类型
	/** 表名称 */
	public static final String TABLE_GID = "GID";// 计数
	/** 各表列簇名 FAMILY_表名_列簇名 */
	public static final String FAMILY_GID_GID = "GID";
	public static final String FAMILY_INFO = "INFO";
	/** 各表列名 */
	public static final String QUALIFIER_GID_GID_GID = "GID";
	// source
	public static final String QUALIFIER_PROJECT = "PROJECT";// 项目
	
	public static final String QUALIFIER_CREATE = "CREATE";// 我创建的
	public static final String QUALIFIER_USER = "USER";// 我的
	public static final String QUALIFIER_PUBLIC = "PUBLIC";// 公共，1公共，0不公开（默认）
	public static final String QUALIFIER_CREATOR = "1";// 
	public static final String QUALIFIER_CREATE_DATETIME = "2";// 
	// node
	public static final String QUALIFIER_FORMATTYPE = "FORMATTYPE";// 结点格式类型
	public static final String QUALIFIER_NODENAME = "NODENAME";// 结点名
	// source,format,node
	public static final String QUALIFIER_SOURCEDATAID = "SOURCEDATAID";// 源数据id
	// format
	public static final String QUALIFIER_FORMATNODEID = "FORMATNODEID";// 结点id

	public static final String QUALIFIER_NODE = "NODE";
	public static final String QUALIFIER_ADD = "ADD";// 添加

	/** 值 */

	public static final String VALUE_ADD_TRUE = "1";// 1添加数据
	public static final String VALUE_ADD_FALSE = "0";// 0自建数据（默认）

	public static final String VALUE_PUBLIC_TRUE = "已公开";// 1公共
	public static final String VALUE_PUBLIC_FALSE = "未公开";// 0不公开（默认）
	/** 存储版本 */
	public static final int VERSION_SOURCE = 1;
	public static final int VERSION_NODE = 1;
	public static final int VERSION_FORMAT = 1;

	/***/
	public static final int IS_meta_true = 1;// meta类型
	public static final int IS_meta_false = 0;// data类型
	// 数据参数
	// 扫描器最大缓冲，一次扫描请求返回数据量
	public static final int SCAN_CACHING_MAX = 50;
	
	static {
		try {			
			Properties properties = new Properties();
			try {
				properties.load(ConstantsHBase.class.getClass().getResourceAsStream("/config.properties"));
			} catch (Exception e) {
				try {
					properties.load(ConstantsHBase.class.getClassLoader().getResourceAsStream("/config.properties"));
				} catch (Exception e1) {
					properties.load(ConstantsHBase.class.getResourceAsStream("/config.properties"));
				}
			}

			String hbaseZookeeperQuorum = properties.getProperty("hbase.zookeeper.quorum");
			if (hbaseZookeeperQuorum != null) {
				ConstantsHBase.HBASE_ZOOKEEPER_QUORUM = hbaseZookeeperQuorum;
			}
			String hbaseMaster = properties.getProperty("hbase.master");
			if (hbaseMaster != null) {
				ConstantsHBase.HBASE_MASTER = hbaseMaster;
			}
			String hbaseRegionServer = properties.getProperty("hbase.regionserver");
			if (hbaseRegionServer != null) {
				ConstantsHBase.HBASE_REGIONSERVER = hbaseRegionServer;
			}
			String hbasezookeeperpropertyclientPort = properties.getProperty("hbase.zookeeper.property.clientPort");
			if (hbasezookeeperpropertyclientPort != null) {
				ConstantsHBase.HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT = hbasezookeeperpropertyclientPort;
			}
			String hbasemasterinfoport = properties.getProperty("hbase.master.info.port");
			if (hbasemasterinfoport != null) {
				ConstantsHBase.HBASE_MASTER_INFO_PORT = hbasemasterinfoport;
			}
			String phoenixDriver = properties.getProperty("phoenixDriver");
			if (phoenixDriver != null) {
				ConstantsHBase.PhoenixDriver = phoenixDriver;
			}
			String phoenix_JDBC = properties.getProperty("phoenix_JDBC");
			if (phoenix_JDBC != null) {
				ConstantsHBase.PhoenixJDBC = phoenix_JDBC;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("初始化配置文件失败！");
		}
	}

}
