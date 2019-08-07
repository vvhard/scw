<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="navbar-wrapper">
	<div class="container">
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="index.html" style="font-size: 32px;">尚筹网-创意产品众筹平台</a>
				</div>
				<div id="navbar" class="navbar-collapse collapse"
					style="float: right;">
					<c:if test="${!empty loginUser }">
						<ul class="nav navbar-nav">
							<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
								<i class="glyphicon glyphicon-user"></i>${loginUser.username}<span class="caret"></span></a>
								<ul class="dropdown-menu" role="menu">
									<li><a href="${ctp }/member/main.html"><i class="glyphicon glyphicon-scale"></i> 会员中心</a></li>
									<li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
									<li class="divider"></li>
									<li><a href="${ctp }/member/logout"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
								</ul></li>
						</ul>
					</c:if>
					<c:if test="${empty loginUser }">
						<ul class="nav navbar-nav navbar-right">
							<li><a href="${ctp }/login.jsp">登录</a></li>
							<li><a href="${ctp }/reg.jsp">注册</a></li>
						</ul>
					</c:if>

				</div>
			</div>
		</nav>
	</div>
</div>