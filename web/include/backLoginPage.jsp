<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<title>管理员登录页面</title>

<script src="js/jquery/2.0.0/jquery.min.js"></script>
<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
<link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">

<script>
	$(function () {
		<c:if test="${!empty errorMessage}">
			$("span#errorMessage").html("${errorMessage}");
			$("div.loginErrorMsgDiv").show();
			$("div.loginSmallDiv").css("height", "290px");
		</c:if>
		
		$("form#loginForm").submit(function () {
			if ($("input#name").val().length == 0) {
				$("span#errorMessage").html("账号不能为空");
				$("div.loginSmallDiv").css("height", "290px");
				$("div.loginErrorMsgDiv").show();
				$("input#name").focus();
				return false;
			}
			if ($("input#password").val().length == 0) {
				$("span#errorMessage").html("密码不能为空");
				$("div.loginErrorMsgDiv").show();
				$("div.loginSmallDiv").css("height", "290px");
				$("input#password").focus();
				return false;
			}
			return true;
		})
		
		$("form#loginForm input").keyup(function () {
			$("div.loginErrorMsgDiv").hide();
			$("div.loginSmallDiv").css("height", "250px");
		})
	})
</script>

<link href="css/back/backLoginPage.css" rel="stylesheet">

<div id="loginDiv">
	<form action="admin_login_login" method="post" id="loginForm">
		<div class="loginSmallDiv" id="loginSmallDiv">
			<div class="loginErrorMsgDiv">
	    		<div class="alert alert-danger" role="alert">
	    			<span id="errorMessage"></span>
	    		</div>
	    	</div>
	        <div class="loginInput">
	            <span class="loginInputIcon">
	                <span class="glyphicon glyphicon-user"></span>
	            </span>
	            <input type="text" placeholder="账号" name="name" id="name">
	        </div>
	        <div class="loginInput">
	            <span class="loginInputIcon">
	                <span class="glyphicon glyphicon-lock"></span>
	            </span>
	            <input type="password" placeholder="密码" name="password" id="password">
	        </div>
	        <div style="margin-top: 20px">
				<button type="submit" class="btn btn-block redButton">登录</button>
			</div>
		</div>
	</form>
</div>
