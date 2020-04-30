<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
</head>
<body>
    <h4 style="color:red">${message}</h4>
    <h2>Please enter your login and password to log in:</h2>
    <form method="post" action="${pageContext.request.contextPath}/login">
        Login: <input type="text" name="login">
        Password: <input type="password" name="psw">
        <button type="submit">Log in</button>
    </form>
    <a href="${pageContext.request.contextPath}/register">
                    <button type="button">Register</button></a>
</body>
</html>
