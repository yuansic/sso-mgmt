/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.x.platform.common.service.TreeService;
import com.x.platform.modules.sys.dao.AreaDao;
import com.x.platform.modules.sys.entity.Area;
import com.x.platform.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * @author bonc
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {


	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
	}
	
}
