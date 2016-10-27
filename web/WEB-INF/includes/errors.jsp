<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty errors && errors.containsKey(param.field)}">
    <ul class="list-group">
    <c:forEach var="item" items="${errors.get(param.field)}">
        <li class="list-group-item list-group-item-danger">${item}</li>
    </c:forEach>
    </ul>
</c:if>
