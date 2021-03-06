<%@ include file="../../includes.jsp" %>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<div class="vmenu">
    <ul>
        <li><a href="<c:url value="/game"/>">All Games</a></li>
    </ul>
    <ul class="highlight">
        <li><a href="<c:url value="/game/${package.name}"/>">${package.descriptor.name}</a></li>
    </ul>
    <ul class="category">
        <li><a href="#">Roles</a></li>
    </ul>
    <ul>
        <c:forEach var="role" items="${package.allRoles}">
        <li><a href="<c:url value="/game/${package.name}/role/${role.name}"/>">${role.descriptor.name}</a></li>
        </c:forEach>
    </ul>
    <ul class="category">
        <li><a href="#">Element Schools</a></li>
    </ul>
    <ul>
        <c:forEach var="element" items="${package.elements}">
        <li><a href="<c:url value="/game/${package.name}/element/${element.name}"/>">${element.descriptor.name}</a></li>
        </c:forEach>
    </ul>
</div>