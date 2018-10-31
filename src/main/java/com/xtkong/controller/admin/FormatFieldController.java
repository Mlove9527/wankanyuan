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
import com.xtkong.model.FormatField;
import com.xtkong.service.FormatFieldService;
import com.xtkong.service.PhoenixClient;
import com.xtkong.util.ConstantsHBase;

@Controller
@RequestMapping(value = "/formatField")
public class FormatFieldController {
	@Autowired
	FormatFieldService formatFieldService;

	/**
	 * 新增一条格式字段
	 * 
	 * @param formatField
	 *            待增格式字段
	 * @param uid
	 * @param cs_id
	 * @return 执行情况
	 */
	@RequestMapping("/insertFormatField")
	@ResponseBody
	public Map<String, Object> insertFormatField(HttpServletRequest request, FormatField formatField, Integer cs_id) {
		User user = (User) request.getAttribute("user");
		Integer uid = user.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置创建时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatField.setCreate_datetime(simpleDateFormat.format(new Date()));
		formatField.setCreate_uid(uid);

		if (1 == formatFieldService.insertFormatField(formatField)) {
			String tableName = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + formatField.getFt_id();
			Integer ff_id = formatFieldService.getFormatField_ff_id(formatField.getFt_id(), formatField.getFf_name());
			String qualifier = String.valueOf(ff_id);
			PhoenixClient.alterViewAddColumn(tableName, qualifier);
			map.put("result", true);
			map.put("message", "新增成功");
		} else {
			map.put("result", false);
			map.put("message", "新增失败");
		}
		return map;
	}

	/**
	 * 更新一条格式字段
	 * 
	 * @param formatField
	 *            待更新格式字段
	 * @param uid
	 * @return 执行情况
	 */
	@RequestMapping("/updateFormatField")
	@ResponseBody
	public Map<String, Object> updateFormatField(HttpServletRequest request, FormatField formatField) {
		User user = (User) request.getAttribute("user");
		Integer uid = user.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatField.setUpdate_datetime(simpleDateFormat.format(new Date()));
		formatField.setUpdate_uid(uid);
		if (1 == formatFieldService.updateFormatField(formatField)) {
			map.put("result", true);
			map.put("message", "更新成功");
		} else {
			map.put("result", false);
			map.put("message", "更新失败");
		}
		return map;
	}

	/**
	 * 删除一条格式字段
	 * 
	 * @param cs_id
	 * 
	 * @param ff_id
	 *            待删除格式字段id
	 * @return 执行情况
	 */
	@RequestMapping("/deleteFormatField")
	@ResponseBody
	public Map<String, Object> deleteFormatField(String ff_ids, String cs_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] ff_idStrs = ff_ids.split(",");
		int i = 0;
		List<String> qualifiers = new ArrayList<>();
		String ft_id = null;
		for (String ff_id : ff_idStrs) {
			if (1 == formatFieldService.deleteFormatField(Integer.valueOf(ff_id))) {
				if (ft_id == null) {
					ft_id = String.valueOf(formatFieldService.getFormatField_ft_id(Integer.valueOf(ff_id)));
				}
				qualifiers.add(ff_id);
				i++;
			}
		}
		String tableName = ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id;
		PhoenixClient.alterViewDropColumns(tableName, qualifiers);
		if (i == ff_idStrs.length) {
			map.put("result", true);
			map.put("message", "成功删除" + i + "行");
		} else {
			map.put("result", false);
			map.put("message", "已删除：" + i + "行，剩余" + (ff_idStrs.length - i) + "行未删除");
		}
		return map;
	}
}
