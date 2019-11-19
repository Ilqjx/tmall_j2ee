<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<title>用户注册</title>

<script>
	$(function () {
		<c:if test="${!empty msg}">
			$("span.errorMessage").html("${msg}");
// 			$("div.registerErrorMessageDiv").css("visibility", "visible");
// 			$("div.registerErrorMessageDiv").css("display", "block");
			$("div.registerErrorMessageDiv").show();
		</c:if>
		
		$("form#registerForm").submit(function () {
			if ($("input#name").val().length == 0) {
				$("span.errorMessage").html("请输入用户名");
// 				$("div.registerErrorMessageDiv").css("visibility", "visible");
// 				$("div.registerErrorMessageDiv").css("display", "block");
				$("div.registerErrorMessageDiv").show();
				return false;
			}
			if ($("input#password").val().length == 0) {
				$("span.errorMessage").html("请输入密码");
// 				$("div.registerErrorMessageDiv").css("visibility", "visible");
// 				$("div.registerErrorMessageDiv").css("display", "block");
				$("div.registerErrorMessageDiv").show();
				return false;
			}
			if ($("input#repeatpassword").val().length == 0) {
				$("span.errorMessage").html("请输入重复密码");
// 				$("div.registerErrorMessageDiv").css("visibility", "visible");
// 				$("div.registerErrorMessageDiv").css("display", "block");
				$("div.registerErrorMessageDiv").show();
				return false;
			}
			if ($("input#password").val() != $("input#repeatpassword").val()) {
				$("span.errorMessage").html("重复密码不一致");
// 				$("div.registerErrorMessageDiv").css("visibility", "visible");
// 				$("div.registerErrorMessageDiv").css("display", "block");
				$("div.registerErrorMessageDiv").show();
				return false;
			}
			if ($("input#password").val().length < 6) {
				$("span.errorMessage").html("密码长度不能小于6");
				$("div.registerErrorMessageDiv").show();
				return false;
			}
			return true;
		})
		
		$("form#registerForm input").keyup(function () {
			$("div.registerErrorMessageDiv").hide();
		})
	})
</script>

<link href="css/fore/registerPage.css" rel="stylesheet">

<form action="foreregister" method="post" id="registerForm">
	<div class="registerDiv">
		<div class="registerErrorMessageDiv">
			<div class="alert alert-danger" role="alert">
<!-- 				<button type="button" class="close" data-dismiss="alert">&times;</button> -->
				<span class="errorMessage"></span>
			</div>
		</div>
		<table align="center" class="registerTable">
		    <tbody>
		        <tr>
		            <td class="registerTip registerTableLeftTD">设置会员名</td>
		            <td></td>
		        </tr>
		        <tr>
		            <td class="registerTableLeftTD">登陆名</td>
		            <td class="registerTableRightTD">
		                <input type="text" placeholder="会员名一旦设置成功，无法修改" name="name" id="name">
		            </td>
		        </tr>
		        <tr>
		            <td class="registerTip registerTableLeftTD">设置登陆密码</td>
		            <td class="registerTableRightTD">登陆时验证，保护账号信息</td>
		        </tr>
		        <tr>
		            <td class="registerTableLeftTD">登陆密码</td>
		            <td class="registerTableRightTD">
		                <input type="password" placeholder="设置你的登陆密码" name="password" id="password">
		            </td>
		        </tr>
		        <tr>
		            <td class="registerTableLeftTD">密码确认</td>
		            <td class="registerTableRightTD">
		                <input type="password" placeholder="请再次输入你的密码" name="repeatpassword" id="repeatpassword">
		            </td>
		        </tr>
		        <tr>
		            <td class="registerButtonTD" colspan="2">
<!-- 		                <a href="registerSuccess.jsp"> -->
		                	<button type="submit">提 交</button>
<!-- 		                </a> -->
		            </td>
		        </tr>
		    </tbody>
		</table>
		<div style="height: 50px;"></div>
	</div>
</form>
