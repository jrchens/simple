<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <%--<link rel="icon" href="../../favicon.ico">--%>

    <title>Dashboard</title>

    <!-- Bootstrap core CSS -->
    <link href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="http://getbootstrap.com/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath }/resources/css/dashboard.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="http://getbootstrap.com/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="http://getbootstrap.com/assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <link href="http://cdn.bootcss.com/bootstrap-fileinput/4.3.6/css/fileinput.min.css" rel="stylesheet">
    
    <link href="http://cdn.bootcss.com/bootstrap-treeview/1.2.0/bootstrap-treeview.min.css" rel="stylesheet">
    
  </head>

  <body>
  
  <tiles:insertAttribute name="top"></tiles:insertAttribute>
  <div class="container-fluid">
    <div class="row">
      <tiles:insertAttribute name="left"></tiles:insertAttribute>

      <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
<%--         <c:if test="${not empty ERROR_MESSAGE }"> --%>
<%--           <div class="alert alert-danger" role="alert">${ERROR_MESSAGE }</div> --%>
<%--           <c:remove var="ERROR_MESSAGE" /> --%>
<%--         </c:if> --%>

          
          <c:if test="${not empty SUCCESS_MESSAGE }">
          <div class="alert alert-success" role="alert">${SUCCESS_MESSAGE }</div>
          <c:remove var="SUCCESS_MESSAGE"/>
          </c:if>
          
        <tiles:insertAttribute name="body"></tiles:insertAttribute>
      </div>
      
    </div>
  </div>

  <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    
    <script src="http://cdn.bootcss.com/bootstrap-fileinput/4.3.6/js/fileinput.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap-fileinput/4.3.6/js/locales/zh.min.js"></script>
    
    <%--<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>--%>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->
    <script src="http://cdn.bootcss.com/holder/2.9.4/holder.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js"></script>
    
    <script src="http://cdn.bootcss.com/twbs-pagination/1.4.1/jquery.twbsPagination.min.js"></script>
    
    <script src="http://cdn.bootcss.com/crypto-js/3.1.2/rollups/hmac-sha512.js"></script>
    
    <%--<script src="http://cdn.bootcss.com/purl/2.3.1/purl.min.js"></script>--%>
    <script src="http://cdn.bootcss.com/tinymce/4.5.2/tinymce.min.js"></script>
    <script src="http://cdn.bootcss.com/tinymce/4.5.2/plugins/image/plugin.min.js"></script>
    <script src="http://cdn.bootcss.com/tinymce/4.5.2/plugins/imagetools/plugin.min.js"></script>
    <script src="http://cdn.bootcss.com/tinymce/4.5.2/plugins/link/plugin.min.js"></script>
    
    <script src="http://cdn.bootcss.com/bootstrap-treeview/1.2.0/bootstrap-treeview.min.js"></script>
    
    <script src="http://cdn.bootcss.com/lodash.js/4.17.4/lodash.min.js"></script>
        
    <script src="${pageContext.request.contextPath }/resources/js/admin-init.js"></script>
    <script src="${pageContext.request.contextPath }/resources/js/simple-util.js"></script>
    
    <c:if test="${not empty MODULE_JS_FILE_NAME }">
    <script src="${pageContext.request.contextPath }/resources/js/${MODULE_JS_FILE_NAME }.js"></script>
    </c:if>
  </body>
</html>
