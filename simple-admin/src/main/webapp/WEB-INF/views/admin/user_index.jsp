<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>

          <h2 class="sub-header">用户管理</h2>
          
          <div class="btn-group pull-right">
            <a href="${pageContext.request.contextPath }/admin/user/create" class="btn btn-success">新建</a>
          </div>
          
          <form:form commandName="user" servletRelativeAction="/admin/user" method="get" id="adminUserIndexQueryForm">
            <input type="hidden" id="currentPage" name="currentPage" value="1"/>
            <div class="input-group col-sm-4">
                <form:input path="viewname" cssClass="form-control" placeholder="请输入用户名称或显示名称" />
                <span class="input-group-btn">
                  <a class="btn btn-default" href="javascript:;" onclick="SimpleUtil.query(1,'#adminUserIndexQueryForm')"><span class="glyphicon glyphicon-search"></span></a>
                </span>
            </div>
          </form:form>
            
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>#</th>
                  <th>用户名称</th>
                  <th>显示名称</th>
                  <th>操&#8195;作</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="user" items="${pagination.records }" varStatus="stat">
                  <tr>
                    <td>${(pagination.currentPage - 1) * pagination.pageSize + stat.count }</td>
                    <td>${user.username }</td>
                    <td>${user.viewname }</td>
                    <td>
                    
                    <a data-toggle="tooltip" data-placement="top" title="删除" href="javascript:;" onclick="SimpleUtil.remove('${pageContext.request.contextPath }/admin/user/remove?username=${user.username }','#adminUserIndexQueryForm')"><span class="glyphicon glyphicon-trash"></span></a>
                    &#160;
                    <a data-toggle="tooltip" data-placement="top" title="编辑" href="${pageContext.request.contextPath }/admin/user/edit?username=${user.username }"><span class="glyphicon glyphicon-edit"></span></a>
                    &#160;
                    <a data-toggle="tooltip" data-placement="top" title="密码重置" href="javascript:;" onclick="AdminUser.resetPassword('${pageContext.request.contextPath }/admin/user/resetPassword?username=${user.username }','#adminUserIndexQueryForm')"><span class="glyphicon glyphicon-random"></span></a>
                    
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          
            <nav>
              <ul id="adminUserIndexPagination" currentPage="${pagination.currentPage }" totalPage="${pagination.totalPage }" class="pagination pull-right" style="margin:0"></ul>
            </nav>
          </div>
          
