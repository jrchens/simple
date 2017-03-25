<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>


<h4 class="sub-header">编辑用户</h4>

<form:form commandName="user" servletRelativeAction="/admin/user/update"
  method="post" id="adminUserEditUpdateForm">

  <div class="col-sm-6">

    <h4 class="page-header">用户信息</h4>

    <form:hidden path="username" />
    <div class="form-group">
      <label for="username" class="control-label">用户名称</label>
      <div>
        <p>${user.username }</p>
      </div>
    </div>

    <spring:bind path="viewname">
      <div class="form-group ${status.error ? 'has-error' : '' }">
        <label for="viewname" class="control-label">显示名称
          ${status.errorMessage }</label>
        <div>
          <form:input path="viewname" cssClass="form-control"
            placeholder="请输入显示名称" />
        </div>
      </div>
    </spring:bind>

    <spring:bind path="groupname">
      <div class="form-group ${status.error ? 'has-error' : '' }">
        <label for="groupname" class="control-label">所属群组
          ${status.errorMessage }</label>
        <div>
          <%--multiple="multiple" --%>
          <form:select path="groupname" cssClass="form-control"
            items="${ADMIN_USER_GROUPS }" itemLabel="viewname"
            itemValue="groupname">
          </form:select>
        </div>
      </div>
    </spring:bind>

    <div class="form-group">
      <a class="btn btn-primary"
        onclick="SimpleUtil.update('#adminUserEditUpdateForm')"
        role="button">保&nbsp;存</a> <a class="btn btn-default"
        href="${pageContext.request.contextPath }/admin/user"
        role="button">返&nbsp;回</a>
    </div>

  </div>


  <div class="col-sm-6">

    <h4 class="page-header">角色信息</h4>

    <div class="form-group">

      <c:forEach var="role" items="${ADMIN_USER_ROLES }">
        <c:set var="checked" />
        <c:forEach var="userRole" items="${USER_ROLES }">
          <c:if test="${role.rolename eq userRole }">
            <c:set var="checked">checked="checked"</c:set>
          </c:if>
        </c:forEach>
        <div class="checkbox">
          <label> <input type="checkbox" ${checked }
            name="roles" value="${role.rolename }">
            ${role.viewname }
          </label>
        </div>
      </c:forEach>

    </div>
  </div>
</form:form>
