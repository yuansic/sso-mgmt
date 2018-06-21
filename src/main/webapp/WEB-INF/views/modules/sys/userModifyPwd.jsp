<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改密码</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#oldPassword").focus();
			$("#inputForm").validate({
				rules: {
					newPassword: {minlength:6,maxlength: 50},
					confirmNewPassword: {minlength:6,maxlength: 50}
				},
				messages: {
					newPassword: {
						required: "请输入密码", 
						minlength: "密码长度须在6个至50个字符之间",
						maxlength: "密码长度须在6个至50个字符之间"},
					confirmNewPassword: {required: "请再次输入密码", minlength: "密码长度须在6个至50个字符之间",
						maxlength: "密码须在6个至50个字符之间",equalTo: "输入与上面相同的密码"},
						oldPassword:{required: "请输入旧密码", minlength: "密码长度须在6个至50个字符之间",
							maxlength: "密码须在6个至50个字符之间"}
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
		<%-- <li><a href="${ctx}/sys/user/info">个人信息</a></li> --%>
		<li class="active"><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/modifyPwd" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">旧密码:</label>
			<div class="controls">
				<input id="oldPassword" name="oldPassword" type="password" onpaste="return false"  onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"  value="" maxlength="50" minlength="6" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" onpaste="return false"  onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" value=""  class="required"/>
				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认新密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" onpaste="return false" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" type="password" value="" equalTo="#newPassword" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form:form>
</body>
</html>