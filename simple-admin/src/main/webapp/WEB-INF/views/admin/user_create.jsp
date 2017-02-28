<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>

          
          <h4 class="sub-header">新建用户</h4>
          
          <div class="col-sm-6">
          
          <form:form commandName="user" servletRelativeAction="/admin/user/save" method="post" id="adminUserCreateSaveForm">
            <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="username" class="control-label">用户名称 ${status.errorMessage }</label>
              <div>
                <form:input path="username" cssClass="form-control" placeholder="请输入用户名称" />
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
            
            <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="password" class="control-label">用户密码 ${status.errorMessage }</label>
              <div>
                <form:password path="password" cssClass="form-control" placeholder="请输入用户密码"/>
              </div>
            </div>
            </spring:bind>
            
            <spring:bind path="groupname">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="groupname" class="control-label">所属群组 ${status.errorMessage }</label>
              <div><%--multiple="multiple" --%>
                <form:select path="groupname" cssClass="form-control" items="${ADMIN_USER_GROUPS }" itemLabel="viewname" itemValue="groupname"></form:select>
              </div>
            </div>
            </spring:bind>
            
            <div class="form-group">
                <a class="btn btn-primary" onclick="AdminUser.save('#adminUserCreateSaveForm')" role="button">保&nbsp;存</a>
                <a class="btn btn-default" href="${pageContext.request.contextPath }/admin/user" role="button">返&nbsp;回</a>
            </div>
          
          </form:form>
          
          </div>
