<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("navInfo", "项目分类");
	pageContext.setAttribute("curUrl", "serviceman/pt/list"); 
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
	<link rel="stylesheet" href="${ctp}/static/ztree/zTreeStyle.css">
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
				  <li><a href="${ctp}/serviceman/pt/list">数据列表</a></li>
				  <li class="active">编辑项目分类</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
				<form role="form" id="editForm">
				
				  <div class="form-group">
					<label for="exampleInputPassword1">分类名称</label>
					<!-- 自定义属性 idval="${type.id}" 记录当前类型的id -->
					<input type="text" class="form-control" id="name" name="name" placeholder="请输入分类名称" value="${type.name }" idval="${type.id}">
					<i style="color:red;" class="errorinfo"></i>
				  </div>
				  <div class="form-group">
					<label for="exampleInputPassword1">简介</label>
					<input type="text" class="form-control" id="description" name="description" placeholder="请输分类简介" value="${type.description }">
					<i style="color:red;" class="errorinfo"></i>
				  </div>
		
				  <button type="button" id="savetBtn" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 保存</button>
				  <button type="button" id="resetBtn"class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
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
    	    $("#resetBtn").click(function(){
    	    	$("#editForm")[0].reset();
    	    });
    		$("#savetBtn").click(function(){
    			var name = $("#name").val();
    			var description = $("#description").val();
    			var id = $("#name").attr("idval");
    			var loadingIndex = null;
    			// 此处可做表单校验,账号是否已存在，是否为空，格式是否正确
    			if(validate(name)){
    				$.ajax({
        				type:"POST",
        				url:"${ctp}/serviceman/pt/save",
        				// $("form").serialize()
        				data:{"id":id,"name":name,"description":description},
        				bedoreSending:function(){
        					loadingIndex = layer.msg("正在处理",{icon:16});
        				},
        				success:function(result){
        					layer.close(loadingIndex);
        					if(result.success){
        						layer.msg("保存成功",{time:1000,icon:6},function(){
        							// 回调函数做页面跳转,跳转到列表
        							window.location.href="${ctp}/serviceman/pt/list";
        						});
        					}else{
        						layer.msg("保存失败",{time:1000,icon:5,shift:6},function(){});
        					}
        				}	// success回调
        			}); // ajax
    				
    			}
    	
    		});// saveBtn事件
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
