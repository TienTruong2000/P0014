<%--
    Document   : search
    Created on : Jan 25, 2021, 5:09:42 PM
    Author     : natton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.tientt.commons.QuestionStatus" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
        <script type="text/javascript" src="js/admin.js"></script>
    </head>
    <body>
        <header>
            <jsp:include page="header.jsp"/>
            <h1>Admin Page</h1>
        </header>
        <!-----------------CREATE QUESTION--------------------->
        <a href="ShowCreateQuestionPageAction">Create new question</a>
        <br/>
        <!-----------------CREATE TEST------------------------>
        <a href="ShowCreateTestPageAction">Create new test</a>
        <br/>
        <a href="ShowTestAdminAction">See created test</a>
        <!---------------SEARCH FORM---------------------->
        <form action="ShowAdminPageAction">
            Content: <input type="text" name="txtSearchQuestionContent"
                            value="${param.txtSearchQuestionContent}"/> </br>
            Subject:
            <c:if test="${not empty requestScope.SUBJECT_LIST}">
                <select name="searchQuestionSubjectID">
                    <c:if test="${empty param.searchQuestionSubjectID}">
                        <option value="" selected>All</option>
                        <c:forEach var="subject" items="${requestScope.SUBJECT_LIST}">
                            <option value="${subject.ID}">${subject.name}</option>
                        </c:forEach>
                    </c:if>
                    <c:if test="${not empty param.searchQuestionSubjectID}">
                        <option value="" >All</option>
                        <c:forEach var="subject" items="${requestScope.SUBJECT_LIST}">
                            <c:if test="${subject.ID == param.searchQuestionSubjectID}">
                                <option value="${subject.ID}" selected>${subject.name}</option>
                            </c:if>
                            <c:if test="${subject.ID != param.searchQuestionSubjectID}">
                                <option value="${subject.ID}">${subject.name}</option>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select> <br>
            </c:if>
            Status:
            <select name="searchQuestionStatus">
                <c:if test="${empty param.searchQuestionStatus}">
                    <option value="" selected>All</option>
                    <option value="1" >Active</option>
                    <option value="2">Inactive</option>
                </c:if>
                <c:if test="${not empty param.searchQuestionStatus}">
                    <option value="">All</option>
                    <c:if test="${param.searchQuestionStatus == 1}">
                        <option value="1" selected>Active</option>
                        <option value="2">Inactive</option>
                    </c:if>
                    <c:if test="${param.searchQuestionStatus == 2}">
                        <option value="1" >Active</option>
                        <option value="2" selected>Inactive</option>
                    </c:if>
                </c:if>

            </select>
            <input type="submit" value="Search"/>
        </form>
        <!-----------------RESULT LIST------------------------>
        <c:set var="questionList" value="${requestScope.SEARCH_RESULT}"/>
        <c:if test="${not empty questionList}">
            <c:forEach var="question" items="${questionList}">
                <table border="1" id="${question.ID}">
                    <tr>
                        <td>ID</td>
                        <td colspan="2">
                            ${question.ID}
                        </td>
                    </tr>
                    <tr>
                        <td>Status</td>
                        <td colspan="2">
                            <select name="uStatus">
                                <c:if test="${question.status == 1}">
                                    <option value="1" selected>Active</option>
                                    <option value="2">Inactive</option>
                                </c:if>
                                <c:if test="${question.status == 2}">
                                    <option value="1">Active</option>
                                    <option value="2" selected>Inactive</option>
                                </c:if>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Subject</td>
                        <td colspan="2">
                            <select name="uSubjectID">
                                <c:forEach var="subject" items="${requestScope.SUBJECT_LIST}">
                                    <c:if test="${subject.ID == question.subject.ID}">
                                        <option value="${subject.ID}" selected>${subject.name}</option>
                                    </c:if>
                                    <c:if test="${subject.ID != question.subject.ID}">
                                        <option value="${subject.ID}">${subject.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Create date</td>
                        <td colspan="2">
                            <jsp:useBean id="date" class="java.util.Date"/>
                            <jsp:setProperty name="date" property="time" value="${question.createDate}"/>
                            <f:formatDate value="${date}" pattern="dd/MM/yyyy" var="formatDate"/>
                                ${formatDate}
                        </td>
                    </tr>
                    <tr>
                        <td>Content</td>
                        <td colspan="2">
                            <input type="text" name="uContent" value="${question.content}"/>
                        </td>
                    </tr>
                    <c:forEach var="choice" items="${question.tblChoiceList}" varStatus="count">
                        <tr name="choice" id="${choice.ID}">
                            <td>Option ${count.count}</td>
                            <td>
                                <input type="text" name="choiceContent" value="${choice.content}"/>
                            </td>
                            <td>
                                <c:if test="${choice.correct}">
                                    <input type="radio" name="correct_${question.ID}" value="${choice.ID}" checked="checked"/> Correct
                                </c:if>
                                <c:if test="${not choice.correct}">
                                    <input type="radio" name="correct_${question.ID}" value="${choice.ID}"/> Correct
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="3">
                            <button onclick="updateQuestion('${question.ID}', this)">Update</button>
                        </td>

                    </tr>
                </table>
            </c:forEach>
            <!----------Paging---------------->
            <c:if test="${not empty requestScope.PREV_PAGE}">
                <c:url var="prevPage" value="ShowAdminPageAction">
                    <c:param name="txtSearchQuestionContent" value="${param.txtSearchQuestionContent}"/>
                    <c:param name="searchQuestionSubjectID" value="${param.searchQuestionSubjectID}"/>
                    <c:param name="searchQuestionStatus" value="${param.searchQuestionStatus}"/>
                    <c:param name="page" value="${requestScope.PREV_PAGE}"/>
                </c:url>
                <a href="${prevPage}"><</a>
            </c:if>
            ${requestScope.PAGE}
            <c:if test="${not empty requestScope.NEXT_PAGE}">
                <c:url var="nextPage" value="ShowAdminPageAction">
                    <c:param name="txtSearchQuestionContent" value="${param.txtSearchQuestionContent}"/>
                    <c:param name="searchQuestionSubjectID" value="${param.searchQuestionSubjectID}"/>
                    <c:param name="searchQuestionStatus" value="${param.searchQuestionStatus}"/>
                    <c:param name="page" value="${requestScope.NEXT_PAGE}"/>
                </c:url>
                <a href="${nextPage}">></a>
            </c:if>
        </c:if>
        <c:if test="${empty questionList}">
            No question found
        </c:if>
    </body>
</html>
