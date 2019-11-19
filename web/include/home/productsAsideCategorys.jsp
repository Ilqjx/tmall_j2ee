<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script>
	function showProductsAsideCategory(cid) {
	    $("div.eachCategory[cid = "+cid+"]").css("background-color", "#fff");
	    $("div.eachCategory[cid = "+cid+"] a").css("color", "#87cefa");
	    $("div.productsAsideCategorys[cid = "+cid+"]").show();
	}
	
	function hideProductsAsideCategory(cid) {
	    $("div.eachCategory[cid = "+cid+"]").css("background-color", "#e2e2e3");
	    $("div.eachCategory[cid = "+cid+"] a").css("color", "#000");
	    $("div.productsAsideCategorys[cid = "+cid+"]").hide();
	}
	
	$(function() {
	    $("div.eachCategory").mouseenter(function() {
	        var cid = $(this).attr("cid");
	        showProductsAsideCategory(cid);
	    });
	    $("div.eachCategory").mouseleave(function() {
	        var cid = $(this).attr("cid");
	        hideProductsAsideCategory(cid);
	    });
	    $("div.productsAsideCategorys").mouseenter(function() {
	        var cid = $(this).attr("cid");
	        showProductsAsideCategory(cid);
	    });
	    $("div.productsAsideCategorys").mouseleave(function() {
	        var cid = $(this).attr("cid");
	        hideProductsAsideCategory(cid);
	    });
	    
	    $("div.productsAsideCategorys div.row a").each(function () {
	    	// Math.round() 四舍五入
	    	// 1/5 概率变色
	    	var randomDigit = Math.round(Math.random() * 4);
	    	if (randomDigit == 2) {
	    		$(this).css("color", "#87CEFA");
	    	}
	    })
	});
</script>

<link href="css/fore/productsAsideCategorys.css" rel="stylesheet">

<c:forEach items="${cs}" var="c">
	<div class="productsAsideCategorys" cid="${c.id}">
		<c:forEach items="${c.productByRow}" var="ps">
			<div class="row">
				<c:forEach items="${ps}" var="p">
					<c:if test="${!empty p.subTitle}">
						<a href="foreproduct?pid=${p.id}">
							<c:forEach items="${fn:split(p.subTitle, ' ')}" var="title" varStatus="st">
								<c:if test="${st.index == 0}">
									${title}
								</c:if>
							</c:forEach>
						</a>
					</c:if>
				</c:forEach>
				<div class="seperator"></div>
			</div>
		</c:forEach>
	</div>
</c:forEach>
