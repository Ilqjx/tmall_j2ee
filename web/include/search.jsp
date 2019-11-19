<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link href="css/fore/search.css" rel="stylesheet">

<a href="${contextPath}">
	<img id="logo" src="img/site/logo.gif">
</a>

<form action="foresearch" method="post">
	<div class="searchDiv">
		<input type="text" name="keyword" placeholder="华为Mate30 苹果A11" value="${param.keyword}">
		<button type="submit" class="searchButton">搜索</button>
		<div class="searchBelow">
			<c:forEach items="${cs}" var="c" varStatus="st">
				<c:if test="${st.count>=5 and st.count<=8}">
					<span>
						<a href="forecategory?cid=${c.id}">${c.name}</a>
						<c:if test="${st.count!=8}">
							<span>|</span>
						</c:if>
					</span>
				</c:if>
			</c:forEach>
		</div>
	</div>
	<div style="clear: both;"></div>
</form>
