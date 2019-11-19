<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
	$(function () {
		$("div.rightMenu span").mouseenter(function () {
		    // offset返回或设置匹配元素相对于文档的偏移
		    var left = $(this).offset().left;
		    var top = $(this).offset().top;
		    var width = $(this).css("width");
		    var destLeft = parseInt(left) + parseInt(width)/2;
		    $("img#catear").css("left", destLeft-14.5);
		    $("img#catear").css("top", top-20);
		    $("img#catear").fadeIn(500);
		});
		
		$("div.rightMenu span").mouseleave(function () {
		    $("img#catear").hide();
		});
		
		// 设置分类轮播...的位置
		var left = $("div#carousel-of-product").offset().left;
		$("div.categoryMenu").css("left", left - 20);
		$("div.categoryWithCarousel div.headbar div.head").css("margin-left", left);
		$("div.productsAsideCategorys").css("left", left - 20);
	})
</script>

<link href="css/fore/categoryAndcarousel.css" rel="stylesheet">

<img class="catear" id="catear" src="img/site/catear.png">

<div class="categoryWithCarousel">
	<div class="headbar">
		<div class="head">
		    <span class="glyphicon glyphicon-th-list"></span>
		    <span>商品分类</span>
		</div>
		<div class="rightMenu">
		    <span>
		        <a href="#nowhere"><img src="img/site/chaoshi.png"></a>
		    </span>
		    <span>
		        <a href="#nowhere"><img src="img/site/guoji.png"></a>
		    </span>
		    <c:forEach items="${cs}" var="c" varStatus="st">
		    	<c:if test="${st.count>=1 and st.count<=4}">
		    		<span>
		    			<a href="forecategory?cid=${c.id}">${c.name}</a>
		    		</span>
		    	</c:if>
		    </c:forEach>
		</div>
	</div>
	
	<div style="position: relative;">
		<%@ include file="categoryMenu.jsp" %>
	</div>
	
	<div style="position: relative;">
		<%@ include file="productsAsideCategorys.jsp" %>
	</div>

	<%@ include file="carousel.jsp" %>
	
	<div class="carouselBackgroundDiv"></div>	
	
</div>
