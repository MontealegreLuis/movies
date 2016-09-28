<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="/WEB-INF/includes/head.jsp">
        <jsp:param name="title" value="Welcome back!"/>
    </jsp:include>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <header class="page-header">
                <h1>Welcome back!</h1>
            </header>
            <div class="panel panel-default">
                <div class="panel-heading">
                    Login
                </div>
                <div class="panel-body">
                    <c:if test="${requestScope.error != null}">
                        <p class="help-block alert alert-danger">
                            ${requestScope.error}
                        </p>
                    </c:if>
                    <form method="post" action="/login">
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input
                                type="text"
                                class="form-control"
                                name="username"
                                id="username"
                                autofocus
                            >
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input
                                type="password"
                                class="form-control"
                                name="password"
                                id="password"
                            >
                        </div>
                        <button type="submit" class="btn btn-primary">Login</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/includes/scripts.jsp"/>
</body>
</html>
