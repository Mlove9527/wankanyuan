package com.xtkong.service;

import com.xtkong.dao.FormatFieldDao;
import com.xtkong.dao.FormatFileDao;
import com.xtkong.model.FormatField;
import com.xtkong.model.FormatFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormatFileService {
	@Autowired
	FormatFileDao formatFileDao;

	public int insertFormatFile(FormatFile formatFile) {
		return formatFileDao.insertFormatFile(formatFile);
	}

//	public int updateFormatFile(Integer uid,Integer csf_id,FormatFile newFormatFile) {
//		return formatFileDao.updateFormatFileByUidAndUid(uid,csf_id,newFormatFile);
//	}

	public FormatFile SelectFormatFileByMD5Code(String md5Code) {
		return formatFileDao.selectFormatFileByMD5Code(md5Code);
	}
}
