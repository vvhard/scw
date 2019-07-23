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

<link rel="stylesheet" href="${ctp }/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctp }/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctp }/css/main.css">
<link rel="stylesheet" href="${ctp}/ztree/zTreeStyle.css">
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
									<input class="form-control has-success" type="text" placeholder="请输入查询条件" id="queryText">
								</div>
							</div>
							<button type="button" class="btn btn-warning" id="queryBtn">
								<i class="glyphicon glyphicon-search" ></i> 查询
							</button>
						</form>
						<button type="button" class="btn btn-danger" style="float: right; margin-left: 10px;" onclick="deleteTypes()">
							 <i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button type="button" class="btn btn-primary" style="float: right;" onclick="addType()">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<form id="typeForm">
								<table class="table  table-bordered">
									<thead>
										<tr>
											<th width="30">#</th>
											<th width="30"><input type="checkbox" id="allSelBox"></th>
											<th width="300">分类名称</th>
											<th>简介</th>
											<th width="100">操作</th>
										</tr>
									</thead>
									<tbody id="typeData">
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

	<script src="${ctp }/jquery/jquery-2.1.1.min.js"></script>
	<script src="${ctp }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctp }/script/docs.min.js"></script>
	<script src="${ctp}/layer/layer.js"></script>
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
				$("#typeData :checkbox").each(function() {
					this.checked = flg;
				})
			});
		});
		function pageQuery(pageno){
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
					url : "${ctp}/serviceman/pt/pageQuery",
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
									var typePage = result.data; // 每一页
									var types = typePage.datas; // 类型
									$.each(types,function(index, type) {
										tableContent += '<tr>';
										tableContent += '	<td>' + (index + 1) + '</td>';
										tableContent += '	<td><input type="checkbox" name="typeid" value="'+type.id+'"></td>';
										tableContent += '    <td>' + type.name + '</td>';
										tableContent += '    <td>' + type.description + '</td>';

										tableContent += '    <td>';
										tableContent += '	    <button type="button" onclick="goUpdatePage('
														+ type.id
														+ ')" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
										tableContent += '		<button type="button" onclick="	deleteType('
														+ type.id
														+ ',\''
														+ +type.name
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
								for (var i = 1; i <= typePage.totalno; i++) {
									if (i == pageno) {
											pageContent += '<li class="active"><a href="#" >'
														+ i + '</a></li>';
									} else {
											pageContent += '<li ><a href="#" onclick="pageQuery('
														+ i + ')">' + i + '</a></li>';
									}
								}
								if (pageno < typePage.totalno) {
											pageContent += '<li ><a href="#" onclick="pageQuery('
														+ (pageno + 1) + ')">下一页</a></li>';
								}
								$("#typeData").html(tableContent);
								$(".pagination").html(pageContent);
							} else { // 返回内容为空
								layer.msg("分页查询失败", {
									time : 1000,
									icon : 5,
									shift : 6
								}, function() {});
							}
						} // success 回调函数
					}); // ajax
		} // 初始化回调函数
		function addType(){
			window.location.href="${ctp}/serviceman/pt/add";
		}
		function goUpdatePage(typeid){
			window.location.href="${ctp}/serviceman/pt/edit?typeid="+typeid;
		}
		
		function deleteType(typeid,name){
			layer.confirm("是否删除分类【" + name + "】信息?", {
				icon : 3,
				title : '提示'
			}, function(cindex) {
				// 删除信息
				$.ajax({
					type : "POST",
					url : "${ctp}/serviceman/pt/delete",
					data : {
						"typeid" : typeid
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
		function deleteTypes(typeid,name){
			// 选中的checkbox
			var boxes = $("#typeData :checkbox:checked");
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
						url : "${ctp}/service/pt/deletes",
						data : $("#typeForm").serialize(), // 元素属性必须使用name
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
			

	</script>
	<%@include file="/WEB-INF/includes/common-js.jsp" %>
</body>
</html>
