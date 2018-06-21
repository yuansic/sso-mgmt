/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.x.platform.modules.sys.entity.Role;
import com.x.platform.modules.sys.entity.RoleMenu;
import com.x.platform.modules.sys.service.SystemService;
import com.x.platform.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.x.platform.common.config.Global;
import com.x.platform.common.persistence.Page;
import com.x.platform.common.utils.StringUtils;
import com.x.platform.common.web.BaseController;
import com.x.platform.modules.sys.entity.Role;
import com.x.platform.modules.sys.entity.RoleMenu;
import com.x.platform.modules.sys.service.SystemService;
import com.x.platform.modules.sys.utils.UserUtils;

/**
 * 角色Controller
 * 
 * @author bonc
 * @version 2013-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/rolemenu")
public class RoleMenuController extends BaseController {

	@Autowired
	private SystemService systemService;

	// @Autowired
	// private OfficeService officeService;

	@ModelAttribute("role")
	public Role get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return systemService.getRole(id);
		} else {
			return new Role();
		}
	}

	@RequiresPermissions("sys:rolemenu:view")
	@RequestMapping(value = { "list", "" })
	public String list(Role role, HttpServletRequest request, HttpServletResponse response, Model model) {
		// List<Role> list = systemService.findAllRole();
		// model.addAttribute("list", list);
		Page<Role> page = new Page<Role>(request, response);
		role.setPage(page);
		List<Role> list = systemService.findRoleList(role);
		page.setList(list);
		model.addAttribute("page", page);
		return "modules/mgmtsys/role_menu_list";
	}

	@RequiresPermissions("sys:rolemenu:view")
	@RequestMapping(value = "form")
	public String form(Role role, Model model) {
		model.addAttribute("menuList", systemService.findAllMenu());
		// model.addAttribute("officeList", officeService.findAll());
		return "modules/mgmtsys/role_menu_form";
	}

	@RequiresPermissions("sys:rolemenu:edit")
	@RequestMapping(value = "save")
	public String save(RoleMenu roleMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!UserUtils.getUser().isAdmin()) {
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + adminPath + "/sys/rolemenu/form?id=" + roleMenu.getId();
		}
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/rolemenu/form?id=" + roleMenu.getId();
		}
		systemService.saveRoleMenu(roleMenu);
		addMessage(redirectAttributes, "权限赋值成功");
		return "redirect:" + adminPath + "/sys/rolemenu/list?repage";
	}
}
