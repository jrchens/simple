<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>layout 后台大框架布局 - Layui</title>
<%@ include file="./common/css.jsp" %>
</head>
<body>
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <!-- 头部区域（可配合layui已有的水平导航） -->
  </div>
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
    </div>
  </div>
  <div class="layui-body">
    <!-- 内容主体区域 -->
  </div>
  <div class="layui-footer">
    <!-- 底部固定区域 -->
  </div>
</div>
<%@ include file="./common/js.jsp" %>
<script type="text/javascript" src="${WEB_CONTEXT_ROOT}/resources/js/index.js"></script>
</body>
</html>
