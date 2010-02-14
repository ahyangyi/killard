{
<%@ include file="/WEB-INF/js/includes.jsp" %>
<%--@elvariable id="time" type="long"--%>
<%--@elvariable id="playerId" type="java.lang.String"--%>
<%--@elvariable id="players" type="com.killard.board.jdo.board.record.PlayerRecordDO[]"--%>
"time":${time},
"players":[
<c:forEach var="player" items="${players}" varStatus="status">
    {
        "username" : "${player.id}",
        "isSelf" : ${player.id == playerId},
        "number" : ${player.number},
        "dealtCards" : [
    <c:if test="${player.id == playerId and player.number > 0}">
    <c:forEach var="element" items="${player.elementRecords}" varStatus="elementStatus">
        <c:forEach var="card" items="${element.dealtCards}" varStatus="cardStatus">
        <c:if test="${not (elementStatus.first and cardStatus.first)}">,</c:if>{
        "name" : "${card.key.name}",
        "elementSchool" : "${card.elementSchool.name}"
        }
        </c:forEach>
    </c:forEach>
    </c:if>
        ],
        "equippedCards" : [
    <c:if test="${player.number > 0}">
    <c:forEach var="card" items="${player.equippedCards}" varStatus="cardStatus">
        <c:if test="${not cardStatus.first}">,</c:if>{
        "name" : "${card.name}",
        "elementSchool" : "${card.elementSchool.key.name}",
        "position" : ${card.position}
        }
    </c:forEach>
    </c:if>
        ]
    }<c:if test="${not status.last}">,</c:if>
</c:forEach>
]
}
