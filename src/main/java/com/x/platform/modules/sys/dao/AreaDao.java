/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.dao;

import com.x.platform.common.persistence.TreeDao;
import com.x.platform.common.persistence.annotation.MyBatisDao;
import com.x.platform.modules.sys.entity.Area;
import com.x.platform.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author bonc
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}
