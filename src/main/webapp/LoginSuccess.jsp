<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Login Success</title>
</head>
<body>
    <h3>Hi <%= request.getAttribute("user") %>, Login Successful!</h3>
    <a href="login.html">Back to Login</a>
</body>
</html>
