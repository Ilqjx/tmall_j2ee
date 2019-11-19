<!DOCTYPE html>

<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

<head>
	<script src="js/jquery/2.0.0/jquery.min.js"></script>
	<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
	<link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
	
	<script>
		function checkEmpty(id, name) {
			var value = $("#" + id).val();
			if (value.length == 0) {
				$("#" + id).focus();
				return false;
			}
			return true;
		}
		
		$(function () {
			$("div#footer a[href=#nowhere]").click(function () {
				alert("模仿天猫链接 并没有实际跳转");
			})
			
			$("a.wangwanglink").click(function () {
				alert("模仿旺旺图标 并不会打开旺旺");
			})
			
			$("a.notImplementLink").click(function () {
				alert("不提供此功能");
			})
		})
	</script>
</head>

<body>

</body>

</html>
