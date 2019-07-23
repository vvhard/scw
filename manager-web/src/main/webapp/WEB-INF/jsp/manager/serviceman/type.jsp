<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("navInfo", "分类管理");
	pageContext.setAttribute("curUrl", "serviceman/cert/type_list"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${ctp }/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctp }/css/font-awesome.min.css">
	<link rel="stylesheet" href="${ctp }/css/main.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
    
    input[type=checkbox] {
        width:18px;
        height:18px;        
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
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据矩阵</h3>
			  </div>
			  <div class="panel-body">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                   	<th>名称</th>
                	<c:forEach items="${certs}" var="cert">
                	  <th >${cert.name }</th>
                	</c:forEach>
                	<th>操作</th>
                </tr>
              </thead>
              <tbody>
              	<c:forEach items="${acctTypes }" var="acctType" >
	              	<tr class="${acctType.id }"><!-- 通过class属性获取被选中的checkbox -->
	                  <td>${acctType.name}</td>
		              <c:forEach items="${certs }" var="cert" varStatus="i">
	                	  <td><input type="checkbox" value="${cert.id }" ${acctType.certList[i.index] == true ?"checked=\"true\"":""} ></td>
	                  </c:forEach>
	                  <td> 
	                  	<button type="button" onclick="modify(${acctType.id})" class="btn btn-primary btn-xs">
	                  		<i class=" glyphicon glyphicon-check"></i>
	                  	</button>
	                  </td>
	                </tr>
              	</c:forEach>
              </tbody>
            </table>
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
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
            });
            function modify(acctTypeId){
            	var c = acctTypeId;
            	var s = "." + c + " :checkbox:checked" ;
    			var boxes = $(s);
    			var certid = new Array();
    			$.each(boxes,function(index,box){
    				certid[index] = box.value; // 取得复选框的值，即资质id
    			})
    			if (boxes.length == 0) {
    				layer.msg("请至少勾选一个资质", {
    					time : 1000,
    					icon : 5,
    					shift : 6
    				}, function() {});
        			return ;
    			}
            	$.ajax({
            		type:"POST",
            		url:"${ctp}/serviceman/cert/type_update",
            		data:{"accttypeid":acctTypeId,"certids":certid},
            		// 指定参数序列化时比进行深度序列化，通过JSON.stringify()将参数作为数组传递到后台，后台不能通过获取参数名的方式获取参数，需要通过inputstream流来读取参数。
            		// 如果不做traditional:true的设置，参数传递中是这样子参数名certids后面加入了[]，服务端是无法通过参数名获取参数的：
            		traditional:true, 
					success : function(result) {
						if (result.success) {
							layer.msg("更新成功",{time:1500,icon:6},function(){
    							// 回调函数做页面跳转,跳转到列表
								window.location.href="${ctp}/serviceman/cert/type_list";
    						});
						} else {
							layer.msg("更新失败", {
								time : 1500,
								icon : 5,
								shift : 6
							}, function() {
								window.location.href="${ctp}/serviceman/cert/type_list";
							});
						}
					}
            		
            	});
            }
        </script>
		<%@include file="/WEB-INF/includes/common-js.jsp" %>
  </body>
</html>
