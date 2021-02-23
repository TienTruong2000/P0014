<%--
  Created by IntelliJ IDEA.
  User: natton
  Date: 28-Jan-21
  Time: 10:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.tientt.commons.Constant" %>
<html>
<head>
    <title>Create Question</title>
    <script type="text/javascript" src="js/admin.js"></script>
</head>
<body>
    <header>
        <jsp:include page="header.jsp"/>
        <h1>Create Question Page</h1>
    </header>
    <a href="ShowAdminPageAction">Return to main page</a>
    <form action="CreateQuestionAction" onsubmit="return validateCreateQuestionForm()">
        <table id="createTable">
            <tr>
                <td>Subject</td>
                <td colspan="2">
                    <select name="cSubjectID">
                        <c:forEach var="subject" items="${requestScope.SUBJECT_LIST}">
                             <option value="${subject.ID}">${subject.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Content</td>
                <td colspan="2">
                    <input type="text" name="cQuestionContent"/>
                </td>
            </tr>

            <c:forEach var="i" begin="0" end="${Constant.QUESTION_MAX_CHOICE - 1}" step="1" varStatus="count">
                <tr>
                    <td>Choice ${count.count}</td>
                    <td>
                        <input type="text" class="choiceContent" name="choiceContent${i}"/>
                    </td>
                    <td>
                        <input type="radio" name="correct" value="${i}"/> Correct
                    </td>
                </tr>
            </c:forEach>
        </table>
        <input type="submit" value="Create"/>
        <input type="reset" value="Reset" onclick="removeError()"/>
    </form>

</body>
</html>
