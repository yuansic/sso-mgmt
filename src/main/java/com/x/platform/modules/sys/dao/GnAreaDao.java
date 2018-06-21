/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.dao;

import java.util.List;

import com.x.platform.common.persistence.CrudDao;
import com.x.platform.common.persistence.annotation.MyBatisDao;
import com.x.platform.modules.sys.entity.GnArea;
import com.x.platform.modules.sys.entity.GnArea;

/**
 * Common工程统一区域代码DAO接口
 * @author bonc
 * @version 2016-08-17
 */
@MyBatisDao
public interface GnAreaDao extends CrudDao<GnArea> {
	public List<GnArea> findListByParentAreaCode(GnArea gnArea);
	@Override
	public List<GnArea> findList(GnArea gnArea);
	
	public GnArea getByCode(String areaCode);
	
	public List<GnArea> findTreeInit();
	
}