<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>

<title>编辑产品</title>

<script>
	$(function () {
		$("#editForm").submit(function () {
			if (!checkEmpty("name", "产品名称")) {
				return false;
			}
			if (!checkNumber("originalPrice", "原价格")) {
				return false;
			}
			if (!checkNumber("promotePrice", "优惠价格")) {
				return false;
			}
			if (!checkInt("stock", "库存")) {
				return false;
			}
			return true;
		})
	})
</script>

<div class="workingArea">
	<ol class="breadcrumb">
		<li><a href="admin_category_list">所有分类</a></li>
		<li><a href="admin_product_list?cid=${product.category.id}">${product.category.name}</a></li>
		<li class="active">${product.name}</li>
		<li class="active">编辑产品</li>
	</ol>
	<div class="editDiv panel panel-warning">
		<div class="panel-heading">编辑产品</div>
		<div class="panel-body">
			<form action="admin_product_update" method="post" id="editForm">
				<table class="editTable">
					<tr>
						<td>产品名称</td>
						<td>
							<input type="text" name="name" id="name" value="${product.name}" class="form-control">
						</td>
					</tr>
					<tr>
						<td>产品小标题</td>
						<td>
							<input type="text" name="subTitle" id="subTitle" value="${product.subTitle}" class="form-control">
						</td>
					</tr>
					<tr>
						<td>原价格</td>
						<td>
							<input type="text" name="originalPrice" id="originalPrice" value="${product.originalPrice}" class="form-control">
						</td>
					</tr>
					<tr>
						<td>优惠价格</td>
						<td>
							<input type="text" name="promotePrice" id="promotePrice" value="${product.promotePrice}" class="form-control">
						</td>
					</tr>
					<tr>
						<td>库存</td>
						<td>
							<input type="text" name="stock" id="stock" value="${product.stock}" class="form-control">
						</td>
					</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
							<input type="hidden" name="id" value="${product.id}">
							<button type="submit" class="btn btn-success">提 交</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>

<%@ include file="../include/admin/adminFooter.jsp" %>
