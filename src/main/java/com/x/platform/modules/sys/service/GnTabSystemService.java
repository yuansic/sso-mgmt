/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.service;

import java.util.List;

import com.x.platform.modules.sys.security.SystemAuthorizingRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.x.platform.common.persistence.Page;
import com.x.platform.common.service.CrudService;
import com.x.platform.common.utils.CacheUtils;
import com.x.platform.modules.sys.dao.GnTabSystemDao;
import com.x.platform.modules.sys.entity.GnTabSystem;
import com.x.platform.modules.sys.security.SystemAuthorizingRealm;
import com.x.platform.modules.sys.utils.LogUtils;

/**
 * 应用配置Service
 * @author bonc
 * @version 2016-08-11
 */
@Service
@Transactional(readOnly = true)
public class GnTabSystemService extends CrudService<GnTabSystemDao, GnTabSystem> {
	
	@Autowired
	private GnTabSystemDao gnTabSystemDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	
	public GnTabSystem get(String id) {
		return super.get(id);
	}
	
	public List<GnTabSystem> findList(GnTabSystem gnTabSystem) {
		return super.findList(gnTabSystem);
	}
	
	public Page<GnTabSystem> findPage(Page<GnTabSystem> page, GnTabSystem gnTabSystem) {
		return super.findPage(page, gnTabSystem);
	}
	
	@Transactional(readOnly = false)
	public void save(GnTabSystem gnTabSystem) {
		super.save(gnTabSystem);
//		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
		
	}
	
	@Transactional(readOnly = false)
	public void delete(GnTabSystem gnTabSystem) {
		super.delete(gnTabSystem);

	}

	public List<GnTabSystem> findAll() {
		return gnTabSystemDao.findAllList(new GnTabSystem());
	}

	public List<GnTabSystem> findvalidateList(GnTabSystem gnTabSystem) {
		return gnTabSystemDao.findAllList(gnTabSystem);
	}
	
}