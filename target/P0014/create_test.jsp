<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: natton
  Date: 28-Jan-21
  Time: 3:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Test</title>
    <script type="text/javascript" src="js/admin.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/datejs/1.0/date.min.js"></script>
</head>
<body>
    <header>
        <jsp:include page="header.jsp"/>
        <h2>Test List</h2>
    </header>
    <a href="ShowTestAdminAction">See created test</a>
    <a href="ShowAdminPageAction">Return to main page</a>
    <form action="CreateTestAction" onsubmit="return validateCreateTestForm(this)">
        Test name: <input type="text" name="cTestName" value="${param.cTestName}" required/> <br>

        Open time: <input type="text" name="cTestOpenTime" value="${param.cTestOpenTime}"
                        placeholder="01/01/2021 21:00" required/> <br>

        <font class="error" color="red">
            ${requestScope.ERROR['openTime']}
        </font> <br>
        Deadline time:  <input type="text" name="cTestDeadlineTime" value="${param.cTestDeadlineTime}" required
                               placeholder="01/01/2021 21:00"/> <br>
        <font class="error" color="red">
            ${requestScope.ERROR['deadlineTime']}
        </font> <br>
        Test length (minute): <input type="number" name="cTestTimeLength" value="${param.cTestTimeLength}" required min="1"/> <br>
        <font class="error" color="red">
            ${requestScope.ERROR['testTimeLength']}
        </font> <br>
        Number of question: <input type="number" name="cTestNumOfQuestion" value="${param.cTestNumOfQuestion}" required min="1"/> <br>
        <font class="error" color="red">
            ${requestScope.ERROR['NumOfQuestion']}
        </font> <br>
        <c:if test="${not empty requestScope.SUBJECT_LIST}">
            Subject:
            <select name="cTestSubjectID">
                <c:forEach var="subject" items="${requestScope.SUBJECT_LIST}">
                    <option value="${subject.ID}">${subject.name}</option>
                </c:forEach>
            </select>
        </c:if>
        <input type="submit" value="Create"/>
    </form>


</body>
</html>
