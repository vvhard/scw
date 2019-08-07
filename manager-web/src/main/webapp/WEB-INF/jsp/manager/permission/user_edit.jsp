<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("navInfo", "用戶维护");
	pageContext.setAttribute("curUrl", "permission/user/list"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="UTF-8">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${ctp }/static/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctp }/static/css/font-awesome.min.css">
	<link rel="stylesheet" href="${ctp }/static/css/main.css">
	<link rel="stylesheet" href="${ctp }/static/css/doc.min.css">
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
				  <li><a href="${ctp }/permission/user/list">数据列表</a></li>
				  <li class="active">编辑</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
				<form role="form" id="editForm">
				  <input type="hidden" value="${user.id}" id="user_id"><!-- 使用隐藏域记录当前用户的id -->
				  <div class="form-group">
					<label for="exampleInputPassword1">登陆账号</label>
					<input type="text" class="form-control" id="loginacct" name="loginacct" placeholder="请输入登陆账号" value="${user.loginacct }">
					<i style="color:red;" class="loginacct_errorinfo"></i>
				  </div>
				  <div class="form-group">
					<label for="exampleInputPassword1" >用户名称</label>
					<input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名称" value="${user.username }">
					<i style="color:red;" class="errorinfo"></i>
				  </div>
				  <div class="form-group">
					<label for="exampleInputEmail1">邮箱地址</label>
					<input type="email" class="form-control" id="email" name="email" placeholder="请输入邮箱地址" value="${user.email }">
					<i style="color:red;" class="email_errorinfo"></i>
				  </div>
				  <button type="button" id="savetBtn" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 保存</button>
				  <button type="button" id="reseBtn"class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
				</form>
			  </div>
			</div>
        </div>
      </div>
    </div>

    <script src="${ctp }/static/jquery/jquery-2.1.1.min.js"></script>
    <script src="${ctp }/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctp }/static/script/docs.min.js"></script>
	<script src="${ctp }/static/jquery-validation-1.13.1/dist/jquery.validate.min.js"></script>
	<script src="${ctp}/static/layer/layer.js"></script>
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
    		$("#savetBtn").click(function(){
    			var loginacct = $("#loginacct").val();
    			var username = $("#username").val();
    			var email = $("#email").val();
    			
    			var loadingIndex = null;
    			// 此处可做表单校验,账号是否已存在，是否为空，格式是否正确
    			if(loginacct != null || loginacct != "") {
        			$.ajax({
        				type:"POST",
        				url:"${ctp}/permission/user/save",
        				// $("form").serialize()
        				data:{"id":$("#user_id").val(),"loginacct":loginacct,"username":username,"email":email},
        				bedoreSending:function(){
        					loadingIndex = layer.msg("正在处理",{icon:16});
        				},
        				success:function(result){
        					layer.close(loadingIndex);
        					if(result.success){
        						layer.msg("用户修改成功",{time:2000,icon:6},function(){
        							// 回调函数做页面跳转,跳转到列表
        							window.location.href="${ctp}/permission/user/list";
        						});
        					}else{
        						layer.msg("修改用户失败",{time:2000,icon:5,shift:6},function(){});
        					}
        				}	
        			});
    			}else{
		    		layer.msg("账号不能为空",{time:2000,icon:5,shift:6});
    			}
    		});
    		$("#resetBtn").click(function(){
    			$("#editForm")[0].reset();
    		});
    	}); 
    	
    	function validate(loginacct,username,email){
    		
    		if(loginacct != null || loginacct != "")
    			return false;
    		if(username != null || username != "")
    			return false;
    		// 邮箱格式先不做检验
    		if(email != null || email != "")
    			return false;
    		return true
    	}
    </script>
	<%@include file="/WEB-INF/includes/common-js.jsp" %>
  </body>
</html>
