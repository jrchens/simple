<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-Hans">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="http://getbootstrap.com/favicon.ico">

    <title>User Login</title>

    <!-- Bootstrap core CSS -->
    <link href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="http://getbootstrap.com/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath }/resources/css/signin.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="http://getbootstrap.com/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="http://getbootstrap.com/assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    
    <div class="container">
    
    <c:if test="${not empty error }">
    <div class="alert alert-danger" role="alert">${error }</div>
    <c:remove var="error"/>
    </c:if>

      <form class="form-signin" id="loginForm" action="${pageContext.request.contextPath }/login" method="post">
        <h2 class="form-signin-heading">User Login</h2>
        <label for="username" class="sr-only">Username</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus>
        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
        <div class="checkbox">
          <label>
            <input type="checkbox" name="rememberMe"> Remember me
          </label>
        </div>
        <a class="btn btn-lg btn-primary btn-block" type="submit" onclick="{
          var _form = jQuery('#loginForm');
          var _username = jQuery('#username',_form).val();
          var _password = jQuery('#password',_form).val();
          jQuery('#password',_form).val(CryptoJS.HmacSHA512(_password,_username).toString());
          _form.submit();
        }">Login</a>
      </form>

    </div> <!-- /container -->


    <script src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js"></script>
    
    <script src="http://cdn.bootcss.com/crypto-js/3.1.2/rollups/hmac-sha512.js"></script>
  </body>
</html>
