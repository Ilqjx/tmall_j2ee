<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>我的订单</title>

<script>
	$(function () {
		// 筛选不同的订单状态
	    $("a[orderstatus]").on("click", function () {
	        var orderstatus = $(this).attr("orderstatus");
	        if (orderstatus == "all") {
	            $("table[orderstatus]").show();
	        } else {
	            $("table[orderstatus]").hide();
	            $("table[orderstatus="+orderstatus+"]").show();
	        }
	        $("div.boughtDiv div.orderType div").removeClass("selectedOrderType");
	        $(this).parent("div").addClass("selectedOrderType");
	    })
	    
	    // 删除
	    var deleteOrderid = 0;
		var deleteOrder = false;
	    $("a.deleteOrderLink").click(function(){
	        deleteOrderid = $(this).attr("oid");
	        deleteOrder = false;
	        $("#deleteConfirmModal").modal("show");
	    });
		
	    $("button.deleteConfirmButton").click(function(){
	        deleteOrder = true;
	        $("#deleteConfirmModal").modal('hide');
	    });
	    
	    $("div#deleteConfirmModal").on("hidden.bs.modal", function (e) {
	        if (deleteOrder) {
	            var url = "foredeleteOrder";
	            $.post(
	                url,
	                {"oid": deleteOrderid},
	                function (res) {
	                    if ("success" == res) {
	                        $("table.orderListItemTable[oid="+deleteOrderid+"]").hide();
	                    } else {
	                        location.href = "login.jsp";
	                    }
	                }
	            );
	        }
	    })
	})
</script>

<link href="css/fore/boughtPage.css" rel="stylesheet">

<c:if test="${empty orders}">
	<div class="emptyOrder">
		您还没有订单~
	</div>
</c:if>

<c:if test="${!empty orders}">
	<div class="boughtDiv">
	    <!-- 标题部分 -->
	    <div class="orderType">
	        <div class="selectedOrderType"><a href="#nowhere" orderstatus="all">所有订单</a></div>
	        <div><a href="#nowhere" orderstatus="waitPay">待付款</a></div>
	        <div><a href="#nowhere" orderstatus="waitDelivery">待发货</a></div>
	        <div><a href="#nowhere" orderstatus="waitConfirm">待收货</a></div>
	        <div><a href="#nowhere" orderstatus="waitReview" class="noRightborder">待评价</a></div>
	        <div class="orderTypeLastOne"></div>
	    </div>
	    <div style="clear: both"></div>
	    <div class="orderListTitle">
	        <table class="orderListTitleTable">
	            <tbody>
	                <tr>
	                    <td>宝贝</td>
	                    <td width="100px">单价</td>
	                    <td width="100px">数量</td>
	                    <td width="120px">实付款</td>
	                    <td width="100px">交易操作</td>
	                </tr>
	            </tbody>
	        </table>
	    </div>
	    <!-- 产品列表 -->
	    <div class="orderListItem">
	    	<c:forEach items="${orders}" var="order">
	    		<table class="orderListItemTable" oid="${order.id}" orderstatus="${order.status}">
	    			<tbody>
	    				<tr class="orderListItemFirstTR">
		                    <td colspan="2">
		                        <b><fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></b>
		                        <span>订单号: ${order.orderCode}</span>
		                    </td>
		                    <td colspan="2">
		                        <img width="13px" src="img/site/orderItemTmall.png">天猫商场
		                    </td>
		                    <td>
		                        <a class="wangwanglink" href="#nowhere">
		                            <div class="orderItemWangWangGif"></div>
		                        </a>
		                    </td>
		                    <td class="orderItemDeleteTD">
		                        <a class="deleteOrderLink" href="#nowhere" oid="${order.id}">
		                            <span class="orderListItemDelete glyphicon glyphicon-trash"></span>
		                        </a>
		                    </td>
		                </tr>
		                <c:forEach items="${order.orderItems}" var="orderItem" varStatus="st">
		                	<tr class="orderItemProductInfoPartTR">
			                    <td class="orderItemProductInfoPartTD">
			                        <img width="80px" height="80px" src="img/productSingle_Middle/${orderItem.product.firstProductImage.id}.jpg">
			                    </td>
			                    <td class="orderItemProductInfoPartTD">
			                        <div class="orderListItemProductLinkOutDiv">
			                            <a href="foreproduct?pid=${orderItem.product.id}">${orderItem.product.name}</a>
			                            <div class="orderListItemProductLinkInnerDiv">
			                                <img title="支持信用卡支付" src="img/site/creditcard.png">
			                                <img title="消费者保障服务，承诺7天退货" src="img/site/7day.png">
			                                <img title="消费者保障服务，承诺如实描述" src="img/site/promise.png">
			                            </div>
			                        </div>
			                    </td>
			                    <td class="orderItemProductInfoPartTD" width="100px">
			                        <div class="orderListItemProductOriginalPrice">
										￥<fmt:formatNumber value="${orderItem.product.originalPrice}" minFractionDigits="2" />
			                        </div>
			                        <div class="orderListItemProductPrice">
			                        	￥<fmt:formatNumber value="${orderItem.product.promotePrice}" minFractionDigits="2" />
			                        </div>
			                    </td>
			                    <c:if test="${st.count == 1}">
			                    	<td rowspan="${fn:length(order.orderItems)}" width="100px" valign="top" class="orderListItemNumberTD orderItemOrderInfoPartTD">
				                        <span class="orderListItemNumber">${order.totalNumber}</span>
				                    </td>
				                    <td rowspan="${fn:length(order.orderItems)}" width="120px" valign="top" class="orderListItemProductRealPriceTD orderItemOrderInfoPartTD">
				                        <div class="orderListItemProductRealPrice">
				                        	￥<fmt:formatNumber value="${order.total}" minFractionDigits="2" maxFractionDigits="2" />
				                        </div>
				                        <div class="orderListItemPriceWithTransport">(含运费：￥0.00)</div>
				                    </td>
				                    <td rowspan="${fn:length(order.orderItems)}" width="100px" valign="top" class="orderListItemButtonTD orderItemOrderInfoPartTD">
				                    	<c:if test="${order.status == 'waitConfirm'}">
				                    		<a href="foreconfirmPay?oid=${order.id}">
				                    			<button class="orderListItemReview">确认收货</button>
				                    		</a>
				                    	</c:if>
				                    	<c:if test="${order.status == 'waitPay'}">
				                    		<a href="forealiPay?oid=${order.id}&total=${order.total}">
				                    			<button class="orderListItemReview">付款</button>
				                    		</a>
				                    	</c:if>
				                    	<c:if test="${order.status == 'waitDelivery'}">
				                    		<span>待发货</span>
				                    	</c:if>
				                    	<c:if test="${order.status == 'waitReview'}">
				                    		<a href="forereview?oid=${order.id}">
				                    			<button class="orderListItemReviewButton">评价</button>
				                    		</a>
				                    	</c:if>
				                    </td>
			                    </c:if>
			                </tr>
		                </c:forEach>
	    			</tbody>
	    		</table>
	    	</c:forEach>
	    </div>
	</div>
</c:if>
