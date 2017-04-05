<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
￼<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-Hans">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Tables</title>
</head>
<body>
  <ul style="list-style-type: none;">
    <c:forEach var="table" items="${tables }">
      <li>${table.tab_name }</li>
    </c:forEach>
  </ul>

  <ul style="list-style-type: none;">
    <c:forEach var="column" items="${tables[0].columns}">
      <li>${column.col_name }: ${column.col_type }</li>
    </c:forEach>
  </ul>
</body>
</html>