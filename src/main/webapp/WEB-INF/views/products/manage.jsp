<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
    <h2>All products:</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Delete</th>
        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <c:out value="${product.id}"/>
                </td>
                <td>
                    <c:out value="${product.name}"/>
                </td>
                <td>
                    <c:out value="${product.price}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}
                                            /products/delete?id=${product.id}">DELETE</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <h2>To add a new product, please provide the following:</h2>
    <form method="post" action="${pageContext.request.contextPath}/products/manage">
        Name: <input type="text" name="name">
        Price: <input type="number" name="price">
        <button type="submit">Submit</button>
    </form>
</body>
</html>
