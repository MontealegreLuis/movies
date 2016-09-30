<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="/WEB-INF/includes/head.jsp">
        <jsp:param name="title" value="Add a new movie"/>
    </jsp:include>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <header class="page-header">
                <h1>Add a new movie</h1>
            </header>
            <form action="/movies/new" method="post">
                <div class="form-group">
                    <label for="title">Title</label>
                    <input
                        type="text"
                        class="form-control"
                        name="title"
                        id="title"
                        autofocus
                    >
                </div>
                <div class="form-group">
                    <label for="title">Rating</label><br>
                    <jsp:include page="/WEB-INF/includes/ratings.jsp"/>
                </div>
                <div class="form-group">
                    <label>Categories</label><br>
                    <c:forEach items="${categories}" var="category">
                    <label class="checkbox-inline">
                        <input
                            type="checkbox"
                            name="category[]"
                            value="${category.id()}"
                        >
                        ${category.name()}
                    </label>
                    </c:forEach>
                </div>
                <button type="submit" class="btn btn-primary">
                    <span class="glyphicon glyphicon-facetime-video"></span>
                    Save
                </button>
            </form>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/includes/scripts.jsp"/>
</body>
</html>
