/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.x.platform.common.persistence.Page;
import com.x.platform.common.service.TreeService;
import com.x.platform.modules.sys.dao.OfficeDao;
import com.x.platform.modules.sys.entity.Office;
import com.x.platform.modules.sys.utils.OfficeUtils;
import com.x.platform.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author bonc
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	public List<Office> find(Office office){
		if(office != null){
			return dao.find(office);
		}
		return  new ArrayList<Office>();
	}
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
			office.setParentIds(office.getParentIds()+"%");
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}

	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		OfficeUtils.removeOfficeCache();
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		OfficeUtils.removeOfficeCache();
	}
	
}
