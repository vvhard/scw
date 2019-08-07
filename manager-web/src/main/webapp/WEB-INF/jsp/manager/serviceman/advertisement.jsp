<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("navInfo", "广告管理");
	pageContext.setAttribute("curUrl", "serviceman/adv/list");
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
									<input class="form-control has-success" type="text" placeholder="请输入查询条件">
								</div>
							</div>
							<button type="button" id="queryBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" id="addBtn" class="btn btn-primary" style="float: right;">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th>广告描述</th>
										<th>状态</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody id="advData">
								</tbody>
		
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<ul class="pagination"><!-- 内容通过ajax请求动态生成 --></ul>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 广告添加的模态框 -->
	<div class="modal fade" id="ad_add_model" tabindex="-1" role="dialog"aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">添加广告</h4>
				</div>
				<div class="modal-body">
					<form action="${ctp }/serviceman/adv/upload" id="ad_form" method="post" enctype="multipart/form-data">
						<div class="form-group">
							<label>广告名</label> 
							<input type="text" class="form-control" id="ad_name_input" name="name" placeholder="广告名">
						</div>
						<div class="form-group">
							<label>选择广告</label> 
							<input type="file" name="ad" id="ad_file_input">
						</div>
						<div class="form-group">
							<!--选择的文件展示位  -->
							<div class="row">
								<div class="col-md-12 imgdiv">
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="submitBtn">确定</button>
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
		$(function() {
			$(".list-group-item").click(function() {
				// jquery对象的回调方法中的this关键字为DOM对象
				// $(DOM) ==> JQuery
				if ($(this).find("ul")) { // 3 li
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

		});
		//点击新增弹出广告添加的模态框
		$("#addBtn").click(function() {
			$("#ad_add_model").find(".imgdiv").empty();
			$("#ad_add_model").modal("show");
		});
		$("#submitBtn").click(function(){
			// 文件上传使用formData对象,普通ajax不可行
			// 上传部分内容
			//var fd = new FormData();
			//fd.append("name",$("#name_input"));
			// 文件取值  $("#ad_file_input")[0].files[0]，dom对象的files取得所有文件，ajax提交fd
			//fd.append("file",$("#ad_file_input")[0].files[0]);
			$.ajax({
				type:"POST",
				url:"${ctp}/serviceman/adv/upload",
				data:new FormData($("#ad_form")[0]), // FormData适用于表单，所有数据都会提交
				processData:false, // ajax不要处理和编码这些数据，直接提交
				contentType:false, // 不适用默认数据
				success:function(result){
					if (result.success) {
						layer.msg("新增成功",{time:1500,icon:6},function(){
							// 回调函数做页面跳转,跳转到列表
							$("#ad_add_model").modal("hide");
							$("#advData").empty();
							$(".pagination").empty();
							pageQuery(1); // 利用ajax刷新数据，不刷新页面	
						});
					} else {
						layer.msg("新增失败", {
							time : 1500,
							icon : 5,
							shift : 6
						}, function() {
							
						});
					}
				}
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
			$
					.ajax({
						type : "POST",
						url : "${ctp}/serviceman/adv/pageQuery",
						data : jsonData,
						beforeSend : function() {
							loadingIndex = layer.msg("处理中", {
								icon : 16
							});
						},
						success : function(result) {
							layer.close(loadingIndex);
							// 返回内容不为空
							if (result.success) {
								var tableContent = "";
								var pageContent = "";
								var advPage = result.data; // 每一页
								var advs = advPage.datas; // 用户
								$.each(advs,function(index, adv) {
													tableContent += '<tr>';
													tableContent += '	<td>'
															+ (index + 1)
															+ '</td>';
													tableContent += '	<td>'+adv.name+'</td>';
													tableContent += '    <td>'
															+ adv.status
															+ '</td>';
													tableContent += '    <td>';
													tableContent += '	    <button type="button"  onclick="goCkeckPage('
															+ adv.id
															+ ')" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
													tableContent += '	    <button type="button" onclick="goUpdatePage('
															+ adv.id
															+ ')" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
													tableContent += '		<button type="button" onclick="deleteAdv('
															+ adv.id
															+ ',\''
															+ adv.name
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
								for (var i = 1; i <= advPage.totalno; i++) {
									if (i == pageno) {
										pageContent += '<li class="active"><a href="#" >'
												+ i + '</a></li>';
									} else {
										pageContent += '<li ><a href="#" onclick="pageQuery('
												+ i + ')">' + i + '</a></li>';
									}
								}
								if (pageno < advPage.totalno) {
									pageContent += '<li ><a href="#" onclick="pageQuery('
											+ (pageno + 1) + ')">下一页</a></li>';
								}
								$("#advData").html(tableContent);
								$(".pagination").html(pageContent);
							} else { // 返回内容为空
								layer.msg("分页查询失败", {
									time : 2000,
									icon : 5,
									shift : 6
								}, function() {
								});
							}
						}
					})
		}

		// 修改用户页面
		function goUpdatePage(id) {
			window.location.href = "${ctp}/serviceman/adv/edit?id=" + id;
		}

		// 删除用户
		function deleteAdv(id, name) {
			layer.confirm("是否删除广告【" + name + "】信息?", {
				icon : 3,
				title : '提示'
			}, function(cindex) {
				// 删除信息
				$.ajax({
					type : "POST",
					url : "${ctp}/serviceman/adv/delete",
					data : {
						id : id
					},
					success : function(result) {
						if (result.success) {
							layer.msg("删除成功", {
								time : 2000,
								icon : 6
							}, function() {
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


		function goCheckPage(id) {
			window.location.href = "${ctp}/serviceman/adv/check?id=" + id;
		}
	</script>
	<%@include file="/WEB-INF/includes/common-js.jsp"%>
</body>
</html>
