package com.xtkong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtkong.dao.FormatTypeDao;
import com.xtkong.model.FormatType;

@Service
public class FormatTypeService {
	@Autowired
	FormatTypeDao formatTypeDao;

	public int insertFormatType(FormatType formatType) {
		return formatTypeDao.insertFormatType(formatType);
	}

	public int updateFormatType(FormatType FormatType) {
		return formatTypeDao.updateFormatType(FormatType);
	}

	/**
	 * 选取格式类型列表
	 * 
	 * @param cs_id
	 * @return 格式类型列表
	 */
	public List<FormatType> getFormatTypes(Integer cs_id) {
		return formatTypeDao.getFormatTypes(cs_id);
	}
	public List<FormatType> getFormatTypesForAdmin(Integer cs_id) {
		return formatTypeDao.getFormatTypesForAdmin(cs_id);
	}
	public FormatType getFormatType(Integer ft_id) {
		return formatTypeDao.getFormatType(ft_id);
	}

	public Integer getFormatType_cs_id(Integer ft_id) {

		return formatTypeDao.getFormatType_cs_id(ft_id);
	}

	public Integer getFormatTypeId(Integer cs_id, String ft_name) {
		return formatTypeDao.getFormatTypeId(cs_id, ft_name);
	}

	public int deleteFormatType(Integer ft_id) {
		return formatTypeDao.deleteFormatType(ft_id);
	}
}
