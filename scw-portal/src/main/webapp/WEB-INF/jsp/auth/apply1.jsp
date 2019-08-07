<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form role="form" style="margin-top: 20px;">
	<c:if test="${param.accttype != null }">
		<input type="hidden" name="accttype" value="${param.accttype }">
	</c:if>
	<c:if test="${param.accttype == null }">
		<input type="hidden" name="accttype" value="${basicInfo.accttype }">
	</c:if>
	<div class="form-group">
		<label for="realname">真实名称</label> 
		<input type="text" class="form-control" name="realname" placeholder="请输入真实名称" value="${basicInfo.realname }">
	</div>
	<div class="form-group">
		<label for="cardnum">身份证号码</label> 
		<input type="text" class="form-control" name="cardnum" placeholder="请输入身份证号码" value="${basicInfo.cardnum }">
	</div>
	<div class="form-group">
		<label for="tel">电话号码</label> 
		<input type="text" class="form-control" name="tel" placeholder="请输入电话号码" value="${basicInfo.tel }">
	</div>
	<!-- 自定义url属性 -->
	<button type="button" onclick="btnClick('${ctp }/member/auth.html')" class="btn btn-default clickbtn">上一步</button>
	<button type="button" onclick="btnClick('${ctp }/auth/certUpload')" class="btn btn-success clickbtn">下一步</button>
</form>