/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.x.platform.common.persistence.DataEntity;

/**
 * 应用配置Entity
 * @author bonc
 * @version 2016-08-11
 */
public class GnTabSystem extends DataEntity<GnTabSystem> {
	
	private static final long serialVersionUID = 1L;
	private String systemId;		// 应用编码
	private String oldSystemId;
	private String systemName;		// 应用名称
	private String oldSystemName;
	private String systemUrlContext;		// 应用上下文
	
	public GnTabSystem() {
		super();
	}

	public GnTabSystem(String id){
		super(id);
	}


	
	public String getOldSystemId() {
		return oldSystemId;
	}

	public void setOldSystemId(String oldSystemId) {
		this.oldSystemId = oldSystemId;
	}

	public String getOldSystemName() {
		return oldSystemName;
	}

	public void setOldSystemName(String oldSystemName) {
		this.oldSystemName = oldSystemName;
	}

	@Length(min=1, max=32, message="应用编码长度必须介于 1 和 32 个字符 之间")
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	@Length(min=1, max=64, message="应用名称长度必须介于 1 和 64  个字符之间")
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	@Length(min=0, max=500, message="应用上下文长度必须介于 0 和 400 个字符之间")
	public String getSystemUrlContext() {
		return systemUrlContext;
	}

	public void setSystemUrlContext(String systemUrlContext) {
		this.systemUrlContext = systemUrlContext;
	}
	
}