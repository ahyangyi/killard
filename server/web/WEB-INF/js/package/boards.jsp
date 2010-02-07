<%@ include file="/WEB-INF/js/includes.jsp" %>
<%--@elvariable id="boards" type="java.util.List<com.killard.board.jdo.board.BoardDO>"--%>
[
<c:forEach var="board" items="${boards}" varStatus="status">
    {
        "packageBundleId" : "${board.package.bundleKey.id}",
        "id" : "${board.key.id}",
        "username" : "${board.currentPlayer.id}",
        "players" : "${board.playerAmount}"
    }<c:if test="${not status.last}">,</c:if>
</c:forEach>
]