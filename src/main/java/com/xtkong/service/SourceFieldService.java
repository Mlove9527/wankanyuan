package com.xtkong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtkong.dao.SourceFieldDao;
import com.xtkong.model.SourceField;

@Service
public class SourceFieldService {
	@Autowired
	SourceFieldDao sourceFieldDao;

	public int insertSourceField(SourceField sourceField) {
		return sourceFieldDao.insertSourceField(sourceField);
	}

	public int updateSourceField(SourceField sourceField) {
		return sourceFieldDao.updateSourceField(sourceField);
	}

	/**
	 * * 选取采集源字段列表
	 * 
	 * @param cs_id
	 *            采集源
	 * @return 采集源字段列表
	 */
	public List<SourceField> getSourceFields(int cs_id) {
		return sourceFieldDao.getSourceFields(cs_id);
	}
	public List<SourceField> getSourceFieldsForAdmin(int cs_id) {
		return sourceFieldDao.getSourceFieldsForAdmin(cs_id);
	}
	public Integer getSourceFieldId(int cs_id,String csf_name){
		return sourceFieldDao.getSourceFieldId(cs_id, csf_name);
	}

	public int deleteSourceField(Integer cs_id) {
		return sourceFieldDao.deleteSourceField(cs_id);
	}

	public SourceField getSourceField(Integer csf_id) {
		return sourceFieldDao.getSourceField(csf_id);
	}
	
	/*
	 * 通过cs_id和csf_id来获取这个这个信息
	 */
	public SourceField getSourceFieldMsg(int cs_id,int csf_id) {
		return sourceFieldDao.getSourceFieldMsg(cs_id, csf_id);
	}
}
