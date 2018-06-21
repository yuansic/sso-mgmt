<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>登录信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#no").focus();
			$("#btnSubmit").click(function(){
				 var beginDate=$("#effectiveDate").val();  
				 var endDate=$("#expiryDate").val();  
				 var d1 = new Date(beginDate.replace(/\-/g, "\/"));  
				 var d2 = new Date(endDate.replace(/\-/g, "\/"));  
				 if(d1>=d2){
					 alert("失效时间要大于生效时间");
					 return false;
				 }
				 
			});
			$("#inputForm").validate({
				rules: {
					loginName: {
						maxlength: 20,
						remote:{
							type:"POST",	
							url:"${ctx}/sys/user/checkLoginName",
							data:{
								oldLoginName:function(){return  '${user.loginName}';}
							}
						}
					},
					newPassword: {minlength:6,maxlength: 50},
					confirmNewPassword: {minlength:6,maxlength: 50},
					email: {
						maxlength: 50,
						remote:{
							type:"POST",	
							url:"${ctx}/sys/user/checkEmail",
							data:{
								oldLoginName:function(){return  '${user.email}';}
							}
						}
					}
				},
				messages: {
					id: {required: "请选择员工"},
					loginName: {
						required: "请输入登录名", 
						maxlength: "登录名不能超过20个字符", 
						remote: "用户登录名已存在"},
					newPassword: {
						required: "请输入密码", 
						maxlength: "密码长度须在6个至50个字符之间"},
					email: {
						required: "请输入邮箱", 
						maxlength: "邮箱不能超过50个字符", 
						remote: "邮箱已存在"},
					confirmNewPassword: {
						required: "请再次输入密码", 
						maxlength: "密码须在6个至50个字符之间",
						equalTo: "输入与上面相同的密码"},
					tenantId: {required: "请选择所属平台"},
					roleIdList: {required: "请选择账号角色"}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/listno">账号列表</a></li>
		<li class="active"><a href="${ctx}/sys/user/formno?id=${user.id}">账号<shiro:hasPermission name="sys:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:user:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/saveno" method="post" class="form-horizontal">
		
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">选择员工:</label>
			<div class="controls">
				<form:select path="id" class="required input-xlarge">
					<form:option value="" label="${productName}"/>
					<form:options items="${userList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登录名:</label>
			<div class="controls">
				<c:if test="${empty user.id}">
					<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
					<form:input path="loginName" htmlEscape="false"  maxlength="21" class="required username"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</c:if>
				<c:if test="${not empty user.id}">
					<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
					<form:input path="loginName" htmlEscape="false" value="${user.loginName}"  maxlength="21" readonly="true"/>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" onpaste="return false"  onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" value="" maxlength="51" minlength="6" class="${empty user.id?'required':''} "/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
				<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" onpaste="return false" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" type="password" class="${empty user.id?'required':''}  value="" maxlength="51" minlength="6" equalTo="#newPassword" />
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="51" minlength="5" class="required email"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否允许登录:</label>
			<div class="controls">
				<form:select path="loginFlag">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属业务平台:</label>
			<div class="controls">
				<form:radiobuttons path="tenantId" items="${gnTenantList}" itemLabel="tenantName" itemValue="tenantId" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		
		<div class="control-group">
			<label class="control-label">账号角色:</label>
			<div class="controls">
				<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生效时间:</label>
			<div class="controls">
				<label class="lbl"><input id="effectiveDate" name="effectiveDate" type="text" readonly="readonly" maxlength="30" class="input Wdate" value="<fmt:formatDate value="${user.effectiveDate}" type="both"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">失效时间:</label>
			<div class="controls">
				<label class="lbl"><input id="expiryDate" name="expiryDate"  type="text" readonly="readonly" maxlength="30" class="input Wdate" value="<fmt:formatDate value="${user.expiryDate}" type="both"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/></label>
			</div>
		</div>
		<div class="form-actions" style="margin-bottom:30px;">
			<shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="javascript:window.location.href='${ctx}/sys/user/listno'"/>
		</div>
	</form:form>
</body>
</html>