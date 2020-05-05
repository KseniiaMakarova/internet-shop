<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en" class="h-100">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <title>Manage products | MyShop</title>
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
                                <th scope="col">Delete</th>
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
                                                /products/delete?id=${product.id}">DELETE</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <form method="post" action="${pageContext.request.contextPath}/products/manage">
                        <h4>To add a new product, please provide the following:</h4>
                        <div class="form-row">
                            <div class="col-5">
                                <input type="text" name="name" class="form-control" placeholder="Name">
                            </div>
                            <div class="col-5">
                                <input type="number" name="price" class="form-control" placeholder="Price">
                            </div>
                            <div class="col-2">
                                <button type="submit" class="btn btn-dark">Add product</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
