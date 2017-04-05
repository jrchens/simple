<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
￼<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="zh-Hans">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Code Generate</title>
</head>
<body>
<form:form commandName="conInfo" method="POST" servletRelativeAction="login">
<form:input path="con_name"/>
<form:input path="con_ip"/>
<form:input path="con_port"/>
<form:input path="con_db"/>
<form:input path="con_username"/>
<form:input path="con_password"/>
<input type="submit">
</form:form>
</body>
</html>