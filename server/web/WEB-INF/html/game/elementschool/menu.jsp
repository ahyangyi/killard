<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<div class="vmenu">
    <ul>
        <li><a href="<c:url value="/game"/>">All Games</a></li>
    </ul>
    <ul class="category">
        <li><a href="<c:url value="/game/${package.name}"/>">${package.descriptor.name}</a></li>
    </ul>
    <ul class="highlight">
        <li><a href="<c:url value="/game/${package.name}/element/${element.name}"/>">${element.descriptor.name}</a></li>
    </ul>
    <ul class="category">
        <li><a href="#">Cards</a></li>
    </ul>
    <ul>
        <c:forEach var="card" items="${element.cards}">
        <li>
            <a href="<c:url value="/game/${bundle.name}/element/${element.name}/card/${card.name}"/>">
            ${card.descriptor.name}
            </a>
        </li>
        </c:forEach>
    </ul>
</div>