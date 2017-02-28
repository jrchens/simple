<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp" %>

        <div class="col-sm-3 col-md-2 sidebar">

          <ul class="nav nav-sidebar">
            <c:forEach var="menu" items="${MENU_RECORDS }">
              <li ${(CURRENT_MENUNAME eq menu.menuname) ? 'class=active' : ''}><a href="${pageContext.request.contextPath }/admin/menu/${menu.menuname}">${menu.viewname}</a></li>
            </c:forEach>
          </ul>
        
        </div>
        