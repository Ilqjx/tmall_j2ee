<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>模仿天猫官网-${category.name}</title>

<script>
	$(function () {
	    $("input.sortBarPrice").keyup(function () {
	        var num = $(this).val();
	        if (num.length == 0) {
	            $("div.productUnit").show();
	            return;
	        }
	        num = parseInt(num); // 解析一个字符串，并返回一个整数 只取前面的数字
	        if (isNaN(num)) {
	            num = 1;
	        }
	        if (num <= 0) {
	            num = 1;
	        }
	        $(this).val(num); // this.val() = num
	        var beginPrice = $("input.beginPrice").val();
	        var endPrice = $("input.endPrice").val();
	        if (!isNaN(beginPrice) && !isNaN(endPrice)) { // isNaN 判断是否是数字
	            $("div.productUnit").hide();
	            $("div.productUnit").each(function () {
	                var price = $(this).attr("price");
	                // price = new Number(price);
	                price = parseFloat(price);
	                // 纯数字与数字型字符串之间比较把字符串转换为数字
	                if (price >= beginPrice && price <= endPrice) {
	                    $(this).show();
	                }
	            });
	        }
	    });
	});
</script>

<link href="css/fore/categoryPage.css" rel="stylesheet">

<div class="categoryPageDiv">
    <img src="img/category/${category.id}.jpg">
    <div class="categorySortBar">
        <table class="categorySortBarTable categorySortTable">
            <tbody>
                <tr>
                    <td <c:if test="${param.sort=='all' || empty param.sort}">class="grayColumn"</c:if> >
                    	<a href="forecategory?cid=${category.id}&sort=all">综合<span class="glyphicon glyphicon-arrow-down"></span></a>
                    </td>
                    <td <c:if test="${param.sort=='review'}">class="grayColumn"</c:if> >
                    	<a href="forecategory?cid=${category.id}&sort=review">人气<span class="glyphicon glyphicon-arrow-down"></span></a>
                    </td>
                    <td <c:if test="${param.sort=='date'}">class="grayColumn"</c:if> >
                    	<a href="forecategory?cid=${category.id}&sort=date">新品<span class="glyphicon glyphicon-arrow-down"></span></a>
                    </td>
                    <td <c:if test="${param.sort=='saleCount'}">class="grayColumn"</c:if> >
                    	<a href="forecategory?cid=${category.id}&sort=saleCount">销量<span class="glyphicon glyphicon-arrow-down"></span></a>
                    </td>
                    <td <c:if test="${param.sort=='price'}">class="grayColumn"</c:if> >
                    	<a href="forecategory?cid=${category.id}&sort=price">价格<span class="glyphicon glyphicon-resize-vertical"></span></a>
                    </td>
                </tr>
            </tbody>
        </table>
        <table class="categorySortBarTable">
            <tbody>
                <tr>
                    <td><input class="sortBarPrice beginPrice" type="text" placeholder="请输入"></td>
                    <td class="grayColumn priceMiddleColumn">-</td>
                    <td><input class="sortBarPrice endPrice" type="text" placeholder="请输入"></td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="categoryProducts">
    	<c:forEach items="${category.products}" var="p">
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
</div>
