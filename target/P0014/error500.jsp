<%-- 
    Document   : error
    Created on : Jan 11, 2021, 5:28:13 PM
    Author     : natton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.tientt.commons.Role" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <jsp:include page="header.jsp"/>
    <body>
        <h1>Oops, something went wrong</h1>
        <h2>Please try again</h2>
        <c:if test="${not empty sessionScope.USER}">
            <c:if test="${sessionScope.USER.role.ID == Role.ADMIN}">
                <a href="ShowAdminPageAction">Return to main page</a>
            </c:if>
            <c:if test="${sessionScope.USER.role.ID == Role.STUDENT}">
                <a href="ShowStudentPageAction">Return to main page</a>
            </c:if>
        </c:if>
    </body>
</html>
