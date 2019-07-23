<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("navInfo", "用戶维护");
	pageContext.setAttribute("curUrl", "permission/user/list"); 
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${ctp}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctp}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${ctp}/css/main.css">
	<link rel="stylesheet" href="${ctp}/css/doc.min.css">
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
				  <li class="active">分配角色</li>
				</ol>
			<div class="panel panel-default">
			  <div class="panel-body">
				<form id="roleForm" role="form" class="form-inline">
				  <input type="hidden" name="userid" value="${userid}">
				  <div class="form-group">
					<label for="exampleInputPassword1">未分配角色列表</label><br>
					<select id="leftList" name="unassignroleids" class="form-control" multiple size="10" style="width:200px;overflow-y:auto;">
                        <c:forEach items="${unassignRoles}" var="role">
                        	<option value="${role.id} ">${role.name}</option>
                        </c:forEach>
                    </select>
				  </div>
				  <div class="form-group">
                        <ul>
                            <li id="left2rightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                            <br>
                            <li id="right2leftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                        </ul>
				  </div>
				  <div class="form-group" style="margin-left:40px;">
					<label for="exampleInputPassword1">已分配角色列表</label><br>
					<select id="rightList" name="assignroleids" class="form-control" multiple size="10" style="width:200px;overflow-y:auto;">
						<c:forEach items="${assignRoles}" var="role">
                        	<option value="${role.id} ">${role.name}</option>
                        </c:forEach>
                    </select>
				  </div>
				</form>
			  </div>
			</div>
        </div>
      </div>
    </div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
		<div class="modal-content">
		  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			<h4 class="modal-title" id="myModalLabel">帮助</h4>
		  </div>
		  <div class="modal-body">
			<div class="bs-callout bs-callout-info">
				<h4>测试标题1</h4>
				<p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
			  </div>
			<div class="bs-callout bs-callout-info">
				<h4>测试标题2</h4>
				<p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
			  </div>
		  </div>
		  <!--
		  <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="button" class="btn btn-primary">Save changes</button>
		  </div>
		  -->
		</div>
	  </div>
	</div>
    <script src="${ctp}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${ctp}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctp}/script/docs.min.js"></script>
	<script src="${ctp}/layer/layer.js"></script>
        <script type="text/javascript">
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
			    $("#left2rightBtn").click(function(){
			    	// 获取被选中的角色
			    	var opts = $("#leftList :selected");
			    	if(opts.length == 0){
			    		layer.msg("请选择角色",{time:2000,icon:5,shift:6},function(){});
			    	}else{
			    		$.ajax({
			    			type:"POST" ,
			    			url:"${ctp}/permission/user/doAssign",
			    			data:$("#roleForm").serialize(),
			    			success:function(result){
			    				if(result.success){
			    					$("#rightList").append(opts); 
			    					layer.msg("分配成功",{time:1000,icon:6},function(){});
			    					
			    				}else{
			    					layer.msg("分配失败",{time:1000,icon:5,shift:6},function(){});
			    				}
			    			}
			    			
			    		});
			    		
			    	}
			    });
			    $("#right2leftBtn").click(function(){
			    	var opts = $("#rightList :selected");
			    	if(opts.length == 0){
			    		layer.msg("请选择需要取消分配的角色",{time:1000,icon:5,shift:6},function(){});
			    	}else{
			    		$.ajax({
			    			type:"POST" ,
			    			url:"${ctp}/permission/user/unAssign",
			    			data:$("#roleForm").serialize(),
			    			success:function(result){
			    				if(result.success){
			    					$("#leftList").append(opts);
			    					layer.msg("取消分配成功",{time:1000,icon:6},function(){});
			    				}else{
			    					layer.msg("取消分配失败",{time:1000,icon:5,shift:6},function(){});
			    				}
			    			}
			    			
			    		});
			    		
			    	}
			    });
            });
        </script>
	<%@include file="/WEB-INF/includes/common-js.jsp" %>
  </body>
</html>