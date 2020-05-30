<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" class="h-100">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <title>Log in | MyShop</title>
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
                <form method="post" action="${pageContext.request.contextPath}/login" class="needs-validation" novalidate>
                    <c:set var="message" value="${message}"/>
                    <c:if test = "${message != null}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <c:out value="${message}"/>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                    </c:if>
                    <h4>Please enter your email and password to log in:</h4>
                    <div class="form-group">
                        <label for="InputEmail">Email address:</label>
                        <input type="email" name="login" class="form-control" id="InputEmail" aria-describedby="emailHelp" required>
                        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                        <div class="invalid-feedback">Please enter your email.</div>
                    </div>
                    <div class="form-group">
                        <label for="InputPassword">Password:</label>
                        <input type="password" name="psw" class="form-control" id="InputPassword" required>
                        <div class="invalid-feedback">Please enter your password.</div>
                    </div>
                    <button type="submit" class="btn btn-dark">Log in</button>
                    <a href="${pageContext.request.contextPath}/register">
                        <button type="button" class="btn btn-dark">Register</button></a>
                </form>
            </div>
        </div>
        <script>
            (function() {
                'use strict';
                window.addEventListener('load', function() {
                    const forms = document.getElementsByClassName('needs-validation');
                    Array.prototype.filter.call(forms, function (form) {
                        form.addEventListener('submit', function (event) {
                            if (form.checkValidity() === false) {
                                event.preventDefault();
                                event.stopPropagation();
                            }
                            form.classList.add('was-validated');
                        }, false);
                    });
                }, false);
            })();
        </script>
    </body>
</html>
