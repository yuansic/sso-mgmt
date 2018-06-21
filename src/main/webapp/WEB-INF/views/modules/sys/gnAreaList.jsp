<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<script type="text/javascript" src="${ctxStatic}/layui/layer.js"></script>
	<title>区域信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function updateArea(id){
			window.location.href='${ctx}/sys/gnArea/form?id='+id;
	    }
		function deleteArea(id){
			return confirmInfo('确认要删除该区域信息吗？','${ctx}/sys/gnArea/delete?areaCode='+id);
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
		<li class="active"><a href="${ctx}/sys/gnArea/">区域信息列表</a></li>
		<shiro:hasPermission name="sys:gnArea:edit"><li><a href="${ctx}/sys/gnArea/form">区域信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gnArea" action="${ctx}/sys/gnArea/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>区域编码：</label>
				<form:input path="areaCode" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>区域名称：</label>
				<form:input path="areaName" htmlEscape="false" maxlength="500" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="state" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('gn_area_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>区域编码</th>
				<th>区域名称</th>
				<th>所属区域</th>
				<th>状态</th>
				<shiro:hasPermission name="sys:gnArea:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gnArea">
			<tr>
				<td><a href="javascript:void(0)" onclick="updateArea('${gnArea.id}')">
					${gnArea.areaCode}
				</a></td>
				<td>
					${gnArea.areaName}
				</td>
				<td>
				<c:set var="parentAreaCode" value="${gnArea.parentAreaCode}"/>
				
				${fns:getAreaName(parentAreaCode)}
				<c:set var="parentAreaCode" value=""/>
				</td>
				<td>
					${fns:getDictLabel(gnArea.state, 'gn_area_state', '')}
				</td>
				<shiro:hasPermission name="sys:gnArea:edit"><td>
				<c:if test="${gnArea.areaLevel ne '0'}">
					<a href="javascript:void(0)" onclick="updateArea('${gnArea.id}')">修改</a>
    				<a href="javascript:void(0)" onclick="deleteArea('${gnArea.areaCode}')">删除</a>
				</c:if>		
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>