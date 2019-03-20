package com.xtkong.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtkong.dao.SourceDao;
import com.xtkong.dao.hbase.HBaseFormatDataDao;
import com.xtkong.dao.hbase.HBaseFormatNodeDao;
import com.xtkong.dao.hbase.HBaseSourceDataDao;
import com.xtkong.model.FormatField;
import com.xtkong.model.Source;
import com.xtkong.model.SourceField;
import com.xtkong.util.ConstantsHBase;

@Service
public class SourceService {
	@Autowired
	SourceDao sourceDao;
	@Autowired
	SourceFieldService sourceFieldService;
	@Autowired
	FormatFieldService formatFieldService;
	/**
	 * 新增采集源
	 * @param source
	 * @return
	 */
	public int insertSource(Source source){
		return sourceDao.insertSource(source);
	}
	public int updateSource(Source source){
		return sourceDao.updateSource(source);
	}
	/**
	 * 选取数据源，用户
	 * @return	数据源列表
	 */
	public List<Source> getSourcesForUser() {
		return sourceDao.getSourcesForUser();
	}
	public List<Source> getSourcesForUserLimit(Integer num) {
		return sourceDao.getSourcesForUserLimit(num);
	}

	public List<Source> getSourcesForUserAndProjectLimit(Integer uid,Integer num) {
		return sourceDao.getSourcesForUserAndProjectLimit(uid,num);
	}
	/**
	 * 选取数据源，管理
	 * @return	数据源列表
	 */
	public List<Source> getSourcesForAdmin() {
		return sourceDao.getSourcesForAdmin();
	}
	
	public Source getSourceByCs_id(Integer cs_id){
		return sourceDao.getSourceByCs_id(cs_id);
	}
	public Source getSourceByCs_Name(String cs_name){
		return sourceDao.getSourceByCs_Name(cs_name);
	}
	public Source getSourceLimit( Integer num){
		return sourceDao.getSourceLimit(num);
	}
	
	public Integer getSourceId( String cs_name){
		return sourceDao.getSourceId(cs_name);
	}
	public int deleteSource(Integer cs_id) {
		return sourceDao.deleteSource(cs_id);
	}
	
	/**
     * 新增采集源(表+视图)
     *
     * @param cs_id
     * @return
     */
    public void insertSourceByCsId(Integer cs_id) {
    	//新增Source表
        HBaseSourceDataDao.createSourceDataTable(String.valueOf(cs_id));
        List<String> qualifiers = new ArrayList<String>();
		qualifiers.add(ConstantsHBase.QUALIFIER_PROJECT);
		qualifiers.add(ConstantsHBase.QUALIFIER_CREATE);
		qualifiers.add(ConstantsHBase.QUALIFIER_USER);
		qualifiers.add(ConstantsHBase.QUALIFIER_PUBLIC);
		qualifiers.add(ConstantsHBase.QUALIFIER_CREATOR);
		qualifiers.add(ConstantsHBase.QUALIFIER_CREATE_DATETIME);
		qualifiers.add(ConstantsHBase.QUALIFIER_SOURCEDATAID);//old
		List<SourceField> fieldList = sourceFieldService.getSourceFieldsForAdmin(cs_id);
		if(fieldList!=null&&fieldList.size()>0) {
			for(SourceField field:fieldList) {
				qualifiers.add(String.valueOf(field.getCsf_id()));
			}
		}
        PhoenixClient.createView(ConstantsHBase.TABLE_PREFIX_SOURCE_ + cs_id, qualifiers);
        //新增Node表
        HBaseFormatNodeDao.createFormatNodeTable(String.valueOf(cs_id));
        List<String> nodeQualifiers = new ArrayList<>();
        nodeQualifiers.add(ConstantsHBase.QUALIFIER_FORMATTYPE);//结点格式数据类型
        nodeQualifiers.add(ConstantsHBase.QUALIFIER_NODENAME);//结点名
        nodeQualifiers.add(ConstantsHBase.QUALIFIER_SOURCEDATAID);//源数据id
        PhoenixClient.createView(ConstantsHBase.TABLE_PREFIX_NODE_ + cs_id, nodeQualifiers);
    }
    
    
    /**
            * 新增node(表+视图)
     *
     * @param cs_id
     * @return
     */
    public void insertNodeByCsId(Integer cs_id){
    	 HBaseFormatNodeDao.createFormatNodeTable(String.valueOf(cs_id));
         List<String> nodeQualifiers = new ArrayList<>();
         nodeQualifiers.add(ConstantsHBase.QUALIFIER_FORMATTYPE);//结点格式数据类型
         nodeQualifiers.add(ConstantsHBase.QUALIFIER_NODENAME);//结点名
         nodeQualifiers.add(ConstantsHBase.QUALIFIER_SOURCEDATAID);//源数据id
         PhoenixClient.createView(ConstantsHBase.TABLE_PREFIX_NODE_ + cs_id, nodeQualifiers);
    }
    
	/**
	     * 新增Format(表+视图)
	*
	* @param cs_id，ft_id
	* @return
	*/
     public void  insertFormatByCsId(Integer cs_id,Integer ft_id) {
    	HBaseFormatDataDao.createFormatDataTable(String.valueOf(cs_id), String.valueOf(ft_id));
 		List<String> qualifiers = new ArrayList<String>();
 		qualifiers.add(ConstantsHBase.QUALIFIER_SOURCEDATAID);
 		qualifiers.add(ConstantsHBase.QUALIFIER_FORMATNODEID);
 		List<FormatField> fieldList = formatFieldService.getFormatFieldsByFormatTypeID(ft_id);
 		if(fieldList!=null&&fieldList.size()>0) {
 			for(FormatField field:fieldList) {
 				qualifiers.add(String.valueOf(field.getFf_id()));
 			}
 		}
        PhoenixClient.createView(ConstantsHBase.TABLE_PREFIX_FORMAT_ + cs_id + "_" + ft_id, qualifiers);
     }
     
}
