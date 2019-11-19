<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>搜索结果页</title>

<link href="css/fore/searchResultPage.css" rel="stylesheet">

<div class="categoryPageDiv">
	<c:if test="${empty ps}">
		<div class="noSuchProduct">
			没有满足条件的商品
		</div>
	</c:if>
	<c:if test="${!empty ps}">
		<div class="categoryProducts">
			<c:forEach items="${ps}" var="p">
				<div class="productUnit" price="${p.promotePrice}">
					<div class="productUnitFrame">
						<a href="foreproduct?pid=${p.id}">
							<img class="productImage" src="img/productSingle_Middle/${p.firstProductImage.id}.jpg">
						</a>
						<span class="productPrice">
							¥<fmt:formatNumber value="${p.promotePrice}" minFractionDigits="2" />
						</span>
		            	<a href="foreproduct?pid=${p.id}" class="productLink">
		                	${fn:substring(p.name, 0, 50)}
		            	</a>
		            	<a href="foreproduct?pid=${p.id}" class="tmallLink">天猫专卖</a>
		            	<div class="show1 productInfo">
		                 <span class="monthDeal">月成交 <span class="productDealNumber">${p.saleCount}笔</span></span>
		                 <span class="productReview">评价<span class="productReviewNumber">${p.reviewCount}</span></span>
		                 <span class="wangwang">
		                     <a href="#nowhere" class="wangwanglink">
		                         <img src="img/site/wangwang.png">
		                     </a>
		                 </span>
		            	</div>
					</div>
				</div>
			</c:forEach>
			<div style="clear: both;"></div>
		</div>
	</c:if>
</div>
