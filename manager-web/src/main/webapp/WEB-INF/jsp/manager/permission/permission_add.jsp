<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	
	pageContext.setAttribute("navInfo", "许可维护"); 
	pageContext.setAttribute("curUrl", "permission/perm/list"); 

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
				  <li><a href="${ctp}/main.html">首页</a></li>
				  <li><a href="${ctp }/permission/perm/list">许可列表</a></li>
				  <li class="active">新增许可</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
				<form id="pForm" role="form">
				  <div class="form-group">
					<label for="exampleInputPassword1">许可名称</label>
					<input type="text" class="form-control" id="permissionname" placeholder="请输入许可名称" pidVal="${pid }">
				  </div>
				  <div class="form-group">
					<label for="exampleInputPassword1">链接地址</label>
					<input type="text" class="form-control" id="url" placeholder="请输入链接地址">
				  </div>
				  <button type="button" id="insertBtn" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
				  <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
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
		    $("#insertBtn").click(function(){
		    	var permissionname = $("#permissionname").val();
		    	if ( permissionname == "" ) {
                    layer.msg("许可名称不能为空，请输入", {time:2000, icon:5, shift:6}, function(){
                    	
                    });
                    return;
		    	}
		    	
		    	var loadingIndex = null;
		    	$.ajax({
		    		type : "POST",
		    		url  : "${ctp}/permission/perm/addPermission",
		    		data : {
		    			"name" : permissionname,
		    			"url"  : $("#url").val(),
		    			"pid"  : $("#permissionname").attr("pidVal")
		    		},
		    		beforeSend : function() {
		    			loadingIndex = layer.msg('处理中', {icon: 16});
		    		},
		    		success : function(result) {
		    			layer.close(loadingIndex);
		    			if ( result.success ) {
	                        layer.msg("许可信息新增成功", {time:1000, icon:6}, function(){
	                        	window.location.href = "${ctp}/permission/perm/list";
	                        });
		    			} else {
	                        layer.msg("许可信息新增失败，请重新操作", {time:2000, icon:5, shift:6}, function(){
	                        	
	                        });
		    			}
		    		}
		    	});
		    });
    	}); 

        
    </script>

	<%@include file="/WEB-INF/includes/common-js.jsp" %>
  </body>
</html>
