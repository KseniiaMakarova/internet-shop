<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
    <h2>All users:</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Login</th>
            <th>Full name</th>
            <th>Delete</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>
                    <c:out value="${user.id}"/>
                </td>
                <td>
                    <c:out value="${user.login}"/>
                </td>
                <td>
                    <c:out value="${user.name}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}
                                    /users/delete?id=${user.id}">DELETE</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
