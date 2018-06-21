package com.x.platform.modules.sys.listener;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import com.x.platform.modules.sys.service.SystemService;
import com.x.sdk.mcs.MCSClientFactory;
import com.x.sdk.mcs.interfaces.ICacheClient;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		logger.info("启动加载开始");
		try{
			 ICacheClient _jedisGnarea = MCSClientFactory.getCacheClient("com.x.platform.common.cache.gnarea");
			_jedisGnarea.del("areaTreeALL","OfficeAllList","findTreeInit");
		}catch(Exception ex){
			logger.error("缓存===com.x.platform.common.cache.gnarea===连接失败");
			
		}

		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		
		logger.info("启动加载完毕");
		
		return super.initWebApplicationContext(servletContext);
	}
}
