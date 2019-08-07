<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form role="form" style="margin-top: 20px;">
	<div class="form-group">
		<label for="exampleInputEmail1">邮箱地址</label> 
		<input type="text" class="form-control" name="email" id="exampleInputEmail1" placeholder="请输入用于接收验证码的邮箱地址" value="${loginUser.email }">
	</div>
	<button type="button" onclick="btnClick('${ctp }/auth/certUpload')" class="btn btn-default clickbtn">上一步</button>
	<button type="button" onclick="btnClick('${ctp }/auth/applyConfirm')" class="btn btn-success clickbtn">下一步</button>
</form>
