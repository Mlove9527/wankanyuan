
package com.xtkong.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xtkong.dao.hbase.HBaseFormatNodeDao;
import com.xtkong.dao.hbase.HBaseSourceDataDao;
import com.xtkong.model.Source;
import com.xtkong.service.FormatTypeService;
import com.xtkong.service.PhoenixClient;
import com.xtkong.service.SourceFieldService;
import com.xtkong.service.SourceService;
import com.xtkong.util.ConstantsHBase;

@Controller
@RequestMapping(value = "/source")
public class SourceController {
	@Autowired
	SourceService sourceService;
	@Autowired
	SourceFieldService sourceFieldService;
	@Autowired
	FormatTypeService formatTypeService;

	/**
	 * 新增采集源
	 * 
	 * @param source
	 * @return
	 */
	@RequestMapping(value = "/insertSource")
	public String insertSource(HttpSession httpSession, Source source) {
		sourceService.insertSource(source);
		Integer cs_id = sourceService.getSourceId(source.getCs_name());
		// source.setCs_id(cs_id);
		// source.setSourceFields(sourceFieldService.getSourceFieldsForAdmin(cs_id));
		// source.setFormatTypes(formatTypeService.getFormatTypesForAdmin(cs_id));

		HBaseSourceDataDao.createSourceDataTable(String.valueOf(cs_id));
		List<String> sourceQualifiers = new ArrayList<>();
		sourceQualifiers.add(ConstantsHBase.QUALIFIER_PROJECT);//项目
		sourceQualifiers.add(ConstantsHBase.QUALIFIER_CREATE);//我创建的
		sourceQualifiers.add(ConstantsHBase.QUALIFIER_PUBLIC);//公共的
		sourceQualifiers.add(ConstantsHBase.QUALIFIER_SOURCEDATAID);//old
		PhoenixClient.createView(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id, sourceQualifiers);

		HBaseFormatNodeDao.createFormatNodeTable(String.valueOf(cs_id));
		List<String> nodeQualifiers = new ArrayList<>();
		nodeQualifiers.add(ConstantsHBase.QUALIFIER_FORMATTYPE);//结点格式数据类型
		nodeQualifiers.add(ConstantsHBase.QUALIFIER_NODENAME);//结点名
		nodeQualifiers.add(ConstantsHBase.QUALIFIER_SOURCEDATAID);//源数据id
		PhoenixClient.createView(ConstantsHBase.TABLE_PREFIX_NODE_ + cs_id, nodeQualifiers);

		// httpSession.setAttribute("source", source);

		return "redirect:/admin/formatdata?cs_id=" + cs_id;
	}

	/**
	 * 提供：采集源id 返回：执行状况，采集源基础信息、采集源字段、采集源所有格式类型
	 * 
	 * @param cs_id
	 *            采集源id
	 * @return 执行状况，采集源基础信息、采集源字段、采集源所有格式类型
	 */
	@RequestMapping("/getSourceAll")
	@ResponseBody
	public Map<String, Object> getSourceAll(Integer cs_id) {
		Map<String, Object> map = new HashMap<String, Object>();

		Source source = sourceService.getSourceByCs_id(cs_id);
		if (source != null) {
			source.setSourceFields(sourceFieldService.getSourceFieldsForAdmin(cs_id));
			source.setFormatTypes(formatTypeService.getFormatTypesForAdmin(cs_id));
			map.put("result", true);
			map.put("source", source);
		} else {
			map.put("result", false);
			map.put("message", "查询失败");
		}
		return map;
	}

	@RequestMapping("/updateSource")
	@ResponseBody
	public Map<String, Object> updateSource(Source source) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (1 == sourceService.updateSource(source)) {
			map.put("result", true);
			map.put("message", "更新成功");
		} else {
			map.put("result", false);
			map.put("message", "更新失败");
		}
		return map;
	}

	@RequestMapping("/deleteSource")
	@ResponseBody
	public Map<String, Object> deleteSource(Integer cs_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (1 == sourceService.deleteSource(cs_id)) {
				HBaseSourceDataDao.deleteSourceDataTable(String.valueOf(cs_id));
				PhoenixClient.dropView(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id);

				HBaseFormatNodeDao.deleteFormatNodeTable(String.valueOf(cs_id));
				PhoenixClient.dropView(ConstantsHBase.TABLE_PREFIX_NODE_ + cs_id);
				map.put("result", true);
				map.put("message", "删除成功");
			} else {
				map.put("result", false);
				map.put("message", "删除失败");
			}
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "请先删除该采集源所有字段");
		}
		return map;
	}

}
