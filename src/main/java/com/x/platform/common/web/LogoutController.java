package com.x.platform.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.x.sso.client.filter.SSOClientConstants;
import com.x.sso.client.filter.SSOClientUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 单点登录用户退出
 * @author bonc
 *
 */
@Controller
public class LogoutController {
	private static final Logger LOG = LoggerFactory.getLogger(LogoutController.class);
	@RequestMapping("/home/ssologout")
	public void logout(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		String logOutServerUrl = SSOClientUtil.getLogOutServerUrlRuntime(request);
		String logOutBackUrl = SSOClientUtil.getLogOutBackUrlRuntime(request);
		try {
			session.removeAttribute(SSOClientConstants.USER_SESSION_KEY);
			session.invalidate();
			response.sendRedirect(logOutServerUrl + "?service=" + logOutBackUrl);
		} catch (IOException e) {
			LOG.error("用户登出失败",e);
		}
	}

}
