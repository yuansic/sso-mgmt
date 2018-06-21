/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.x.platform.modules.sys.entity.GnTenant;
import com.x.platform.modules.sys.service.GnTenantService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.x.platform.common.config.Global;
import com.x.platform.common.persistence.Page;
import com.x.platform.common.web.BaseController;
import com.x.platform.common.utils.StringUtils;
import com.x.platform.modules.sys.entity.GnTenant;
import com.x.platform.modules.sys.service.GnTenantService;

/**
 * 租户（业务平台）Controller
 * @author bonc
 * @version 2016-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/gnTenant")
public class GnTenantController extends BaseController {

	@Autowired
	private GnTenantService gnTenantService;
	
	@ModelAttribute
	public GnTenant get(@RequestParam(required=false) String id) {
		GnTenant entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gnTenantService.get(id);
		}
		if (entity == null){
			entity = new GnTenant();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:gnTenant:view")
	@RequestMapping(value = {"list", ""})
	public String list(GnTenant gnTenant, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GnTenant> page = gnTenantService.findPage(new Page<GnTenant>(request, response), gnTenant); 
		model.addAttribute("page", page);
		return "modules/sys/gnTenantList";
	}

	@RequiresPermissions("sys:gnTenant:view")
	@RequestMapping(value = "form")
	public String form(GnTenant gnTenant, Model model) {
		model.addAttribute("gnTenant", gnTenant);
		return "modules/sys/gnTenantForm";
	}

	@RequiresPermissions("sys:gnTenant:edit")
	@RequestMapping(value = "save")
	public String save(GnTenant gnTenant, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gnTenant)){
			return form(gnTenant, model);
		}
		gnTenantService.save(gnTenant);
		addMessage(redirectAttributes, "保存业务平台成功");
		return "redirect:"+Global.getAdminPath()+"/sys/gnTenant/?repage";
	}
	
	@RequiresPermissions("sys:gnTenant:edit")
	@RequestMapping(value = "delete")
	public String delete(GnTenant gnTenant, RedirectAttributes redirectAttributes) {
		gnTenantService.delete(gnTenant);
		addMessage(redirectAttributes, "删除业务平台成功");
		return "redirect:"+Global.getAdminPath()+"/sys/gnTenant/?repage";
	}
	
	
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkTenantName")
	public String checkTenantName(String oldTenantName, String tenantName) {
		if (tenantName != null && tenantName.equals(oldTenantName)) {
			return "true";
		} else if (tenantName != null && gnTenantService.get(tenantName) == null) {
			return "true";
		}
		return "false";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkTenantId")
	public String checkTenantId(String oldTenantId, String tenantId) {
		if (tenantId != null && tenantId.equals(oldTenantId)) {
			return "true";
		} else if (tenantId != null && gnTenantService.get(tenantId) == null) {
			return "true";
		}
		return "false";
	}

}