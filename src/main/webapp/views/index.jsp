<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/register">REGISTER</a><p>
    <a href="${pageContext.request.contextPath}/login">LOG IN</a><p>
    <a href="${pageContext.request.contextPath}/users/all">VIEW ALL USERS</a><p>
    <a href="${pageContext.request.contextPath}/products/manage">MANAGE PRODUCTS</a><p>
    <a href="${pageContext.request.contextPath}/products/all">BUY PRODUCTS</a><p>
    <a href="${pageContext.request.contextPath}/orders/all">MY ORDERS</a><p>
</body>
</html>