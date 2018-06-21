# 统一系统管理平台
### 部署环境
* jdk 1.8+
* tomcat 8.5+
* gradle 3.1+
* mysql 5.7+

### 应用集成
* 待补充

### 技术选型
##### 后端
* JDK：1.8（支持1.6+）
* 数据库：Mysql 5.7
* 项目构建工具：gradle 3.+
* 配置中心 disconf
* MVC框架：SpringMVC 4.1.7.RELEASE
* 核心框架：Spring 4.1.7.RELEASE
* ORM框架：MyBatis 3.2.8
* 分布式协调服务：Zookeeper 3.4.6
* 分布式RPC服务：Dubbox 2.8.3
* 分布式缓存服务：Redis 3.0.1
* 分布式消息服务：ActiveMQ 5.13.3
* NIO框架：Netty 4.0.23.Final
* JSON工具：Fastjson 1.2.29
* 定时任务：Quartz 2.2.1
* 数据库连接池：Druid 1.0.18
* 日志管理：SLF4J 1.7.7,log4j 1.2.17
* 单点登录：cas 3.3.3
* 权限控制：shrio 1.2.3

##### 前端
* 待补充

##### 配置说明
	1.修改sso.properties中对应的单点登录服务器地址，和认证成功后的返回地址
	2.修改paas/paas-conf.properties中对应的disconf系统参数
	3.修改mgmt.properties中对应的系统参数
	4.目前数据库连接池使用的是spring-context.xml中的配置，部署时，需要修改此文件。将来sdk修正完毕后，需将数据库配置存入disconf系统中。
	5.修改表gn_tab_system.SYSTEM_URL_CONTEXT为对应系统域名或应用地址，例如“统一系统管理平台”的地址为：http://172.16.13.158:8085/mgmt/home

##### 部署过程
* 待补充

##### 常用sql
```sql
/**查询有效用户信息*/
SELECT * FROM devcommondb.sys_user where del_flag = '0';
/**查询管理员用户*/
select * from sys_user where id in (SELECT user_id 
FROM devcommondb.sys_user_role where role_id in (1,3,4) and user_id in (SELECT id
FROM devcommondb.sys_user where del_flag = '0'));
/**查询应用系统对应的域名*/
SELECT id, tenant_id, system_id, system_name, remarks, SYSTEM_URL_CONTEXT, del_flag, create_by, create_date, update_by, update_date 
FROM devcommondb.gn_tab_system
```
