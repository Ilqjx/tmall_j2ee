<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>结算页面</title>

<script>
	$(function () {
	    $("img.leaveMessageImg").on("click", function () {
	        $("span.leaveMessageTextareaSpan").show();
	        $(this).hide();
	        $("div.orderItemSumDiv").css("height", "100px");
	    })
	    
	    // 提交信息非空判断
	    $("form#infoForm").submit(function () {
	    	if ($("textarea#address").val().length == 0) {
	    		alert("详细地址不能为空");
	    		$("textarea#address").focus();
	    		return false;
	    	}
	    	if ($("input#receiver").val().length == 0) {
	    		alert("收货人姓名不能为空");
	    		$("input#receiver").focus();
	    		return false;
	    	}
	    	var mobile = $("input#mobile").val();
	    	if (mobile.length == 0) {
	    		alert("手机号码不能为空");
	    		$("input#mobile").focus();
	    		return false;
	    	}
	    	if (isNaN(mobile) || mobile.length != 11) {
	    		alert("手机号码不符合要求 请重新输入");
	    		$("input#mobile").focus();
	    		return false;
	    	}
	    	return true;
	    })
	})
</script>

<link href="css/fore/buyPage.css" rel="stylesheet">

<div class="buyPageDiv">
	<form action="forecreateOrder" method="post" id="infoForm">
	    <div class="buyFlow">
	    	<a href="${contextPath}">
	    		<img class="pull-left" src="img/site/simpleLogo.png">
	    	</a>
	        <img class="pull-right" src="img/site/buyflow.png">
	        <div style="clear: both"></div>
	    </div>
	    <div class="address">
	        <div class="addressTip">输入收货地址</div>
	        <div>
	            <table class="addressTable">
	                <tbody>
	                    <tr>
	                        <td class="firstColumn">详细地址<span class="redStar">*</span></td>
	                        <td>
	                            <textarea id="address" name="address" placeholder="建议您如实填写详细收货地址，例如街道名称，门牌号码，楼层和房间号等信息"></textarea>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td>邮政编码</td>
	                        <td><input type="text" name="post" placeholder="如果您不清楚邮递区号，请填写000000"></td>
	                    </tr>
	                    <tr>
	                        <td>收货人姓名<span class="redStar">*</span></td>
	                        <td><input id="receiver" type="text" name="receiver" placeholder="长度不超过25个字符"></td>
	                    </tr>
	                    <tr>
	                        <td>手机号码<span class="redStar">*</span></td>
	                        <td><input id="mobile" type="text" name="mobile" placeholder="请输入11位手机号码"></td>
	                    </tr>
	                </tbody>
	            </table>
	        </div>
	    </div>
	    <div class="productList">
	        <div class="productListTip">确认订单信息</div>
	        <table class="productListTable">
	            <thead>
	                <tr>
	                    <th class="productListTableFirstColumn" colspan="2">
	                        <img class="tmallbuy" src="img/site/tmallbuy.png">
	                        <a class="marketLink" href="#nowhere">店铺：天猫店铺</a>
	                        <a href="#nowhere" class="wangwanglink"><span class="wangwangGif"></span></a>
	                    </th>
	                    <th>单价</th>
	                    <th>数量</th>
	                    <th>小计</th>
	                    <th>配送方式</th>
	                </tr>
	                <tr class="rowborder">
	                    <td colspan="2"></td>
	                    <td></td>
	                    <td></td>
	                    <td></td>
	                    <td></td>
	                </tr>
	            </thead>
	            <tbody class="productListTableTbody">
	            	<c:forEach items="${ois}" var="orderItem" varStatus="st">
	            		<tr class="orderItemTR">
		                    <td class="orderItemFirstTD">
		                        <img class="orderItemImg" src="img/productSingle_Small/${orderItem.product.firstProductImage.id}.jpg">
		                    </td>
		                    <td class="orderItemProductInfo">
		                        <a class="orderItemProductLink" href="foreproduct?pid=${orderItem.product.id}">
		                            ${orderItem.product.name}
		                        </a>
		                        <img title="支持信用卡支付" src="img/site/creditcard.png">
		                        <img title="消费者保障服务,承诺7天退货" src="img/site/7day.png">
		                        <img title="消费者保障服务,承诺如实描述" src="img/site/promise.png">
		                    </td>
		                    <td>
		                        <span class="orderItemProductPrice">
									￥<fmt:formatNumber value="${orderItem.product.promotePrice}" minFractionDigits="2" />
		                        </span>
		                    </td>
		                    <td>
		                        <span class="orderItemProductNumber">${orderItem.number}</span>
		                    </td>
		                    <td>
		                        <span class="orderItemUnitSum">
		                        	￥<fmt:formatNumber value="${orderItem.product.promotePrice * orderItem.number}" minFractionDigits="2" />
		                        </span>
		                    </td>
		                    <c:if test="${st.count == 1}">
		                    	<td class="orderItemLastTD" rowspan="${fn:length(ois)}">
									<!-- label 无任何特殊效果  当用户选择该标签时，浏览器就会自动将焦点转到和标签相关的表单控件上 -->
			                        <label class="orderItemDeliveryLabel">
			                            <input type="radio" checked="checked"> 普通配送
			                        </label>
			                        <select class="orderItemDeliverySelect">
			                            <option>快递 免邮费</option>
			                        </select>
			                    </td>
		                    </c:if>
		                </tr>
	            	</c:forEach>
	            </tbody>
	        </table>
	        <div class="orderItemSumDiv">
	            <div class="pull-left">
	                <span class="leaveMessageText">给卖家留言:</span>
	                <span>
	                    <img class="leaveMessageImg" src="img/site/leaveMessage.png">
	                </span>
	                <span class="leaveMessageTextareaSpan" style="display: none;">
	                    <textarea class="leaveMessageTextarea" name="userMessage"></textarea>
	                    <div>
	                        <span>还可以输入200个字符</span>
	                    </div>
	                </span>
	            </div>
	            <span class="pull-right">
	            	店铺合计(含运费): ￥<fmt:formatNumber value="${total}" minFractionDigits="2" maxFractionDigits="2" />
	            </span>
	            <div style="clear: both"></div>
	        </div>
	        <div class="orderItemTotalSumDiv">
	            <div class="pull-right">
	                <span>实付款：</span>
	                <span class="orderItemTotalSumSpan">
	                	￥<fmt:formatNumber value="${total}" minFractionDigits="2" maxFractionDigits="2" />
	                </span>
	            </div>
	        </div>
	        <div style="clear: both"></div>
	        <div class="submitOrderDiv">
	            <button class="submitOrderButton" type="submit">提交订单</button>
	        </div>
	        <div style="clear: both"></div>
	    </div>
    </form>
</div>
