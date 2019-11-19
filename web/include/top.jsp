<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
	$(function () {
		$("a#cart").click(function () {
			<c:if test="${!empty user}">
				return true;
			</c:if>
			<c:if test="${empty user}">
				$("div#loginModal").modal('show');
				return false;
			</c:if>
		})
	})
</script>

<link href="css/fore/top.css" rel="stylesheet">

<nav class="top">
	<a href="${contextPath}">
		<span style="margin: 0;" class="glyphicon glyphicon-home redColor"></span>
		天猫首页
	</a>
	
	<span>喵，欢迎来天猫</span>
	
	<c:if test="${!empty user}">
		<a href="login.jsp">${user.name}</a>
		<a href="forelogout">退出</a>
	</c:if>
	
	<c:if test="${empty user}">
		<a href="login.jsp">请登录</a>
		<a href="register.jsp">免费注册</a>
	</c:if>
	
	<span class="pull-right">
		<a href="forebought">我的订单</a>
		<a href="forecart" id="cart">
			<span style="margin: 0;" class="glyphicon glyphicon-shopping-cart redColor"></span>
			购物车<strong><span id="cartProductNumber" style="margin: 0;">${cartTotalItemNumber}</span></strong>件
		</a>
	</span>
</nav>
