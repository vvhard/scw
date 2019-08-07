<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form role="form" style="margin-top: 20px;" enctype="multipart/form-data">
	<c:forEach items="${certs }" var="cert" varStatus="i">
		<div class="form-group">
			<label for="exampleInputEmail1">${cert.name}</label> 
			<input type="hidden" name="certid" value="${cert.id }">
			<input type="file" name="file" class="form-control"> <br> 
			<img src="">
		</div>
	</c:forEach>
	<input type="hidden" name="memberid" value="${loginUser.id }">
	<button type="button" onclick="btnClick('${ctp }/auth/basicInfo')" class="btn btn-default clickbtn">上一步</button>
	<button type="button" onclick="btnClick('${ctp }/auth/emailConfirm')" class="btn btn-success clickbtn">下一步</button>
</form>
