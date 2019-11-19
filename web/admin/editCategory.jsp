<!DOCTYPE html>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" contentType="text/html; charset=utf-8" %>

<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>

<html>

<head>
	<title>编辑分类</title>

	<script src="js/jquery/2.0.0/jquery.min.js"></script>
	<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
	<link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet" >
	
	<script>
		$("#editForm").submit(function () {
			if (!checkEmpty("name", "分类名称")) {
				return false;
			}
			return true;
		})
	</script>
</head>

<body>
	<div class="workingArea">
		<ol class="breadcrumb">
			<li>
				<a href="admin_category_list">所有分类</a>
			</li>
			<li class="active">编辑分类</li>
		</ol>
		<div class="panel panel-warning editDiv">
			<div class="panel-heading">编辑分类</div>
			<div class="panel-body">
				<form action="admin_category_update" method="post" enctype="multipart/form-data" id="editForm">
					<table class="editTable">
						<tr>
							<td>分类名称</td>
							<td>
								<input type="text" name="name" id="name" value="${category.name}" class="form-control">
							</td>
						</tr>
						<tr>
							<td>分类图片</td>
							<td>
								<input type="file" id="categoryPic" name="filePath" accept="image/*">
							</td>
						</tr>
						<tr class="submitTR">
							<td colspan="2" align="center">
								<input type="hidden" name="id" value="${category.id}">
								<button type="submit" class="btn btn-success">提交</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<%@ include file="../include/admin/adminFooter.jsp" %>
</body>

</html>
