<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
    <h2>Please provide product information:</h2>
    <form method="post" action="${pageContext.request.contextPath}/products/new">
        Name: <input type="text" name="name">
        Price: <input type="number" name="price">
        <button type="submit">Submit</button>
    </form>
</body>
</html>
