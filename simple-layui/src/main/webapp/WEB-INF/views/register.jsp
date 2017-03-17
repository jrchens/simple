<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>register</title>
<%@ include file="./common/css.jsp" %>
</head>
<body>

<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
  <ul class="layui-tab-title">
    <li class="layui-this">登入</li>
    <li>注册</li>
  </ul>
  <div class="layui-tab-content">
    <div class="layui-tab-item layui-show">
      <form class="layui-form layui-form-pane" action="${WEB_CONTEXT_ROOT }/login" method="post">
      
        <div class="layui-form-item">
          <label class="layui-form-label">用户名</label>
          <div class="layui-input-inline">
            <input type="text" name="username" required lay-verify="required" autocomplete="off" class="layui-input">
          </div>
        </div>
        
        <div class="layui-form-item">
          <label class="layui-form-label">密&#12288;码</label>
          <div class="layui-input-inline">
            <input type="password" name="password" required lay-verify="required" autocomplete="off" class="layui-input">
          </div>
        </div>
        
        <div class="layui-form-item">
          <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="*">立即登入</button>
          </div>
        </div>
        
      </form>
    </div>
    
    <div class="layui-tab-item">
      <form class="layui-form layui-form-pane" action="${WEB_CONTEXT_ROOT }/register" method="post">
      
        <div class="layui-form-item">
          <label class="layui-form-label">用户名</label>
          <div class="layui-input-inline">
            <input type="text" name="username" required lay-verify="required" autocomplete="off" class="layui-input">
          </div>
        </div>
        
        <div class="layui-form-item">
          <label class="layui-form-label">密&#12288;码</label>
          <div class="layui-input-inline">
            <input type="password" name="password" required lay-verify="required" autocomplete="off" class="layui-input">
          </div>
        </div>
        
        <div class="layui-form-item">
          <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formRegister">立即注册</button>
          </div>
        </div>
        
      </form>
    
    </div>
  
  </div>
</div>

<%@ include file="./common/js.jsp" %>
<script type="text/javascript">
layui.config({
  base: '${WEB_CONTEXT_ROOT}/resources/js/'
}).use('register');
</script>

</body>
</html>
