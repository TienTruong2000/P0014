<%-- 
    Document   : student
    Created on : Jan 25, 2021, 8:18:48 PM
    Author     : natton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.tientt.commons.TestStatus" %>
<%@page import="com.tientt.commons.Constant" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Page</title>
        <script type="text/javascript" src="js/student.js"></script>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <h2>Test page</h2>
        <a href="ShowQuizHistoryAction">Click to see your quiz history</a>
        <!--------------SEARCH FORM------------------>
        <c:if test="${not empty requestScope.SUBJECT_LIST}">
            <form action="ShowStudentPageAction">
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
                    <th>Open Time</th>
                    <th>Deadline</th>
                    <th>Length(minute)</th>
                    <th>Number of question</th>
                    <th>SubjectID</th>
                    <th>Status</th>
                    <th>Take</th>
                </tr>
                <c:forEach var="test" items="${requestScope.TEST_LIST}" varStatus="count">
                    <tr>
                        <td>${count.count}</td>
                        <td>${test.name}</td>
                        <td>
                            <jsp:useBean id="openTime" class="java.util.Date"/>
                            <jsp:setProperty name="openTime" property="time" value="${test.openTime}"/>
                            <f:formatDate value="${openTime}" pattern="${Constant.DATE_TIME_FORMAT}"/>
                        </td>
                        <td>
                            <jsp:useBean id="deadline" class="java.util.Date"/>
                            <jsp:setProperty name="deadline" property="time" value="${test.deadline}"/>
                            <f:formatDate value="${deadline}" pattern="${Constant.DATE_TIME_FORMAT}"/>
                        </td>
                        <td>${test.length}</td>
                        <td>${test.numOfQuestion}</td>
                        <td>${test.subject.name}</td>
                        <td>
                            ${test.status.name}
                        </td>
                        <td>
                            <c:if test="${test.status.statusCode == TestStatus.OPENED.statusCode}">
                                <form action="TakeQuizAction" onsubmit="checkTakeTest(this); return false;">
                                    <input type="hidden" name="testID" value="${test.ID}"/>
                                    <input type="hidden" name="deadline" value="${test.deadline}"/>
                                    <input type="submit" value="Take"/>
                                </form>
                            </c:if>
                            <c:if test="${test.status.statusCode == TestStatus.ON_GOING.statusCode}">
                                    <form action="ShowQuizAction">
                                    <input type="hidden" name="quizID" value="${test.quiz.ID}"/>
                                    <input type="submit" value="Continue"/>
                                </form>
                            </c:if>

                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>
