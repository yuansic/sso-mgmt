<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<script type="text/javascript" src="${ctxStatic}/layui/layer.js"></script>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出角色数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/role/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body').css('top','55px');
			});
		});
	
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function assign(id){
			window.location.href='${ctx}/sys/role/assign?id='+id;
	    }
		function updateRole(id){
			window.location.href='${ctx}/sys/role/form?id='+id;
	    }
		function deleteRole(id){
			return confirmInfo('确认要删除该角色吗？','${ctx}/sys/role/delete?id='+id);
	    }
		
		function confirmInfo(msg, url){
			layer.confirm(msg, {
				  btn: ['确认','关闭'], //按钮
				  title: "系统提示"
				}, function(){
					window.location.href = url;
				}, function(){
				 /*  layer.msg('也可以这样', {
				    time: 20000, //20s后自动关闭
				    btn: ['明白了', '知道了']
				  }); */
				});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/role/">角色列表</a></li>
		<shiro:hasPermission name="sys:role:edit"><li><a href="${ctx}/sys/role/form">角色添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="role" action="${ctx}/sys/role/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>角色名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>英文名称：</label>
				<form:input path="enname" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<!-- <input id="btnExport" class="btn btn-primary" type="button" value="导出"/> --> 
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>角色名称</th><th>英文名称</th><!-- <th>归属机构</th> --><shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission></tr>
		<c:forEach items="${page.list}" var="role">
			<tr>
				<td><a href="javascript:void(0)" onclick="updateRole('${role.id}')">${role.name}</a></td>
				<td>${role.enname}</td>
				<%-- <td>${role.office.name}</td> --%>
				<shiro:hasPermission name="sys:role:edit"><td>
					<a href="javascript:void(0)" onclick="assign('${role.id}')">分配</a>
					<c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
						<a href="javascript:void(0)" onclick="updateRole('${role.id}')">修改</a>
					</c:if>
					<c:if test="${(role.id ne '1')&&(role.id ne '2')&&(role.id ne '3')&&(role.id ne '4')&&(role.id ne '5')&&(role.id ne '6')}">
						<a href="javascript:void(0)" onclick="deleteRole('${role.id}')">删除</a>
					</c:if>
				</td></shiro:hasPermission>	
			</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>