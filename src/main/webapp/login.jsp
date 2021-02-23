<%-- 
    Document   : login
    Created on : Jan 25, 2021, 10:25:39 AM
    Author     : natton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        
        <h1>Login</h1>
        <c:if test="${not empty param.logout}">
            Logout successfully
        </c:if>
        <c:if test="${not empty param.error}">
            <font color="red">
                Wrong username or password!
            </font>
        </c:if>
        <c:if test="${not empty param.register}">
            <font color="green">
                Register successfully!
            </font>
        </c:if>
        <form action="loginAction" method="POST">
            Email: <input type="text" name="txtEmail" value="" /><br/>
            Password: <input type="password" name="txtPassword" value="" />
            <input type="submit" value="Login" />
            <input type="reset" value="Reset" />
        </form>
        <a href="RegisterPage">Register</a>
    </body>
</html>
