<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty errors && errors.containsKey(param.field)}">
    <c:forEach var="item" items="${errors.get(param.field)}">
        <p class="help-block alert alert-danger">
            ${item}
        </p>
    </c:forEach>
</c:if>
