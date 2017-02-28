<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>

          <h2 class="sub-header">群组管理</h2>
          
          <div class="btn-group pull-right">
            <a href="${pageContext.request.contextPath }/admin/group/create" class="btn btn-success">新建</a>
          </div>
          
          <form:form commandName="group" servletRelativeAction="/admin/group" method="get" id="adminGroupIndexQueryForm">
            <input type="hidden" id="currentPage" name="currentPage" value="1"/>
            <div class="input-group col-sm-4">
                <form:input path="viewname" cssClass="form-control" placeholder="请输入群组名称或显示名称" />
                <span class="input-group-btn">
                  <a class="btn btn-default" href="javascript:;" onclick="SimpleUtil.query(1,'#adminGroupIndexQueryForm')"><span class="glyphicon glyphicon-search"></span></a>
                </span>
            </div>
          </form:form>
            
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>#</th>
                  <th>群组名称</th>
                  <th>显示名称</th>
                  <th>操&#8195;作</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="group" items="${pagination.records }" varStatus="stat">
                  <tr>
                    <td>${(pagination.currentPage - 1) * pagination.pageSize + stat.count }</td>
                    <td>${group.groupname }</td>
                    <td>${group.viewname }</td>
                    <td>
                    
                    <a data-toggle="tooltip" data-placement="top" title="删除" href="javascript:;" onclick="SimpleUtil.remove('${pageContext.request.contextPath }/admin/group/remove?groupname=${group.groupname }','#adminGroupIndexQueryForm')"><span class="glyphicon glyphicon-trash"></span></a>
                    &#160;
                    <a data-toggle="tooltip" data-placement="top" title="编辑" href="${pageContext.request.contextPath }/admin/group/edit?groupname=${group.groupname }"><span class="glyphicon glyphicon-edit"></span></a>
                    <%--&#160;
                    <a data-toggle="tooltip" data-placement="top" title="权限管理" href="${pageContext.request.contextPath }/admin/group/edit?groupname=${group.groupname }"><span class="glyphicon glyphicon-magnet"></span></a>
                    --%>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          
            <nav>
              <ul id="adminGroupIndexPagination" currentPage="${pagination.currentPage }" totalPage="${pagination.totalPage }" class="pagination pull-right" style="margin:0"></ul>
            </nav>
          </div>
          
