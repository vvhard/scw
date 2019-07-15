<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="UTF-8">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${ctp }/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctp }/css/font-awesome.min.css">
	<link rel="stylesheet" href="${ctp }/css/main.css">
	<link rel="stylesheet" href="${ctp }/css/doc.min.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	</style>
  </head>

  <body>

	<%@include file="/WEB-INF/includes/nav_bar.jsp" %>

    <div class="container-fluid">
      <div class="row">
 		<%@include file="/WEB-INF/includes/user_menu.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
				  <li><a href="${ctp }/main.html">首页</a></li>
				  <li><a href="${ctp}/permission/user/list">数据列表</a></li>
				  <li class="active">新增用户</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
				<form role="form">
				  <div class="form-group">
					<label for="exampleInputPassword1">登陆账号</label>
					<input type="text" class="form-control" id="loginacct" name="loginacct" placeholder="请输入登陆账号">
					<i style="color:red;" class="errorinfo"></i>
				  </div>
				  <div class="form-group">
					<label for="exampleInputPassword1">用户名称</label>
					<input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名称">
					<i style="color:red;" class="errorinfo"></i>
				  </div>
				  <div class="form-group">
					<label for="exampleInputEmail1">邮箱地址</label>
					<input type="email" class="form-control" id="email" name="email" placeholder="请输入邮箱地址">
					<i style="color:red;" class="errorinfo"></i>
				  </div>
				  <button type="button" id="insertBtn" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
				  <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
				</form>
			  </div>
			</div>
        </div>
      </div>
    </div>

    <script src="${ctp }/jquery/jquery-2.1.1.min.js"></script>
    <script src="${ctp }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctp }/script/docs.min.js"></script>
	<script src="${ctp }/jquery-validation-1.13.1/dist/jquery.validate.min.js"></script>
	<script src="${ctp}/layer/layer.js"></script>
    <script type="text/javascript">
    	$(function(){
    	    $(".list-group-item").click(function(){
                // jquery对象的回调方法中的this关键字为DOM对象
                // $(DOM) ==> JQuery
    		    if ( $(this).find("ul") ) { // 3 li
    				$(this).toggleClass("tree-closed");
    				if ( $(this).hasClass("tree-closed") ) {
    					$("ul", this).hide("fast");
    				} else {
    					$("ul", this).show("fast");
    				}
    			}
    		});
    		$("#insertBtn").click(function(){
    			var loginacct = $("#loginacct").val();
    			var username = $("#username").val();
    			var email = $("#email").val();
    			var loadingIndex = null;
    			// 此处可做表单校验,账号是否已存在，是否为空，格式是否正确
    			if(validate(loginacct,username,email)){
    				$.ajax({
        				type:"POST",
        				url:"${ctp}/permission/user/addUser",
        				// $("form").serialize()
        				data:{"loginacct":loginacct,"username":username,"email":email},
        				bedoreSending:function(){
        					loadingIndex = layer.msg("正在处理",{icon:16});
        				},
        				success:function(result){
        					layer.close(loadingIndex);
        					if(result.success){
        						layer.msg("用户新增成功",{time:2000,icon:6},function(){
        							// 回调函数做页面跳转,跳转到列表
        							window.location.href="${ctp}/permission/user/list";
        						});
        					}else{
        						layer.msg("新增用户失败",{time:2000,icon:5,shift:6},function(){});
        					}
        				}	
        			});
    			}
    	
    		});
    	}); 
    	// 表单验证
    	function validate(loginacct,username,email){
    		if(loginacct == null || loginacct == ""){
    			return false;
    		}
    		return true
    	}
        
    </script>
  </body>
</html>
