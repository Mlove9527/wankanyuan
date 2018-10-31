package com.xtkong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtkong.dao.SourceDao;
import com.xtkong.model.Source;

@Service
public class SourceService {
	@Autowired
	SourceDao sourceDao;
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
	public Source getSourceLimit( Integer num){
		return sourceDao.getSourceLimit(num);
	}
	
	public Integer getSourceId( String cs_name){
		return sourceDao.getSourceId(cs_name);
	}
	public int deleteSource(Integer cs_id) {
		return sourceDao.deleteSource(cs_id);
	}
}
