/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.dao;

import java.util.List;

import com.x.platform.common.persistence.TreeDao;
import com.x.platform.common.persistence.annotation.MyBatisDao;
import com.x.platform.modules.sys.entity.Office;
import com.x.platform.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author bonc
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	/**  
	 * 找到所有节点
	 * @param entity
	 * @return
	 */
	public List<Office> find(Office office);
}
