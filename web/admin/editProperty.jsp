<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>

<title>编辑属性</title>

<script>
	$(function () {
		$("#editForm").submit(function () {
			if (!checkEmpty("name", "属性名称")) {
				return false;
			}
			return true;
		})
	})
</script>

<div class="workingArea">
	<ol class="breadcrumb">
		<li><a href="admin_category_list">所有分类</a></li>
		<li><a href="admin_property_list?cid=${property.category.id}">${property.category.name}</a></li>
		<li class="active">属性管理</li>
	</ol>
	<div class="editDiv panel panel-warning">
		<div class="panel-heading">编辑属性</div>
		<div class="panel-body">
			<form action="admin_property_update" method="post" id="editForm">
				<table class="editTable">
					<tr>
						<td>属性名称</td>
						<td>
							<input type="text" name="name" id="name" class="form-control" value="${property.name}">
						</td>
					</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
							<input type="hidden" name="id" value="${property.id}">
							<button type="submit" class="btn btn-success">提 交</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>

<%@ include file="../include/admin/adminFooter.jsp" %>
