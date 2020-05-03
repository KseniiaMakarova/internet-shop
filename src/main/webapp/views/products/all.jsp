<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products</title>
</head>
<body>
    <h2>All products:</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Add to cart</th>
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
                                        /products/add-to-cart?id=${product.id}">ADD</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/cart/show">
                    <button type="button">Show my cart</button></a>
</body>
</html>
