package com.xtkong.controller.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	@Autowired
	SourceService sourceService;
	@Autowired
	SourceFieldService sourceFieldService;
	@Autowired
	FormatTypeService formatTypeService;
	@Autowired
	FormatFieldService formatFieldService;

	@RequestMapping(value = "/sourceData")
	@ResponseBody
	public Map<String, Object> sourceData(@RequestParam(value = "file", required = false) MultipartFile file,
			String cs_id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (cs_id == null) {
			map.put("result", false);
			map.put("message", "请求异常！");
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

			List<SourceField> sourceFields = sourceFieldService.getSourceFields(Integer.valueOf(cs_id));
			// 待导入文件中要导入的列的索引,对应的配置表中的字段id
			HashMap<Integer, String> index_csfIdMap = new HashMap<>();
			for (SourceField sourceField : sourceFields) {
				if (index_nameMap.containsKey(sourceField.getCsf_name())) {
					index_csfIdMap.put(index_nameMap.get(sourceField.getCsf_name()),
							String.valueOf(sourceField.getCsf_id()));
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
				for (Entry<Integer, String> index_csfId : index_csfIdMap.entrySet()) {
					try {
						cell = row.getCell(index_csfId.getKey());						
						String cellValue=getStringCellValue(cell);
						if(!cellValue.trim().isEmpty()){
							sourceFieldDatas.put(index_csfId.getValue(), cellValue);
						}
					} catch (Exception e) {
						e.printStackTrace();
						continue;
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
			map.put("message", "文件上传失败");
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
