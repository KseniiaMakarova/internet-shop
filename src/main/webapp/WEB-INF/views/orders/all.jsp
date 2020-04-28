<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders</title>
</head>
<body>
    <h2>Your orders:</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Show</th>
            <th>Delete</th>
        </tr>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>
                    <c:out value="${order.id}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}
                                        /orders/show?id=${order.id}">SHOW</a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}
                                        /orders/delete?id=${order.id}">DELETE</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
