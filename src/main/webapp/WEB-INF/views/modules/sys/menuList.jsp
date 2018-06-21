<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<script type="text/javascript" src="${ctxStatic}/layui/layer.js"></script>
	<title>菜单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	$(document).ready(function(){
		var a = $("#searchName").val();//取出值
		 $("#menuName").val(a);
	});
	
	function formMenu(id){
		window.location.href='${ctx}/sys/menu/form?id='+id;
    }
	function deleteMenu(id){
		return confirmInfo('要删除该菜单及所有子菜单项吗？','${ctx}/sys/menu/delete?id='+id);
    }
	function formParentMenu(id){
		window.location.href='${ctx}/sys/menu/form?parent.id='+id;
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
		<li class="active"><a href="${ctx}/sys/menu/page/">菜单列表</a></li>
		<shiro:hasPermission name="sys:menu:edit"><li><a href="${ctx}/sys/menu/form">菜单添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
      <!--查询条件-->
             <form:form id="searchForm" modelAttribute="menu" action="${ctx}/sys/menu/page/" method="post" class="breadcrumb form-search">
                 <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			     <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	               <input type="hidden" name="searchName" id="searchName" value="${requestScope.searchName}"/>
	               <ul class="ul-form">
					<li><label>名称：</label>
						<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium" id="menuName"/>
					</li>
					<li><label>资源类型：</label>
					<form:select path="resourceType" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('resource_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					</li>
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<li class="clearfix"></li>
				</ul>
	       </form:form>
	   	<!--查询结束-->      
              <table class="table table-striped table-bordered table-condensed">
                 <thead><tr><th>所属应用</th><th>名称</th><th>资源类型</th><th>链接</th><th>操作</th></tr></thead>
                  <tbody>
		              <c:forEach items="${page.list}" var="menu">
						<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
						   <td >${fns:getGnTabSystem(menu.gnTabSystem)}</td>
							<td nowrap><i class="icon-${not empty menu.icon?menu.icon:' hide'}"></i><a href="javascript:void(0)" onclick="formMenu('${menu.id}')">${menu.name}</a></td>
							<td>${fns:getDictLabel(menu.resourceType, 'resource_type', '')}</td>
							<td title="${menu.href}">${fns:abbr(menu.href,30)}</td>
							<!--<td style="text-align:center;">
							<shiro:hasPermission name="sys:menu:edit">
								<input type="hidden" name="ids" value="${menu.id}"/>
								<input name="sorts" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
							</shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit">
								${menu.sort}
							</shiro:lacksPermission>
						</td>-->
						<!--  <td>${menu.isShow eq '1'?'显示':'隐藏'}</td>
						<td title="${menu.permission}">${fns:abbr(menu.permission,30)}</td>-->
						<shiro:hasPermission name="sys:menu:edit"><td nowrap>
							<a href="javascript:void(0)" onclick="formMenu('${menu.id}')">修改</a>
							<a href="javascript:void(0)" onclick="deleteMenu('${menu.id}')">删除</a>
							<a href="javascript:void(0)" onclick="formParentMenu('${menu.id}')">添加下级菜单</a> 
						</td></shiro:hasPermission>
					</tr>
					</c:forEach>
					</tbody>
                  </table>
                 <!--分页-->
                <div class="pagination">${page}</div>
</body>
</html>