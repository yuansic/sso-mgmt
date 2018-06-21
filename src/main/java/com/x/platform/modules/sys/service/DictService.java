/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.x.platform.common.service.CrudService;
import com.x.platform.common.utils.CacheUtils;
import com.x.platform.modules.sys.dao.DictDao;
import com.x.platform.modules.sys.entity.Dict;
import com.x.platform.modules.sys.utils.DictUtils;

/**
 * 字典Service
 * @author bonc
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
	}

}
