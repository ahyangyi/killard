<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementSchoolDO"--%>
<%--@elvariable id="card" type="com.killard.board.jdo.board.MetaCardDO"--%>
<div class="vmenu">
    <ul>
        <li><a href="<c:url value="/games"/>">All Games</a></li>
    </ul>
    <ul class="category">
        <li><a href="<c:url value="/games/${package.name}"/>">${package.descriptor.name}</a></li>
    </ul>
    <ul class="highlight">
        <li><a href="<c:url value="/games/${package.name}/element/${element.name}"/>">${element.descriptor.name}</a></li>
    </ul>
    <ul class="category">
        <li><a href="#">Cards</a></li>
    </ul>
    <ul>
        <c:forEach var="c" items="${element.cards}">
            <c:choose>
                <c:when test="${c.name == card.name}">
                    <li class="active">
                        <a href="<c:url value="/games/${bundle.name}/element/${element.name}/card/${c.name}"/>">
                                ${c.descriptor.name}
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="<c:url value="/games/${bundle.name}/element/${element.name}/card/${c.name}"/>">
                                ${c.descriptor.name}
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>
</div>