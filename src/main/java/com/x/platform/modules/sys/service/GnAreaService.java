/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.x.platform.common.persistence.Page;
import com.x.platform.common.service.CrudService;
import com.x.platform.modules.sys.entity.GnArea;
import com.x.platform.modules.sys.utils.GnAreaUtils;
import com.x.platform.modules.sys.utils.UserUtils;
import com.x.platform.modules.sys.dao.GnAreaDao;

/**
 * Common工程统一区域代码Service
 * @author bonc
 * @version 2016-08-17
 */
@Service
@Transactional(readOnly = true)
public class GnAreaService extends CrudService<GnAreaDao, GnArea> {
	@Autowired
	private GnAreaDao areaDao;

	public GnArea get(String id,String areaCode) {
		return super.get(new GnArea(id,areaCode));
	}
	
	public GnArea getByCode(String areaCode) {
		return areaDao.getByCode(areaCode);
	}
	
	public List<GnArea> findList(GnArea gnArea) {
		return areaDao.findList(gnArea);
	}
	
	public List<GnArea> findListByParentAreaCode(GnArea gnArea) {
		return areaDao.findListByParentAreaCode(gnArea);
	}
	
	public Page<GnArea> findPage(Page<GnArea> page, GnArea gnArea) {
		return super.findPage(page, gnArea);
	}
	
	@Transactional(readOnly = false)
	public void save(GnArea gnArea) {
		GnAreaUtils.clearCache();
		super.save(gnArea);
	}
	
	@Transactional(readOnly = false)
	public void delete(GnArea gnArea) {
		GnAreaUtils.clearCache();
		super.delete(gnArea);
	}

	public List<GnArea> findTreeInit() {
		// TODO Auto-generated method stub
		return areaDao.findTreeInit();
	}

	
	
}