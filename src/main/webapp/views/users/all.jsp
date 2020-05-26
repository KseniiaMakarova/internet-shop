<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en" class="h-100">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <title>All users | MyShop</title>
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
                    <h3>All users:</h3>
                    <table class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Login</th>
                                <th scope="col">Full name</th>
                                <th scope="col">Show orders</th>
                                <th scope="col">Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <th scope="row">
                                        <c:out value="${user.id}"/>
                                    </th>
                                    <td>
                                        <c:out value="${user.login}"/>
                                    </td>
                                    <td>
                                        <c:out value="${user.name}"/>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}
                                                /user/orders?id=${user.id}">SHOW ORDERS</a>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}
                                                /users/delete?id=${user.id}">DELETE</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
