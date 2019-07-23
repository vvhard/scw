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

	<link rel="stylesheet" href="${ctp}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctp}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${ctp}/css/main.css">
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
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h1 class="page-header">控制面板</h1>
          <div class="row placeholders">
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <script src="${ctp}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${ctp}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctp}/script/docs.min.js"></script>
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
