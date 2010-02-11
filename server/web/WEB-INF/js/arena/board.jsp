{
<%@ include file="/WEB-INF/js/includes.jsp" %>
<%--@elvariable id="time" type="long"--%>
<%--@elvariable id="board" type="com.killard.board.jdo.board.BoardDO"--%>
<%--@elvariable id="playerId" type="java.lang.String"--%>
<%--@elvariable id="players" type="com.killard.board.jdo.board.record.PlayerRecordDO[]"--%>
"time":${time},
"players":[
<c:forEach var="player" items="${players}" varStatus="status">
    {
        "username" : "${player.id}",
        "isSelf" : ${player.id == playerId},
        "number" : ${status.count},
    <c:if test="${player.id == playerId}">
        "dealtCards" : [
    <c:forEach var="element" items="${player.elementRecords}" varStatus="elementStatus">
        <c:forEach var="card" items="${element.dealtCards}" varStatus="cardStatus">
        <c:if test="${not (elementStatus.first and cardStatus.first)}">,</c:if>{
        "name" : "${card.key.name}",
        "elementSchool" : "${card.elementSchool.name}",
        "packageBundleId" : ${board.package.bundleKey.id}
        }
        </c:forEach>
    </c:forEach>
        ],
    </c:if>
        "equippedCards" : [
    <c:forEach var="card" items="${player.equippedCards}" varStatus="cardStatus">
        <c:if test="${not cardStatus.first}">,</c:if>{
        "name" : "${card.name}",
        "elementSchool" : "${card.elementSchool.key.name}",
        "packageBundleId" : "${board.package.bundleKey.id}",
        "position" : ${card.position}
        }
    </c:forEach>
        ]
    }<c:if test="${not status.last}">,</c:if>
</c:forEach>
]
}
