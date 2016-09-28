<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="/WEB-INF/includes/head.jsp">
        <jsp:param name="title" value="Search your favorite movies"/>
    </jsp:include>
</head>
<body>
    <jsp:include page="/WEB-INF/includes/navigation.jsp"/>
    <div class="container">
        <div class="row">
            <div class="col-md-8">
                <header class="page-header">
                    <h1>Search your favorite movies</h1>
                </header>
            </div>
            <div class="col-md-4 search">
                <form class="form-inline">
                    <div class="form-group">
                        <label for="category">Category</label>
                        <select name="category" id="category" class="form-control">
                            <option value="">Choose one</option>
                            <c:forEach items="${categories}" var="category">
                                <option value="${category.id()}">
                                    ${category.name()}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-default">
                        <span class="glyphicon glyphicon-search"></span>
                        Search
                    </button>
                </form>
            </div>
        </div>
        <table class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Rating</th>
                    <c:if test="${user != null}">
                    <th>Rate!</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${movies}" var="movie">
            <tr>
                <td>
                    <a href="/movies/show?id=${movie.id()}">
                        ${movie.title()}
                    </a>
                </td>
                <td>${movie.rating()}</td>
                <c:if test="${user != null}">
                <td>
                    <form action="/movies/rate" method="post">
                        <input type="hidden" name="id" value="${movie.id()}">
                        <jsp:include page="/WEB-INF/includes/ratings.jsp"/>
                        &nbsp;&nbsp;
                        <button type="submit" class="btn btn-primary">
                            <span class="glyphicon glyphicon-ok"></span>
                            Rate
                        </button>
                    </form>
                </td>
                </c:if>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <jsp:include page="/WEB-INF/includes/scripts.jsp"/>
</body>
</html>
