<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>

<title>产品图片管理</title>

<script>
	$(function () {
		$("#addFormSingle").submit(function () {
			if (!checkEmpty("filepathSingle", "图片文件")) {
				return false;
			}
			return true;
		})
		$("#addFormDetail").submit(function () {
			if (!checkEmpty("filepathDetail", "图片文件")) {
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
	 	<li class="active">产品图片管理</li>
	</ol>
	<table class="addPictureTable" align="center">
		<tr>
			<td class="addPictureTableTD">
				<div>
					<div class="panel panel-warning addPictureDiv">
						<div class="panel-heading">新增产品<b class="text-primary"> 单个 </b>图片</div>
						<div class="panel-body">
							<form action="admin_productImage_add" id="addFormSingle" method="post" enctype="multipart/form-data">
								<table class="addTable">
									<tr>
										<td>请选择本地图片 尺寸400X400 为佳</td>
									</tr>
									<tr>
										<td>
											<input type="file" name="filepath" id="filepathSingle" accept="image/*">
										</td>
									</tr>
									<tr class="submitTR">
										<td>
											<input type="hidden" name="pid" value="${product.id}">
											<input type="hidden" name="type" value="type_single">
											<button type="submit" class="btn btn-success">提 交</button>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover table-condensed">
						<thead>
							<tr class="success">
								<th>ID</th>
								<th>产品单个图片缩略图</th>
								<th>删除</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pisSingle}" var="pi">
								<tr>
									<td>${pi.id}</td>
									<td>
										<a title="点击查看原图" href="img/productSingle/${pi.id}.jpg">
											<img height="50px" src="img/productSingle/${pi.id}.jpg">
										</a>
									</td>
									<td>
										<a deleteLink="true" href="admin_productImage_delete?id=${pi.id}">
											<span class="glyphicon glyphicon-trash"></span>
										</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</td>
			<td class="addPictureTableTD">
				<div>
					<div class="panel panel-warning addPictureDiv">
						<div class="panel-heading">新增产品<b class="text-primary"> 详情 </b>图片</div>
						<div class="panel-body">
							<form action="admin_productImage_add" id="addFormDetail" method="post" enctype="multipart/form-data">
								<table class="addTable">
									<tr>
										<td>请选择本地图片 宽度790 为佳</td>
									</tr>
									<tr>
										<td>
											<input type="file" name="filepath" id="filepathDetail" accept="image/*">
										</td>
									</tr>
									<tr class="submitTR">
										<td>
											<input type="hidden" name="pid" value="${product.id}">
											<input type="hidden" name="type" value="type_detail">
											<button type="submit" class="btn btn-success">提 交</button>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
					<table class="table table-striped table-bordered table-hover table-condensed">
						<thead>
							<tr class="success">
								<th>ID</th>
								<th>产品详情图片缩略图</th>
								<th>删除</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pisDetail}" var="pi">
								<tr>
									<td>${pi.id}</td>
									<td>
										<a title="点击查看原图" href="img/productDetail/${pi.id}.jpg">
											<img height="50px" src="img/productDetail/${pi.id}.jpg">
										</a>
									</td>
									<td>
										<a deleteLink="true" href="admin_productImage_delete?id=${pi.id}">
											<span class="glyphicon glyphicon-trash"></span>
										</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>

<%@ include file="../include/admin/adminFooter.jsp" %>
