<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en" class="h-100">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <title>Oops! | MyShop</title>
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
        <form style="text-align: center">
            <h3>Oops! Something went wrong!</h3>
            <a href="${pageContext.request.contextPath}/">
                <button type="button" class="btn btn-dark">Return to main page</button></a>
        </form>
    </div>
</div>
</body>
</html>
