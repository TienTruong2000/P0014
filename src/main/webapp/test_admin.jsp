<%--
  Created by IntelliJ IDEA.
  User: natton
  Date: 29-Jan-21
  Time: 12:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.tientt.commons.Constant" %>
<html>
<head>
    <title>Test Page</title>
</head>
<body>
    <header>
        <jsp:include page="header.jsp"/>
        <h2>Test page</h2>
        <a href="ShowAdminPageAction">Main Page</a>
    </header>
    <!-----------------CREATE TEST------------------------>
    <a href="ShowCreateTestPageAction">Create new test</a>
    <!--------------SEARCH FORM------------------>
    <c:if test="${not empty requestScope.SUBJECT_LIST}">
        <form action="ShowTestAdminAction">
            <select name="sSubjectID">
                <c:if test="${empty param.sSubjectID}">
                    <option value="" selected>All</option>
                    <c:forEach var="subject" items="${requestScope.SUBJECT_LIST}">
                        <option value="${subject.ID}">${subject.name}</option>
                    </c:forEach>
                </c:if>
                <c:if test="${not empty param.sSubjectID}">
                    <option value="" >All</option>
                    <c:forEach var="subject" items="${requestScope.SUBJECT_LIST}">
                        <c:if test="${subject.ID == param.sSubjectID}">
                            <option value="${subject.ID}" selected>${subject.name}</option>
                        </c:if>
                        <c:if test="${subject.ID != param.sSubjectID}">
                            <option value="${subject.ID}">${subject.name}</option>
                        </c:if>
                    </c:forEach>
                </c:if>
            </select>
            <input type="submit" value="Search"/>
        </form>
    </c:if>
    <!-------------RESULT TABLE----------------->
    <c:if test="${not empty requestScope.TEST_LIST}">
        <table border="1">
            <tr>
                <th>No.</th>
                <th>Name</th>
                <th>Create date</th>
                <th>Open Time</th>
                <th>Deadline </th>
                <th>Length(minute)</th>
                <th>Number of question</th>
                <th>SubjectID</th>
            </tr>
            <c:forEach var="test" items="${requestScope.TEST_LIST}" varStatus="count">
                <tr>
                    <td>${count.count}</td>
                    <td>${test.name}</td>
                    <td>
                        <jsp:useBean id="createDate" class="java.util.Date"/>
                        <jsp:setProperty name="createDate" property="time" value="${test.createDate}"/>
                        <f:formatDate value="${createDate}" pattern="${Constant.DATE_TIME_FORMAT}"/>
                    </td>
                    <td>
                        <jsp:useBean id="openTime" class="java.util.Date"/>
                        <jsp:setProperty name="openTime" property="time" value="${test.openTime}"/>
                        <f:formatDate value="${openTime}" pattern="${Constant.DATE_TIME_FORMAT}"/>
                    </td>
                    <td>
                        <jsp:useBean id="deadline" class="java.util.Date"/>
                        <jsp:setProperty name="deadline" property="time" value="${test.deadlineTime}"/>
                        <f:formatDate value="${deadline}" pattern="${Constant.DATE_TIME_FORMAT}"/>
                    </td>
                    <td align="right">${test.testTimeLength}</td>
                    <td align="right">${test.numOfQuestion}</td>
                    <td>${test.subject.name}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty requestScope.TEST_LIST}">
        No test found
    </c:if>


</body>
</html>
