<%--
  Created by IntelliJ IDEA.
  User: natton
  Date: 31-Jan-21
  Time: 4:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quiz</title>
    <script type="text/javascript" src="js/quiz.js">
    </script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <a href="ShowStudentPageAction">Main Page</a>
    <h2>${requestScope.QUIZ.test.name}</h2>

    <div id="countdown"></div>
    <c:forEach var="question" items="${requestScope.QUIZ.quizQuestionList}" varStatus="count">
        <form>
            <h3>Question no. ${count.count}</h3>
            <span>${question.content}</span>
            <br>
            <c:forEach var="choice" items="${question.quizChoiceList}" varStatus="count">
                <c:if test="${choice.select}">
                    <input type="radio" name="${question.ID}" value="${choice.ID}" checked/>
                    ${choice.content} <br>
                </c:if>
                <c:if test="${not choice.select}">
                    <input type="radio" name="${question.ID}" value="${choice.ID}"/>
                    ${choice.content} <br>
                </c:if>

            </c:forEach>
        </form>
    </c:forEach>
    <button id="submit">Submit</button>
    <script type="text/javascript">
        addRadioEvent();
        addSubmitEvent('${requestScope.QUIZ.ID}');
        countdown('${requestScope.QUIZ.endTime}');
        updateSubmission('${requestScope.QUIZ.ID}');
    </script>

</body>
</html>
