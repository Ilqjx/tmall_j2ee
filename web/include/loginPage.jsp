<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<title>登录页面</title>

<link href="css/fore/loginPage.css" rel="stylesheet">

<script>
	$(function () {
		<c:if test="${!empty msg}">
			$("span#errorMessage").html("${msg}");
			$("div.loginErrorMsgDiv").show();
		</c:if>
		
		$("form#loginForm").submit(function () {
			if ($("input#name").val().length == 0) {
				$("span#errorMessage").html("用户名不能为空");
				$("div.loginErrorMsgDiv").show();
				$("input#name").focus();
				return false;
			}
			if ($("input#password").val().length == 0) {
				$("span#errorMessage").html("密码不能为空");
				$("div.loginErrorMsgDiv").show();
				$("input#password").focus();
				return false;
			}
			return true;
		})
		
		$("form#loginForm input").keyup(function () {
			$("div.loginErrorMsgDiv").hide();
		})
		
// 		var left = window.innerWidth / 2;
// 		$("div.loginSmallDiv").css("left", left);
	})
</script>

<div id="loginDiv">
	<div class="simpleLogo">
		<a href="${contextPath}">
			<img src="img/site/simpleLogo.png">
		</a>
	</div>
	<div class="backgroundImgDiv">
		<form action="forelogin" method="post" id="loginForm">
			<div class="loginSmallDiv" id="loginSmallDiv">
		    	<div class="loginErrorMsgDiv">
		    		<div class="alert alert-danger" role="alert">
		    			<span id="errorMessage"></span>
		    		</div>
		    	</div>
		        <div class="login_acount_text">账户登录</div>
		        <div class="loginInput">
		            <span class="loginInputIcon">
		                <span class="glyphicon glyphicon-user"></span>
		            </span>
		            <input type="text" placeholder="手机/会员名/邮箱" name="name" id="name">
		        </div>
		        <div class="loginInput">
		            <span class="loginInputIcon">
		                <span class="glyphicon glyphicon-lock"></span>
		            </span>
		            <input type="password" placeholder="密码" name="password" id="password">
		        </div>
		        <div>
		            <a class="notImplementLink" href="#nowhere">忘记登录密码</a>
		            <a class="pull-right" href="register.jsp">免费注册</a>
		        </div>
		        <div style="margin-top: 20px">
		                <button type="submit" class="btn btn-block redButton">登录</button>
				</div>
			</div>
		</form>
	</div>
	<div style="height: 50px;"></div>	
</div>