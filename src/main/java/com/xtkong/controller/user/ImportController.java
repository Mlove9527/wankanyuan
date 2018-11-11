package com.xtkong.controller.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

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
import com.xtkong.service.FormatFieldService;
import com.xtkong.service.FormatTypeService;
import com.xtkong.service.SourceFieldService;
import com.xtkong.service.SourceService;
import com.xtkong.util.ConstantsHBase;

@Controller
@RequestMapping("/import")
public class ImportController {
	private static final Logger logger  =  Logger.getLogger(ImportController.class );
	@Autowired
	SourceService sourceService;
	@Autowired
	SourceFieldService sourceFieldService;
	@Autowired
	FormatTypeService formatTypeService;
	@Autowired
	FormatFieldService formatFieldService;

	@Value("${formatData.file.location}")
	private String dataFileLocation;

	private boolean ifFileExists(String uid,String fileName)
	{
		return new File(dataFileLocation+File.separatorChar+uid+File.separatorChar+fileName).exists();
	}

	private boolean ifImgExists(String uid,String fileName)
	{
		return new File(dataFileLocation+File.separatorChar+uid+File.separatorChar+fileName).exists();
	}

	private void validateData(String data,SourceField csf,String uid) throws Exception
	{
		if(csf.isNot_null() && (data==null || data.trim().isEmpty()))
		{
			throw new Exception("不能为空.");
		}
		if(csf.getType().equals("图片"))
		{
			if(!ifImgExists(uid,data))
			{
				logger.warn("uid: "+uid+", image file: "+data+" not exists.");
				throw new Exception("图片文件不存在: "+data);
			}
		}
		else if(csf.getType().equals("文件"))
		{
			if(!ifFileExists(uid,data))
			{
				logger.warn("uid: "+uid+", file: "+data+" not exists.");
				throw new Exception("文件不存在: "+data);
			}
		}
	}
	
	/**
	    上传文件接口
	    建议前端data为FormData()类型
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
       if(fileMap != null || fileMap.size() > 0){
    	   Collection<MultipartFile> files = fileMap.values();
           for(MultipartFile file:files){
        	   String key = file.getName();
               String req = file.getOriginalFilename();
               if(StringUtils.isBlank(req)){
                   continue;
               }
            //文件上传地址
            //String contexPath= request.getSession().getServletContext().getRealPath("\\")+user.getUsername()+"\\";
          	String path =this.dataFileLocation;
           	File temp = new File(path);
           	if(!temp.exists() && !temp.isDirectory()){
           		temp.mkdir();
            }
          		
       		String path1 =this.dataFileLocation+"\\"+user.getId()+"\\";
       		File temp1 = new File(path1);
       		if(!temp1.exists() && !temp1.isDirectory()){
       			temp1.mkdir();
       		}
       		
            String fileName = file.getOriginalFilename();
       	    File dest = new File(path1 + "\\" + fileName);
               if(!dest.getParentFile().exists()){//判断文件父目录是否存在
                   dest.getParentFile().mkdir();
               }
               try {
       			file.transferTo(dest); //保存文件
       		    sucFiles = sucFiles + fileName + ",";
       		    count = count++;
       			System.out.println(dest.getAbsolutePath());
		   		} catch (IllegalStateException e) {
		   			failFiles = failFiles + fileName + ",";
		   			e.printStackTrace();
		   			map.put("result", false);
		   	        map.put("message", "文件保存失败！"+"文件名称："+fileName);
		   	        return map;
		   		} catch (IOException e) {
		   			failFiles = failFiles + fileName + ",";
		   			e.printStackTrace();
		   			map.put("result", false);
		   	        map.put("message", "文件保存失败！"+"文件名称："+fileName);
		   	        return map;
		   		}
           }
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

						validateData(cellValue,index_csf.getValue(),String.valueOf(user.getId()));

						if(!cellValue.trim().isEmpty()){
							sourceFieldDatas.put(String.valueOf(index_csf.getValue().getCsf_id()), cellValue);
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
			String cs_id, String ft_id, String sourceDataId, String formatNodeId) {
		Map<String, Object> map = new HashMap<String, Object>();
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
			HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
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
			HashMap<Integer, String> index_ffIdMap = new HashMap<>();
			for (FormatField formatField : datas) {
				if (index_nameMap.containsKey(formatField.getFf_name())) {
					index_ffIdMap.put(index_nameMap.get(formatField.getFf_name()),
							String.valueOf(formatField.getFf_id()));
				}
			}
			for (int i = sheetAt.getFirstRowNum() + 1; i <= sheetAt.getLastRowNum(); i++) {
				row = sheetAt.getRow(i);
				if (row == null) {
					continue;
				}
				Map<String, String> formatFieldDatas = new HashMap<>();
				for (Entry<Integer, String> index_csfId : index_ffIdMap.entrySet()) {
					try {
						cell = row.getCell(index_csfId.getKey());						
						String cellValue=getStringCellValue(cell);
						if(!cellValue.trim().isEmpty()){
							formatFieldDatas.put(index_csfId.getValue(), cellValue);
						}
					} catch (Exception e) {
						e.printStackTrace();
						continue;
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
