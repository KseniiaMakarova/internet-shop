<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your shopping cart</title>
</head>
<body>
    <h2>Your shopping cart:</h2>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Remove</th>
        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <c:out value="${product.name}"/>
                </td>
                <td>
                    <c:out value="${product.price}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}
                                    /products/remove-from-cart?id=${product.id}">REMOVE</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/orders/complete">
                    <button type="button">Complete Order</button></a>
</body>
</html>
