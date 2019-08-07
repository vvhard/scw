<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("navInfo", "实名认证审核");
	pageContext.setAttribute("curUrl", "audi/auth/list");
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
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
	</style>
  </head>

  <body>
	<%@include file="/WEB-INF/includes/nav_bar.jsp" %>

    <div class="container-fluid">
      <div class="row">
	<%@include file="/WEB-INF/includes/user_menu.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
		<form class="form-inline" role="form" style="float:left;">
		  <div class="form-group has-feedback">
		    <div class="input-group">
		      <div class="input-group-addon">查询条件</div>
		      <input class="form-control has-success" id="queryText" type="text" placeholder="请输入查询条件">
		    </div>
		  </div>
		  <button type="button" id="queryBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
		</form>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
			 	  <th>申请用户</th>
                  <th>真实姓名</th>
                  <th>身份证号</th>
                  <th>电话号码</th>
                  <th>账号类型</th>
                  <th>资质文件</th>
                  <th>流程名称</th>
                  <th>流程版本</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody id="authData">
              </tbody>
			  <tfoot>
			     <tr >
					<td colspan="12" align="center">
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
			url : "${ctp}/audi/auth/pageQuery",
			data : jsonData,
			beforeSend : function() {loadingIndex = layer.msg("处理中", {icon : 16});},
			success : function(result) {
					layer.close(loadingIndex);
					// 返回内容不为空
					if (result.success) {
					var tableContent = "";
					var pageContent = "";
					var page = result.data; // 每一页
					var certs = page.datas; // 用户
					$.each(certs,function(index, cert) {
						tableContent += '<tr id="'+ cert.memberid +'">';
						tableContent += '	<td>'+ (index + 1) + '</td>';
						tableContent += '	<td>'+  cert.username   +'</td>';
						tableContent += '   <td>'+  cert.realname  + '</td>';
						tableContent += '   <td>'+  cert.cardnum  + '</td>';
						tableContent += '   <td>'+  cert.tel  + '</td>';
						tableContent += '   <td>'+  cert.accttype  + '</td>';
						tableContent += '   <td><button type="button" class="btn btn-success btn-xs" onclick="">查看资质文件</button></td>';
						tableContent += '   <td>'+  cert.procname  + '</td>';
						tableContent += '   <td>'+  cert.version  + '</td>';
						tableContent += '   <td>';
						tableContent += '	    <button type="button"  onclick="hasChecked(' + cert.memberid + ')" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
						tableContent += '		<button type="button" onclick="unChecked(' + cert.memberid + ')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
						tableContent += '	</td>';
						tableContent += '</tr>';
					});

					// 动态生成分页导航
					if (pageno > 1) {
						pageContent += '<li><a href="#" onclick="pageQuery('
								    + (pageno - 1) + ')">上一页</a></li>';
					}
					// 循环生成1 2 3 4 5 等等
					for (var i = 1; i <= page.totalno; i++) {
						if (i == pageno) {
							pageContent += '<li class="active"><a href="#" >'
										+ i + '</a></li>';
						} else {
							pageContent += '<li ><a href="#" onclick="pageQuery('
										+ i + ')">' + i + '</a></li>';
						}
					}
					if (pageno < page.totalno) {
						pageContent += '<li ><a href="#" onclick="pageQuery('
									+ (pageno + 1) + ')">下一页</a></li>';
					}
					$("#authData").html(tableContent);
					$(".pagination").html(pageContent);
				} else { // 返回内容为空
					layer.msg("分页查询失败", {time : 2000,icon : 5,shift : 6}, function() {});
				}
			} // SUCCESS
		}); // AJAX
	} // 异步分页查询
	
	// 完成审核
	function hasChecked(memberid){
		layer.confirm("是否确定审核通过?",{icon : 3,title : '提示'},
				function(){
					$.ajax({
						type:"POST",
						url:"${ctp}/audi/auth/checked",
						data:{"memberid":memberid} ,
						success:function(result){
							if(result.success){
								layer.msg("已完成",{time:1500,icon:6},function(){
									pageQuery(1);
								})
								//$("#authData").empty();
								//$(".pagination").empty();
								//pageQuery(1);
							}else{
								layer.msg("请求失败，请稍后重试",{time:1500,icon:5,shitf:6},function(){})
							}
						}
						
					});
				},
				function(cindex) {
					layer.close(cindex);
				}
		);
	}
	// 审核不通过
	function unChecked(memberid){
		layer.confirm("是否确定审核不予通过?",{icon : 3,title : '提示'},
				function(){
					$.ajax({
						type:"POST",
						url:"${ctp}/audi/auth/unchecked",
						data:{"memberid":memberid},
						success:function(result){
							if(result.success){
								layer.msg("已完成",{time:1500,icon:6},function(){})
								$("#authData").empty();
								$(".pagination").empty();
								pageQuery(1);
							}else{
								layer.msg("请求失败，请稍后重试",{time:1500,icon:5,shitf:6},function(){})
							}
						}
						
					});
				},
				function(cindex) {
					layer.close(cindex);
				}
		);
	}

	</script>
	<%@include file="/WEB-INF/includes/common-js.jsp"%>
  </body>
</html>
