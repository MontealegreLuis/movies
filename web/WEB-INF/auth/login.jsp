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
<jsp:include page="/WEB-INF/includes/navigation.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
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
                    <form method="post" action="${request.contextPath}/login">
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input
                                type="text"
                                class="form-control"
                                name="username"
                                id="username"
                                autofocus
                                value="${param.username}"
                            >
                            <jsp:include page="/WEB-INF/includes/errors.jsp">
                                <jsp:param name="field" value="username"/>
                            </jsp:include>
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input
                                type="password"
                                class="form-control"
                                name="password"
                                id="password"
                            >
                            <jsp:include page="/WEB-INF/includes/errors.jsp">
                                <jsp:param name="field" value="password"/>
                            </jsp:include>
                        </div>
                        <button type="submit" class="btn btn-primary">
                            <span class="glyphicon glyphicon-user"></span>
                            Login
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/includes/scripts.jsp"/>
</body>
</html>
