package com.x.platform.modules.sys.utils;

import java.util.List;

import com.x.platform.modules.sys.entity.GnTabSystem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.x.platform.common.utils.SpringContextHolder;
import com.x.platform.modules.sys.dao.GnTabSystemDao;
import com.x.sso.client.filter.SSOClientUtil;

public class GnTabSystemUtils {
	@Autowired
	private final static GnTabSystemDao gnTabSystemDao = SpringContextHolder.getBean(GnTabSystemDao.class);
	
	private static String homeContextUrl;
	private GnTabSystemUtils(){
		
	}
	/**
	 * 获取当前用户授权的新区域
	 * 
	 * @return
	 */
	public static List<GnTabSystem> getGnTabSystemList() {
		return gnTabSystemDao.findList(new GnTabSystem());
	}
 
	

	public static GnTabSystem getGnTabSystem(String id){
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(id)){
			for (GnTabSystem gnTabSystem : getGnTabSystemList()){
				if((id).equals(gnTabSystem.getId())){
					return gnTabSystem;
				}
			}
		}
		return null;
	}
	public static String getGnTabSystemName(String id){
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(id)){
			for (GnTabSystem gnTabSystem : getGnTabSystemList()){
				if((id).equals(gnTabSystem.getId())){
					return gnTabSystem.getSystemName();
				}
			}
		}
		return null;
	}
	public static String getGnTabSystemUrl(String id){
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(id)){
			for (GnTabSystem gnTabSystem : getGnTabSystemList()){
				if((id).equals(gnTabSystem.getId())){
					return gnTabSystem.getSystemUrlContext();
				}
			}
		}
		return null;
	}

	/**
	 * 获取serverContext
	 * @return
	 * @author bonc
	 */
	public static String getHomeContextUrl(){
		if(homeContextUrl==null){
			homeContextUrl =SSOClientUtil.getProperty("serverName")+SSOClientUtil.getProperty("serverContextPath");
		}
		return homeContextUrl;
	}

}
