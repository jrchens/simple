<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>

<div class="col-sm-12" style="margin-bottom: 8px;">
  <ul class="nav nav-tabs" role="tablist" id="cmsProductCreateTab">
    <li role="presentation"><a href="#cmsProductBaseInfo">产品信息</a></li>
    <li role="presentation" class="active"><a href="#cmsProductImageInfo">产品图片</a></li>
  </ul>
</div>

<div class="tab-content">
  <div role="tabpanel" class="tab-pane" id="cmsProductBaseInfo">


    <%--<h4 class="sub-header">编辑产品</h4>--%>
    <form:form commandName="product"
      servletRelativeAction="/cms/product/update" method="post"
      id="cmsProductEditUpdateForm">

      <div class="col-sm-8">
        <h4 class="page-header">基本信息</h4>

        <spring:bind path="productId">
          <c:if test="${status.error }">
            <div class="form-group has-error">
              <label for="productId" class="control-label">
                ${status.errorMessage }</label>
            </div>
          </c:if>
          <form:hidden path="productId" />
        </spring:bind>

        <spring:bind path="productName">
          <div class="form-group ${status.error ? 'has-error' : '' }">
            <label for="productName" class="control-label">产品名称
              ${status.errorMessage }</label>
            <div>
              <form:input path="productName" cssClass="form-control"
                placeholder="请输入产品名称" />
            </div>
          </div>
        </spring:bind>

        <spring:bind path="spec">
          <div class="form-group ${status.error ? 'has-error' : '' }">
            <label for="spec" class="control-label">规格型号
              ${status.errorMessage }</label>
            <div>
              <form:input path="spec" cssClass="form-control"
                placeholder="请输入规格型号" />
            </div>
          </div>
        </spring:bind>

        <spring:bind path="intro">
          <div class="form-group ${status.error ? 'has-error' : '' }">
            <label for="intro" class="control-label">产品简介
              ${status.errorMessage }</label>
            <div>
              <form:input path="intro" cssClass="form-control"
                placeholder="请输入产品简介" />
            </div>
          </div>
        </spring:bind>

        <spring:bind path="richContent">
          <div class="form-group ${status.error ? 'has-error' : '' }">
            <label for="cmsProductRichContent" class="control-label">产品说明
              ${status.errorMessage }</label>
            <div>
              <form:textarea id="cmsProductRichContent" path="richContent" cssClass="form-control" placeholder="请输入产品说明" />
            </div>
          </div>
        </spring:bind>

        <div class="form-group">
          <a class="btn btn-primary" href="javascript:;"
            onclick="SimpleUtil.update('#cmsProductEditUpdateForm')"
            role="button">保&nbsp;存</a> <a class="btn btn-default"
            href="${pageContext.request.contextPath }/cms/product"
            role="button">返&nbsp;回</a>
        </div>


      </div>



      <div class="col-sm-4">

        <h4 class="page-header">产品标签</h4>

        <div class="form-group">

          <c:forEach var="tag" items="${CMS_ALL_TAGS }">
            <c:set var="checked" />
            <c:forEach var="productag" items="${product.tags }">
              <c:if test="${tag.tagname eq productag }">
                <c:set var="checked">checked="checked"</c:set>
              </c:if>
            </c:forEach>
            <div class="checkbox">
              <label> <input type="checkbox" ${checked }
                name="tags" value="${tag.tagname }">
                ${tag.viewname }
              </label>
            </div>
          </c:forEach>

        </div>
      </div>

    </form:form>

  </div>
  <div role="tabpanel" class="tab-pane active" id="cmsProductImageInfo">
  
      <div class="col-sm-12">
      <input id="cmsProductEditFiles" type="file" 
        name="files" 
        dataModule="product" 
        dataEntity="${fn:escapeXml(product.productId)}" 
        dataAttr="image"  
        dataUrl="${pageContext.request.contextPath }/common/attach/upload"
        class="file-loading"
        multiple="true">
      </div>

  </div>
</div>
