/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.common.utils.excel.fieldtype;

import com.x.platform.common.utils.StringUtils;
import com.x.platform.modules.sys.entity.Office;
import com.x.platform.modules.sys.utils.UserUtils;
import com.x.platform.modules.sys.entity.Office;
import com.x.platform.modules.sys.utils.UserUtils;

/**
 * 字段类型转换
 * @author bonc
 * @version 2013-03-10
 */
public class OfficeType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		for (Office e : UserUtils.getOfficeList()){
			if (StringUtils.trimToEmpty(val).equals(e.getCode())){
				return e;
			}
		}
		return null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Office)val).getCode() != null){
			return ((Office)val).getCode();
		}
		return "";
	}
}
