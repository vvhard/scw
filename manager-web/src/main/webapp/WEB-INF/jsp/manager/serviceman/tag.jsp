<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("navInfo", "项目标签");
	pageContext.setAttribute("curUrl", "serviceman/tag/list"); 
%>
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
<link rel="stylesheet" href="${ctp }/ztree/zTreeStyle.css">
<style>
.tree li {
	list-style-type: none;
	cursor: pointer;
}
</style>
</head>

<body>

	<%@ include file="/WEB-INF/includes/nav_bar.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/includes/user_menu.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<i class="glyphicon glyphicon-th-list"></i> 项目标签列表
					</div>
					<div class="panel-body">
						<ul id="tagTree" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${ctp }/jquery/jquery-2.1.1.min.js"></script>
	<script src="${ctp }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctp }/script/docs.min.js"></script>
	<script src="${ctp }/ztree/jquery.ztree.all-3.5.min.js"></script>
	<script src="${ctp}/layer/layer.js"></script>
	<script type="text/javascript">
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
			initTagTree();
		});

		function initTagTree(){
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
						selectedMulti : false,
						addDiyDom : function(treeId, treeNode) {
							var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
							if (treeNode.icon) {
								icoObj.removeClass("button ico_docu ico_open");
								icoObj.before("<span class='" + treeNode.icon  +"'></span>");
							}
						},
						addHoverDom : function(treeId, treeNode) {
							var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
							aObj.attr("href", "javascript:;");
							if (treeNode.editNameFlag
									|| $("#btnGroup" + treeNode.tId).length > 0)
								return;
							var s = '<span id="btnGroup'+treeNode.tId+'">';
							if (treeNode.level == 0) {
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="javascript:;" onclick="addTag('+ treeNode.id +')">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
							} else if (treeNode.level == 1) {
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="javascript:;" onclick="editTag('+ treeNode.id +')">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
								
								if (treeNode.children == null) {
									s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="javascript:;" onclick="deleteTag('+ treeNode.id +')">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
								}
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="javascript:;" onclick="addTag('+ treeNode.id +')">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
							} else if (treeNode.level == 2) {
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="javascript:;" onclick="editTag('+ treeNode.id +')">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="javascript:;" onclick="deleteTag('+ treeNode.id +')">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
							}

							s += '</span>';
							aObj.after(s);
						},
						removeHoverDom : function(treeId, treeNode) {
							$("#btnGroup" + treeNode.tId).remove();
						}
					} // view
				
				}; // setting
				//$.fn.zTree.init($("#treeDemo"), setting); //异步访问数据,但返回数据到前端时需要自己封装好格式
				//从数据库查出的所有权限节点数据
				//发送ajax请求获取到所有权限的json数据
				$.getJSON("${ctp}/serviceman/tag/loadData", function(nodes) {
					
					//给每一个节点修改或者添加一些属性
					$.each(nodes, function() {
						if (this.pid == 1 || this.pid == 0) {
							this.open = true;
						}
						
					});
					//ztree为了不影响下面的操作是异步展示数据的
					$.fn.zTree.init($("#tagTree"), setting,nodes);
				});
			
		}
		
		function editTag(id){
			window.location.href="${ctp}/serviceman/tag/edit?id=" + id;
		}
		function addTag(id){
			window.location.href="${ctp}/serviceman/tag/add?pid=" + id;
		}
		function deleteTag(id){
			layer.confirm("是否删除",{icon:3,title:'提示'},
        			function(cindex){
        				$.ajax({
        					type:"POST",
        					url:"${ctp}/serviceman/tag/delete",
        					data:{id:id},
        					success:function(result){
        						if(result.success){
        							layer.msg("删除成功",{time:2000,icon:6},function(){
            							window.location.href="${ctp}/serviceman/tag/list";
        							});

        						}else{
        							layer.msg("删除失败",{time:2000,icon:5,shift:6},function(){});
        						}
        					}
        					
        				});
        			},
        			function(cindex){
        				layer.close(cindex);
        			}); // layer
		}
		
		
	</script>
	<%@include file="/WEB-INF/includes/common-js.jsp" %>
</body>
</html>
