<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<title>付款页面</title>

<link href="css/fore/aliPayPage.css" rel="stylesheet">

<div class="aliPayPageDiv">
    <div class="aliPayPageLogo">
        <img class="pull-left" src="img/site/simpleLogo.png">
        <div style="clear: both"></div>
    </div>
    <div>
        <span class="confirmMoneyText">扫一扫付款（元）</span>
        <span class="confirmMoney">
        	￥<fmt:formatNumber value="${order.total}" minFractionDigits="2" />
        </span>
    </div>
    <div>
        <img class="aliPayImg" src="img/site/alipay2wei.png">
    </div>
    <div>
        <a href="forepayed?oid=${order.id}&total=${order.total}">
        	<button class="confirmPay">确认支付</button>
        </a>
    </div>
</div>