/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.oa.dao;

import java.util.List;

import com.x.platform.common.persistence.CrudDao;
import com.x.platform.common.persistence.annotation.MyBatisDao;
import com.x.platform.modules.oa.entity.OaNotify;

/**
 * 通知通告DAO接口
 * @author bonc
 * @version 2014-05-16
 */
@MyBatisDao
public interface OaNotifyDao extends CrudDao<OaNotify> {
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(OaNotify oaNotify);
	/**
	 * 查询自己的通知前三条,用于首页展示
	 * @param oaNotify
	 * @return
	 */	
	public List<OaNotify> findListLimit(OaNotify oaNotify);
	
}