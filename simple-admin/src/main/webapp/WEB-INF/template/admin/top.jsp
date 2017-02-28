<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="${pageContext.request.contextPath }/admin/dashboard">Dashboard</a></li>
            <li><a href="#"><shiro:principal /></a></li>
            <li><a href="${pageContext.request.contextPath }/logout">Exit</a></li>
          </ul>
          <%--<form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="Search...">
          </form>--%>
        </div>
      </div>
    </nav>