<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>

          <h2 class="sub-header">产品管理</h2>
          
          
          <div class="col-sm-12">
          <form:form cssClass="form-inline" commandName="product" servletRelativeAction="/cms/product" method="get" id="cmsProductIndexQueryForm">
            <input type="hidden" id="currentPage" name="currentPage" value="1"/>
            <div class="input-group col-sm-6">
                <form:input path="productName" cssClass="form-control" placeholder="请输入产品名称" />
                <span class="input-group-btn">
                  <a class="btn btn-default" href="javascript:;" onclick="SimpleUtil.query(1,'#cmsProductIndexQueryForm')"><span class="glyphicon glyphicon-search"></span></a>
                </span>
            </div>
            
            <a href="${pageContext.request.contextPath }/cms/product/create" class="btn btn-success pull-right">新建</a>
          
          </form:form>
          
          
          </div>
          
          <%-- <div class="col-sm-4" style="margin-top: 8px">
          <div id="cmsProductIndexChannelTreeview" dataUrl="${pageContext.request.contextPath }/cms/channel/queryUserChannel" ></div>
          </div> --%>
          
          <div class="col-sm-12">
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>#</th>
                  <th>产品名称</th>
                  <th>规格型号</th>
                  <th>操&#8195;作</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="product" items="${pagination.records }" varStatus="stat">
                  <tr>
                    <td>${(pagination.currentPage - 1) * pagination.pageSize + stat.count }</td>
                    <td>${product.productName }</td>
                    <td>${product.spec }</td>
                    <td>
                    
                    <a data-toggle="tooltip" data-placement="top" title="删除" href="javascript:;" onclick="SimpleUtil.remove('${pageContext.request.contextPath }/cms/product/remove?productId=${product.productId }','#cmsProductIndexQueryForm')"><span class="glyphicon glyphicon-trash"></span></a>
                    &#160;
                    <a data-toggle="tooltip" data-placement="top" title="编辑" href="${pageContext.request.contextPath }/cms/product/edit?productId=${product.productId }"><span class="glyphicon glyphicon-edit"></span></a>
                    <%--&#160;
                    <a data-toggle="tooltip" data-placement="top" title="权限管理" href="${pageContext.request.contextPath }/cms/product/edit?productName=${product.productName }"><span class="glyphicon glyphicon-magnet"></span></a>
                    --%>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          
            <nav>
              <ul id="cmsProductIndexPagination" currentPage="${pagination.currentPage }" totalPage="${pagination.totalPage }" class="pagination pull-right" style="margin:0"></ul>
            </nav>
          </div>
          </div>
          
