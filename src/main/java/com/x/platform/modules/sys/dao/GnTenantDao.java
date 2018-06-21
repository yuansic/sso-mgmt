/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.dao;

import com.x.platform.common.persistence.CrudDao;
import com.x.platform.common.persistence.annotation.MyBatisDao;
import com.x.platform.modules.sys.entity.GnTenant;
import com.x.platform.modules.sys.entity.GnTenant;

/**
 * 租户（业务平台）DAO接口
 * @author bonc
 * @version 2016-10-18
 */
@MyBatisDao
public interface GnTenantDao extends CrudDao<GnTenant> {
	
}