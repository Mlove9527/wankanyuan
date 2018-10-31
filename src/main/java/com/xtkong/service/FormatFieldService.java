package com.xtkong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtkong.dao.FormatFieldDao;
import com.xtkong.model.FormatField;

@Service
public class FormatFieldService {
	@Autowired
	FormatFieldDao formatFieldDao;

	public int insertFormatField(FormatField formatField) {
		return formatFieldDao.insertFormatField(formatField);
	}

	public int updateFormatField(FormatField formatField) {
		return formatFieldDao.updateFormatField(formatField);
	}

	public int deleteFormatField(Integer ff_id) {
		return formatFieldDao.deleteFormatField(ff_id);
	}

	/**
	 * 选取格式字段列表
	 * 
	 * @param ft_id
	 *            格式类型
	 * @return 选取格式字段列表
	 */
	public List<FormatField> getFormatFieldsForAdmin(Integer ft_id) {
		return formatFieldDao.getFormatFieldsForAdmin(ft_id);
	}

	public List<FormatField> getFormatFieldsForUser(Integer ft_id) {
		return formatFieldDao.getFormatFieldsForUser(ft_id);
	}

	public List<FormatField> getFormatFieldsIs_meta(Integer ft_id, Integer is_meta) {
		return formatFieldDao.getFormatFieldsIs_meta(ft_id, is_meta);
	}

	public FormatField getFormatField(Integer ff_id) {
		return formatFieldDao.getFormatField(ff_id);
	}

	public Integer getFormatField_ft_id(Integer ff_id) {
		return formatFieldDao.getFormatField_ft_id(ff_id);
	}

	public Integer getFormatField_ff_id(Integer ft_id, String ff_name) {
		return formatFieldDao.getFormatField_ff_id(ft_id,ff_name);
	}
}
