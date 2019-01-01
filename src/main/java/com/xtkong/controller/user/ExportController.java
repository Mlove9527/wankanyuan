package com.xtkong.controller.user;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xtkong.model.SourceField;
import com.xtkong.service.*;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liutianjun.pojo.User;
import com.xtkong.controller.user.SourceDataController.SourceDataSQLInfo;
import com.xtkong.dao.hbase.HBaseFormatDataDao;
import com.xtkong.dao.hbase.HBaseFormatNodeDao;
import com.xtkong.dao.hbase.HBaseSourceDataDao;
import com.xtkong.model.FormatDataSQLInfo;
import com.xtkong.model.FormatField;
import com.xtkong.model.FormatField1;
import com.xtkong.model.FormatType;
import com.xtkong.model.Source;
import com.xtkong.util.ConstantsHBase;

@Controller
@RequestMapping("/export")
public class ExportController {
	private static final Logger logger  =  Logger.getLogger(ExportController.class );
	@Autowired
	SourceService sourceService;
	@Autowired
	SourceFieldService sourceFieldService;
	@Autowired
	FormatTypeService formatTypeService;
	@Autowired
	FormatFieldService formatFieldService;
	@Autowired
	SourceDataController sourceDataController;
	@Autowired
	UserDataService userDataService;
	@Autowired
	FormatNodeController formatNodeController;

	@Value("${formatData.file.location}")
	private String dataFileLocation;
	@Value("${formatData.file.thumbnailImage}")
	private String thumbnailImageLocation;
	 
    //缩略图相关
	private static String DEFAULT_PREVFIX = "thumb_";
    private static Boolean DEFAULT_FORCE = false;//建议该值为false
    
	/*
	返回结果: key是文件名, value是文件路径
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Entry<String,String> getFilePath(Integer cs_id,String sourceDataId,Integer csf_id,Integer myUid) throws Exception
	{
		List<String> fields=new ArrayList<>();
		//第一个字段数据的创建者ID,找文件的时候要用
		fields.add(ConstantsHBase.QUALIFIER_CREATE);
		//文件名
		fields.add(String.valueOf(csf_id));
		//是否公开
		fields.add(ConstantsHBase.QUALIFIER_PUBLIC);
		List<String> sourceData = HBaseSourceDataDao.getSourceDataByIdByFieldNames(cs_id.toString(), sourceDataId, fields);
		//任何一个为空都不行
		for(String data : sourceData)
		{
			if(data==null || data.trim().equals(""))
			{
				throw new Exception("数据非法,无法获取文件名,创建者ID或公开状态.");
			}
		}
		Integer uid=null;
		String fileName=null;
		String pub=null;
		try
		{
			logger.info("cs_id: "+cs_id+", sourceDataId: "+sourceDataId+", csf_id: "+csf_id+"creator uid: "+sourceData.get(1)+", filename: "+sourceData.get(2)+", pub: "+sourceData.get(3)+", myUid: "+myUid);
			uid=Integer.valueOf(sourceData.get(1));
			fileName=sourceData.get(2);
			pub=sourceData.get(3);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("数据非法,无法获取文件名,创建者ID或公开状态.");
		}

		if(!uid.equals(myUid) && !(pub.equals(ConstantsHBase.VALUE_PUBLIC_TRUE) && userDataService.selects(myUid,cs_id).contains(sourceDataId)))
		{
			throw new Exception("无权限操作.");
		}

		Map<String,String> result=new HashMap();

		//_号前面是MD5值, _号后边是真正的文件名
		//fileNameINDB当初用户可能填的是文件相对路径,这个名字会用于下载时的文件名,所以最好处理一下
		String fileNameINDB=fileName.substring(fileName.indexOf("_")+1,fileName.length());
		String fileRealPath=this.dataFileLocation+File.separatorChar+fileName.substring(0,fileName.indexOf("_"));
		result.put(fileNameINDB,fileRealPath);

		return (Entry<String,String>)result.entrySet().toArray()[0];
	}

	/**
	 * 下载文件
	 *
	 * @param response
	 * @param request
	 * @param sourceDataId
	 * @param cs_id
	 * @param csf_id
	 */
	@SuppressWarnings("resource")
	@RequestMapping("/downloadFile")
	public void downloadFile(HttpServletResponse response,HttpServletRequest request,String sourceDataId,Integer cs_id,Integer csf_id)
	{
		User user = (User) request.getAttribute("user");
		String filePath=null;
		try
		{
			Integer myUid=user.getId();
			Entry<String,String> result=getFilePath(cs_id,sourceDataId,csf_id,myUid);

			filePath=result.getValue();

			File fileToBeDownload=new File(filePath);
			if(!fileToBeDownload.exists())
			{
				logger.error("下载文件错误: "+filePath+" 文件不存在,应该是文件管理目录和数据库的信息不一致.");
				return;
			}
			long fileLength = fileToBeDownload.length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(result.getKey().getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileToBeDownload));
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[20480];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
			bos.close();
		}
		catch(Exception e)
		{
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	
	/**
     * <p>Title: thumbnailImage</p>
     * <p>Description: 依据图片路径生成缩略图 </p>
     * @param imagePath    原图片路径
     * @param w            缩略图宽
     * @param h            缩略图高
     * @param prevfix    生成缩略图的前缀
     * @param thumbImagePath    缩略图存放地址
     * @param force        是否强制依照宽高生成缩略图(假设为false，则生成最佳比例缩略图)
     */
    public void thumbnailImage(String imagePath, int w, int h, String prevfix, boolean force, String thumbImagePath){
        File imgFile = new File(imagePath);
        if(imgFile.exists()){
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames());
                String suffix = null;
                // 获取图片后缀
                if(imgFile.getName().indexOf(".") > -1) {
                    suffix = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀所有小写，然后推断后缀是否合法
                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()) < 0){
                	logger.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return ;
                }
                logger.debug("target image's size, width:{}, height:{}."+w+":"+h);
                Image img = ImageIO.read(imgFile);
                if(!force){
                    // 依据原图与要求的缩略图比例，找到最合适的缩略图比例
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if((width*1.0)/w < (height*1.0)/h){
                        if(width > w){
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));
                            logger.debug("change image's height, width:{}, height:{}."+w+":"+h);
                        }
                    } else {
                        if(height > h){
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
                            logger.debug("change image's width, width:{}, height:{}."+w+":"+h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                //String p = imgFile.getPath();// 将图片保存在原文件夹并加上前缀
                ImageIO.write(bi, suffix, new File(thumbImagePath.substring(0,thumbImagePath.lastIndexOf(File.separator)) + File.separator + prevfix +imgFile.getName()));
                logger.debug("缩略图在路径下生成成功");
            } catch (IOException e) {
            	logger.error("generate thumbnail image failed.",e);
            }
        }else{
        	logger.warn("the image is not exist.");
        }
    }

    /**
	 * 获取缩略图
	 *
	 * @param response
	 * @param request
	 * @param sourceDataId
	 * @param cs_id
	 * @param csf_id
	 * @return thumbnailImageUrl
	 */
	@SuppressWarnings("resource")
	@RequestMapping("/getThumbnailImage")
	public void getThumbnailImage(HttpServletResponse response,HttpServletRequest request,
			String sourceDataId,Integer cs_id,Integer csf_id)throws ServletException, IOException 
	{
		User user = (User) request.getAttribute("user");
		String filePath=null;
		try
		{
			Integer myUid=user.getId();
			Entry<String,String> result=getFilePath(cs_id,sourceDataId,csf_id,myUid);

			filePath=result.getValue();

			File fileToBeDownload=new File(filePath);
			if(!fileToBeDownload.exists())
			{
				logger.error("下载文件错误: "+filePath+" 文件不存在,应该是文件管理目录和数据库的信息不一致.");
				return;
			}
			//读取图片输入流
			FileInputStream inputStream = new FileInputStream(fileToBeDownload);
			int i = inputStream.available();
			//byte数组用于存放图片字节数据
			byte[] buff = new byte[i];
			inputStream.read(buff);
			//记得关闭输入流
			inputStream.close();
			//设置发送到客户端的响应内容类型
			response.setContentType("image/*");
			OutputStream out = response.getOutputStream();
			out.write(buff);
			//关闭响应输出流
			out.close();
		}
		catch(Exception e)
		{
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//读取本地图片输入流
		FileInputStream inputStream = new FileInputStream("D:/image/123.jpg");
		int i = inputStream.available();
		//byte数组用于存放图片字节数据
		byte[] buff = new byte[i];
		inputStream.read(buff);
		//记得关闭输入流
		inputStream.close();
		//设置发送到客户端的响应内容类型
		response.setContentType("image/*");
		OutputStream out = response.getOutputStream();
		out.write(buff);
		//关闭响应输出流
		out.close();
	}
 
	
	/**
	 * 导出源数据上传格式
	 * 
	 * @param response
	 * @param cs_id
	 */
	@RequestMapping("/sourceDataModel")
	public void sourceDataModel(HttpServletResponse response, Integer cs_id) {
		response.setContentType("application/vnd.ms-excel");
		/**
		 * 以下为生成Excel操作
		 */
		// 1.创建一个workbook，对应一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 2.单元格居中
		HSSFCellStyle style = workbook.createCellStyle();
		// 居中格式
		style.setAlignment(HorizontalAlignment.CENTER);

		// 3.在workbook中添加一个sheet，对应Excel中的一个sheet

		HSSFSheet sheet1 = workbook.createSheet("字段描述");
		sheet1=sheetSourceDataForm1(sheet1,style,cs_id);
		HSSFSheet sheet = workbook.createSheet("源数据模版");
		sheet = sheetSourceDataForm(sheet, style, cs_id);
		try {
			OutputStream output = response.getOutputStream();
			workbook.write(output);
			output.flush();
			output.close();
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 导出源数据
	 * 
	 * @param response
	 * @param cs_id
	 * @param ids
	 */
	@RequestMapping("/sourceData")
	public void sourceData(HttpServletResponse response,HttpServletRequest request,String type,String cs_id, String ids, boolean isAll,
    		String searchId, String searchWord,String desc_asc,String oldCondition,
            String searchFirstWord,String chooseDatas,String likeSearch) {
		response.setContentType("application/vnd.ms-excel");
		//logger.info("ExportController-sourceData: "+ids);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);

		HSSFSheet sheet = workbook.createSheet("源数据");
		if (isAll == true) {
			User user = (User) request.getAttribute("user");
			Integer uid = user.getId();
			String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;

			Integer csId = cs_id.equals("")?null:Integer.valueOf(cs_id);
			Integer searchIdInt = searchId.equals("")?null:Integer.valueOf(searchId);
			SourceDataSQLInfo sourceDataSQLInfo=sourceDataController.getSourceDataSQL(csId,user,"2",searchFirstWord,oldCondition,null,null,searchIdInt,chooseDatas,likeSearch,searchWord,true,ids);

			Map<String, Map<String, Object>> result = PhoenixClient.select(sourceDataSQLInfo.getSql());

			List<List<String>> sourceDatas = null;
			String resultMsg;
			for (int j = 0; j < 6; j++) {
				resultMsg = String.valueOf((result.get("msg")).get("msg"));
				if (resultMsg.equals("success")) {
					sourceDatas = (List<List<String>>) result.get("records").get("data");
					break;
				} else {
					PhoenixClient.undefined(resultMsg, tableName, sourceDataSQLInfo.getQualifiers(), sourceDataSQLInfo.getConditionEqual(), sourceDataSQLInfo.getConditionLike());
					result = PhoenixClient.select(sourceDataSQLInfo.getSql());
				}
			}
            
			String idsStr = "";
			if(sourceDatas!=null)
			{
				for (List<String> record : sourceDatas)
				{
					String idTemp = record.get(0);
					idsStr=idsStr+idTemp+",";
				}
				idsStr = idsStr.substring(0, idsStr.length()-1);
			}
			sheet = sheetSourceDataByIds(sheet, style, cs_id, idsStr);
		}else {
			if (ids.startsWith(",")) {
				ids = ids.substring(1,ids.length() ).replaceAll("check", "");
			}
			sheet = sheetSourceDataByIds(sheet, style, cs_id, ids);
		}
		
		// 设置表头
		try {
			OutputStream output = response.getOutputStream();
			workbook.write(output);
			output.flush();
			output.close();
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 导出某格式类型数据
	 * 
	 * @param response
	 * @param cs_id
	 * @param sourceDataId
	 * @param ft_id
	 */
	@RequestMapping("/formatType")
	public void formatType(HttpServletResponse response, String cs_id, String sourceDataId, String ft_id) {
		response.setContentType("application/vnd.ms-excel");
		/**
		 * 以下为生成Excel操作
		 */
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		HSSFSheet sheet;
		FormatType formatType = HBaseFormatNodeDao.getFormatNodesByType(cs_id, sourceDataId, ft_id);
		String formatNodeId;
		String formatNodeName;
		for (Entry<String, String> formatNode : formatType.getFormatDataNodes().entrySet()) {
			formatNodeId = formatNode.getKey();
			formatNodeName = formatNode.getValue();
			// meta数据
			sheet = workbook.createSheet("公共数据-" + formatNodeName);
			sheet = sheetFormatDatas(sheet, style, cs_id, ft_id, formatNodeId, ConstantsHBase.IS_meta_true);

			// data数据
			sheet = workbook.createSheet("专属数据-" + formatNodeName);
			sheet = sheetFormatDatas(sheet, style, cs_id, ft_id, formatNodeId, ConstantsHBase.IS_meta_false);
		}

		try {
			OutputStream output = response.getOutputStream();
			workbook.write(output);
			output.flush();
			output.close();
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 导出结点数据
	 * 
	 * @param response
	 * @param cs_id
	 * @param ft_ids
	 * @param formatNodeIds
	 */
	@RequestMapping("/formatNode")
	public void formatNode(HttpServletResponse response, String cs_id, String ft_ids, String formatNodeIds) {
		response.setContentType("application/vnd.ms-excel");

		Map<String, Object> map = new HashMap<String, Object>();

		logger.info("formatNodeIds-----------------------"+formatNodeIds);
		if(formatNodeIds==null || formatNodeIds.trim().equals("") ||ft_ids==null ||ft_ids.trim().equals(""))
		{
			logger.error("没有选择待导出的数据结点.");
			map.put("result", false);
			map.put("message", "请选择待导出数据的结点！");
			return;
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		HSSFSheet sheet;
		String[] formatNodeIdsStr = formatNodeIds.split(",");
		String[] ft_idsStr = ft_ids.split(",");

		for (int i = 0; i < ft_idsStr.length; i++) {
			// meta数据
			String ft_id = ft_idsStr[i];
			String formatNodeId = formatNodeIdsStr[i];
			FormatType formatType = formatTypeService.getFormatType(Integer.valueOf(ft_id));
			List<String> formatNode = HBaseFormatNodeDao.getFormatNodeById(cs_id, formatNodeId);
			String nodeName = "";
			try {
				nodeName = formatNode.get(2);
			} catch (Exception e) {
				nodeName = "" + i;
			}
			sheet = workbook.createSheet(formatType.getFt_name() + "_" + nodeName + "_" + "公共数据");
			sheet = sheetFormatDatas(sheet, style, cs_id, ft_id, formatNodeId, ConstantsHBase.IS_meta_true);

			// data数据
			sheet = workbook.createSheet(formatType.getFt_name() + "_" + nodeName + "_" + "专属数据");
			sheet = sheetFormatDatas(sheet, style, cs_id, ft_id, formatNodeId, ConstantsHBase.IS_meta_false);
		}
		try {
			OutputStream output = response.getOutputStream();
			workbook.write(output);
			output.flush();
			output.close();
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 导出格式数据上传格式
	 * 
	 * @param response
	 * @param ft_id
	 */
	@RequestMapping("/formatDataModel")
	public void formatDataModel(HttpServletResponse response, String ft_id) {
		response.setContentType("application/vnd.ms-excel");
		/**
		 * 以下为生成Excel操作
		 */
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFSheet sheet1 = workbook.createSheet("字段描述");
		sheet1=sheetFormatFieldForm1(sheet1, style, ft_id, ConstantsHBase.IS_meta_false);
		
		style.setAlignment(HorizontalAlignment.CENTER);
		HSSFSheet sheet = workbook.createSheet("格式数据");
		sheet = sheetFormatFieldForm(sheet, style, ft_id, ConstantsHBase.IS_meta_false);
		try {
			OutputStream output = response.getOutputStream();
			workbook.write(output);
			output.flush();
			output.close();
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 导出某格式数据
	 * 
	 * @param response
	 * @param cs_id
	 * @param ft_id
	 *            1公共，0非公共
	 */
	@RequestMapping("/formatData")
	public void formatData(HttpServletRequest request,HttpServletResponse response,HttpSession httpSession, String cs_id, String sourceDataId, String ft_id,
			String formatNodeId, String type, Integer page, Integer strip, Integer searchId, String desc_asc,
			String chooseDatas, String oldConditionNode8, String searchWord, String searchFirstWord, String fieldIds,
			String likeSearch,String ids,boolean isAll) {
		response.setContentType("application/vnd.ms-excel");

		if (ids.startsWith(",")) {
			ids = ids.substring(1, ids.length()).replaceAll("check4_", "");
		}
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		HSSFSheet sheet = workbook.createSheet("格式数据");
		User user = (User) request.getAttribute("user");
		if(isAll) {
			//全选
			List<List<String>> dataDataLists = new ArrayList<>();
			String tableName = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
			FormatDataSQLInfo formatDataSQLInfo = formatNodeController.getFormatDataSQL(cs_id, user, ft_id, sourceDataId, formatNodeId, httpSession, type, desc_asc, searchFirstWord, null, fieldIds, null, searchId, chooseDatas, likeSearch, searchWord, false, ids);
			Map<String, Map<String, Object>> dataDatas = PhoenixClient.select(formatDataSQLInfo.getSql());
			String dataMsg = String.valueOf((dataDatas.get("msg")).get("msg"));
			for (int j = 0; j < 6; j++) {
				dataMsg = String.valueOf((dataDatas.get("msg")).get("msg"));
				if (dataMsg.equals("success")) {
					dataDataLists = (List<List<String>>) dataDatas.get("records").get("data");
					break;
				} else {
					PhoenixClient.undefined(dataMsg, tableName, formatDataSQLInfo.getQualifiers(), formatDataSQLInfo.getConditionEqual(), formatDataSQLInfo.getConditionLike());
					dataDatas = PhoenixClient.select(formatDataSQLInfo.getSql());
				}
			}
			
			HSSFRow row = sheet.createRow((short) 0);
			// 设置表头

			List<FormatField1> formatFields = formatDataSQLInfo.getData1();
			if (!formatFields.isEmpty()&&formatFields.size()>0) {
				HSSFCell cell = row.createCell(0);
				for (int i = 0; i < formatFields.size(); i++) {
					cell = row.createCell((i));
					cell.setCellValue(formatFields.get(i).getFf_name());
					cell.setCellStyle(style);
				}
				// 写入各条记录，每条记录对应Excel中的一行
				if(dataDataLists.size()>0) {
					for (int iRow = 0; iRow < dataDataLists.size(); iRow++) {
						row = sheet.createRow( iRow + 1);
						for (int j = 0; j < formatFields.size(); j++) {
							cell = row.createCell(j);
							cell.setCellValue(dataDataLists.get(iRow).get(j+1));
							cell.setCellStyle(style);
						}
					}
				}
			}
		}else {
			sheet = sheetFormatDataByIds(sheet, style, cs_id, ft_id, ids);
		}
		try {
			OutputStream output = response.getOutputStream();
			workbook.write(output);
			output.flush();
			output.close();
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private HSSFSheet sheetSourceDataForm(HSSFSheet sheet, HSSFCellStyle style, Integer cs_id) {
		Source source = sourceService.getSourceByCs_id(cs_id);
		// 4.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
		HSSFRow row = sheet.createRow((short) 0);
		// 设置表头
		source.setSourceFields(sourceFieldService.getSourceFields(cs_id));
		HSSFCell cell = row.createCell(0);
		for (int i = 0; i < source.getSourceFields().size(); i++) {
			cell = row.createCell((i));
			cell.setCellValue(source.getSourceFields().get(i).getCsf_name());
		}
		return sheet;
	}
	/*
	 * 这个1是测试生产的excel中对字段的描述信息
	 */
	private HSSFSheet sheetSourceDataForm1(HSSFSheet sheet, HSSFCellStyle style, Integer cs_id) {
		
		Source source = sourceService.getSourceByCs_id(cs_id);
		source.setSourceFields(sourceFieldService.getSourceFields(cs_id));
		
		
		for(int a=0;a<=source.getSourceFields().size();a++) {
			// 4.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
			HSSFRow row = sheet.createRow(a);
			
//			HSSFCell createCell = row.createCell(0);
//			createCell.setCellValue(source.getSourceFields().get(a).getCsf_name());
//			HSSFCell createCell = row.createCell(1);
			
			for (int i = 0; i < 6; i++) {
				// 设置表头
				HSSFCell cell = row.createCell(i);
				if(i==0) {
					if(a==0) {
						cell.setCellValue("字段名");
					}else {
						cell.setCellValue(source.getSourceFields().get(a-1).getCsf_name());
					}
				}else if(i==1) {
					if(a==0) {
						cell.setCellValue("描述信息");
					}else {
						
						cell.setCellValue(source.getSourceFields().get(a-1).getDescription());
					}
				}else if(i==2){
					if(a==0) {
						cell.setCellValue("类型");
					}else {
						
						cell.setCellValue(source.getSourceFields().get(a-1).getType());
					}
				}else if(i==3) {
					if(a==0) {
						cell.setCellValue("校验规则");
					}else {
						
						cell.setCellValue(source.getSourceFields().get(a-1).getCheck_rule());
					}
				}else if(i==4) {
					if(a==0) {
						cell.setCellValue("是否枚举");
					}else {
						
						cell.setCellValue(source.getSourceFields().get(a-1).isEnumerated());
					}
				}else if(i==5) {
					if(a==0) {
						cell.setCellValue("枚举值");
					}else {
						
						cell.setCellValue(source.getSourceFields().get(a-1).getEmvalue());
					}
				}
			}
		}
		
		return sheet;
	}

	private HSSFSheet sheetSourceDataByIds(HSSFSheet sheet, HSSFCellStyle style, String cs_id, String sourceDataIds) {
		// 在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
		HSSFRow row = sheet.createRow((short) 0);
		Source source = sourceService.getSourceByCs_id(Integer.valueOf(cs_id));
		// 设置表头
		source.setSourceFields(sourceFieldService.getSourceFields(Integer.valueOf(cs_id)));
		HSSFCell cell = row.createCell(0);
		for (int i = 0; i < source.getSourceFields().size(); i++) {
			cell = row.createCell((i));
			cell.setCellValue(source.getSourceFields().get(i).getCsf_name());
			cell.setCellStyle(style);
		}
		if (sourceDataIds != null) {
			// 写入各条记录，每条记录对应Excel中的一行
			List<List<String>> sourceDatas = HBaseSourceDataDao.getSourceDatasByIds(cs_id, sourceDataIds,
					source.getSourceFields());
			if (sourceDatas.isEmpty()) {
				return sheet;
			}
			for (int iRow = 0; iRow < sourceDatas.size(); iRow++) {
				row = sheet.createRow(iRow + 1);
				for (int j = 0; j < source.getSourceFields().size(); j++) {
					cell = row.createCell(j);
					cell.setCellValue(sourceDatas.get(iRow).get(j + 1));
					cell.setCellStyle(style);
				}
			}
		}
		return sheet;
	}
	private HSSFSheet sheetFormatFieldForm(HSSFSheet sheet, HSSFCellStyle style, String ft_id, Integer isMeta) {
		HSSFRow row = sheet.createRow((short) 0);
		List<FormatField> formatFields = formatFieldService.getFormatFieldsIs_meta(Integer.valueOf(ft_id), isMeta);
		HSSFCell cell = row.createCell(0);
		for (int i = 0; i < formatFields.size(); i++) {
			cell = row.createCell((i));
			cell.setCellValue(formatFields.get(i).getFf_name());
			cell.setCellStyle(style);
		}
		return sheet;
	}
	private HSSFSheet sheetFormatFieldForm1(HSSFSheet sheet, HSSFCellStyle style, String ft_id, Integer isMeta) {
//		HSSFRow row = sheet.createRow((short) 0);
		// 设置表头
		List<FormatField> formatFields = formatFieldService.getFormatFieldsIs_meta(Integer.valueOf(ft_id), isMeta);
//		HSSFCell cell = row.createCell(0);
//		for (int i = 0; i < formatFields.size(); i++) {
//			cell = row.createCell((i));
//			cell.setCellValue(formatFields.get(i).getFf_name());
//			cell.setCellStyle(style);
//		}
		for(int a=0;a<=formatFields.size();a++) {
			// 4.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
			HSSFRow row = sheet.createRow((short)a);
			
//			HSSFCell createCell = row.createCell(0);
//			createCell.setCellValue(source.getSourceFields().get(a).getCsf_name());
//			HSSFCell createCell = row.createCell(1);
			
			for (int i = 0; i < 6; i++) {
				// 设置表头
				HSSFCell cell = row.createCell(i);
				if(i==0) {
					if(a==0) {
						cell.setCellValue("字段名");
					}else {
						cell.setCellValue(formatFields.get(a-1).getFf_name());
					}
				}else if(i==1) {
					if(a==0) {
						cell.setCellValue("描述信息");
					}else {
						
						cell.setCellValue(formatFields.get(a-1).getDescription());
					}
				}else if(i==2){
					if(a==0) {
						cell.setCellValue("类型");
					}else {
						
						cell.setCellValue(formatFields.get(a-1).getType());
					}
				}else if(i==3) {
					if(a==0) {
						cell.setCellValue("校验规则");
					}else {
						
						cell.setCellValue(formatFields.get(a-1).getCheck_rule());
					}
				}else if(i==4) {
					if(a==0) {
						cell.setCellValue("是否枚举");
					}else {
						
						cell.setCellValue(formatFields.get(a-1).isEnumerated());
					}
				}else if(i==5) {
					if(a==0) {
						cell.setCellValue("枚举值");
					}else {
						
						cell.setCellValue(formatFields.get(a-1).getEmvalue());
					}
				}
				cell.setCellStyle(style);
			}
		}
		return sheet;
	}

	private HSSFSheet sheetFormatDataByIds(HSSFSheet sheet, HSSFCellStyle style, String cs_id, String ft_id,
			String formatDataIds) {
		HSSFRow row = sheet.createRow((short) 0);
		// 设置表头

		List<FormatField> formatFields = formatFieldService.getFormatFieldsForUser(Integer.valueOf(ft_id));
		if (formatFields.isEmpty()) {
			return sheet;
		}
		HSSFCell cell = row.createCell(0);
		for (int i = 0; i < formatFields.size(); i++) {
			cell = row.createCell((i));
			cell.setCellValue(formatFields.get(i).getFf_name());
			cell.setCellStyle(style);
		}

		List<List<String>> formatDatas = HBaseFormatDataDao.getFormatDataByIds(cs_id, ft_id, formatDataIds,
				formatFields);
		if (formatDatas.isEmpty()) {
			return sheet;
		}
		// 写入各条记录，每条记录对应Excel中的一行

		for (int iRow = 0; iRow < formatDatas.size(); iRow++) {
			row = sheet.createRow( iRow + 1);
			for (int j = 0; j < formatFields.size(); j++) {
				cell = row.createCell(j);
				cell.setCellValue(formatDatas.get(iRow).get(j));
				cell.setCellStyle(style);
			}
		}

		return sheet;
	}

	private HSSFSheet sheetFormatDatas(HSSFSheet sheet, HSSFCellStyle style, String cs_id, String ft_id,
			String formatNodeId, Integer isMeta) {
		HSSFRow row = sheet.createRow((short) 0);
		// 设置表头
		List<FormatField> formatFields = formatFieldService.getFormatFieldsIs_meta(Integer.valueOf(ft_id), isMeta);
		if (formatFields.isEmpty()) {
			return sheet;
		}

		HSSFCell cell = row.createCell(0);
		for (int i = 0; i < formatFields.size(); i++) {
			cell = row.createCell((i));
			cell.setCellValue(formatFields.get(i).getFf_name());
			cell.setCellStyle(style);
		}

		List<List<String>> formatDatas = HBaseFormatDataDao.getFormatDatas(cs_id, ft_id, formatNodeId, formatFields);
		if (formatDatas == null || formatDatas.isEmpty()) {
			return sheet;
		}
		// 写入各条记录，每条记录对应Excel中的一行
		if (isMeta.equals(ConstantsHBase.IS_meta_true)) {
			row = sheet.createRow((short) 1);
			for (int j = 0; j < formatFields.size(); j++) {
				cell = row.createCell(j);
				cell.setCellValue(formatDatas.get(0).get(j + 1));
				cell.setCellStyle(style);
			}
		} else {
			for (int iRow = 0; iRow < formatDatas.size(); iRow++) {
//				if(iRow>30000)
//				{
//					break;
//				}
				row = sheet.createRow(iRow + 1);
				for (int j = 0; j < formatFields.size(); j++) {
					cell = row.createCell(j);
					cell.setCellValue(formatDatas.get(iRow).get(j + 1));
					cell.setCellStyle(style);
				}
			}
		}
		return sheet;
	}

}
