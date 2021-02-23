<%-- 
    Document   : register
    Created on : Jan 5, 2021, 3:49:30 PM
    Author     : natton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Register Page</title>
    </head>
    <body>
        <h2>Register Page</h2>
        <form action="RegisterAction" method="POST">
            <br> Email* <input type="text" name="txtEmail" value="${param.txtEmail}" required/>
            <c:if test="${not empty requestScope.ERROR['email']}">
                <font color="red">${requestScope.ERROR['email']}</font>
            </c:if>

            <br> Password* <input type="password" name="txtPassword" required/>
            <c:if test="${not empty requestScope.ERROR['password']}">
                <font color="red">${requestScope.ERROR['password']}</font>
            </c:if>

            <br> Confirmed Password* <input type="password" name="txtConfirmedPassword" required/>
            <c:if test="${not empty requestScope.ERROR['confirmedPassword']}">
                <font color="red">${requestScope.ERROR['confirmedPassword']}</font>
            </c:if>

            <br> Fullname* <input type="text" name="txtFullname" value="${param.txtFullname}" required/>
            <c:if test="${not empty requestScope.ERROR['fullname']}">
                <font color="red">${requestScope.ERROR['fullname']}</font>
            </c:if>
            <br/>
            <input type="submit" value="Register" />
            <input type="reset" value="Reset" />
        </form>
        <a href="loginPage">Return to login</a>
    </body>
</html>
