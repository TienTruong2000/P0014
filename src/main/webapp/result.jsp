<%--
  Created by IntelliJ IDEA.
  User: natton
  Date: 31-Jan-21
  Time: 7:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Result</title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <c:set var="quiz" value="${requestScope.QUIZ}"/>
    <a href="ShowStudentPageAction">Click to return to main page</a>
    <br>
    <a href="ShowQuizHistoryAction">Click to see your quiz history</a>
    <table>
        <tr>
            <td>Test name</td>
            <td> ${quiz.test.name}</td>
        </tr>
        <tr>
            <td>Begin time</td>
            <td>
                <jsp:useBean id="beginDate" class="java.util.Date"/>
                <jsp:setProperty name="beginDate" property="time" value="${quiz.beginTime}"/>
                <f:formatDate value="${beginDate}" pattern="dd/MM/yyyy hh:mm:ss" var="formatDate"/>
                ${formatDate}
            </td>
        </tr>
        <tr>
            <td>End time</td>
            <td>
                <jsp:useBean id="endTime" class="java.util.Date"/>
                <jsp:setProperty name="endTime" property="time" value="${quiz.endTime}"/>
                <f:formatDate value="${endTime}" pattern="dd/MM/yyyy hh:mm:ss" var="formatDate"/>
                ${formatDate}
            </td>
        </tr>
        <tr>
            <td>Mark</td>
            <td>${quiz.mark} / 10</td>
        </tr>
    </table>
</body>
</html>
