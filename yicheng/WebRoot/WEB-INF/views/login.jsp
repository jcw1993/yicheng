<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />

<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min.css" />" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap-theme.min.css" />" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/main.css" />" />

<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.11.2.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.min.js" />"></script>


<title>怡诚-用户登录</title>
</head>
<body>
	<div class="container center">
		<div class="row">
		</div>
		<div class="row col-xs-4"></div>
		<div class="row col-xs-5">
			<form id="loginForm" action="LoginPost" method="post">
				<div class="input-group input-item">
					<h3>怡诚-用户登录</h3>
				</div>
				<div class="input-group input-item">
					<span class="input-group-addon">用户名</span> <input type="text"
						class="form-control" id="name" name="name" placeholder="用户名"
						aria-describedby="basic-addon2" />
				</div>
				<div class="input-group input-item">
					<span class="input-group-addon">密码&nbsp;&nbsp;&nbsp;</span> <input type="password"
						class="form-control" id="password" name="password"
						placeholder="密码" aria-describedby="basic-addon2" />
				</div>
				<div class="input-group input-item">
					<label for="userType">选择身份:</label>
					<select name="userType">
						<option value="0" selected="selected">打样开发员</option>
						<option value="1">报价员</option>
						<option value="2">采购员</option>
						<option value="3">总经理</option>
					</select>
				</div>
				<div class="input-item">
					<div class="row">
						<a id="login" class="btn btn-lg login btn-success" href="#"> 登
						录 </a>
					</div>
				</div>
				<div class="input-item">
					<p id="error-info" class="error-info"></p>
				</div>
			</form>
		</div>
	</div>

	<jsp:include page="footer.jsp" flush="true" />

	<script type="text/javascript">

	var USER_NAME_NOT_EXIST = 0x00000040;
	var PASSWORD_ERROR = 0x00000041;

	var $nameInput;
	var $passwordInput;
	var $loginBtn;
	var $loginForm;
	var $errorInfo;

	$(function() {
		$nameInput = $("#name");
		$passwordInput = $("#password");
		$loginLink = $("#login");
		$loginForm = $("#loginForm");
		$errorInfo = $("#error-info");
		
		$loginLink.click(function(e) {
			var name = $nameInput.val();
			var password = $passwordInput.val();
			var userType = $("select[name='userType'] option:selected").val();
			if(checkParameters(name, password, userType)) {
				name = name.trim();
				password = password.trim();
				$.ajax({
					url: "LoginPost",
					method: "post",
					data: {
						name: name,
						password: password,
						userType: userType
					},
					success: function(result) {
						if(result.resultCode == 0) {
							var redirectUrl = result.data;
							window.location = redirectUrl;
						}else if(result.resultCode == USER_NAME_NOT_EXIST) {
							$errorInfo.text("用户名不存在");
							clearInput();
						}else if(result.resultCode == PASSWORD_ERROR) {
							$errorInfo.text("密码错误");
							clearInput();
						}
					}
				});
			}
		}); 

		$passwordInput.keyup(function(e){
			if(e.keyCode == 13) {
				$loginLink.trigger("click");
			}
		});


	});

	function checkParameters(name, password, userType) {
		if(name == undefined || name.trim() == "") {
			alert("用户名不能为空");
			return false;
		}
		if(password == undefined || password == "") {
			alert("密码不能为空!");
			return false;
		}
		if(userType == undefined || (userType != 0 && userType != 1 && userType != 2 && userType != 3)) {
			alert("请选择身份");
			return false;
		}
		return true;
	}

	function clearInput() {
		$nameInput.val("");
		$passwordInput.val("");
	}
</script>
</body>
</html>