<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>我的购物车</title>

<script>
	function formatMoney(num) {
	    num = num.toString().replace(/\$|\,/g, '');
	    if (isNaN(num)) {
	        num = "0";
	    }
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num * 100 + 0.50000000001);
	    cents = num % 100;
	    num = Math.floor(num / 100).toString();
	    if (cents < 10) {
	        cents = "0" + cents;
	    }
	    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++) {
	        num = num.substring(0, num.length - (4 * i + 3)) + ',' +
	            num.substring(num.length - (4 * i + 3));
	    }
	    return (((sign) ? '' : '-') + num + '.' + cents);
	}
	
	// 更改结算按钮状态
	function syncCreateOrderButton() {
	    var selectAny = false;
	    $("img.cartProductItemIfSelected").each(function () {
	        if ("selectit" == $(this).attr("selectit")) {
	            selectAny = true;
	        }
	    })
	    if (selectAny) {
	        // disabled 按钮不可点击
	        $("button.createOrderButton").css("background-color", "#C40000");
	        $("button.createOrderButton").removeAttr("disabled");
	    } else {
	        $("button.createOrderButton").css("background-color", "#AAA");
	        $("button.createOrderButton").attr("disabled", "disabled");
	    }
	}
	
	// 是否全选
	function syncSelect() {
	    var selectAll = true;
	    $("img.cartProductItemIfSelected").each(function () {
	        if ($(this).attr("selectit") == "false") {
	            selectAll = false;
	        }
	    })
	    if (selectAll) {
	        $("img.selectAllItem").attr("selectit", "selectit");
	        $("img.selectAllItem").attr("src", "img/site/cartSelected.png");
	    } else {
	        $("img.selectAllItem").attr("selectit", "false");
	        $("img.selectAllItem").attr("src", "img/site/cartNotSelected.png");
	    }
	}
	
	// 设置总金额和总数量
	function calcCartSumPriceAndNumber() {
	    var sum = 0;
	    var totalNumber = 0;
	    $("img.cartProductItemIfSelected[selectit='selectit']").each(function () {
	        var oiid = $(this).attr("oiid");
	        var price = $("span.cartProductItemSmallSumPrice[oiid="+oiid+"]").text();
	        price = price.replace(/,/g, "");
	        price = price.replace(/￥/g, "");
	        sum += new Number(price);
	        var num = $("input.orderItemNumberSetting[oiid="+oiid+"]").val();
	        totalNumber += new Number(num);
	    })
	    $("span.cartSumPrice").html("￥" + formatMoney(sum));
	    $("span.cartTitlePrice").html("￥" + formatMoney(sum));
	    $("span.cartSumNumber").html(totalNumber);
	}
	
	// 修改购物车的数量
	function cartNumber() {
		var totalNumber = 0;
		$("input.orderItemNumberSetting").each(function () {
			var hide = $(this).attr("hide");
			if (hide == "false") {
				var num = $(this).val();
				totalNumber += new Number(num);
			}
		})
		$("span#cartProductNumber").html(totalNumber);
	}
	
	// 用于对表单元素的处理
	function syncPrice(pid, num, price) {
	    $("input.orderItemNumberSetting[pid="+pid+"]").val(num);
	    var cartProductItemSmallSumPrice = formatMoney(num * price);
	    $("span.cartProductItemSmallSumPrice[pid="+pid+"]").html("￥" + cartProductItemSmallSumPrice);
	    calcCartSumPriceAndNumber();
	    cartNumber();
	    
	    // 更新数据库中订单项中产品的数量
	    var url = "forechangeOrderItem";
	    $.post(
	    	url,
	    	{"num" : num, "pid" : pid},
	    	function (res) {
	    		if (res == "fail") {
	    			location.href = "login.jsp";
	    		}
	    	}
	    )
	}
	
	var deleteOrderItemId = 0;
	var deleteOrderItem = false;
	$(function () {
		// 删除
		$("a.deleteOrderItem").click(function () {
			deleteOrderItem = false;
			var oiid = $(this).attr("oiid");
			deleteOrderItemId = oiid;
			$("div#deleteConfirmModal").modal('show');
		})
		
		// 确认删除
		$("button.deleteConfirmButton").click(function () {
			deleteOrderItem = true;
			$("div#deleteConfirmModal").modal('hide');
		})
		
		// 在数据库中进行删除
		$("div#deleteConfirmModal").on("hide.bs.modal", function () {
			if (deleteOrderItem) {
				var url = "foredeleteOrderItem";
				$.post(
					url,
					{"oiid" : deleteOrderItemId},
					function (res) {
						if (res == "success") {
							$("tr.cartProductItemTR[oiid="+ deleteOrderItemId +"]").hide();
							$("img.cartProductItemIfSelected[oiid="+ deleteOrderItemId +"]").attr("selectit", "false");
							$("input.orderItemNumberSetting[oiid="+ deleteOrderItemId +"]").attr("hide", "true");
							calcCartSumPriceAndNumber();
							cartNumber();
						} else {
							location.href = "login.jsp";
						}
					}
				)
			}
		})
		
		// 点击结算按钮跳转到结算页面
		$("button.createOrderButton").click(function () {
			var params = "";
			$("img.cartProductItemIfSelected").each(function () {
				if ("selectit" == $(this).attr("selectit")) {
					params += "&oiid=" + $(this).attr("oiid");
				}
			})
			params = params.substring(1);
			location.href = "forebuy?" + params;
		})
		
	    // 选中一种商品
	    $("img.cartProductItemIfSelected").on("click", function () {
	        var selectit = $(this).attr("selectit");
	        if (selectit == "selectit") {
	            $(this).attr("selectit", "false");
	            $(this).attr("src", "img/site/cartNotSelected.png");
	            $(this).parents("tr.cartProductItemTR").css("background-color", "#FFF");
	        } else {
	            $(this).attr("selectit", "selectit");
	            $(this).attr("src", "img/site/cartSelected.png");
	            $(this).parents("tr.cartProductItemTR").css("background-color", "#FFF8E1");
	        }
	        syncSelect();
	        syncCreateOrderButton();
	        calcCartSumPriceAndNumber();
	    })
	    
	    // 全选
	    $("img.selectAllItem").on("click", function () {
	        var selectit = $(this).attr("selectit");
	        if (selectit == "selectit") {
	            $("img.selectAllItem").attr("selectit", "false");
	            $("img.selectAllItem").attr("src", "img/site/cartNotSelected.png");
	            $("img.cartProductItemIfSelected").attr("selectit", "false");
	            $("img.cartProductItemIfSelected").attr("src", "img/site/cartNotSelected.png");
	            $("tr.cartProductItemTR").css("background-color", "#FFF");
	        } else {
	            $("img.selectAllItem").attr("selectit", "selectit");
	            $("img.selectAllItem").attr("src", "img/site/cartSelected.png");
	            $("img.cartProductItemIfSelected").attr("selectit", "selectit");
	            $("img.cartProductItemIfSelected").attr("src", "img/site/cartSelected.png");
	            $("tr.cartProductItemTR").css("background-color", "#FFF8E1");
	        }
	        syncCreateOrderButton();
	        calcCartSumPriceAndNumber();
	    })
	    
	    // 增加数量
	    $("a.numberPlus").on("click", function () {
	        var pid = $(this).attr("pid");
	        var stock = $("span.orderItemStock[pid="+pid+"]").text();
	        var price = $("span.orderItemPromotePrice[pid="+pid+"]").text();
	        var num = $("input.orderItemNumberSetting[pid="+pid+"]").val();
	        num++;
	        if (num > stock) {
	            num = stock;
	        }
	        syncPrice(pid, num, price);
	    })
	    
	    // 减少数量
	    $("a.numberMinus").on("click", function () {
	        var pid = $(this).attr("pid");
	        var price = $("span.orderItemPromotePrice[pid="+pid+"]").text();
	        var num = $("input.orderItemNumberSetting[pid="+pid+"]").val();
	        num--;
	        if (num <= 0) {
	            num = 1;
	        }
	        syncPrice(pid, num, price);
	    })
	    
	    // 直接修改数量
	    $("input.orderItemNumberSetting").keyup(function () {
	        var pid = $(this).attr("pid");
	        var stock = $("span.orderItemStock[pid="+pid+"]").text();
	        var num = $("input.orderItemNumberSetting[pid="+pid+"]").val();
	        var price = $("span.orderItemPromotePrice[pid="+pid+"]").text();
	        num = parseInt(num);
	        if (isNaN(num)) {
	            num = 1;
	        }
	        if (num <= 0) {
	            num = 1;
	        }
	        if (num > stock) {
	            num = stock;
	        }
	        syncPrice(pid, num, price);
	    })
	})
</script>

<link href="css/fore/cartPage.css" rel="stylesheet">

<c:if test="${empty ois}">
	<div class="emptyCart">
		您的购物车空空如也~
	</div>
</c:if>

<c:if test="${!empty ois}">
	<div class="cartDiv">
		<div class="cartTitle pull-right">
		    <span>已选商品 (不含运费)</span>
		    <span class="cartTitlePrice">￥0.00</span>
		    <!-- disabled 禁用该按钮 -->
		    <button class="createOrderButton" disabled="disabled">结 算</button>
		</div>
		<!-- 订单项内容 -->
		<div class="cartProductList">
		    <table class="cartProductTable">
		        <thead>
		            <tr>
		                <th class="selectAndImage">
		                    <img class="selectAllItem" selectit="false" src="img/site/cartNotSelected.png">
		                    <span>全选</span>
		                </th>
		                <th>商品信息</th>
		                <th>单价</th>
		                <th>数量</th>
		                <th style="width: 120px">金额</th>
		                <th class="operation">操作</th>
		            </tr>
		        </thead>
		        <tbody>
	        		<c:forEach items="${ois}" var="oi">
		        		<tr class="cartProductItemTR" oiid="${oi.id}">
		        			<td>
			                    <img class="cartProductItemIfSelected" oiid="${oi.id}" selectit="false" src="img/site/cartNotSelected.png">
			                    <a href="#nowhere" style="display: none">
			                        <img src="img/site/cartSelected.png">
			                    </a>
			                    <img class="cartProductImg" src="img/productSingle_Middle/${oi.product.firstProductImage.id}.jpg">
			                </td>
			                <td>
			                    <div class="cartProductLinkOutDiv">
			                        <a class="cartProductLink" href="foreproduct?pid=${oi.product.id}">${oi.product.name}</a>
			                        <div class="cartProductLinkInnerDiv">
			                            <img title="支持信用卡支付" src="img/site/creditcard.png">
			                            <img title="消费者保障服务，承诺7天退货" src="img/site/7day.png">
			                            <img title="消费者保障服务，承诺如实描述" src="img/site/promise.png">
			                        </div>
			                    </div>
			                </td>
			                <td>
			                    <span class="cartProductItemOringalPrice">￥${oi.product.originalPrice}</span>
			                    <span class="cartProductItemPromotionPrice">￥${oi.product.promotePrice}</span>
			                </td>
			                <td>
			                    <div class="cartProductChangeNumberDiv">
			                        <span pid="${oi.product.id}" class="hidden orderItemStock">${oi.product.stock}</span>
			                        <span pid="${oi.product.id}" class="hidden orderItemPromotePrice">${oi.product.promotePrice}</span>
			                        <a class="numberMinus" href="#nowhere" pid="${oi.product.id}">-</a>
			                        <input class="orderItemNumberSetting" hide="false" oiid="${oi.id}" pid="${oi.product.id}" autocomplete="off" type="text" value="${oi.number}">
			                        <a class="numberPlus" href="#nowhere" pid="${oi.product.id}" stock="${oi.product.stock}">+</a>
			                    </div>
			                </td>
			                <td>
			                    <span class="cartProductItemSmallSumPrice" pid="${oi.product.id}" oiid="${oi.id}">
									￥<fmt:formatNumber value="${oi.product.promotePrice * oi.number}" minFractionDigits="2" />
			                    </span>
			                </td>
			                <td>
			                    <a class="deleteOrderItem" oiid="${oi.id}" href="#nowhere">删除</a>
			                </td>
		        		</tr>
		        	</c:forEach>
		        </tbody>
		    </table>
		</div>
		<div class="cartFoot">
		    <img class="selectAllItem" selectit="false" src="img/site/cartNotSelected.png">
		    <span>全选</span>
		    <div class="pull-right">
		        <span>已选商品<span class="cartSumNumber"> 0 </span>件</span>
		        <span>合计 (不含运费):</span>
		        <span class="cartSumPrice">￥0.00</span>
		        <button class="createOrderButton" disabled="disabled">结 算</button>
		    </div>
		</div>
		<div style="height: 50px;"></div>
	</div>
</c:if>
