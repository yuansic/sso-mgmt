package com.x.platform.modules.sys.task;

import java.util.List;

import com.x.platform.modules.sys.entity.GnArea;
import com.x.platform.modules.sys.entity.Office;
import com.x.platform.modules.sys.service.GnAreaService;
import com.x.platform.modules.sys.service.OfficeService;
import org.apache.log4j.Logger;

import com.x.platform.common.config.Global;
import com.x.platform.modules.sys.entity.GnArea;
import com.x.platform.modules.sys.entity.Office;
import com.x.platform.modules.sys.service.GnAreaService;
import com.x.platform.modules.sys.service.OfficeService;

public class OfficeThread extends Thread {
	private static final Logger LOG = Logger.getLogger(OfficeThread.class);

	private OfficeService officeService;
	private GnAreaService areaService;

	private String[] userInfo;

	public OfficeThread(String[] userInfo, OfficeService officeService, GnAreaService areaService) {
		this.userInfo = userInfo;
		this.officeService = officeService;
		this.areaService = areaService;
	}

	public void run() {
		try {

			Office officeInfo = setOfficeInfo(userInfo);
			Office office = new Office();
			office.setCode(officeInfo.getCode());
			List<Office> list = officeService.find(office);
			if (!list.isEmpty()) {
				office = list.get(0);
				office.setCode(officeInfo.getCode());
				office.setName(officeInfo.getName());
				office.setParent(officeInfo.getParent());
				office.setUseable(officeInfo.getUseable());
				office.setType(officeInfo.getType());
				office.setGrade(officeInfo.getGrade());
				officeService.save(office);

			} else {
				officeService.save(officeInfo);
			}
		} catch (Exception e) {
			LOG.info("导入部门信息失败，部门编号:" + userInfo[0] + ",部门名称:" + userInfo[1]);
		}
	}

	/**
	 * 封装导入部门信息
	 * 
	 * @param officeInfo
	 * @return Office
	 */
	private Office setOfficeInfo(String[] officeInfo) {
		Office office = new Office();
		office.setCode(officeInfo[0]);
		office.setName(officeInfo[1]);
		if (!officeInfo[2].isEmpty()) {
			Office parentOffice = new Office();
			parentOffice.setCode(officeInfo[2]);
			List<Office> parentList = officeService.find(parentOffice);
			if (!parentList.isEmpty()) {
				String[] parentIds = parentList.get(0).getParentIds().split(",");
				switch (parentIds.length) {
				case 1:
					office.setGrade("2");
					break;
				case 2:
					office.setGrade("3");
					break;
				default:
					office.setGrade("4");
					break;
				}
				office.setType("2");// 部门
				office.setParent(parentList.get(0));
			} else {
				office.setGrade("1");// 默认导入的部门级别为：一级
				office.setType("1");// 公司
			}
		}
		GnArea gnArea = areaService.getByCode("00");
		if (gnArea != null) {
			office.setGnArea(gnArea);
		}
		office.setUseable(officeInfo[3]);

		office.setTenantId(Global.getTenantID());
		return office;
	}

}
