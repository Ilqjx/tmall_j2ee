<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>

<title>编辑产品属性值</title>

<script>
	$(function () {
		$("input.pvValueInput").keyup(function () {
			var pvid = $(this).attr("pvid");
			var value = $(this).val();
			var url = "admin_product_updatePropertyValue";
			var parentSpan = $(this).parent("span");
			parentSpan.css("border", "1px solid yellow");
			
			$.post(
				url,
				{"pvid" : pvid, "value" : value},
				function (res) {
					if ("success" == res) {
						parentSpan.css("border", "1px solid green");
					} else {
						parentSpan.css("border", "1px solid red");
					}
				}
			)
		})
	})
</script>

<div class="workingArea">
	<ol class="breadcrumb">
		<li><a href="admin_category_list">所有分类</a></li>
		<li><a href="admin_product_list?cid=${product.category.id}">${product.category.name}</a></li>
		<li class="active">${product.name}</li>
		<li class="active">编辑产品属性</li>
	</ol>
	<div class="pvDiv">
		<c:forEach items="${pvs}" var="pv">
			<div class="pvEachDiv">
				<span class="pvName">${pv.property.name}</span>
				<span class="pvValue">
					<input class="pvValueInput" type="text" value="${pv.value}" pvid="${pv.id}">
				</span>
			</div>
		</c:forEach>
		<div style="clear: both;"></div>
	</div>
</div>

<%@ include file="../include/admin/adminFooter.jsp" %>
