/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.x.platform.common.config.Global;
import com.x.platform.common.persistence.Page;
import com.x.platform.common.utils.StringUtils;
import com.x.platform.common.web.BaseController;
import com.x.platform.modules.sys.entity.GnArea;
import com.x.platform.modules.sys.service.GnAreaService;
import com.x.platform.modules.sys.utils.GnAreaUtils;
import com.x.sdk.mcs.MCSClientFactory;
import com.x.sdk.mcs.interfaces.ICacheClient;
import com.x.sdk.util.SerializeUtil;

/**
 * Common工程统一区域代码Controller
 * 
 * @author bonc
 * @version 2016-08-17
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/gnArea")
public class GnAreaController extends BaseController {

	private static final String AREATREECACHENS = "com.x.platform.common.cache.gnarea";
	private static final String REDIRECT = "redirect:";
	private static final String REPAGE = "/sys/gnArea/?repage";
	@Autowired
	private GnAreaService gnAreaService;

	@ModelAttribute
	public GnArea get(@RequestParam(required = false) String id, @RequestParam(required = false) String areaCode) {
		GnArea entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = gnAreaService.get(id);
		}

		if (entity == null) {
			entity = new GnArea();
		}
		return entity;
	}

	@RequiresPermissions("sys:gnArea:view")
	@RequestMapping(value = { "list", "" })
	public String list(GnArea gnArea, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GnArea> page = gnAreaService.findPage(new Page<GnArea>(request, response), gnArea);
		model.addAttribute("page", page);
		return "modules/sys/gnAreaList";
	}

	@RequiresPermissions("sys:gnArea:view")
	@RequestMapping(value = "form")
	public String form(GnArea gnArea, Model model) {
		gnArea.setParentAreaCode(GnAreaUtils.getParentCode(gnArea.getAreaCode()));

		model.addAttribute("gnArea", gnArea);
		return "modules/sys/gnAreaForm";
	}

	@RequiresPermissions("sys:gnArea:edit")
	@RequestMapping(value = "save")
	public String save(GnArea gnArea, Model model, RedirectAttributes redirectAttributes) {
		if ((gnArea.getId() == null || "".equals(gnArea.getId()))
				&& gnAreaService.getByCode(gnArea.getAreaCode()) != null) {
			addMessage(model, "保存失败，区域编码已存在");
			return form(gnArea, model);
		}
		if (gnArea.getParentAreaCode() == null || "".equals(gnArea.getParentAreaCode())) {
			addMessage(model, "保存失败，请选择所属区域");
			return form(gnArea, model);
		}
		// 设置记录是否为新记录
		if (gnArea.getId() == null || "".equals(gnArea.getId())) {
			gnArea.setIsNewRecord(true);
			gnArea.setId(gnArea.getAreaCode());
		} else {
			gnArea.setIsNewRecord(false);
		}

		GnArea parentArea = gnAreaService.getByCode(gnArea.getParentAreaCode());

		gnArea.setAreaLevel(Integer.toString(StringUtils.toInteger(parentArea.getAreaLevel()) + 1));

		if (StringUtils.toInteger(parentArea.getAreaLevel()) >= 4) {
			addMessage(model, "保存失败，所属区域不能选择乡镇街道级");
			return form(gnArea, model);
		}
		// 如果是省级 则将所属市编码插入为 000 所属省为当前区域的编码
		if ("1".equals(gnArea.getAreaLevel())) {

			gnArea.setCityCode("000");
			gnArea.setProvinceCode(gnArea.getAreaCode());
		}
		// 如果是国家级 则将所属市编码插入为 000 所属省为当前区域的编码
		if ("0".equals(gnArea.getAreaLevel())) {
			gnArea.setCityCode("000");
			gnArea.setProvinceCode("00");

		}
		// 如果是地市级所属市为当前区域的编码
		if ("2".equals(gnArea.getAreaLevel())) {
			gnArea.setCityCode(gnArea.getAreaCode());
			gnArea.setProvinceCode(gnArea.getParentAreaCode());
		}
		// 如果是区县级所属市为当前区域的编码
		if ("3".equals(gnArea.getAreaLevel())) {
			gnArea.setCityCode(gnArea.getParentAreaCode());

			gnArea.setProvinceCode(parentArea.getParentAreaCode());
		}
		// 如果是街道级所属市为当前区域的编码
		if ("4".equals(gnArea.getAreaLevel())) {
			String cityCode = parentArea.getParentAreaCode();

			String provinceCode = gnAreaService.getByCode(cityCode).getParentAreaCode();
			gnArea.setCityCode(cityCode);
			gnArea.setProvinceCode(provinceCode);
		}

		gnAreaService.save(gnArea);
		addMessage(redirectAttributes, "保存区域信息成功");
		return REDIRECT + Global.getAdminPath() + REPAGE;
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "refCacheArea")
	public String refCacheArea(RedirectAttributes redirectAttributes) {
		addMessage(redirectAttributes, "刷新区域缓存成功");
		return REDIRECT + Global.getAdminPath() + REPAGE;
	}

	@RequiresPermissions("sys:gnArea:edit")
	@RequestMapping(value = "delete")
	public String delete(GnArea gnArea, RedirectAttributes redirectAttributes) {
		gnArea.setState("0");
		gnAreaService.delete(gnArea);
		addMessage(redirectAttributes, "删除区域信息成功");
		return REDIRECT + Global.getAdminPath() + REPAGE;
	}

	@SuppressWarnings("unchecked")
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String areaId,
			@RequestParam(required = false) boolean init, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		ICacheClient jedis = null;

		jedis = MCSClientFactory.getCacheClient(AREATREECACHENS);
		if (init) {

			byte[] in = jedis.get("findTreeInit".getBytes());
			mapList = (List<Map<String, Object>>) SerializeUtil.deserialize(in);
			if (mapList == null || mapList.isEmpty()) {
				mapList = Lists.newArrayList();

				List<GnArea> listInit = gnAreaService.findTreeInit();

				for (int i = 0; i < listInit.size(); i++) {
					GnArea e = listInit.get(i);
					if (StringUtils.isBlank(areaId) || (areaId != null && !areaId.equals(e.getId()))) {
						Map<String, Object> map = Maps.newHashMap();
						map.put("id", e.getId());
						map.put("pId", (e.getParentAreaCode() == null) ? "" : e.getParentAreaCode());
						map.put("name", e.getAreaName());
						map.put("isParent", isParent(e.getId()));
						mapList.add(map);

					}
				}
				jedis.set("findTreeInit".getBytes(), SerializeUtil.serialize(mapList));
			}
		} else if (StringUtils.isNotBlank(areaId)) {

			List<GnArea> listAsyc = GnAreaUtils.findListByParentAreaCode(areaId);
			for (int i = 0; i < listAsyc.size(); i++) {
				GnArea e = listAsyc.get(i);
				if (StringUtils.isBlank(areaId) || (areaId != null && !areaId.equals(e.getId()))) {

					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", (e.getParentAreaCode() == null) ? "" : e.getParentAreaCode());
					map.put("name", e.getAreaName());
					map.put("isParent", isParent(e.getId()));
					mapList.add(map);
				}
			}

		}

		return mapList;
	}

	private boolean isParent(String parentId) {
		if (StringUtils.isBlank(parentId)) {
			return false;
		}

		List<GnArea> list = GnAreaUtils.getParentCodeList(parentId);
		return !list.isEmpty();
	}
}