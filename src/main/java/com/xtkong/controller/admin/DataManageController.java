package com.xtkong.controller.admin;




import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xtkong.model.Source;
import com.xtkong.service.FormatFieldService;
import com.xtkong.service.FormatTypeService;
import com.xtkong.service.SourceFieldService;
import com.xtkong.service.SourceService;

@Controller
@RequestMapping(value = "/admin")
public class DataManageController {

    @Autowired
    SourceService sourceService;
    @Autowired
    SourceFieldService sourceFieldService;
    @Autowired
    FormatTypeService formatTypeService;
    @Autowired
    FormatFieldService formatFieldService;

    /**
     * 说明：首次进入格式数据管理页面，默认显示第一个数据源所有内容； 返回：所有采集源基础信息，第一个采集源字段、采集源所有格式类型
     *
     * @param httpSession
     * @param cs_id
     *            源
     * @param csf_ids
     *            源字段
     * @param ft_id
     *            类型
     * @param ft_ids
     * @param ff_id
     *            类型字段
     * @param ff_ids
     * @return 采集源基础信息、采集源字段、采集源所有格式类型
     */
    @RequestMapping(value = "/formatdata")
    public String formatData(HttpSession httpSession, Integer cs_id, String csf_ids, Integer ft_id, String ft_ids,
                             Integer ff_id, String ff_ids) {
        List<Source> sources = sourceService.getSourcesForAdmin();
        Source source = new Source();
        int i = 0;
        if (!sources.isEmpty()) {
            if (cs_id != null) {
                cs_id = cs_id;
            } else if (csf_ids != null) {
                String[] csf_idsStr = csf_ids.split(",");
                cs_id = sourceFieldService.getSourceField(Integer.valueOf(csf_idsStr[0])).getCs_id();
            } else if (ft_id != null) {
                cs_id = formatTypeService.getFormatType_cs_id(ft_id);
            } else if (ft_ids != null) {
                String[] ft_idsStr = ft_ids.split(",");
                cs_id = formatTypeService.getFormatType_cs_id(Integer.valueOf(ft_idsStr[0]));
            } else if (ff_id != null) {
                cs_id = formatTypeService.getFormatType_cs_id(formatFieldService.getFormatField_ft_id(ff_id));
            } else if (ff_ids != null) {
                String[] ff_idsStr = ff_ids.split(",");
                cs_id = formatTypeService
                        .getFormatType_cs_id(formatFieldService.getFormatField_ft_id(Integer.valueOf(ff_idsStr[0])));
            } else if (cs_id == null) {
                cs_id = sources.get(0).getCs_id();
            }
            for (; i < sources.size(); i++) {
                if (cs_id == sources.get(i).getCs_id()) {
                    break;
                }
            }
            if (i >= sources.size()) {
                i = 0;
            }
            source = sources.get(i);
            source.setSourceFields(sourceFieldService.getSourceFieldsForAdmin(cs_id));
            source.setFormatTypes(formatTypeService.getFormatTypesForAdmin(cs_id));
            if ((ft_id == null) && (!source.getFormatTypes().isEmpty())) {
                ft_id = source.getFormatTypes().get(0).getFt_id();
            }
        }
        httpSession.setAttribute("sources", sources);
        httpSession.setAttribute("source", source);
        httpSession.setAttribute("cs_index", i);
        httpSession.setAttribute("thiscs_id", cs_id);
        httpSession.setAttribute("ft_id", ft_id);

        return "redirect:/admin/datamanage.jsp";

    }

}