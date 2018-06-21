/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.x.platform.modules.sys.entity.Log;
import com.x.platform.modules.sys.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.x.platform.common.persistence.Page;
import com.x.platform.common.web.BaseController;
import com.x.platform.modules.sys.entity.Log;
import com.x.platform.modules.sys.entity.User;
import com.x.platform.modules.sys.service.LogService;
import com.x.platform.modules.sys.service.SystemService;
import com.x.sdk.util.StringUtil;

/**
 * 日志Controller
 * @author bonc
 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/log")
public class LogController extends BaseController {

	@Autowired
	private LogService logService;
	
	@RequiresPermissions("sys:log:view")
	@RequestMapping(value = {"list",""})
	public String list(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
		log.setTitle("系统登录");
//		if(log !=null && log.getCreateBy() !=null && log.getCreateBy().getName() !=null){
//			User user = systemService.getUserByName(log.getCreateBy().getName());
//			log.setCreateBy(user);
//		}
		String requestUri = log.getRequestUri();
		if(!StringUtil.isBlank(requestUri)) {
			log.setRequestUri(requestUri.replaceAll("/", "//"));
		}
		Page<Log> page = logService.findPage(new Page<Log>(request, response), log); 
		log.setRequestUri(requestUri);
        model.addAttribute("page", page);
		return "modules/sys/logList";
	}
	
	
	@RequiresPermissions("sys:log:view")
	@RequestMapping(value = {"page"})
	public String page(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Log> page = logService.findPage(new Page<Log>(request, response,5), log); 
        model.addAttribute("page", page);
		return "modules/mgmtsys/logList";
	}

}
