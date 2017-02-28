<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>

          <h2 class="sub-header">频道管理</h2>
          
          <div class="btn-group pull-right">
            <a href="${pageContext.request.contextPath }/cms/channel/create?parentName=${parentChannel.channelName}" class="btn btn-success">新建</a>
          </div>
          
          <form:form commandName="channel" servletRelativeAction="/cms/channel" method="get" id="cmsChannelIndexQueryForm">
            <input type="hidden" id="currentPage" name="currentPage" value="1"/>
            <form:hidden path="parentName"/>
            <%-- <div class="input-group col-sm-4">
                <form:select path="parentName" cssClass="form-control">
                  <form:option value=""></form:option>
                  <form:options items="${CHANNEL_CHILDREN }" itemLabel="viewname" itemValue="channelName"/>
                </form:select>
                <span class="input-group-btn">
                  <a class="btn btn-default" href="javascript:;" onclick="SimpleUtil.query(1,'#cmsChannelIndexQueryForm')"><span class="glyphicon glyphicon-search"></span></a>
                </span>
            </div> --%>
          </form:form>
            
            
          <div class="col-sm-4 list-group">
          <c:if test="${empty parentChannelParentName }">
              <a href="javascript:;" class="list-group-item ${empty channel.parentName ? 'active' : ''}" 
              onclick="CMSChannel.queryChildren('','#cmsChannelIndexQueryForm')">全部</a>
          </c:if>
          
          <c:if test="${not empty parentChannelParentName}">
              <a href="javascript:;" class="list-group-item" 
              onclick="CMSChannel.queryChildren('${parentChannel.parentName}','#cmsChannelIndexQueryForm')">返回</a>
          </c:if>
              
          <c:forEach var="chl" items="${CHANNEL_CHILDREN }">
              <a href="javascript:;" class="list-group-item ${(chl.channelName eq parentChannelName) ? 'active' : '' }" 
              onclick="CMSChannel.queryChildren('${fn:escapeXml(chl.channelName) }','#cmsChannelIndexQueryForm')">
                ${fn:escapeXml(chl.viewname) }
              </a>
          </c:forEach>
          </div>
            
          
          <div class="col-sm-8">
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>#</th>
                  <th>频道名称</th>
                  <th>显示名称</th>
                  <shiro:hasRole name="ROLE_SYS_ADMIN">
                  <th>Owner</th>
                  </shiro:hasRole>
                  <th>操&#8195;作</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="channel" items="${pagination.records }" varStatus="stat">
                  <tr>
                    <td>${(pagination.currentPage - 1) * pagination.pageSize + stat.count }</td>
                    <td>${fn:escapeXml(channel.channelName) }</td>
                    <td>${fn:escapeXml(channel.viewname) }</td>
                  <shiro:hasRole name="ROLE_SYS_ADMIN">
                    <td>${fn:escapeXml(channel.owner) }</td>
                  </shiro:hasRole>
                  
                    <td>
                    
                    <a data-toggle="tooltip" data-placement="top" title="删除" href="javascript:;" onclick="SimpleUtil.remove('${pageContext.request.contextPath }/cms/channel/remove?channelName=${fn:escapeXml(channel.channelName) }','#cmsChannelIndexQueryForm')"><span class="glyphicon glyphicon-trash"></span></a>
                    &#160;
                    <a data-toggle="tooltip" data-placement="top" title="编辑" href="${pageContext.request.contextPath }/cms/channel/edit?parentName=${fn:escapeXml(channel.parentName)}&channelName=${fn:escapeXml(channel.channelName)}"><span class="glyphicon glyphicon-edit"></span></a>
                    &#160;
                    <a data-toggle="tooltip" data-placement="top" title="子频道管理" href="${pageContext.request.contextPath }/cms/channel?parentName=${fn:escapeXml(channel.channelName)}"><span class="glyphicon glyphicon-folder-open"></span></a>
                    <%----%>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          
            <nav>
              <ul id="cmsChannelIndexPagination" currentPage="${pagination.currentPage }" totalPage="${pagination.totalPage }" class="pagination pull-right" style="margin:0"></ul>
            </nav>
          </div>
          </div>
