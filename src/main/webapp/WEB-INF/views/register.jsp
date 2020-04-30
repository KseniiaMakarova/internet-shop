<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h4 style="color:red">${message}</h4>
    <h2>Please provide account information:</h2>
    <form method="post" action="${pageContext.request.contextPath}/register">
        Full name: <input type="text" name="name">
        Login: <input type="text" name="login">
        Password: <input type="password" name="psw">
        Repeat password: <input type="password" name="rep-psw">
        <button type="submit">Register</button>
    </form>
</body>
</html>
