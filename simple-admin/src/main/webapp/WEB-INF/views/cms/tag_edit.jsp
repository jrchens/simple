<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>

          
          <h4 class="sub-header">编辑标签</h4>
          
          <div class="col-sm-6">
          
          <form:form commandName="tag" servletRelativeAction="/cms/tag/update" method="post" id="cmsTagEditUpdateForm">
            <form:hidden path="tagname"/>
            <div class="form-group">
              <label for="tagname" class="control-label">标签名称</label>
              <div>
                <p>${tag.tagname }</p>
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
                <a class="btn btn-primary" href="javascript:;" onclick="SimpleUtil.update('#cmsTagEditUpdateForm')" role="button">保&nbsp;存</a>
                <a class="btn btn-default" href="${pageContext.request.contextPath }/cms/tag" role="button">返&nbsp;回</a>
            </div>
          
          </form:form>
          
          </div>
