{
<%@ include file="/WEB-INF/js/includes.jsp" %>
<%--@elvariable id="playerId" type="java.lang.String"--%>
<%--@elvariable id="board" type="com.killard.board.jdo.board.BoardDO"--%>
<%--@elvariable id="actions" type="java.util.Collection<com.killard.board.jdo.board.record.ActionLogDO>"--%>
"actions":[
<c:set var="start" value="false"/>
<c:forEach var="action" items="${actions}" varStatus="status">
        <c:if test="${start == 'true'}">,</c:if>
        <c:set var="start" value="true"/>
        {
        <c:choose>
            <c:when test="${action.actionClass == 'PlayerJoinAction'}">
                "player" : {
                "nickname" : "${action.nickname}",
                "number" : "${action.number}",
                "self" : ${action.playerId == playerId}
                },
            </c:when>
            <c:when test="${action.actionClass == 'PlayerQuitAction'}">
                "player" : {
                "nickname" : "${action.nickname}",
                "number" : "${action.number}",
                "self" : ${action.playerId == playerId}
                },
            </c:when>
            <c:when test="${action.actionClass == 'KillPlayerAction'}">
                "player" : {
                "nickname" : "${action.nickname}",
                "number" : "${action.number}",
                "self" : ${action.playerId == playerId}
                },
            </c:when>
            <c:when test="${action.actionClass == 'BeginTurnAction'}">
                "player" : {
                "nickname" : "${action.nickname}",
                "number" : "${action.number}",
                "self" : ${action.playerId == playerId}
                },
            </c:when>
            <c:when test="${action.actionClass == 'EndTurnAction'}">
                "player" : {
                "nickname" : "${action.nickname}",
                "number" : "${action.number}",
                "self" : ${action.playerId == playerId}
                },
            </c:when>
            <c:when test="${action.actionClass == 'DealCardAction'}">
                "card" : {
                "elementSchool" : "${action.cardElementSchoolName}",
                "name" : "${action.cardName}"
                },
            </c:when>
            <c:when test="${action.actionClass == 'EquipCardAction'}">
                "card" : {
                "elementSchool" : "${action.cardElementSchoolName}",
                "name" : "${action.cardName}",
                "position" : "${action.cardPosition}"
                },
                "self" : ${action.playerId == playerId},
                "playerNumber" : "1",
            </c:when>
        </c:choose>
        "time" : "${action.time.time}",
        "actionClass" : "${action.actionClass}"
    }
</c:forEach>
]
}
