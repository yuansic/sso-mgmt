/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.x.platform.modules.sys.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.x.platform.modules.sys.entity.Menu;
import com.x.platform.modules.sys.entity.Office;
import com.x.platform.modules.sys.entity.Role;
import com.x.platform.modules.sys.entity.User;
import com.x.platform.modules.sys.security.SystemAuthorizingRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.x.platform.common.config.Global;
import com.x.platform.common.service.BaseService;
import com.x.platform.common.utils.SpringContextHolder;
import com.x.platform.common.utils.StringUtils;
import com.x.platform.modules.sys.dao.MenuDao;
import com.x.platform.modules.sys.dao.OfficeDao;
import com.x.platform.modules.sys.dao.RoleDao;
import com.x.platform.modules.sys.dao.UserDao;
import com.x.platform.modules.sys.entity.Menu;
import com.x.platform.modules.sys.entity.Office;
import com.x.platform.modules.sys.entity.Role;
import com.x.platform.modules.sys.entity.User;
import com.x.platform.modules.sys.security.SystemAuthorizingRealm.Principal;

/**
 * 用户工具类
 * 
 * @author bonc
 * @version 2013-12-05
 */
public class UserUtils {
	private UserUtils(){
		
	}

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_THEME = "theme";
	public static final String USER_CACHE_THEMEINX = "theme_index";

	public static final String USER_DEFAULT_THEME = Global.getDefTheme();
	
	public static final String SYS_USER_ID ="SYS$SYSUSER$ID";
	public static final String BIND_EMAIL = "email/pwd-reset-binemail.xml";
	public static final String SAVEUSER_EMAIL = "email/user-save-binemail.xml";
	
	
	public static List<User> getUserList(User user){
		return userDao.findList(user);
	}
	

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id) {
		User user = userDao.get(id);
		if (user == null) {
			return null;
		}
		user.setRoleList(roleDao.findList(new Role(user)));
		return user;
	}

	/**
	 * 获取当前用户主题
	 * 
	 * @param id
	 * @return 取不到返回null
	 */
	public static String getTheme() {
		String theme = "";
		String themeIndex = getThemeIndex();
		
		 if(StringUtils.equals(themeIndex, "theme-white")){
			theme= "green";
		}else if(StringUtils.equals(themeIndex, "theme-whbl")){
			theme= "cerulean";
		}else{			
			theme = USER_DEFAULT_THEME;
		}
		return theme;
	}
	/**
	 * 获取当前用户主题
	 * 
	 * @param id
	 * @return 取不到返回null
	 */
	public static String getThemeIndex() {
		String theme = "";
		String theme_index="";
		User user = (User) getUser();
		theme = user.getTheme();

		if(StringUtils.equals(theme, "cerulean") || StringUtils.equals(theme, "default")){
			
			theme_index="theme-whbl";
		}else if(StringUtils.equals(theme, "green")){
			theme_index="theme-white";
		}else{
			theme_index="theme-whbl";
		}
		
	
		return theme_index;
	}
	/**
	 * 根据登录名获取用户
	 * 
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName) {
		User user = userDao.getByLoginName(new User(null, loginName));
		if (user == null) {
			return null;
		}
		user.setRoleList(roleDao.findList(new Role(user)));
		return user;
	}

	/**
	 * 获取当前用户
	 * 
	 * @return 取不到返回 new User()
	 */
	public static User getUser() {
		SystemAuthorizingRealm.Principal principal = getPrincipal();
		if (principal != null) {
			User user = get(principal.getId());
		
			if (user != null) {
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}

	/**
	 * 获取当前用户角色列表
	 * 
	 * @return
	 */
	public static List<Role> getRoleList() {
		User user = getUser();
		if (user.isAdmin()) {
			return roleDao.findAllList(new Role());
		} else {
			Role role = new Role();
			//role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
			return roleDao.findList(role);
		}
	}

	/**
	 * 获取当前用户角色分页列表
	 * 
	 * @return
	 */
	public static List<Role> getRoleList(Role role) {
		List<Role> roleList = null;
		User user = getUser();
		if (user.isAdmin()) {
			roleList = roleDao.findAllByParams(role);
		} else {
			//role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
			roleList = roleDao.findListByParams(role);
		}
		return roleList;
	}

	/**
	 * 获取当前用户授权菜单
	 * 
	 * @return
	 */
	
	public static List<Menu> getMenuList() {
		
		return getMenuList(new Menu());
	}
	
	/**
	 * 获取当前用户单点登录应用资源
	 * 
	 * @return
	 */
	
	public static List<Menu> getAppMenuList() {
		User user = getUser();
		if (user.isAdmin()) {
			return menuDao.findAllAppList(new Menu());
		} else {
			Menu menu = new Menu();
			menu.setUserId(user.getId());
			return menuDao.findByAppUserId(menu);
		}
	}
	
	
	public static List<Menu> getMenuList(Menu menu) {
		User user = getUser();
		if (user.isAdmin()) {
			return menuDao.findAllList(new Menu());
		} else {
			menu.setUserId(user.getId());
			return menuDao.findByUserId(menu);
		}
	}

	/**
	 * 根据父节点ID获取子节点菜单
	 * 
	 * @return
	 */
	public static List<Menu> getMenuNodesbyId(String id) {
		
		// List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_CHILDLIST+id);
		// if (menuList == null || menuList.isEmpty()){
		//
		List<Menu> MenuNodes = new ArrayList<Menu>();
		List<Menu> menuList = getMenuList(new Menu());
		for(Menu menu :menuList){
			if((id).equals(menu.getParentId())){
				 MenuNodes.add(menu);
			}
		}

		// putCache(CACHE_MENU_CHILDLIST+menu.getId(), menuList);
		// }
		return MenuNodes;
	}

	


	/**
	 * 获取当前用户有权限访问的部门
	 * 
	 * @return
	 */
	public static List<Office> getOfficeList() {
			User user = getUser();
			if (user.isAdmin()) {
				return officeDao.findAllList(new Office());
			} else {
				Office office = new Office();
				office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
				return officeDao.findList(office);
			}
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * 
	 * @return
	 */
	public static List<Office> getOfficeAllList() {
		return officeDao.findAllList(new Office());
	}

	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取当前登录者对象
	 */
	public static SystemAuthorizingRealm.Principal getPrincipal(){
		User user =null;
		try{
			Subject subject = SecurityUtils.getSubject();
		    PrincipalCollection principals =subject.getPrincipals();
			Map map =(Map)(principals.asList().get(1));
			user =userDao.get(map.get("userId").toString());
		}catch (Exception e){
			Subject subject = SecurityUtils.getSubject();
		    user =getByLoginName(subject.getPrincipal()+"");
		}finally{
		   if(user == null)
		    	return null;
			SystemAuthorizingRealm.Principal principal =new SystemAuthorizingRealm.Principal(user, false);
			if (principal != null){
				return principal;
			}
		}
		return null;
	}

	public static Session getSession() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			if (session != null) {
				return session;
			}
			// subject.logout();
		} catch (InvalidSessionException e) {

		}
		return null;
	}

	// ============== User Cache ==============

	public static Object getCache(String key) {
		return getCache(key, null);
	}

	public static Object getCache(String key, Object defaultValue) {
		 Object obj = getCacheMap().get(key);
		//Object obj = getSession().getAttribute(key);
		return obj == null ? defaultValue : obj;
	}

	public static void putCache(String key, Object value) {
		 getCacheMap().put(key, value);
		 //getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
		 getCacheMap().remove(key);
		 //getSession().removeAttribute(key);
	}

	 public static Map<String, Object> getCacheMap(){
	 SystemAuthorizingRealm.Principal principal = getPrincipal();
	 if(principal!=null){
		 return principal.getCacheMap();
	 }
	 return null;
	 }

}
