{
<%@ include file="/WEB-INF/js/includes.jsp" %>
<%--@elvariable id="lastAction" type="long"--%>
<%--@elvariable id="board" type="com.killard.board.jdo.board.BoardDO"--%>
<%--@elvariable id="playerId" type="java.lang.String"--%>
<%--@elvariable id="players" type="com.killard.board.jdo.board.record.PlayerRecordDO[]"--%>
"players":[
<c:forEach var="player" items="${players}" varStatus="status">
    <c:if test="${not status.first}">,</c:if>{
        "username" : "${player.nickname}",
        "self" : ${player.id == playerId},
        "current" : ${player.number == board.currentPlayerNumber},
        "number" : ${player.number},
        "health" : ${player.health},
        "dealtCards" : [
    <c:if test="${player.id == playerId and player.number > 0}">
    <c:forEach var="element" items="${player.elementRecords}" varStatus="elementStatus">
        <c:forEach var="card" items="${element.dealtCards}" varStatus="cardStatus">
        <c:if test="${not (elementStatus.first and cardStatus.first)}">,</c:if>{
        "name" : "${card.key.name}",
        "elementSchool" : "${card.elementSchool.name}",
        "level" : ${card.level},
        "maxHealth" : ${card.maxHealth},
        "attack" : ${card.attack.value}
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
        "position" : ${card.position},
        "level" : ${card.level},
        "maxHealth" : ${card.maxHealth},
        "health" : ${card.health},
        "attack" : ${card.attack.value},
        "skills" : [
        <c:forEach var="skill" items="${card.skills}" varStatus="skillStatus">
            <c:if test="${not skillStatus.first}">,</c:if>{
            "name" : "${skill.name}",
            "cost" : ${skill.cost},
            "targets" : [
            <c:forEach var="target" items="${skill.targets}" varStatus="targetStatus">
                <c:if test="${not targetStatus.first}">,</c:if>"${target.name}"
            </c:forEach>
            ]
            }
        </c:forEach>
        ]
        }
    </c:forEach>
    </c:if>
        ]
    }
</c:forEach>
],
"lastAction":${lastAction}
}
