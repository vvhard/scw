<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("navInfo", "控制面板");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="UTF-8">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${ctp}/static/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctp}/static/css/font-awesome.min.css">
	<link rel="stylesheet" href="${ctp}/static/css/main.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	.tree-closed {
	    height : 40px;
	}
	.tree-expanded {
	    height : auto;
	}
	</style>
  </head>

  <body>

  	<%@ include file="/WEB-INF/includes/nav_bar.jsp" %>
    <div class="container-fluid">
      <div class="row">
		<%@ include file="/WEB-INF/includes/user_menu.jsp" %>
        <%@ include file="/WEB-INF/includes/controllpane.jsp" %>
      </div>
    </div>
    <script src="${ctp}/static/jquery/jquery-2.1.1.min.js"></script>
    <script src="${ctp}/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctp}/static/script/docs.min.js"></script>
        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
                    // jquery对象的回调方法中的this关键字为DOM对象
                    // $(DOM) ==> JQuery
				    if ( $(this).find("ul") ) { // 3 li
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
			    // 用户菜单展开状态
			    function exspanTree(url){
				    $("a[href='${ctp}/permission/user/list']").css("color","red");
				    $("a[href='${ctp}/permission/user/list']").parents(".list-group-item").removeClass("tree-closed");
				    $("a[href='${ctp}/permission/user/list']").parent.parent("ul").show(100);
			    }

			    
            });
            
        </script>
  </body>
</html>
