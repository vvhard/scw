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
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input id="queryText" class="form-control has-success" type="text" placeholder="请输入查询条件">
								</div>
							</div>
							<button type="button" id="queryBtn" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;" onclick="deleteUsers()">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button type="button" class="btn btn-primary"
							style="float: right;"
							onclick="window.location.href='${ctp}/permission/user/add'">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<form id="userForm">
								<table class="table  table-bordered">
									<thead>
										<tr>
											<th width="30">#</th>
											<th width="30"><input type="checkbox" id="allSelBox"></th>
											<th>账号</th>
											<th>名称</th>
											<th>邮箱地址</th>
											<th width="100">操作</th>
										</tr>
									</thead>
									<tbody id="userData">
									<!-- 内容通过ajax请求动态生成 -->
									</tbody>
									<tfoot>
										<tr>
											<td colspan="6" align="center">
												<ul class="pagination"><!-- 内容通过ajax请求动态生成 --></ul>
											</td>
										</tr>
									</tfoot>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="${ctp }/static/jquery/jquery-2.1.1.min.js"></script>
	<script src="${ctp }/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctp}/static/script/docs.min.js"></script>
	<script src="${ctp}/static/layer/layer.js"></script>
	<script type="text/javascript">
		var likeflg = false;
		$(function() {
			
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
			
			
			
			// 页面加载完成后，查询第一页
			pageQuery(1); 
			// 给查询按钮绑定事件处理
			$("#queryBtn").click(function() {
				// 获取查询输入框内容，不为空时使用模糊查询
				var queryText = $("#queryText").val();
				if (queryText == "") {
					likeflg = false;
				} else {
					likeflg = true;
				}
				pageQuery(1);
			});
			// 全选框绑定点击事件
			$("#allSelBox").click(function() {
				// 记录选择框状态
				var flg = this.checked;
				$("#userData :checkbox").each(function() {
					this.checked = flg;
				})
			});

		});

		
		// 异步分页查询
		function pageQuery(pageno) {
			var loadingIndex = null;
			var jsonData = {
				"pageno" : pageno,
				"pagesize" : 10
			};
			// 拼接查询条件
			if (likeflg == true) {
				jsonData.queryText = $("#queryText").val();
			}
			$.ajax({
					type : "POST",
					url : "${ctp}/permission/user/pageQuery",
					data : jsonData,
					beforeSend : function() {
									loadingIndex = layer.msg("处理中", {icon : 16});
								},
					success : function(result) {
								layer.close(loadingIndex);
								// 返回内容不为空
								if (result.success) {
									var tableContent = "";
									var pageContent = "";
									var userPage = result.data; // 每一页
									var users = userPage.datas; // 用户
									$.each(users,function(index, user) {
										tableContent += '<tr>';
										tableContent += '	<td>' + (index + 1) + '</td>';
										tableContent += '	<td><input type="checkbox" name="userid" value="'+user.id+'"></td>';
										tableContent += '    <td>' + user.loginacct + '</td>';
										tableContent += '    <td>' + user.username + '</td>';
										tableContent += '    <td>' + user.email + '</td>';
										tableContent += '    <td>';
										tableContent += '	    <button type="button"  onclick="goAssignPage('
														+ user.id
														+ ')" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
										tableContent += '	    <button type="button" onclick="goUpdatePage('
														+ user.id
														+ ')" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
										tableContent += '		<button type="button" onclick="deleteUser('
														+ user.id
														+ ',\''
														+ user.loginacct
														+ '\')"class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
										tableContent += '	</td>';
										tableContent += '</tr>';
									});
								
									// 动态生成分页导航
								if (pageno > 1) {
										pageContent += '<li><a href="#" onclick="pageQuery('
														+ (pageno - 1) + ')">上一页</a></li>';
								}
									// 循环生成1 2 3 4 5 等等
								for (var i = 1; i <= userPage.totalno; i++) {
									if (i == pageno) {
											pageContent += '<li class="active"><a href="#" >'
														+ i + '</a></li>';
									} else {
											pageContent += '<li ><a href="#" onclick="pageQuery('
														+ i + ')">' + i + '</a></li>';
									}
								}
								if (pageno < userPage.totalno) {
											pageContent += '<li ><a href="#" onclick="pageQuery('
														+ (pageno + 1) + ')">下一页</a></li>';
								}
								$("#userData").html(tableContent);
								$(".pagination").html(pageContent);
							} else { // 返回内容为空
								layer.msg("分页查询失败", {
									time : 2000,
									icon : 5,
									shift : 6
								}, function() {});
							}
						}
					})
		}

		// 修改用户页面
		function goUpdatePage(id) {
			window.location.href = "${ctp}/permission/user/edit?id=" + id;
		}
		
		// 删除用户
		function deleteUser(id, loginacct) {
			layer.confirm("是否删除用户【" + loginacct + "】信息?", {
				icon : 3,
				title : '提示'
			}, function(cindex) {
				// 删除信息
				$.ajax({
					type : "POST",
					url : "${ctp}/permission/user/delete",
					data : {
						id : id
					},
					success : function(result) {
						if (result.success) {
							layer.msg("删除成功",{time:2000,icon:6},function(){
    							// 回调函数做页面跳转,跳转到列表
								pageQuery(1);
    						});
						} else {
							layer.msg("删除失败", {
								time : 2000,
								icon : 5,
								shift : 6
							}, function() {
							});
						}

					}
				});
			}, function(cindex) {
				layer.close(cindex);
			});
		}
		// 删除多个用户
		function deleteUsers() {
			// 选中的checkbox
			var boxes = $("#userData :checkbox:checked");
			if (boxes.length == 0) {
				layer.msg("请选择删除的用户", {
					time : 2000,
					icon : 5,
					shift : 6
				}, function() {});
			} else {
				layer.confirm("是否删除选中用户信息?", {
					icon : 3,
					title : '提示'
				}, function(cindex) {
					// 删除信息
					$.ajax({
						type : "POST",
						url : "${ctp}/permission/user/deletes",
						data : $("#userForm").serialize(), // 元素属性必须使用name
						success : function(result) {
							if (result.success) {
								layer.msg("删除成功",{time:2000,icon:6},function(){
        							// 回调函数做页面跳转,跳转到列表
									pageQuery(1);
        						});
							} else {
								layer.msg("删除失败", {
									time : 2000,
									icon : 5,
									shift : 6
								}, function() {});
							}
						}
					});
					layer.close(cindex);
				}, function(cindex) {
					layer.close(cindex);
				});
			}
		}
		// 分配角色
		function goAssignPage(id) {
			window.location.href = "${ctp}/permission/user/assign?id=" + id;
		}
	</script>
	<%@include file="/WEB-INF/includes/common-js.jsp" %>
</body>
</html>
