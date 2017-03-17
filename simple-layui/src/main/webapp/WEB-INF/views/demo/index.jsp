<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>layout 后台大框架布局 - Layui</title>
<%@ include file="../common/css.jsp" %>
</head>
<body>

<%@ include file="../common/js.jsp" %>
<script>
layui.config({
    debug: true,
    base: '${WEB_CONTEXT_ROOT}/resources/js/modules/' //你的模块目录
  }).use('index'); //加载入口
</script>
</body>
</html>
