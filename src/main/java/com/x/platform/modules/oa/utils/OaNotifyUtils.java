package com.x.platform.modules.oa.utils;

import java.util.List;

import com.x.platform.common.utils.CacheUtils;
import com.x.platform.common.utils.SpringContextHolder;
import com.x.platform.modules.oa.dao.OaNotifyDao;
import com.x.platform.modules.oa.entity.OaNotify;
import com.x.platform.modules.sys.entity.User;
import com.x.platform.modules.sys.utils.UserUtils;
import com.x.platform.modules.sys.entity.User;
import com.x.platform.modules.sys.utils.UserUtils;

public class OaNotifyUtils {

	private static OaNotifyDao oanotifyDao = SpringContextHolder.getBean(OaNotifyDao.class);
	public static final String OA_CACHE = "oaCache";
	public static final String OA_CACHE_ID_ = "id_oa_";
	
	/**
	 * 获取用户首页公告信息,最新前三条
	 * 
	 * @param id
	 * @return 取不到返回null
	 */
	public static List<OaNotify> getOaNotifyByUser(){

		OaNotify oaNotify = new OaNotify();
		User user = UserUtils.getUser();
		oaNotify.setCurrentUser(user);
		List<OaNotify> oaNotifyList =(List<OaNotify>)oanotifyDao.findListLimit(oaNotify);
		
		if(oaNotifyList.size()>4){
			return oaNotifyList.subList(0, 4);
		}else{
			return oaNotifyList;
		}
		 
		
	}
}
