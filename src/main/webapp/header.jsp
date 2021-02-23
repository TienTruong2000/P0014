<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>Quiz Online</h1>
<c:if test="${not empty sessionScope.USER}">
    <h2>Welcome, ${sessionScope.USER.fullname}</h2>
    <a href="LogoutAction">Logout</a>
</c:if>
<c:if test="${empty sessionScope.USER}">
    <a href="loginPage">Login</a>
</c:if>

