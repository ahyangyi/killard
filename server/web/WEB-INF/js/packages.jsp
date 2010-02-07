<%@ include file="/WEB-INF/js/includes.jsp" %>
<%--@elvariable id="packages" type="java.util.List<com.killard.board.jdo.board.PackageDO>"--%>
[
<c:forEach var="package" items="${packages}">
    {
        id : ${package.bundleKey.id},
        title : "${package.name}",
        picture : "image/2.png"
    }
</c:forEach>
]