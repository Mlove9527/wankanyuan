package com.xtkong.tools;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenEntityMysql {

	private String filePath = "F:/workspace/eclipseTemp/wankangyuan/src/main/java/com/dzjin/model";
	private String packageOutPath = "com.dzjin.model";// 指定实体生成所在包的路径
	private String authorName = "dzjin";// 作者名字
	private String tablename = "user";// 表名
	// 数据库连接
	private static final String URL = "jdbc:mysql://140.143.36.150:3306/wankangyuan";
	private static final String NAME = "root";
	private static final String PASS = "root";
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	/**
	 * 出口 TODO
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//String[] tablename = {"meeting","article","i_e_file","i_e_group","group_user"
				//,"student","teacher","user_role","menu","role_menu","role"};
		String[] tablename = {"project_user"};
		System.out.println("共"+tablename.length+"张数据表");
		for (int i = 0; i < tablename.length; i++) {
			GenEntityMysql g = new GenEntityMysql();
			g.setTablename(tablename[i]);
			g.GenEntity();
		}
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	private String[] colnames; // 列名数组
	private String[] colTypes; // 列名类型数组
	private int[] colSizes; // 列名大小数组
	private boolean f_util = false; // 是否需要导入包java.util.*
	private boolean f_sql = false; // 是否需要导入包java.sql.*

	/*
	 * 构造函数
	 */
	public void GenEntity() {
		// 创建连接
		Connection con;
		// 查要生成实体类的表
		String sql = "select * from `" + tablename + "`";
		System.out.println(sql);
		PreparedStatement pStemt = null;
		try {
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			con = DriverManager.getConnection(URL, NAME, PASS);
			pStemt = con.prepareStatement(sql);
			ResultSetMetaData rsmd = pStemt.getMetaData();
			int size = rsmd.getColumnCount(); // 统计列
			colnames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			for (int i = 0; i < size; i++) {
				colnames[i] = rsmd.getColumnName(i + 1);
				colTypes[i] = rsmd.getColumnTypeName(i + 1);

				if (colTypes[i].equalsIgnoreCase("datetime")) {
					f_util = true;
				}
				if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")) {
					f_sql = true;
				}
				colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
			}

			String content = parse(colnames, colTypes, colSizes);

			try {
				String path = this.getClass().getResource("").getPath();

				System.out.println(path);
				System.out.println("src/?/" + path.substring(path.lastIndexOf("/com/", path.length())));
				String outputPath = filePath + "/" + initcap(tablename) + ".java";
				FileWriter fw = new FileWriter(outputPath);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(content);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * 功能：生成实体类主体代码
	 * 
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @return
	 */
	private String parse(String[] colnames, String[] colTypes, int[] colSizes) {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + this.packageOutPath + ";\r\n");
		sb.append("\r\n");
		// 判断是否导入工具包
		if (f_util) {
			sb.append("import java.util.Date;\r\n");
		}
		if (f_sql) {
			sb.append("import java.sql.*;\r\n");
		}

		// 注释部分
		sb.append("	/**\r\n");
		sb.append("	* 表名：" + tablename + "\r\n");
		sb.append("	* 作者：" + this.authorName + "\r\n");
		sb.append("	* 联系方式：dzjin5678@163.com\r\n");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sb.append("	* 创建时间：" + simpleDateFormat.format(new Date()) + "\r\n");
		sb.append("	*/\r\n");
		// 实体部分
		sb.append("\r\npublic class " + initcap(tablename) + " {\n\n");
		processAllAttrs(sb);// 属性
		sb.append("\n");
		processAllMethod(sb);// get set方法
		sb.append("\n");
		processToString(sb);
		sb.append("}\r\n");

		return sb.toString();
	}

	/**
	 * 功能：生成所有属性
	 * 
	 * @param sb
	 */
	private void processAllAttrs(StringBuffer sb) {
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + colnames[i] + ";\r\n");
		}
	}

	/**
	 * 功能：生成所有方法
	 * 
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {

		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tpublic void set" + initcap(colnames[i]) + "(" + sqlType2JavaType(colTypes[i]) + " "
					+ colnames[i] + "){\r\n");
			sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
			sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initcap(colnames[i]) + "(){\r\n");
			sb.append("\t\treturn " + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
		}

	}
	
	/**
	 * 重写toString()方法
	 * @param sb
	 */
	private void processToString(StringBuffer sb){
		
		sb.append("\tpublic String toString(){\n");
		sb.append("\t\treturn \"\\n"+tablename+"[\"+\n");
		for(int i = 0 ; i < colnames.length ; i++){
			sb.append("\t\t\t\"\\n   "+colnames[i]+"=\"+"+colnames[i]+"+\",\"+\n");
		}
		sb.append("\t\t\t\"\\n]\\n\";\n");
		sb.append("\t}\n");
		
	}

	/**
	 * 功能：将输入字符串的首字母改成大写，并将英文下滑线后面的第一个字母改成大写，删除掉下划线
	 * @param str
	 * @return
	 */
	private String initcap(String str) {

		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		for( int i = 0 ; i < ch.length ; i++){
			if(ch[i] == '_'){
				if (ch[i+1] >= 'a' && ch[i+1] <= 'z') {
					ch[i+1] = (char) (ch[i+1] - 32);
				}
			}
		}
		String result = new String(ch);
		result = result.replace("_", "");
		return result;
	}

	/**
	 * 功能：获得列的数据类型
	 * @param sqlType
	 * @return
	 */
	private String sqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("bit")) {
			return "boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "int";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "float";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "Blod";
		}
		
		//无法识别的一律用String
		return "String";
	}

}