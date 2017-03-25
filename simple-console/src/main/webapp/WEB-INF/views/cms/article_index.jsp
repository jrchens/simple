<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>

          <h2 class="sub-header">文章管理</h2>
          
          
          <div class="col-sm-12">
          <form:form cssClass="form-inline" commandName="article" servletRelativeAction="/cms/article" method="get" id="cmsArticleIndexQueryForm">
            <input type="hidden" id="currentPage" name="currentPage" value="1"/>
            <form:hidden path="channelId"/>
            <form:hidden path="channelName"/>
            <div class="input-group col-sm-6">
                <form:input path="title" cssClass="form-control" placeholder="请输入文章标题" />
                <span class="input-group-btn">
                  <a class="btn btn-default" href="javascript:;" onclick="SimpleUtil.query(1,'#cmsArticleIndexQueryForm')"><span class="glyphicon glyphicon-search"></span></a>
                </span>
            </div>
            
            <a href="${pageContext.request.contextPath }/cms/article/create?channelName=${article.channelName}" class="btn btn-success pull-right">新建</a>
          
          </form:form>
          
          
          </div>
          
          <%-- --%> <div class="col-sm-4" style="margin-top: 8px">
          <div id="cmsArticleIndexChannelTreeview" dataChannelId="${fn:escapeXml(article.channelId) }" dataUrl="${pageContext.request.contextPath }/cms/channel/queryUserChannel" ></div>
          </div>
          
          <div class="col-sm-8">
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>#</th>
                  <th>文章标题</th>
                  <th>发布日期</th>
                  <th>操&#8195;作</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="article" items="${pagination.records }" varStatus="stat">
                  <tr>
                    <td>${(pagination.currentPage - 1) * pagination.pageSize + stat.count }</td>
                    <td>${article.title }</td>
                    <td>${article.pubDate }</td>
                    <td>
                    
                    <a data-toggle="tooltip" data-placement="top" title="删除" href="javascript:;" onclick="SimpleUtil.remove('${pageContext.request.contextPath }/cms/article/remove?articleId=${article.articleId }','#cmsArticleIndexQueryForm')"><span class="glyphicon glyphicon-trash"></span></a>
                    &#160;
                    <a data-toggle="tooltip" data-placement="top" title="编辑" href="${pageContext.request.contextPath }/cms/article/edit?articleId=${article.articleId }"><span class="glyphicon glyphicon-edit"></span></a>
                    <%--&#160;
                    <a data-toggle="tooltip" data-placement="top" title="权限管理" href="${pageContext.request.contextPath }/cms/article/edit?articleName=${article.articleName }"><span class="glyphicon glyphicon-magnet"></span></a>
                    --%>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          
            <nav>
              <ul id="cmsArticleIndexPagination" currentPage="${pagination.currentPage }" totalPage="${pagination.totalPage }" class="pagination pull-right" style="margin:0"></ul>
            </nav>
          </div>
          </div>
          
