<%@ include file="/WEB-INF/js/includes.jsp" %>
<%--@elvariable id="packages" type="java.util.List<com.killard.board.jdo.board.PackageDO>"--%>
[
<c:forEach var="package" items="${packages}" varStatus="status">
    {
        "id" : "${package.bundleKey.id}",
        "title" : "${package.descriptor.name}",
        "description" : "${package.descriptor.description}",
        "picture" : "image/index.png"
    }<c:if test="${not status.last}">,</c:if>
</c:forEach>
]
