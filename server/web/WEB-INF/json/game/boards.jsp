[
<%@ include file="/WEB-INF/json/includes.jsp" %>
<%--@elvariable id="boards" type="java.util.List<com.killard.board.jdo.board.BoardDO>"--%>
<c:forEach var="board" items="${boards}" varStatus="status">
    {
        "packageBundleId" : "${board.packageBundleKey.id}",
        "id" : "${board.key.id}",
        "username" : "${board.creator}",
        "players" : "${board.playerAmount}"
    }<c:if test="${not status.last}">,</c:if>
</c:forEach>
]