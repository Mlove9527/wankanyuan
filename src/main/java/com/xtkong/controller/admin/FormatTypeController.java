
package com.xtkong.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liutianjun.pojo.User;
import com.xtkong.dao.SourceDao;
import com.xtkong.dao.hbase.HBaseFormatDataDao;
import com.xtkong.model.FormatType;
import com.xtkong.service.FormatFieldService;
import com.xtkong.service.FormatTypeService;
import com.xtkong.service.PhoenixClient;
import com.xtkong.util.ConstantsHBase;

@Controller
@RequestMapping(value = "/formatType")
public class FormatTypeController {
	@Autowired
	SourceDao sourceDao;
	@Autowired
	FormatTypeService formatTypeService;
	@Autowired
	FormatFieldService formatFieldService;

	/**
	 * 新增格式类型:创建格式数据表
	 * 
	 * @param formatType
	 *            待增格式类型
	 * @param uid
	 * @return 执行情况，采集源id
	 */
	@RequestMapping("/insertFormatType")
	@ResponseBody
	public Map<String, Object> insertFormatType(HttpServletRequest request,FormatType formatType) {
		User user = (User) request.getAttribute("user");
		Integer uid = user.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置创建时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatType.setCreate_datetime(simpleDateFormat.format(new Date()));
		formatType.setCreate_uid(uid);
		formatType.setUpdate_datetime(simpleDateFormat.format(new Date()));
		formatType.setUpdate_uid(uid);
		
		if (1 == formatTypeService.insertFormatType(formatType)) {
			map.put("result", true);
			map.put("message", "新增成功");
			String cs_id=String.valueOf(formatType.getCs_id());
			Integer ft_id=formatTypeService.getFormatTypeId(formatType.getCs_id(), formatType.getFt_name());
			HBaseFormatDataDao.createFormatDataTable(cs_id, String.valueOf(ft_id));
		
			String tableName=ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
			List<String> typeQualifiers=new ArrayList<>();
			typeQualifiers.add(ConstantsHBase.QUALIFIER_SOURCEDATAID);
			typeQualifiers.add(ConstantsHBase.QUALIFIER_FORMATNODEID);
			PhoenixClient.createView(tableName, typeQualifiers);
		} else {
			map.put("result", false);
			map.put("message", "新增失败");
		}
		return map;
	}

	/**
	 * 提供：格式类型id 返回：执行情况，格式类型基础信息、该类型所有格式字段
	 * 
	 * @param ft_id
	 * @return 执行情况，格式类型基础信息、该类型所有格式字段
	 */
	@RequestMapping(value = "/getFormatFields")
	@ResponseBody
	public Map<String, Object> getFormatFields(Integer ft_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		FormatType formatType = formatTypeService.getFormatType(ft_id);
		if (formatType != null) {
			formatType.setFormatFields(formatFieldService.getFormatFieldsForAdmin(ft_id));
			map.put("result", true);
			map.put("formatType", formatType);
		} else {
			map.put("result", false);
			map.put("message", "查询失败");
		}
		return map;
	}

	/**
	 * 更新一条格式类型
	 * 
	 * @param formatType
	 *            待更新格式类型
	 * @param uid
	 * @return 执行情况，采集源id
	 */
	@RequestMapping("/updateFormatType")
	@ResponseBody
	public Map<String, Object> updateFormatType(HttpServletRequest request,FormatType formatType) {
		User user = (User) request.getAttribute("user");
		Integer uid = user.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatType.setUpdate_datetime(simpleDateFormat.format(new Date()));
		formatType.setUpdate_uid(uid);

		if (1 == formatTypeService.updateFormatType(formatType)) {
			map.put("result", true);
			map.put("message", "更新成功");
		} else {
			map.put("result", false);
			map.put("message", "更新失败");
		}

		return map;
	}

	/**
	 * 删除一条格式类型
	 * 
	 * @param ft_id
	 *            待删除格式类型id
	 * @return 执行情况，采集源id
	 */
	@RequestMapping("/deleteFormatType")
	@ResponseBody
	public Map<String, Object> deleteFormatType(String ft_ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] ft_idStrs = ft_ids.split(",");
		int i = 0;
		Integer cs_id=formatTypeService.getFormatType_cs_id(Integer.valueOf(ft_idStrs[0]));
		for (String ft_id : ft_idStrs) {
			if (1 == formatTypeService.deleteFormatType(Integer.valueOf(ft_id))) {
				HBaseFormatDataDao.deleteFormatDataTable(String.valueOf(cs_id), String.valueOf(ft_id));
				PhoenixClient.dropView(ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id);
				i++;
			}
		}
		if (i == ft_idStrs.length) {
			map.put("result", true);
			map.put("message", "成功删除" + i + "行");
		} else {
			map.put("result", false);
			map.put("message", "已删除：" + i + "行，剩余" + (ft_idStrs.length - i) + "行未删除");
		}
		return map;
	}

}
