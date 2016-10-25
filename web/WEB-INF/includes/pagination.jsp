<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="categoryId" value="${not empty param.category ? '&category='.concat(param.category) : '' }"/>
<nav aria-label="Page navigation">
    <ul class="pagination">
        <li>
            <c:if test="${movies.hasPreviousPage()}">
            <a
                href="${request.contextPath}?page=${movies.previousPage()}${categoryId}"
                aria-label="Previous"
            >
                <span aria-hidden="true">&laquo;</span>
            </a>
            </c:if>
        </li>
        <c:forEach begin="1" end="${movies.pagesCount()}" varStatus="loop">
            <li>
                <a href="${request.contextPath}?page=${loop.count}${categoryId}">
                    <c:out value="${loop.count}"/>
                </a>
            </li>
        </c:forEach>
        <li>
            <c:if test="${movies.hasNextPage()}">
            <a
                href="${request.contextPath}?page=${movies.nextPage()}${categoryId}"
                aria-label="Next"
            >
                <span aria-hidden="true">&raquo;</span>
            </a>
            </c:if>
        </li>
    </ul>
</nav>
