<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" href="${ctp }/static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctp }/static/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctp }/static/css/theme.css">
<style>
#footer {
	padding: 15px 0;
	background: #fff;
	border-top: 1px solid #ddd;
	text-align: center;
}
</style>
</head>
<body>
	<%@include file="/WEB-INF/includes/nav-bar.jsp"%>
	<div class="container theme-showcase" role="main">
		<div class="page-header">
			<h1>实名认证 - 申请 - ${param.acctType }</h1>
		</div>
		<ul class="nav nav-tabs" role="tablist">
			<li role="presentation" class="active">
				<a url="${ctp}/auth/basicInfo" onclick="btnClick('${ctp}/auth/basicInfo')"><span class="badge">1</span> 基本信息</a>
			</li>
			<li role="presentation">
				<a url="${ctp}/auth/certUpload" onclick="btnClick('${ctp}/auth/certUpload')"><span class="badge">2</span> 资质文件上传</a>
			</li>
			<li role="presentation">
				<a url="${ctp}/auth/emailConfirm" onclick="btnClick('${ctp}/auth/emailConfirm')"><span class="badge">3</span> 邮箱确认</a>
			</li>
			<li role="presentation">
				<a url="${ctp}/auth/applyConfirm" onclick="btnClick('${ctp}/auth/applyConfirm')"><span class="badge">4</span> 申请确认</a>
			</li>
		</ul>
		<div id="content">
			<!-- ajax动态生成内容 -->
		</div>
		<hr>
	</div>
	<!-- /container -->
	<div class="container" style="margin-top: 20px;">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="footer">
					<div class="footerNav">
						<a rel="nofollow" href="http://www.atguigu.com">关于我们</a> |
						<a rel="nofollow" href="http://www.atguigu.com">服务条款</a> |
						<a rel="nofollow" href="http://www.atguigu.com">免责声明</a> |
						<a rel="nofollow" href="http://www.atguigu.com">网站地图</a> | 
						<a rel="nofollow" href="http://www.atguigu.com">联系我们</a>
					</div>
					<div class="copyRight">Copyright ?2017-2017 atguigu.com 版权所有</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${ctp }/static/jquery/jquery-2.1.1.min.js"></script>
	<script src="${ctp }/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctp }/static/script/docs.min.js"></script>
	<script src="${ctp }/static/layer/layer.js"></script>
	<script>
		$(function(){
			$.get("${ctp}/auth/basicInfo","accttype=${param.acctType}",function(data){
				$("#content").html(data);	// 初始化页面
			});
		});
		$('#myTab a').click(function(e) {
			e.preventDefault();
			$(this).tab('show');
		});
		
		function btnClick(url){
			//var url = $(this).attr("url"); // 得到按钮包含的url
			if(url == "${ctp }/member/auth.html"){
				window.location.href = url; // 页面跳转
			}else if(url == "${ctp}/auth/emailConfirm"){				
				upload(url)


			}else{
				$.ajax({
					type:"POST",
					url:url,
					data:$("form").serialize(), // 序列化表单数据
					success:function(data){
						$("#content").empty().html(data);	// 初始化页面
						$("a[url='"+ url +"']").parent().addClass("active").siblings().removeClass("active");
					}
				});
			}
		}
		
		function upload(url){
			
				// 文件上传使用formData对象,普通ajax不可行
				// 上传部分内容
				//var fd = new FormData();
				//fd.append("name",$("#name_input"));
				// 文件取值  $("#ad_file_input")[0].files[0]，dom对象的files取得所有文件，ajax提交fd
				//fd.append("file",$("#ad_file_input")[0].files[0]);
				$.ajax({
					type:"POST",
					url:"http://localhost:8081/scw-restapi/upload", // "${ctp}/auth/upload"
 					data:new FormData($("form")[0]), // FormData适用于表单，所有数据都会提交
					processData:false, // ajax不要处理和编码这些数据，直接提交
					contentType:false, // 不适用默认数据
					dataType:"json",
					success:function(data){
						if(data.code == 1){
							layer.msg("上传成功",{time:1500,icon:6},function(){});
						}
						$.ajax({
							type:"POST",
							url:url,
							success:function(data){
								$("#content").empty().html(data);	// 初始化页面
								$("a[url='"+ url +"']").parent().addClass("active").siblings().removeClass("active");
							}
						});	
	
					},
					error:function(data){
						console.log(data);
					}
				});
		}
		

	</script>
</body>
</html>