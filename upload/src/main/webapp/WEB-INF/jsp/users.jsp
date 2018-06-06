<%--
  Created by IntelliJ IDEA.
  User: AlxB
  Date: 03/06/2018
  Time: 11:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Show Data</title>
</head>
<body>
<h1> Users from javaops</h1>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>User</th>
        <th>e-mail</th>
        <th>status</th>
    </tr>
    </thead>
    <c:forEach items="${users}" var="user">
        <jsp:useBean id="user" scope="page" type="ru.javaops.masterjava.xml.schema.User"/>
        <tr>
            <td>${user.value}</td>
            <td>${user.email}</td>
            <td>${user.flag}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
