[
<%@ include file="/WEB-INF/json/includes.jsp" %>
<%--@elvariable id="packages" type="java.util.List<com.killard.board.jdo.board.PackageDO>"--%>
<c:forEach var="package" items="${packages}" varStatus="status">
    {
        "bundle" : "${package.bundleKey.name}",
        "id" : "${package.key.id}",
        "title" : "${package.descriptor.name}",
        "description" : "${package.descriptor.description}",
        "picture" : "/image/index.png"
    }<c:if test="${not status.last}">,</c:if>
</c:forEach>
]
