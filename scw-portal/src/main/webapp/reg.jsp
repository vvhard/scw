<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keys" content="">
<meta name="author" content="">
<link rel="stylesheet" href="${ctp}/static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctp}/static/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctp}/static/css/login.css">
<style>
</style>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<div>
				<a class="navbar-brand" href="index.html" style="font-size: 32px;">尚筹网-创意产品众筹平台</a>
			</div>
		</div>
	</div>
	</nav>

	<div class="container">

		<form id="regForm" class="form-signin" role="form" action="${ctp }/member/regist" method="post">
			<h2 class="form-signin-heading">
				<i class="glyphicon glyphicon-log-in"></i> 用户注册
			</h2>
			<div class="form-group has-success has-feedback">
				<input type="text" class="form-control" name="loginacct" id="loginacct_input" placeholder="请输入登录账号" autofocus value="${memeber.loginacct}"> 
					<span class="glyphicon glyphicon-user form-control-feedback"></span>
					<span class="errorinfo" style="color:red;">${msg} </span>
			</div>
			<div class="form-group has-success has-feedback">
				<input type="password" class="form-control" name="userpswd"
					id="userpswd_input" placeholder="请输入登录密码" style="margin-top: 10px;">
				<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				<span class="errorinfo" style="color:red;"></span>
			</div>
			<div class="form-group has-success has-feedback ">
				<input type="text" class="form-control" name="email" id="email_input" placeholder="请输入邮箱地址" style="margin-top: 10px;" value="${memeber.email}">
				<span class="glyphicon glyphicon glyphicon-envelope form-control-feedback"></span>
				<span class="errorinfo" style="color:red;"></span>
			</div>
			<div class="form-group has-success has-feedback">
				<select class="form-control">
					<option>会员</option>
				</select>
			</div>
			<div class="checkbox">
				<label> 忘记密码 </label> <label style="float: right"> <a
					href="${ctp }/login.jsp">我有账号</a>
				</label>
			</div>
			<a class="btn btn-lg btn-success btn-block" id="submitBtn"> 注册</a>
		</form>
	</div>
	<script src="${ctp}/static/jquery/jquery-2.1.1.min.js"></script>
	<script src="${ctp}/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctp}/static/jquery-validation-1.13.1/dist/jquery.validate.min.js"></script>
	<script src="${ctp}/static/layer/layer.js"></script>
	<script type="text/javascript">
		// 设置校验器策略
		$.validator.setDefaults({
			// 定义错误信息显示
			// map代表每一个错误字段的错误信息，list封装了错误元素，错误信息，错误规则

			showErrors:function(map,list){
				$(".errorinfo").empty();
				$(".form-group").removeClass("has-success has-error has-warning");
				$.each(list,function(){
					// this.element为dom元素
					$(this.element).nextAll(".errorinfo").text(this.message);
					$(this.element).parent("div.form-group").addClass("has-error");
				})
			}
		});
		// 注册按钮点击事件
		$("#submitBtn").click(function() {
			//$("#regForm").submit();
			$.ajax({
				type:"POST",
				url:"${ctp}/member/regist",
				data:$("#regForm").serialize(),
				success:function(result){
					if(result.code ==1){
						layer.msg("注册成功，请登录!",{time:1500,icon:6},function(){
							window.location.href="${ctp}/login.jsp";
						});
					}else{
						layer.msg("注册失败，" + result.ext.error, {
							time : 1500,
							icon : 5,
							shift : 6
						}, function() {});
					}
				}
				
			});
		
		});
		// 表单校验
		$("#regForm").validate({
			// 校验规则
			rules : {
				loginacct : {
					required : true,
					minlength : 6
				},
				userpswd : {
					required : true,
					minlength : 6
				},
				email : {
					required : true,
					email : true
				}
			},
			// 错误提示
			messages : {
				loginacct : {
					required : "登陆账号必须的",
					minlength : "登陆账号6位以上"
				},
				userpswd : {
					required : "密码必须的",
					minlength : "密码6位以上"
				},
				email : {
					required : "邮箱必须的",
					email : "必须为邮箱格式"
				}
			}
		});
	</script>
</body>
</html>