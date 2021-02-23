<%--
  Created by IntelliJ IDEA.
  User: natton
  Date: 01-Feb-21
  Time: 11:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.tientt.commons.Constant" %>
<html>
<head>
    <title>Quiz History</title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <h2>Quiz history </h2>
    <a href="ShowStudentPageAction">Click to return to main page</a>
    <!--------------SEARCH FORM------------------>
    <c:if test="${not empty requestScope.SUBJECT_LIST}">
        <form action="ShowQuizHistoryAction">
            <select name="subjectID">
                <c:if test="${empty param.subjectID}">
                    <option value="" selected>All</option>
                    <c:forEach var="subject" items="${requestScope.SUBJECT_LIST}">
                        <option value="${subject.ID}">${subject.name}</option>
                    </c:forEach>
                </c:if>
                <c:if test="${not empty param.subjectID}">
                    <option value="" >All</option>
                    <c:forEach var="subject" items="${requestScope.SUBJECT_LIST}">
                        <c:if test="${subject.ID == param.subjectID}">
                            <option value="${subject.ID}" selected>${subject.name}</option>
                        </c:if>
                        <c:if test="${subject.ID != param.subjectID}">
                            <option value="${subject.ID}">${subject.name}</option>
                        </c:if>
                    </c:forEach>
                </c:if>
            </select>
            <input type="submit" value="Search"/>
        </form>
    </c:if>
    <!------------RESULT TABLE--------------------->
    <c:if test="${not empty requestScope.QUIZ_LIST}">
        <table border="1">
            <tr>
                <th>No.</th>
                <th>Name</th>
                <th>Begin Time</th>
                <th>End time</th>
                <th>Length(minute)</th>
                <th>Number of question</th>
                <th>SubjectID</th>
                <th>Mark</th>
                <th>Detail</th>
            </tr>
            <c:forEach var="quiz" items="${requestScope.QUIZ_LIST}" varStatus="count">
                <tr>
                    <td>${count.count}</td>
                    <td>${quiz.test.name}</td>
                    <td>
                        <jsp:useBean id="beginTime" class="java.util.Date"/>
                        <jsp:setProperty name="beginTime" property="time" value="${quiz.beginTime}"/>
                        <f:formatDate value="${beginTime}" pattern="${Constant.DATE_TIME_FORMAT}"/>
                    </td>
                    <td>
                        <jsp:useBean id="endTime" class="java.util.Date"/>
                        <jsp:setProperty name="endTime" property="time" value="${quiz.endTime}"/>
                        <f:formatDate value="${endTime}" pattern="${Constant.DATE_TIME_FORMAT}"/>
                    </td>
                    <td>
                        <f:formatNumber var="minute" value="${(quiz.endTime - quiz.beginTime) / (60 * 1000)}" maxFractionDigits="0"/>
                        <f:formatNumber var="second" value="${((quiz.endTime - quiz.beginTime) % (60 * 1000)) / 1000}" maxFractionDigits="0"/>
                        ${minute}m${second}s
                    </td>
                    <td align="right">${quiz.test.numOfQuestion}</td>
                    <td>${quiz.test.subject.ID}</td>
                    <td>${quiz.mark}</td>
                    <td>
                        <a href="ShowResultAction?quizID=${quiz.ID}">Detail</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</body>
</html>
