<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>

    <h4 class="sub-header">编辑频道</h4>
    
    <form:form commandName="channel"
      servletRelativeAction="/cms/channel/update" method="post"
      id="cmsChannelEditUpdateForm">

      <div class="col-sm-8">
          
            <spring:bind path="parentName">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="parentName" class="control-label">上级频道 ${status.errorMessage }</label>
              <div>
                <form:select path="parentName" cssClass="form-control">
                  <form:option value=""></form:option>
                  <form:options items="${CHANNEL_CHILDREN }" itemLabel="viewname" itemValue="channelName"/>
                </form:select>
              </div>
            </div>
            </spring:bind>
          
            <spring:bind path="channelName">
            <form:hidden path="channelName"/>
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="channelName" class="control-label">频道名称 ${status.errorMessage }</label>
              <div>
                ${fn:escapeXml(channel.channelName) }
              </div>
            </div>
            </spring:bind>
            
            <spring:bind path="viewname">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="viewname" class="control-label">显示名称 ${status.errorMessage }</label>
              <div>
                <form:input path="viewname" cssClass="form-control" placeholder="请输入显示名称" />
              </div>
            </div>
            </spring:bind>
            
            <spring:bind path="url">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="url" class="control-label">频道链接 ${status.errorMessage }</label>
              <div>
                <form:input path="url" cssClass="form-control" placeholder="请输入频道链接" />
              </div>
            </div>
            </spring:bind>
            
            <shiro:hasRole name="ROLE_SYS_ADMIN">
            <spring:bind path="owner">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="owner" class="control-label">Owner ${status.errorMessage }</label>
              <div>
                <form:select path="owner" cssClass="form-control" items="${SYS_ALL_USERS }" itemLabel="viewname" itemValue="username"></form:select>
              </div>
            </div>
            </spring:bind>
            </shiro:hasRole>

        <div class="form-group">
          <a class="btn btn-primary" href="javascript:;"
            onclick="SimpleUtil.update('#cmsChannelEditUpdateForm')"
            role="button">保&nbsp;存</a> <a class="btn btn-default"
            href="${pageContext.request.contextPath }/cms/channel?parentName=${channel.parentName}"
            role="button">返&nbsp;回</a>
        </div>

      </div>

    </form:form>
