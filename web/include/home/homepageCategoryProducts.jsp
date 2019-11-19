<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<link href="css/fore/homepageCategoryProducts.css" rel="stylesheet">

<c:if test="${empty param.categoryCount}">
	<c:set var="categoryCount" value="100" scope="page" />
</c:if>

<c:if test="${!empty param.categoryCount}">
	<c:set var="categoryCount" value="${param.categoryCount}" scope="page" />
</c:if>

<div class="homepageCategoryProducts">
	<c:forEach items="${cs}" var="c" varStatus="st">
		<c:if test="${st.count <= categoryCount}">
			<div class="eachHomepageCategoryProducts">
				<div class="left-mark"></div>
				<span class="categoryTitle">${c.name}</span><br>
				<c:forEach items="${c.products}" var="p" varStatus="vs">
					<c:if test="${vs.count <= 5}">
						<div class="productItem">
							<a href="foreproduct?pid=${p.id}">
			        			<img src="img/productSingle_Middle/${p.firstProductImage.id}.jpg">
			    			</a>
			    			<a href="foreproduct?pid=${p.id}" class="productItemDescLink">
			        			<span class="productItemDesc">
			        				[热销]${fn:substring(p.name, 0, 20)}
			        			</span>
			    			</a>
			    			<span class="productPrice">
			    				<fmt:formatNumber value="${p.promotePrice}" minFractionDigits="2" />
			    			</span>
						</div>
					</c:if>
				</c:forEach>
				<div style="clear: both"></div>
			</div>
		</c:if>
	</c:forEach>
	<img class="endpng" id="endpng" src="img/site/end.png">
</div>
