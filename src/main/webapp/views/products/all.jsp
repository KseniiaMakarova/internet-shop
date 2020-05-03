<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en" class="h-100">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <title>All products | MyShop</title>
        <style>
            body {
                background: url("https://i.imgur.com/1WRx5BD.png") no-repeat;
                background-size: cover;
            }
        </style>
    </head>

    <body class="h-100">
        <div class="container h-50">
            <div class="row h-100 justify-content-center align-items-center">
                <div class="col-10" style="text-align: center">
                    <h3>All products:</h3>
                    <table class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Name</th>
                                <th scope="col">Price</th>
                                <th scope="col">Add to cart</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${products}">
                                <tr>
                                    <th scope="row">
                                        <c:out value="${product.id}"/>
                                    </th>
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
                        </tbody>
                    </table>
                    <a href="${pageContext.request.contextPath}/cart/show">
                            <button type="button" class="btn btn-dark">Show my cart</button></a>
                </div>
            </div>
        </div>
    </body>
</html>
