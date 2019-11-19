<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>

<script>
	$(function () {
		$("button.loginSubmitButton").click(function () {
			var name = $("input#modalName").val();
			var password = $("input#modalPassword").val();
			if (name.length == 0) {
				$("span.errorMessage").html("用户名不能为空");
				$("div.loginErrorMessageDiv").show();
				// 取消掉提交功能
				return false;
			}
			if (password.length == 0) {
				$("span.errorMessage").html("密码不能为空");
				$("div.loginErrorMessageDiv").show();
				return false;
			}
			
			var url = "foreloginAjax";
			$.post(
				url,
				{"name" : name, "password" : password},
				function (res) {
					if (res == "success") {
						// 刷新当前页面
						location.reload();
					} else {
						$("span.errorMessage").html(res);
						$("div.loginErrorMessageDiv").show();
					}
				}
			)
			return true;
		})
		
		$("div.loginInput input#modalName").keyup(function () {
			$("div.loginErrorMessageDiv").hide();
		})
		
		$("div.loginInput input#modalPassword").keyup(function () {
			$("div.loginErrorMessageDiv").hide();
		})
	})
</script>

<link href="css/fore/modal.css" rel="stylesheet">

<div class="modal fade" id="loginModal" tabindex="1" role="dialog">
	<div class="modal-dialog loginDivInProductPageModalDiv" style="width: 350px; height: 400px;">
		<div class="modal-content">
			<div class="loginDivInProductPage">
				<div class="loginErrorMessageDiv">
					<div class="alert alert-danger">
						<button type="button" class="close" data-dismiss="alert"></button>
						<span class="errorMessage" style="display: block; text-align: center;"></span>
					</div>
				</div>
				<div class="login_acount_text">账户登录</div>
				<div class="loginInput">
					<span class="loginInputIcon">
						<span class="glyphicon glyphicon-user"></span>
					</span>
					<input id="modalName" name="modalName" type="text" placeholder="手机/会员名/邮箱">
				</div>
				<div class="loginInput">
					<span class="loginInputIcon">
						<span class="glyphicon glyphicon-lock"></span>
					</span>
					<input id="modalPassword" name="modalPassword" type="password" placeholder="密码">
				</div>
				<div>
					<a href="#nowhere">忘记登录密码</a>
					<a href="register.jsp" class="pull-right">免费注册</a>
				</div>
				<div style="margin-top: 20px;">
					<button type="button" class="btn btn-block redButton loginSubmitButton">登录</button>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="deleteConfirmModal" tabindex="-1" role="dialog">
	<div class="modal-dialog deleteConfirmModalDiv" style="width: 400px;">
		<div class="modal-content">
			<div class="modal-header">
				<button data-dismiss="modal" class="close" type="button">
					<span>×</span>
					<span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">确认删除？</h4>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" data-dismiss="modal" type="button">关闭</button>
				<button class="btn btn-primary deleteConfirmButton" id="submit" type="button">确认</button>
			</div>
		</div>
	</div>
</div>
