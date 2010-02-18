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
        <c:if test="${action.playerId != null}">
            "player" : {
            "nickname" : "${action.nickname}",
            "number" : "${action.number}",
            "self" : ${action.playerId == playerId}
            },
        </c:if>
        <c:if test="${action.cardPosition > 0}">
            "card" : {
            "elementSchool" : "${action.cardElementSchoolName}",
            "name" : "${action.cardName}",
            "position" : "${action.cardPosition}"
            },
            "self" : ${action.playerId == playerId},
            "playerNumber" : "1",
        </c:if>
        <c:if test="${action.action == 'DealCardAction'}">
            "card" : {
            "elementSchool" : "${action.cardElementSchoolName}",
            "name" : "${action.cardName}"
            },
        </c:if>
        "time" : "${action.time.time}",
        "actionClass" : "${action.action}"
    }
</c:forEach>
]
}
