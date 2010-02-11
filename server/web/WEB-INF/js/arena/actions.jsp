{
<%@ include file="/WEB-INF/js/includes.jsp" %>
<%--@elvariable id="playerId" type="java.lang.String"--%>
<%--@elvariable id="time" type="long"--%>
<%--@elvariable id="lastUpdatedTime" type="long"--%>
<%--@elvariable id="board" type="com.killard.board.jdo.board.BoardDO"--%>
<%--@elvariable id="actions" type="java.util.List<com.killard.board.jdo.board.record.ActionLogDO>"--%>
"last":${lastUpdatedTime},
"time":${time},
"actions":[
<c:set var="start" value="false"/>
<c:forEach var="action" items="${actions}" varStatus="status">
    <c:if test="${action.time.time > lastUpdatedTime}">
        <c:if test="${start == 'true'}">,</c:if>
        <c:set var="start" value="true"/>
        {
        <c:choose>
            <c:when test="${action.actionClass == 'EquipCardAction'}">
                "card" : {
                "packageBundleId" : "${board.package.bundleKey.id}",
                "elementSchool" : "${action.cardElementSchoolName}",
                "name" : "${action.cardName}",
                "position" : "${action.cardPosition}"
                },
                "isSelf" : ${action.playerId == playerId},
                "playerNumber" : "1",
            </c:when>
        </c:choose>
        "time" : "${action.time.time}",
        "actionClass" : "${action.actionClass}"
    }
    </c:if>
</c:forEach>
]
}
