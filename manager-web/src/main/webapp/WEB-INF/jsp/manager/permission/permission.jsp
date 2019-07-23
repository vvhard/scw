<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("navInfo", "许可维护"); 
	pageContext.setAttribute("curUrl", "permission/perm/list"); 
	
	
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" href="${ctp}/ztree/zTreeStyle.css">

<style>
.tree li {
	list-style-type: none;
	cursor: pointer;
}
</style>
</head>

<body>

	<%@include file="/WEB-INF/includes/nav_bar.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<%@include file="/WEB-INF/includes/user_menu.jsp"%>
	        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
				  <div class="panel-heading">
					<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
				  </div>
				<div class="panel-body">
					<ul id="permissionTree" class="ztree"></ul>
				</div>
				</div>
	        </div>
		</div>
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
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
	<script src="${ctp}/ztree/jquery.ztree.all-3.5.min.js"></script>
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
		    initPermissionTree();
        });
		
		function initPermissionTree() {
			var setting = {
				data : {
					simpleData : {
						enable : true,
						idKey : "id",
						pIdKey : "pid",
					},
					key : {
						url : "haha" // 不使用url属性
					}
				},
				view : {
					//自定义显示的效果
					addDiyDom : function(treeId, treeNode) { // 用于在节点上固定显示用户自定义控件
						var icoObj = $("#" + treeNode.tId + "_ico"); // 获取到图标，treeNode.tId自动生成
						//treeNode里面有一个tId；
						//这个tid用来拼串以后就是图标显示位置的元素id和名字显示位置的元素id
						//tId:"permissionTree_3"
						//<span id="permissionTree_2_span">用户维护</span>
						//<span id="permissionTree_2_ico" ></span>
						//console.log(treeNode);
						//改图标；找到当前元素图标显示的节点，将这个节点的class设置为当前节点的icon
						if (treeNode.icon) {
							icoObj.removeClass("button ico_docu ico_open");
							icoObj.before("<span class='" + treeNode.icon  +"'></span>");
						}

					},
					// 鼠标移到节点显示自定义的图标
					addHoverDom: function(treeId, treeNode){  
					
                    //   <a><span></span></a>
						var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
						aObj.attr("href", "javascript:;");
						if (treeNode.editNameFlag || $("#btnGroup"+treeNode.tId).length>0) return;
						var s = '<span id="btnGroup'+treeNode.tId+'">';
						if ( treeNode.level == 0 ) {
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addNode('+treeNode.id+')" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
						} else if ( treeNode.level == 1 ) {
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="editNode('+treeNode.id+')" href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
							if (treeNode.children == null) {
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteNode('+treeNode.id+')" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
							}
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addNode('+treeNode.id+')" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
						} else if ( treeNode.level == 2 ) {
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="editNode('+treeNode.id+')" href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteNode('+treeNode.id+')" href="#">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
						}
		
						s += '</span>';
						aObj.after(s);
					},
					// 鼠标移走节点隐藏自定义的图标
					removeHoverDom: function(treeId, treeNode){
						$("#btnGroup"+treeNode.tId).remove();
					}
				}
			};
			//从数据库查出的所有权限节点数据
			//发送ajax请求获取到所有权限的json数据
			$.getJSON("${ctp}/permission/perm/loadData", function(nodes) {
				
				//给每一个节点修改或者添加一些属性
				$.each(nodes, function() {
					if (this.pid == 19 || this.pid == 0) {
						this.open = true;
					}
				})
				//如果不是用var声明的变量，这个变量就默认变为全局的
				//把初始化好的ztree对象传递给外界使用，可以通用操作这个对象，来改变树
				//ztree为了不影响下面的操作是异步展示数据的
				$.fn.zTree.init($("#permissionTree"), setting,nodes);
			})
		}

        function addNode(id){
        	window.location.href = "${ctp}/permission/perm/add?id=" + id;
        }
        function editNode(id){
        	window.location.href = "${ctp}/permission/perm/edit?id=" + id;
        }
        function deleteNode(id){
        	layer.confirm("是否删除",{icon:3,title:'提示'},
        			function(cindex){
        				$.ajax({
        					type:"POST",
        					url:"${ctp}/permission/perm/delete",
        					data:{id:id},
        					success:function(result){
        						if(result.success){
        							layer.msg("删除成功",{time:2000,icon:6},function(){
            							var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
            							treeObj.reAsyncChildNodes(null, "refresh");// 属性许可树
        							});

        						}else{
        							layer.msg("删除失败",{time:2000,icon:5,shift:6},function(){});
        						}
        					}
        					
        				});
        			},
        			function(cindex){layer.close(cindex)})
        }
     
	</script>
		    <%pageContext.setAttribute("curUrl", "permission/perm/list"); %>
	<%@include file="/WEB-INF/includes/common-js.jsp" %>
</body>
</html>