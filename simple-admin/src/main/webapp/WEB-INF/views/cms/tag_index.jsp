<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>

          <h2 class="sub-header">标签管理</h2>
          
          <div class="btn-group pull-right">
            <a href="${pageContext.request.contextPath }/cms/tag/create" class="btn btn-success">新建</a>
          </div>
          
          <form:form commandName="tag" servletRelativeAction="/cms/tag" method="get" id="cmsTagIndexQueryForm">
            <input type="hidden" id="currentPage" name="currentPage" value="1"/>
            <div class="input-group col-sm-4">
                <form:input path="viewname" cssClass="form-control" placeholder="请输入标签名称或显示名称" />
                <span class="input-group-btn">
                  <a class="btn btn-default" href="javascript:;" onclick="SimpleUtil.query(1,'#cmsTagIndexQueryForm')"><span class="glyphicon glyphicon-search"></span></a>
                </span>
            </div>
          </form:form>
            
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>#</th>
                  <th>标签名称</th>
                  <th>显示名称</th>
                  <th>操&#8195;作</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="tag" items="${pagination.records }" varStatus="stat">
                  <tr>
                    <td>${(pagination.currentPage - 1) * pagination.pageSize + stat.count }</td>
                    <td>${tag.tagname }</td>
                    <td>${tag.viewname }</td>
                    <td>
                    
                    <a data-toggle="tooltip" data-placement="top" title="删除" href="javascript:;" onclick="SimpleUtil.remove('${pageContext.request.contextPath }/cms/tag/remove?tagname=${tag.tagname }','#cmsTagIndexQueryForm')"><span class="glyphicon glyphicon-trash"></span></a>
                    &#160;
                    <a data-toggle="tooltip" data-placement="top" title="编辑" href="${pageContext.request.contextPath }/cms/tag/edit?tagname=${tag.tagname }"><span class="glyphicon glyphicon-edit"></span></a>
                    <%--&#160;
                    <a data-toggle="tooltip" data-placement="top" title="权限管理" href="${pageContext.request.contextPath }/cms/tag/edit?tagname=${tag.tagname }"><span class="glyphicon glyphicon-magnet"></span></a>
                    --%>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          
            <nav>
              <ul id="cmsTagIndexPagination" currentPage="${pagination.currentPage }" totalPage="${pagination.totalPage }" class="pagination pull-right" style="margin:0"></ul>
            </nav>
          </div>
          
