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
<jsp:include page="/WEB-INF/includes/navigation.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <header class="page-header">
                <h1>
                    ${movie.title()}
                    <small> Rating: <strong>${movie.rating()}</strong></small>
                </h1>
            </header>
            <div class="media">
                <div class="media-left media-middle">
                    <c:choose>
                        <c:when test="${empty movie.thumbnail()}">
                            <img src="http://placehold.it/100x100">
                        </c:when>
                        <c:otherwise>
                            <img src="${request.contextPath}${initParam["thumbnails"]}/${movie.thumbnail()}">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="media-body">
                    <div class="panel panel-default">
                        <div class="panel-heading">Categories</div>
                        <ul class="list-group">
                            <c:forEach items="${movie.categories()}" var="category">
                                <li class="list-group-item">${category.name()}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/includes/scripts.jsp"/>
</body>
</html>
