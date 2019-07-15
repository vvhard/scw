<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
					<div class="panel-heading" style="height: 50px;">
						<ol class="breadcrumb">
							<li><a href="${ctp }/main.html">首页</a></li>
							<li><a href="${ctp}/permission/role/list">数据列表</a></li>
							<li class="active">分配权限	</li>
						</ol>
					</div>
					<button class="btn btn-success" style="margin-left: 20px;margin-top: 10px;"  onclick="doAssign()">分配许可</button>
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
		$(function() {
			$(".list-group-item").click(function() {
				if ($(this).find("ul")) {
					$(this).toggleClass("tree-closed");
					if ($(this).hasClass("tree-closed")) {
						$("ul", this).hide("fast");
					} else {
						$("ul", this).show("fast");
					}
				}
			});
			/*
				var setting = {
				// 可选框
				check : {
					enable : true
				},
				// 异步请求数据
				async : {
					enable : true,
					url : "${ctp}/permission/role/loadAssignData?roleid=${param.id}", // ${param.id}请求域里的id
					autoParam : [ "id", "name=n", "level=lv" ] // 异步请求时，自动提交的参数
				},
				
				view : {
					selectedMulti : false,// 多选
					addDiyDom : function(treeId, treeNode) { // 用于在节点上固定显示用户自定义控件
						var icoObj = $("#" + treeNode.tId + "_ico"); // 获取到图标，treeNode.tId自动生成
						if (treeNode.icon) {
							icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.icon).css("background","");
						}

					}
				}
			};
			$.fn.zTree.init($("#permissionTree"), setting);
			*/
			var rid = ${param.id};
			initPermissionTree(rid); // 初始化权限树
		});
		
		//保存ztree对象的
		var ztreeObject;
		///传入角色id，将当前角色拥有的权限勾选
		function checkedNodes(rid) {
			$.getJSON("${ctp}/permission/role/ckeckedPermission?roleid=" + rid, function(data) {
				//查出的当前角色拥有的权限
				//ztree对象的方法；checkNode；
				//三个参数：
				//第一个参数就是要勾选的节点
				//第二个参数就是勾选与否
				//第三个参数是是否和父节点级联互动
				//第四个参数是勾选状态变化后，是否调用之前用（callback）规定的回调函数
				$.each(data, function() {
					//从ztree中获取到要勾选的对象；
					// 根据id查找节点
					var node = ztreeObject.getNodeByParam("id", this.id, null);
					ztreeObject.checkNode(node, true, false);
				})
			})
		}

		function initPermissionTree(rid) {
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
							icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.icon).css("background","");
						}

					}
				},
				check : {
					enable : true
				}
			};

			//从数据库查出的所有权限节点数据
			//发送ajax请求获取到所有权限的json数据
			$.getJSON("${ctp}/permission/role/loadAssignData?roleid="+rid, function(nodes) {
				
				//给每一个节点修改或者添加一些属性
				$.each(nodes, function() {
					if (this.pid == 19 || this.pid == 0 ) {
						this.open = true;
					}
				})
				//如果不是用var声明的变量，这个变量就默认变为全局的
				//把初始化好的ztree对象传递给外界使用，可以通用操作这个对象，来改变树
				//ztree为了不影响下面的操作是异步展示数据的
				ztreeObject = $.fn.zTree.init($("#permissionTree"), setting,nodes);
				checkedNodes(rid); // 勾选节点
			})
		}


		// 分配
		function doAssign() {
			var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
			var nodes = treeObj.getCheckedNodes(true);
			if (nodes.length == 0) {
				layer.msg("请选择要分配的许可", {
					time : 1000,
					icon : 5,
					shift : 6
				}, function() {
				});
			} else {
				var d = "roleid=${param.id}";
				$.each(nodes, function(i, node) {
					d += "&permissionids=" + node.id;
				});
				$.ajax({
					type : "POST",
					url : "${ctp}/permission/role/doAssign",
					data : d,
					success : function(result) {
						if (result.success) {
							layer.msg("分配成功", {
								time : 1000,
								icon : 6
							}, function() {
								window.location.href="${ctp}/permission/role/list";
							});
						} else {
							layer.msg("分配失败", {
								time : 1000,
								icon : 5,
								shift : 6
							}, function() {
							});
						}
					}

				});
			}
		}
	</script>
</body>
</html>