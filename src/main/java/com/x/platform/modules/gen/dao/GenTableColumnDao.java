/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.gen.dao;

import com.x.platform.common.persistence.CrudDao;
import com.x.platform.common.persistence.annotation.MyBatisDao;
import com.x.platform.modules.gen.entity.GenTableColumn;
import com.x.platform.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 * @author bonc
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {
	
	public void deleteByGenTableId(String genTableId);
}
