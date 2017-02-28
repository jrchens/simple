<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>

          <h4 class="sub-header">新建文章</h4>
     
          <form:form commandName="article" servletRelativeAction="/cms/article/save" method="post" id="cmsArticleCreateSaveForm">
          <div class="col-sm-8">
            
            <spring:bind path="channelName">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="channelName" class="control-label">所属频道 ${status.errorMessage }</label>
              <div>
                <form:hidden path="channelName"/>
                <form:input path="channelNameValue" readonly="true" cssClass="form-control" placeholder="请选择文章频道" />
              </div>
            </div>
            </spring:bind>
            
            <spring:bind path="title">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="title" class="control-label">文章标题 ${status.errorMessage }</label>
              <div>
                <form:input path="title" cssClass="form-control" placeholder="请输入文章标题" />
              </div>
            </div>
            </spring:bind>
          
            <spring:bind path="origin">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="origin" class="control-label">文章来源 ${status.errorMessage }</label>
              <div>
                <form:input path="origin" cssClass="form-control" placeholder="请输入文章来源" />
              </div>
            </div>
            </spring:bind>
          
            <spring:bind path="intro">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="intro" class="control-label">文章简介 ${status.errorMessage }</label>
              <div>
                <form:textarea path="intro" cssClass="form-control" placeholder="请输入文章简介" />
              </div>
            </div>
            </spring:bind>
            
            <spring:bind path="author">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="author" class="control-label">文章作者 ${status.errorMessage }</label>
              <div>
                <form:input path="author" cssClass="form-control" placeholder="请输入文章作者" />
              </div>
            </div>
            </spring:bind>
            
            <spring:bind path="pubDate">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="pubDate" class="control-label">发布日期 ${status.errorMessage }</label>
              <div>
                <form:input path="pubDate" cssClass="form-control" placeholder="请输入发布日期" />
              </div>
            </div>
            </spring:bind>
            
            <spring:bind path="richContent">
            <div class="form-group ${status.error ? 'has-error' : '' }">
              <label for="cmsArticleRichContent" class="control-label">文章内容 ${status.errorMessage }</label>
              <div>
                <form:textarea id="cmsArticleRichContent" path="richContent" cssClass="form-control" placeholder="请输入文章内容" />
              </div>
            </div>
            </spring:bind>
            
            <div class="form-group">
                <a class="btn btn-primary" href="javascript:;" onclick="SimpleUtil.save('#cmsArticleCreateSaveForm')" role="button">保&nbsp;存</a>
                <a class="btn btn-default" href="${pageContext.request.contextPath }/cms/article" role="button">返&nbsp;回</a>
            </div>
          
          </div>
          
          <div id="cmsArticleCreateChannelTreeview" class="col-sm-4"  dataUrl="${pageContext.request.contextPath }/cms/channel/queryUserChannel" >
          
          </div>
          
   </form:form>
          