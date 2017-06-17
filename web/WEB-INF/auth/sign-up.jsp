<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/includes/head.jsp">
        <jsp:param name="title" value="Sign up!"/>
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/includes/navigation.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <header class="page-header">
                <h1>Sign up! <small>Discover awesome movies to watch!</small></h1>
            </header>
            <div class="panel panel-default">
                <div class="panel-heading">
                    Welcome!
                </div>
                <div class="panel-body">
                    <form method="post" action="${request.contextPath}/sign-up">
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input type="text" id="username" name="username" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" id="password" name="password" class="form-control">
                        </div>
                        <button class="btn btn-primary">
                            <span class="glyphicon glyphicon-ok"></span> Sign me up!
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
