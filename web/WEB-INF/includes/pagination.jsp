<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav aria-label="Page navigation">
    <ul class="pagination">
        <li>
            <a href="#" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <c:forEach begin="1" end="${movies.pagesCount()}" varStatus="loop">
            <li>
                <a href="${request.contextPath}?page=${loop.count}">
                    <c:out value="${loop.count}"/>
                </a>
            </li>
        </c:forEach>
        <li>
            <a href="#" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>
