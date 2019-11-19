<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link href="css/fore/categoryMenu.css" rel="stylesheet">

<div class="categoryMenu">
	<c:forEach items="${cs}" var="c">
		<div class="eachCategory" cid="${c.id}">
			<span class="glyphicon glyphicon-link"></span>
			<a href="forecategory?cid=${c.id}">${c.name}</a>
		</div>
	</c:forEach>
</div>
