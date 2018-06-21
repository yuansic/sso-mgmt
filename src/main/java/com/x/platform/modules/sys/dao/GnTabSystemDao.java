/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.dao;

import com.x.platform.common.persistence.CrudDao;
import com.x.platform.common.persistence.annotation.MyBatisDao;
import com.x.platform.modules.sys.entity.GnTabSystem;
import com.x.platform.modules.sys.entity.GnTabSystem;

/**
 * 应用配置DAO接口
 * @author bonc
 * @version 2016-08-11
 */
@MyBatisDao
public interface GnTabSystemDao extends CrudDao<GnTabSystem> {
	
}