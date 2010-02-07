<%@ include file="/WEB-INF/js/includes.jsp" %>
<%--@elvariable id="time" type="long"--%>
<%--@elvariable id="lastUpdatedTime" type="long"--%>
<%--@elvariable id="board" type="com.killard.board.jdo.board.BoardDO"--%>
<%--@elvariable id="actions" type="java.util.List<com.killard.board.jdo.board.record.ActionLogDO>"--%>
{
"time":${time},
"actions":[
<c:forEach var="action" items="${actions}" varStatus="status">
    <c:if test="${action.time.time > lastUpdatedTime}">
    <c:if test="${not status.first}">,</c:if>{
    "actionClass" : "${action.actionClass}",
    "card" : {
        "name" : "${action.cardName}",
        "id" : 1,
        "position" : 1
        }
    }
    </c:if>
</c:forEach>
]
}