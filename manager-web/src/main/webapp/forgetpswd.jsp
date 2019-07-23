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

      <form  class="form-signin" role="form" >
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-user"></i> 找回密码</h2>
        <!-- 取出一次就将session中的这个属性移除,scope=""不写默认从所有域 -->
		  <c:remove var="msg"/>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" id="loginacct_input" name="loginacct" placeholder="请输入要找回密码的账号" 
					autofocus>
			<c:remove var="errorUser"/>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  </div>
		  
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" id="email_input" name="email" placeholder="请输入账号对应的邮箱" 
					style="margin-top:10px;" >
			<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
		  </div>
		  
		  <div class="form-group has-success has-feedback">
			<select class="form-control" >
                <option value="0">会员账号</option>
                <option value="1">管理员账号</option>
            </select>
		  </div>
		  
        <a class="btn btn-lg btn-success btn-block" onclick="confirm()" > 确定</a>
      </form>
    </div>
    <script src="${ctp}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${ctp}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ctp}/jquery-validation-1.13.1/dist/jquery.validate.min.js"></script>
    <script src="${ctp}/layer/layer.js"></script>
    <script>
    
  
	    function confirm(){
	    	var loginacct = $("#loginacct_input").val().trim();
	    	var email = $("#email_input").val().trim();
	    	var type = $(".form-control :selected").val();
	    	var url = null;
	    	if(type == "1")
	    		url =  "${ctp}/permission/user/sendEmail";
	    	if(type == "0")
	    		url =  "${ctp}/main.html";
	    	$.ajax({
	    		type: "POST",
	    		url: url,
	    		data:{"loginacct":loginacct,"email":email},
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