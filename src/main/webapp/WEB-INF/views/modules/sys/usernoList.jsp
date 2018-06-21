<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>

	<script type="text/javascript" src="${ctxStatic}/layui/layer.js"></script>

	<title>登录信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出员工数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/user/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/listno");
			$("#searchForm").submit();
	    	return false;
	    }
		
		function formno(id){
			window.location.href='${ctx}/sys/user/formno?id='+id;
	    }
		function resetPWD(id){
			return confirmInfo('确认要重置密码吗？','${ctx}/sys/user/resetPWD?id='+id);
	    }
		
		function prohibit(confirmxInfo,loginFlag,id){
			return confirmInfo(confirmxInfo,'${ctx}/sys/user/prohibit?loginFlag='+loginFlag+'&id='+id);
	    }
		function deleteno(id){
			return confirmInfo('确认要删除该账号吗？','${ctx}/sys/user/deleteno?id='+id);
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
 	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file"  accept=".txt" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/listno">账号列表</a></li>
		<shiro:hasPermission name="sys:user:edit"><li><a href="${ctx}/sys/user/formno">账号添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/listno" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<%-- <li><label>归属公司：</label><sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" 
				title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li> --%>
			<li><label>登录名：</label><form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<%-- <li><label>归属部门：</label><sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" 
				title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/></li> --%>
			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>邮&nbsp;&nbsp;&nbsp;箱：</label><form:input path="email" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
				<!-- <input id="btnExport" class="btn btn-primary" type="button" value="导出"/> 
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>-->
			<li class="clearfix"></li>
			
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>归属公司</th><!-- <th>归属部门</th> --><th >登录名</th><th >姓名</th><th >员工编号</th><th>邮箱</th><th>手机</th><th>是否允许登录</th><%--<th>角色</th> --%><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td>${user.company.name}</td>
			<%-- 	<td>${user.office.name}</td> --%>
				<td><a href="javascript:void(0)" onclick="formno('${user.id}')">${user.loginName}</a></td>
				<td>${user.name}</td>
				<td>${user.no}</td>
				<td>${user.email}</td>
				<td>${user.mobile}</td>
				<c:set var="loginFlag" value="${user.loginFlag}"/>
				<td>${fns:getDictLabel(loginFlag, 'yes_no', '')}</td><%--
				<td>${user.roleNames}</td> --%>
				<shiro:hasPermission name="sys:user:edit"><td>
    				<a href="javascript:void(0)" onclick="formno('${user.id}')">修改</a>
    				<a href="javascript:void(0)" onclick="resetPWD('${user.id}')">密码重置</a>
    				<c:if test="${user.loginFlag eq '1'}">
    					<a href="javascript:void(0)" onclick="prohibit('确认要冻结该账号吗？',0,'${user.id}')">冻结</a>    					
					</c:if>
					<c:if test="${user.loginFlag eq '0'}">
						<a href="javascript:void(0)" onclick="prohibit('确认要解冻该账号吗？',1,'${user.id}')">解冻</a> 
					</c:if>
					<a href="javascript:void(0)" onclick="deleteno('${user.id}')">删除</a>
					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>