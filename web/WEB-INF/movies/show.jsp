<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="/WEB-INF/includes/head.jsp">
        <jsp:param name="title" value="${movie.title()}"/>
    </jsp:include>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-8">
            <header class="page-header">
                <h1>${movie.title()}</h1>
            </header>
            <ul>
                <c:forEach items="${movie.categories()}" var="category">
                    <li>${category.name()}</li>
                </c:forEach>
            </ul>
            <p class="lead">Rating: <strong>${movie.rating()}</strong></p>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/includes/scripts.jsp"/>
</body>
</html>
