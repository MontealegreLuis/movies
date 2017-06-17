<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button
                type="button"
                class="navbar-toggle collapsed"
                data-toggle="collapse"
                data-target="#bs-example-navbar-collapse-1"
                aria-expanded="false"
            >
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${request.contextPath}/">
                <span class="glyphicon glyphicon-hd-video"></span>
                movies.com
            </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <c:choose>
                <c:when test="${user != null}">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a>Hi ${user.username()}</a></li>
                        <li>
                            <a href="${request.contextPath}/movies/new">
                                <span class="glyphicon glyphicon-facetime-video"></span>
                                Add a movie
                            </a>
                        </li>
                        <li>
                            <a href="${request.contextPath}/logout">
                                <span class="glyphicon glyphicon-off"></span>
                                Logout
                            </a>
                        </li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="${request.contextPath}/sign-up">
                                <strong>Sign up!</strong>
                            </a>
                        </li>
                        <li><a href="${request.contextPath}/login">Login</a></li>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
