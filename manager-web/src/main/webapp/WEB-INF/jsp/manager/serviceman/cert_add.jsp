<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("navInfo", "资质维护");
	pageContext.setAttribute("curUrl", "serviceman/cert/list"); 
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
<style>
.tree li {
	list-style-type: none;
	cursor: pointer;
}

table tbody tr:nth-child(odd) {
	background: #F4F4F4;
}

table tbody td:nth-child(even) {
	color: #C00;
}
</style>
</head>

<body>

	<%@ include file="/WEB-INF/includes/nav_bar.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/includes/user_menu.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
				  <li><a href="${ctp }/main.html">首页</a></li>
				  <li><a href="${ctp}/serviceman/cert/list">数据列表</a></li>
				  <li class="active">新增资质</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">表单数据
              	<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal">
              		<i class="glyphicon glyphicon-question-sign"></i>
              	</div>
              </div>
			  <div class="panel-body">
				<form role="form">
				  <div class="form-group">
					<label for="exampleInputPassword1">资质名称</label>
					<input type="text" class="form-control" id="name" name="name" >
					<i style="color:red;" class="errorinfo"></i>
				  </div>
				  <button type="button" id="saveBtn" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 保存</button>
				  <button type="reset" id="resetBtn"class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
				</form>
			  </div>
			</div>
        </div>
		</div>
	</div>

	<script src="${ctp }/static/jquery/jquery-2.1.1.min.js"></script>
	<script src="${ctp }/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctp }/static/script/docs.min.js"></script>
	<script src="${ctp}/static/layer/layer.js"></script>
	<script type="text/javascript">
		var likeflg = false;
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
	    		$("#insertBtn").click(function(){
	    			var name = $("#name").val();
	    			var loadingIndex = null;
	    			// 此处可做表单校验,账号是否已存在，是否为空，格式是否正确
	    			if(validate(name)){
	    				$.ajax({
	        				type:"POST",
	        				url:"${ctp}/serviceman/cert/addCert",
	        				// $("form").serialize()
	        				data:{"name":name},
	        				bedoreSending:function(){
	        					loadingIndex = layer.msg("正在处理",{icon:16});
	        				},
	        				success:function(result){
	        					layer.close(loadingIndex);
	        					if(result.success){
	        						layer.msg("分类新增成功",{time:2000,icon:6},function(){
	        							// 回调函数做页面跳转,跳转到列表
	        							window.location.href="${ctp}/serviceman/cert/list";
	        						});
	        					}else{
	        						layer.msg("分类用户失败",{time:2000,icon:5,shift:6},function(){});
	        					}
	        				}	
	        			});
	    			}
	    	
	    		});
	    	}); 
	    	// 表单验证
	    	function validate(name){
	    		if(name == null || name == ""){
	    			return false;
	    		}
	    		return true
	    	}
        </script>
		<%@include file="/WEB-INF/includes/common-js.jsp" %>
</body>
</html>
