<%@ include file="../../includes.jsp" %>
<%--@elvariable id="bundle" type="com.killard.board.jdo.board.PackageBundleDO"--%>
<%--@elvariable id="package" type="com.killard.board.jdo.board.PackageDO"--%>
<%--@elvariable id="element" type="com.killard.board.jdo.board.ElementDO"--%>
<%--@elvariable id="attribute" type="com.killard.board.jdo.board.AttributeDO"--%>
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
        <li><a href="#">Attributes</a></li>
    </ul>
    <ul>
        <c:forEach var="a" items="${element.attributes}">
            <c:choose>
                <c:when test="${a.name == attribute.name}">
                    <li class="active">
                        <a href="<c:url value="/game/${bundle.name}/element/${element.name}/attribute/${a.name}"/>">
                                ${a.descriptor.name}
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="<c:url value="/game/${bundle.name}/element/${element.name}/attribute/${a.name}"/>">
                                ${a.descriptor.name}
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>
</div>