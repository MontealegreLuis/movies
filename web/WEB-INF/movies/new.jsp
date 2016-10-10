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
<jsp:include page="/WEB-INF/includes/navigation.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <header class="page-header">
                <h1>Add a new movie</h1>
            </header>
            <div class="panel panel-default">
                <div class="panel-heading">Movie details</div>
                <div class="panel-body">
                    <form
                        action="${request.contextPath}/movies/new"
                        method="post"
                        enctype="multipart/form-data"
                    >
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
                        <div class="form-group">
                            <label for="thumbnail">Thumbnail</label>
                            <div class="input-group">
                                <label class="input-group-btn">
                                    <span class="btn btn-primary">
                                        Browse&hellip;
                                        <input
                                            type="file"
                                            id="thumbnail"
                                            name="thumbnail"
                                            style="display: none;"
                                            multiple
                                        >
                                    </span>
                                </label>
                                <input type="text" class="form-control" disabled>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary">
                            <span class="glyphicon glyphicon-facetime-video"></span>
                            Save
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/includes/scripts.jsp"/>
<script src="${request.contextPath}/assets/scripts/upload.js"></script>
</body>
</html>
