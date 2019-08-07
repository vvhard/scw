<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="utf-8">
  <head>
    <meta charset="utf-8">
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
.seltype {
    position: absolute;
    margin-top: 70px;
    margin-left: 10px;
    color: red;
}
	</style>
  </head>
  <body>
	<%@include file="/WEB-INF/includes/nav-bar.jsp" %>
    <div class="container theme-showcase" role="main">
      <div class="page-header">
        <h1>实名认证 - 账户类型选择</h1>
      </div>
	  <div style="padding-top:10px;">
		<div class="row">
      <div class="col-xs-6 col-md-3">
      
      <h2>个人经营</h2>
        <a href="javascript:;" class="thumbnail">
          <img alt="100%x180" src="${ctp }/static/img/services-box1.jpg" data-holder-rendered="true" style="height: 180px; width: 100%; display: block;">
        </a>
      </div>
      <div class="col-xs-6 col-md-3">
        <h2>个体工商户</h2>
        <a href="javascript:;" class="thumbnail">
          <img alt="100%x180" src="${ctp }/static/img/services-box2.jpg" data-holder-rendered="true" style="height: 180px; width: 100%; display: block;">
        </a>
      </div>
      <div class="col-xs-6 col-md-3">
        <h2>商业公司</h2>
        <a href="javascript:;" class="thumbnail">
          <img alt="100%x180" src="${ctp }/static/img/services-box3.jpg" data-holder-rendered="true" style="height: 180px; width: 100%; display: block;">
        </a>
      </div>
      <div class="col-xs-6 col-md-3">
        <h2>政府及非营利组织</h2>
        <a href="javascript:;" class="thumbnail">
          <img alt="100%x180" src="${ctp }/static/img/services-box4.jpg" data-holder-rendered="true" style="height: 180px; width: 100%; display: block;">
        </a>
      </div>
    </div>
	<button type="button" class="btn btn-danger btn-lg btn-block" onclick="authApply()">认证申请</button>
    </div> <!-- /container -->
      <!-- /END THE FEATURETTES -->


        <div class="container" style="margin-top:20px;">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div id="footer">
                        <div class="footerNav">
                             <a rel="nofollow" href="http://www.atguigu.com">关于我们</a> | <a rel="nofollow" href="http://www.atguigu.com">服务条款</a> | <a rel="nofollow" href="http://www.atguigu.com">免责声明</a> | <a rel="nofollow" href="http://www.atguigu.com">网站地图</a> | <a rel="nofollow" href="http://www.atguigu.com">联系我们</a>
                        </div>
                        <div class="copyRight">
                            Copyright ?2017-2017atguigu.com 版权所有
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
    <script src="${ctp }/static/jquery/jquery-2.1.1.min.js"></script>
    <script src="${ctp }/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctp }/static/script/docs.min.js"></script>
	<script>
    $(".thumbnail").click(function(){
        $('.seltype').remove();
        $(this).prepend('<div class="glyphicon glyphicon-ok seltype"></div>');
    });
    function authApply(){
    	// 也可以在被点击的时候，使用全局变量保存
    	var acctType = $(".glyphicon-ok").parent().siblings().text();
    	window.location.href="${ctp}/member/authApply?acctType=" + acctType ;
    }
	</script>
  </body>
</html>