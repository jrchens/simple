<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>

          
          <h4 class="sub-header">编辑角色</h4>
          
          <div class="col-sm-6">
          
          <form:form commandName="role" servletRelativeAction="/admin/role/update" method="post" id="adminRoleEditUpdateForm">
            <form:hidden path="rolename"/>
            <div class="form-group">
              <label for="rolename" class="control-label">角色名称</label>
              <div>
                <p>${role.rolename }</p>
              </div>
            </div>
            
            <spring:bind path="viewname">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="viewname" class="control-label">显示名称  ${status.errorMessage }</label>
              <div>
                <form:input path="viewname" cssClass="form-control" placeholder="请输入显示名称" />
              </div>
            </div>
            </spring:bind>
            
            <div class="form-group">
                <a class="btn btn-primary" onclick="SimpleUtil.update('#adminRoleEditUpdateForm')" role="button">保&nbsp;存</a>
                <a class="btn btn-default" href="${pageContext.request.contextPath }/admin/role" role="button">返&nbsp;回</a>
            </div>
          
          </form:form>
          
          </div>
