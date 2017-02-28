<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>

          
          <h4 class="sub-header">${ERROR_MESSAGE }, ERROR_UUID : ${ERROR_UUID }</h4>
          
          <div class="col-sm-12">
          
            <div class="alert alert-danger" role="alert" style="height: 200px; overflow-x: hidden; overflow-y:auto;">${ERROR_CAUSE }</div>
            
            <div class="form-group">
                <a class="btn btn-default" href="javascript:;" onclick="history.back();" role="button">返&nbsp;回</a>
            </div>
          
          </div>
          
          <c:remove var="ERROR_MESSAGE"/>
          <c:remove var="ERROR_UUID"/>
          <c:remove var="ERROR_CAUSE"/>
