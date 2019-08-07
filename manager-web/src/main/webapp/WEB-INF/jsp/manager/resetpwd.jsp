<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
          <div><a class="navbar-brand" href="index.jsp" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
      </div>
    </nav>

    <div class="container">

      <form  class="form-signin" role="form" >
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-user"></i> 重置密码</h2>
          <input type="hidden" id="token" value="${param.token }">
		  <div class="form-group has-success has-feedback">
			<input type="password" class="form-control" id="password_input" name="password" placeholder="请输入新密码" 
					autofocus>
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			<span style="color: red;" class="errorinfo"></span>
		  </div>
		  
		  <div class="form-group has-success has-feedback">
			<input type="password" class="form-control" id="repassword_input" name="repassword" placeholder="请再次输入新密码" 
					style="margin-top:10px;" >
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			<span style="color: red;" class="errorinfo"></span>
		  </div>		  
        <a class="btn btn-lg btn-success btn-block" onclick="confirm()" > 确定</a>
      </form>
    </div>
    <script src="${ctp}/static/jquery/jquery-2.1.1.min.js"></script>
    <script src="${ctp}/static/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ctp}/static/jquery-validation-1.13.1/dist/jquery.validate.min.js"></script>
    <script src="${ctp}/static/layer/layer.js"></script>
    <script src="${ctp}/static/jquery-validation-1.13.1/dist/jquery.validate.min.js"></script>
    <script>
	    $.validator.setDefaults({
	    	showErrors:function(map,list){
	    		$(".errorinfo").empty();
	    		$(".form-group").removeClass("has-success has-error has-warning");
	    		$.each(list,function(){
					$(this.element).nextAll(".errorinfo").text(this.message)
					$(this.element).parent("div.form-group").addClass("has-error")
	    		})
	    	}
	    });
		// 表单校验
		$(".form-signin").validate({
			// 校验规则
			rules : {
				userpswd : {
					required : true
				}
			},
			// 错误提示
			messages : {
				userpswd : {
					required : "密码不能为空"
				}
			}
		});  
  
	    function confirm(){
	    	var password = $("#password_input").val().trim();
	    	var password1 = $("#repassword_input").val().trim();
	    	var token = $("#token").val();
			
	    	if(password != password1){
				layer.msg("两次密码不一致，请检查", {
					time : 1500,
					icon : 5,
					shift : 6
				}, function() {
					return;
				});
	    	}
	    	$.ajax({
	    		type: "POST",
	    		url: "${ctp}/permission/user/resetpswd",
	    		data:{"password":password,"token":token},
	    		success:function(result){
	    			if(result.success){
	    				layer.msg(result.data.msg,{time:1500,icon:6},function(){
	    					window.location.href="${ctp}/main.html";
	    				});
	    			}else{
						layer.msg(result.data.msg, {
							time : 1500,
							icon : 5,
							shift : 6
						}, function() {});
	    			}
	    		}
	    	});
	    }
    </script>
  </body>
</html>