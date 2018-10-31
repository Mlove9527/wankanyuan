
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
import com.xtkong.model.SourceField;
import com.xtkong.service.PhoenixClient;
import com.xtkong.service.SourceFieldService;
import com.xtkong.util.ConstantsHBase;

@Controller
@RequestMapping(value = "/sourceField")
public class SourceFieldController {
	@Autowired
	SourceDao sourceDao;
	@Autowired
	SourceFieldService sourceFieldService;

	@RequestMapping("/insertSourceField")
	@ResponseBody
	public Map<String, Object> insertSourceField(HttpServletRequest request, SourceField sourceField) {
		User user = (User) request.getAttribute("user");
		Integer uid = user.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置创建时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sourceField.setCreate_datetime(simpleDateFormat.format(new Date()));
		sourceField.setCreate_uid(uid);
		sourceField.setUpdate_datetime(simpleDateFormat.format(new Date()));
		sourceField.setUpdate_uid(uid);
		
		if (1 == sourceFieldService.insertSourceField(sourceField)) {
			PhoenixClient.alterViewAddColumn(ConstantsHBase.TABLE_PREFIX_SOURCE_ + sourceField.getCs_id(), String
					.valueOf(sourceFieldService.getSourceFieldId(sourceField.getCs_id(), sourceField.getCsf_name())));
			map.put("result", true);
			map.put("message", "新增成功");
		} else {
			map.put("result", false);
			map.put("message", "新增失败");
		}
		return map;
	}

	@RequestMapping("/getSourceField")
	@ResponseBody
	public Map<String, Object> getSourceField(Integer csf_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		SourceField sourceField = sourceFieldService.getSourceField(csf_id);
		map.put("result", true);
		map.put("sourceField", sourceField);

		return map;
	}

	@RequestMapping("/updateSourceField")
	@ResponseBody
	public Map<String, Object> updateSourceField(HttpServletRequest request, SourceField sourceField) {
		User user = (User) request.getAttribute("user");
		Integer uid = user.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置更新时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sourceField.setCreate_datetime(simpleDateFormat.format(new Date()));
		sourceField.setUpdate_uid(uid);

		if (1 == sourceFieldService.updateSourceField(sourceField)) {
			map.put("result", true);
			map.put("message", "更新成功");
		} else {
			map.put("result", false);
			map.put("message", "更新失败");
		}
		return map;
	}

	@RequestMapping("/deleteSourceField")
	@ResponseBody
	public Map<String, Object> deleteSourceField(String csf_ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] csf_idStrs = csf_ids.split(",");
		int i = 0;
		Integer cs_id = null;
		List<String> qualifiers = new ArrayList<>();
		for (String csf_id : csf_idStrs) {
			if (csf_id == null) {
				cs_id = sourceFieldService.getSourceField(Integer.valueOf(csf_id)).getCs_id();
			}
			if (1 == sourceFieldService.deleteSourceField(Integer.valueOf(csf_id))) {
				i++;
				qualifiers.add(csf_id);
			}
		}
		String tableName = ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id;
		PhoenixClient.alterViewDropColumns(tableName, qualifiers);
		if (i == csf_idStrs.length) {
			map.put("result", true);
			map.put("message", "成功删除" + i + "行");
		} else {
			map.put("result", false);
			map.put("message", "已删除：" + i + "行，剩余" + (csf_idStrs.length - i) + "行未删除");
		}
		return map;
	}
}
