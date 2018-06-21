/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.common.utils.excel.fieldtype;

import com.x.platform.modules.sys.entity.Area;
import com.x.platform.modules.sys.entity.Area;

/**
 * 字段类型转换
 * @author bonc
 * @version 2013-03-10
 */
public class AreaType {


	/**
	 * 获取对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Area)val).getName() != null){
			return ((Area)val).getName();
		}
		return "";
	}
}
