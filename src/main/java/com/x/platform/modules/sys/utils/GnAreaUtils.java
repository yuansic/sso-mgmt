package com.x.platform.modules.sys.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.x.platform.common.utils.SpringContextHolder;
import com.x.platform.modules.sys.dao.GnAreaDao;
import com.x.platform.modules.sys.entity.GnArea;
import com.x.sdk.mcs.MCSClientFactory;
import com.x.sdk.mcs.interfaces.ICacheClient;
import com.x.sdk.util.CollectionUtil;
import com.x.sdk.util.SerializeUtil;

public class GnAreaUtils {
	@Autowired
	private final static GnAreaDao gnAreaDao = SpringContextHolder.getBean(GnAreaDao.class);

	private final static ICacheClient jedis = MCSClientFactory.getCacheClient("com.x.platform.common.cache.gnarea");

	private static List<GnArea> listAll = Lists.newArrayList();

	private GnAreaUtils() {

	}

	/**
	 * 获取当前用户授权的新区域
	 * 
	 * @return
	 */
	public static List<GnArea> getGnAreaList() {

		if (CollectionUtil.isEmpty(listAll)) {
			listAll = Lists.newArrayList();
			listAll = GnAreaUtils.gnAreaDao.findList(new GnArea());
			jedis.set("areaTreeALL".getBytes(), SerializeUtil.serialize(listAll));
		}

		return listAll;
	}

	public static String getAreaName(String code) {
		if (StringUtils.isNotBlank(code)) {
			for (GnArea gnArea : GnAreaUtils.getGnAreaList()) {
				if ((code).equals(gnArea.getAreaCode())) {
					return gnArea.getAreaName();
				}
			}
		}
		return null;
	}

	public static GnArea getGnAreaByCode(String code) {
		if (StringUtils.isNotBlank(code)) {
			for (GnArea gnArea : GnAreaUtils.getGnAreaList()) {
				if ((code).equals(gnArea.getAreaCode())) {
					return gnArea;
				}
			}
		}
		return null;
	}

	public static String getParentCode(String code) {
		if (StringUtils.isNotBlank(code)) {
			for (GnArea gnArea : GnAreaUtils.getGnAreaList()) {
				if ((code).equals(gnArea.getAreaCode())) {
					return gnArea.getParentAreaCode();
				}
			}
		}
		return null;
	}

	public static List<GnArea> findListByParentAreaCode(String gnAreaParent) {
		List<GnArea> list = Lists.newArrayList();
		if (StringUtils.isNotBlank(gnAreaParent)) {
			List<GnArea> allAreaList = GnAreaUtils.getGnAreaList();
			if (!CollectionUtil.isEmpty(allAreaList)) {

				for (GnArea gnArea : allAreaList) {
					if ((gnAreaParent).equals(gnArea.getParentAreaCode())) {
						list.add(gnArea);

					}
				}
			}

			return list;
		}
		return list;
	}

	public static List<GnArea> getParentCodeList(String code) {
		List<GnArea> mapper = Lists.newArrayList();
		if (StringUtils.isNotBlank(code)) {
			for (GnArea gnArea : GnAreaUtils.getGnAreaList()) {
				if ((code).equals(gnArea.getParentAreaCode())) {
					mapper.add(gnArea);
				}
			}
		}
		return mapper;
	}

	public static void clearCache() {
		jedis.del("findTreeInit".getBytes());
		jedis.del("areaTreeALL".getBytes());

	}

}
