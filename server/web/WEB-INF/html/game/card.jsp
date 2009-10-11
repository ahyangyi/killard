<%@ include file="../includes.jsp" %>
<%--@elvariable id="playerName" type="java.lang.String"--%>
<%--@elvariable id="elementRecord" type="com.killard.jdo.board.player.ElementRecordDO"--%>
<%--@elvariable id="player" type="com.killard.jdo.board.player.PlayerRecordDO"--%>
<div class="elementschool">
    ${elementRecord.elementSchool.name}:&nbsp;${elementRecord.amount}
    <c:if test="${player.name == playerName}">
        <form action="/game/playcard.html" method="POST">
            <div id="newcard">
                <select name="cardIndex">
                    <c:forEach var="card"
                               items="${elementRecord.holdedCards}">
                        <option value="${card.key.id}">
                            <span style="font-size:10px;">${card.name},${card.level}</span>
                        </option>
                    </c:forEach>
                </select>
                <select name="cardPosition">
                    <c:forEach var="position"
                               items="${player.availablePositions}">
                        <option value="${position}">${position}</option>
                    </c:forEach>
                </select>
            </div>
            <c:if test="${not empty player.availablePositions and myturn and not player.cardPlayed}">
                <input type="hidden" name="targetPosition" value="1"/>
                <input type="submit" value="New"/>
            </c:if>
        </form>
    </c:if>
</div>