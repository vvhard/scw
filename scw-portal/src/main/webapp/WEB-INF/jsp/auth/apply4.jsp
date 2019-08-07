<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form role="form" style="margin-top: 20px;">
	<div class="form-group">
		<label for="exampleInputEmail1">验证码</label> 
		<input type="text" name="code" class="form-control" id="exampleInputEmail1" placeholder="请输入你邮箱中接收到的验证码">
	</div>
	<button type="button" onclick="javascript:;" class="btn btn-primary clickbtn">重新发送验证码</button>
	<button type="button" onclick="btnClick('${ctp }/auth/apply')" class="btn btn-success clickbtn">申请完成</button>
</form>
