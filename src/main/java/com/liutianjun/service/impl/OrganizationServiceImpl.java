package com.liutianjun.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liutianjun.dao.OrganizationDao;
import com.liutianjun.pojo.Organization;
import com.liutianjun.pojo.OrganizationQuery;
import com.liutianjun.pojo.OrganizationQuery.Criteria;
import com.liutianjun.service.OrganizationService;

/**
 * 组织结构Impl
 * @Title: OrganizationServiceImpl.java  
 * @Package com.liutianjun.service.impl  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年6月1日  
 * @version V1.0
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDao organizationDao;
	
	/**
	 * 添加组织结构
	 * @Title: addNewOrg 
	 * @param record
	 * @return 
	 * int
	 */
	@Override
	public int addNewOrg(Organization record) {
		record.setCreateTime(new Date());
		record.setParentId(0);
		record.setRootId(0);
		record.setStatus(0);
		organizationDao.insert(record);
		record.setRootId(record.getId());
		
		return organizationDao.updateByPrimaryKeySelective(record);
	}

	/**
	 * 添加组
	 * <p>Title: addNewGroup</p>  
	 * <p>Description: </p>  
	 * @param parentId
	 * @param id
	 * @return
	 */
	@Override
	public int addNewGroup(Integer parentId, String organizationName) {
		Organization org = organizationDao.selectByPrimaryKey(parentId);
		
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
		Organization organization = new Organization();
		organization.setCreateTime(new Date());
		organization.setParentId(parentId);
		organization.setRootId(org.getRootId());
		organization.setOrganizationName(organizationName);
		organization.setCreator(username);
		organization.setStatus(1);
		return organizationDao.insert(organization);
	}

	/**
	 * 更新组信息
	 * <p>Title: undateGroup</p>  
	 * <p>Description: </p>  
	 * @param organizationName
	 * @return
	 */
	@Override
	public int undateGroup(Organization record) {
		return organizationDao.updateByPrimaryKeySelective(record);
	}

	/**
	 * 删除组
	 * <p>Title: deleteGroupById</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return
	 */
	@Override
	public int deleteGroupById(Integer id) {
		//同时删除重置组内成员的组id
		return organizationDao.deleteByPrimaryKey(id);
	}

	/**
	 * 根据父ID显示列表
	 * <p>Title: findOrgList</p>  
	 * <p>Description: </p>  
	 * @param parentid
	 * @return
	 */
	@Override
	public List<Organization> findOrgList(Integer parentId) {
		OrganizationQuery example = new OrganizationQuery();
		Criteria criteria = example.createCriteria();
		if(parentId != -1) {
			criteria.andParentIdEqualTo(parentId);
		}
		criteria.andStatusEqualTo(1);
		List<Organization> orgList = organizationDao.selectByExample(example);
		for (Organization org : orgList) {
			org.setGroupList(findOrgList(org.getId()));
		}
		
		return orgList;
	}
	
	@Override
	public List<Organization> findOrgList(Integer parentId,List<Integer> list) {
		OrganizationQuery example = new OrganizationQuery();
		Criteria criteria = example.createCriteria();
		if(null != list && list.size()>0) {
			criteria.andRootIdIn(list);
		}else {
			criteria.andRootIdEqualTo(-1);
		}
		if(parentId != -1) {
			criteria.andParentIdEqualTo(parentId);
		}
		criteria.andStatusEqualTo(1);
		List<Organization> orgList = organizationDao.selectByExample(example);
		for (Organization org : orgList) {
			org.setGroupList(findOrgList(org.getId(),list));
		}
		
		return orgList;
	}

	/**
	 * 处理添加组织结构请求
	 * <p>Title: dealAddOrgRequest</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @param cmd 0:拒绝 1:通过
	 * @return
	 */
	@Override
	public int dealAddOrgRequest(Integer[] ids, Integer cmd) {
		OrganizationQuery example = new OrganizationQuery();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(Arrays.asList(ids));
		Organization organization = new Organization();
		organization.setStatus(cmd);
		return organizationDao.updateByExampleSelective(organization, example);
	}

	/**
	 * 根据id获取组
	 * <p>Title: selectByPrimaryKey</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return
	 */
	@Override
	public Organization selectByPrimaryKey(Integer id) {
		return organizationDao.selectByPrimaryKey(id);
	}

	/**
	 * 根据用户名获取组织结构IDS
	 * <p>Title: fingOrgIds</p>  
	 * <p>Description: </p>  
	 * @return
	 */
	@Override
	public List<Integer> fingOrgIds() {
		List<Integer> list = new ArrayList<>();
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		OrganizationQuery example = new OrganizationQuery();
		Criteria criteria = example.createCriteria();
		criteria.andCreatorEqualTo(username);
		List<Organization> orgList = organizationDao.selectByExample(example);
		if(null != orgList && orgList.size() > 0) {
			for (Organization organization : orgList) {
				list.add(organization.getRootId());
			}
		}
		return list;
	}

	/**
	 * 查询组织机构
	 * <p>Title: findOrgList</p>  
	 * <p>Description: </p>  
	 * @param page
	 * @param rows
	 * @param organizationName
	 * @return
	 */
	@Override
	public Map<String, Object> findOrgList(Integer page, Integer rows, String organizationName) {
		OrganizationQuery example = new OrganizationQuery();
		Criteria criteria = example.createCriteria();
		if(null != organizationName && organizationName.trim() != "") {
			criteria.andOrganizationNameLike("%"+organizationName+"%");
		}
		criteria.andParentIdEqualTo(0);
		int total = organizationDao.countByExample(example);
		example.setPageNo(page);
		example.setPageSize(rows);
		List<Organization> list = organizationDao.selectByExample(example);
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		map.put("total", total);
		return map;
	}
	
	

}
