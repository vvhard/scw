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
	<link rel="stylesheet" href="${ctp}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctp}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${ctp}/css/login.css">
	<style>

	</style>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
      </div>
    </nav>

    <div class="container">

      <form id="loginForm" class="form-signin" role="form" action="${ctp }/permission/user/login" method="post">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-user"></i> 用户登录</h2>
        <span style="color: red;text-align: center;">${msg}</span>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" id="loginacct_input" name="loginacct" placeholder="请输入登录账号" autofocus value="${errorUser.loginacct} ">
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  	<span style="color: red;" class="errorinfo"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" id="userpswd_input" name="userpswd" placeholder="请输入登录密码" 
					style="margin-top:10px;" >
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			<span style="color: red;" class="errorinfo"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<select class="form-control" >
                <option value="member">会员</option>
                <option value="manager">管理</option>
            </select>
		  </div>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> 记住我
          </label>
          <br>
          <label>
            忘记密码
          </label>
          <label style="float:right">
            <a href="${ctp }/reg.jsp">我要注册</a>
          </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
      </form>
    </div>
    <script src="${ctp}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${ctp}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ctp}/jquery-validation-1.13.1/dist/jquery.validate.min.js"></script>
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
	$("#loginForm").validate({
		// 校验规则
		rules : {
			loginacct : {
				required : true
			},
			userpswd : {
				required : true
			}
		},
		// 错误提示
		messages : {
			loginacct : {
				required : "账号必须的"
			},
			userpswd : {
				required : "密码必须的"
			}
		}
	});
    // 验证登陆账号是否可用
    function validateAcct(){
    	var loginacct = $("#loginacct_input").val();
    	var regex = /(\s)+/
    	
    	if(loginacct == null || regex.test(loginacct))
    		return false;
    	return true;
    }
    // 验证密码是否为空
    function validatePwd(){
    	var pwd = $("#userpswd_input").val();
    	var regex = /(\s)+/
    	if(pwd == null || regex.test(pwd))
    		return false;
    	return true;
    }
    function dologin() {
        var type = $(":selected").val();
        if ( type == "manager" ) {
           $("#loginForm").submit();    		
        } else {
            //window.location.href = "member.html";
            alert("功能暂未开放");
        }
    }
    </script>
  </body>
</html>