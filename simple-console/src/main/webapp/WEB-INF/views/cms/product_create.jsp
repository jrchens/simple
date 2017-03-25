<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>

          <%--<h4 class="sub-header">新建产品</h4>--%>
         <div class="col-sm-12" style="margin-bottom: 8px;">
              <ul class="nav nav-tabs" role="tablist" id="cmsProductCreateTab">
                <li role="presentation" class="active"><a href="javascript:;">产品信息</a></li>
                <li role="presentation" class="disabled"><a href="javascript:;">产品图片</a></li>
              </ul>
         </div>
     
          <form:form commandName="product" servletRelativeAction="/cms/product/save" method="post" id="cmsProductCreateSaveForm">
          
          <div class="col-sm-8">
            <h4 class="page-header">基本信息</h4>
          
            <spring:bind path="productName">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="productName" class="control-label">产品名称 ${status.errorMessage }</label>
              <div>
                <form:input path="productName" cssClass="form-control" placeholder="请输入产品名称" />
              </div>
            </div>
            </spring:bind>
            
            <spring:bind path="spec">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="spec" class="control-label">规格型号 ${status.errorMessage }</label>
              <div>
                <form:input path="spec" cssClass="form-control" placeholder="请输入规格型号" />
              </div>
            </div>
            </spring:bind>
            
            <spring:bind path="intro">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="intro" class="control-label">产品简介 ${status.errorMessage }</label>
              <div>
                <form:input path="intro" cssClass="form-control" placeholder="请输入产品简介" />
              </div>
            </div>
            </spring:bind>
            
            <spring:bind path="richContent">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="cmsProductRichContent" class="control-label">产品说明 ${status.errorMessage }</label>
              <div>
              <form:textarea id="cmsProductRichContent" path="richContent" cssClass="form-control" placeholder="请输入产品说明" />
              </div>
            </div>
            </spring:bind>
            
            <div class="form-group">
                <a class="btn btn-primary" href="javascript:;" onclick="SimpleUtil.save('#cmsProductCreateSaveForm')" role="button">保&nbsp;存</a>
                <a class="btn btn-default" href="${pageContext.request.contextPath }/cms/product" role="button">返&nbsp;回</a>
            </div>
          
          </div>
          
          <div class="col-sm-4">
              <h4 class="page-header">产品标签</h4>
              <spring:bind path="tags">
              <div class="form-group  ${status.error ? 'has-error' : '' }">
                <c:forEach var="tag" items="${CMS_ALL_TAGS }">
                  <div class="checkbox">
                    <label>
                      <input type="checkbox" name="tags" value="${tag.tagname }">
                      ${tag.viewname }
                    </label>
                  </div>
                </c:forEach>
              </div>
            </spring:bind>
        </div>
          
   </form:form>
          