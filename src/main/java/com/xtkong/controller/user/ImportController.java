package com.xtkong.controller.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.xtkong.model.FormatFile;
import com.xtkong.service.*;
import com.xtkong.util.MyFileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.liutianjun.pojo.User;
import com.xtkong.dao.hbase.HBaseFormatDataDao;
import com.xtkong.dao.hbase.HBaseSourceDataDao;
import com.xtkong.model.FormatField;
import com.xtkong.model.SourceField;
import com.xtkong.util.ConstantsHBase;

@Controller
@RequestMapping("/import")
public class ImportController {
	private static final Logger logger =  Logger.getLogger(ImportController.class );
	@Autowired
	SourceService sourceService;
	@Autowired
	SourceFieldService sourceFieldService;
	@Autowired
	FormatTypeService formatTypeService;
	@Autowired
	FormatFieldService formatFieldService;

	@Autowired
	FormatFileService formatFileService;

	@Value("${formatData.file.tmp}")
	private String dataFileTmpLocation;
	@Value("${formatData.file.location}")
	private String dataFileFinalLocation;

	private boolean ifFileExists(String username,String fileName)
	{
		return new File(dataFileTmpLocation+File.separatorChar+username+File.separatorChar+fileName).exists();
	}

	private boolean ifImgExists(String username,String fileName)
	{
		return new File(dataFileTmpLocation+File.separatorChar+username+File.separatorChar+fileName).exists();
	}

	private String validateData(String val,SourceField csf,String username) throws Exception
	{
		if(val==null || val.trim().isEmpty())
		{
			//如果字段为非空,但是值为空
			if(csf.isNot_null())
			{
				throw new Exception(csf.getError_msg());
			}

			return "";
		}
		else
		{
			if(csf.getType().equals("图片"))
			{
				if(!ifImgExists(username,val.trim()))
				{
					//如果字段可以为空,即使数据是非法的也不会报错,只是数据当做空
//					if(!csf.isNot_null())
//					{
//						return "";
//					}
					logger.warn("username: "+username+", image file: "+val.trim()+" not exists.");
					throw new Exception(csf.getError_msg());
				}
			}
			else if(csf.getType().equals("文件"))
			{
				if(!ifFileExists(username,val.trim()))
				{
					//如果字段可以为空,即使数据是非法的也不会报错,只是数据当做空
//					if(!csf.isNot_null())
//					{
//						return "";
//					}
					logger.warn("username: "+username+", file: "+val.trim()+" not exists.");
					throw new Exception(csf.getError_msg());
				}
			}
			//是枚举值
			else if(csf.isEnumerated())
			{
				String eVals=csf.getEmvalue();
				if(eVals!=null && !eVals.trim().equals(""))
				{
					String eValArr[]=eVals.trim().split(",");
					boolean ifHas=false;
					for(String eVal : eValArr)
					{
						if(val.trim().equals(eVal.trim()))
						{
							ifHas=true;
							break;
						}
					}
					if(!ifHas)
					{
						//如果字段可以为空,即使数据是非法的也不会报错,只是数据当做空
//						if(!csf.isNot_null())
//						{
//							return "";
//						}
						throw new Exception(csf.getError_msg());
					}
				}

			}

			return val.trim();
		}
	}

	private String validateData(String val,FormatField ff,String username) throws Exception
	{
		if(val==null || val.trim().isEmpty())
		{
			//如果字段为非空,但是值为空
			if(ff.isNot_null())
			{
				throw new Exception(ff.getError_msg());
			}

			return "";
		}
		else
		{
			if(ff.getType().equals("图片"))
			{
				if(!ifImgExists(username,val.trim()))
				{
					//如果字段可以为空,即使数据是非法的也不会报错,只是数据当做空
//					if(!ff.isNot_null())
//					{
//						return "";
//					}
					logger.warn("username: "+username+", image file: "+val.trim()+" not exists.");
					throw new Exception(ff.getError_msg());
				}
			}
			else if(ff.getType().equals("文件"))
			{
				if(!ifFileExists(username,val.trim()))
				{
					//如果字段可以为空,即使数据是非法的也不会报错,只是数据当做空
//					if(!ff.isNot_null())
//					{
//						return "";
//					}
					logger.warn("username: "+username+", file: "+val.trim()+" not exists.");
					throw new Exception(ff.getError_msg());
				}
			}
			//是枚举值
			else if(ff.isEnumerated())
			{
				String eVals=ff.getEmvalue();
				if(eVals!=null && !eVals.trim().equals(""))
				{
					String eValArr[]=eVals.trim().split(",");
					boolean ifHas=false;
					for(String eVal : eValArr)
					{
						if(val.trim().equals(eVal.trim()))
						{
							ifHas=true;
							break;
						}
					}
					if(!ifHas)
					{
						//如果字段可以为空,即使数据是非法的也不会报错,只是数据当做空
//						if(!ff.isNot_null())
//						{
//							return "";
//						}
						throw new Exception(ff.getError_msg());
					}
				}

			}

			return val.trim();
		}

	}
	
	/**
	    上传文件接口
	    建议前端data为FormData()类型
	 文件将上传到临时目录,以用户名为独立目录
	 * @param request
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/uploadFiles")
	@ResponseBody
	public Map<String, Object> uploadFiles(
			 HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) request.getAttribute("user");
		String  sucFiles = "";
		String  failFiles = "";
		int count = 0;
		int fileCount = 0;
		//除文件外所有参数
		 Map param = request.getParameterMap();
		 Iterator entries = param.entrySet().iterator(); 
		 while (entries.hasNext()) { 
		   Map.Entry entry = (Map.Entry) entries.next(); 
		   String key = (String)entry.getKey(); 
		   String[] value = (String[])entry.getValue(); 
		   System.out.println("Key = " + key + ", Value = " + value[0]); 
		 }
		 //文件参数
	   MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
       Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
       fileCount = fileMap.size();
		try {
			if (fileMap != null || fileMap.size() > 0) {
				Collection<MultipartFile> files = fileMap.values();
				for (MultipartFile file : files) {
					String key = file.getName();
					String req = file.getOriginalFilename();
					if (StringUtils.isBlank(req)) {
						continue;
					}
					//文件上传地址
					//String contexPath= request.getSession().getServletContext().getRealPath("\\")+user.getUsername()+"\\";
					String path = this.dataFileTmpLocation;
					File temp = new File(path);
					if (!temp.exists() && !temp.isDirectory()) {
						temp.mkdir();
					}

					String path1 = this.dataFileTmpLocation + File.separator + user.getUsername();
					File temp1 = new File(path1);
					if (!temp1.exists() && !temp1.isDirectory()) {
						temp1.mkdir();
					}

					String fileName = file.getOriginalFilename();
					File dest = new File(path1 + File.separator + fileName);
					if (!dest.getParentFile().exists()) {//判断文件父目录是否存在
						dest.getParentFile().mkdir();
					}
					try {
						file.transferTo(dest); //保存文件
						sucFiles = sucFiles + fileName + ",";
						count = count+1;
						System.out.println(dest.getAbsolutePath());
					} catch (IllegalStateException e) {
						failFiles = failFiles + fileName + ",";
						e.printStackTrace();
						map.put("result", false);
						map.put("message", "文件保存失败！" + "文件名称：" + fileName);
						return map;
					} catch (IOException e) {
						failFiles = failFiles + fileName + ",";
						e.printStackTrace();
						map.put("result", false);
						map.put("message", "文件保存失败！" + "文件名称：" + fileName);
						return map;
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}
       
       if(count == fileCount) {
    	   map.put("result", true);
  	       map.put("message", "文件保存成功！共"+fileCount+"个。"+"文件名称："+sucFiles.substring(0, sucFiles.length()-1));
       }else {
    	   map.put("result", false);
  	       map.put("message", "文件保存失败！"+"文件名称："+failFiles.substring(0, failFiles.length()-1));
       }
	   return map;
               
	}

	/*
	key是最终的文件名,value是相对路径
	 */
	private Entry<String,String> importFile(String username,Integer uid,String fileName) throws Exception
	{
		File sourceFile=new File(dataFileTmpLocation+File.separatorChar+username+File.separatorChar+fileName);
		if(!sourceFile.exists())
		{
			throw new Exception("文件不存在: "+sourceFile.getAbsolutePath());
		}
		String md5=MyFileUtil.FileMD5(sourceFile.getAbsolutePath());
		if(md5==null)
		{
			throw new Exception("计算文件MD5值出错: "+sourceFile.getAbsolutePath());
		}
		String relaPath=md5;

		FormatFile formatFileExists=formatFileService.SelectFormatFileByMD5Code(md5);
		//如果没有这个文件的MD5,则新建并导入数据
		if(formatFileExists==null)
		{
			FormatFile formatFile=new FormatFile();
			//formatFile.setCs_id(Integer.valueOf(cs_id));
			formatFile.setFilename(fileName);
			formatFile.setMd5code(md5);
			formatFile.setUid(uid);
			formatFile.setPath(relaPath);
			//formatFile.setCsf_id(Integer.valueOf(key));
			try
			{
				formatFileService.insertFormatFile(formatFile);
			}
			catch(Exception e)
			{
				//如果异常不是因为文件已存在，则报错退出
				if(!e.getMessage().toLowerCase().contains("ak_md5code"))
				{
					logger.error(e);
					throw new Exception("操作数据库出错: "+e.getMessage());
				}
			}

			String destPath=dataFileFinalLocation+File.separator+relaPath;

			MyFileUtil.CopyTo(sourceFile,new File(destPath));

		}
		//如果有,则不新建，直接导入数据

		Map<String,String> resultMap=new HashMap();
		resultMap.put(md5+"_"+fileName,relaPath);
		return (Entry<String,String>)resultMap.entrySet().toArray()[0];
	}

	@RequestMapping(value = "/sourceData")
	@ResponseBody
	public Map<String, Object> sourceData(@RequestParam(value = "file", required = false) MultipartFile file,
			String cs_id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (cs_id == null) {
			map.put("result", false);
			map.put("message", "请求异常,无法获取采集数据源ID！");
			return map;
		}
		User user = (User) request.getAttribute("user");
		try {
			if (file == null || file.getInputStream() == null) {
				map.put("result", false);
				map.put("message", "文件上传失败");
				return map;
			}
			if (file.getSize() > 1024 * 1024*10) {
				map.put("result", false);
				map.put("message", "文件不能超过8M");
				return map;
			}

			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(file.getInputStream());
			HSSFSheet sheetAt = hssfWorkbook.getSheetAt(1);
			HSSFCell cell = null;
			HSSFRow row = null;

			//key为列名,value为index
			HashMap<String, Integer> index_nameMap = new HashMap<>();
			row = sheetAt.getRow(sheetAt.getFirstRowNum());
			for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				String colName=getStringCellValue(cell);
				if(colName==null || colName.trim().equals(""))
				{
					map.put("result", false);
					map.put("message", "请在第一行指定列名.");
					return map;
				}
				colName=colName.trim();
				try {
					index_nameMap.put(colName, j);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}

			List<SourceField> sourceFields = sourceFieldService.getSourceFields(Integer.valueOf(cs_id));
			// 待导入文件中要导入的列的索引,对应的配置表中的字段id
			HashMap<Integer, SourceField> index_csfMap = new HashMap<>();
			for (SourceField sourceField : sourceFields) {
				if (index_nameMap.containsKey(sourceField.getCsf_name())) {
					index_csfMap.put(index_nameMap.get(sourceField.getCsf_name()),
							sourceField);
				}
			}

			// row = sheetAt.getRow(sheetAt.getFirstRowNum());
			// for (int j = row.getFirstCellNum(); j < row.getLastCellNum();
			// j++) {
			// cell = row.getCell(j);
			// String csf_Id =
			// String.valueOf(sourceFieldService.getSourceFieldId(Integer.valueOf(cs_id),
			// getStringCellValue(cell)));
			// if (!csf_Id.equals("null")) {
			// index_csfIdMap.put(j, csf_Id);
			// }
			// }

			for (int i = sheetAt.getFirstRowNum() + 1; i <= sheetAt.getLastRowNum(); i++) {
				row = sheetAt.getRow(i);
				if (row == null) {
					continue;
				}
				Map<String, String> sourceFieldDatas = new HashMap<>();

				for (Entry<Integer, SourceField> index_csf : index_csfMap.entrySet()) {
					try {
						cell = row.getCell(index_csf.getKey());
						String cellValue=getStringCellValue(cell);

						cellValue=validateData(cellValue,index_csf.getValue(),user.getUsername());

						//如果字段是文件或图片,则先导入文件,再把文件名(MD5+文件名)写入hbase
						if(cellValue!=null && !cellValue.trim().isEmpty() && (index_csf.getValue().getType().equals("图片")||index_csf.getValue().getType().equals("文件"))){
							//把文件导入到正式目录
							Entry<String,String> result=importFile(user.getUsername(),user.getId(),cellValue.trim());
							sourceFieldDatas.put(String.valueOf(index_csf.getValue().getCsf_id()), result.getKey());
						}
						else
						{
							sourceFieldDatas.put(String.valueOf(index_csf.getValue().getCsf_id()), cellValue!=null && !cellValue.trim().isEmpty()?cellValue:"");
						}

					} catch (Exception e) {
						map.put("result", false);
						map.put("message", "导入失败, 列 "+index_csf.getValue().getCsf_name()+", 第 "+i+" 行, 错误信息为: "+e.getMessage());
						return map;
					}
				}
				// for (int j = row.getFirstCellNum(); j < row.getLastCellNum();
				// j++) {
				// cell = row.getCell(j);
				// sourceFieldDatas.put(String.valueOf(sourceFields.get(j).getCsf_id()),
				// getStringCellValue(cell));
				// }
				if (!sourceFieldDatas.isEmpty()) {
					HBaseSourceDataDao.insertSourceData(cs_id, String.valueOf(user.getId()), sourceFieldDatas,user.getUsername());
				}
			}
			hssfWorkbook.close();
		} catch (IOException e1) {
			map.put("result", false);
			map.put("message", "文件上传失败,错误信息为: "+ e1.getMessage());
			return map;
		}

		return map;
	}

	@RequestMapping(value = "/formatData")
	@ResponseBody
	public Map<String, Object> formatData(@RequestParam(value = "file", required = false) MultipartFile file,
			String cs_id, String ft_id, String sourceDataId, String formatNodeId,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = (User) request.getAttribute("user");

		try {
			if (file == null || file.getInputStream() == null) {
				map.put("result", false);
				map.put("message", "文件上传失败");
				return map;
			}
			if (file.getSize() > 1024 * 1024*10) {
				map.put("result", false);
				map.put("message", "文件不能超过8M");
				return map;
			}

			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(file.getInputStream());
			HSSFSheet sheetAt = hssfWorkbook.getSheetAt(1);
			HSSFCell cell = null;
			HSSFRow row = null;

			HashMap<String, Integer> index_nameMap = new HashMap<>();
			row = sheetAt.getRow(sheetAt.getFirstRowNum());
			for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				try {
					index_nameMap.put(getStringCellValue(cell), j);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
			List<FormatField> datas = formatFieldService.getFormatFieldsIs_meta(Integer.valueOf(ft_id),
					ConstantsHBase.IS_meta_false);
			// 待导入文件中要导入的列的索引,对应的配置表中的字段id
			HashMap<Integer, FormatField> index_ffMap = new HashMap<>();
			for (FormatField formatField : datas) {
				if (index_nameMap.containsKey(formatField.getFf_name())) {
					index_ffMap.put(index_nameMap.get(formatField.getFf_name()), formatField);
				}
			}
			for (int i = sheetAt.getFirstRowNum() + 1; i <= sheetAt.getLastRowNum(); i++) {
				row = sheetAt.getRow(i);
				if (row == null) {
					continue;
				}
				Map<String, String> formatFieldDatas = new HashMap<>();
				for (Entry<Integer, FormatField> index_csf : index_ffMap.entrySet()) {
					try {
						cell = row.getCell(index_csf.getKey());
						String cellValue=getStringCellValue(cell);

						validateData(cellValue,index_csf.getValue(),user.getUsername());

						//如果字段是文件或图片,则先导入文件,再把文件名(MD5+文件名)写入hbase
						if(cellValue!=null && !cellValue.trim().isEmpty() && (index_csf.getValue().getType().equals("图片")||index_csf.getValue().getType().equals("文件"))){
							//把文件导入到正式目录
							Entry<String,String> result=importFile(user.getUsername(),user.getId(),cellValue.trim());
							formatFieldDatas.put(String.valueOf(index_csf.getValue().getFf_id()), result.getKey());
						}
						else
						{
							formatFieldDatas.put(String.valueOf(index_csf.getValue().getFf_id()), cellValue!=null && !cellValue.trim().isEmpty()?cellValue:"");
						}


						if(!cellValue.trim().isEmpty()){
							formatFieldDatas.put(String.valueOf(index_csf.getValue().getFf_id()), cellValue);
						}
					} catch (Exception e) {
						map.put("result", false);
						map.put("message", "导入失败, 列 "+index_csf.getValue().getFf_name()+", 第 "+i+" 行, 错误信息为: "+e.getMessage());
						return map;
					}
				}
				if (!formatFieldDatas.isEmpty()) {
					HBaseFormatDataDao.insertFormatData(cs_id, ft_id, sourceDataId, formatNodeId, formatFieldDatas);
				}
			}
			hssfWorkbook.close();
		} catch (IOException e1) {
			map.put("result", false);
			map.put("message", "文件上传失败");
			return map;
		}

		return map;
	}

	@SuppressWarnings("deprecation")
	private String getStringCellValue(HSSFCell cell) {
		String cellValue = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DecimalFormat decimalFormat = new DecimalFormat("#.#######################################");
		if (cell == null) {
			return cellValue;
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 数字
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				double d = cell.getNumericCellValue();
				Date date = HSSFDateUtil.getJavaDate(d);
				cellValue = simpleDateFormat.format(date);
			} else {
				cellValue = decimalFormat.format((cell.getNumericCellValue()));
//				double t = cell.getNumericCellValue();
//				cellValue = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case HSSFCell.CELL_TYPE_STRING: // 字符串
			cellValue = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA: // 公式
			cellValue = cell.getCellFormula().toString();
			break;
		case HSSFCell.CELL_TYPE_BLANK: // 空值
			cellValue = "";
			break;
		case HSSFCell.CELL_TYPE_ERROR: // 故障
			cellValue = "";
			break;
		default:
			cellValue = cell.toString();
			break;
		}

		return cellValue;
	}

	public static void main(String[] args) {
		File f = new File("E:\\Users\\admin\\Desktop\\sourceDataModel.xls");
		ImportController importController = new ImportController();
		try {
			FileInputStream is = new FileInputStream(f);
			HSSFWorkbook wbs = new HSSFWorkbook(is);
			HSSFSheet childSheet = wbs.getSheetAt(0);
			 System.out.println(childSheet.getLastRowNum());
			System.out.println("有行数" + childSheet.getPhysicalNumberOfRows());
			int n=childSheet.getLastRowNum();
			for (int j = 0; j <= n; j++) {
				HSSFRow row = childSheet.getRow(j);
				// System.out.println(row.getPhysicalNumberOfCells());
				// System.out.println("有列数" + row.getLastCellNum());
				System.out.print(j+"--\t");
				if (null != row) {
					for (int k = 0; k < row.getLastCellNum(); k++) {
						HSSFCell cell = row.getCell(k);
						if (cell != null) {
							System.out.print(cell.toString() + ":" + importController.getStringCellValue(cell) + ",\t");
						} else {
							System.out.print("---:---,\t");
						}
					}
				}
				System.out.println();
			}
			wbs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
