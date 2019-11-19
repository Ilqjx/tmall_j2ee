<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<title>评价页面</title>

<link href="css/fore/reviewPage.css" rel="stylesheet">

<div class="reviewDiv">
	<div class="reviewProductInfoDiv">
        <div class="reviewProductInfoImg">
            <img src="img/productSingle/${product.firstProductImage.id}.jpg">
        </div>
        <div class="reviewProductInfoRightDiv">
            <div class="reviewProductInfoRightText">
                ${product.name}
            </div>
            <table class="reviewProductInfoTable">
                <tbody>
                    <tr>
                        <td width="75px">价格:</td>
                        <td>
                            <span class="reviewProductInfoTablePrice">￥<fmt:formatNumber value="${product.originalPrice}" minFractionDigits="2" maxFractionDigits="2" /></span> 元
                        </td>
                    </tr>
                    <tr>
                        <td>配送</td>
                        <td>快递: 0.00</td>
                    </tr>
                    <tr>
                        <td>月销量:</td>
                        <td>
                            <span class="reviewProductInfoTableSellNumber">${product.saleCount}</span> 件
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="reviewProductInfoRightBelowDiv">
                <span class="reviewProductInfoRightBelowImg"></span>
                <span class="reviewProductInfoRightBelowText">现在查看的是 您所购买商品的信息于<fmt:formatDate value="${order.payDate}" pattern="yyyy年MM月dd日" />下单购买了此商品</span>
            </div>
        </div>
        <div style="clear: both"></div>
    </div>
    <!-- commentPageDown -->
    <div class="reviewStasticsDiv">
        <div class="reviewStasticsLeft">
            <div class="reviewStasticsLeftTop"></div>
            <div class="reviewStasticsLeftContent">累计评价 <span class="reviewStasticsNumber">${product.reviewCount}</span></div>
            <div class="reviewStasticsLeftFoot"></div>
        </div>
        <div class="reviewStasticsRight">
            <div class="reviewStasticsRightEmpty"></div>
            <div class="reviewStasticsFoot"></div>
        </div>
    </div>
    <div style="clear: both"></div>
    <c:if test="${order.status == 'waitReview'}">
    	<div class="makeReviewDiv">
	        <form action="foredoreview" method="POST">
	            <div class="makeReviewText">其他买家，需要你的建议哦！</div>
	            <table class="makeReviewTable">
	                <tbody>
	                    <tr>
	                        <td class="makeReviewTableFirstTD">评价商品</td>
	                        <td><textarea name="content"></textarea></td>
	                    </tr>
	                </tbody>
	            </table>
	            <div class="makeReviewButtonDiv">
	                <input type="hidden" value="${order.id}" name="oid">
	                <input type="hidden" value="${product.id}" name="pid">
	                <button type="submit">提交评价</button>
	            </div>
	        </form>
	    </div>
    </c:if>
    <c:if test="${order.status == 'finish'}">
    	<div class="reviewContentPart">
		   	<c:forEach items="${reviews}" var="review">
		   		<div class="reviewItem">
		   			<div class="reviewDesc">
		   				<div class="reviewDate">
			   				<fmt:formatDate value="${review.createDate}" pattern="yyyy-MM-dd" />
			   			</div>
			   			<div class="reviewContent">${review.content}</div>
			   			<div class="reviewName">
			   				${review.user.anonymousName}<span class="userInfoGrayPart">（匿名）</span>
			   			</div>
		   			</div>
		           	<div style="clear: both"></div>
		   		</div>
		   	</c:forEach>
		</div>
    </c:if>
</div>
